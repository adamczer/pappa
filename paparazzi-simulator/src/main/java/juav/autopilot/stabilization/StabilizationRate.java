package juav.autopilot.stabilization;

import juav.autopilot.radiocontrol.RadioControl;
import juav.autopilot.telemetry.TelemetryCb;
import ub.juav.airborne.math.structs.algebra.Rates;

import static juav.autopilot.commands.Commands.*;
import static juav.autopilot.guidance.GuidanceH.*;
import static juav.autopilot.messages.Messages.*;
import static juav.autopilot.radiocontrol.RadioControl.*;
import static juav.autopilot.stabilization.Stabilization.*;
import static juav.autopilot.state.State.stateGetBodyRates_i;
import static juav.autopilot.telemetry.Telemetry.*;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.*;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.*;

/**
 * Created by adamczer on 11/6/16.
 */
public class StabilizationRate {

    public static final int STABILIZATION_RATE_DEADBAND_P = 20;
    public static final int STABILIZATION_RATE_DEADBAND_Q = 20;
    public static final int STABILIZATION_RATE_DEADBAND_R = 200;

    public static final int STABILIZATION_RATE_GAIN_P = 400;
    public static final int STABILIZATION_RATE_GAIN_Q = 400;
    public static final int STABILIZATION_RATE_GAIN_R = 350;

    public static final double STABILIZATION_RATE_SP_MAX_P = 2.44346095;
    public static final double STABILIZATION_RATE_SP_MAX_Q = 2.44346095;
    public static final double STABILIZATION_RATE_SP_MAX_R = 2.44346095;

    public static final int MAX_SUM_ERR = 4000000;
    public static final int STABILIZATION_RATE_IGAIN_P = 75;
    public static final int STABILIZATION_RATE_IGAIN_Q = 75;
    public static final int STABILIZATION_RATE_IGAIN_R = 50;


// TODO CHECK if positive   #if (STABILIZATION_RATE_GAIN_P < 0) || \
//            (STABILIZATION_RATE_GAIN_Q < 0)   || \
//            (STABILIZATION_RATE_GAIN_R < 0)   || \
//            (STABILIZATION_RATE_IGAIN_P < 0)  || \
//            (STABILIZATION_RATE_IGAIN_Q < 0)  || \
//            (STABILIZATION_RATE_IGAIN_R < 0)
//            #error "ALL control gains have to be positive!!!"
//            #endif

    public static int OFFSET_AND_ROUND(int _a, int _b) {
        return (((_a) + (1 << ((_b) - 1))) >> (_b));
    }

    public static int OFFSET_AND_ROUND2(int _a, int _b) {
        return (((_a) + (1 << ((_b) - 1)) - ((_a) < 0 ? 1 : 0)) >> (_b));
    }

    public static Rates<Integer> stabilization_rate_sp = Rates.newInteger();
    public static Rates<Integer> stabilization_rate_gain = Rates.newInteger();
    public static Rates<Integer> stabilization_rate_igain = Rates.newInteger();
    public static Rates<Integer> stabilization_rate_sum_err = Rates.newInteger();

    public static Rates<Integer> stabilization_rate_fb_cmd = Rates.newInteger();


    public static boolean ROLL_RATE_DEADBAND_EXCEEDED() {
        return (RadioControl.values[RADIO_ROLL] > STABILIZATION_RATE_DEADBAND_P ||
                RadioControl.values[RADIO_ROLL] < -STABILIZATION_RATE_DEADBAND_P);
    }

    public static boolean PITCH_RATE_DEADBAND_EXCEEDED() {
        return (RadioControl.values[RADIO_PITCH] > STABILIZATION_RATE_DEADBAND_Q ||
                RadioControl.values[RADIO_PITCH] < -STABILIZATION_RATE_DEADBAND_Q);
    }

    public static boolean YAW_RATE_DEADBAND_EXCEEDED() {
        return (RadioControl.values[RADIO_YAW] > STABILIZATION_RATE_DEADBAND_R ||
                RadioControl.values[RADIO_YAW] < -STABILIZATION_RATE_DEADBAND_R);
    }



// #if PERIODIC_TELEMETRY
//    public static void send_rate(struct transport_tx *trans, struct link_device *dev)
//    {
//        pprz_msg_send_RATE_LOOP(trans, dev, AC_ID,
//                stabilization_rate_sp.p,
//        stabilization_rate_sp.q,
//        stabilization_rate_sp.r,
//        stabilization_rate_sum_err.p,
//        stabilization_rate_sum_err.q,
//        stabilization_rate_sum_err.r,
//        stabilization_rate_fb_cmd.p,
//        stabilization_rate_fb_cmd.q,
//        stabilization_rate_fb_cmd.r,
//        stabilization_cmd[COMMAND_THRUST]);
//    }
//    #endif

    public static void stabilization_rate_init() {

        INT_RATES_ZERO(stabilization_rate_sp);

        RATES_ASSIGN(stabilization_rate_gain,
                STABILIZATION_RATE_GAIN_P,
                STABILIZATION_RATE_GAIN_Q,
                STABILIZATION_RATE_GAIN_R);
        RATES_ASSIGN(stabilization_rate_igain,
                STABILIZATION_RATE_IGAIN_P,
                STABILIZATION_RATE_IGAIN_Q,
                STABILIZATION_RATE_IGAIN_R);

        INT_RATES_ZERO(stabilization_rate_sum_err);

        if (PERIODIC_TELEMETRY) {
//            register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_RATE_LOOP, send_rate);
//            register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_RATE_LOOP, new send_rate());
            throw new IllegalStateException("Unimplemented");
        }
    }


    void stabilization_rate_read_rc() {

        if (ROLL_RATE_DEADBAND_EXCEEDED()) {
            stabilization_rate_sp.p = RadioControl.values[RADIO_ROLL] * RATE_BFP_OF_REAL(STABILIZATION_RATE_SP_MAX_P) / MAX_PPRZ;
        } else {
            stabilization_rate_sp.p = 0;
        }

        if (PITCH_RATE_DEADBAND_EXCEEDED()) {
            stabilization_rate_sp.q = RadioControl.values[RADIO_PITCH] * RATE_BFP_OF_REAL(STABILIZATION_RATE_SP_MAX_Q) / MAX_PPRZ;
        } else {
            stabilization_rate_sp.q = 0;
        }

        if (YAW_RATE_DEADBAND_EXCEEDED() && !THROTTLE_STICK_DOWN()) {
            stabilization_rate_sp.r = RadioControl.values[RADIO_YAW] * RATE_BFP_OF_REAL(STABILIZATION_RATE_SP_MAX_R) / MAX_PPRZ;
        } else {
            stabilization_rate_sp.r = 0;
        }
    }

    //Read rc with roll and yaw sitcks switched if the default orientation is vertical but airplane sticks are desired
    void stabilization_rate_read_rc_switched_sticks() {

        if (ROLL_RATE_DEADBAND_EXCEEDED()) {
            stabilization_rate_sp.r = -RadioControl.values[RADIO_ROLL] * RATE_BFP_OF_REAL(STABILIZATION_RATE_SP_MAX_P) / MAX_PPRZ;
        } else {
            stabilization_rate_sp.r = 0;
        }

        if (PITCH_RATE_DEADBAND_EXCEEDED()) {
            stabilization_rate_sp.q = RadioControl.values[RADIO_PITCH] * RATE_BFP_OF_REAL(STABILIZATION_RATE_SP_MAX_Q) / MAX_PPRZ;
        } else {
            stabilization_rate_sp.q = 0;
        }

        if (YAW_RATE_DEADBAND_EXCEEDED() && !THROTTLE_STICK_DOWN()) {
            stabilization_rate_sp.p = RadioControl.values[RADIO_YAW] * RATE_BFP_OF_REAL(STABILIZATION_RATE_SP_MAX_R) / MAX_PPRZ;
        } else {
            stabilization_rate_sp.p = 0;
        }
    }

    public static void stabilization_rate_enter() {
        INT_RATES_ZERO(stabilization_rate_sum_err);
    }

    void stabilization_rate_run(boolean in_flight) {
  /* compute feed-back command */
        Rates<Integer> _error = Rates.newInteger();
        Rates<Integer> body_rate = stateGetBodyRates_i();
        RATES_DIFF(_error, stabilization_rate_sp, (body_rate));
        if (in_flight) {
    /* update integrator */
            RATES_ADD(stabilization_rate_sum_err, _error);
            RATES_BOUND_CUBE(stabilization_rate_sum_err, -MAX_SUM_ERR, MAX_SUM_ERR);
        } else {
            INT_RATES_ZERO(stabilization_rate_sum_err);
        }

  /* PI */
        stabilization_rate_fb_cmd.p = stabilization_rate_gain.p * _error.p +
                OFFSET_AND_ROUND2((stabilization_rate_igain.p * stabilization_rate_sum_err.p), 10);

        stabilization_rate_fb_cmd.q = stabilization_rate_gain.q * _error.q +
                OFFSET_AND_ROUND2((stabilization_rate_igain.q * stabilization_rate_sum_err.q), 10);

        stabilization_rate_fb_cmd.r = stabilization_rate_gain.r * _error.r +
                OFFSET_AND_ROUND2((stabilization_rate_igain.r * stabilization_rate_sum_err.r), 10);

        stabilization_cmd[COMMAND_ROLL] = stabilization_rate_fb_cmd.p >> 11;
        stabilization_cmd[COMMAND_PITCH] = stabilization_rate_fb_cmd.q >> 11;
        stabilization_cmd[COMMAND_YAW] = stabilization_rate_fb_cmd.r >> 11;

  /* bound the result */
        BoundAbs(stabilization_cmd[COMMAND_ROLL], MAX_PPRZ);
        BoundAbs(stabilization_cmd[COMMAND_PITCH], MAX_PPRZ);
        BoundAbs(stabilization_cmd[COMMAND_YAW], MAX_PPRZ);

    }

    //    TODO throtlestick down doesnt seem to be in the imports for lisaM2 therefore stopgap
    private static boolean THROTTLE_STICK_DOWN() {
        return false;
    }
}

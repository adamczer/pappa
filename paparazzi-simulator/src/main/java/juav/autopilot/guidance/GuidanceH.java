package juav.autopilot.guidance;

import juav.autopilot.guidance.structures.HorizantalGuidance;
import juav.autopilot.guidance.structures.HorizontalGuidanceSetpoint;
import juav.autopilot.stabilization.Stabilization;
import juav.autopilot.telemetry.callbacks.SendGh;
import juav.autopilot.telemetry.callbacks.SendHoverLoop;
import juav.autopilot.telemetry.callbacks.SendHref;
import juav.autopilot.telemetry.callbacks.SendTuneHover;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Vect2;

import static juav.autopilot.commands.Commands.*;
import static juav.autopilot.guidance.GuidanceV.get_vertical_thrust_coeff;
import static juav.autopilot.guidance.ref.GuidanceHRef.*;
import static juav.autopilot.messages.Messages.*;
import static juav.autopilot.navigation.Navigation.*;
import static juav.autopilot.radiocontrol.RadioControl.RC_OK;
import static juav.autopilot.radiocontrol.RadioControl.radio_control;
import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.*;
import static juav.autopilot.stabilization.StabilizationAttitudeRcSetpoint.stabilization_attitude_read_rc_setpoint_eulers;
import static juav.autopilot.state.State.*;
import static juav.autopilot.telemetry.Telemetry.DefaultPeriodic;
import static juav.autopilot.telemetry.Telemetry.register_periodic_telemetry;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.*;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.*;
import static ub.juav.airborne.math.functions.geodetic.PprzGeodeticInt.INT32_VECT2_NED_OF_ENU;
import static ub.juav.airborne.math.util.UtilityFunctions.*;

/**
 * Created by adamczer on 6/9/16.
 */
public class GuidanceH {
    public static final boolean PERIODIC_TELEMETRY = true;
    public static final double GUIDANCE_H_MAX_BANK = 0.34906585;
    private int transition_percentage = 0;
    private int transition_theta_offset = 0;
    private static HorizantalGuidance guidance_h;
    private Vect2<Integer> guidance_h_cmd_earth;
    private Vect2<Integer> guidance_h_pos_err;
    private Vect2<Integer> guidance_h_speed_err;
    private static Vect2<Integer> guidance_h_trim_att_integrator;
    private int MAX_POS_ERR = PprzAlgebraInt.POS_BFP_OF_REAL(16.);
    private int MAX_SPEED_ERR = PprzAlgebraInt.POS_BFP_OF_REAL(16.);
    private static final int GH_GAIN_SCALE = 2;
    public static final int MAX_PPRZ = 9600;
    public static final int MIN_PPRZ = - MAX_PPRZ;
    private static final int GUIDANCE_H_THRUST_CMD_FILTER = 10;
    private int thrust_cmd_filt;
    private Stabilization stabilization_cmd;

    private static final short GUIDANCE_H_MODE_KILL = 0;
    private static final short GUIDANCE_H_MODE_RATE     =   1;
    public static final short GUIDANCE_H_MODE_ATTITUDE =   2;
    private static final short GUIDANCE_H_MODE_HOVER    =   3;
    public static final short GUIDANCE_H_MODE_NAV      =   4;
    private static final short GUIDANCE_H_MODE_RC_DIRECT=   5;
    private static final short GUIDANCE_H_MODE_CARE_FREE=   6;
    private static final short GUIDANCE_H_MODE_FORWARD  =   7;
    private static final short GUIDANCE_H_MODE_MODULE   =   8;
    private static final short GUIDANCE_H_MODE_FLIP     =   9;
    private static final short GUIDANCE_H_MODE_GUIDED   =   10;
    private boolean GUIDANCE_H_USE_REF = false;
    private boolean GUIDANCE_H_APPROX_FORCE_BY_THRUST = false;
    private int GUIDANCE_H_PGAIN = 50;
    private int  GUIDANCE_H_DGAIN =100;
    private int  GUIDANCE_H_AGAIN =70;
    private int  GUIDANCE_H_IGAIN =20;
    private int GUIDANCE_H_VGAIN = 0;

    public static GuidanceH guidanceH;

    public static void guidance_h_init() {
        guidanceH = new GuidanceH();
        guidanceH.init();
    }

    public void init() {
        guidance_h_trim_att_integrator = Vect2.newIntVect2();

        thrust_cmd_filt = 0;
        guidance_h = new HorizantalGuidance();
        guidance_h.mode = GUIDANCE_H_MODE_KILL;
        guidance_h.use_ref = GUIDANCE_H_USE_REF;
        guidance_h.approx_force_by_thrust = GUIDANCE_H_APPROX_FORCE_BY_THRUST;
        INT_VECT2_ZERO(guidance_h.sp.pos);
        INT_VECT2_ZERO(guidance_h_trim_att_integrator);
        INT_EULERS_ZERO(guidance_h.rc_sp);
        guidance_h.sp.heading = 0;
        guidance_h.gains.p = GUIDANCE_H_PGAIN;
        guidance_h.gains.i = GUIDANCE_H_IGAIN;
        guidance_h.gains.d = GUIDANCE_H_DGAIN;
        guidance_h.gains.a = GUIDANCE_H_AGAIN;
        guidance_h.gains.v = GUIDANCE_H_VGAIN;
        transition_percentage = 0;
        transition_theta_offset = 0;

        gh_ref_init();

        //TODO telem init still needs to be jni?
        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_GUIDANCE_H_INT, new SendGh());
        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_HOVER_LOOP, new SendHoverLoop());
        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_GUIDANCE_H_REF_INT, new SendHref());
        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_ROTORCRAFT_TUNE_HOVER, new SendTuneHover());
    }

    public static void stabilizationAttitudeRun(boolean inFlight) {
        stabilization_attitude_run(inFlight);
    }

    public void guidance_h_traj_run(boolean inFlight)
    {
  /* maximum bank angle: default 20 deg, max 40 deg*/
        int traj_max_bank = Math.min(BFP_OF_REAL(GUIDANCE_H_MAX_BANK, INT32_ANGLE_FRAC),
            BFP_OF_REAL(RadOfDeg(40), INT32_ANGLE_FRAC));
        int total_max_bank = BFP_OF_REAL(RadOfDeg(45), INT32_ANGLE_FRAC);

  /* compute position error    */
        VECT2_DIFF(guidance_h_pos_err, guidance_h.ref.pos, stateGetPositionNed_i());
  /* saturate it               */
        VECT2_STRIM(guidance_h_pos_err, -MAX_POS_ERR, MAX_POS_ERR);

  /* compute speed error    */
        VECT2_DIFF(guidance_h_speed_err, guidance_h.ref.speed, stateGetSpeedNed_i());
  /* saturate it               */
        VECT2_STRIM(guidance_h_speed_err, -MAX_SPEED_ERR, MAX_SPEED_ERR);

  /* run PID */
        int pd_x =
                ((guidance_h.gains.p * guidance_h_pos_err.x) >> (INT32_POS_FRAC - GH_GAIN_SCALE)) +
                        ((guidance_h.gains.d * (guidance_h_speed_err.x >> 2)) >> (INT32_SPEED_FRAC - GH_GAIN_SCALE - 2));
        int pd_y =
                ((guidance_h.gains.p * guidance_h_pos_err.y) >> (INT32_POS_FRAC - GH_GAIN_SCALE)) +
                        ((guidance_h.gains.d * (guidance_h_speed_err.y >> 2)) >> (INT32_SPEED_FRAC - GH_GAIN_SCALE - 2));
        guidance_h_cmd_earth.x = pd_x +
                ((guidance_h.gains.v * guidance_h.ref.speed.x) >> 17) + /* speed feedforward gain */
                ((guidance_h.gains.a * guidance_h.ref.accel.x) >> 8);   /* acceleration feedforward gain */
        guidance_h_cmd_earth.y = pd_y +
                ((guidance_h.gains.v * guidance_h.ref.speed.y) >> 17) + /* speed feedforward gain */
                ((guidance_h.gains.a * guidance_h.ref.accel.y) >> 8);   /* acceleration feedforward gain */

  /* trim max bank angle from PD */
        VECT2_STRIM(guidance_h_cmd_earth, -traj_max_bank, traj_max_bank);

  /* Update pos & speed error integral, zero it if not inFlight.
   * Integrate twice as fast when not only POS but also SPEED are wrong,
   * but do not integrate POS errors when the SPEED is already catching up.
   */
        if (inFlight) {
    /* ANGLE_FRAC (12) * GAIN (8) * LOOP_FREQ (9) -> INTEGRATOR HIGH RES ANGLE_FRAX (28) */
            guidance_h_trim_att_integrator.x += (guidance_h.gains.i * pd_x);
            guidance_h_trim_att_integrator.y += (guidance_h.gains.i * pd_y);
    /* saturate it  */
            VECT2_STRIM(guidance_h_trim_att_integrator, -(traj_max_bank << 16), (traj_max_bank << 16));
    /* add it to the command */
            guidance_h_cmd_earth.x += (guidance_h_trim_att_integrator.x >> 16);
            guidance_h_cmd_earth.y += (guidance_h_trim_att_integrator.y >> 16);
        } else {
            INT_VECT2_ZERO(guidance_h_trim_att_integrator);
        }

  /* compute a better approximation of force commands by taking thrust into account */
        if (guidance_h.approx_force_by_thrust && inFlight) {
//            TODO int vertical_thrust = (stabilization_cmd.stabilization_cmd[COMMAND_THRUST] * guidance_v_thrust_coeff) >> INT32_TRIG_FRAC;
            int vertical_thrust = (stabilization_cmd.stabilization_cmd[COMMAND_THRUST] * get_vertical_thrust_coeff()) >> INT32_TRIG_FRAC;
            thrust_cmd_filt = (thrust_cmd_filt * GUIDANCE_H_THRUST_CMD_FILTER + vertical_thrust) /
                    (GUIDANCE_H_THRUST_CMD_FILTER + 1);
            guidance_h_cmd_earth.x = ANGLE_BFP_OF_REAL(Math.atan2((guidance_h_cmd_earth.x * MAX_PPRZ / INT32_ANGLE_PI_2),
                    thrust_cmd_filt));
            guidance_h_cmd_earth.y = ANGLE_BFP_OF_REAL(Math.atan2((guidance_h_cmd_earth.y * MAX_PPRZ / INT32_ANGLE_PI_2),
                    thrust_cmd_filt));
        }

        VECT2_STRIM(guidance_h_cmd_earth, -total_max_bank, total_max_bank);
    }

    //new

    static void reset_guidance_reference_from_current_position()
    {
        VECT2_COPY(guidance_h.ref.pos, stateGetPositionNed_i());
        VECT2_COPY(guidance_h.ref.speed, stateGetSpeedNed_i());
        INT_VECT2_ZERO(guidance_h.ref.accel);
        gh_ref.gh_set_ref(guidance_h.ref.pos, guidance_h.ref.speed, guidance_h.ref.accel);
        INT_VECT2_ZERO(guidance_h_trim_att_integrator);
    }

    public void guidance_h_mode_changed(short new_mode)
    {
        if (new_mode == guidance_h.mode) {
            return;
        }

        if (new_mode != GUIDANCE_H_MODE_FORWARD && new_mode != GUIDANCE_H_MODE_RATE) {
            transition_percentage = 0;
            transition_theta_offset = 0;
        }

        switch (new_mode) {
            case GUIDANCE_H_MODE_ATTITUDE:
                stabilization_attitude_enter();
                break;
            case GUIDANCE_H_MODE_NAV:
                guidance_h_nav_enter();
                stabilization_attitude_enter();
                break;
            default:
                break;
        }

        guidance_h.mode = new_mode;

    }


    public void guidance_h_read_rc(boolean in_flight)
    {

        switch (guidance_h.mode) {
            case GUIDANCE_H_MODE_ATTITUDE: //TODO
                stabilization_attitude_read_rc(in_flight, false, false);
            break;
            case GUIDANCE_H_MODE_NAV: //TODO
                if (radio_control.status == RC_OK) {
                    stabilization_attitude_read_rc_setpoint_eulers(guidance_h.rc_sp, in_flight, false, false);
                } else {
                    INT_EULERS_ZERO(guidance_h.rc_sp);
                }
                break;
            default:
                break;
        }

    }

    public void guidance_h_run(boolean  in_flight)
    {
        switch (guidance_h.mode) {
            case GUIDANCE_H_MODE_ATTITUDE:
                stabilization_attitude_run(in_flight);
                break;
            case GUIDANCE_H_MODE_NAV:
                if (!in_flight) {
                    guidance_h_nav_enter();
                }

                if (horizontal_mode == HORIZONTAL_MODE_ATTITUDE) {
                    Eulers<Integer> sp_cmd_i = Eulers.newInteger();
                    sp_cmd_i.phi = nav_roll;
                    sp_cmd_i.theta = nav_pitch;
                    sp_cmd_i.psi = nav_heading;
                    stabilization_attitude_set_rpy_setpoint_i(sp_cmd_i);
                } else {
                    INT32_VECT2_NED_OF_ENU(guidance_h.sp.pos, navigation_carrot);

                    guidance_h_update_reference();

        /* set psi command */
                    guidance_h.sp.heading = nav_heading;
                    INT32_ANGLE_NORMALIZE(guidance_h.sp.heading);
        /* compute x,y earth commands */
                    guidance_h_traj_run(in_flight);
        /* set final attitude setpoint */
                    stabilization_attitude_set_earth_cmd_i(guidance_h_cmd_earth,
                            guidance_h.sp.heading);
                }
                stabilization_attitude_run(in_flight);
                break;

            default:
                break;
        }
    }


    public static void guidance_h_update_reference()
    {
  /* compute reference even if usage temporarily disabled via guidance_h_use_ref */
//        #if GUIDANCE_H_USE_REF
//        #if GUIDANCE_H_USE_SPEED_REF
        if (guidance_h.mode == GUIDANCE_H_MODE_HOVER) {
            gh_update_ref_from_speed_sp(guidance_h.sp.speed);
        } else
//        #endif
        gh_update_ref_from_pos_sp(guidance_h.sp.pos);
//        #endif

  /* either use the reference or simply copy the pos setpoint */
        if (guidance_h.use_ref) {
    /* convert our reference to generic representation */
            INT32_VECT2_RSHIFT_L(guidance_h.ref.pos,   gh_ref.pos, (GH_POS_REF_FRAC - INT32_POS_FRAC));
            INT32_VECT2_LSHIFT(guidance_h.ref.speed, gh_ref.speed, (INT32_SPEED_FRAC - GH_SPEED_REF_FRAC));
            INT32_VECT2_LSHIFT(guidance_h.ref.accel, gh_ref.accel, (INT32_ACCEL_FRAC - GH_ACCEL_REF_FRAC));
        } else {
            VECT2_COPY(guidance_h.ref.pos, guidance_h.sp.pos);
            INT_VECT2_ZERO(guidance_h.ref.speed);
            INT_VECT2_ZERO(guidance_h.ref.accel);
        }

//        #if GUIDANCE_H_USE_SPEED_REF
        if (guidance_h.mode == GUIDANCE_H_MODE_HOVER) {
            VECT2_COPY(guidance_h.sp.pos, guidance_h.ref.pos); // for display only
        }
//        #endif
    }

    static void guidance_h_nav_enter()
    {
  /* horizontal position setpoint from navigation/flightplan */
        INT32_VECT2_NED_OF_ENU(guidance_h.sp.pos, navigation_carrot);

        reset_guidance_reference_from_current_position();

        nav_heading = stateGetNedToBodyEulers_i().psi;
    }
}

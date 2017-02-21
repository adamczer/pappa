package juav.autopilot.stabilization;

import juav.autopilot.stabilization.attitude.AttitudeGains;
import juav.autopilot.stabilization.attitude.AttitudeRef;
import juav.autopilot.stabilization.attitude.StabilizationCommand;
import juav.autopilot.state.State;
import juav.autopilot.telemetry.Telemetry;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.Rates;
import ub.juav.airborne.math.structs.algebra.Vect2;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import static juav.autopilot.guidance.GuidanceH.MAX_PPRZ;
import static juav.autopilot.stabilization.StabilizationAttitudeQuatTransformations.quat_from_earth_cmd_i;
import static juav.autopilot.stabilization.StabilizationAttitudeRcSetpoint.stabilization_attitude_get_heading_i;
import static juav.autopilot.stabilization.StabilizationAttitudeRcSetpoint.stabilization_attitude_read_rc_setpoint_quat_f;
import static juav.autopilot.stabilization.StabilizationAttitudeRefQuatInt.attitude_ref_quat_int_enter;
import static juav.autopilot.stabilization.StabilizationAttitudeRefQuatInt.attitude_ref_quat_int_init;
import static juav.autopilot.stabilization.StabilizationAttitudeRefQuatInt.attitude_ref_quat_int_update;
import static juav.autopilot.stabilization.StabilizationRate.OFFSET_AND_ROUND;
import static juav.autopilot.state.State.stateGetNedToBodyEulers_i;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.QUAT_BFP_OF_REAL;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.RATES_DIFF;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.*;
import static ub.juav.airborne.math.functions.trig.PprzTrig.PPRZ_ITRIG_COS;
import static ub.juav.airborne.math.functions.trig.PprzTrig.PPRZ_ITRIG_SIN;

/**
 * Created by adamczer on 6/10/16.
 */
public class StabilizationAttitudeQuatInt {

    public static final float PERIODIC_FREQUENCY = 512;
    public static final int REF_RATE_FRAC = 16;
    public static final int REF_ACCEL_FRAC = 12;
//    public static final int REF_ANGLE_FRAC = 20;
    private static final int INT32_RATE_FRAC = 12;
    private static final int IERROR_SCALE = 128;

    private static final int GAIN_PRESCALER_FF = 48;
    private static final int GAIN_PRESCALER_P = 12;
    private static final int GAIN_PRESCALER_D = 3;
    private static final int GAIN_PRESCALER_I = 3;

    private static boolean logTimeMetrics = false;


    public static Eulers<Integer> stab_att_sp_euler = Eulers.newInteger();
public static Eulers<Integer> getStabilizationAttSpEuler() {
//    Eulers<Integer> ret = Eulers.newInteger();
//    ret.setPhi(NativeTasksWrapper.getStabilizationAttSpEulerPhi());
//    ret.setPsi(NativeTasksWrapper.getStabilizationAttSpEulerPsi());
//    ret.setTheta(NativeTasksWrapper.getStabilizationAttSpEulerTheta());
//    return ret;
    return stab_att_sp_euler;
}
    public static void setStabilizationAttSpEuler(Eulers<Integer> newSpEulers) {
//        NativeTasksWrapper.setStabilizationAttSpEulerPhi(newSpEulers.phi);
//        NativeTasksWrapper.setStabilizationAttSpEulerPsi(newSpEulers.psi);
//        NativeTasksWrapper.setStabilizationAttSpEulerTheta(newSpEulers.theta);
        stab_att_sp_euler = newSpEulers;
    }
//    public static Quat<Integer> stab_att_sp_quat = Quat.newInteger();
    public static Quat<Integer> getStabilizationAttSpQuat() {
        Quat<Integer> ret = Quat.newInteger();
        ret.setQi(NativeTasksWrapper.getStabilizationAttSpQuatQi());
        ret.setQx(NativeTasksWrapper.getStabilizationAttSpQuatQx());
        ret.setQy(NativeTasksWrapper.getStabilizationAttSpQuatQy());
        ret.setQz(NativeTasksWrapper.getStabilizationAttSpQuatQz());
        return ret;
    }
    public static void setStabilizationAttSpQuat(Quat<Integer> newStabAttSpQuat) {
        NativeTasksWrapper.setStabilizationAttSpQuatQi(newStabAttSpQuat.qi);
        NativeTasksWrapper.setStabilizationAttSpQuatQx(newStabAttSpQuat.qx);
        NativeTasksWrapper.setStabilizationAttSpQuatQy(newStabAttSpQuat.qy);
        NativeTasksWrapper.setStabilizationAttSpQuatQz(newStabAttSpQuat.qz);
    }
    public static AttitudeRef<Integer> att_ref_quat_i = AttitudeRef.newInteger();
    public static Quat<Integer> stabilization_att_sum_err_quat = Quat.newInteger();

    public static void stabilization_attitude_init()
    {
        attitude_ref_quat_int_init(att_ref_quat_i);

        int32_quat_identity(stabilization_att_sum_err_quat);

        //TODO handel telem
//        throw new IllegalStateException("Implement telemetry");
//        #if PERIODIC_TELEMETRY
//        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_STAB_ATTITUDE_INT, send_att);
        Telemetry.registerPeriodicTelemetrySendAtt();
//        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_STAB_ATTITUDE_REF_INT, send_att_ref);
        Telemetry.registerPeriodicTelemetrySendAttRef();
//        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_AHRS_REF_QUAT, send_ahrs_ref_quat);
        Telemetry.registerPeriodicTelemetrySendAhrsRefQuat();
//        #endif
    }

    public static void stabilization_attitude_enter() {
          /* reset psi setpoint to current psi angle */
        Eulers<Integer> stab_att_sp_euler = getStabilizationAttSpEuler();
        stab_att_sp_euler.psi = stabilization_attitude_get_heading_i();

        attitude_ref_quat_int_enter(att_ref_quat_i, stab_att_sp_euler.psi);

        int32_quat_identity(stabilization_att_sum_err_quat);
//        System.out.println("stab_att_sp_euler psi,psi,theta= "+
//                stab_att_sp_euler.psi+", "+
//                stab_att_sp_euler.phi+", "+
//                stab_att_sp_euler.theta);
    }

    public static void stabilization_attitude_run(boolean enable_integrator) {
//        NativeTasksWrapper.juavStabilizationAttitudeRunNative(enable_integrator);
        /*
   * Update reference
   * Warning: dt is currently not used in the quat_int ref impl
   * PERIODIC_FREQUENCY is assumed to be 512Hz
   */
        float dt = (1.f/PERIODIC_FREQUENCY);
        Quat<Integer> stab_att_sp_quat = getStabilizationAttSpQuat();
        AttitudeRef<Integer> att_ref_quat_i = AttitudeRef.getIntegerFromJni();
        attitude_ref_quat_int_update(att_ref_quat_i, stab_att_sp_quat, dt);

//        System.out.println("stab_att_sp_quat qi,qx,qy,qz = "+
//                stab_att_sp_quat.qi+","+
//                stab_att_sp_quat.qx+","+
//                stab_att_sp_quat.qy+","+
//                stab_att_sp_quat.qz);
        setStabilizationAttSpQuat(stab_att_sp_quat);

  /*
   * Compute errors for feedback
   */

  /* attitude error                          */
        Quat<Integer> att_quat = State.getNedToBodyQuatI();
//        System.out.println(att_quat);
        Quat<Integer> att_err = Quat.newInteger();
        PprzAlgebraInt.int32_quat_inv_comp(att_err, att_quat, att_ref_quat_i.getQuat());
//        System.out.println("att_err = "+ att_err);
  /* wrap it in the shortest direction       */
        int32_quat_wrap_shortest(att_err);
        int32_quat_normalize(att_err);

  /*  rate error                */
        Rates<Integer> rate_ref_scaled = Rates.newInteger(
                OFFSET_AND_ROUND(att_ref_quat_i.rate.p, (REF_RATE_FRAC - INT32_RATE_FRAC)),
                OFFSET_AND_ROUND(att_ref_quat_i.rate.q, (REF_RATE_FRAC - INT32_RATE_FRAC)),
                OFFSET_AND_ROUND(att_ref_quat_i.rate.r, (REF_RATE_FRAC - INT32_RATE_FRAC))
        );
        Rates<Integer> rate_err = Rates.newInteger();
        Rates<Integer> body_rate = State.stateGetBodyRates_i();
        RATES_DIFF(rate_err, rate_ref_scaled, (body_rate));

        int INTEGRATOR_BOUND = 100000;
  /* integrated error */
        if (enable_integrator) {
            stabilization_att_sum_err_quat.qx += att_err.qx / IERROR_SCALE;
            stabilization_att_sum_err_quat.qy += att_err.qy / IERROR_SCALE;
            stabilization_att_sum_err_quat.qz += att_err.qz / IERROR_SCALE;
            Bound(stabilization_att_sum_err_quat.qx, -INTEGRATOR_BOUND, INTEGRATOR_BOUND);
            Bound(stabilization_att_sum_err_quat.qy, -INTEGRATOR_BOUND, INTEGRATOR_BOUND);
            Bound(stabilization_att_sum_err_quat.qz, -INTEGRATOR_BOUND, INTEGRATOR_BOUND);
        } else {
    /* reset accumulator */
            int32_quat_identity(stabilization_att_sum_err_quat);
        }

  /* compute the feed forward command */
        StabilizationCommand<Integer> stabilization_att_ff_cmd = StabilizationCommand.newInteger();
        AttitudeGains<Integer> stabilization_gains = AttitudeGains.getIntegerFromJni();
        attitude_run_ff(stabilization_att_ff_cmd, stabilization_gains, att_ref_quat_i.accel);
//        System.out.println("Stabilization Cmd roll,pitch,yaw = "+stabilization_att_ff_cmd.getRoll()+","+stabilization_att_ff_cmd.getPitch()+", "+stabilization_att_ff_cmd.getYaw());
//        System.out.println("att_ref_quat_i.accel p,q,r = "+att_ref_quat_i.accel.p+","+att_ref_quat_i.accel.q+","+att_ref_quat_i.accel.r);
  /* compute the feed back command */
        StabilizationCommand<Integer> stabilization_att_fb_cmd = StabilizationCommand.newInteger();
        attitude_run_fb(stabilization_att_fb_cmd, stabilization_gains, att_err, rate_err, stabilization_att_sum_err_quat);

        StabilizationCommand<Integer> stabilization_cmd = StabilizationCommand.newInteger();
  /* sum feedforward and feedback */
        stabilization_cmd.setRoll(stabilization_att_fb_cmd.getRoll() + stabilization_att_ff_cmd.getRoll());
        stabilization_cmd.setPitch(stabilization_att_fb_cmd.getPitch() + stabilization_att_ff_cmd.getPitch());
        stabilization_cmd.setYaw(stabilization_att_fb_cmd.getYaw() + stabilization_att_ff_cmd.getYaw());

  /* bound the result */
        stabilization_cmd.setRoll(BoundAbs(stabilization_cmd.getRoll(), MAX_PPRZ));
        stabilization_cmd.setPitch(BoundAbs(stabilization_cmd.getPitch(), MAX_PPRZ));
        stabilization_cmd.setYaw(BoundAbs(stabilization_cmd.getYaw(), MAX_PPRZ));
        sendResultsBack(stabilization_att_sum_err_quat,att_ref_quat_i,stabilization_cmd);
    }

    private static int iterCount = 0;


    private static Quat<Integer> getStabilizationAttSumErrQuatFromJni() {
        Quat<Integer> ret = Quat.newInteger();
        ret.setQi(NativeTasksWrapper.getStabilizationAttSumErrQuatQi());
        ret.setQx(NativeTasksWrapper.getStabilizationAttSumErrQuatQx());
        ret.setQy(NativeTasksWrapper.getStabilizationAttSumErrQuatQy());
        ret.setQz(NativeTasksWrapper.getStabilizationAttSumErrQuatQz());
        return ret;
    }

    private static void attitude_run_fb(StabilizationCommand<Integer> fb_commands, AttitudeGains<Integer> gains, Quat<Integer> att_err, Rates<Integer> rate_err, Quat<Integer> sum_err) {
        fb_commands.setRoll(
                (int) (GAIN_PRESCALER_P * gains.getP().getX() * PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(att_err.getQx()) +
                                        GAIN_PRESCALER_D * gains.getD().getX() * PprzAlgebraInt.RATE_FLOAT_OF_BFP(rate_err.getP()) +
                                                GAIN_PRESCALER_I * gains.getI().getX() * PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(sum_err.getQx()))
        );
        fb_commands.setPitch(
                (int) (GAIN_PRESCALER_P * gains.getP().getY() * PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(att_err.getQy()) +
                                        GAIN_PRESCALER_D * gains.getD().getY() * PprzAlgebraInt.RATE_FLOAT_OF_BFP(rate_err.getQ()) +
                                                GAIN_PRESCALER_I * gains.getI().getY() * PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(sum_err.getQy()))
        );
        fb_commands.setYaw(
                (int) (GAIN_PRESCALER_P * gains.getP().getZ() * PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(att_err.getQz()) +
                                        GAIN_PRESCALER_D * gains.getD().getZ() * PprzAlgebraInt.RATE_FLOAT_OF_BFP(rate_err.getR()) +
                                                GAIN_PRESCALER_I * gains.getI().getZ() * PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(sum_err.getQz()))
        );
//        System.out.println("fc_commands yaw, pitch, roll = "+
//        fb_commands.getYaw()+","+
//        fb_commands.getPitch()+","+
//        fb_commands.getRoll());
    }

    private static void attitude_run_ff(StabilizationCommand<Integer> stabilization_att_ff_cmd, AttitudeGains<Integer> gains, Rates<Integer> ref_accel) {
          /* Compute feedforward based on reference acceleration */
//        System.out.println("gains->dd.x = "+gains.getDd().x);
//        System.out.println("gains->dd.y = "+gains.getDd().y);
//        System.out.println("gains->dd.z = "+gains.getDd().z);
//        System.out.println("ref_accel.p = "+ref_accel.p);
//        System.out.println("ref_accel.q = "+ref_accel.q);
//        System.out.println("ref_accel.r = "+ref_accel.r);
        stabilization_att_ff_cmd.setRoll((int) (GAIN_PRESCALER_FF * gains.getDd().getX() * PprzAlgebraInt.RATE_FLOAT_OF_BFP(ref_accel.getP()) / (1 << 7)));
        stabilization_att_ff_cmd.setPitch((int) (GAIN_PRESCALER_FF * gains.getDd().getY() * PprzAlgebraInt.RATE_FLOAT_OF_BFP(ref_accel.getQ()) / (1 << 7)));
        stabilization_att_ff_cmd.setYaw((int) (GAIN_PRESCALER_FF * gains.getDd().getZ() * PprzAlgebraInt.RATE_FLOAT_OF_BFP(ref_accel.getR()) / (1 << 7)));
    }

    public static void stabilization_attitude_read_rc(boolean in_flight, boolean in_carefree, boolean coordinated_turn)
    {
//  printf("stabilization_attitude_read_rc\n");
        Quat<Float> q_sp = Quat.newFloat();
//        #if USE_EARTH_BOUND_RC_SETPOINT
//        stabilization_attitude_read_rc_setpoint_quat_earth_bound_f(&q_sp, in_flight, in_carefree, coordinated_turn);
//        #else
        stabilization_attitude_read_rc_setpoint_quat_f(q_sp, in_flight, in_carefree, coordinated_turn);
//        #endif
        Quat<Integer> stab_att_sp_quat = getStabilizationAttSpQuat();
        QUAT_BFP_OF_REAL(stab_att_sp_quat, q_sp);
        setStabilizationAttSpQuat(stab_att_sp_quat);
    }

    public static void setStabilizationAttitudeSetRpySetpointINative(Eulers<Integer> rpy) {
        NativeTasksWrapper.setStabilizationAttitudeSetRpySetpointI(rpy.psi,rpy.phi,rpy.theta);
    }

    public static void stabilization_attitude_set_rpy_setpoint_i(Eulers<Integer> rpy)//TODO PORT
    {
//        setStabilizationAttitudeSetRpySetpointINative(rpy);

//  printf("stabilization_attitude_set_rpy_setpoint_i\n");
        // stab_att_sp_euler.psi still used in ref..
        setStabilizationAttSpEuler(rpy);

        Quat<Integer> stab_att_sp_quat = getStabilizationAttSpQuat();
        int32_quat_of_eulers(stab_att_sp_quat, getStabilizationAttSpEuler());
        setStabilizationAttSpQuat(stab_att_sp_quat);
    }

    public static void stabilization_attitude_set_earth_cmd_i(Vect2<Integer> cmd, int heading)
    {
//  printf("stabilization_attitude_set_earth_cmd_i\n");
        // stab_att_sp_euler.psi still used in ref..
        Eulers<Integer> stab_att_sp_euler = Eulers.newInteger();
        stab_att_sp_euler.psi = heading;

        // compute sp_euler phi/theta for debugging/telemetry
  /* Rotate horizontal commands to body frame by psi */
        int psi = stateGetNedToBodyEulers_i().psi;
        int s_psi=0, c_psi=0;
        s_psi = PPRZ_ITRIG_SIN(s_psi, psi);
        c_psi = PPRZ_ITRIG_COS(c_psi, psi);
        stab_att_sp_euler.phi = (-s_psi * cmd.x + c_psi * cmd.y) >> INT32_TRIG_FRAC;
        stab_att_sp_euler.theta = -(c_psi * cmd.x + s_psi * cmd.y) >> INT32_TRIG_FRAC;
        setStabilizationAttSpEuler(stab_att_sp_euler);
//        System.out.println("JJJ stab_att_sp_euler psi,phi,theta = "+ stab_att_sp_euler.psi+","+stab_att_sp_euler.phi+","+stab_att_sp_euler.theta);

        Quat<Integer> stab_att_sp_quat = getStabilizationAttSpQuat();
        quat_from_earth_cmd_i(stab_att_sp_quat, cmd, heading);
//        System.out.println("JJJ stab_att_sp_quat qi,qx,qy,qz = "+stab_att_sp_quat.qi+","+stab_att_sp_quat.qx+","+stab_att_sp_quat.qy+","+stab_att_sp_quat.qz);

        setStabilizationAttSpQuat(stab_att_sp_quat);
    }


    ///// communication

    private static void sendResultsBack(Quat<Integer> stabilization_att_sum_err_quat, AttitudeRef<Integer> att_ref_quat_i, StabilizationCommand<Integer> stabilization_cmd) {
        //sum error quat
        NativeTasksWrapper.setStabilizationAttSumErrQuatQi(stabilization_att_sum_err_quat.getQi());
        NativeTasksWrapper.setStabilizationAttSumErrQuatQx(stabilization_att_sum_err_quat.getQx());
        NativeTasksWrapper.setStabilizationAttSumErrQuatQy(stabilization_att_sum_err_quat.getQy());
        NativeTasksWrapper.setStabilizationAttSumErrQuatQz(stabilization_att_sum_err_quat.getQz());
//        att_ref_quat_i quat
        NativeTasksWrapper.setAttRefQuatIQuatQi(att_ref_quat_i.getQuat().getQi());
        NativeTasksWrapper.setAttRefQuatIQuatQx(att_ref_quat_i.getQuat().getQx());
        NativeTasksWrapper.setAttRefQuatIQuatQy(att_ref_quat_i.getQuat().getQy());
        NativeTasksWrapper.setAttRefQuatIQuatQz(att_ref_quat_i.getQuat().getQz());
        //att_ref_quat_i rate
        NativeTasksWrapper.setAttRefQuatIRateP(att_ref_quat_i.getRate().getP());
        NativeTasksWrapper.setAttRefQuatIRateQ(att_ref_quat_i.getRate().getQ());
        NativeTasksWrapper.setAttRefQuatIRateR(att_ref_quat_i.getRate().getR());
        //att_ref_quat_i accel
        NativeTasksWrapper.setAttRefQuatIAccelP(att_ref_quat_i.getAccel().getP());
        NativeTasksWrapper.setAttRefQuatIAccelQ(att_ref_quat_i.getAccel().getQ());
        NativeTasksWrapper.setAttRefQuatIAccelR(att_ref_quat_i.getAccel().getR());

        //stablization command
        NativeTasksWrapper.setStabilizationCommands(
                stabilization_cmd.getYaw(),
                stabilization_cmd.getPitch(),
                stabilization_cmd.getRoll()
        );

    }
}

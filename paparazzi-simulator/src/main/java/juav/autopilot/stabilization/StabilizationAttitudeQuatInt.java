package juav.autopilot.stabilization;

import juav.autopilot.stabilization.attitude.AttitudeGains;
import juav.autopilot.stabilization.attitude.AttitudeRef;
import juav.autopilot.stabilization.attitude.StabilizationCommand;
import juav.autopilot.state.State;
import juav.autopilot.telemetry.Telemetry;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.Rates;
import ub.juav.airborne.math.structs.algebra.Vect2;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;

import static juav.autopilot.commands.Commands.COMMAND_PITCH;
import static juav.autopilot.commands.Commands.COMMAND_ROLL;
import static juav.autopilot.commands.Commands.COMMAND_YAW;
import static juav.autopilot.guidance.GuidanceH.MAX_PPRZ;
import static juav.autopilot.stabilization.StabilizationAttitudeQuatTransformations.quat_from_earth_cmd_i;
import static juav.autopilot.stabilization.StabilizationAttitudeRcSetpoint.*;
import static juav.autopilot.stabilization.StabilizationAttitudeRefQuatInt.attitude_ref_quat_int_enter;
import static juav.autopilot.stabilization.StabilizationAttitudeRefQuatInt.attitude_ref_quat_int_init;
import static juav.autopilot.stabilization.StabilizationAttitudeRefQuatInt.attitude_ref_quat_int_update;
import static juav.autopilot.stabilization.StabilizationRate.OFFSET_AND_ROUND;
import static juav.autopilot.state.State.stateGetNedToBodyEulers_i;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.QUAT_BFP_OF_REAL;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.RATES_DIFF;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.*;
import static ub.juav.airborne.math.functions.trig.PprzTrig.*;

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
    private static FileWriter totalTimeLog;
    private static FileWriter jniTimeLog;

    static {
        if(logTimeMetrics)
        try {
            totalTimeLog = new FileWriter("juav-stabilization.data");
            jniTimeLog = new FileWriter("juav-stabilization-jni-only.data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Eulers<Integer> stab_att_sp_euler = Eulers.newInteger();
//    public static Quat<Integer> stab_att_sp_quat = Quat.newInteger();
    public static Quat<Integer> getStabilizationAttSpQuat() {
        Quat<Integer> ret = Quat.newInteger();
        ret.setQi(NativeTasks.getStabilizationAttSpQuatQi());
        ret.setQx(NativeTasks.getStabilizationAttSpQuatQx());
        ret.setQy(NativeTasks.getStabilizationAttSpQuatQy());
        ret.setQz(NativeTasks.getStabilizationAttSpQuatQz());
        return ret;
    }
    public static void setStabilizationAttSpQuat(Quat<Integer> newStabAttSpQuat) {
        NativeTasks.setStabilizationAttSpQuatQi(newStabAttSpQuat.qi);
        NativeTasks.setStabilizationAttSpQuatQx(newStabAttSpQuat.qx);
        NativeTasks.setStabilizationAttSpQuatQy(newStabAttSpQuat.qy);
        NativeTasks.setStabilizationAttSpQuatQz(newStabAttSpQuat.qz);
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
        stab_att_sp_euler.psi = stabilization_attitude_get_heading_i();

        attitude_ref_quat_int_enter(att_ref_quat_i, stab_att_sp_euler.psi);

        int32_quat_identity(stabilization_att_sum_err_quat);
    }

    public static void stabilization_attitude_run(boolean enable_integrator) {
        /*
   * Update reference
   * Warning: dt is currently not used in the quat_int ref impl
   * PERIODIC_FREQUENCY is assumed to be 512Hz
   */
        float dt = (1.f/PERIODIC_FREQUENCY);
        Quat<Integer> stab_att_sp_quat = getStabilizationAttSpQuat();
        attitude_ref_quat_int_update(att_ref_quat_i, stab_att_sp_quat, dt);
        setStabilizationAttSpQuat(stab_att_sp_quat);

  /*
   * Compute errors for feedback
   */

  /* attitude error                          */
        Quat<Integer> att_quat = State.getNedToBodyQuatI();
        System.out.println(att_quat);
        Quat<Integer> att_err = Quat.newInteger();
        PprzAlgebraInt.int32_quat_inv_comp(att_err, att_quat, att_ref_quat_i.quat);
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
        System.out.println("Stabilization Cmd roll,pitch,yaw = "+stabilization_att_ff_cmd.getRoll()+","+stabilization_att_ff_cmd.getPitch()+", "+stabilization_att_ff_cmd.getYaw());
        System.out.println("att_ref_quat_i.accel p,q,r = "+att_ref_quat_i.accel.p+","+att_ref_quat_i.accel.q+","+att_ref_quat_i.accel.r);
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
//        System.out.println("Stabilization Cmd roll,pitch,yaw = "+stabilization_cmd.getRoll()+","+stabilization_cmd.getPitch()+", "+stabilization_cmd.getYaw());
        sendResultsBack(stabilization_att_sum_err_quat,att_ref_quat_i,stabilization_cmd);
    }
//    public static void stabilization_attitude_run_old(boolean enable_integrator) {
////  printf("stabilization_attitude_run\n");
//          /*
//   * Update reference
//   * Warning: dt is currently not used in the quat_int ref impl
//   * PERIODIC_FREQUENCY is assumed to be 512Hz
//   */
//        float dt = (1.f / PERIODIC_FREQUENCY);
//        //TODO below over jni do more if necessary
////        StabilizationAttitudeRefQuatInt.attitude_ref_quat_int_update(&att_ref_quat_i, &stab_att_sp_quat, dt);
//          attitude_ref_quat_int_update(att_ref_quat_i, stab_att_sp_quat, dt);
////        NativeTasks.attitudeRefQuatIntUpdateJuav(dt);
//
//        long start = 0;
//        long jniGetValuesFinish = 0;
//        if(logTimeMetrics) {
//            start = System.nanoTime();
//        }
//
//
//        Quat<Integer> att_quat = Quat.newInteger();
//        att_quat.setQi(NativeTasks.stateGetNedToBodyQuatIQi());
//        att_quat.setQx(NativeTasks.stateGetNedToBodyQuatIQx());
//        att_quat.setQy(NativeTasks.stateGetNedToBodyQuatIQy());
//        att_quat.setQz(NativeTasks.stateGetNedToBodyQuatIQz());
//
//        Rates<Integer> body_rate = Rates.newInteger();
//        body_rate.setP(NativeTasks.stateGetBodyRatesIP());
//        body_rate.setQ(NativeTasks.stateGetBodyRatesIQ());
//        body_rate.setR(NativeTasks.stateGetBodyRatesIR());
//        //Get required inputs from jni
//        Quat<Integer> stabilization_att_sum_err_quat = getStabilizationAttSumErrQuatFromJni();
//        AttitudeRef<Integer> att_ref_quat_i = AttitudeRef.getIntegerFromJni();
//        AttitudeGains<Integer> stabilization_gains = AttitudeGains.getIntegerFromJni();
//        if(logTimeMetrics) {
//            jniGetValuesFinish = System.nanoTime();
//        }
//  /*
//   * Compute errors for feedback
//   */
//
//  /* attitude error                          */
//        Quat<Integer> att_err = Quat.newInteger();
//        PprzAlgebraInt.int32_quat_inv_comp(att_err, att_quat, att_ref_quat_i.getQuat());
//  /* wrap it in the shortest direction       */
//        PprzAlgebraInt.int32_quat_wrap_shortest(att_err);
//        PprzAlgebraInt.int32_quat_normalize(att_err);
//
//  /*  rate error                */
//        Rates<Integer> rate_ref_scaled = Rates.newInteger();
//        int b = (REF_RATE_FRAC - INT32_RATE_FRAC);
//        rate_ref_scaled.setP((att_ref_quat_i.getRate().getP() + (1 << (b - 1))) >> b);
//        rate_ref_scaled.setQ((att_ref_quat_i.getRate().getQ() + (1 << (b - 1))) >> b);
//        rate_ref_scaled.setR((att_ref_quat_i.getRate().getR() + (1 << (b - 1))) >> b);
//        Rates<Integer> rate_err = Rates.newInteger();
//
//        RATES_DIFF(rate_err, rate_ref_scaled, body_rate);
//
//        int INTEGRATOR_BOUND = 100000;
//  /* integrated error */
//        if (enable_integrator) {
//            stabilization_att_sum_err_quat.setQx(stabilization_att_sum_err_quat.getQx() + att_err.getQx() / IERROR_SCALE);
//            stabilization_att_sum_err_quat.setQy(stabilization_att_sum_err_quat.getQy() + att_err.getQy() / IERROR_SCALE);
//            stabilization_att_sum_err_quat.setQz(stabilization_att_sum_err_quat.getQz() + att_err.getQz() / IERROR_SCALE);
//            stabilization_att_sum_err_quat.setQx(Bound(stabilization_att_sum_err_quat.getQx(), -INTEGRATOR_BOUND, INTEGRATOR_BOUND));
//            stabilization_att_sum_err_quat.setQy(Bound(stabilization_att_sum_err_quat.getQy(), -INTEGRATOR_BOUND, INTEGRATOR_BOUND));
//            stabilization_att_sum_err_quat.setQz(Bound(stabilization_att_sum_err_quat.getQz(), -INTEGRATOR_BOUND, INTEGRATOR_BOUND));
//        } else {
//    /* reset accumulator */
//            int32_quat_identity(stabilization_att_sum_err_quat);
//        }
//
//        StabilizationCommand<Integer> stabilization_att_ff_cmd = StabilizationCommand.newInteger();
//  /* compute the feed forward command */
//        attitude_run_ff(stabilization_att_ff_cmd, stabilization_gains, att_ref_quat_i.getAccel());
//
//        StabilizationCommand<Integer> stabilization_att_fb_cmd = StabilizationCommand.newInteger();
//  /* compute the feed back command */
//        attitude_run_fb(stabilization_att_fb_cmd, stabilization_gains, att_err, rate_err, stabilization_att_sum_err_quat);
//
//        StabilizationCommand<Integer> stabilization_cmd = StabilizationCommand.newInteger();
//  /* sum feedforward and feedback */
//        stabilization_cmd.setRoll(stabilization_att_fb_cmd.getRoll() + stabilization_att_ff_cmd.getRoll());
//        stabilization_cmd.setPitch(stabilization_att_fb_cmd.getPitch() + stabilization_att_ff_cmd.getPitch());
//        stabilization_cmd.setYaw(stabilization_att_fb_cmd.getYaw() + stabilization_att_ff_cmd.getYaw());
//
//  /* bound the result */
//        stabilization_cmd.setRoll(BoundAbs(stabilization_cmd.getRoll(), MAX_PPRZ));
//        stabilization_cmd.setPitch(BoundAbs(stabilization_cmd.getPitch(), MAX_PPRZ));
//        stabilization_cmd.setYaw(BoundAbs(stabilization_cmd.getYaw(), MAX_PPRZ));
//
//        long jniStartSendValues = 0;
//        long jniFinishSendValues = 0;
//        if(logTimeMetrics)
//            jniStartSendValues = System.nanoTime();
//        sendResultsBack(stabilization_att_sum_err_quat,att_ref_quat_i,stabilization_cmd);
//        if(logTimeMetrics)
//            jniFinishSendValues = System.nanoTime();
//
//        if(logTimeMetrics) {
//            long finish = System.nanoTime();
//            long elapsed = finish - start;
//            long timestamp  = ManagementFactory.getRuntimeMXBean().getUptime();
//            try {
//                iterCount++;
//                totalTimeLog.write(""+iterCount);
//                totalTimeLog.write(" " +timestamp );
//                totalTimeLog.write(" "+elapsed+"\n");
//                totalTimeLog.flush();
//
//                long jniTimeInIter = (jniGetValuesFinish-start)+(jniFinishSendValues-jniStartSendValues);
//                jniTimeLog.write(""+iterCount);
//                jniTimeLog.write(" " +timestamp );
//                jniTimeLog.write(" "+jniTimeInIter+"\n");
//                jniTimeLog.flush();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private static int iterCount = 0;


    private static Quat<Integer> getStabilizationAttSumErrQuatFromJni() {
        Quat<Integer> ret = Quat.newInteger();
        ret.setQi(NativeTasks.getStabilizationAttSumErrQuatQi());
        ret.setQx(NativeTasks.getStabilizationAttSumErrQuatQx());
        ret.setQy(NativeTasks.getStabilizationAttSumErrQuatQy());
        ret.setQz(NativeTasks.getStabilizationAttSumErrQuatQz());
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
    }

    private static void attitude_run_ff(StabilizationCommand<Integer> stabilization_att_ff_cmd, AttitudeGains<Integer> gains, Rates<Integer> ref_accel) {
          /* Compute feedforward based on reference acceleration */
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

    public static void stabilization_attitude_set_rpy_setpoint_i(Eulers<Integer> rpy)//TODO PORT
    {
//  printf("stabilization_attitude_set_rpy_setpoint_i\n");
        // stab_att_sp_euler.psi still used in ref..
        stab_att_sp_euler = rpy;

        Quat<Integer> stab_att_sp_quat = getStabilizationAttSpQuat();
        int32_quat_of_eulers(stab_att_sp_quat, stab_att_sp_euler);
        setStabilizationAttSpQuat(stab_att_sp_quat);
    }

    public static void stabilization_attitude_set_earth_cmd_i(Vect2<Integer> cmd, int heading)
    {
//  printf("stabilization_attitude_set_earth_cmd_i\n");
        // stab_att_sp_euler.psi still used in ref..
        stab_att_sp_euler.psi = heading;

        // compute sp_euler phi/theta for debugging/telemetry
  /* Rotate horizontal commands to body frame by psi */
        int psi = stateGetNedToBodyEulers_i().psi;
        int s_psi=0, c_psi=0;
        s_psi = PPRZ_ITRIG_SIN(s_psi, psi);
        c_psi = PPRZ_ITRIG_COS(c_psi, psi);
        stab_att_sp_euler.phi = (-s_psi * cmd.x + c_psi * cmd.y) >> INT32_TRIG_FRAC;
        stab_att_sp_euler.theta = -(c_psi * cmd.x + s_psi * cmd.y) >> INT32_TRIG_FRAC;

        Quat<Integer> stab_att_sp_quat = getStabilizationAttSpQuat();
        quat_from_earth_cmd_i(stab_att_sp_quat, cmd, heading);
        setStabilizationAttSpQuat(stab_att_sp_quat);
    }


    ///// communication

    private static void sendResultsBack(Quat<Integer> stabilization_att_sum_err_quat, AttitudeRef<Integer> att_ref_quat_i, StabilizationCommand<Integer> stabilization_cmd) {
        //sum error quat
//        NativeTasks.setStabilizationAttSumErrQuatQi(stabilization_att_sum_err_quat.getQi());
//        NativeTasks.setStabilizationAttSumErrQuatQx(stabilization_att_sum_err_quat.getQx());
//        NativeTasks.setStabilizationAttSumErrQuatQy(stabilization_att_sum_err_quat.getQy());
//        NativeTasks.setStabilizationAttSumErrQuatQz(stabilization_att_sum_err_quat.getQz());
        //att_ref_quat_i quat
//        NativeTasks.setAttRefQuatIQuatQi(att_ref_quat_i.getQuat().getQi());
//        NativeTasks.setAttRefQuatIQuatQx(att_ref_quat_i.getQuat().getQx());
//        NativeTasks.setAttRefQuatIQuatQy(att_ref_quat_i.getQuat().getQy());
//        NativeTasks.setAttRefQuatIQuatQz(att_ref_quat_i.getQuat().getQz());
//        //att_ref_quat_i rate
//        NativeTasks.setAttRefQuatIRateP(att_ref_quat_i.getRate().getP());
//        NativeTasks.setAttRefQuatIRateQ(att_ref_quat_i.getRate().getQ());
//        NativeTasks.setAttRefQuatIRateR(att_ref_quat_i.getRate().getR());
//        //att_ref_quat_i accel
//        NativeTasks.setAttRefQuatIAccelP(att_ref_quat_i.getAccel().getP());
//        NativeTasks.setAttRefQuatIAccelQ(att_ref_quat_i.getAccel().getQ());
//        NativeTasks.setAttRefQuatIAccelR(att_ref_quat_i.getAccel().getR());

        //stablization command
        NativeTasks.setStabilizationCommands(
                stabilization_cmd.getYaw(),
                stabilization_cmd.getPitch(),
                stabilization_cmd.getRoll()
        );

    }
}

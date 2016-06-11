package juav.autopilot.stabilization;

import juav.autopilot.stabilization.attitude.AttitudeGains;
import juav.autopilot.stabilization.attitude.AttitudeRef;
import juav.autopilot.stabilization.attitude.StabilizationCommand;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.Rates;

/**
 * Created by adamczer on 6/10/16.
 */
public class StabilizationAttitudeQuatInt {
    private static final int MAX_PPRZ = 9600;
    private static final float PERIODIC_FREQUENCY = 512;
    private static final int REF_RATE_FRAC = 16;
    private static final int INT32_RATE_FRAC = 12;
    private static final int IERROR_SCALE = 128;

    private static final int GAIN_PRESCALER_FF = 48;
    private static final int GAIN_PRESCALER_P = 12;
    private static final int GAIN_PRESCALER_D = 3;
    private static final int GAIN_PRESCALER_I = 3;

    public static void stabilization_attitude_run(boolean enable_integrator) {
//  printf("stabilization_attitude_run\n");
          /*
   * Update reference
   * Warning: dt is currently not used in the quat_int ref impl
   * PERIODIC_FREQUENCY is assumed to be 512Hz
   */
        float dt = (1.f / PERIODIC_FREQUENCY);
        //TODO below over jni do more if necessary
//        StabilizationAttitudeRefQuatInt.attitude_ref_quat_int_update(&att_ref_quat_i, &stab_att_sp_quat, dt);
        NativeTasks.attitudeRefQuatIntUpdateJuav(dt);

        //Get required inputs from jni
        Quat<Integer> stabilization_att_sum_err_quat = getStabilizationAttSumErrQuatFromJni();
        AttitudeRef<Integer> att_ref_quat_i = AttitudeRef.getIntegerFromJni();
        AttitudeGains<Integer> stabilization_gains = AttitudeGains.getIntegerFromJni();

  /*
   * Compute errors for feedback
   */

  /* attitude error                          */
        Quat<Integer> att_err = Quat.newInteger();
        Quat<Integer> att_quat = Quat.newInteger();
        att_quat.setQi(NativeTasks.stateGetNedToBodyQuatIQi());
        att_quat.setQx(NativeTasks.stateGetNedToBodyQuatIQx());
        att_quat.setQy(NativeTasks.stateGetNedToBodyQuatIQy());
        att_quat.setQz(NativeTasks.stateGetNedToBodyQuatIQz());
        PprzAlgebraInt.int32_quat_inv_comp(att_err, att_quat, att_ref_quat_i.getQuat());
  /* wrap it in the shortest direction       */
        PprzAlgebraInt.int32_quat_wrap_shortest(att_err);
        PprzAlgebraInt.int32_quat_normalize(att_err);

  /*  rate error                */
        Rates<Integer> rate_ref_scaled = Rates.newInteger();
        int b = (REF_RATE_FRAC - INT32_RATE_FRAC);
        att_ref_quat_i.getRate().setP((att_ref_quat_i.getRate().getP() + (1 << (b - 1))) >> b);
        att_ref_quat_i.getRate().setQ((att_ref_quat_i.getRate().getQ() + (1 << (b - 1))) >> b);
        att_ref_quat_i.getRate().setR((att_ref_quat_i.getRate().getR() + (1 << (b - 1))) >> b);
        Rates<Integer> rate_err = Rates.newInteger();
        Rates<Integer> body_rate = Rates.newInteger();
        body_rate.setP(NativeTasks.stateGetBodyRatesIP());
        body_rate.setQ(NativeTasks.stateGetBodyRatesIQ());
        body_rate.setR(NativeTasks.stateGetBodyRatesIR());

        PprzAlgebra.RATES_DIFF(rate_err, rate_ref_scaled, body_rate);

        int INTEGRATOR_BOUND = 100000;
  /* integrated error */
        if (enable_integrator) {
            stabilization_att_sum_err_quat.setQx(stabilization_att_sum_err_quat.getQx() + att_err.getQx() / IERROR_SCALE);
            stabilization_att_sum_err_quat.setQy(stabilization_att_sum_err_quat.getQy() + att_err.getQy() / IERROR_SCALE);
            stabilization_att_sum_err_quat.setQz(stabilization_att_sum_err_quat.getQz() + att_err.getQz() / IERROR_SCALE);
            stabilization_att_sum_err_quat.setQx(Bound(stabilization_att_sum_err_quat.getQx(), -INTEGRATOR_BOUND, INTEGRATOR_BOUND));
            stabilization_att_sum_err_quat.setQy(Bound(stabilization_att_sum_err_quat.getQy(), -INTEGRATOR_BOUND, INTEGRATOR_BOUND));
            stabilization_att_sum_err_quat.setQz(Bound(stabilization_att_sum_err_quat.getQz(), -INTEGRATOR_BOUND, INTEGRATOR_BOUND));
        } else {
    /* reset accumulator */
            PprzAlgebraInt.int32_quat_identity(stabilization_att_sum_err_quat);
        }

        StabilizationCommand<Integer> stabilization_att_ff_cmd = StabilizationCommand.newInteger();
  /* compute the feed forward command */
        attitude_run_ff(stabilization_att_ff_cmd, stabilization_gains, att_ref_quat_i.getAccel());

        StabilizationCommand<Integer> stabilization_att_fb_cmd = StabilizationCommand.newInteger();
  /* compute the feed back command */
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

    private static void sendResultsBack(Quat<Integer> stabilization_att_sum_err_quat, AttitudeRef<Integer> att_ref_quat_i, StabilizationCommand<Integer> stabilization_cmd) {
        //sum error quat
        NativeTasks.setStabilizationAttSumErrQuatQi(stabilization_att_sum_err_quat.getQi());
        NativeTasks.setStabilizationAttSumErrQuatQx(stabilization_att_sum_err_quat.getQx());
        NativeTasks.setStabilizationAttSumErrQuatQy(stabilization_att_sum_err_quat.getQy());
        NativeTasks.setStabilizationAttSumErrQuatQz(stabilization_att_sum_err_quat.getQz());
        //att_ref_quat_i quat
        NativeTasks.setAttRefQuatIQuatQi(att_ref_quat_i.getQuat().getQi());
        NativeTasks.setAttRefQuatIQuatQx(att_ref_quat_i.getQuat().getQx());
        NativeTasks.setAttRefQuatIQuatQy(att_ref_quat_i.getQuat().getQy());
        NativeTasks.setAttRefQuatIQuatQz(att_ref_quat_i.getQuat().getQz());
        //att_ref_quat_i rate
        NativeTasks.setAttRefQuatIRateP(att_ref_quat_i.getRate().getP());
        NativeTasks.setAttRefQuatIRateQ(att_ref_quat_i.getRate().getQ());
        NativeTasks.setAttRefQuatIRateR(att_ref_quat_i.getRate().getR());
        //att_ref_quat_i accel
        NativeTasks.setAttRefQuatIAccelP(att_ref_quat_i.getAccel().getP());
        NativeTasks.setAttRefQuatIAccelQ(att_ref_quat_i.getAccel().getQ());
        NativeTasks.setAttRefQuatIAccelR(att_ref_quat_i.getAccel().getR());

        //stablization command
        NativeTasks.setStabilizationCommands(
                stabilization_cmd.getYaw(),
                stabilization_cmd.getPitch(),
                stabilization_cmd.getRoll()
        );

    }

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


    private static int Bound(int input, int min, int max) {
        if (input > max) {
            input = max;
        } else if (input < min) {
            input = min;
        }
        return input;
    }

    private static int BoundAbs(int input, int bound) {
        if (bound >= 0)
            return Bound(input, -bound, bound);
        else
            return Bound(input, bound, -bound);
    }
}

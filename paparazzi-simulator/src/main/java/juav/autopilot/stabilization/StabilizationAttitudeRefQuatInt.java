package juav.autopilot.stabilization;

import juav.autopilot.stabilization.attitude.AttitudeRef;
import ub.juav.airborne.math.structs.algebra.Rates;

import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.int32_quat_of_eulers;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.int32_quat_wrap_shortest;

/**
 * Created by adamczer on 11/27/16.
 */
public class StabilizationAttitudeRefQuatInt {

    public static void attitude_ref_quat_int_enter(AttitudeRef<Integer> ref, int psi)
    {
        reset_psi_ref(ref, psi);

        int32_quat_of_eulers(ref.quat, ref.euler);
        int32_quat_wrap_shortest(ref.quat);

  /* set reference rate and acceleration to zero */
//        memset(&ref->accel, 0, sizeof(struct Int32Rates));
        ref.accel = Rates.newInteger();
//        memset(&ref->rate, 0, sizeof(struct Int32Rates));
        ref.rate = Rates.newInteger();
    }

    static void reset_psi_ref(AttitudeRef<Integer> ref, int psi)
    {
        ref.euler.psi = psi;
        ref.rate.r = 0;
        ref.accel.r = 0;
    }
}

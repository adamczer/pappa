package juav.autopilot.stabilization;

import juav.autopilot.stabilization.attitude.AttitudeRef;
import ub.juav.airborne.math.structs.algebra.Rates;

import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.REF_ACCEL_FRAC;
import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.REF_RATE_FRAC;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.INT_EULERS_ZERO;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.*;
import static ub.juav.airborne.math.util.UtilityFunctions.RadOfDeg;

/**
 * Created by adamczer on 11/27/16.
 */
public class StabilizationAttitudeRefQuatInt {
    public static final float STABILIZATION_ATTITUDE_REF_OMEGA_P =6.981317f;
    public static final float STABILIZATION_ATTITUDE_REF_OMEGA_Q =6.981317f;
    public static final float STABILIZATION_ATTITUDE_REF_OMEGA_R =4.363323125f;

    public static final float STABILIZATION_ATTITUDE_REF_ZETA_P =0.85f;
    public static final float STABILIZATION_ATTITUDE_REF_ZETA_Q =0.85f;
    public static final float STABILIZATION_ATTITUDE_REF_ZETA_R =0.85f;

    public static final float STABILIZATION_ATTITUDE_REF_MAX_P =6.981317f;
    public static final float STABILIZATION_ATTITUDE_REF_MAX_Q =6.981317f;
    public static final float STABILIZATION_ATTITUDE_REF_MAX_R =3.14159265f;

    public static final float STABILIZATION_ATTITUDE_REF_MAX_PDOT = (float) RadOfDeg(8000.);
    public static final float STABILIZATION_ATTITUDE_REF_MAX_QDOT = (float) RadOfDeg(8000.);
    public static final float STABILIZATION_ATTITUDE_REF_MAX_RDOT = (float) RadOfDeg(1800.);

    public static final int TWO_ZETA_OMEGA_RES = 10;
    public static final int TWO_OMEGA_2_RES = 7;

    public static void attitude_ref_quat_int_init(AttitudeRef<Integer> ref)
    {
        INT_EULERS_ZERO(ref.euler);
        int32_quat_identity(ref.quat);
        INT_RATES_ZERO(ref.rate);
        INT_RATES_ZERO(ref.accel);

        attitude_ref_quat_int_set_max_p(ref, STABILIZATION_ATTITUDE_REF_MAX_P);
        attitude_ref_quat_int_set_max_q(ref, STABILIZATION_ATTITUDE_REF_MAX_Q);
        attitude_ref_quat_int_set_max_r(ref, STABILIZATION_ATTITUDE_REF_MAX_R);
        attitude_ref_quat_int_set_max_pdot(ref, STABILIZATION_ATTITUDE_REF_MAX_PDOT);
        attitude_ref_quat_int_set_max_qdot(ref, STABILIZATION_ATTITUDE_REF_MAX_QDOT);
        attitude_ref_quat_int_set_max_rdot(ref, STABILIZATION_ATTITUDE_REF_MAX_RDOT);

        Rates<Float> omega0 = Rates.newFloat(STABILIZATION_ATTITUDE_REF_OMEGA_P,
            STABILIZATION_ATTITUDE_REF_OMEGA_Q,
            STABILIZATION_ATTITUDE_REF_OMEGA_R);
        Rates<Float> zeta0 = Rates.newFloat(STABILIZATION_ATTITUDE_REF_ZETA_P,
            STABILIZATION_ATTITUDE_REF_ZETA_Q,
            STABILIZATION_ATTITUDE_REF_ZETA_R);

        attitude_ref_quat_int_set_omega(ref, omega0);
        attitude_ref_quat_int_set_zeta(ref, zeta0);

  /* calc the intermediate cached values */
        update_ref_model(ref);
    }

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

    static void attitude_ref_quat_int_set_max_p(AttitudeRef<Integer> ref, float max_p)
    {
        ref.saturation.max_rate.p = BFP_OF_REAL(max_p, REF_RATE_FRAC);
    }

    static void attitude_ref_quat_int_set_max_q(AttitudeRef<Integer> ref, float max_q)
    {
        ref.saturation.max_rate.q = BFP_OF_REAL(max_q, REF_RATE_FRAC);
    }

    static void attitude_ref_quat_int_set_max_r(AttitudeRef<Integer> ref, float max_r)
    {
        ref.saturation.max_rate.r = BFP_OF_REAL(max_r, REF_RATE_FRAC);
    }

    static void attitude_ref_quat_int_set_max_pdot(AttitudeRef<Integer> ref, float max_pdot)
    {
        ref.saturation.max_accel.p = BFP_OF_REAL(max_pdot, REF_ACCEL_FRAC);
    }

    static void attitude_ref_quat_int_set_max_qdot(AttitudeRef<Integer> ref, float max_qdot)
    {
        ref.saturation.max_accel.q = BFP_OF_REAL(max_qdot, REF_ACCEL_FRAC);
    }

    static void attitude_ref_quat_int_set_max_rdot(AttitudeRef<Integer> ref, float max_rdot)
    {
        ref.saturation.max_accel.r = BFP_OF_REAL(max_rdot, REF_ACCEL_FRAC);
    }

    static void attitude_ref_quat_int_set_omega(AttitudeRef<Integer> ref, Rates<Float> omega)
    {
        attitude_ref_quat_int_set_omega_p(ref, omega.p);
        attitude_ref_quat_int_set_omega_q(ref, omega.q);
        attitude_ref_quat_int_set_omega_r(ref, omega.r);
    }

    /*
 * Setting handlers for changing the ref model parameters.
 *
 */
    static void attitude_ref_quat_int_set_omega_p(AttitudeRef<Integer> ref, float omega_p)
    {
        ref.model.omega.p = omega_p;
        update_ref_model_p(ref);
    }

    static void attitude_ref_quat_int_set_omega_q(AttitudeRef<Integer> ref, float omega_q)
    {
        ref.model.omega.q = omega_q;
        update_ref_model_q(ref);
    }

    static void attitude_ref_quat_int_set_omega_r(AttitudeRef<Integer> ref, float omega_r)
    {
        ref.model.omega.r = omega_r;
        update_ref_model_r(ref);
    }

    /*
 * Recomputation of cached values.
 *
 */
    static void update_ref_model_p(AttitudeRef<Integer> ref)
    {
        ref.model.two_zeta_omega.p = BFP_OF_REAL((2 * ref.model.zeta.p * ref.model.omega.p), TWO_ZETA_OMEGA_RES);
        ref.model.two_omega2.p = BFP_OF_REAL((2 * ref.model.omega.p * ref.model.omega.p), TWO_OMEGA_2_RES);
    }

    static void update_ref_model_q(AttitudeRef<Integer> ref)
    {
        ref.model.two_zeta_omega.q = BFP_OF_REAL((2 * ref.model.zeta.q * ref.model.omega.q), TWO_ZETA_OMEGA_RES);
        ref.model.two_omega2.q = BFP_OF_REAL((2 * ref.model.omega.q * ref.model.omega.q), TWO_OMEGA_2_RES);
    }

    static void update_ref_model_r(AttitudeRef<Integer> ref)
    {
        ref.model.two_zeta_omega.r = BFP_OF_REAL((2 * ref.model.zeta.r * ref.model.omega.r), TWO_ZETA_OMEGA_RES);
        ref.model.two_omega2.r = BFP_OF_REAL((2 * ref.model.omega.r * ref.model.omega.r), TWO_OMEGA_2_RES);
    }

    static void update_ref_model(AttitudeRef<Integer> ref)
    {
        update_ref_model_p(ref);
        update_ref_model_q(ref);
        update_ref_model_r(ref);
    }

    static void attitude_ref_quat_int_set_zeta(AttitudeRef<Integer> ref, Rates<Float> zeta)
    {
        attitude_ref_quat_int_set_zeta_p(ref, zeta.p);
        attitude_ref_quat_int_set_zeta_q(ref, zeta.q);
        attitude_ref_quat_int_set_zeta_r(ref, zeta.r);
    }

    static void attitude_ref_quat_int_set_zeta_p(AttitudeRef<Integer> ref, float zeta_p)
    {
        ref.model.zeta.p = zeta_p;
        update_ref_model_p(ref);
    }

    static void attitude_ref_quat_int_set_zeta_q(AttitudeRef<Integer> ref, float zeta_q)
    {
        ref.model.zeta.q = zeta_q;
        update_ref_model_q(ref);
    }

    static void attitude_ref_quat_int_set_zeta_r(AttitudeRef<Integer> ref, float zeta_r)
    {
        ref.model.zeta.r = zeta_r;
        update_ref_model_r(ref);
    }
}

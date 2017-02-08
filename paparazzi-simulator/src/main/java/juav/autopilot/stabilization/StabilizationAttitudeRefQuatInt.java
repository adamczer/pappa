package juav.autopilot.stabilization;

import juav.autopilot.stabilization.attitude.AttitudeRef;
import juav.autopilot.stabilization.attitude.RefSat;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.Rates;
import ub.juav.airborne.math.wrappers.PrimitiveWrapper;

import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.REF_ACCEL_FRAC;
import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.REF_RATE_FRAC;
import static juav.autopilot.stabilization.StabilizationRate.OFFSET_AND_ROUND;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.*;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.*;
import static ub.juav.airborne.math.util.UtilityFunctions.RadOfDeg;

/**
 * Created by adamczer on 11/27/16.
 */
public class StabilizationAttitudeRefQuatInt {
    public static final int F_UPDATE_RES = 9;

    public static final float STABILIZATION_ATTITUDE_REF_OMEGA_P = 6.981317f;
    public static final float STABILIZATION_ATTITUDE_REF_OMEGA_Q = 6.981317f;
    public static final float STABILIZATION_ATTITUDE_REF_OMEGA_R = 4.363323125f;

    public static final float STABILIZATION_ATTITUDE_REF_ZETA_P = 0.85f;
    public static final float STABILIZATION_ATTITUDE_REF_ZETA_Q = 0.85f;
    public static final float STABILIZATION_ATTITUDE_REF_ZETA_R = 0.85f;

    public static final float STABILIZATION_ATTITUDE_REF_MAX_P = 6.981317f;
    public static final float STABILIZATION_ATTITUDE_REF_MAX_Q = 6.981317f;
    public static final float STABILIZATION_ATTITUDE_REF_MAX_R = 3.14159265f;

    public static final float STABILIZATION_ATTITUDE_REF_MAX_PDOT = (float) RadOfDeg(8000.);
    public static final float STABILIZATION_ATTITUDE_REF_MAX_QDOT = (float) RadOfDeg(8000.);
    public static final float STABILIZATION_ATTITUDE_REF_MAX_RDOT = (float) RadOfDeg(1800.);

    public static final int TWO_ZETA_OMEGA_RES = 10;
    public static final int TWO_OMEGA_2_RES = 7;

    public static void attitude_ref_quat_int_init(AttitudeRef<Integer> ref) {
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

    public static void attitude_ref_quat_int_enter(AttitudeRef<Integer> ref, int psi) {
        reset_psi_ref(ref, psi);

        int32_quat_of_eulers(ref.quat, ref.euler);
        int32_quat_wrap_shortest(ref.quat);

  /* set reference rate and acceleration to zero */
//        memset(&ref->accel, 0, sizeof(struct Int32Rates));
        ref.accel = Rates.newInteger();
//        memset(&ref->rate, 0, sizeof(struct Int32Rates));
        ref.rate = Rates.newInteger();
    }

    static void reset_psi_ref(AttitudeRef<Integer> ref, int psi) {
        ref.euler.psi = psi;
        ref.rate.r = 0;
        ref.accel.r = 0;
    }

    static void attitude_ref_quat_int_set_max_p(AttitudeRef<Integer> ref, float max_p) {
        ref.saturation.max_rate.p = BFP_OF_REAL(max_p, REF_RATE_FRAC);
    }

    static void attitude_ref_quat_int_set_max_q(AttitudeRef<Integer> ref, float max_q) {
        ref.saturation.max_rate.q = BFP_OF_REAL(max_q, REF_RATE_FRAC);
    }

    static void attitude_ref_quat_int_set_max_r(AttitudeRef<Integer> ref, float max_r) {
        ref.saturation.max_rate.r = BFP_OF_REAL(max_r, REF_RATE_FRAC);
    }

    static void attitude_ref_quat_int_set_max_pdot(AttitudeRef<Integer> ref, float max_pdot) {
        ref.saturation.max_accel.p = BFP_OF_REAL(max_pdot, REF_ACCEL_FRAC);
    }

    static void attitude_ref_quat_int_set_max_qdot(AttitudeRef<Integer> ref, float max_qdot) {
        ref.saturation.max_accel.q = BFP_OF_REAL(max_qdot, REF_ACCEL_FRAC);
    }

    static void attitude_ref_quat_int_set_max_rdot(AttitudeRef<Integer> ref, float max_rdot) {
        ref.saturation.max_accel.r = BFP_OF_REAL(max_rdot, REF_ACCEL_FRAC);
    }

    static void attitude_ref_quat_int_set_omega(AttitudeRef<Integer> ref, Rates<Float> omega) {
        attitude_ref_quat_int_set_omega_p(ref, omega.p);
        attitude_ref_quat_int_set_omega_q(ref, omega.q);
        attitude_ref_quat_int_set_omega_r(ref, omega.r);
    }

    /*
 * Setting handlers for changing the ref model parameters.
 *
 */
    static void attitude_ref_quat_int_set_omega_p(AttitudeRef<Integer> ref, float omega_p) {
        ref.model.omega.p = omega_p;
        update_ref_model_p(ref);
    }

    static void attitude_ref_quat_int_set_omega_q(AttitudeRef<Integer> ref, float omega_q) {
        ref.model.omega.q = omega_q;
        update_ref_model_q(ref);
    }

    static void attitude_ref_quat_int_set_omega_r(AttitudeRef<Integer> ref, float omega_r) {
        ref.model.omega.r = omega_r;
        update_ref_model_r(ref);
    }

    /*
 * Recomputation of cached values.
 *
 */
    static void update_ref_model_p(AttitudeRef<Integer> ref) {
        ref.model.two_zeta_omega.p = BFP_OF_REAL((2 * ref.model.zeta.p * ref.model.omega.p), TWO_ZETA_OMEGA_RES);
        ref.model.two_omega2.p = BFP_OF_REAL((2 * ref.model.omega.p * ref.model.omega.p), TWO_OMEGA_2_RES);
    }

    static void update_ref_model_q(AttitudeRef<Integer> ref) {
        ref.model.two_zeta_omega.q = BFP_OF_REAL((2 * ref.model.zeta.q * ref.model.omega.q), TWO_ZETA_OMEGA_RES);
        ref.model.two_omega2.q = BFP_OF_REAL((2 * ref.model.omega.q * ref.model.omega.q), TWO_OMEGA_2_RES);
    }

    static void update_ref_model_r(AttitudeRef<Integer> ref) {
        ref.model.two_zeta_omega.r = BFP_OF_REAL((2 * ref.model.zeta.r * ref.model.omega.r), TWO_ZETA_OMEGA_RES);
        ref.model.two_omega2.r = BFP_OF_REAL((2 * ref.model.omega.r * ref.model.omega.r), TWO_OMEGA_2_RES);
    }

    static void update_ref_model(AttitudeRef<Integer> ref) {
        update_ref_model_p(ref);
        update_ref_model_q(ref);
        update_ref_model_r(ref);
    }

    static void attitude_ref_quat_int_set_zeta(AttitudeRef<Integer> ref, Rates<Float> zeta) {
        attitude_ref_quat_int_set_zeta_p(ref, zeta.p);
        attitude_ref_quat_int_set_zeta_q(ref, zeta.q);
        attitude_ref_quat_int_set_zeta_r(ref, zeta.r);
    }

    static void attitude_ref_quat_int_set_zeta_p(AttitudeRef<Integer> ref, float zeta_p) {
        ref.model.zeta.p = zeta_p;
        update_ref_model_p(ref);
    }

    static void attitude_ref_quat_int_set_zeta_q(AttitudeRef<Integer> ref, float zeta_q) {
        ref.model.zeta.q = zeta_q;
        update_ref_model_q(ref);
    }

    static void attitude_ref_quat_int_set_zeta_r(AttitudeRef<Integer> ref, float zeta_r) {
        ref.model.zeta.r = zeta_r;
        update_ref_model_r(ref);
    }

    static void attitude_ref_quat_int_update(AttitudeRef<Integer> ref, Quat<Integer> sp_quat,
                                             float dt) {
  /* integrate reference attitude            */
        System.out.println("ref.rate p,q,r = "+);
        Rates<Integer> rate_ref_scaled = Rates.newInteger(OFFSET_AND_ROUND(ref.rate.p, (REF_RATE_FRAC - INT32_RATE_FRAC)),
                OFFSET_AND_ROUND(ref.rate.q, (REF_RATE_FRAC - INT32_RATE_FRAC)),
                OFFSET_AND_ROUND(ref.rate.r, (REF_RATE_FRAC - INT32_RATE_FRAC)));

        Quat<Integer> qdot = Quat.newInteger();
        int32_quat_derivative(qdot, rate_ref_scaled, ref.quat);
        qdot.qi = qdot.qi >> F_UPDATE_RES;
        qdot.qx = qdot.qx >> F_UPDATE_RES;
        qdot.qy = qdot.qy >> F_UPDATE_RES;
        qdot.qz = qdot.qz >> F_UPDATE_RES;
        QUAT_ADD(ref.quat, qdot);
        int32_quat_normalize(ref.quat);

  /* integrate reference rotational speeds
   * delta rate = ref_accel * dt
   * ref_rate = old_ref_rate + delta_rate
   */
        Rates<Integer> delta_rate = Rates.newInteger(
                ref.accel.p >> (F_UPDATE_RES + REF_ACCEL_FRAC - REF_RATE_FRAC),
                ref.accel.q >> (F_UPDATE_RES + REF_ACCEL_FRAC - REF_RATE_FRAC),
                ref.accel.r >> (F_UPDATE_RES + REF_ACCEL_FRAC - REF_RATE_FRAC));
        RATES_ADD(ref.rate, delta_rate);

  /* compute reference angular accelerations */
        Quat<Integer> err = Quat.newInteger();
  /* compute reference attitude error        */
        int32_quat_inv_comp(err, sp_quat, ref.quat);
  /* wrap it in the shortest direction       */
        int32_quat_wrap_shortest(err);

  /* propagate the 2nd order linear model : accel = -2*zeta*omega * rate - omega^2 * angle  */

        Rates<Integer> accel_rate = Rates.newInteger(
                (-ref.model.two_zeta_omega.p * (ref.rate.p >> (REF_RATE_FRAC - REF_ACCEL_FRAC))) >> (TWO_ZETA_OMEGA_RES),
                (-ref.model.two_zeta_omega.q * (ref.rate.q >> (REF_RATE_FRAC - REF_ACCEL_FRAC))) >> (TWO_ZETA_OMEGA_RES),
                (-ref.model.two_zeta_omega.r * (ref.rate.r >> (REF_RATE_FRAC - REF_ACCEL_FRAC))) >> (TWO_ZETA_OMEGA_RES)
        );

  /* since error quaternion contains the half-angles we get 2*omega^2*err */
        Rates<Integer> accel_angle = Rates.newInteger(
                (-ref.model.two_omega2.p * (err.qx >> (INT32_QUAT_FRAC - REF_ACCEL_FRAC))) >> (TWO_OMEGA_2_RES),
                (-ref.model.two_omega2.q * (err.qy >> (INT32_QUAT_FRAC - REF_ACCEL_FRAC))) >> (TWO_OMEGA_2_RES),
                (-ref.model.two_omega2.r * (err.qz >> (INT32_QUAT_FRAC - REF_ACCEL_FRAC))) >> (TWO_OMEGA_2_RES)
        );

        RATES_SUM(ref.accel, accel_rate, accel_angle);


  /* saturate */
        attitude_ref_int_saturate_naive(ref.rate, ref.accel, ref.saturation);

  /* compute euler representation for debugging and telemetry */
        int32_eulers_of_quat(ref.euler, ref.quat);
    }

    static void attitude_ref_int_saturate_naive(Rates<Integer> rate,
                                                Rates<Integer> accel,
                                                RefSat<Integer> sat) {
  /* saturate angular acceleration */
        RATES_BOUND_BOX_ABS(accel, sat.max_accel);

  /* saturate angular speed and trim accel accordingly */
        PrimitiveWrapper<Integer> ratePWrapper = new PrimitiveWrapper<>(rate.p);
        PrimitiveWrapper<Integer> accelPWrapper = new PrimitiveWrapper<>(accel.p);
        SATURATE_SPEED_TRIM_ACCEL(ratePWrapper, accelPWrapper, sat.max_rate.p);
        rate.p = ratePWrapper.primitive;
        accel.p = accelPWrapper.primitive;
        PrimitiveWrapper<Integer> rateQWrapper = new PrimitiveWrapper<>(rate.q);
        PrimitiveWrapper<Integer> accelQWrapper = new PrimitiveWrapper<>(accel.q);
        SATURATE_SPEED_TRIM_ACCEL(rateQWrapper, accelQWrapper, sat.max_rate.q);
        rate.q = rateQWrapper.primitive;
        accel.q = accelQWrapper.primitive;
        PrimitiveWrapper<Integer> accelRWrapper = new PrimitiveWrapper<>(accel.r);
        PrimitiveWrapper<Integer> rateRWrapper = new PrimitiveWrapper<>(rate.r);
        SATURATE_SPEED_TRIM_ACCEL(rateRWrapper, accelRWrapper, sat.max_rate.r);
        rate.r = rateRWrapper.primitive;
        accel.r = accelRWrapper.primitive;
    }

    private static void RATES_BOUND_BOX_ABS(Rates<Integer> _v, Rates<Integer> _v_max) {
        if ((_v).p > (_v_max).p) (_v).p = (_v_max).p;
        else if ((_v).p < -(_v_max).p) (_v).p = -(_v_max).p;
        if ((_v).q > (_v_max).q) (_v).q = (_v_max).q;
        else if ((_v).q < -(_v_max).q) (_v).q = -(_v_max).q;
        if ((_v).r > (_v_max).r) (_v).r = (_v_max).r;
        else if ((_v).r < -(_v_max).r) (_v).r = -(_v_max).r;
    }

    /**
     * saturate angular speed and trim accel accordingly
     */
    static void SATURATE_SPEED_TRIM_ACCEL(PrimitiveWrapper<Integer> _rate, PrimitiveWrapper<Integer> _accel, int _max_rate) {
        if ((_rate.primitive) >= (_max_rate)) {
            (_rate.primitive) = (_max_rate);
            if ((_accel.primitive) > 0) {
                (_accel.primitive) = 0;
            }
        } else if ((_rate.primitive) <= -(_max_rate)) {
            (_rate.primitive) = -(_max_rate);
            if ((_accel.primitive) < 0) {
                (_accel.primitive) = 0;
            }
        }
    }
}

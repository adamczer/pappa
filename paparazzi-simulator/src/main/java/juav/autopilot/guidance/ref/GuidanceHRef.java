package juav.autopilot.guidance.ref;

import ub.juav.airborne.math.structs.algebra.Vect2;

import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.*;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.*;
import static ub.juav.airborne.math.functions.trig.PprzTrig.PPRZ_ITRIG_COS;
import static ub.juav.airborne.math.functions.trig.PprzTrig.PPRZ_ITRIG_SIN;
import static ub.juav.airborne.math.util.UtilityFunctions.Bound;
import static ub.juav.airborne.math.util.UtilityFunctions.RadOfDeg;

/**
 * Created by adamczer on 11/5/16.
 */
public class GuidanceHRef {
    public static GuidanceHRef gh_ref;


    public static final float GUIDANCE_H_REF_OMEGA = (float) RadOfDeg(67.);
    public static final float GUIDANCE_H_REF_ZETA = 0.85f;
    public static final float GUIDANCE_H_REF_TAU = 0.5f;
    public static final float GUIDANCE_H_REF_MAX_SPEED = 5.f;
    public static final int GH_MAX_SPEED_REF_FRAC = 7;
    public static final int GH_REF_INV_TAU_FRAC = 16;
    public static final int GH_ZETA_OMEGA_FRAC =10;
    public static final int GH_OMEGA_2_FRAC =7;
    public static final double GUIDANCE_H_REF_MAX_ACCEL = 5.66;

    public static final int GH_FREQ_FRAC = 9;
    public static final int GH_FREQ = (1<<GH_FREQ_FRAC);

    public static final int GH_ACCEL_REF_FRAC = 8;
    public static final int GH_SPEED_REF_FRAC =(GH_ACCEL_REF_FRAC + GH_FREQ_FRAC);
    public static final int GH_POS_REF_FRAC =(GH_SPEED_REF_FRAC + GH_FREQ_FRAC);
    public static final int gh_max_accel = BFP_OF_REAL(GUIDANCE_H_REF_MAX_ACCEL, GH_ACCEL_REF_FRAC);

    /** Reference model acceleration.
     * in meters/sec2 (output)
     * fixed point representation: Q23.8
     * accuracy 0.0039, range 8388km/s2
     */
    public Vect2<Integer> accel;

    /** Reference model speed.
     * in meters/sec
     * with fixedpoint representation: Q14.17
     * accuracy 0.0000076 , range 16384m/s
     */
    public Vect2<Integer> speed;

    /** Reference model position.
     * in meters
     * with fixedpoint representation: Q37.26
     */
    public Vect2<Long> pos;

    public float tau;    ///< first order time constant
    public float omega;  ///< second order model natural frequency
    public float zeta;   ///< second order model damping

    /** Current maximum speed for waypoint navigation.
     * Defaults to #GUIDANCE_H_REF_MAX_SPEED
     */
    public float max_speed;

    /*
     * internal variables
     */
    public int zeta_omega;
    public int omega_2;
    public int inv_tau;

    public Vect2<Integer> max_vel;
    public Vect2<Integer> max_accel;

    /** gh_max_speed in fixed point representation with #GH_MAX_SPEED_REF_FRAC
     * must be limited to 2^14 to avoid overflow
     */
    public int max_speed_int;

    public int route_ref;
    public int s_route_ref;
    public int c_route_ref;

    public static void gh_ref_init()
    {
        gh_ref = new GuidanceHRef();
        gh_ref.omega = GUIDANCE_H_REF_OMEGA;
        gh_ref.zeta = GUIDANCE_H_REF_ZETA;
        gh_ref.zeta_omega = BFP_OF_REAL((GUIDANCE_H_REF_ZETA * GUIDANCE_H_REF_OMEGA), GH_ZETA_OMEGA_FRAC);
        gh_ref.omega_2 = BFP_OF_REAL((GUIDANCE_H_REF_OMEGA * GUIDANCE_H_REF_OMEGA), GH_OMEGA_2_FRAC);
        gh_ref.gh_set_tau(GUIDANCE_H_REF_TAU);
        gh_ref.gh_set_max_speed(GUIDANCE_H_REF_MAX_SPEED);
        gh_ref.accel = Vect2.newIntVect2();
        gh_ref.speed = Vect2.newIntVect2();
        gh_ref.pos = Vect2.newLongVect2();
        gh_ref.max_accel = Vect2.newIntVect2();
        gh_ref.max_vel = Vect2.newIntVect2();
    }


    public float gh_set_max_speed(float max_speed)
    {
  /* limit to 100m/s as int version would overflow at  2^14 = 128 m/s */
        gh_ref.max_speed = Math.min(Math.abs(max_speed), 100.0f);
        gh_ref.max_speed_int = BFP_OF_REAL(gh_ref.max_speed, GH_MAX_SPEED_REF_FRAC);
        return gh_ref.max_speed;
    }

    public float gh_set_tau(float tau)
    {
        gh_ref.tau = tau;
        Bound(gh_ref.tau, 0.01f, 2.0f);
        gh_ref.inv_tau = BFP_OF_REAL((1. / gh_ref.tau), GH_REF_INV_TAU_FRAC);
        return gh_ref.tau;
    }

    public float gh_set_omega(float omega)
    {
        gh_ref.omega = omega;
        Bound(gh_ref.omega, 0.1f, 5.0f);
        gh_ref.omega_2 = BFP_OF_REAL((gh_ref.omega * gh_ref.omega), GH_OMEGA_2_FRAC);
        gh_ref.zeta_omega = BFP_OF_REAL((gh_ref.zeta * gh_ref.omega), GH_ZETA_OMEGA_FRAC);
        return gh_ref.omega;
    }

    public float gh_set_zeta(float zeta)
    {
        gh_ref.zeta = zeta;
        Bound(gh_ref.zeta, 0.7f, 1.2f);
        gh_ref.zeta_omega = BFP_OF_REAL((gh_ref.zeta * gh_ref.omega), GH_ZETA_OMEGA_FRAC);
        return gh_ref.zeta;
    }

    public void gh_set_ref(Vect2<Integer> pos, Vect2<Integer> speed, Vect2<Integer> accel)
    {
        Vect2<Long> new_pos = Vect2.newLongVect2();
        new_pos.x = ((long)pos.x) << (GH_POS_REF_FRAC - INT32_POS_FRAC);
        new_pos.y = ((long)pos.y) << (GH_POS_REF_FRAC - INT32_POS_FRAC);
        gh_ref.pos = new_pos;
        INT32_VECT2_RSHIFT(gh_ref.speed, speed, (INT32_SPEED_FRAC - GH_SPEED_REF_FRAC));
        INT32_VECT2_RSHIFT(gh_ref.accel, accel, (INT32_ACCEL_FRAC - GH_ACCEL_REF_FRAC));
    }

    public static void gh_update_ref_from_pos_sp(Vect2<Integer> pos_sp)
    {

        VECT2_ADD(gh_ref.pos, gh_ref.speed);
        VECT2_ADD(gh_ref.speed, gh_ref.accel);

        // compute the "speed part" of accel = -2*zeta*omega*speed -omega^2(pos - pos_sp)
        Vect2<Integer> speed = Vect2.newIntVect2();
        INT32_VECT2_RSHIFT(speed, gh_ref.speed, (GH_SPEED_REF_FRAC - GH_ACCEL_REF_FRAC));
        VECT2_SMUL(speed, speed, -2 * gh_ref.zeta_omega);
        INT32_VECT2_RSHIFT(speed, speed, GH_ZETA_OMEGA_FRAC);
        // compute pos error in pos_sp resolution
        Vect2<Integer> pos_err = Vect2.newIntVect2();
        INT32_VECT2_RSHIFT_L(pos_err, gh_ref.pos, (GH_POS_REF_FRAC - INT32_POS_FRAC));
        VECT2_DIFF(pos_err, pos_err, pos_sp);
        // convert to accel resolution
        INT32_VECT2_RSHIFT(pos_err, pos_err, (INT32_POS_FRAC - GH_ACCEL_REF_FRAC));
        // compute the "pos part" of accel
        Vect2<Integer> pos = Vect2.newIntVect2();
        VECT2_SMUL(pos, pos_err, -gh_ref.omega_2);
        INT32_VECT2_RSHIFT(pos, pos, GH_OMEGA_2_FRAC);
        // sum accel
        VECT2_SUM(gh_ref.accel, speed, pos);

  /* Compute max ref accel/speed along route before saturation */
        gh_compute_ref_max(pos_err);

        gh_saturate_ref_accel();
        gh_saturate_ref_speed();
    }


    public static void gh_update_ref_from_speed_sp(Vect2<Integer> speed_sp)
    {
  /* WARNING: SPEED SATURATION UNTESTED */
        VECT2_ADD(gh_ref.pos, gh_ref.speed);
        VECT2_ADD(gh_ref.speed, gh_ref.accel);

        // compute speed error
        Vect2<Integer> speed_err = Vect2.newIntVect2();
        INT32_VECT2_RSHIFT(speed_err, speed_sp, (INT32_SPEED_FRAC - GH_SPEED_REF_FRAC));
        VECT2_DIFF(speed_err, gh_ref.speed, speed_err);
        // convert to accel resolution
        INT32_VECT2_RSHIFT(speed_err, speed_err, (GH_SPEED_REF_FRAC - GH_ACCEL_REF_FRAC));
        // compute accel from speed_sp
        VECT2_SMUL(gh_ref.accel, speed_err, -gh_ref.inv_tau);
        INT32_VECT2_RSHIFT(gh_ref.accel, gh_ref.accel, GH_REF_INV_TAU_FRAC);

  /* Compute max ref accel/speed along route before saturation */
        gh_compute_ref_max_speed(speed_sp);
        gh_compute_ref_max_accel(speed_err);

        gh_saturate_ref_accel();
        gh_saturate_ref_speed();
    }

    public static void gh_compute_route_ref(Vect2<Integer> ref_vector)
    {
        float f_route_ref = (float) Math.atan2(-ref_vector.y, -ref_vector.x);
        gh_ref.route_ref = ANGLE_BFP_OF_REAL(f_route_ref);
  /* Compute North and East route components */
        gh_ref.s_route_ref = PPRZ_ITRIG_SIN(gh_ref.s_route_ref, gh_ref.route_ref);
        gh_ref.c_route_ref = PPRZ_ITRIG_COS(gh_ref.c_route_ref, gh_ref.route_ref);
        gh_ref.c_route_ref = Math.abs(gh_ref.c_route_ref);
        gh_ref.s_route_ref = Math.abs(gh_ref.s_route_ref);
    }

    public static void gh_compute_ref_max(Vect2<Integer> ref_vector)
    {
  /* Bound ref to max speed/accel along route reference angle.
   * If angle can't be computed, simply set both axes to max magnitude/sqrt(2).
   */
        if (ref_vector.x == 0 && ref_vector.y == 0) {
            gh_ref.max_accel.x = gh_ref.max_accel.y = (int)(gh_max_accel * 0.707);
            gh_ref.max_vel.x = gh_ref.max_vel.y = (int)(gh_ref.max_speed_int * 0.707);
        } else {
            gh_compute_route_ref(ref_vector);
    /* Compute maximum acceleration*/
            gh_ref.max_accel.x = INT_MULT_RSHIFT(gh_max_accel, gh_ref.c_route_ref, INT32_TRIG_FRAC);
            gh_ref.max_accel.y = INT_MULT_RSHIFT(gh_max_accel, gh_ref.s_route_ref, INT32_TRIG_FRAC);
    /* Compute maximum reference x/y velocity from absolute max_speed */
            gh_ref.max_vel.x = INT_MULT_RSHIFT(gh_ref.max_speed_int, gh_ref.c_route_ref, INT32_TRIG_FRAC);
            gh_ref.max_vel.y = INT_MULT_RSHIFT(gh_ref.max_speed_int, gh_ref.s_route_ref, INT32_TRIG_FRAC);
        }
  /* restore gh_ref.speed range (Q14.17) */
        INT32_VECT2_LSHIFT(gh_ref.max_vel, gh_ref.max_vel, (GH_SPEED_REF_FRAC - GH_MAX_SPEED_REF_FRAC));
    }

    public static void gh_compute_ref_max_accel(Vect2<Integer> ref_vector)
    {
  /* Bound ref to max accel along reference vector.
   * If angle can't be computed, simply set both axes to max magnitude/sqrt(2).
   */
        if (ref_vector.x == 0 && ref_vector.y == 0) {
            gh_ref.max_accel.x = gh_ref.max_accel.y = (int)(gh_max_accel * 0.707);
        } else {
            gh_compute_route_ref(ref_vector);
    /* Compute maximum acceleration*/
            gh_ref.max_accel.x = INT_MULT_RSHIFT(gh_max_accel, gh_ref.c_route_ref, INT32_TRIG_FRAC);
            gh_ref.max_accel.y = INT_MULT_RSHIFT(gh_max_accel, gh_ref.s_route_ref, INT32_TRIG_FRAC);
        }
    }

    public static void gh_compute_ref_max_speed(Vect2<Integer> ref_vector)
    {
  /* Bound ref to max speed along reference vector.
   * If angle can't be computed, simply set both axes to max magnitude/sqrt(2).
   */
        if (ref_vector.x == 0 && ref_vector.y == 0) {
            gh_ref.max_vel.x = gh_ref.max_vel.y = (int)(gh_ref.max_speed_int * 0.707);
        } else {
            gh_compute_route_ref(ref_vector);
    /* Compute maximum reference x/y velocity from absolute max_speed */
            gh_ref.max_vel.x = INT_MULT_RSHIFT(gh_ref.max_speed_int, gh_ref.c_route_ref, INT32_TRIG_FRAC);
            gh_ref.max_vel.y = INT_MULT_RSHIFT(gh_ref.max_speed_int, gh_ref.s_route_ref, INT32_TRIG_FRAC);
        }
  /* restore gh_ref.speed range (Q14.17) */
        INT32_VECT2_LSHIFT(gh_ref.max_vel, gh_ref.max_vel, (GH_SPEED_REF_FRAC - GH_MAX_SPEED_REF_FRAC));
    }

    /** saturate reference accelerations */
    public static void gh_saturate_ref_accel()
    {
  /* Saturate accelerations */
        gh_ref.accel.x = BoundAbs(gh_ref.accel.x, gh_ref.max_accel.x);
        gh_ref.accel.y = BoundAbs(gh_ref.accel.y, gh_ref.max_accel.y);
    }

    /** Saturate ref speed and adjust acceleration accordingly */
    public static void gh_saturate_ref_speed()
    {
        if (gh_ref.speed.x < -gh_ref.max_vel.x) {
            gh_ref.speed.x = -gh_ref.max_vel.x;
            if (gh_ref.accel.x < 0) {
                gh_ref.accel.x = 0;
            }
        } else if (gh_ref.speed.x > gh_ref.max_vel.x) {
            gh_ref.speed.x = gh_ref.max_vel.x;
            if (gh_ref.accel.x > 0) {
                gh_ref.accel.x = 0;
            }
        }
        if (gh_ref.speed.y < -gh_ref.max_vel.y) {
            gh_ref.speed.y = -gh_ref.max_vel.y;
            if (gh_ref.accel.y < 0) {
                gh_ref.accel.y = 0;
            }
        } else if (gh_ref.speed.y > gh_ref.max_vel.y) {
            gh_ref.speed.y = gh_ref.max_vel.y;
            if (gh_ref.accel.y > 0) {
                gh_ref.accel.y = 0;
            }
        }
    }
}

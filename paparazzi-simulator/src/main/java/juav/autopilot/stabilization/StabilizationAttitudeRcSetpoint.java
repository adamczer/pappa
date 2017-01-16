package juav.autopilot.stabilization;

import ub.juav.airborne.math.functions.algebra.PprzAlgebraFloat;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.Vect3;

import static juav.autopilot.guidance.GuidanceH.MAX_PPRZ;
import static juav.autopilot.radiocontrol.RadioControl.*;
import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.stab_att_sp_euler;
import static juav.autopilot.state.State.stateGetNedToBodyEulers_f;
import static juav.autopilot.state.State.stateGetNedToBodyEulers_i;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.QUAT_COPY;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraFloat.*;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.*;
import static ub.juav.airborne.math.functions.trig.PprzTrig.PPRZ_ITRIG_SIN;

/**
 * Created by adamczer on 11/14/16.
 */
public class StabilizationAttitudeRcSetpoint {

    private static float care_free_heading = 0;
    public static final double STABILIZATION_ATTITUDE_SP_MAX_PHI = 0.7853981625;
    public static final double STABILIZATION_ATTITUDE_SP_MAX_THETA = 0.7853981625;
    public static final double STABILIZATION_ATTITUDE_SP_MAX_R = 10.4719755;

    /*   This is a different way to obtain yaw. It will not switch when going beyond 90 degrees pitch.
     However, when rolling more then 90 degrees in combination with pitch it switches. For a
     transition vehicle this is better as 90 degrees pitch will occur, but more than 90 degrees roll probably not. */
    public static int stabilization_attitude_get_heading_i()
    {
        Eulers<Integer> att = stateGetNedToBodyEulers_i();

        int heading;

        if (Math.abs(att.phi) < INT32_ANGLE_PI_2) {
            int sin_theta = 0;
            sin_theta = PPRZ_ITRIG_SIN(sin_theta, att.theta);
            heading = att.psi - INT_MULT_RSHIFT(sin_theta, att.phi, INT32_TRIG_FRAC);
        } else if (ANGLE_FLOAT_OF_BFP(att.theta) > 0) {
            heading = att.psi - att.phi;
        } else {
            heading = att.psi + att.phi;
        }
        return heading;
    }

    /** Read attitude setpoint from RC as quaternion
     * Interprets the stick positions as axes.
     * @param[in]  coordinated_turn  true if in horizontal mode forward
     * @param[in]  in_carefree       true if in carefree mode
     * @param[in]  in_flight         true if in flight
     * @param[out] q_sp              attitude setpoint as quaternion
     */
    public static void stabilization_attitude_read_rc_setpoint_quat_f(Quat<Float> q_sp, boolean in_flight, boolean in_carefree,
                                                                      boolean coordinated_turn)
    {

        // FIXME: remove me, do in quaternion directly
        // is currently still needed, since the yaw setpoint integration is done in eulers
//        #if defined STABILIZATION_ATTITUDE_TYPE_INT
        stabilization_attitude_read_rc_setpoint_eulers(stab_att_sp_euler, in_flight, in_carefree, coordinated_turn);
//        #else
//        stabilization_attitude_read_rc_setpoint_eulers_f(&stab_att_sp_euler, in_flight, in_carefree, coordinated_turn);
//        #endif

        Quat<Float> q_rp_cmd = Quat.newFloat();
        stabilization_attitude_read_rc_roll_pitch_quat_f(q_rp_cmd);

  /* get current heading */
        Vect3<Float> zaxis = Vect3.newFloat(0.f, 0.f, 1.f);
        Quat<Float> q_yaw = Quat.newFloat();

        //Care Free mode
        if (in_carefree) {
            //care_free_heading has been set to current psi when entering care free mode.
            float_quat_of_axis_angle(q_yaw, zaxis, care_free_heading);
        } else {
            float_quat_of_axis_angle(q_yaw, zaxis, stateGetNedToBodyEulers_f().psi);
        }

  /* roll/pitch commands applied to to current heading */
        Quat<Float> q_rp_sp = Quat.newFloat();
        float_quat_comp(q_rp_sp, q_yaw, q_rp_cmd);
        float_quat_normalize(q_rp_sp);

        if (in_flight) {
    /* get current heading setpoint */
            Quat<Float> q_yaw_sp = Quat.newFloat();
//            #if defined STABILIZATION_ATTITUDE_TYPE_INT
            float_quat_of_axis_angle(q_yaw_sp, zaxis, ANGLE_FLOAT_OF_BFP(stab_att_sp_euler.psi));
//            #else
//            float_quat_of_axis_angle(&q_yaw_sp, &zaxis, stab_att_sp_euler.psi);
//            #endif

    /* rotation between current yaw and yaw setpoint */
            Quat<Float> q_yaw_diff = Quat.newFloat();
            float_quat_comp_inv(q_yaw_diff, q_yaw_sp, q_yaw);

    /* compute final setpoint with yaw */
            float_quat_comp_norm_shortest(q_sp, q_rp_sp, q_yaw_diff);
        } else {
            QUAT_COPY(q_sp, q_rp_sp);
        }
    }

    /** Read attitude setpoint from RC as euler angles
     * @param[in]  coordinated_turn  true if in horizontal mode forward
     * @param[in]  in_carefree       true if in carefree mode
     * @param[in]  in_flight         true if in flight
     * @param[out] sp                attitude setpoint as euler angles
     */
    private static long last_ts = 0;
    public static void stabilization_attitude_read_rc_setpoint_eulers(Eulers<Integer> sp, boolean in_flight, boolean in_carefree,
                                                        boolean coordinated_turn)
    {
  /* last time this function was called, used to calculate yaw setpoint update */


        sp.phi = get_rc_roll();
        sp.theta = get_rc_pitch();

        if (in_flight) {
    /* calculate dt for yaw integration */
            float dt = (System.currentTimeMillis() - last_ts) / 1000;
    /* make sure nothing drastically weird happens, bound dt to 0.5sec */
            PprzAlgebraFloat.Bound(dt, 0, 0.5f);

    /* do not advance yaw setpoint if within a small deadband around stick center or if throttle is zero */
//            if (YAW_DEADBAND_EXCEEDED() && !THROTTLE_STICK_DOWN()) {
//                sp->psi += get_rc_yaw() * dt;
//                INT32_ANGLE_NORMALIZE(sp->psi);
//            }
//            if (coordinated_turn) {
//                //Coordinated turn
//                //feedforward estimate angular rotation omega = g*tan(phi)/v
//                //Take v = 9.81/1.3 m/s
//                int32_t omega;
//                const int32_t max_phi = ANGLE_BFP_OF_REAL(RadOfDeg(85.0));
//                if (abs(sp->phi) < max_phi) {
//                    omega = ANGLE_BFP_OF_REAL(1.3 * tanf(ANGLE_FLOAT_OF_BFP(sp->phi)));
//                } else { //max 60 degrees roll, then take constant omega
//                    omega = ANGLE_BFP_OF_REAL(1.3 * 1.72305 * ((sp->phi > 0) - (sp->phi < 0)));
//                }
//
//                sp->psi += omega * dt;
//            }
//            #ifdef STABILIZATION_ATTITUDE_SP_PSI_DELTA_LIMIT
//            // Make sure the yaw setpoint does not differ too much from the real yaw
//            // to prevent a sudden switch at 180 deg
//            const int32_t delta_limit = ANGLE_BFP_OF_REAL(STABILIZATION_ATTITUDE_SP_PSI_DELTA_LIMIT);
//
//            int32_t heading = stabilization_attitude_get_heading_i();
//
//            int32_t delta_psi = sp->psi - heading;
//            INT32_ANGLE_NORMALIZE(delta_psi);
//            if (delta_psi > delta_limit) {
//                sp->psi = heading + delta_limit;
//            } else if (delta_psi < -delta_limit) {
//                sp->psi = heading - delta_limit;
//            }
//            INT32_ANGLE_NORMALIZE(sp->psi);
//            #endif
            //Care Free mode
//            if (in_carefree) {
//                //care_free_heading has been set to current psi when entering care free mode.
//                int32_t cos_psi;
//                int32_t sin_psi;
//                int32_t temp_theta;
//                int32_t care_free_delta_psi_i;
//
//                care_free_delta_psi_i = sp->psi - ANGLE_BFP_OF_REAL(care_free_heading);
//
//                INT32_ANGLE_NORMALIZE(care_free_delta_psi_i);
//
//                PPRZ_ITRIG_SIN(sin_psi, care_free_delta_psi_i);
//                PPRZ_ITRIG_COS(cos_psi, care_free_delta_psi_i);
//
//                temp_theta = INT_MULT_RSHIFT(cos_psi, sp->theta, INT32_ANGLE_FRAC) - INT_MULT_RSHIFT(sin_psi, sp->phi,
//                        INT32_ANGLE_FRAC);
//                sp->phi = INT_MULT_RSHIFT(cos_psi, sp->phi, INT32_ANGLE_FRAC) - INT_MULT_RSHIFT(sin_psi, sp->theta, INT32_ANGLE_FRAC);
//
//                sp->theta = temp_theta;
//            }
        } else { /* if not flying, use current yaw as setpoint */
            sp.psi = stateGetNedToBodyEulers_i().psi;
        }

  /* update timestamp for dt calculation */
        last_ts = System.currentTimeMillis();
    }

    /** Read roll/pitch command from RC as quaternion.
     * Interprets the stick positions as axes.
     * @param[out] q quaternion representing the RC roll/pitch input
     */
    public static void stabilization_attitude_read_rc_roll_pitch_quat_f(Quat<Float> q)
    {
  /* orientation vector describing simultaneous rotation of roll/pitch */
        Vect3<Float> ov = Vect3.newFloat();
        ov.x = get_rc_roll_f();
        ov.y = get_rc_pitch_f();
        ov.z = 0.0f;

  /* quaternion from that orientation vector */
        float_quat_of_orientation_vect(q, ov);
    }

    public static int get_rc_roll()
    {
        int max_rc_phi = ANGLE_BFP_OF_REAL(STABILIZATION_ATTITUDE_SP_MAX_PHI);
        int roll = radio_control.getValue(RADIO_ROLL);
//        #if STABILIZATION_ATTITUDE_DEADBAND_A
//        DeadBand(roll, STABILIZATION_ATTITUDE_DEADBAND_A);
//        return roll * max_rc_phi / (MAX_PPRZ - STABILIZATION_ATTITUDE_DEADBAND_A);
//        #else
        return roll * max_rc_phi / MAX_PPRZ;
//        #endif
    }

    public static int get_rc_pitch()
    {
        int max_rc_theta = ANGLE_BFP_OF_REAL(STABILIZATION_ATTITUDE_SP_MAX_THETA);
        int pitch = radio_control.getValue(RADIO_PITCH);
//        #if STABILIZATION_ATTITUDE_DEADBAND_E
//        DeadBand(pitch, STABILIZATION_ATTITUDE_DEADBAND_E);
//        return pitch * max_rc_theta / (MAX_PPRZ - STABILIZATION_ATTITUDE_DEADBAND_E);
//        #else
        return pitch * max_rc_theta / MAX_PPRZ;
//        #endif
    }

    static float get_rc_roll_f()
    {
//        int roll = radio_control.values[RADIO_ROLL];
        int roll = radio_control.getValue(RADIO_ROLL);
//        #if STABILIZATION_ATTITUDE_DEADBAND_A
//        DeadBand(roll, STABILIZATION_ATTITUDE_DEADBAND_A);
//        return roll * STABILIZATION_ATTITUDE_SP_MAX_PHI / (MAX_PPRZ - STABILIZATION_ATTITUDE_DEADBAND_A);
//        #else
        return (float) (roll * STABILIZATION_ATTITUDE_SP_MAX_PHI / MAX_PPRZ);
//        #endif
    }

    static float get_rc_pitch_f()
    {
        int pitch = radio_control.getValue(RADIO_PITCH);
//        #if STABILIZATION_ATTITUDE_DEADBAND_E
//        DeadBand(pitch, STABILIZATION_ATTITUDE_DEADBAND_E);
//        return pitch * STABILIZATION_ATTITUDE_SP_MAX_THETA / (MAX_PPRZ - STABILIZATION_ATTITUDE_DEADBAND_E);
//        #else
        return (float) (pitch * STABILIZATION_ATTITUDE_SP_MAX_THETA / MAX_PPRZ);
//        #endif
    }
}

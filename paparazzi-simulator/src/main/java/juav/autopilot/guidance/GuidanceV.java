package juav.autopilot.guidance;

import ub.juav.airborne.math.structs.algebra.RMat;

import static juav.autopilot.commands.Commands.COMMAND_THRUST;
import static juav.autopilot.guidance.GuidanceH.MAX_PPRZ;
import static juav.autopilot.guidance.GuidanceVAdapt.*;
import static juav.autopilot.guidance.ref.GuidanceVRef.*;
import static juav.autopilot.navigation.Navigation.*;
import static juav.autopilot.radiocontrol.RadioControl.*;
import static juav.autopilot.stabilization.Stabilization.stabilization_cmd;
import static juav.autopilot.state.State.*;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.*;

/**
 * Created by adamczer on 10/28/16.
 */
public class GuidanceV {
    public static final short GUIDANCE_V_MODE_KILL = 0;
    public static final short GUIDANCE_V_MODE_RC_DIRECT = 1;
    public static final short GUIDANCE_V_MODE_RC_CLIMB = 2;
    public static final short GUIDANCE_V_MODE_CLIMB = 3;
    public static final short GUIDANCE_V_MODE_HOVER = 4;
    public static final short GUIDANCE_V_MODE_NAV = 5;
    public static final short GUIDANCE_V_MODE_MODULE = 6;
    public static final short GUIDANCE_V_MODE_FLIP = 7;
    public static final short GUIDANCE_V_MODE_GUIDED = 8;
    public static final int GUIDANCE_V_HOVER_KP = 150;
    public static final int GUIDANCE_V_HOVER_KD = 80;
    public static final int GUIDANCE_V_HOVER_KI = 20;
    public static final float GUIDANCE_V_NOMINAL_HOVER_THROTTLE = 0.5f;
    public static final boolean GUIDANCE_V_ADAPT_THROTTLE_ENABLED = true;
    public static final int GUIDANCE_V_CLIMB_RC_DEADBAND = MAX_PPRZ/10;
    public static final double GUIDANCE_V_MAX_RC_CLIMB_SPEED = GUIDANCE_V_REF_MIN_ZD;
    public static final double GUIDANCE_V_MAX_RC_DESCENT_SPEED = GUIDANCE_V_REF_MIN_ZD;
    public static final int GUIDANCE_V_MIN_ERR_Z = POS_BFP_OF_REAL(-10.);
    public static final int GUIDANCE_V_MAX_ERR_Z = POS_BFP_OF_REAL(10.);
    public static final int GUIDANCE_V_MIN_ERR_ZD = SPEED_BFP_OF_REAL(-10.);
    public static final int GUIDANCE_V_MAX_ERR_ZD = SPEED_BFP_OF_REAL(10.);
    public static final int GUIDANCE_V_MAX_SUM_ERR = 2000000;
    public static final int FF_CMD_FRAC = 18;

    static short guidance_v_mode;
    static int guidance_v_ff_cmd;
    static int guidance_v_fb_cmd;
    static int guidance_v_delta_t;

    static float guidance_v_nominal_throttle;
    static boolean guidance_v_adapt_throttle_enabled;


    /**
     * Direct throttle from radio control.
     * range 0:#MAX_PPRZ
     */
    static int guidance_v_rc_delta_t;

    /**
     * Vertical speed setpoint from radio control.
     * fixed point representation: Q12.19
     * accuracy 0.0000019, range +/-4096
     */
    static int guidance_v_rc_zd_sp;

    static int guidance_v_z_sp;
    static int guidance_v_zd_sp;
    static int guidance_v_z_ref;
    static int guidance_v_zd_ref;
    static int guidance_v_zdd_ref;

    static int guidance_v_kp;
    static int guidance_v_kd;
    static int guidance_v_ki;

    static int guidance_v_z_sum_err;

    static int guidance_v_thrust_coeff;

    public static GuidanceV guidanceV;
    public static void guidance_v_init() {
        guidanceV = new GuidanceV();
        guidanceV.init();
    }
    public void init() {
        guidance_v_mode = GUIDANCE_V_MODE_KILL;

        guidance_v_kp = GUIDANCE_V_HOVER_KP;
        guidance_v_kd = GUIDANCE_V_HOVER_KD;
        guidance_v_ki = GUIDANCE_V_HOVER_KI;

        guidance_v_z_sum_err = 0;

        guidance_v_nominal_throttle = GUIDANCE_V_NOMINAL_HOVER_THROTTLE;
        guidance_v_adapt_throttle_enabled = GUIDANCE_V_ADAPT_THROTTLE_ENABLED;

        gv_adapt_init();

//        #if GUIDANCE_V_MODE_MODULE_SETTING == GUIDANCE_V_MODE_MODULE
//        guidance_v_module_init();
//        #endif

        //TODO Telemetry
        throw new IllegalStateException("Implement PERIODIC_TELEMETRY");
//        #if PERIODIC_TELEMETRY
//        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_VERT_LOOP, send_vert_loop);
//        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_TUNE_VERT, send_tune_vert);
//        #endif
    }
    static int climb_scale;
    static int descent_scale;
    public void guidance_v_read_rc()
    {
//  printf("guidance_v_read_rc\n");//TODO
  /* used in RC_DIRECT directly and as saturation in CLIMB and HOVER */
        guidance_v_rc_delta_t = radio_control.values[RADIO_THROTTLE];

  /* used in RC_CLIMB */
        guidance_v_rc_zd_sp = (MAX_PPRZ / 2) - radio_control.values[RADIO_THROTTLE];
        guidance_v_rc_zd_sp = DeadBand(guidance_v_rc_zd_sp, GUIDANCE_V_CLIMB_RC_DEADBAND);

        climb_scale = Math.abs(SPEED_BFP_OF_REAL(GUIDANCE_V_MAX_RC_CLIMB_SPEED) /
            (MAX_PPRZ / 2 - GUIDANCE_V_CLIMB_RC_DEADBAND));
        descent_scale = Math.abs(SPEED_BFP_OF_REAL(GUIDANCE_V_MAX_RC_DESCENT_SPEED) /
                (MAX_PPRZ / 2 - GUIDANCE_V_CLIMB_RC_DEADBAND));

        if (guidance_v_rc_zd_sp > 0) {
            guidance_v_rc_zd_sp *= descent_scale;
        } else {
            guidance_v_rc_zd_sp *= climb_scale;
        }
    }

    public void guidance_v_mode_changed(short new_mode)
    {
        if (new_mode == guidance_v_mode) {
            return;
        }

        switch (new_mode) {
//            {
///*//    case GUIDANCE_V_MODE_HOVER:
////      printf("CASE GUIDANCE_V_MODE_HOVER\n");
////    case GUIDANCE_V_MODE_GUIDED:
////      printf("CASE GUIDANCE_V_MODE_GUIDED\n");
////      guidance_v_z_sp = stateGetPositionNed_i()->z; // set current altitude as setpoint
////      guidance_v_z_sum_err = 0;
////      GuidanceVSetRef(stateGetPositionNed_i()->z, 0, 0);
////      break;
////
////    case GUIDANCE_V_MODE_RC_CLIMB:
////      printf("CASE GUIDANCE_V_MODE_RC_CLIMB\n");
////    case GUIDANCE_V_MODE_CLIMB:
////      printf("CASE GUIDANCE_V_MODE_CLIMB\n");
////      guidance_v_zd_sp = 0;*/
//            }
            case GUIDANCE_V_MODE_NAV:
//      printf("CASE GUIDANCE_V_MODE_NAV\n");
                guidance_v_z_sum_err = 0;
                GuidanceVSetRef(stateGetPositionNed_i().z, stateGetSpeedNed_i().z, 0);
                break;

   /*         {//#if GUIDANCE_V_MODE_MODULE_SETTING == GUIDANCE_V_MODE_MODULE
//    case GUIDANCE_V_MODE_MODULE:
//      printf("CASE GUIDANCE_V_MODE_MODULE\n");
//      guidance_v_module_enter();
//      break;
//#endif
//
//    case GUIDANCE_V_MODE_FLIP:
//      printf("CASE GUIDANCE_V_MODE_FLIP\n");
//      break;
}*/

            default:
                break;

        }

        guidance_v_mode = new_mode;

    }



    public void guidance_v_run(boolean in_flight)
    {
//  printf("guidance_v_run\n");//TODO

        // FIXME... SATURATIONS NOT TAKEN INTO ACCOUNT
        // AKA SUPERVISION and co
        guidance_v_thrust_coeff = get_vertical_thrust_coeff();
        if (in_flight) {
            int vertical_thrust = (stabilization_cmd[COMMAND_THRUST] * guidance_v_thrust_coeff) >> INT32_TRIG_FRAC;
            gv_adapt_run(stateGetAccelNed_i().z, vertical_thrust, guidance_v_zd_ref);
        } else {
    /* reset estimate while not in_flight */
            gv_adapt_init();
        }

        switch (guidance_v_mode) {

            case GUIDANCE_V_MODE_RC_DIRECT:
//        printf("CASE GUIDANCE_V_MODE_RC_DIRECT\n");
                guidance_v_z_sp = stateGetPositionNed_i().z; // for display only
                stabilization_cmd[COMMAND_THRUST] = guidance_v_rc_delta_t;
                break;

//    case GUIDANCE_V_MODE_RC_CLIMB:
//      printf("CASE GUIDANCE_V_MODE_RC_CLIMB\n");
//      guidance_v_zd_sp = guidance_v_rc_zd_sp;
//      gv_update_ref_from_zd_sp(guidance_v_zd_sp, stateGetPositionNed_i()->z);
//      run_hover_loop(in_flight);
//      stabilization_cmd[COMMAND_THRUST] = guidance_v_delta_t;
//      break;

//    case GUIDANCE_V_MODE_CLIMB:
//      printf("CASE GUIDANCE_V_MODE_CLIMB\n");
//      gv_update_ref_from_zd_sp(guidance_v_zd_sp, stateGetPositionNed_i()->z);
//      run_hover_loop(in_flight);
//#if !NO_RC_THRUST_LIMIT
//      /* use rc limitation if available */
//      if (radio_control.status == RC_OK) {
//        stabilization_cmd[COMMAND_THRUST] = Min(guidance_v_rc_delta_t, guidance_v_delta_t);
//      } else
//#endif
//        stabilization_cmd[COMMAND_THRUST] = guidance_v_delta_t;
//      break;

//    case GUIDANCE_V_MODE_HOVER:
//      printf("CASE GUIDANCE_V_MODE_HOVER\n");
//    case GUIDANCE_V_MODE_GUIDED:
//      printf("CASE GUIDANCE_V_MODE_GUIDED\n");
//      guidance_v_zd_sp = 0;
//      gv_update_ref_from_z_sp(guidance_v_z_sp);
//      run_hover_loop(in_flight);
//#if !NO_RC_THRUST_LIMIT
//      /* use rc limitation if available */
//      if (radio_control.status == RC_OK) {
//        stabilization_cmd[COMMAND_THRUST] = Min(guidance_v_rc_delta_t, guidance_v_delta_t);
//      } else
//#endif
//        stabilization_cmd[COMMAND_THRUST] = guidance_v_delta_t;
//      break;
//
//#if GUIDANCE_V_MODE_MODULE_SETTING == GUIDANCE_V_MODE_MODULE
//    case GUIDANCE_V_MODE_MODULE:
//      printf("CASE GUIDANCE_V_MODE_MODULE\n");
//      guidance_v_module_run(in_flight);
//      break;
//#endif

            case GUIDANCE_V_MODE_NAV:
//      printf("CASE GUIDANCE_V_MODE_NAV\n");
            {
                if (vertical_mode == VERTICAL_MODE_ALT) {
//        printf("vertical_mode == VERTICAL_MODE_ALT\n");
                    guidance_v_z_sp = -nav_flight_altitude;
                    guidance_v_zd_sp = 0;
                    gv_update_ref_from_z_sp(guidance_v_z_sp);
                    run_hover_loop(in_flight);
                } else if (vertical_mode == VERTICAL_MODE_CLIMB) {
//        printf("vertical_mode == VERTICAL_MODE_CLIMB\n");
                    guidance_v_z_sp = stateGetPositionNed_i().z;
                    guidance_v_zd_sp = -nav_climb;
                    gv_update_ref_from_zd_sp(guidance_v_zd_sp, stateGetPositionNed_i().z);
                    run_hover_loop(in_flight);
                } else if (vertical_mode == VERTICAL_MODE_MANUAL) {
//        printf("vertical_mode == VERTICAL_MODE_MANUAL\n");
                    guidance_v_z_sp = stateGetPositionNed_i().z;
                    guidance_v_zd_sp = stateGetSpeedNed_i().z;
                    GuidanceVSetRef(guidance_v_z_sp, guidance_v_zd_sp, 0);
                    guidance_v_z_sum_err = 0;
                    guidance_v_delta_t = nav_throttle;
                }
//                #if !NO_RC_THRUST_LIMIT
//      printf("!NO_RC_THRUST_LIMIT\n");
      /* use rc limitation if available */
                if (radio_control.status == RC_OK) {
                    stabilization_cmd[COMMAND_THRUST] = Math.min(guidance_v_rc_delta_t, guidance_v_delta_t);
                } else
//                #endif
                stabilization_cmd[COMMAND_THRUST] = guidance_v_delta_t;
                break;
            }

//    case GUIDANCE_V_MODE_FLIP:
//      printf("CASE GUIDANCE_V_MODE_FLIP\n");
//      break;

            default:
                break;
        }
    }

    /// get the cosine of the angle between thrust vector and gravity vector
    public static int get_vertical_thrust_coeff() {
        // cos(30°) = 0.8660254
        int max_bank_coef = BFP_OF_REAL(0.8660254f, INT32_TRIG_FRAC);

        RMat<Integer> att = stateGetNedToBodyRMat_i();
  /* thrust vector:
   *  int32_rmat_vmult(&thrust_vect, &att, &zaxis)
   * same as last colum of rmat with INT32_TRIG_FRAC
   * struct Int32Vect thrust_vect = {att.m[2], att.m[5], att.m[8]};
   *
   * Angle between two vectors v1 and v2:
   *  angle = acos(dot(v1, v2) / (norm(v1) * norm(v2)))
   * since here both are already of unit length:
   *  angle = acos(dot(v1, v2))
   * since we we want the cosine of the angle we simply need
   *  thrust_coeff = dot(v1, v2)
   * also can be simplified considering: v1 is zaxis with (0,0,1)
   *  dot(v1, v2) = v1.z * v2.z = v2.z
   */
        int coef = att.getFlattendElement(8);
        if (coef < max_bank_coef) {
            coef = max_bank_coef;
        }
        return coef;
    }

    static void run_hover_loop(boolean in_flight)
    {
//  printf("run_hover_loop\n");

  /* convert our reference to generic representation */
        long tmp  = gv_z_ref >> (GV_Z_REF_FRAC - INT32_POS_FRAC);
        guidance_v_z_ref = (int)tmp;
        guidance_v_zd_ref = gv_zd_ref << (INT32_SPEED_FRAC - GV_ZD_REF_FRAC);
        guidance_v_zdd_ref = gv_zdd_ref << (INT32_ACCEL_FRAC - GV_ZDD_REF_FRAC);
  /* compute the error to our reference */
        int err_z  = guidance_v_z_ref - stateGetPositionNed_i().z;
        Bound(err_z, GUIDANCE_V_MIN_ERR_Z, GUIDANCE_V_MAX_ERR_Z);
        int err_zd = guidance_v_zd_ref - stateGetSpeedNed_i().z;
        Bound(err_zd, GUIDANCE_V_MIN_ERR_ZD, GUIDANCE_V_MAX_ERR_ZD);

        if (in_flight) {
            guidance_v_z_sum_err += err_z;
            Bound(guidance_v_z_sum_err, -GUIDANCE_V_MAX_SUM_ERR, GUIDANCE_V_MAX_SUM_ERR);
        } else {
            guidance_v_z_sum_err = 0;
        }

  /* our nominal command : (g + zdd)*m   */
        int inv_m;
        if (guidance_v_adapt_throttle_enabled) {
            inv_m =  gv_adapt_X >> (GV_ADAPT_X_FRAC - FF_CMD_FRAC);
        } else {
    /* use the fixed nominal throttle */
            inv_m = BFP_OF_REAL(9.81 / (guidance_v_nominal_throttle * MAX_PPRZ), FF_CMD_FRAC);
        }

        int g_m_zdd = BFP_OF_REAL(9.81, FF_CMD_FRAC) -
            (guidance_v_zdd_ref << (FF_CMD_FRAC - INT32_ACCEL_FRAC));

        guidance_v_ff_cmd = g_m_zdd / inv_m;
  /* feed forward command */
        guidance_v_ff_cmd = (guidance_v_ff_cmd << INT32_TRIG_FRAC) / guidance_v_thrust_coeff;

  /* bound the nominal command to 0.9*MAX_PPRZ */
        Bound(guidance_v_ff_cmd, 0, 8640);


  /* our error feed back command                   */
  /* z-axis pointing down -> positive error means we need less thrust */
        guidance_v_fb_cmd = ((-guidance_v_kp * err_z)  >> 7) +
                ((-guidance_v_kd * err_zd) >> 16) +
                ((-guidance_v_ki * guidance_v_z_sum_err) >> 16);

        guidance_v_delta_t = guidance_v_ff_cmd + guidance_v_fb_cmd;

  /* bound the result */
        Bound(guidance_v_delta_t, 0, MAX_PPRZ);

    }

    static void GuidanceVSetRef(int _pos, int _speed, int _accel) {
        gv_set_ref(_pos, _speed, _accel);
        guidance_v_z_ref = _pos;
        guidance_v_zd_ref = _speed;
        guidance_v_zdd_ref = _accel;
    }
}

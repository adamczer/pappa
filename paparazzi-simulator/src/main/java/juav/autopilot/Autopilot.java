package juav.autopilot;

import juav.autopilot.stabilization.Stabilization;
import juav.autopilot.state.State;
import ub.cse.juav.jni.tasks.NativeTasks;

import static juav.autopilot.AutopilotArmingYaw.autopilot_arming_check_motors_on;
import static juav.autopilot.AutopilotArmingYaw.autopilot_arming_init;
import static juav.autopilot.AutopilotRcHelpers.kill_switch_is_on;
import static juav.autopilot.commands.Commands.SetCommands;
import static juav.autopilot.commands.Commands.SetRotorcraftCommands;
import static juav.autopilot.commands.Commands.commands_failsafe;
import static juav.autopilot.guidance.GuidanceH.*;
import static juav.autopilot.guidance.GuidanceV.*;
import static juav.autopilot.navigation.Navigation.*;
import static juav.autopilot.radiocontrol.RadioControl.RADIO_MODE;
import static juav.autopilot.radiocontrol.RadioControl.radio_control;
import static juav.autopilot.stabilization.Stabilization.stabilization_init;
import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.PERIODIC_FREQUENCY;
import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.stabilization_attitude_init;
import static juav.autopilot.stabilization.StabilizationNone.stabilization_none_init;
import static juav.autopilot.stabilization.StabilizationRate.stabilization_rate_init;

/**
 * Created by adamczer on 11/29/16.
 */
public class Autopilot {
    public static final short AP_MODE_KILL =             0;
    public static final short AP_MODE_FAILSAFE         = 1;
    public static final short AP_MODE_HOME            =  2;
    public static final short AP_MODE_RATE_DIRECT      = 3;
    public static final short AP_MODE_ATTITUDE_DIRECT  = 4;
    public static final short AP_MODE_RATE_RC_CLIMB    = 5;
    public static final short AP_MODE_ATTITUDE_RC_CLIMB =6;
    public static final short AP_MODE_ATTITUDE_CLIMB =   7;
    public static final short AP_MODE_RATE_Z_HOLD    =   8;
    public static final short AP_MODE_ATTITUDE_Z_HOLD =  9;
    public static final short AP_MODE_HOVER_DIRECT   =   10;
    public static final short AP_MODE_HOVER_CLIMB    =   11;
    public static final short AP_MODE_HOVER_Z_HOLD   =   12;
    public static final short AP_MODE_NAV           =    13;
    public static final short AP_MODE_RC_DIRECT    =     14 ; // Safety Pilot Direct Commands for helicopter direct control
    public static final short AP_MODE_CARE_FREE_DIRECT = 15;
    public static final short AP_MODE_FORWARD =          16;
    public static final short AP_MODE_MODULE =           17;
    public static final short AP_MODE_FLIP =             18;
    public static final short AP_MODE_GUIDED =           19;


    public static final float AUTOPILOT_IN_FLIGHT_MIN_THRUST = 500;
    public static final float AUTOPILOT_IN_FLIGHT_MIN_ACCEL = 2.0f;
    public static final float AUTOPILOT_IN_FLIGHT_MIN_SPEED = 0.2f;

    public static final int AUTOPILOT_IN_FLIGHT_TIME = 20;


    public static final short MODE_AUTO1 = AP_MODE_HOVER_Z_HOLD;
    public static final short MODE_AUTO2 =AP_MODE_NAV;
    public static final short MODE_MANUAL = AP_MODE_ATTITUDE_DIRECT;

    public static short autopilot_mode = -1;
    static short  autopilot_mode_auto2;

//    boolean   autopilot_in_flight;
    public static boolean getAutopilotInFlight() {
        boolean ret = NativeTasks.getAutopilotInFlightJuav();
        return ret;
    }
    long autopilot_in_flight_counter;
    int autopilot_flight_time;

//    public static boolean   autopilot_motors_on;
    boolean   kill_throttle;

    boolean   autopilot_rc;
    boolean   autopilot_power_switch;

//    boolean   autopilot_ground_detected;
//    public static boolean getAutopilotGroundDetected() {
//
//    }
    public static void setAutopilotGroundDetected(boolean b) {
        NativeTasks.setAutopilotGroundDetected(b);
    }
//    boolean   autopilot_detect_ground_once;
    public static void setAutopilotDetectGroundOnce(boolean b) {
        NativeTasks.setAutopilotDetectGroundOnce(b);
    }

    public static final short MODE_STARTUP = AP_MODE_KILL;

    public static final short FAILSAFE_MODE_TOO_FAR_FROM_HOME = AP_MODE_FAILSAFE;

//    below must be in c
    /*void send_autopilot_version(TransportTx trans, struct link_device*dev)
    {
        static uint32_t ap_version = PPRZ_VERSION_INT;
        static char *ver_desc = PPRZ_VERSION_DESC;
        pprz_msg_send_AUTOPILOT_VERSION(trans, dev, AC_ID, &ap_version, strlen(ver_desc), ver_desc);
    }

    static void send_alive(struct transport_tx *trans, struct link_device *dev)
    {
        pprz_msg_send_ALIVE(trans, dev, AC_ID, 16, MD5SUM);
    }

    static void send_attitude(struct transport_tx *trans, struct link_device *dev)
    {
        struct FloatEulers *att = stateGetNedToBodyEulers_f();
        pprz_msg_send_ATTITUDE(trans, dev, AC_ID, &(att.phi), &(att.psi), &(att.theta));
    };

    #if USE_MOTOR_MIXING
    #include "subsystems/actuators/motor_mixing.h"
            #endif

    static void send_status(struct transport_tx *trans, struct link_device *dev)
    {
        uint32_t imu_nb_err = 0;
        #if USE_MOTOR_MIXING
        uint8_t _motor_nb_err = motor_mixing.nb_saturation + motor_mixing.nb_failure * 10;
        #else
        uint8_t _motor_nb_err = 0;
        #endif
        #if USE_GPS
        uint8_t fix = gps.fix;
        #else
        uint8_t fix = 0;
        #endif
        uint16_t time_sec = sys_time.nb_sec;
        pprz_msg_send_ROTORCRAFT_STATUS(trans, dev, AC_ID,
                &imu_nb_err, &_motor_nb_err,
        &radio_control.status, &radio_control.frame_rate,
        &fix, &autopilot_mode,
        &autopilot_in_flight, &autopilot_motors_on,
        &guidance_h.mode, &guidance_v_mode,
        &electrical.vsupply, &time_sec);
    }

    static void send_energy(struct transport_tx *trans, struct link_device *dev)
    {
        uint16_t e = electrical.energy;
        if (fabs(electrical.energy) >= INT16_MAX) {
            e = INT16_MAX;
        }
        float vsup = ((float)electrical.vsupply) / 10.0f;
        float curs = ((float)electrical.current) / 1000.0f;
        float power = vsup * curs;
        pprz_msg_send_ENERGY(trans, dev, AC_ID, &vsup, &curs, &e, &power);
    }

    static void send_fp(struct transport_tx *trans, struct link_device *dev)
    {
        int32_t carrot_up = -guidance_v_z_sp;
        pprz_msg_send_ROTORCRAFT_FP(trans, dev, AC_ID,
                &(stateGetPositionEnu_i().x),
        &(stateGetPositionEnu_i().y),
        &(stateGetPositionEnu_i().z),
        &(stateGetSpeedEnu_i().x),
        &(stateGetSpeedEnu_i().y),
        &(stateGetSpeedEnu_i().z),
        &(stateGetNedToBodyEulers_i().phi),
        &(stateGetNedToBodyEulers_i().theta),
        &(stateGetNedToBodyEulers_i().psi),
        &guidance_h.sp.pos.y,
        &guidance_h.sp.pos.x,
        &carrot_up,
        &guidance_h.sp.heading,
        &stabilization_cmd[COMMAND_THRUST],
        &autopilot_flight_time);
    }

    #ifdef RADIO_CONTROL
    static void send_rc(struct transport_tx *trans, struct link_device *dev)
    {
        pprz_msg_send_RC(trans, dev, AC_ID, RADIO_CONTROL_NB_CHANNEL, radio_control.values);
    }

    static void send_rotorcraft_rc(struct transport_tx *trans, struct link_device *dev)
    {
        #ifdef RADIO_KILL_SWITCH
        int16_t _kill_switch = radio_control.values[RADIO_KILL_SWITCH];
        #else
        int16_t _kill_switch = 42;
        #endif
        pprz_msg_send_ROTORCRAFT_RADIO_CONTROL(trans, dev, AC_ID,
                &radio_control.values[RADIO_ROLL],
        &radio_control.values[RADIO_PITCH],
        &radio_control.values[RADIO_YAW],
        &radio_control.values[RADIO_THROTTLE],
        &radio_control.values[RADIO_MODE],
        &_kill_switch,
        &radio_control.status);
    }
    #endif

    #ifdef ACTUATORS
    static void send_actuators(struct transport_tx *trans, struct link_device *dev)
    {
        pprz_msg_send_ACTUATORS(trans, dev, AC_ID , ACTUATORS_NB, actuators);
    }
    #endif

    static void send_dl_value(struct transport_tx *trans, struct link_device *dev)
    {
        PeriodicSendDlValue(trans, dev);
    }

    static void send_rotorcraft_cmd(struct transport_tx *trans, struct link_device *dev)
    {
        pprz_msg_send_ROTORCRAFT_CMD(trans, dev, AC_ID,
                &stabilization_cmd[COMMAND_ROLL],
        &stabilization_cmd[COMMAND_PITCH],
        &stabilization_cmd[COMMAND_YAW],
        &stabilization_cmd[COMMAND_THRUST]);
    }*/
    //// Above must be in c ^^


    public void autopilot_init()//TODO PORT
    {
//    printf("autopilot_init*********************************************************************************\n");
  /* mode is finally set at end of init if MODE_STARTUP is not KILL */
        autopilot_mode = AP_MODE_KILL;
//        autopilot_motors_on = false;
        kill_throttle = ! getAutopilotMotorsOn();
//        autopilot_in_flight = false; //todo modified by c commands
        autopilot_in_flight_counter = 0;
        autopilot_mode_auto2 = MODE_AUTO2;
        setAutopilotGroundDetected(false);
        setAutopilotDetectGroundOnce(false);
        autopilot_flight_time = 0;
        autopilot_rc = true;
        autopilot_power_switch = false;
//#ifdef POWER_SWITCH_GPIO
//    printf("POWER_SWITCH_GPIO\n");
//  gpio_setup_output(POWER_SWITCH_GPIO);
//  gpio_clear(POWER_SWITCH_GPIO); // POWER OFF
//#endif

        autopilot_arming_init();

//        if(1==1) throw new IllegalStateException("bisect nav_init(). ?");
        nav_init();//already inited
        guidance_h_init();
        guidance_v_init();

//        stabilization_init();
        stabilization_none_init();
        stabilization_rate_init();
        stabilization_attitude_init();

  /* set startup mode, propagates through to guidance h/v */
        autopilot_set_mode(MODE_STARTUP);

        //TODO periodic telemetry
//        throw new IllegalStateException("Implement periodic telemetry.");
//        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_AUTOPILOT_VERSION, send_autopilot_version);
//        Telemetry.registerPeriodicTelemetrySendAutopilotVersion();
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_ALIVE, send_alive);
//        Telemetry.registerPeriodicTelemetrySendAlive();
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_ROTORCRAFT_STATUS, send_status);
//        Telemetry.registerPeriodicTelemetrySendStatus();
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_ATTITUDE, send_attitude);
//        Telemetry.registerPeriodicTelemetrySendAttitude();
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_ENERGY, send_energy);
//        Telemetry.registerPeriodicTelemetrySendEnergy();
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_ROTORCRAFT_FP, send_fp);
//        Telemetry.registerPeriodicTelemetrySendFp();
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_ROTORCRAFT_CMD, send_rotorcraft_cmd);
//        Telemetry.registerPeriodicTelemetrySendRotorcraftCmd();
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_DL_VALUE, send_dl_value);
//        Telemetry.registerPeriodicTelemetrySendDlValue();
////        #ifdef ACTUATORS
//////  printf("ACTUATORS\n");
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_ACTUATORS, send_actuators);
//        Telemetry.registerPeriodicTelemetrySendActuators();
////        #endif
////        #ifdef RADIO_CONTROL
//////    printf("RADIO_CONTROL\n");
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_RC, send_rc);
//        Telemetry.registerPeriodicTelemetrySendRc();
////        register_periodic_telemetry(DefaultPeriodic, PPRZ_MSG_ID_ROTORCRAFT_RADIO_CONTROL, send_rotorcraft_rc);
//        Telemetry.registerPeriodicTelemetrySendRotorcraftRc();
//        #endif
    }


    public static final float NAV_PRESCALER = (PERIODIC_FREQUENCY / NAV_FREQ);

    void autopilot_periodic()
    {
//        System.out.println("autopilot_mode = "+autopilot_mode);

//        RunOnceEvery(NAV_PRESCALER, compute_dist2_to_home()); // not needed
//        Navigation.compute_dist2_to_home.runOnceEvery((int) NAV_PRESCALER);

        if (getAutopilotInFlight() && autopilot_mode == AP_MODE_NAV) {
            if (getTooFarFromHome()) {
                if (getDist2ToHome() > failsafe_mode_dist2) {
                    autopilot_set_mode(FAILSAFE_MODE_TOO_FAR_FROM_HOME);
                } else {
                    autopilot_set_mode(AP_MODE_HOME);
                }
            }
        }

        if (autopilot_mode == AP_MODE_HOME) {
//            RunOnceEvery(NAV_PRESCALER, nav_home()); // not needed
//            Navigation.nav_home.runOnceEvery((int) NAV_PRESCALER);

        } else {
            // otherwise always call nav_periodic_task so that carrot is always updated in GCS for other modes
//            RunOnceEvery(NAV_PRESCALER, nav_periodic_task());
            nav_periodic_task.runOnceEvery((int) NAV_PRESCALER);
        }


  /* If in FAILSAFE mode and either already not in_flight anymore
   * or just "detected" ground, go to KILL mode.
   */
        if (autopilot_mode == AP_MODE_FAILSAFE) {
            if (!getAutopilotInFlight()) {
                autopilot_set_mode(AP_MODE_KILL);
            }

//            #if FAILSAFE_GROUND_DETECT
//            INFO("Using FAILSAFE_GROUND_DETECT: KILL")
//            if (autopilot_ground_detected) {
//                autopilot_set_mode(AP_MODE_KILL);
//            }
//            #endif
        }

  /* Reset ground detection _after_ running flight plan
   */
        if (!getAutopilotInFlight()) {
            setAutopilotGroundDetected(false);
            setAutopilotDetectGroundOnce(false);
        }

  /* Set fixed "failsafe" commands from airframe file if in KILL mode.
   * If in FAILSAFE mode, run normal loops with failsafe attitude and
   * downwards velocity setpoints.
   */
        if (autopilot_mode == AP_MODE_KILL) {
            SetCommands(commands_failsafe);
        } else {
            boolean inFlight = getAutopilotInFlight();
//            NativeTasks.guidanceVRunJuav(inFlight);
            guidanceV.guidance_v_run(getAutopilotInFlight());
//            NativeTasks.guidanceHRunNativeTestJuav(inFlight);
            guidanceH.guidance_h_run(getAutopilotInFlight());//TODO
            NativeTasks.autopilotPeriodicPostJuav(); //->SetRotorcraftCommands(stabilization_cmd, getAutopilotInFlight(), getAutopilotMotorsOn());

//            guidanceV.guidance_v_run(getAutopilotInFlight());
//            guidanceH.guidance_h_run(getAutopilotInFlight());//TODO
//            int[] stabilization_cmd = new int[4];
//            stabilization_cmd[0] = Stabilization.getStabilizationCommand(0);
//            stabilization_cmd[1] = Stabilization.getStabilizationCommand(1);
//            stabilization_cmd[2] = Stabilization.getStabilizationCommand(2);
//            stabilization_cmd[3] = Stabilization.getStabilizationCommand(3);
////            System.out.println("Stabilzation Commands [0],[1],[2],[3] = "+stabilization_cmd[0]+", "+stabilization_cmd[1]+", "+stabilization_cmd[2]+", "+stabilization_cmd[3]+", ");
//            SetRotorcraftCommands(stabilization_cmd, getAutopilotInFlight(), getAutopilotMotorsOn());
        }

    }

    public static boolean getAutopilotMotorsOn() {
        return NativeTasks.getAutopilotMotorsOnJuav();
    }




    void autopilot_set_mode(short new_autopilot_mode)
    {
//        NativeTasks.setAutopilotModeNativeLogic(new_autopilot_mode); autopilot_mode = new_autopilot_mode;
//        System.out.println("JAVA autopilot_set_mode = " +new_autopilot_mode);

  /* force startup mode (default is kill) as long as AHRS is not aligned */
        if (!ahrs_is_aligned()) {
            new_autopilot_mode = MODE_STARTUP;
        }

        if (new_autopilot_mode != autopilot_mode) {
    /* horizontal mode */
            switch (new_autopilot_mode) {
//                case AP_MODE_FAILSAFE:
//                    #ifndef KILL_AS_FAILSAFE
//                    stabilization_attitude_set_failsafe_setpoint();
//                    guidance_h_mode_changed(GUIDANCE_H_MODE_ATTITUDE);
//                    break;
//                #endif
//                case AP_MODE_KILL:
//                    autopilot_in_flight = false;
//                    autopilot_in_flight_counter = 0;
//                    guidance_h_mode_changed(GUIDANCE_H_MODE_KILL);
//                    break;
//                case AP_MODE_RC_DIRECT:
//                    guidance_h_mode_changed(GUIDANCE_H_MODE_RC_DIRECT);
//                    break;
//                case AP_MODE_RATE_DIRECT:
//                case AP_MODE_RATE_Z_HOLD:
//                    guidance_h_mode_changed(GUIDANCE_H_MODE_RATE);
//                    break;
                case AP_MODE_ATTITUDE_RC_CLIMB:
                case AP_MODE_ATTITUDE_DIRECT:
                case AP_MODE_ATTITUDE_CLIMB:
                case AP_MODE_ATTITUDE_Z_HOLD:
                    guidanceH.guidance_h_mode_changed(GUIDANCE_H_MODE_ATTITUDE);
                    break;
//                case AP_MODE_FORWARD:
//                    guidance_h_mode_changed(GUIDANCE_H_MODE_FORWARD);
//                    break;
//                case AP_MODE_CARE_FREE_DIRECT:
//                    guidance_h_mode_changed(GUIDANCE_H_MODE_CARE_FREE);
//                    break;
//                case AP_MODE_HOVER_DIRECT:
//                case AP_MODE_HOVER_CLIMB:
//                case AP_MODE_HOVER_Z_HOLD:
//                    guidance_h_mode_changed(GUIDANCE_H_MODE_HOVER);
//                    break;
                case AP_MODE_HOME:
                case AP_MODE_NAV:
                    guidanceH.guidance_h_mode_changed(GUIDANCE_H_MODE_NAV);
                    break;
//                case AP_MODE_MODULE:
////                    #ifdef GUIDANCE_H_MODE_MODULE_SETTING
//                    guidanceH.guidance_h_mode_changed(GUIDANCE_H_MODE_MODULE_SETTING);
////                    #endif
//                    break;
//                case AP_MODE_FLIP:
//                    guidance_h_mode_changed(GUIDANCE_H_MODE_FLIP);
//                    break;
//                case AP_MODE_GUIDED:
//                    guidance_h_mode_changed(GUIDANCE_H_MODE_GUIDED);
//                    break;
                default:
                    break;
            }
    /* vertical mode */
            switch (new_autopilot_mode) {
//                case AP_MODE_FAILSAFE:
//                    #ifndef KILL_AS_FAILSAFE
//                    guidance_v_mode_changed(GUIDANCE_V_MODE_CLIMB);
//                    guidance_v_zd_sp = SPEED_BFP_OF_REAL(FAILSAFE_DESCENT_SPEED);
//                    break;
//                #endif
//                case AP_MODE_KILL:
//                    autopilot_set_motors_on(false);
//                    stabilization_cmd[COMMAND_THRUST] = 0;
//                    guidance_v_mode_changed(GUIDANCE_V_MODE_KILL);
//                    break;
                case AP_MODE_RC_DIRECT:
                case AP_MODE_RATE_DIRECT:
                case AP_MODE_ATTITUDE_DIRECT:
                case AP_MODE_HOVER_DIRECT:
                case AP_MODE_CARE_FREE_DIRECT:
                case AP_MODE_FORWARD:
                    guidanceV.guidance_v_mode_changed(GUIDANCE_V_MODE_RC_DIRECT);
                    break;
//                case AP_MODE_RATE_RC_CLIMB:
//                case AP_MODE_ATTITUDE_RC_CLIMB:
//                    guidance_v_mode_changed(GUIDANCE_V_MODE_RC_CLIMB);
//                    break;
//                case AP_MODE_ATTITUDE_CLIMB:
//                case AP_MODE_HOVER_CLIMB:
//                    guidance_v_mode_changed(GUIDANCE_V_MODE_CLIMB);
//                    break;
//                case AP_MODE_RATE_Z_HOLD:
//                case AP_MODE_ATTITUDE_Z_HOLD:
//                case AP_MODE_HOVER_Z_HOLD:
//                    guidance_v_mode_changed(GUIDANCE_V_MODE_HOVER);
//                    break;
                case AP_MODE_HOME:
                case AP_MODE_NAV:
                    guidanceV.guidance_v_mode_changed(GUIDANCE_V_MODE_NAV);
                    break;
//                case AP_MODE_MODULE:
//                    #ifdef GUIDANCE_V_MODE_MODULE_SETTING
//                    guidance_v_mode_changed(GUIDANCE_V_MODE_MODULE_SETTING);
//                    #endif
//                    break;
//                case AP_MODE_FLIP:
//                    guidance_v_mode_changed(GUIDANCE_V_MODE_FLIP);
//                    break;
//                case AP_MODE_GUIDED:
//                    guidance_v_mode_changed(GUIDANCE_V_MODE_GUIDED);
//                    break;
                default:
                    break;
            }
            autopilot_mode = new_autopilot_mode;

            NativeTasks.setAutopilotMode(new_autopilot_mode);
        }

    }

//TODO this in java
//    void autopilot_check_in_flight(boolean motors_on)
//    {
//        if (getAutopilotInFlight()) {
//            if (autopilot_in_flight_counter > 0) {
//      /* probably in_flight if thrust, speed and accel above IN_FLIGHT_MIN thresholds */
//                if ((Stabilization.getStabilizationCommand(COMMAND_THRUST) <= AUTOPILOT_IN_FLIGHT_MIN_THRUST) &&
//                        (Math.abs(stateGetSpeedNed_f().z) < AUTOPILOT_IN_FLIGHT_MIN_SPEED) &&
//                (Math.abs(stateGetAccelNed_f().z) < AUTOPILOT_IN_FLIGHT_MIN_ACCEL)) {
//                    autopilot_in_flight_counter--;
//                    if (autopilot_in_flight_counter == 0) {
//                        setAutopilotInFlight(false);
//                    }
//                } else { /* thrust, speed or accel not above min threshold, reset counter */
//                    autopilot_in_flight_counter = AUTOPILOT_IN_FLIGHT_TIME;
//                }
//            }
//        } else { /* currently not in flight */
//            if (autopilot_in_flight_counter < AUTOPILOT_IN_FLIGHT_TIME &&
//                    motors_on) {
//      /* if thrust above min threshold, assume in_flight.
//       * Don't check for velocity and acceleration above threshold here...
//       */
//                if (Stabilization.getStabilizationCommand(COMMAND_THRUST) > AUTOPILOT_IN_FLIGHT_MIN_THRUST) {
//                    autopilot_in_flight_counter++;
//                    if (autopilot_in_flight_counter == AUTOPILOT_IN_FLIGHT_TIME) {
//                        setAutopilotInFlight(true);
//                    }
//                } else { /* currently not in_flight and thrust below threshold, reset counter */
//                    autopilot_in_flight_counter = 0;
//                }
//            }
//        }
//    }

    private void setAutopilotInFlight(boolean newInFlight) {
        throw new IllegalStateException("UNIMPLEMENTED");
    }


// TODO this in java
//   void autopilot_set_motors_on(boolean motors_on)
//    {
//        if (autopilot_mode != AP_MODE_KILL && ahrs_is_aligned() && motors_on) {
//            setAutopilotMotorsOn(true);
//        } else {
//            setAutopilotMotorsOn(false);
//        }
//        kill_throttle = ! getAutopilotMotorsOn();
//        autopilot_arming_set(getAutopilotMotorsOn());
//    }

    public static void setAutopilotMotorsOn(boolean b) {
        NativeTasks.juavSetAutopilotMotorsOn(b);
    }


    public static final int THRESHOLD_1_PPRZ = (MIN_PPRZ / 2);
    public static final int THRESHOLD_2_PPRZ = (MAX_PPRZ / 2);

    /** get autopilot mode as set by RADIO_MODE 3-way switch */
    static short ap_mode_of_3way_switch()
    {
//        if (radio_control.values[RADIO_MODE] > THRESHOLD_2_PPRZ) {
        if (radio_control.getValue(RADIO_MODE) > THRESHOLD_2_PPRZ) {
            return autopilot_mode_auto2;
        }
//        else if (radio_control.values[RADIO_MODE] > THRESHOLD_1_PPRZ) {
        else if (radio_control.getValue(RADIO_MODE) > THRESHOLD_1_PPRZ) {
            return MODE_AUTO1;
        }
        else {
            return MODE_MANUAL;
        }
    }

    void autopilot_on_rc_frame()
    {

        if (kill_switch_is_on()) {
            autopilot_set_mode(AP_MODE_KILL);
        } else {
//            #ifdef RADIO_AUTO_MODE
//            INFO("Using RADIO_AUTO_MODE to switch between AUTO1 and AUTO2.")
//            uint8_t new_autopilot_mode = ap_mode_of_two_switches();
//            #else
            short new_autopilot_mode = ap_mode_of_3way_switch();
//            #endif

    /* don't enter NAV mode if GPS is lost (this also prevents mode oscillations) */
            if (!(new_autopilot_mode == AP_MODE_NAV && GpsIsLost())) {
      /* always allow to switch to manual */
                if (new_autopilot_mode == MODE_MANUAL) {
                    autopilot_set_mode(new_autopilot_mode);
                }
      /* if in HOME mode, don't allow switching to non-manual modes */
                else if ((autopilot_mode != AP_MODE_HOME)
//                #if UNLOCKED_HOME_MODE
//               /* Allowed to leave home mode when UNLOCKED_HOME_MODE */
//                        || !too_far_from_home
//                #endif
                ) {
                    autopilot_set_mode(new_autopilot_mode);
                }
            }
        }

  /* an arming sequence is used to start/stop motors.
   * only allow arming if ahrs is aligned
   */
        if (ahrs_is_aligned()) {
            autopilot_arming_check_motors_on();
            kill_throttle = ! getAutopilotMotorsOn();
        }

  /* if not in FAILSAFE or HOME mode, read RC and set commands accordingly */
        if (autopilot_mode != AP_MODE_FAILSAFE && autopilot_mode != AP_MODE_HOME) {

    /* if there are some commands that should always be set from RC, do it */
//            #ifdef SetAutoCommandsFromRC
//            SetAutoCommandsFromRC(commands, radio_control.values);
//            #endif

    /* if not in NAV_MODE set commands from the rc */
//            #ifdef SetCommandsFromRC
//            if (autopilot_mode != AP_MODE_NAV) {
//                SetCommandsFromRC(commands, radio_control.values);
//            }
//            #endif

            guidanceV.guidance_v_read_rc();
//            guidanceH.guidance_h_read_rc(getAutopilotInFlight());
        }

    }

    private static boolean GpsIsLost() {
        return false;
    }

    static boolean ahrs_is_aligned()
    {
        return State.stateIsAttitudeValid();
    }

}

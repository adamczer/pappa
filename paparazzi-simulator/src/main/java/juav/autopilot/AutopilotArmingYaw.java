package juav.autopilot;

import static juav.autopilot.Autopilot.AP_MODE_KILL;
import static juav.autopilot.Autopilot.autopilot_mode;
import static juav.autopilot.Autopilot.autopilot_motors_on;
import static juav.autopilot.AutopilotArmingYaw.arming_state.*;
import static juav.autopilot.AutopilotRcHelpers.*;

/**
 * Created by adamczer on 12/5/16.
 */
public class AutopilotArmingYaw {

    enum arming_state {
        STATUS_INITIALISE_RC,
        STATUS_MOTORS_AUTOMATICALLY_OFF,
        STATUS_MOTORS_AUTOMATICALLY_OFF_SAFETY_WAIT,
        STATUS_MOTORS_OFF,
        STATUS_M_OFF_STICK_PUSHED,
        STATUS_START_MOTORS,
        STATUS_MOTORS_ON,
        STATUS_M_ON_STICK_PUSHED,
        STATUS_STOP_MOTORS
    };

    static long autopilot_motors_on_counter;
    static arming_state autopilot_check_motor_status;
    public static final int MOTOR_ARMING_DELAY = 40;


    public static void autopilot_arming_init()
    {
        autopilot_motors_on_counter = 0;
        autopilot_check_motor_status = STATUS_INITIALISE_RC;
    }


/** Update the status of the check_motors state machine.
 */
    static void autopilot_arming_set(boolean motors_on)
    {
        if (motors_on) {
            autopilot_check_motor_status = STATUS_MOTORS_ON;
        } else {
            autopilot_check_motor_status = STATUS_MOTORS_AUTOMATICALLY_OFF;
        }
    }

/**
 * State machine to check if motors should be turned ON or OFF.
 * The motors start/stop when pushing the yaw stick without throttle until #MOTOR_ARMING_DELAY is reached.
 * An intermediate state prevents oscillating between ON and OFF while keeping the stick pushed.
 * The stick must return to a neutral position before starting/stoping again.
 */
    static void autopilot_arming_check_motors_on()
    {
  /* only allow switching motor if not in KILL mode */
        if (autopilot_mode != AP_MODE_KILL) {

            switch (autopilot_check_motor_status) {
                case STATUS_INITIALISE_RC: // Wait until RC is initialised (it being centered is a good pointer to this)
                    if (THROTTLE_STICK_DOWN() && YAW_STICK_CENTERED() && PITCH_STICK_CENTERED() && ROLL_STICK_CENTERED()) {
                        autopilot_check_motor_status = STATUS_MOTORS_OFF;
                    }
                    break;
                case STATUS_MOTORS_AUTOMATICALLY_OFF: // Motors were disarmed externally
                    //(possibly due to crash)
                    //wait extra delay before enabling the normal arming state machine
                    autopilot_motors_on = false;
                    autopilot_motors_on_counter = 0;
                    if (THROTTLE_STICK_DOWN() && YAW_STICK_CENTERED()) { // stick released
                        autopilot_check_motor_status = STATUS_MOTORS_AUTOMATICALLY_OFF_SAFETY_WAIT;
                    }
                    break;
                case STATUS_MOTORS_AUTOMATICALLY_OFF_SAFETY_WAIT:
                    autopilot_motors_on_counter++;
                    if (autopilot_motors_on_counter >= MOTOR_ARMING_DELAY) {
                        autopilot_check_motor_status = STATUS_MOTORS_OFF;
                    }
                    break;
                case STATUS_MOTORS_OFF:
                    autopilot_motors_on = false;
                    autopilot_motors_on_counter = 0;
                    if (THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED()) { // stick pushed
                        autopilot_check_motor_status = STATUS_M_OFF_STICK_PUSHED;
                    }
                    break;
                case STATUS_M_OFF_STICK_PUSHED:
                    autopilot_motors_on = false;
                    autopilot_motors_on_counter++;
                    if (autopilot_motors_on_counter >= MOTOR_ARMING_DELAY) {
                        autopilot_check_motor_status = STATUS_START_MOTORS;
                    } else if (!(THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED())) { // stick released too soon
                        autopilot_check_motor_status = STATUS_MOTORS_OFF;
                    }
                    break;
                case STATUS_START_MOTORS:
                    autopilot_motors_on = true;
                    autopilot_motors_on_counter = MOTOR_ARMING_DELAY;
                    if (!(THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED())) { // wait until stick released
                        autopilot_check_motor_status = STATUS_MOTORS_ON;
                    }
                    break;
                case STATUS_MOTORS_ON:
                    autopilot_motors_on = true;
                    autopilot_motors_on_counter = MOTOR_ARMING_DELAY;
                    if (THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED()) { // stick pushed
                        autopilot_check_motor_status = STATUS_M_ON_STICK_PUSHED;
                    }
                    break;
                case STATUS_M_ON_STICK_PUSHED:
                    autopilot_motors_on = true;
                    autopilot_motors_on_counter--;
                    if (autopilot_motors_on_counter == 0) {
                        autopilot_check_motor_status = STATUS_STOP_MOTORS;
                    } else if (!(THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED())) { // stick released too soon
                        autopilot_check_motor_status = STATUS_MOTORS_ON;
                    }
                    break;
                case STATUS_STOP_MOTORS:
                    autopilot_motors_on = false;
                    autopilot_motors_on_counter = 0;
                    if (!(THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED())) { // wait until stick released
                        autopilot_check_motor_status = STATUS_MOTORS_OFF;
                    }
                    break;
                default:
                    break;
            }
        }
    }

}

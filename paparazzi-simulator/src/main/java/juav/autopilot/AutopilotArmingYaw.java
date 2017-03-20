package juav.autopilot;

import juav.logging.JiveStateLog;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;

import static juav.autopilot.Autopilot.*;
import static juav.autopilot.AutopilotArmingYaw.arming_state.*;
import static juav.autopilot.AutopilotRcHelpers.*;

/**
 * Created by adamczer on 12/5/16.
 */
public class AutopilotArmingYaw {

    public enum arming_state {
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

//    static long autopilot_motors_on_counter;
//    static arming_state autopilot_check_motor_status;
    public static final int MOTOR_ARMING_DELAY = 40;


    public static void autopilot_arming_init()
    {
//        autopilot_motors_on_counter = 0;
//        autopilot_check_motor_status = STATUS_INITIALISE_RC;
    }


/** Update the status of the check_motors state machine.
 */
    static void autopilot_arming_set(boolean motors_on)
    {
        if (motors_on) {
            setAutopilotCheckMotorStatus(STATUS_MOTORS_ON);
        } else {
            setAutopilotCheckMotorStatus(STATUS_MOTORS_AUTOMATICALLY_OFF);
        }
    }

    static void setAutopilotCheckMotorStatus(arming_state newValue) {
        JiveStateLog.setMotorStatus(newValue);
        NativeTasksWrapper.juavSetAutopilotCheckMotorStatus(newValue.ordinal());
    }

    static arming_state getAutopilotCheckMotorStatus() {
        int tmp = NativeTasksWrapper.juavGetAutopilotCheckMotorStatus();
        switch (tmp) {
            case 0:
                return STATUS_INITIALISE_RC;
            case 1:
                return STATUS_MOTORS_AUTOMATICALLY_OFF;
            case 2:
                return STATUS_MOTORS_AUTOMATICALLY_OFF_SAFETY_WAIT;
            case 3:
                return STATUS_MOTORS_OFF;
            case 4:
                return STATUS_M_OFF_STICK_PUSHED;
            case 5:
                return STATUS_START_MOTORS;
            case 6:
                return STATUS_MOTORS_ON;
            case 7:
                return STATUS_M_ON_STICK_PUSHED;
            case 8:
                return STATUS_STOP_MOTORS;
            default:
                throw new IllegalStateException("unsupported enum val");
        }
    }

    static void setAutopilotMotorsOnCounter(int newCount) {
        NativeTasksWrapper.juavSetAutopilotMotorsOnCounter(newCount);
    }
    static int getAutopilotMotorsOnCounter() {
        return NativeTasksWrapper.juavGetAutopilotMotorsOnCounter();
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

            switch (getAutopilotCheckMotorStatus()) {
                case STATUS_INITIALISE_RC: // Wait until RC is initialised (it being centered is a good pointer to this)
                    if (THROTTLE_STICK_DOWN() && YAW_STICK_CENTERED() && PITCH_STICK_CENTERED() && ROLL_STICK_CENTERED()) {
                        setAutopilotCheckMotorStatus(STATUS_MOTORS_OFF);
                    }
                    break;
                case STATUS_MOTORS_AUTOMATICALLY_OFF: // Motors were disarmed externally
                    //(possibly due to crash)
                    //wait extra delay before enabling the normal arming state machine
                    setAutopilotMotorsOn(false);
                    setAutopilotMotorsOnCounter(0);
                    if (THROTTLE_STICK_DOWN() && YAW_STICK_CENTERED()) { // stick released
                        setAutopilotCheckMotorStatus(STATUS_MOTORS_AUTOMATICALLY_OFF_SAFETY_WAIT);
                    }
                    break;
                case STATUS_MOTORS_AUTOMATICALLY_OFF_SAFETY_WAIT:
                    setAutopilotMotorsOnCounter(getAutopilotMotorsOnCounter()+1);
                    if (getAutopilotMotorsOnCounter() >= MOTOR_ARMING_DELAY) {
                        setAutopilotCheckMotorStatus(STATUS_MOTORS_OFF);
                    }
                    break;
                case STATUS_MOTORS_OFF:
                    setAutopilotMotorsOn(false);
                    setAutopilotMotorsOnCounter(0);
                    if (THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED()) { // stick pushed
                        setAutopilotCheckMotorStatus(STATUS_M_OFF_STICK_PUSHED);
                    }
                    break;
                case STATUS_M_OFF_STICK_PUSHED:
                    setAutopilotMotorsOn(false);
                    setAutopilotMotorsOnCounter(getAutopilotMotorsOnCounter()+1);
                    if (getAutopilotMotorsOnCounter() >= MOTOR_ARMING_DELAY) {
                        setAutopilotCheckMotorStatus(STATUS_START_MOTORS);
                    } else if (!(THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED())) { // stick released too soon
                        setAutopilotCheckMotorStatus(STATUS_MOTORS_OFF);
                    }
                    break;
                case STATUS_START_MOTORS:
                    setAutopilotMotorsOn(true);
                    setAutopilotMotorsOnCounter(MOTOR_ARMING_DELAY);
                    if (!(THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED())) { // wait until stick released
                        setAutopilotCheckMotorStatus(STATUS_MOTORS_ON);
                    }
                    break;
                case STATUS_MOTORS_ON:
                    setAutopilotMotorsOn(true);
                    setAutopilotMotorsOnCounter(MOTOR_ARMING_DELAY);
                    if (THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED()) { // stick pushed
                        setAutopilotCheckMotorStatus(STATUS_M_ON_STICK_PUSHED);
                    }
                    break;
                case STATUS_M_ON_STICK_PUSHED:
                    setAutopilotMotorsOn(true);
                    setAutopilotMotorsOnCounter(getAutopilotMotorsOnCounter()-1);
                    if (getAutopilotMotorsOnCounter() == 0) {
                        setAutopilotCheckMotorStatus(STATUS_STOP_MOTORS);
                    } else if (!(THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED())) { // stick released too soon
                        setAutopilotCheckMotorStatus(STATUS_MOTORS_ON);
                    }
                    break;
                case STATUS_STOP_MOTORS:
                    setAutopilotMotorsOn(false);
                    setAutopilotMotorsOnCounter(0);
                    if (!(THROTTLE_STICK_DOWN() && YAW_STICK_PUSHED())) { // wait until stick released
                        setAutopilotCheckMotorStatus(STATUS_MOTORS_OFF);
                    }
                    break;
                default:
                    break;
            }
        }
    }

}

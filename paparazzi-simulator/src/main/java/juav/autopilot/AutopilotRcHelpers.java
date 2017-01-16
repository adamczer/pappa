package juav.autopilot;

import static juav.autopilot.guidance.GuidanceH.MAX_PPRZ;
import static juav.autopilot.radiocontrol.RadioControl.*;

/**
 * Created by adamczer on 12/5/16.
 */
public class AutopilotRcHelpers {
    public static final int AUTOPILOT_THROTTLE_THRESHOLD = (MAX_PPRZ / 20);
    public static final int AUTOPILOT_YAW_THRESHOLD = (MAX_PPRZ * 19 / 20);
    public static final int AUTOPILOT_STICK_CENTER_THRESHOLD = (MAX_PPRZ * 1 / 20);


    public static boolean THROTTLE_STICK_DOWN() {
        return (radio_control.getValue(RADIO_THROTTLE) < AUTOPILOT_THROTTLE_THRESHOLD);
    }

    public static boolean YAW_STICK_PUSHED() {
        return (radio_control.getValue(RADIO_YAW) > AUTOPILOT_YAW_THRESHOLD ||
                radio_control.getValue(RADIO_YAW) < -AUTOPILOT_YAW_THRESHOLD);
    }

    public static boolean YAW_STICK_CENTERED() {


        return (radio_control.getValue(RADIO_YAW) < AUTOPILOT_STICK_CENTER_THRESHOLD &&
                radio_control.getValue(RADIO_YAW) > -AUTOPILOT_STICK_CENTER_THRESHOLD);
    }

    public static boolean PITCH_STICK_CENTERED() {
        return (radio_control.getValue(RADIO_PITCH) < AUTOPILOT_STICK_CENTER_THRESHOLD &&
                radio_control.getValue(RADIO_PITCH) > -AUTOPILOT_STICK_CENTER_THRESHOLD);
    }

    public static boolean ROLL_STICK_CENTERED() {
        return (radio_control.getValue(RADIO_ROLL) < AUTOPILOT_STICK_CENTER_THRESHOLD &&
                radio_control.getValue(RADIO_ROLL) > -AUTOPILOT_STICK_CENTER_THRESHOLD);
    }

    static boolean rc_attitude_sticks_centered() {
        return ROLL_STICK_CENTERED() && PITCH_STICK_CENTERED() && YAW_STICK_CENTERED();
    }

//    #ifdef RADIO_KILL_SWITCH
//    static inline bool_t
//
//    kill_switch_is_on(void) {
//        if (radio_control.values[RADIO_KILL_SWITCH] < 0) {
//            return TRUE;
//        } else {
//            return FALSE;
//        }
//    }
//
//    #else
    public static boolean kill_switch_is_on() {
        return false;
    }
//    #endif

    public static int percent_from_rc(int channel) {
        int per = (MAX_PPRZ + radio_control.getValue(channel)) * 50 / MAX_PPRZ;
        if (per < 0) {
            per = 0;
        } else if (per > 100) {
            per = 100;
        }
        return per;
    }
}

package juav.autopilot.radiocontrol;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;

/**
 * Created by adamczer on 11/6/16.
 */
public class RadioControl {
    public static final int RADIO_THROTTLE = 0;
    public static final int RADIO_ROLL = 1;
    public static final int RADIO_PITCH = 2;
    public static final int RADIO_YAW = 3;
    public static final int RADIO_GEAR = 4;
    public static final int RADIO_FLAP = 5;
    public static final int RADIO_AUX1 = 5;
    public static final int RADIO_AUX2 = 6;
    public static final int RADIO_AUX3 = 7;
    public static final int RADIO_AUX4 = 8;
    public static final int RADIO_AUX5 = 9;
    public static final int RADIO_AUX6 = 10;
    public static final int RADIO_AUX7 = 11;
    public static final int RADIO_CONTROL_NB_CHANNEL = 12;

    public static final int RADIO_MODE    =   5;

    public static final int RC_OK = 0;
    public static final int RC_LOST =      1;
    public static final int RC_REALLY_LOST = 2;

//    public static short status=0;
    public static short time_since_last_frame=0;
    public static short radio_ok_cpt=0;
    public static short frame_rate=0;
    public static short frame_cpt=0;
//    public int[] values= new int[RADIO_CONTROL_NB_CHANNEL];

    public static RadioControl radio_control = new RadioControl();

    public int getValue(int index) {
        int val = NativeTasksWrapper.getRadioControlValue(index);
        return val;
    }

    public short getStatus() {
        short rcStatus = NativeTasksWrapper.getRadioControlStatus();
//        System.out.println("radioControl.status = "+rcStatus);
        return rcStatus;
    }
}

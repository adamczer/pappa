package ub.cse.juav.jni.imu;

import ub.cse.juav.fijiorjni.NativeSwitch;

/**
 * Created by adamczer on 2/20/17.
 */
public class ImuWrapper {
    public static void imuFeedGyro(double x, double y, double z) {
        if(NativeSwitch.isFiji()) {
            ImuFiji.imuFeedGyro(x, y, z);
        } else {
            ImuNative.imuFeedGyro(x, y, z);
        }
    }
    public static void imuFeedAccel(double x, double y, double z) {
        if(NativeSwitch.isFiji()) {
            ImuFiji.imuFeedAccel(x, y, z);
        } else {
            ImuNative.imuFeedAccel(x, y, z);
        }
    }
    public static void imuFeedMag(double x, double y, double z) {
        if(NativeSwitch.isFiji()) {
            ImuFiji.imuFeedMag(x, y, z);
        } else {
            ImuNative.imuFeedMag(x, y, z);
        }
    }
}

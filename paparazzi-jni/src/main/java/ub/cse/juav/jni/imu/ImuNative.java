package ub.cse.juav.jni.imu;

/**
 * Created by adamczer on 6/1/16.
 */
public class ImuNative {
    public static native void imuFeedGyro(double x, double y, double z);
    public static native void imuFeedAccel(double x, double y, double z);
    public static native void imuFeedMag(double x, double y, double z);
}

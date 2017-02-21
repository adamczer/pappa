package ub.cse.juav.jni.imu;

import com.fiji.fivm.r1.GodGiven;
import com.fiji.fivm.r1.Import;

/**
 * Created by adamczer on 2/20/17.
 */
public class ImuFiji {
    public static void imuFeedGyro(double x, double y, double z) {
        imu_feed_gyro_juav(x,y,z);
    }
    @Import
    @GodGiven
    private static native void imu_feed_gyro_juav(double x, double y, double z);

    public static void imuFeedAccel(double x, double y, double z) {
        imu_feed_accel_juav(x,y,z);
    }
    @Import
    @GodGiven
    private static native void imu_feed_accel_juav(double x, double y, double z);

    public static void imuFeedMag(double x, double y, double z) {
        imu_feed_mag_juav(x,y,z);
    }
    @Import
    @GodGiven
    private static native void imu_feed_mag_juav(double x, double y, double z);
}

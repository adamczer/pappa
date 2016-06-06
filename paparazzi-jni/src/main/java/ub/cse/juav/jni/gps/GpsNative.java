package ub.cse.juav.jni.gps;

/**
 * Created by adamczer on 6/1/16.
 */
public class GpsNative {
    public static native void gps_feed_value_week_juav(int week);
    public static native void gps_feed_value_tow_juav(double time);
    public static native void gps_feed_value_ecef_pos_juav(double ecef_pos_x, double ecef_pos_y, double ecef_pos_z);
    public static native void gps_feed_value_ecef_vel_juav(double ecef_vel_x, double ecef_vel_y, double ecef_vel_z);
    public static native void gps_feed_value_lla_pos_juav(double lla_pos_lat, double lla_pos_lon, double lla_pos_alt);
    public static native void gps_feed_value_hmsl_juav(double hmsl);
    public static native void gps_feed_value_ned_speed(double ned_vel_x, double ned_vel_y, double ned_vel_z);
    public static native void gps_feed_value_finalize_juav();
    public static native void gps_feed_latency_speed_juav(double time,double x,double y, double z);
    public static native void gps_feed_latency_pos_juav(double time,double x,double y, double z);
    public static native void gps_feed_latency_lla_juav(double time,double lat,double lon, double alt);
    public static native void gps_feed_latency_hmsl_juav(double time,double hmsl);
    public static native void gps_feed_all_data_juav(int week, double time, double ecef_pos_x, double ecef_pos_y, double ecef_pos_z,
                                                     double ecef_vel_x, double ecef_vel_y, double ecef_vel_z,
                                                     double lla_pos_lat, double lla_pos_lon, double lla_pos_alt,
                                                     double hmsl);

    public static native double gps_get_selected_value_speed_juav();

}

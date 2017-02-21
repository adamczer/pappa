package ub.cse.juav.jni.gps;

import com.fiji.fivm.r1.GodGiven;
import com.fiji.fivm.r1.Import;

/**
 * Created by adamczer on 2/20/17.
 */
public class GpsNativeFiji {
    public static void gps_feed_value_week_juav_fun(int week) {
        gps_feed_value_week_juav(week);
    }
    @Import
    @GodGiven
    private static native void gps_feed_value_week_juav(int week);

    public static void gps_feed_value_tow_juav_fun(double time) {
        gps_feed_value_tow_juav(time);
    }
    @Import
    @GodGiven
    private static native void gps_feed_value_tow_juav(double time);

    public static void gps_feed_value_ecef_pos_juav_fun(double ecef_pos_x, double ecef_pos_y, double ecef_pos_z) {
        gps_feed_value_ecef_pos_juav(ecef_pos_x, ecef_pos_y, ecef_pos_z);
    }
    @Import
    @GodGiven
    private static native void gps_feed_value_ecef_pos_juav(double x,double y,double z);

    public static void gps_feed_value_ecef_vel_juav_fun(double ecef_vel_x, double ecef_vel_y, double ecef_vel_z) {
        gps_feed_value_ecef_vel_juav(ecef_vel_x,ecef_vel_y,ecef_vel_z);
    }
    @Import
    @GodGiven
    private static native void gps_feed_value_ecef_vel_juav(double ecef_vel_x, double ecef_vel_y, double ecef_vel_z);

    public static void gps_feed_value_lla_pos_juav_fun(double lla_pos_lat, double lla_pos_lon, double lla_pos_alt) {
        gps_feed_value_lla_pos_juav(lla_pos_lat,lla_pos_lon,lla_pos_alt);
    }
    @Import
    @GodGiven
    private static native void gps_feed_value_lla_pos_juav(double lat,double lon,double alt);

    public static void gps_feed_value_hmsl_juav_fun(double hmsl) {
        gps_feed_value_hmsl_juav(hmsl);
    }
    @Import
    @GodGiven
    private static native void gps_feed_value_hmsl_juav(double hmsl);

    public static void gps_feed_value_ned_speed_fun(double ned_vel_x, double ned_vel_y, double ned_vel_z) {
        gps_feed_value_ned_speed(ned_vel_x,ned_vel_y,ned_vel_z);
    }
    @Import
    @GodGiven
    private static native void gps_feed_value_ned_speed(double ned_vel_x, double ned_vel_y, double ned_vel_z);

    public static void gps_feed_value_finalize_juav_fun() {
        gps_feed_value_finalize_juav();
    }
    @Import
    @GodGiven
    private static native void gps_feed_value_finalize_juav();

    public static void gps_feed_latency_speed_juav(double time,double x,double y, double z) {
        UpdateSensorLatencySpeedJuav(time, x, y, z);
    }
    @Import
    @GodGiven
    private static native void UpdateSensorLatencySpeedJuav(double time, double x, double y, double z);

    public static void gps_feed_latency_pos_juav(double time,double x,double y, double z) {
        UpdateSensorLatencyPosJuav( time, x, y, z);
    }
    @Import
    @GodGiven
    private static native void UpdateSensorLatencyPosJuav(double time, double x, double y, double z);

    public static void gps_feed_latency_lla_juav(double time,double lat,double lon, double alt) {
        UpdateSensorLatencyLlaJuav( time, lat, lon, alt);
    }
    @Import
    @GodGiven
    private static native void UpdateSensorLatencyLlaJuav(double time, double lat, double lon, double alt);

    public static void gps_feed_latency_hmsl_juav(double time,double hmsl) {
        UpdateSensorLatency_Single_Hmsl(time, hmsl);
    }
    @Import
    @GodGiven
    private static native void UpdateSensorLatency_Single_Hmsl(double time, double hmsl);

    public static void gps_feed_all_data_juav(int week, double time, double ecef_pos_x, double ecef_pos_y, double ecef_pos_z,
                                              double ecef_vel_x, double ecef_vel_y, double ecef_vel_z,
                                              double lla_pos_lat, double lla_pos_lon, double lla_pos_alt,
                                              double hmsl) {
        gps_feed_value_juav( week,  time,  ecef_pos_x,  ecef_pos_y,  ecef_pos_z,
                ecef_vel_x,  ecef_vel_y,  ecef_vel_z,
                lla_pos_lat,  lla_pos_lon,  lla_pos_alt,
                hmsl
        );
    }
    @Import
    @GodGiven
    private static native void gps_feed_value_juav(int week, double time, double ecef_pos_x, double ecef_pos_y, double ecef_pos_z, double ecef_vel_x, double ecef_vel_y, double ecef_vel_z, double lla_pos_lat, double lla_pos_lon, double lla_pos_alt, double hmsl);

    public static double gps_get_selected_value_speed_juav() {
        return 0; //UNUSED
    }
}

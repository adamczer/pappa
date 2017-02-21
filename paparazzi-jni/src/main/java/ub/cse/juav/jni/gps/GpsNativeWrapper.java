package ub.cse.juav.jni.gps;

import ub.cse.juav.fijiorjni.NativeSwitch;

/**
 * Created by adamczer on 2/20/17.
 */
public class GpsNativeWrapper {
    public static void gps_feed_value_week_juav(int week) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_value_week_juav_fun(week);
        } else {
            GpsNative.gps_feed_value_week_juav(week);
        }
    }
    public static void gps_feed_value_tow_juav(double time) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_value_tow_juav_fun(time);
        } else {
            GpsNative.gps_feed_value_tow_juav(time);
        }
    }
    public static void gps_feed_value_ecef_pos_juav(double ecef_pos_x, double ecef_pos_y, double ecef_pos_z) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_value_ecef_pos_juav_fun(ecef_pos_x, ecef_pos_y, ecef_pos_z);
        } else {
            GpsNative.gps_feed_value_ecef_pos_juav(ecef_pos_x, ecef_pos_y, ecef_pos_z);
        }
    }
    public static void gps_feed_value_ecef_vel_juav(double ecef_vel_x, double ecef_vel_y, double ecef_vel_z) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_value_ecef_vel_juav_fun(ecef_vel_x, ecef_vel_y, ecef_vel_z);
        } else {
            GpsNative.gps_feed_value_ecef_vel_juav(ecef_vel_x, ecef_vel_y, ecef_vel_z);
        }
    }
    public static void gps_feed_value_lla_pos_juav(double lla_pos_lat, double lla_pos_lon, double lla_pos_alt) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_value_lla_pos_juav_fun(lla_pos_lat, lla_pos_lon, lla_pos_alt);
        } else {
            GpsNative.gps_feed_value_lla_pos_juav(lla_pos_lat, lla_pos_lon, lla_pos_alt);
        }
    }
    public static void gps_feed_value_hmsl_juav(double hmsl) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_value_hmsl_juav_fun(hmsl);
        } else {
            GpsNative.gps_feed_value_hmsl_juav(hmsl);
        }
    }
    public static void gps_feed_value_ned_speed(double ned_vel_x, double ned_vel_y, double ned_vel_z) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_value_ned_speed_fun(ned_vel_x, ned_vel_y, ned_vel_z);
        } else {
            GpsNative.gps_feed_value_ned_speed(ned_vel_x, ned_vel_y, ned_vel_z);
        }
    }
    public static void gps_feed_value_finalize_juav() {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_value_finalize_juav_fun();
        } else {
            GpsNative.gps_feed_value_finalize_juav();
        }
    }
    public static void gps_feed_latency_speed_juav(double time,double x,double y, double z) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_latency_speed_juav(time, x, y, z);
        } else {
            GpsNative.gps_feed_latency_speed_juav(time, x, y, z);
        }
    }
    public static void gps_feed_latency_pos_juav(double time,double x,double y, double z) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_latency_pos_juav(time, x, y, z);
        } else {
            GpsNative.gps_feed_latency_pos_juav(time, x, y, z);
        }
    }
    public static void gps_feed_latency_lla_juav(double time,double lat,double lon, double alt) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_latency_lla_juav(time, lat, lon, alt);
        } else {
            GpsNative.gps_feed_latency_lla_juav(time, lat, lon, alt);
        }
    }
    public static void gps_feed_latency_hmsl_juav(double time,double hmsl) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_latency_hmsl_juav(time, hmsl);
        } else {
            GpsNative.gps_feed_latency_hmsl_juav(time, hmsl);
        }
    }
    public static void gps_feed_all_data_juav(int week, double time, double ecef_pos_x, double ecef_pos_y, double ecef_pos_z,
                                                     double ecef_vel_x, double ecef_vel_y, double ecef_vel_z,
                                                     double lla_pos_lat, double lla_pos_lon, double lla_pos_alt,
                                                     double hmsl) {
        if(NativeSwitch.isFiji()) {
            GpsNativeFiji.gps_feed_all_data_juav(week, time, ecef_pos_x, ecef_pos_y, ecef_pos_z, ecef_vel_x, ecef_vel_y, ecef_vel_z, lla_pos_lat, lla_pos_lon, lla_pos_alt, hmsl);
        } else {
            GpsNative.gps_feed_all_data_juav(week, time, ecef_pos_x, ecef_pos_y, ecef_pos_z, ecef_vel_x, ecef_vel_y, ecef_vel_z, lla_pos_lat, lla_pos_lon, lla_pos_alt, hmsl);
        }
    }

    public static double gps_get_selected_value_speed_juav() {
        if(NativeSwitch.isFiji()) {
            return GpsNativeFiji.gps_get_selected_value_speed_juav();
        } else {
            return GpsNative.gps_get_selected_value_speed_juav();
        }
    }
}

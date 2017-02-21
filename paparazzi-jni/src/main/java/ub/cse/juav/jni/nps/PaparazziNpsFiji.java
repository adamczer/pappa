package ub.cse.juav.jni.nps;

import com.fiji.fivm.r1.GodGiven;
import com.fiji.fivm.r1.Import;

/**
 * Created by adamczer on 2/19/17.
 */
public class PaparazziNpsFiji {

    public static void npsInit() {
        nps_main_parse_options_juav();
        nps_main_init_juav();
    }
    @Import
    @GodGiven
    private static native void nps_main_parse_options_juav();
    @Import
    @GodGiven
    private static native void nps_main_init_juav();

    public static void npsMainPeriodicJuavNative() {
        nps_main_periodic_juav_native();
    }
    @Import
    @GodGiven
    private static native void nps_main_periodic_juav_native();

    public static void npsMainDisplay() {
        nps_main_display_juav();
    }
    @Import
    @GodGiven
    private static native void nps_main_display_juav();

    public static void setNpsMainSimTime(double npsMainSimTime) {
        set_nps_sim_time_juav(npsMainSimTime);
    }
    @Import
    @GodGiven
    private static native void set_nps_sim_time_juav(double npsMainSimTime);

    public static double getNpsMainSimTime() {
        return get_nps_sim_time_juav();
    }
    @Import
    @GodGiven
    private static native double get_nps_sim_time_juav();

    public static double getNpsMainHostTimeElapsed() {
        return get_nps_host_time_elapsed_juav();
    }
    @Import
    @GodGiven
    protected static native double get_nps_host_time_elapsed_juav();

    public static double getNpsMainSimDt() {
        return get_sim_dt_juav();
    }
    @Import
    @GodGiven
    private static native double get_sim_dt_juav();

    public static double getNpsMainRealInitialTime() {
        return get_nps_main_real_initial_time_juav();
    }
    @Import
    @GodGiven
    private static native double get_nps_main_real_initial_time_juav();

    public static double getHostTimeNow() {
        return get_nps_host_time_now_juav();
    }
    @Import
    @GodGiven
    private static native double get_nps_host_time_now_juav();

    public static double getNpsMainDisplayTime() {
        return get_nps_display_time_juav();
    }
    @Import
    @GodGiven
    private static native double get_nps_display_time_juav();

    public static double getNpsMainDisplayDt() {
        return get_display_dt_juav();
    }
    @Import
    @GodGiven
    private static native double get_display_dt_juav();

    public static void setNpsMainDisplayTime(double newDisplayTime) {
        set_nps_display_time_juav(newDisplayTime);
    }
    @Import
    @GodGiven
    private static native void set_nps_display_time_juav(double newDisplayTime);

    public static void mainEvent() {
        main_event_juav();
    }
    @Import
    @GodGiven
    private static native void main_event_juav();

    public static void mainEventPrior() {
        main_event_juav_prior();
    }
    @Import
    @GodGiven
    private static native void main_event_juav_prior();

    public static void mainEventPost() {
        main_event_juav_post();
    }
    @Import
    @GodGiven
    private static native void main_event_juav_post();
}

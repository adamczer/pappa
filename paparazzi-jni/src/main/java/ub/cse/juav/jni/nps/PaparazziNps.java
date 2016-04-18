package ub.cse.juav.jni.nps;

/**
 * Created by adamczer on 4/9/16.
 */
public class PaparazziNps {
    public static native void npsInit();

    public static native void npsMainPeriodicJuavNative();

    public static native void npsMainDisplay();

    public static native void setNpsMainSimTime(double newSimTime);

    public static native double getNpsMainSimTime();

    public static native double getNpsMainHostTimeElapsed();

    public static native double getNpsMainSimDt();

    public static native double getNpsMainRealInitialTime();

    public static native double getHostTimeNow();

    public static native double getNpsMainDisplayTime();

    public static native double getNpsMainDisplayDt();

    public static native void setNpsMainDisplayTime(double newDisplayTime);


}

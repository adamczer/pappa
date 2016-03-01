package ub.cse.juav.sensor.polls;

/**
 * Created by adamczer on 2/29/16.
 * These functions return a json representation of the
 * sensor similar to that of the struct
 *  {
 *      "key":value,
 *      "key2":"strVal"
 *  }
 */

public class SensorPolls {
    public static native String pollGyro();
    public static native String pollAccelerometer();
    public static native String pollMagnetic();
    public static native String pollBarometer();
    public static native String pollGps();
    public static native String pollSonar();
}

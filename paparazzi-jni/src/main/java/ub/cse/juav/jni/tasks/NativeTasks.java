package ub.cse.juav.jni.tasks;

/**
 * Created by adamczer on 4/17/16.
 */
public class NativeTasks {
    public static native void atmosphereUpdate();
    public static native void autoPilotRunSystimeStep();
    public static native void fdmRunStep();
    public static native void sensorsRunStep(double simTime);
    public static native void autoPilotRunStep(double simTime);
}

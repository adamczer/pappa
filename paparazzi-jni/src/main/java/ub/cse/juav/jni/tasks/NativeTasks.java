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
    public static native void npsAutopilotRunStepRadio(double simTime);
    public static native void npsAutopilotRunStepOverwriteIns();
    public static native void npsAutopilotRunStepOverwriteAhrs();
    public static native void npsAutopilotRunStepHandelPeriodicTasks();
    public static native void npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();

    public static native void npsElectricalRunStep(double time);
}

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
    public static native void sendBarometricReading(float pressure);

    public static native void npsSensorInitGyro(double time);
    public static native void npsSensorInitAccel(double time);
    public static native void npsSensorInitMag(double time);
    public static native void npsSensorInitBaro(double time);
    public static native void npsSensorInitGps(double time);

    public static native void npsSensorFdmCopyGyro(double time);
    public static native void npsSensorFdmCopyAccel(double time);
    public static native void npsSensorFdmCopyMag(double time);
    public static native void npsSensorFdmCopyBaro(double time);
    public static native void npsSensorFdmCopyGps(double time);

    public static native void npsSensorFeedStepGyro();
    public static native void npsSensorFeedStepAccel();
    public static native void npsSensorFeedStepMag();
    public static native void npsSensorFeedStepBaro();
    public static native void npsSensorFeedStepGps();



    public static native void mainPeriodicJuavAutopilotPrior();
    public static native boolean sysTimeCheckAndAckTimerMainPeriodicJuav();
    public static native void handlePeriodicTasksFollowingMainPeriodicJuav() ;

    public static native void autopilotPeriodicPriorJuav();

    public static native boolean isAutopilotModeApModeKillJuav();

    public static native void autopilotPeriodicPostJuav();

    public static native void guidanceHRunJuav(boolean inFlight);

    public static native boolean getAutopilotInFlightJuav();

    public static native boolean runStabilizationAttitudeRunJuav();

    public static native void guidanceHRunNativeTestJuav(boolean inFlight);

    public static native void mainPeriodicJuavAutopilotPost();

    public static native void mainPeriodicJuavTest();
}

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

    public static native int stateGetNedToBodyQuatIQi();
    public static native int stateGetNedToBodyQuatIQx();
    public static native int stateGetNedToBodyQuatIQy();
    public static native int stateGetNedToBodyQuatIQz();

    public static native int stateGetBodyRatesIP();
    public static native int stateGetBodyRatesIQ();
    public static native int stateGetBodyRatesIR();

    public static native void attitudeRefQuatIntUpdateJuav(float dt);

    public static native int getStabilizationAttSumErrQuatQi();
    public static native int getStabilizationAttSumErrQuatQx();
    public static native int getStabilizationAttSumErrQuatQy();
    public static native int getStabilizationAttSumErrQuatQz();

//    public static native int getAttitudeRefEulerPsi();
//    public static native int getAttitudeRefEulerPhi();
//    public static native int getAttitudeRefEulerTheta();

    public static native int getAttitudeRefQuatQi();
    public static native int getAttitudeRefQuatQx();
    public static native int getAttitudeRefQuatQy();
    public static native int getAttitudeRefQuatQz();

    public static native int getAttitudeRefRateP();
    public static native int getAttitudeRefRateQ();
    public static native int getAttitudeRefRateR();

    public static native int getAttitudeGainPX();
    public static native int getAttitudeGainPY();
    public static native int getAttitudeGainPZ();
    public static native int getAttitudeGainDX();
    public static native int getAttitudeGainDY();
    public static native int getAttitudeGainDZ();
    public static native int getAttitudeGainDdX();
    public static native int getAttitudeGainDdY();
    public static native int getAttitudeGainDdZ();
    public static native int getAttitudeGainIX();
    public static native int getAttitudeGainIY();
    public static native int getAttitudeGainIZ();

    public static native int getAttitudeRefAccelP();
    public static native int getAttitudeRefAccelQ();
    public static native int getAttitudeRefAccelR();

    public static native int setStabilizationAttSumErrQuatQi(int qi);
    public static native int setStabilizationAttSumErrQuatQx(int qx);
    public static native int setStabilizationAttSumErrQuatQy(int qy);
    public static native int setStabilizationAttSumErrQuatQz(int qz);

    public static native void setAttRefQuatIQuatQi(int qi);
    public static native void setAttRefQuatIQuatQx(int qx);
    public static native void setAttRefQuatIQuatQy(int qy);
    public static native void setAttRefQuatIQuatQz(int qz);

    public static native void setAttRefQuatIRateP(int p);
    public static native void setAttRefQuatIRateQ(int q);
    public static native void setAttRefQuatIRateR(int r);

    public static native void setAttRefQuatIAccelP(int p);
    public static native void setAttRefQuatIAccelQ(int q);
    public static native void setAttRefQuatIAccelR(int r);

    public static native void setStabilizationCommands(int yaw,int pitch, int roll);
}

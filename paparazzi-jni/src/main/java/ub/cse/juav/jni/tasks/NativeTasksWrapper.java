package ub.cse.juav.jni.tasks;

import ub.cse.juav.fijiorjni.NativeSwitch;

/**
 * Created by adamczer on 2/20/17.
 */
public class NativeTasksWrapper {
    public static void atmosphereUpdate() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.atmosphereUpdate();
        } else {
            NativeTasks.atmosphereUpdate();
        }
    }

    public static void autoPilotRunSystimeStep() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.autoPilotRunSystimeStep();
        } else {
            NativeTasks.autoPilotRunSystimeStep();
        }
    }

    public static void fdmRunStep() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.fdmRunStep();
        } else {
            NativeTasks.fdmRunStep();
        }
    }

    public static void sensorsRunStep(double simTime) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.sensorsRunStep(simTime);
        } else {
            NativeTasks.sensorsRunStep(simTime);
        }
    }

    public static void autoPilotRunStep(double simTime) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.autoPilotRunStep(simTime);
        } else {
            NativeTasks.autoPilotRunStep(simTime);
        }

    }

    public static void npsAutopilotRunStepRadio(double simTime) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsAutopilotRunStepRadio(simTime);
        } else {
            NativeTasks.npsAutopilotRunStepRadio(simTime);
        }

    }

    public static void npsAutopilotRunStepOverwriteIns() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsAutopilotRunStepOverwriteIns();
        } else {
            NativeTasks.npsAutopilotRunStepOverwriteIns();
        }

    }

    public static void npsAutopilotRunStepOverwriteAhrs() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsAutopilotRunStepOverwriteAhrs();
        } else {
            NativeTasks.npsAutopilotRunStepOverwriteAhrs();
        }
    }

    public static void npsAutopilotRunStepHandelPeriodicTasks() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsAutopilotRunStepOverwriteAhrs();
        } else {
            NativeTasks.npsAutopilotRunStepOverwriteAhrs();
        }
    }

    public static void npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();
        } else {
            NativeTasks.npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();
        }
    }

    public static void npsElectricalRunStep(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsElectricalRunStep(time);
        } else {
            NativeTasks.npsElectricalRunStep(time);
        }
    }

    public static void sendBarometricReading(float pressure) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.sendBarometricReading(pressure);
        } else {
            NativeTasks.sendBarometricReading(pressure);
        }
    }

    public static void npsSensorInitGyro(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorInitGyro(time);
        } else {
            NativeTasks.npsSensorInitGyro(time);
        }
    }

    public static void npsSensorInitAccel(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorInitAccel(time);
        } else {
            NativeTasks.npsSensorInitAccel(time);
        }
    }

    public static void npsSensorInitMag(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorInitMag(time);
        } else {
            NativeTasks.npsSensorInitMag(time);
        }
    }

    public static void npsSensorInitBaro(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorInitBaro(time);
        } else {
            NativeTasks.npsSensorInitBaro(time);
        }
    }

    public static void npsSensorInitGps(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorInitGps(time);
        } else {
            NativeTasks.npsSensorInitGps(time);
        }
    }

    public static void npsSensorFdmCopyGyro(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFdmCopyGyro(time);
        } else {
            NativeTasks.npsSensorFdmCopyGyro(time);
        }

    }

    public static void npsSensorFdmCopyAccel(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFdmCopyAccel(time);
        } else {
            NativeTasks.npsSensorFdmCopyAccel(time);
        }
    }

    public static void npsSensorFdmCopyMag(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFdmCopyMag(time);
        } else {
            NativeTasks.npsSensorFdmCopyMag(time);
        }
    }

    public static void npsSensorFdmCopyBaro(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFdmCopyBaro(time);
        } else {
            NativeTasks.npsSensorFdmCopyBaro(time);
        }
    }

    public static void npsSensorFdmCopyGps(double time) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFdmCopyGps(time);
        } else {
            NativeTasks.npsSensorFdmCopyGps(time);
        }
    }

    public static void npsSensorFeedStepGyro() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFeedStepGyro();
        } else {
            NativeTasks.npsSensorFeedStepGyro();
        }
    }

    public static void npsSensorFeedStepAccel() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFeedStepAccel();
        } else {
            NativeTasks.npsSensorFeedStepAccel();
        }
    }

    public static void npsSensorFeedStepMag() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFeedStepMag();
        } else {
            NativeTasks.npsSensorFeedStepMag();
        }
    }

    public static void npsSensorFeedStepBaro() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFeedStepBaro();
        } else {
            NativeTasks.npsSensorFeedStepBaro();
        }
    }

    public static void npsSensorFeedStepGps() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.npsSensorFeedStepGps();
        } else {
            NativeTasks.npsSensorFeedStepGps();
        }
    }


    public static void mainPeriodicJuavAutopilotPrior() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.mainPeriodicJuavAutopilotPrior();
        } else {
            NativeTasks.mainPeriodicJuavAutopilotPrior();
        }
    }

    public static boolean sysTimeCheckAndAckTimerMainPeriodicJuav() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.sysTimeCheckAndAckTimerMainPeriodicJuav();
        } else {
            return NativeTasks.sysTimeCheckAndAckTimerMainPeriodicJuav();
        }
    }

    public static void handlePeriodicTasksFollowingMainPeriodicJuav() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.handlePeriodicTasksFollowingMainPeriodicJuav();
        } else {
            NativeTasks.handlePeriodicTasksFollowingMainPeriodicJuav();
        }
    }

    public static void autopilotPeriodicPriorJuav() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.autopilotPeriodicPriorJuav();
        } else {
            NativeTasks.autopilotPeriodicPriorJuav();
        }
    }

    public static boolean isAutopilotModeApModeKillJuav() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.isAutopilotModeApModeKillJuav();
        } else {
            return NativeTasks.isAutopilotModeApModeKillJuav();
        }
    }

    public static void autopilotPeriodicPostJuav() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.autopilotPeriodicPostJuav();
        } else {
            NativeTasks.autopilotPeriodicPostJuav();
        }
    }

    public static void guidanceHRunJuav(boolean inFlight) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidanceHRunJuav(inFlight);
        } else {
            NativeTasks.guidanceHRunJuav(inFlight);
        }
    }

    public static boolean getAutopilotInFlightJuav() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAutopilotInFlightJuav();
        } else {
            return NativeTasks.getAutopilotInFlightJuav();
        }
    }

    public static boolean runStabilizationAttitudeRunJuav() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.runStabilizationAttitudeRunJuav();
        } else {
            return NativeTasks.runStabilizationAttitudeRunJuav();
        }
    }

    public static void guidanceHRunNativeTestJuav(boolean inFlight) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidanceHRunNativeTestJuav(inFlight);
        } else {
            NativeTasks.guidanceHRunNativeTestJuav(inFlight);
        }
    }

    public static void mainPeriodicJuavAutopilotPost() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.mainPeriodicJuavAutopilotPost();
        } else {
            NativeTasks.mainPeriodicJuavAutopilotPost();
        }
    }

    public static void mainPeriodicJuavTest() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.mainPeriodicJuavTest();
        } else {
            NativeTasks.mainPeriodicJuavTest();
        }
    }

    public static int stateGetNedToBodyQuatIQi() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyQuatIQi();
        } else {
            return NativeTasks.stateGetNedToBodyQuatIQi();
        }
    }

    public static int stateGetNedToBodyQuatIQx() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyQuatIQx();
        } else {
            return NativeTasks.stateGetNedToBodyQuatIQx();
        }
    }

    public static int stateGetNedToBodyQuatIQy() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyQuatIQy();
        } else {
            return NativeTasks.stateGetNedToBodyQuatIQy();
        }
    }

    public static int stateGetNedToBodyQuatIQz() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyQuatIQz();
        } else {
            return NativeTasks.stateGetNedToBodyQuatIQz();
        }
    }

    public static int stateGetBodyRatesIP() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetBodyRatesIP();
        } else {
            return NativeTasks.stateGetBodyRatesIP();
        }
    }

    public static int stateGetBodyRatesIQ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetBodyRatesIQ();
        } else {
            return NativeTasks.stateGetBodyRatesIQ();
        }
    }

    public static int stateGetBodyRatesIR() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetBodyRatesIR();
        } else {
            return NativeTasks.stateGetBodyRatesIR();
        }
    }

    public static void attitudeRefQuatIntUpdateJuav(float dt) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.attitudeRefQuatIntUpdateJuav(dt);
        } else {
            NativeTasks.attitudeRefQuatIntUpdateJuav(dt);
        }
    }

    public static int getStabilizationAttSumErrQuatQi() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getStabilizationAttSumErrQuatQi();
        } else {
            return NativeTasks.getStabilizationAttSumErrQuatQi();
        }
    }

    public static int getStabilizationAttSumErrQuatQx() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getStabilizationAttSumErrQuatQx();
        } else {
            return NativeTasks.getStabilizationAttSumErrQuatQx();
        }
    }

    public static int getStabilizationAttSumErrQuatQy() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getStabilizationAttSumErrQuatQy();
        } else {
            return NativeTasks.getStabilizationAttSumErrQuatQy();
        }
    }

    public static int getStabilizationAttSumErrQuatQz() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getStabilizationAttSumErrQuatQz();
        } else {
            return NativeTasks.getStabilizationAttSumErrQuatQz();
        }
    }

//    public static int getAttitudeRefEulerPsi() {}
//    public static int getAttitudeRefEulerPhi() {}
//    public static int getAttitudeRefEulerTheta() {}

    public static int getAttitudeRefQuatQi() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefQuatQi();
        } else {
            return NativeTasks.getAttitudeRefQuatQi();
        }
    }

    public static int getAttitudeRefQuatQx() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefQuatQx();
        } else {
            return NativeTasks.getAttitudeRefQuatQx();
        }
    }

    public static int getAttitudeRefQuatQy() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefQuatQy();
        } else {
            return NativeTasks.getAttitudeRefQuatQy();
        }
    }

    public static int getAttitudeRefQuatQz() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefQuatQz();
        } else {
            return NativeTasks.getAttitudeRefQuatQz();
        }
    }

    public static int getAttitudeRefRateP() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefRateP();
        } else {
            return NativeTasks.getAttitudeRefRateP();
        }
    }

    public static int getAttitudeRefRateQ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefRateQ();
        } else {
            return NativeTasks.getAttitudeRefRateQ();
        }
    }

    public static int getAttitudeRefRateR() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefRateR();
        } else {
            return NativeTasks.getAttitudeRefRateR();
        }
    }

    public static int getAttitudeGainPX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainPX();
        } else {
            return NativeTasks.getAttitudeGainPX();
        }
    }

    public static int getAttitudeGainPY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainPY();
        } else {
            return NativeTasks.getAttitudeGainPY();
        }
    }

    public static int getAttitudeGainPZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainPZ();
        } else {
            return NativeTasks.getAttitudeGainPZ();
        }
    }

    public static int getAttitudeGainDX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainDX();
        } else {
            return NativeTasks.getAttitudeGainDX();
        }
    }

    public static int getAttitudeGainDY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainDY();
        } else {
            return NativeTasks.getAttitudeGainDY();
        }
    }

    public static int getAttitudeGainDZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainDZ();
        } else {
            return NativeTasks.getAttitudeGainDZ();
        }
    }

    public static int getAttitudeGainDdX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainDdX();
        } else {
            return NativeTasks.getAttitudeGainDdX();
        }
    }

    public static int getAttitudeGainDdY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainDdY();
        } else {
            return NativeTasks.getAttitudeGainDdY();
        }
    }

    public static int getAttitudeGainDdZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainDdZ();
        } else {
            return NativeTasks.getAttitudeGainDdZ();
        }
    }

    public static int getAttitudeGainIX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainIX();
        } else {
            return NativeTasks.getAttitudeGainIX();
        }
    }

    public static int getAttitudeGainIY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainIY();
        } else {
            return NativeTasks.getAttitudeGainIY();
        }
    }

    public static int getAttitudeGainIZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeGainIZ();
        } else {
            return NativeTasks.getAttitudeGainIZ();
        }
    }

    public static int getAttitudeRefAccelP() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefAccelP();
        } else {
            return NativeTasks.getAttitudeRefAccelP();
        }
    }

    public static int getAttitudeRefAccelQ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefAccelQ();
        } else {
            return NativeTasks.getAttitudeRefAccelQ();
        }
    }

    public static int getAttitudeRefAccelR() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefAccelR();
        } else {
            return NativeTasks.getAttitudeRefAccelR();
        }
    }

    public static void setStabilizationAttSumErrQuatQi(int qi) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationAttSumErrQuatQi(qi);
        } else {
            NativeTasks.setStabilizationAttSumErrQuatQi(qi);
        }
    }

    public static void setStabilizationAttSumErrQuatQx(int qx) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationAttSumErrQuatQx(qx);
        } else {
            NativeTasks.setStabilizationAttSumErrQuatQx(qx);
        }
    }

    public static void setStabilizationAttSumErrQuatQy(int qy) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationAttSumErrQuatQy(qy);
        } else {
            NativeTasks.setStabilizationAttSumErrQuatQy(qy);
        }
    }

    public static void setStabilizationAttSumErrQuatQz(int qz) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationAttSumErrQuatQz(qz);
        } else {
            NativeTasks.setStabilizationAttSumErrQuatQz(qz);
        }
    }

    public static void setAttRefQuatIQuatQi(int qi) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIQuatQi(qi);
        } else {
            NativeTasks.setAttRefQuatIQuatQi(qi);
        }
    }

    public static void setAttRefQuatIQuatQx(int qx) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIQuatQx(qx);
        } else {
            NativeTasks.setAttRefQuatIQuatQx(qx);
        }
    }

    public static void setAttRefQuatIQuatQy(int qy) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIQuatQy(qy);
        } else {
            NativeTasks.setAttRefQuatIQuatQy(qy);
        }
    }

    public static void setAttRefQuatIQuatQz(int qz) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIQuatQz(qz);
        } else {
            NativeTasks.setAttRefQuatIQuatQz(qz);
        }
    }

    public static void setAttRefQuatIRateP(int p) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIRateP(p);
        } else {
            NativeTasks.setAttRefQuatIRateP(p);
        }
    }

    public static void setAttRefQuatIRateQ(int q) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIRateQ(q);
        } else {
            NativeTasks.setAttRefQuatIRateQ(q);
        }
    }

    public static void setAttRefQuatIRateR(int r) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIRateR(r);
        } else {
            NativeTasks.setAttRefQuatIRateR(r);
        }
    }

    public static void setAttRefQuatIAccelP(int p) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIAccelP(p);
        } else {
            NativeTasks.setAttRefQuatIAccelP(p);
        }
    }

    public static void setAttRefQuatIAccelQ(int q) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIAccelQ(q);
        } else {
            NativeTasks.setAttRefQuatIAccelQ(q);
        }
    }

    public static void setAttRefQuatIAccelR(int r) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAttRefQuatIAccelR(r);
        } else {
            NativeTasks.setAttRefQuatIAccelR(r);
        }
    }

    public static void setStabilizationCommands(int yaw, int pitch, int roll) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationCommands(yaw,pitch,roll);
        } else {
            NativeTasks.setStabilizationCommands(yaw,pitch,roll);
        }
    }


    /////////////
    public static int stateGetPositionNedIX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetPositionNedIX();
        } else {
            return NativeTasks.stateGetPositionNedIX();
        }
    }

    public static int stateGetPositionNedIY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetPositionNedIY();
        } else {
            return NativeTasks.stateGetPositionNedIY();
        }
    }

    public static int stateGetPositionNedIZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetPositionNedIZ();
        } else {
            return NativeTasks.stateGetPositionNedIZ();
        }
    }

    public static int stateGetSpeedNedIX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetSpeedNedIX();
        } else {
            return NativeTasks.stateGetSpeedNedIX();
        }
    }

    public static int stateGetSpeedNedIY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetSpeedNedIY();
        } else {
            return NativeTasks.stateGetSpeedNedIY();
        }
    }

    public static int stateGetSpeedNedIZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetSpeedNedIZ();
        } else {
            return NativeTasks.stateGetSpeedNedIZ();
        }
    }

    public static int stateGetNedToBodyRMatI_0() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyRMatI_0();
        } else {
            return NativeTasks.stateGetNedToBodyRMatI_0();
        }
    }

    public static int stateGetNedToBodyRMatI_1() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyRMatI_1();
        } else {
            return NativeTasks.stateGetNedToBodyRMatI_1();
        }
    }

    public static int stateGetNedToBodyRMatI_2() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyRMatI_2();
        } else {
            return NativeTasks.stateGetNedToBodyRMatI_2();
        }
    }

    public static int stateGetNedToBodyRMatI_3() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyRMatI_3();
        } else {
            return NativeTasks.stateGetNedToBodyRMatI_3();
        }
    }

    public static int stateGetNedToBodyRMatI_4() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyRMatI_4();
        } else {
            return NativeTasks.stateGetNedToBodyRMatI_4();
        }
    }

    public static int stateGetNedToBodyRMatI_5() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyRMatI_5();
        } else {
            return NativeTasks.stateGetNedToBodyRMatI_5();
        }
    }

    public static int stateGetNedToBodyRMatI_6() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyRMatI_6();
        } else {
            return NativeTasks.stateGetNedToBodyRMatI_6();
        }
    }

    public static int stateGetNedToBodyRMatI_7() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyRMatI_7();
        } else {
            return NativeTasks.stateGetNedToBodyRMatI_7();
        }
    }

    public static int stateGetNedToBodyRMatI_8() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyRMatI_8();
        } else {
            return NativeTasks.stateGetNedToBodyRMatI_8();
        }
    }

    public static int stateGetNedToBodyEulersIPsiInt() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyEulersIPsiInt();
        } else {
            return NativeTasks.stateGetNedToBodyEulersIPsiInt();
        }
    }

    public static int stateGetNedToBodyEulersITheataInt() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyEulersITheataInt();
        } else {
            return NativeTasks.stateGetNedToBodyEulersITheataInt();
        }
    }

    public static int stateGetNedToBodyEulersIPhiInt() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyEulersIPhiInt();
        } else {
            return NativeTasks.stateGetNedToBodyEulersIPhiInt();
        }
    }

    public static float stateGetNedToBodyEulersIPsiFloat() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyEulersIPsiFloat();
        } else {
            return NativeTasks.stateGetNedToBodyEulersIPsiFloat();
        }
    }

    public static float stateGetNedToBodyEulersITheataFloat() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyEulersITheataFloat();
        } else {
            return NativeTasks.stateGetNedToBodyEulersITheataFloat();
        }
    }

    public static float stateGetNedToBodyEulersIPhiFloat() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetNedToBodyEulersIPhiFloat();
        } else {
            return NativeTasks.stateGetNedToBodyEulersIPhiFloat();
        }
    }

    public static int stateGetAccelNedIX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetAccelNedIX();
        } else {
            return NativeTasks.stateGetAccelNedIX();
        }
    }

    public static int stateGetAccelNedIY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetAccelNedIY();
        } else {
            return NativeTasks.stateGetAccelNedIY();
        }
    }

    public static int stateGetAccelNedIZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetAccelNedIZ();
        } else {
            return NativeTasks.stateGetAccelNedIZ();
        }
    }

    public static void navPeriodicTask() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.navPeriodicTask();
        } else {
            NativeTasks.navPeriodicTask();
        }
    }

    public static void navHome() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.navHome();
        } else {
            NativeTasks.navHome();
        }
    }

    public static void computeDist2ToHome() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.computeDist2ToHome();
        } else {
            NativeTasks.computeDist2ToHome();
        }
    }

    public static float stateGetSpeedNedFX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetSpeedNedFX();
        } else {
            return NativeTasks.stateGetSpeedNedFX();
        }
    }

    public static float stateGetSpeedNedFY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetSpeedNedFY();
        } else {
            return NativeTasks.stateGetSpeedNedFY();
        }
    }

    public static float stateGetSpeedNedFZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetSpeedNedFZ();
        } else {
            return NativeTasks.stateGetSpeedNedFZ();
        }
    }

    public static float stateGetAccelNedFX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetAccelNedFX();
        } else {
            return NativeTasks.stateGetAccelNedFX();
        }
    }

    public static float stateGetAccelNedFY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetAccelNedFY();
        } else {
            return NativeTasks.stateGetAccelNedFY();
        }
    }

    public static float stateGetAccelNedFZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateGetAccelNedFZ();
        } else {
            return NativeTasks.stateGetAccelNedFZ();
        }
    }

    public static boolean stateIsAttitudeValid() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.stateIsAttitudeValidCall();
        } else {
            return NativeTasks.stateIsAttitudeValid();
        }
    }

    public static void navInit() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.navInit();
        } else {
            NativeTasks.navInit();
        }
    }

    //    Telemetry registeration
    public static void periodicTelemetrySendHoverLoop() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendHoverLoop();
        } else {
            NativeTasks.periodicTelemetrySendHoverLoop();
        }
    }

    public static void periodicTelemetrySendHref() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendHref();
        } else {
            NativeTasks.periodicTelemetrySendHref();
        }
    }

    public static void periodicTelemetrySendGh() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendGh();
        } else {
            NativeTasks.periodicTelemetrySendGh();
        }
    }

    public static void periodicTelemetrySendTuneHover() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendTuneHover();
        } else {
            NativeTasks.periodicTelemetrySendTuneHover();
        }
    }

    public static void periodicTelemetrySendAutopilotVersion() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendAutopilotVersion();
        } else {
            NativeTasks.periodicTelemetrySendAutopilotVersion();
        }
    }

    public static void periodicTelemetrySendAlive() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendAlive();
        } else {
            NativeTasks.periodicTelemetrySendAlive();
        }
    }

    public static void periodicTelemetrySendStatus() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendStatus();
        } else {
            NativeTasks.periodicTelemetrySendStatus();
        }
    }

    public static void periodicTelemetrySendAttitude() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendAttitude();
        } else {
            NativeTasks.periodicTelemetrySendAttitude();
        }
    }

    public static void periodicTelemetrySendEnergy() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendEnergy();
        } else {
            NativeTasks.periodicTelemetrySendEnergy();
        }
    }

    public static void periodicTelemetrySendFp() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendFp();
        } else {
            NativeTasks.periodicTelemetrySendFp();
        }
    }

    public static void periodicTelemetrySendRotorcraftCmd() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendRotorcraftCmd();
        } else {
            NativeTasks.periodicTelemetrySendRotorcraftCmd();
        }
    }

    public static void periodicTelemetrySendDlValue() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendDlValue();
        } else {
            NativeTasks.periodicTelemetrySendDlValue();
        }
    }

    public static void periodicTelemetrySendActuators() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendActuators();
        } else {
            NativeTasks.periodicTelemetrySendActuators();
        }
    }

    public static void periodicTelemetrySendRc() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendRc();
        } else {
            NativeTasks.periodicTelemetrySendRc();
        }
    }

    public static void periodicTelemetrySendRotorcraftRc() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendRotorcraftRc();
        } else {
            NativeTasks.periodicTelemetrySendRotorcraftRc();
        }
    }

    public static void periodicTelemetrySendVertLoop() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendVertLoop();
        } else {
            NativeTasks.periodicTelemetrySendVertLoop();
        }
    }

    public static void periodicTelemetrySendTuneVert() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendTuneVert();
        } else {
            NativeTasks.periodicTelemetrySendTuneVert();
        }
    }

    public static void periodicTelemetrySendAtt() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendAtt();
        } else {
            NativeTasks.periodicTelemetrySendAtt();
        }
    }

    public static void periodicTelemetrySendAttRef() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendAttRef();
        } else {
            NativeTasks.periodicTelemetrySendAttRef();
        }
    }

    public static void periodicTelemetrySendAhrsRefQuat() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendAhrsRefQuat();
        } else {
            NativeTasks.periodicTelemetrySendAhrsRefQuat();
        }
    }

    public static void periodicTelemetrySendRate() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.periodicTelemetrySendRate();
        } else {
            NativeTasks.periodicTelemetrySendRate();
        }
    }

    public static int getRadioControlValue(int index) {
        if (NativeSwitch.isFiji()) {
           return NativeTasksFiji.getRadioControlValue(index);
        } else {
           return NativeTasks.getRadioControlValue(index);
        }
    }

    public static int getNavigationCarrotX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getNavigationCarrotX();
        } else {
            return NativeTasks.getNavigationCarrotX();
        }
    }

    public static int getNavigationCarrotY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getNavigationCarrotY();
        } else {
            return NativeTasks.getNavigationCarrotY();
        }
    }

    public static int getNavigationCarrotZ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getNavigationCarrotZ();
        } else {
            return NativeTasks.getNavigationCarrotZ();
        }
    }

    public static boolean npsAutopilotRunRadioStepAndShouldRunMainEvent(double time) {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.npsAutopilotRunRadioStepAndShouldRunMainEvent(time);
        } else {
            return NativeTasks.npsAutopilotRunRadioStepAndShouldRunMainEvent(time);
        }
    }

    public static short getRadioControlStatus() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getRadioControlStatus();
        } else {
            return NativeTasks.getRadioControlStatus();
        }
    }

    public static float navigationGetDist2ToHome() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetDist2ToHome();
        } else {
            return NativeTasks.navigationGetDist2ToHome();
        }
    }

    public static boolean navigationGetTooFarFromHome() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetTooFarFromHome();
        } else {
            return NativeTasks.navigationGetTooFarFromHome();
        }
    }

    public static short navigationGetHorizontalMode() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetHorizontalMode();
        } else {
            return NativeTasks.navigationGetHorizontalMode();
        }
    }

    public static int navigationGetNavRoll() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetNavRoll();
        } else {
            return NativeTasks.navigationGetNavRoll();
        }
    }

    public static int navigationGetNavPitch() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetNavPitch();
        } else {
            return NativeTasks.navigationGetNavPitch();
        }
    }

    public static int navigationGetNavHeading() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetNavHeading();
        } else {
            return NativeTasks.navigationGetNavHeading();
        }
    }

    public static void navigationSetNavHeading(int newHeading) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.navigationSetNavHeading(newHeading);
        } else {
            NativeTasks.navigationSetNavHeading(newHeading);
        }
    }

    public static int navigationGetNavVerticleMode() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetNavVerticleMode();
        } else {
            return NativeTasks.navigationGetNavVerticleMode();
        }
    }

    public static int navigationGetNavClimb() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetNavClimb();
        } else {
            return NativeTasks.navigationGetNavClimb();
        }
    }

    public static int navigationGetNavFlightAltitude() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetNavFlightAltitude();
        } else {
            return NativeTasks.navigationGetNavFlightAltitude();
        }
    }

    public static int navigationGetNavThrottle() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.navigationGetNavThrottle();
        } else {
            return NativeTasks.navigationGetNavThrottle();
        }
    }

    public static void setGuidanceHMode(short newMode) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setGuidanceHMode(newMode);
        } else {
            NativeTasks.setGuidanceHMode(newMode);
        }
    }

    public static void setGuidanceVMode(short newMode) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setGuidanceVMode(newMode);
        } else {
            NativeTasks.setGuidanceVMode(newMode);
        }
    }

    public static void setAutopilotMode(short new_autopilot_mode) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAutopilotMode(new_autopilot_mode);
        } else {
            NativeTasks.setAutopilotMode(new_autopilot_mode);
        }
    }

    public static void setGuidanceVRcDeltaT(int newValue) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setGuidanceVRcDeltaT(newValue);
        } else {
            NativeTasks.setGuidanceVRcDeltaT(newValue);
        }
    }

    public static void setGuidanceVRcZdSp(int newValue) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setGuidanceVRcZdSp(newValue);
        } else {
            NativeTasks.setGuidanceVRcZdSp(newValue);
        }
    }

    public static void setStabilizationCommand(int commandIndex, int command) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationCommand(commandIndex,command);
        } else {
            NativeTasks.setStabilizationCommand(commandIndex,command);
        }
    }

    public static boolean getAutopilotMotorsOnJuav() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAutopilotMotorsOnJuav();
        } else {
            return NativeTasks.getAutopilotMotorsOnJuav();
        }
    }

    public static void juavSetAutopilotMotorsOn(boolean b) {
        if (NativeSwitch.isFiji()) {
             NativeTasksFiji.juavSetAutopilotMotorsOn(b);
        } else {
             NativeTasks.juavSetAutopilotMotorsOn(b);
        }
    }

    public static void juavSetAutopilotCheckMotorStatus(int ordinal) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.juavSetAutopilotCheckMotorStatus(ordinal);
        } else {
            NativeTasks.juavSetAutopilotCheckMotorStatus(ordinal);
        }
    }

    public static int juavGetAutopilotCheckMotorStatus() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.juavGetAutopilotCheckMotorStatus();
        } else {
            return NativeTasks.juavGetAutopilotCheckMotorStatus();
        }
    }

    public static void juavSetAutopilotMotorsOnCounter(int newCount) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.juavSetAutopilotMotorsOnCounter(newCount);
        } else {
            NativeTasks.juavSetAutopilotMotorsOnCounter(newCount);
        }
    }

    public static int juavGetAutopilotMotorsOnCounter() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.juavGetAutopilotMotorsOnCounter();
        } else {
            return NativeTasks.juavGetAutopilotMotorsOnCounter();
        }
    }

    public static int getStabilizationCmd(int index) {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getStabilizationCmd(index);
        } else {
            return NativeTasks.getStabilizationCmd(index);
        }
    }

    public static void setStabilizationCmd(int index, int newValue) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationCmd(index,newValue);
        } else {
            NativeTasks.setStabilizationCmd(index,newValue);
        }
    }

    public static int getHorizantialGuidanceSetPointPosX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceSetPointPosX();
        } else {
            return NativeTasks.getHorizantialGuidanceSetPointPosX();
        }
    }

    public static int getHorizantialGuidanceSetPointPosY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceSetPointPosY();
        } else {
            return NativeTasks.getHorizantialGuidanceSetPointPosY();
        }
    }

    public static void setHorizantialGuidanceSetPointPosX(int x) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setHorizantialGuidanceSetPointPosX(x);
        } else {
            NativeTasks.setHorizantialGuidanceSetPointPosX(x);
        }
    }

    public static void setHorizantialGuidanceSetPointPosY(int y) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setHorizantialGuidanceSetPointPosY(y);
        } else {
            NativeTasks.setHorizantialGuidanceSetPointPosY(y);
        }
    }

    public static int getHorizantialGuidanceSetPointSpeedX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceSetPointSpeedX();
        } else {
            return NativeTasks.getHorizantialGuidanceSetPointSpeedX();
        }
    }

    public static int getHorizantialGuidanceSetPointSpeedY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceSetPointSpeedY();
        } else {
            return NativeTasks.getHorizantialGuidanceSetPointSpeedY();
        }
    }

    public static int getHorizantialGuidanceReferencePosX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceReferencePosX();
        } else {
            return NativeTasks.getHorizantialGuidanceReferencePosX();
        }
    }

    public static int getHorizantialGuidanceReferencePosY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceReferencePosY();
        } else {
            return NativeTasks.getHorizantialGuidanceReferencePosY();
        }
    }

    public static void setHorizantialGuidanceReferencePosX(int x) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setHorizantialGuidanceReferencePosX(x);
        } else {
            NativeTasks.setHorizantialGuidanceReferencePosX(x);
        }
    }

    public static void setHorizantialGuidanceReferencePosY(int y) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setHorizantialGuidanceReferencePosY(y);
        } else {
            NativeTasks.setHorizantialGuidanceReferencePosY(y);
        }
    }

    public static int getHorizantialGuidanceReferenceSpeedX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceReferenceSpeedX();
        } else {
            return NativeTasks.getHorizantialGuidanceReferenceSpeedX();
        }
    }

    public static int getHorizantialGuidanceReferenceSpeedY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceReferenceSpeedY();
        } else {
            return NativeTasks.getHorizantialGuidanceReferenceSpeedY();
        }
    }

    public static void setHorizantialGuidanceReferenceSpeedX(int x) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setHorizantialGuidanceReferenceSpeedX(x);
        } else {
            NativeTasks.setHorizantialGuidanceReferenceSpeedX(x);
        }
    }

    public static void setHorizantialGuidanceReferenceSpeedY(int y) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setHorizantialGuidanceReferenceSpeedY(y);
        } else {
            NativeTasks.setHorizantialGuidanceReferenceSpeedY(y);
        }
    }

    public static int getHorizantialGuidanceReferenceAccelX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceReferenceAccelX();
        } else {
            return NativeTasks.getHorizantialGuidanceReferenceAccelX();
        }
    }

    public static int getHorizantialGuidanceReferenceAccelY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceReferenceAccelY();
        } else {
            return NativeTasks.getHorizantialGuidanceReferenceAccelY();
        }
    }

    public static void setHorizantialGuidanceReferenceAccelX(int x) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setHorizantialGuidanceReferenceAccelX(x);
        } else {
            NativeTasks.setHorizantialGuidanceReferenceAccelX(x);
        }
    }

    public static void setHorizantialGuidanceReferenceAccelY(int y) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setHorizantialGuidanceReferenceAccelY(y);
        } else {
            NativeTasks.setHorizantialGuidanceReferenceAccelY(y);
        }
    }

    public static int getHorizantialGuidanceHeading() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getHorizantialGuidanceHeading();
        } else {
            return NativeTasks.getHorizantialGuidanceHeading();
        }
    }

    public static void setHorizantialGuidanceHeading(int newHeading) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setHorizantialGuidanceHeading(newHeading);
        } else {
            NativeTasks.setHorizantialGuidanceHeading(newHeading);
        }
    }

    public static void setGuidanceHCmdEarthX(int x) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setGuidanceHCmdEarthX(x);
        } else {
            NativeTasks.setGuidanceHCmdEarthX(x);
        }
    }

    public static void setGuidanceHCmdEarthY(int y) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setGuidanceHCmdEarthY(y);
        } else {
            NativeTasks.setGuidanceHCmdEarthY(y);
        }
    }

    public static int getGuidanceHCmdEarthX() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getGuidanceHCmdEarthX();
        } else {
            return NativeTasks.getGuidanceHCmdEarthX();
        }
    }

    public static int getGuidanceHCmdEarthY() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getGuidanceHCmdEarthY();
        } else {
            return NativeTasks.getGuidanceHCmdEarthY();
        }
    }

    public static int getStabilizationAttSpQuatQi() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getStabilizationAttSpQuatQi();
        } else {
            return NativeTasks.getStabilizationAttSpQuatQi();
        }
    }

    public static int getStabilizationAttSpQuatQx() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getStabilizationAttSpQuatQx();
        } else {
            return NativeTasks.getStabilizationAttSpQuatQx();
        }
    }

    public static int getStabilizationAttSpQuatQy() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getStabilizationAttSpQuatQy();
        } else {
            return NativeTasks.getStabilizationAttSpQuatQy();
        }
    }

    public static int getStabilizationAttSpQuatQz() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getStabilizationAttSpQuatQz();
        } else {
            return NativeTasks.getStabilizationAttSpQuatQz();
        }
    }

    public static void setStabilizationAttSpQuatQi(int qi) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationAttSpQuatQi(qi);
        } else {
            NativeTasks.setStabilizationAttSpQuatQi(qi);
        }
    }

    public static void setStabilizationAttSpQuatQx(int qx) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationAttSpQuatQx(qx);
        } else {
            NativeTasks.setStabilizationAttSpQuatQx(qx);
        }
    }

    public static void setStabilizationAttSpQuatQy(int qy) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationAttSpQuatQy(qy);
        } else {
            NativeTasks.setStabilizationAttSpQuatQy(qy);
        }
    }

    public static void setStabilizationAttSpQuatQz(int qz) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationAttSpQuatQz(qz);
        } else {
            NativeTasks.setStabilizationAttSpQuatQz(qz);
        }
    }

    public static void juavAutopilotPeriodic() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.juavAutopilotPeriodic();
        } else {
            NativeTasks.juavAutopilotPeriodic();
        }
    }

    public static void setAutopilotGroundDetected(boolean b) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAutopilotGroundDetected(b);
        } else {
            NativeTasks.setAutopilotGroundDetected(b);
        }
    }

    public static void setAutopilotDetectGroundOnce(boolean b) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAutopilotDetectGroundOnce(b);
        } else {
            NativeTasks.setAutopilotDetectGroundOnce(b);
        }
    }

    public static void guidanceVRunJuav(boolean autopilotInFlight) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidanceVRunJuav(autopilotInFlight);
        } else {
            NativeTasks.guidanceVRunJuav(autopilotInFlight);
        }
    }

    public static void guidance_h_mode_changed_native(short new_mode) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidance_h_mode_changed_native(new_mode);
        } else {
            NativeTasks.guidance_h_mode_changed_native(new_mode);
        }
    }

    public static void guidance_v_mode_changed_native(short new_mode) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidance_v_mode_changed_native(new_mode);
        } else {
            NativeTasks.guidance_v_mode_changed_native(new_mode);
        }
    }

    public static void guidanceHReadRc(boolean in_flight) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidanceHReadRc(in_flight);
        } else {
            NativeTasks.guidanceHReadRc(in_flight);
        }
    }

    public static void setAutopilotModeNativeLogic(short new_autopilot_mode) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setAutopilotModeNativeLogic(new_autopilot_mode);
        } else {
            NativeTasks.setAutopilotModeNativeLogic(new_autopilot_mode);
        }
    }

    public static void juavStabilizationAttitudeRunNative(boolean enable_integrator) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.juavStabilizationAttitudeRunNative(enable_integrator);
        } else {
            NativeTasks.juavStabilizationAttitudeRunNative(enable_integrator);
        }
    }

    public static void setStabilizationAttitudeSetRpySetpointI(int psi, int phi, int theta) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.setStabilizationAttitudeSetRpySetpointI(psi,phi,theta);
        } else {
            NativeTasks.setStabilizationAttitudeSetRpySetpointI(psi,phi,theta);
        }
    }

    public static void guidanceHUpdateReference() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidanceHUpdateReference();
        } else {
            NativeTasks.guidanceHUpdateReference();
        }
    }

    public static void guidanceHNavEnter() {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidanceHNavEnter();
        } else {
            NativeTasks.guidanceHNavEnter();
        }
    }

    public static void guidanceHTrajRun(boolean inFlight) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidanceHTrajRun(inFlight);
        } else {
            NativeTasks.guidanceHTrajRun(inFlight);
        }
    }


    public static int getAttitudeRefEulerPsi() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefEulerPsi();
        } else {
            return NativeTasks.getAttitudeRefEulerPsi();
        }
    }

    public static int getAttitudeRefEulerPhi() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefEulerPhi();
        } else {
            return NativeTasks.getAttitudeRefEulerPhi();
        }
    }

    public static int getAttitudeRefEulerTheta() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefEulerTheta();
        } else {
            return NativeTasks.getAttitudeRefEulerTheta();
        }
    }

    public static int getAttitudeRefModelTwoZetaOmegaP() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelTwoZetaOmegaP();
        } else {
            return NativeTasks.getAttitudeRefModelTwoZetaOmegaP();
        }
    }

    public static int getAttitudeRefModelTwoZetaOmegaQ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelTwoZetaOmegaQ();
        } else {
            return NativeTasks.getAttitudeRefModelTwoZetaOmegaQ();
        }
    }

    public static int getAttitudeRefModelTwoZetaOmegaR() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelTwoZetaOmegaR();
        } else {
            return NativeTasks.getAttitudeRefModelTwoZetaOmegaR();
        }
    }

    public static int getAttitudeRefModelTwoOmega2P() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelTwoOmega2P();
        } else {
            return NativeTasks.getAttitudeRefModelTwoOmega2P();
        }
    }

    public static int getAttitudeRefModelTwoOmega2Q() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelTwoOmega2Q();
        } else {
            return NativeTasks.getAttitudeRefModelTwoOmega2Q();
        }
    }

    public static int getAttitudeRefModelTwoOmega2R() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelTwoOmega2R();
        } else {
            return NativeTasks.getAttitudeRefModelTwoOmega2R();
        }
    }

    public static float getAttitudeRefModelZetaP() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelZetaP();
        } else {
            return NativeTasks.getAttitudeRefModelZetaP();
        }
    }

    public static float getAttitudeRefModelZetaQ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelZetaQ();
        } else {
            return NativeTasks.getAttitudeRefModelZetaQ();
        }
    }

    public static float getAttitudeRefModelZetaR() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelZetaR();
        } else {
            return NativeTasks.getAttitudeRefModelZetaR();
        }
    }

    public static float getAttitudeRefModelOmegaP() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelOmegaP();
        } else {
            return NativeTasks.getAttitudeRefModelOmegaP();
        }
    }

    public static float getAttitudeRefModelOmegaQ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelOmegaQ();
        } else {
            return NativeTasks.getAttitudeRefModelOmegaQ();
        }
    }

    public static float getAttitudeRefModelOmegaR() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefModelOmegaR();
        } else {
            return NativeTasks.getAttitudeRefModelOmegaR();
        }
    }

    public static int getAttitudeRefSaturationMaxAccelP() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefSaturationMaxAccelP();
        } else {
            return NativeTasks.getAttitudeRefSaturationMaxAccelP();
        }
    }

    public static int getAttitudeRefSaturationMaxAccelQ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefSaturationMaxAccelQ();
        } else {
            return NativeTasks.getAttitudeRefSaturationMaxAccelQ();
        }
    }

    public static int getAttitudeRefSaturationMaxAccelR() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefSaturationMaxAccelR();
        } else {
            return NativeTasks.getAttitudeRefSaturationMaxAccelR();
        }
    }

    public static int getAttitudeRefSaturationMaxRateP() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefSaturationMaxRateP();
        } else {
            return NativeTasks.getAttitudeRefSaturationMaxRateP();
        }
    }

    public static int getAttitudeRefSaturationMaxRateQ() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefSaturationMaxRateQ();
        } else {
            return NativeTasks.getAttitudeRefSaturationMaxRateQ();
        }
    }

    public static int getAttitudeRefSaturationMaxRateR() {
        if (NativeSwitch.isFiji()) {
            return NativeTasksFiji.getAttitudeRefSaturationMaxRateR();
        } else {
            return NativeTasks.getAttitudeRefSaturationMaxRateR();
        }
    }

    public static void guidanceHRunJuavCaseModeNav(boolean inFlight) {
        if (NativeSwitch.isFiji()) {
            NativeTasksFiji.guidanceHRunJuavCaseModeNav(inFlight);
        } else {
            NativeTasks.guidanceHRunJuavCaseModeNav(inFlight);
        }
    }
}

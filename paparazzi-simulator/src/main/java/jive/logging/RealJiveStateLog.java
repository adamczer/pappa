package jive.logging;

import juav.autopilot.AutopilotArmingYaw;

/**
 * Created by adamczer on 3/19/17.
 */
public class RealJiveStateLog {
    private static boolean motorsOn;
    private static AutopilotArmingYaw.arming_state motorStatus;
    private static short autopilotMode;
    private static short guidanceHMode;
    private static short guidanceVMode;
    private static boolean ahrsIsAligned;

    public static AutopilotArmingYaw.arming_state getMotorStatus() {
        return motorStatus;
    }

    public static void setMotorStatus(AutopilotArmingYaw.arming_state motorStatus) {
    		RealJiveStateLog.motorStatus = motorStatus;
    }

    public static boolean isAhrsIsAligned() {
        return ahrsIsAligned;
    }

    public static void setAhrsIsAligned(boolean ahrsIsAligned) {
    		RealJiveStateLog.ahrsIsAligned = ahrsIsAligned;
    }

    public static boolean isMotorsOn() {
        return motorsOn;
    }

    public static void setMotorsOn(boolean motorsOn) {
    		RealJiveStateLog.motorsOn = motorsOn;
    }

    public static short getAutopilotMode() {
        return autopilotMode;
    }

    public static void setAutopilotMode(short autopilotMode) {
    		RealJiveStateLog.autopilotMode = autopilotMode;
    }

    public static short getGuidanceHMode() {
        return guidanceHMode;
    }

    public static void setGuidanceHMode(short guidanceHMode) {
    		RealJiveStateLog.guidanceHMode = guidanceHMode;
    }

    public static short getGuidanceVMode() {
        return guidanceVMode;
    }

    public static void setGuidanceVMode(short guidanceVMode) {
    		RealJiveStateLog.guidanceVMode = guidanceVMode;
    }
}

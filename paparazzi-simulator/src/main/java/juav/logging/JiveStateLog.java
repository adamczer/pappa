package juav.logging;

import jive.logging.RealJiveStateLog;
import juav.autopilot.AutopilotArmingYaw;

/**
 * Created by adamczer on 3/19/17.
 */
public class JiveStateLog {
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
    	if(JiveStateLog.motorStatus != motorStatus) {
            JiveStateLog.motorStatus = motorStatus;
            RealJiveStateLog.setMotorStatus(motorStatus);
        }

    }

    public static boolean isAhrsIsAligned() {
        return ahrsIsAligned;
    }

    public static void setAhrsIsAligned(boolean ahrsIsAligned) {
    	if(JiveStateLog.ahrsIsAligned != ahrsIsAligned) {
            JiveStateLog.ahrsIsAligned = ahrsIsAligned;
            RealJiveStateLog.setAhrsIsAligned(ahrsIsAligned);
        }
    }

    public static boolean isMotorsOn() {
        return motorsOn;
    }

    public static void setMotorsOn(boolean motorsOn) {
    	if(JiveStateLog.motorsOn != motorsOn) {
            JiveStateLog.motorsOn = motorsOn;
            RealJiveStateLog.setMotorsOn(motorsOn);
        }
    }

    public static short getAutopilotMode() {
        return autopilotMode;
    }

    public static void setAutopilotMode(short autopilotMode) {
    	if(JiveStateLog.autopilotMode != autopilotMode) {
            JiveStateLog.autopilotMode = autopilotMode;
            RealJiveStateLog.setAutopilotMode(autopilotMode);
        }
    }

    public static short getGuidanceHMode() {
        return guidanceHMode;
    }

    public static void setGuidanceHMode(short guidanceHMode) {
    	if(JiveStateLog.guidanceHMode != guidanceHMode) {
            JiveStateLog.guidanceHMode = guidanceHMode;
            RealJiveStateLog.setGuidanceHMode(guidanceHMode);
    	}
    }

    public static short getGuidanceVMode() {
        return guidanceVMode;
    }

    public static void setGuidanceVMode(short guidanceVMode) {
    	if(JiveStateLog.guidanceVMode != guidanceVMode) {
            JiveStateLog.guidanceVMode = guidanceVMode;
            RealJiveStateLog.setGuidanceVMode(guidanceVMode);
        }
    }
}

package jive.logging;

import juav.autopilot.AutopilotArmingYaw;

/**
 * Created by adamczer on 3/19/17.
 */
public class RealJiveStateLog {
    private static boolean motorsOn;
    private static String motorStatus;
    private static String autopilotMode;
    private static String guidanceHMode;
    private static String guidanceVMode;
    private static boolean ahrsIsAligned;
    
    private static String cyclicStateLog; 
    private static String sensorState; 
    private static String navigationStateLog;
    private static String stabilization_none;
    private static String stabilization_rate;
    private static String stabilization_attitude;
	private static String imuNpsStatus;
	private static String jniSensorStatus;
	private static String gpsSimNps;
    
    public static String getMotorStatus() {
        return motorStatus;
    }

    public static void setMotorStatus(String motorStatus) {
    		RealJiveStateLog.motorStatus = motorStatus;
    }
    
    public static void setnavigationStateLog(String n){
    		navigationStateLog = n;
    }
    public static boolean isAhrsIsAligned() {
        return ahrsIsAligned;
    }

    public static void setAhrsIsAligned(boolean ahrsIsAligned) {
    		RealJiveStateLog.ahrsIsAligned = ahrsIsAligned;
    }
    
    public static void setcyclicStateLog(String cyclicStateLog) {
		RealJiveStateLog.cyclicStateLog = cyclicStateLog;
    }
    
    public static void setsensorState(String sensorState) {
		RealJiveStateLog.sensorState = sensorState;
    }
    
    public static boolean isMotorsOn() {
        return motorsOn;
    }

    public static void setMotorsOn(boolean motorsOn) {
    		RealJiveStateLog.motorsOn = motorsOn;
    }

    public static String getAutopilotMode() {
        return autopilotMode;
    }

    public static void setAutopilotMode(String autopilotMode) {
    		RealJiveStateLog.autopilotMode = autopilotMode;
    }

    public static String getGuidanceHMode() {
        return guidanceHMode;
    }

    public static void setGuidanceHMode(String guidanceHMode) {
    		RealJiveStateLog.guidanceHMode = guidanceHMode;
    }

    public static String getGuidanceVMode() {
        return guidanceVMode;
    }

    public static void setGuidanceVMode(String guidanceVMode) {
    		RealJiveStateLog.guidanceVMode = guidanceVMode;
    }

	public static void setStabilizationNone(String stabilization_none) {
		RealJiveStateLog.stabilization_none = stabilization_none;
		
	}

	public static void setStabilizationRate(String stabilization_rate) {
		RealJiveStateLog.stabilization_rate = stabilization_rate;
		
	}

	public static void setStabilizationAttitude(String stabilization_attitude) {
		RealJiveStateLog.stabilization_attitude = stabilization_attitude;
		
	}

	public static void setJniImuNps(String imuNpsStatus) {
		RealJiveStateLog.imuNpsStatus = imuNpsStatus;
		
	}

	public static void setjniSensorStatus(String jniSensorStatus) {
		RealJiveStateLog.jniSensorStatus = jniSensorStatus;
		
	}

	public static void setGpsSimNps(String gpsSimNps) {
		RealJiveStateLog.gpsSimNps = gpsSimNps;
		
	}
	
}

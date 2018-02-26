package jive;

import juav.autopilot.AutopilotArmingYaw;

/**
 * Created by adamczer on 3/19/17.
 */
public class RealJiveStateLog {
    public static boolean motorsOn;
    public static String motorStatus;
    public static String autopilotMode;
    public static String guidanceHMode;
    public static String guidanceVMode;
    public static boolean ahrsIsAligned;
    
    public static String cyclicStateLog; 
    public static String sensorState; 
    public static String navigationStateLog;
    public static String stabilization_none;
    public static String stabilization_rate;
    public static String stabilization_attitude;
	public static String imuNpsStatus;
	public static String jniSensorStatus;
	public static String gpsSimNps;
    
	
	public static String bciLog; 
	
	
	public static RealJiveStateLog log = new RealJiveStateLog();
	
	/*
    public static String getMotorStatus() {
        return motorStatus;
    }

    public static void setMotorStatus(String motorStatus) {
    		RealJiveStateLog.motorStatus = motorStatus;
    }

   public static void setnavigationStateLog(String n){
    		log.navigationStateLog = n;
   }
        public static boolean isAhrsIsAligned() {
        return ahrsIsAligned;
  }

    public static void setAhrsIsAligned(boolean ahrsIsAligned) {
    		log.ahrsIsAligned = ahrsIsAligned;
    }

    public static void setcyclicStateLog(String cyclicStateLog) {
		log.cyclicStateLog = cyclicStateLog;
    }

    public static void setsensorState(String sensorState) {
		log.sensorState = sensorState;
    }

    public static boolean isMotorsOn() {
        return motorsOn;
    }

    public static void setMotorsOn(boolean motorsOn) {
    		log.motorsOn = motorsOn;
    }

    public static String getAutopilotMode() {
        return autopilotMode;
    }

    public static void setAutopilotMode(String autopilotMode) {
    		log.autopilotMode = autopilotMode;
    }

    public static String getGuidanceHMode() {
        return guidanceHMode;
    }

    public static void setGuidanceHMode(String guidanceHMode) {
    		log.guidanceHMode = guidanceHMode;
    }

    public static String getGuidanceVMode() {
        return guidanceVMode;
    }

    public static void setGuidanceVMode(String guidanceVMode) {
    		log.guidanceVMode = guidanceVMode;
    }

	public static void setStabilizationNone(String stabilization_none) {
		log.stabilization_none = stabilization_none;

	}

	public static void setStabilizationRate(String stabilization_rate) {
		log.stabilization_rate = stabilization_rate;

	}

	public static void setStabilizationAttitude(String stabilization_attitude) {
		log.stabilization_attitude = stabilization_attitude;

	}

	public static void setJniImuNps(String imuNpsStatus) {
		log.imuNpsStatus = imuNpsStatus;

	}

	public static void setjniSensorStatus(String jniSensorStatus) {
		log.jniSensorStatus = jniSensorStatus;

	}

	public static void setGpsSimNps(String gpsSimNps) {
		log.gpsSimNps = gpsSimNps;

	}
	
	*/
	
}

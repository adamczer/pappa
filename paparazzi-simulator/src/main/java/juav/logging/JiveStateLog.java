package juav.logging;

import jive.logging.RealJiveStateLog;
import juav.autopilot.AutopilotArmingYaw;

/**
 * Created by adamczer on 3/19/17.
 */
public class JiveStateLog {
    private static boolean motorsOn;
    private static String motorStatus;
    private static String autopilotMode;
    private static String guidanceHMode;
    private static String guidanceVMode;
    private static boolean ahrsIsAligned;
    private static String stabilization_none;
    private static String stabilization_rate;
    private static String stabilization_attitude;
    
    private static String navigationStateLog;
    
    //New variables
    private static String cyclicStateLog; //NpsCyclicImpl
    private static String sensorState; //npsAutoPilotRotorCraft
	private static String imuNpsStatus;
	private static String jniSensorStatus;
	private static String gpsSimNps;
    
    public static void setcyclicStateLog(String cyclicStateLog){
    	if(JiveStateLog.cyclicStateLog != cyclicStateLog ){
    	JiveStateLog.cyclicStateLog = cyclicStateLog;
    	 RealJiveStateLog.setcyclicStateLog(cyclicStateLog);
    	}
    }
    
    public static void setnavigationStateLog(String navi){
    	if(JiveStateLog.navigationStateLog != navi ){
    	navigationStateLog = navi;
    	RealJiveStateLog.setnavigationStateLog(navigationStateLog);
    	}
    	
    }
    public static void setStabilizationNone(String s){
    	if(JiveStateLog.stabilization_none != s ){
    	stabilization_none = s;
    	RealJiveStateLog.setStabilizationNone(stabilization_none);
    	}
    }
    public static void setStabilizationRate(String s){
    	if(JiveStateLog.stabilization_rate != s ){
    	stabilization_rate = s;
    	RealJiveStateLog.setStabilizationRate(stabilization_rate);
    	}
    }
    
    public static void setStabilizationAttitude(String s){
    	if(JiveStateLog.stabilization_attitude != s ){
    	stabilization_attitude = s;
    	RealJiveStateLog.setStabilizationAttitude(stabilization_attitude);
    	}
    }
    
    public static void setsensorState(String sensorState){
    	if(JiveStateLog.sensorState!= sensorState ){
    	JiveStateLog.sensorState = sensorState;
    	 RealJiveStateLog.setsensorState(sensorState);
    	}
    }
    
    
    public static String getMotorStatus() {
        return motorStatus;
    }

    public static void setMotorStatus(String motorStatus) {
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

    public static String  getAutopilotMode() {
        return autopilotMode;
    }

    public static void setAutopilotMode(String autopilotMode) {
    	if(JiveStateLog.autopilotMode != autopilotMode) {
            JiveStateLog.autopilotMode = autopilotMode;
            RealJiveStateLog.setAutopilotMode(autopilotMode);
        }
    }

    public static String getGuidanceHMode() {
        return guidanceHMode;
    }

    public static void setGuidanceHMode(String guidanceHMode) {
    	if(JiveStateLog.guidanceHMode != guidanceHMode) {
            JiveStateLog.guidanceHMode = guidanceHMode;
            RealJiveStateLog.setGuidanceHMode(guidanceHMode);
    	}
    }

    public static String getGuidanceVMode() {
        return guidanceVMode;
    }

    public static void setGuidanceVMode(String guidanceVMode) {
    	if(JiveStateLog.guidanceVMode != guidanceVMode) {
            JiveStateLog.guidanceVMode = guidanceVMode;
            RealJiveStateLog.setGuidanceVMode(guidanceVMode);
        }
    }

	public static void setJniImuNps(String string) {
		if(JiveStateLog.imuNpsStatus!= string ){
		JiveStateLog.imuNpsStatus = string;
		RealJiveStateLog.setJniImuNps(imuNpsStatus);
		}
	}

	public static void setjniSensors(String string) {
		if(JiveStateLog.jniSensorStatus != string ){
		JiveStateLog.jniSensorStatus = string;
		RealJiveStateLog.setjniSensorStatus(jniSensorStatus);
		}
	}

	public static void setGpsSimNps(String string) {
		if(JiveStateLog.gpsSimNps != string ){
		JiveStateLog.gpsSimNps = string;
		RealJiveStateLog.setGpsSimNps(gpsSimNps);
		}
	}
}

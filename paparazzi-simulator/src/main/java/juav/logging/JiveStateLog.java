package juav.logging;

import jive.logging.RealJiveStateLog;
import juav.autopilot.AutopilotArmingYaw;

/**
 * Created by adamczer on 3/19/17.
 */
public class JiveStateLog {
    private static boolean motorsOn;
    private static String motorStatus = "";
    private static String autopilotMode = "";
    private static String guidanceHMode = "";
    private static String guidanceVMode = "";
    private static boolean ahrsIsAligned;
    private static String stabilization_none = "";
    private static String stabilization_rate = "";
    private static String stabilization_attitude = "";

    private static String navigationStateLog = "";

    //New variables
    private static String cyclicStateLog = ""; //NpsCyclicImpl
    private static String sensorState = ""; //npsAutoPilotRotorCraft
	private static String imuNpsStatus = "";
	private static String jniSensorStatus = "";
	private static String gpsSimNps = "";
    
    public static void setcyclicStateLog(String cyclicStateLog){
    	if(!JiveStateLog.cyclicStateLog.equals( cyclicStateLog )){
    	JiveStateLog.cyclicStateLog = cyclicStateLog;
    	 RealJiveStateLog.cyclicStateLog = (cyclicStateLog);
    	}
    }
    
    public static void setnavigationStateLog(String navi){
    	if(!JiveStateLog.navigationStateLog.equals( navi )){
    	navigationStateLog = navi;
    	RealJiveStateLog.navigationStateLog = (navigationStateLog);
    	}
    	
    }
    public static void setStabilizationNone(String s){
    	if(!JiveStateLog.stabilization_none.equals( s )){
    	stabilization_none = s;
    	RealJiveStateLog.stabilization_none = (stabilization_none);
    	}
    }
    public static void setStabilizationRate(String s){
    	if(!JiveStateLog.stabilization_rate.equals( s )){
    	stabilization_rate = s;
    	RealJiveStateLog.stabilization_rate = (stabilization_rate);
    	}
    }
    
    public static void setStabilizationAttitude(String s){
    	if(!JiveStateLog.stabilization_attitude.equals( s )){
    	stabilization_attitude = s;
    	RealJiveStateLog.stabilization_attitude=(stabilization_attitude);
    	}
    }
    
    public static void setsensorState(String sensorState){
    	if(!JiveStateLog.sensorState.equals(sensorState )){
    	JiveStateLog.sensorState = sensorState;
    	 RealJiveStateLog.sensorState = (sensorState);
    	}
    }
    
    
    public static String getMotorStatus() {
        return motorStatus;
    }

    public static void setMotorStatus(String motorStatus) {
    	if(!JiveStateLog.motorStatus.equals( motorStatus)) {
            JiveStateLog.motorStatus = motorStatus;
            RealJiveStateLog.motorStatus = (motorStatus);
        }

    }

    public static boolean isAhrsIsAligned() {
        return ahrsIsAligned;
    }

    public static void setAhrsIsAligned(boolean ahrsIsAligned) {
    	if(JiveStateLog.ahrsIsAligned != ahrsIsAligned) {
            JiveStateLog.ahrsIsAligned = ahrsIsAligned;
            RealJiveStateLog.ahrsIsAligned=(ahrsIsAligned);
        }
    }

    public static boolean isMotorsOn() {
        return motorsOn;
    }

    public static void setMotorsOn(boolean motorsOn) {
    	if(JiveStateLog.motorsOn != motorsOn) {
            JiveStateLog.motorsOn = motorsOn;
            RealJiveStateLog.motorsOn = (motorsOn);
        }
    }

    public static String  getAutopilotMode() {
        return autopilotMode;
    }

    public static void setAutopilotMode(String autopilotMode) {
    	if(!JiveStateLog.autopilotMode.equals( autopilotMode)) {
            JiveStateLog.autopilotMode = autopilotMode;
            RealJiveStateLog.autopilotMode = (autopilotMode);
        }
    }

    public static String getGuidanceHMode() {
        return guidanceHMode;
    }

    public static void setGuidanceHMode(String guidanceHMode) {
    	if(!JiveStateLog.guidanceHMode.equals( guidanceHMode)) {
            JiveStateLog.guidanceHMode = guidanceHMode;
            RealJiveStateLog.guidanceHMode = (guidanceHMode);
    	}
    }

    public static String getGuidanceVMode() {
        return guidanceVMode;
    }

    public static void setGuidanceVMode(String guidanceVMode) {
    	if(!JiveStateLog.guidanceVMode.equals( guidanceVMode)) {
            JiveStateLog.guidanceVMode = guidanceVMode;
            RealJiveStateLog.guidanceVMode = (guidanceVMode);
        }
    }

	public static void setJniImuNps(String string) {
		if(!JiveStateLog.imuNpsStatus.equals(string) ){
		JiveStateLog.imuNpsStatus = string;
		RealJiveStateLog.imuNpsStatus = (imuNpsStatus);
		}
	}

	public static void setjniSensors(String string) {
		if(!JiveStateLog.jniSensorStatus.equals( string) ){
		JiveStateLog.jniSensorStatus = string;
		RealJiveStateLog.jniSensorStatus = (jniSensorStatus);
		}
	}

	public static void setGpsSimNps(String string) {
		if(!JiveStateLog.gpsSimNps.equals(string) ){
		JiveStateLog.gpsSimNps = string;
		RealJiveStateLog.gpsSimNps = (gpsSimNps);
		}
	}
}

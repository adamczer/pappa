package ub.cse.juav.ardupilot;

import juav.autopilot.Autopilot;
import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.sensors.device.jni.*;
import ub.cse.juav.ardupilot.sensors.*;
import ub.cse.juav.ardupilot.time.ParameterizeTimer;
import ub.cse.juav.jni.fdm.FdmWrapper;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ArdupilotAutopilotRotorcraft extends PeriodicTask {
    JniGyroSensor gyroSensor;
    JniMagSensor magSensor;
    JniGpsSensor gpsSensor;
    JniAccelSensor accelSensor;
    JniBaroSensor baroSensor;
    JniImuNps jniImu;
    GpsSimNps gpsSimNps;
    Autopilot autopilot;
    private static double test = 0;

    @Override
    public void execute() {
        ardupilotAutopilotRunStep();
    }

    private void ardupilotAutopilotRunStep() {

        NativeTasksWrapper.npsElectricalRunStep(test);

        if(NativeTasksWrapper.npsAutopilotRunRadioStepAndShouldRunMainEvent(test+=1000)) {
            main_event();
        }

        if (gyroSensor.getData().isData_available()) {
            jniImu.imuFeedGyro(gyroSensor.getData());
            main_event();
            gyroSensor.getData().setData_available(false);
        }

        if (accelSensor.getData().isData_available()) {
            jniImu.imuFeedAccel(accelSensor.getData());
            main_event();
            accelSensor.getData().setData_available(false);
        }


        if (magSensor.getData().isData_available()) {
            jniImu.imuFeedMag(magSensor.getData());
            main_event();
            magSensor.getData().setData_available(false);
        }

        if (baroSensor.getData().isData_available()) {
            float pressure = (float) baroSensor.getData().getValue();
            NativeTasksWrapper.sendBarometricReading(pressure);
            main_event();
            baroSensor.getData().setData_available(false);
        }

        if (gpsSensor.getData().isData_available()) {
            gpsSimNps.gpsFeedValue(gpsSensor.getData());
            main_event();
            gpsSensor.getData().setData_available(false);
        }

        NativeTasksWrapper.npsAutopilotRunStepOverwriteAhrs();
        NativeTasksWrapper.npsAutopilotRunStepOverwriteIns();
/***************************************************************/
        if(ParameterizeTimer.sysTimeCheckAndAckTimerMainPeriodic()) {
            newControlLoop();
            NativeTasksWrapper.mainPeriodicJuavAutopilotPost();
            NativeTasksWrapper.handlePeriodicTasksFollowingMainPeriodicJuav();
            NativeTasksWrapper.npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();
        }
    }

    private void newControlLoop() {
        autopilot.autopilot_periodic();
        double [] rotorValues = new double[4];
        rotorValues[0]= FdmWrapper.getRotor0Value();
        rotorValues[1]= FdmWrapper.getRotor1Value();
        rotorValues[2]= FdmWrapper.getRotor2Value();
        rotorValues[3]= FdmWrapper.getRotor3Value();
//	System.out.println("rotors 0,1,2,3 = "+rotorValues[0]+", "+rotorValues[1]+", "+rotorValues[2]+", "+rotorValues[3]);
//	System.out.println("r0="+rotorValues[0]);
//	System.out.println("r1="+rotorValues[1]);
//	System.out.println("r2="+rotorValues[2]);
//	System.out.println("r3="+rotorValues[3]);
	int r0 = Math.max((int) ((rotorValues[0]*1000) + 1000),1000);
	int r1 = Math.max((int) ((rotorValues[1]*1000) + 1000),1000);
	int r2 = Math.max((int) ((rotorValues[2]*1000) + 1000),1000);
	int r3 = Math.max((int) ((rotorValues[3]*1000) + 1000),1000);
//	System.out.println("rotors pwm 0,1,2,3 = "+r0+", "+r1+", "+r2+", "+r3);
//	System.out.println("r00="+r0);
//        System.out.println("r11="+r1);
//        System.out.println("r22="+r2);
//        System.out.println("r33="+r3);
	ArdupilotBridge.setRcValue(0, r0);
        ArdupilotBridge.setRcValue(1, r1);
        ArdupilotBridge.setRcValue(2, r2);
        ArdupilotBridge.setRcValue(3, r3);
        ArdupilotBridge.flushRc();
    }

    private void main_event() {
        PaparazziNpsWrapper.mainEventPrior();
        autopilot.autopilot_on_rc_frame();
        PaparazziNpsWrapper.mainEventPost();
    }

    @Override
    public void init() {
        autopilot = new Autopilot();
        autopilot.autopilot_init();
    }

    public void setGyroSensor(JniGyroSensor gyroSensor) {
        this.gyroSensor = gyroSensor;
    }

    public void setMagSensor(JniMagSensor magSensor) {
        this.magSensor = magSensor;
    }

    public void setGpsSensor(JniGpsSensor gpsSensor) {
        this.gpsSensor = gpsSensor;
    }

    public void setAccelSensor(JniAccelSensor accelSensor) {
        this.accelSensor = accelSensor;
    }

    public void setJniImuNps(JniImuNps jniImu) {
        this.jniImu = jniImu;
    }

    public void setGpsSimNps(GpsSimNps gpsSimNps) {
        this.gpsSimNps = gpsSimNps;
    }

    public void setBaroSensor(JniBaroSensor baroSensor) {
        this.baroSensor = baroSensor;
    }
}

package ub.cse.juav.ardupilot;

import juav.autopilot.Autopilot;
import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.sensors.device.jni.*;
import ub.cse.juav.ardupilot.sensors.*;
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

    @Override
    public void execute() {
        ardupilotAutopilotRunStep();
    }

    private void ardupilotAutopilotRunStep() {

            main_event();

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
        long mainPeriodicStart = -1;
        long mainPeriodicEnd =-1;
        if(NativeTasksWrapper.sysTimeCheckAndAckTimerMainPeriodicJuav()) {
            mainPeriodicStart = System.nanoTime();
            newControlLoop();
            NativeTasksWrapper.mainPeriodicJuavAutopilotPost();
            mainPeriodicEnd=System.nanoTime();
            NativeTasksWrapper.handlePeriodicTasksFollowingMainPeriodicJuav();
            NativeTasksWrapper.npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();
        }
    }

    private void newControlLoop() {
        autopilot.autopilot_periodic();
//        NativeTasksWrapper.juavAutopilotPeriodic();
    }

    private void main_event() {
        PaparazziNpsWrapper.mainEventPrior();
        autopilot.autopilot_on_rc_frame();
        PaparazziNpsWrapper.mainEventPost();
    }

    private FileOutputStream fis;
    private long iterationCount;
    @Override
    public void init() {
        iterationCount =0;
        try {
            fis = new FileOutputStream("main_periodic.log");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

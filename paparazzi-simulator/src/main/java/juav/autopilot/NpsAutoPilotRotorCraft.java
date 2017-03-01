package juav.autopilot;

import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.sensors.device.jni.*;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by adamczer on 5/30/16.
 */
public class NpsAutoPilotRotorCraft extends PeriodicTask {
    JniGyroSensor gyroSensor;
    JniMagSensor magSensor;
    JniGpsSensor gpsSensor;
    JniAccelSensor accelSensor;
    JniBaroSensor baroSensor;
    JniImuNps jniImuNps;
    GpsSimNps gpsSimNps;
    Autopilot autopilot;

    @Override
    public void execute() {
        double time = PaparazziNpsWrapper.getNpsMainSimTime();
        npsAutopilotRunStep(time);
    }

    private void npsAutopilotRunStep(double time) {
        //Not needed it should decrement battery
        NativeTasksWrapper.npsElectricalRunStep(time);


        if(NativeTasksWrapper.npsAutopilotRunRadioStepAndShouldRunMainEvent(time)) {
            main_event();
        }

//        NativeTasksWrapper.npsSensorFeedStepGyro();
        if (gyroSensor.getData().isData_available()) {
            jniImuNps.imuFeedGyro(gyroSensor.getData());
            main_event();
            gyroSensor.getData().setData_available(false);
        }

//        NativeTasksWrapper.npsSensorFeedStepAccel();
        if (accelSensor.getData().isData_available()) {
            jniImuNps.imuFeedAccel(accelSensor.getData());
            main_event();
            accelSensor.getData().setData_available(false);
        }


//        NativeTasksWrapper.npsSensorFeedStepMag();
        if (magSensor.getData().isData_available()) {
            jniImuNps.imuFeedMag(magSensor.getData());
            main_event();
            magSensor.getData().setData_available(false);
        }

//        NativeTasksWrapper.npsSensorFeedStepBaro();
        if (baroSensor.getData().isData_available()) {
            float pressure = (float) baroSensor.getData().getValue();
            NativeTasksWrapper.sendBarometricReading(pressure);
            main_event();
            baroSensor.getData().setData_available(false);
        }

        //not used
//            #if USE_SONAR
//            if (nps_sensors_sonar_available()) {
//                float dist = (float) sensors.sonar.value;
//                AbiSendMsgAGL(AGL_SONAR_NPS_ID, dist);
//
//                uint16_t foo = 0;
//                DOWNLINK_SEND_SONAR(DefaultChannel, DefaultDevice, &foo, &dist);
//
//                main_event();
//            }
//            #endif

//        NativeTasksWrapper.npsSensorFeedStepGps();
        if (gpsSensor.getData().isData_available()) {
            gpsSimNps.gpsFeedValue(gpsSensor.getData());
            main_event();
            gpsSensor.getData().setData_available(false);
        }

        NativeTasksWrapper.npsAutopilotRunStepOverwriteAhrs();
        NativeTasksWrapper.npsAutopilotRunStepOverwriteIns();
/***************************************************************/
        long mainPeriodicStart = System.nanoTime();
        if(NativeTasksWrapper.sysTimeCheckAndAckTimerMainPeriodicJuav()) {
            newControlLoop();
            NativeTasksWrapper.mainPeriodicJuavAutopilotPost();
            NativeTasksWrapper.handlePeriodicTasksFollowingMainPeriodicJuav();
            NativeTasksWrapper.npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();
        }
        long mainPeriodicEnd = System.nanoTime();
        try {
            fis.write((""+(iterationCount++)+" "+mainPeriodicStart+" "+mainPeriodicEnd+" "+(mainPeriodicEnd-mainPeriodicStart)+"\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
//        oldControlLoop();
    }

    private void oldControlLoop() {
        NativeTasksWrapper.mainPeriodicJuavAutopilotPrior();
        if(NativeTasksWrapper.sysTimeCheckAndAckTimerMainPeriodicJuav()) {
//            NativeTasksWrapper.mainPeriodicJuavTest();//TEST main periodic
            NativeTasksWrapper.autopilotPeriodicPriorJuav();
            if(!NativeTasksWrapper.isAutopilotModeApModeKillJuav()) {
                boolean inFlight = NativeTasksWrapper.getAutopilotInFlightJuav();
                NativeTasksWrapper.guidanceHRunJuav(inFlight);
                if(NativeTasksWrapper.runStabilizationAttitudeRunJuav()) {
                    NativeTasksWrapper.guidanceHRunNativeTestJuav(inFlight); // test plumbing
//                    NativeTasksWrapper.guidanceHRunJuav(inFlight);
//                    stabilization_attitude_run_old(inFlight);
                }
            }
            NativeTasksWrapper.autopilotPeriodicPostJuav();//finaizes after guidance_h.c run
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
//        PaparazziNpsWrapper.mainEvent();
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

    public void setJniImuNps(JniImuNps jniImuNps) {
        this.jniImuNps = jniImuNps;
    }

    public void setGpsSimNps(GpsSimNps gpsSimNps) {
        this.gpsSimNps = gpsSimNps;
    }

    public void setBaroSensor(JniBaroSensor baroSensor) {
        this.baroSensor = baroSensor;
    }
}

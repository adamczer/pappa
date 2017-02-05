package juav.autopilot;

import juav.autopilot.commands.Commands;
import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.autopilot.stabilization.Stabilization;
import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.sensors.device.jni.*;
import ub.cse.juav.jni.nps.PaparazziNps;
import ub.cse.juav.jni.tasks.NativeTasks;

import static juav.autopilot.Autopilot.*;
import static juav.autopilot.commands.Commands.*;
import static juav.autopilot.guidance.GuidanceH.stabilizationAttitudeRun;
import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.stabilization_attitude_run_old;

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
        double time = PaparazziNps.getNpsMainSimTime();
        npsAutopilotRunStep(time);
    }

    private void npsAutopilotRunStep(double time) {
        //Not needed it should decrement battery
        NativeTasks.npsElectricalRunStep(time);


        if(NativeTasks.npsAutopilotRunRadioStepAndShouldRunMainEvent(time)) {
            main_event();
        }

//        NativeTasks.npsSensorFeedStepGyro();
        if (gyroSensor.getData().isData_available()) {
            jniImuNps.imuFeedGyro(gyroSensor.getData());
            main_event();
            gyroSensor.getData().setData_available(false);
        }

//        NativeTasks.npsSensorFeedStepAccel();
        if (accelSensor.getData().isData_available()) {
            jniImuNps.imuFeedAccel(accelSensor.getData());
            main_event();
            accelSensor.getData().setData_available(false);
        }


//        NativeTasks.npsSensorFeedStepMag();
        if (magSensor.getData().isData_available()) {
            jniImuNps.imuFeedMag(magSensor.getData());
            main_event();
            magSensor.getData().setData_available(false);
        }

//        NativeTasks.npsSensorFeedStepBaro();
        if (baroSensor.getData().isData_available()) {
            float pressure = (float) baroSensor.getData().getValue();
            NativeTasks.sendBarometricReading(pressure);
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

//        NativeTasks.npsSensorFeedStepGps();
        if (gpsSensor.getData().isData_available()) {
            gpsSimNps.gpsFeedValue(gpsSensor.getData());
            main_event();
            gpsSensor.getData().setData_available(false);
        }
        NativeTasks.npsAutopilotRunStepOverwriteAhrs();
        NativeTasks.npsAutopilotRunStepOverwriteIns();
/***************************************************************/
        if(NativeTasks.sysTimeCheckAndAckTimerMainPeriodicJuav()) {
            newControlLoop();
            NativeTasks.mainPeriodicJuavAutopilotPost();
            NativeTasks.handlePeriodicTasksFollowingMainPeriodicJuav();
            NativeTasks.npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();
        }
//        oldControlLoop();
    }

    private void oldControlLoop() {
        NativeTasks.mainPeriodicJuavAutopilotPrior();
        if(NativeTasks.sysTimeCheckAndAckTimerMainPeriodicJuav()) {
//            NativeTasks.mainPeriodicJuavTest();//TEST main periodic
            NativeTasks.autopilotPeriodicPriorJuav();
            if(!NativeTasks.isAutopilotModeApModeKillJuav()) {
                boolean inFlight = NativeTasks.getAutopilotInFlightJuav();
                NativeTasks.guidanceHRunJuav(inFlight);
                if(NativeTasks.runStabilizationAttitudeRunJuav()) {
                    NativeTasks.guidanceHRunNativeTestJuav(inFlight); // test plumbing
//                    NativeTasks.guidanceHRunJuav(inFlight);
//                    stabilization_attitude_run_old(inFlight);
                }
            }
            NativeTasks.autopilotPeriodicPostJuav();//finaizes after guidance_h.c run
        }
    }

    private void newControlLoop() {
        autopilot.autopilot_periodic();
//        NativeTasks.juavAutopilotPeriodic();
    }

    private void main_event() {
        PaparazziNps.mainEventPrior();
        autopilot.autopilot_on_rc_frame();
        PaparazziNps.mainEventPost();
//        PaparazziNps.mainEvent();
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

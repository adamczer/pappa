package juav.autopilot;

import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.autopilot.stabilization.JniStabilizationAttitudeComputation;
import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.sensors.device.jni.*;
import ub.cse.juav.jni.nps.PaparazziNps;
import ub.cse.juav.jni.tasks.NativeTasks;

import java.io.FileWriter;
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
    private static final boolean enableLogging = true;
    static FileWriter log;
    static int iter = 1;

    static {
        if(enableLogging) {
            try {
                log = new FileWriter("autopilot_run_step_juav.data");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void execute() {
        long start;
        if(enableLogging)
            start = System.nanoTime();
        double time = PaparazziNps.getNpsMainSimTime();
        npsAutopilotRunStep(time);
        if(enableLogging) {
            long finish = System.nanoTime();
            try {
                log.write("" + (iter++) + " " + (finish - start) + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void npsAutopilotRunStep(double time) {
        //Not needed it should decrement battery
        NativeTasks.npsElectricalRunStep(time);


        NativeTasks.npsAutopilotRunStepRadio(time);

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
            gyroSensor.getData().setData_available(false);
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
            gpsSensor.getData().setData_available(false);
            main_event();
            gpsSensor.getData().setData_available(false);
        }


        NativeTasks.npsAutopilotRunStepOverwriteAhrs();

        NativeTasks.npsAutopilotRunStepOverwriteIns();
/***************************************************************/
//        NativeTasks.npsAutopilotRunStepHandelPeriodicTasks(); //-> all c for this task
//        below -> guidance attitude compuatation in java then passed back.
        NativeTasks.mainPeriodicJuavAutopilotPrior();
        if(NativeTasks.sysTimeCheckAndAckTimerMainPeriodicJuav()) {
//            NativeTasks.mainPeriodicJuavTest();//TEST main periodic
            NativeTasks.autopilotPeriodicPriorJuav();
            if(!NativeTasks.isAutopilotModeApModeKillJuav()) {
                boolean inFlight = NativeTasks.getAutopilotInFlightJuav();
                NativeTasks.guidanceHRunJuav(inFlight);
                if(NativeTasks.runStabilizationAttitudeRunJuav()) {
//                    NativeTasks.guidanceHRunNativeTestJuav(inFlight); // test plumbing
                    NativeTasks.guidanceHRunJuav(inFlight);
                    JniStabilizationAttitudeComputation.stabilizationAttitudeRun(inFlight);
                    //TODO stabilization_attitude_quat_int.c->stabilization_attitude_run() collect metrics on this
                    // this code uses boolean inFlight;
                }
            }
            NativeTasks.autopilotPeriodicPostJuav();//finaizes after guidance_h.c run
        }
        NativeTasks.mainPeriodicJuavAutopilotPost();
        NativeTasks.handlePeriodicTasksFollowingMainPeriodicJuav();
/***************************************************************/
        NativeTasks.npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();
    }

    private void main_event() {
        PaparazziNps.mainEvent();
    }


    @Override
    public void init() {

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

package juav.autopilot;

import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.sensors.device.jni.JniAccelSensor;
import juav.simulator.tasks.sensors.device.jni.JniGpsSensor;
import juav.simulator.tasks.sensors.device.jni.JniGyroSensor;
import juav.simulator.tasks.sensors.device.jni.JniMagSensor;
import ub.cse.juav.jni.nps.PaparazziNps;
import ub.cse.juav.jni.tasks.NativeTasks;

/**
 * Created by adamczer on 5/30/16.
 */
public class NpsAutoPilotRotorCraft extends PeriodicTask {
    JniGyroSensor gyroSensor;
    JniMagSensor magSensor;
    JniGpsSensor gpsSensor;
    JniAccelSensor accelSensor;
    JniImuNps jniImuNps;
    GpsSimNps gpsSimNps;

    @Override
    public void execute() {
        double time = PaparazziNps.getNpsMainSimTime();
        npsAutopilotRunStep(time);
    }

    private void npsAutopilotRunStep(double time) {

            //Not needed it should decrement battery
            NativeTasks.npsElectricalRunStep(time);


        NativeTasks.npsAutopilotRunStepRadio(time);

            if (gyroSensor.getData().isData_available()) {
                jniImuNps.imuFeedGyro(gyroSensor.getData());
                jniImuNps.imuFeedAccel(accelSensor.getData());
                main_event();
            }

            if (magSensor.getData().isData_available()) {
                jniImuNps.imuFeedMag(magSensor.getData());
                main_event();
            }

        // Not used for this aircraft
//            if (barometricReading.isData_available()) {
//                float pressure = (float) sensors.baro.value;
//                AbiSendMsgBARO_ABS(BARO_SIM_SENDER_ID, pressure);
//                main_event();
//            }

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

            if (gpsSensor.getData().isData_available()) {
                gpsSimNps.gpsFeedValue(gpsSensor.getData());
                gpsSensor.getData().setData_available(false);
                main_event();
            }



        NativeTasks.npsAutopilotRunStepOverwriteAhrs();

        NativeTasks.npsAutopilotRunStepOverwriteIns();

        NativeTasks.npsAutopilotRunStepHandelPeriodicTasks();

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
}

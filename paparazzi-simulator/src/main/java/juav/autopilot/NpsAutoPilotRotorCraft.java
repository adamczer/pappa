package juav.autopilot;

import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.ImuNps;
import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.sensors.readings.BarometricReading;
import juav.simulator.tasks.sensors.readings.GpsReading;
import juav.simulator.tasks.sensors.readings.GyroReading;
import juav.simulator.tasks.sensors.readings.MagneticReading;
import ub.cse.juav.jni.nps.PaparazziNps;
import ub.juav.airborne.math.structs.orientation.OrientationReps;

/**
 * Created by adamczer on 5/30/16.
 */
public class NpsAutoPilotRotorCraft extends PeriodicTask {
    OrientationReps orientation;

    BarometricReading barometricReading;
    GyroReading gyroReading;
    MagneticReading magneticReading;
    GpsReading gpsReading;

    ImuNps imuNps;
    GpsSimNps gpsSimNps;

    @Override
    public void execute() {
        double time = PaparazziNps.getNpsMainSimTime();
        nps_autopilot_run_step(time);
    }

    private void nps_autopilot_run_step(double time) {

            //Not needed it should decrement battery
            // Actually not implemented in paparazzi
//            nps_electrical_run_step(time);

//            #if RADIO_CONTROL && !RADIO_CONTROL_TYPE_DATALINK
            if (nps_radio_control_available(time)) {
                radio_control_feed();
                main_event();
            }
//            #endif

            if (gyroReading.isData_available()) {
                imuNps.imu_feed_gyro_accel();
                main_event();
            }

            if (magneticReading.isData_available()) {
                imuNps.imu_feed_mag();
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

            if (gpsReading.isData_available()) {
                gpsSimNps.gps_feed_value();
                main_event();
            }


//  These were skipped for config.
//            if (nps_bypass_ahrs) {
//                sim_overwrite_ahrs();
//            }
//
//            if (nps_bypass_ins) {
//                sim_overwrite_ins();
//            }

            handle_periodic_tasks();

  /* scale final motor commands to 0-1 for feeding the fdm */
            for (uint8_t i = 0; i < NPS_COMMANDS_NB; i++) {
                autopilot.commands[i] = (double)motor_mixing.commands[i] / MAX_PPRZ;
            }

    }

    private void main_event() {

    }



    @Override
    public void init() {

    }
}

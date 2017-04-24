package juav.simulator.tasks.sensors.device.jni;
import jive.logging.StateTransitions;
import juav.logging.JiveStateLog;
import juav.simulator.nps.random.NpsRandom;
import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.GpsReading;
import ub.cse.juav.jni.fdm.FdmWrapper;
import ub.cse.juav.jni.fdm.JniFdm;
import ub.cse.juav.jni.gps.GpsNative;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.geodetic.PprzGeodeticDouble;
import ub.juav.airborne.math.structs.algebra.Vect3;
import ub.juav.airborne.math.structs.geodetic.EcefCoor;
import ub.juav.airborne.math.structs.geodetic.LlaCoor;

/**
 * Created by adamczer on 5/25/16.
 */
public class JniGpsSensor extends ISensor<GpsReading> {
    //perfect gps from nps_sensors_params_default.h
    private static final double NPS_GPS_SPEED_NOISE_STD_DEV = 0.;
    private static final double NPS_GPS_SPEED_LATENCY = 0.;
    private static final double NPS_GPS_POS_NOISE_STD_DEV = 0.001;
    private static final double NPS_GPS_POS_BIAS_INITIAL_X = 0.;
    private static final double NPS_GPS_POS_BIAS_INITIAL_Y = 0.;
    private static final double NPS_GPS_POS_BIAS_INITIAL_Z = 0.;
    private static final double NPS_GPS_POS_BIAS_RANDOM_WALK_STD_DEV_X = 0.;
    private static final double NPS_GPS_POS_BIAS_RANDOM_WALK_STD_DEV_Y = 0.;
    private static final double NPS_GPS_POS_BIAS_RANDOM_WALK_STD_DEV_Z = 0.;
    private static final double NPS_GPS_POS_LATENCY = 0.;

    private static final double NPS_GPS_DT = (1.d / 4.d);


    @Override
    protected void executePeriodic() {
        double time = PaparazziNpsWrapper.getNpsMainSimTime();
//        NativeTasksWrapper.npsSensorFdmCopyGps(time);if(true)return;

        if (time < data.getNext_update()) {
            return;
        }
        StateTransitions.instance.add_transition(new String[]{"Copy Sensor Values"});
        //StateTransitions.instance.add_transition(new String[]{"Copy GPS"});
        JiveStateLog.setjniSensors("GpsSensor_execute_Periodic");


  /*
   * simulate speed sensor
   */
        EcefCoor<Double> cur_speed_reading = new EcefCoor<>();
        cur_speed_reading.setX(FdmWrapper.getFdmEcefEcefVelX());
        cur_speed_reading.setY(FdmWrapper.getFdmEcefEcefVelY());
        cur_speed_reading.setZ(FdmWrapper.getFdmEcefEcefVelZ());
  /* add a gaussian noise */
        NpsRandom.double_vect3_add_gaussian_noise(cur_speed_reading, data.getSpeed_noise_std_dev());

  /* store that for later and retrieve a previously stored data */
        //TODO is this required ???? not with latency = 0
//        UpdateSensorLatency(time, &cur_speed_reading, &gps->speed_history, gps->speed_latency, &gps->ecef_vel);
//        GpsNativeWrapper.gps_feed_latency_speed_juav(time,cur_speed_reading.getX(),cur_speed_reading.getY(),cur_speed_reading.getZ());
        data.setEcef_vel(cur_speed_reading);

  /*
   * simulate position sensor
   */
  /* compute gps error readings */

        Vect3<Double> pos_error = new Vect3<>();
        PprzAlgebra.VECT3_COPY(pos_error, data.getPos_bias_initial());
  /* add a gaussian noise */
        NpsRandom.double_vect3_add_gaussian_noise(pos_error, data.getSpeed_noise_std_dev());
  /* update random walk bias and add it to error*/
        NpsRandom.double_vect3_update_random_walk(data.getPos_bias_random_walk_value(), data.getPos_bias_random_walk_std_dev(), NPS_GPS_DT, 5.d);
        PprzAlgebra.VECT3_ADD(pos_error, data.getPos_bias_random_walk_value());
//
//  /* add error to current pos reading */
        EcefCoor<Double> cur_pos_reading = EcefCoor.EcefCoorDouble();
        cur_pos_reading.setX(FdmWrapper.getFdmEcefPosX());
        cur_pos_reading.setY(FdmWrapper.getFdmEcefPosY());
        cur_pos_reading.setZ(FdmWrapper.getFdmEcefPosZ());
        PprzAlgebra.VECT3_ADD(cur_pos_reading, pos_error);
        data.setEcef_pos(cur_pos_reading);

  /* store that for later and retrieve a previously stored data */
        //TODO is this required????not with latency = 0
//        UpdateSensorLatency(time, & cur_pos_reading,&gps -> pos_history, gps -> pos_latency,&gps -> ecef_pos);
//        GpsNativeWrapper.gps_feed_latency_pos_juav(time,cur_pos_reading.getX(),cur_pos_reading.getY(),cur_pos_reading.getZ());
//
//  /*
//   * simulate lla pos
//   */
//  /* convert current ecef reading to lla */
        LlaCoor<Double> cur_lla_reading = new LlaCoor<>();
        PprzGeodeticDouble.lla_of_ecef_d(cur_lla_reading, cur_pos_reading);
        data.setLla_pos(cur_lla_reading);
  /* store that for later and retrieve a previously stored data */
        //TODO is this required????not with latency = 0
//        UpdateSensorLatency(time, & cur_lla_reading,&gps -> lla_history, gps -> pos_latency,&gps -> lla_pos);
//        GpsNativeWrapper.gps_feed_latency_lla_juav(time,cur_lla_reading.getLat(),cur_lla_reading.getLon(),cur_lla_reading.getAlt());

        double cur_hmsl_reading = FdmWrapper.getHmsl();

        data.setHmsl(cur_hmsl_reading);
        //TODO is this required????not with latency = 0
//        UpdateSensorLatency_Single(time, & cur_hmsl_reading,&gps -> hmsl_history, gps -> pos_latency,&gps -> hmsl);
//        GpsNativeWrapper.gps_feed_latency_hmsl_juav(time,cur_hmsl_reading);

        data.setNext_update(data.getNext_update()+NPS_GPS_DT);
        data.setData_available(true);
    }

    @Override
    public void init() {
    	JiveStateLog.setjniSensors("GpsSensor_init");
//        NativeTasksWrapper.npsSensorInitGps(0);
    	
        data = new GpsReading();

        EcefCoor<Double> ecef_pos = new EcefCoor<>();
        PprzAlgebra.VECT3_ASSIGN(ecef_pos, 0.d, 0.d, 0.d);
        data.setEcef_pos(ecef_pos);

        EcefCoor<Double> ecef_vel = new EcefCoor<>();
        PprzAlgebra.VECT3_ASSIGN(ecef_vel, 0.d, 0.d, 0.d);
        data.setEcef_vel(ecef_vel);

        data.setHmsl(0.d);
        data.setPos_latency(NPS_GPS_POS_LATENCY);
        data.setSpeed_latency(NPS_GPS_SPEED_LATENCY);

        Vect3<Double> pos_noise_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(pos_noise_std_dev,
                NPS_GPS_POS_NOISE_STD_DEV, NPS_GPS_POS_NOISE_STD_DEV, NPS_GPS_POS_NOISE_STD_DEV);
        data.setPos_noise_std_dev(pos_noise_std_dev);

        Vect3<Double> speed_noise_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(speed_noise_std_dev, NPS_GPS_SPEED_NOISE_STD_DEV, NPS_GPS_SPEED_NOISE_STD_DEV, NPS_GPS_SPEED_NOISE_STD_DEV);
        data.setSpeed_noise_std_dev(speed_noise_std_dev);

        Vect3<Double> pos_bias_initial = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(pos_bias_initial, NPS_GPS_POS_BIAS_INITIAL_X, NPS_GPS_POS_BIAS_INITIAL_Y, NPS_GPS_POS_BIAS_INITIAL_Z);
        data.setPos_bias_initial(pos_bias_initial);

        Vect3<Double> pos_bias_random_walk_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(pos_bias_random_walk_std_dev, NPS_GPS_POS_BIAS_RANDOM_WALK_STD_DEV_X, NPS_GPS_POS_BIAS_RANDOM_WALK_STD_DEV_Y, NPS_GPS_POS_BIAS_RANDOM_WALK_STD_DEV_Z);
        data.setPos_bias_random_walk_std_dev(pos_bias_random_walk_std_dev);

        Vect3<Double> pos_bias_random_walk_value = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(pos_bias_random_walk_value, 0.d, 0.d, 0.d);
        data.setPos_bias_random_walk_value(pos_bias_random_walk_value);

        data.setNext_update(0);
        data.setData_available(false);
        PprzAlgebra.VECT3_ASSIGN(data.getPos_bias_random_walk_value(),0.d,0.d,0.d);
    }
}

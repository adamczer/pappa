package ub.cse.juav.ardupilot.sensors;

import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.device.jni.JniGpsSensor;
import juav.simulator.tasks.sensors.device.jni.SensorLoggings;
import juav.simulator.tasks.sensors.readings.GpsReading;
import org.joda.time.DateTime;
import ub.cse.juav.ardupilot.ArdupilotBridge;
import ub.cse.juav.ardupilot.time.ParameterizeTimer;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.geodetic.PprzGeodeticDouble;
import ub.juav.airborne.math.structs.algebra.Vect3;
import ub.juav.airborne.math.structs.geodetic.EcefCoor;
import ub.juav.airborne.math.structs.geodetic.LlaCoor;

public class ArdupilotJniGpsSensor extends JniGpsSensor {
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

    long previousReadingTime = 0;
    double [] previousEcefXyz ={0,0,0};

    @Override
    protected void executePeriodic() {
        if(!ParameterizeTimer.shouldReadGpsSensor())
            return;

        ArdupilotBridge.updateGps();

        long readTime = ArdupilotBridge.getGpsReadTime();
        double latitude = ArdupilotBridge.getGpsLatitude();
        double longitude = ArdupilotBridge.getGpsLongitude();
        double altitude = ArdupilotBridge.getGpsAltitude();
        
//        double latitude = 42.6036861;
//        double longitude = -78.5898838;
//        double altitude = 17059.99;

        double[] xyz = lla2ecef(latitude,longitude,altitude);
//        System.out.println("Lat: "+latitude+", Lon: "+longitude+", Alt: "+altitude);
//        System.out.println("X: "+xyz[0]+", Y: "+xyz[1]+", Z: "+xyz[2]);

        EcefCoor<Double> cur_pos_reading = EcefCoor.EcefCoorDouble();
        cur_pos_reading.setX(xyz[0]);
        cur_pos_reading.setY(xyz[1]);
        cur_pos_reading.setZ(xyz[2]);
        data.setEcef_pos(cur_pos_reading);

        EcefCoor<Double> cur_speed_reading = new EcefCoor<>();
        if(previousReadingTime!=0) {
            long delta = (previousReadingTime /*millis*/ - readTime/*millis*/)/1000; // ->seconds
            cur_speed_reading.setX((xyz[0] - previousEcefXyz[0])/delta); //m/s
            cur_speed_reading.setY((xyz[1] - previousEcefXyz[1])/delta); //m/s
            cur_speed_reading.setZ((xyz[2] - previousEcefXyz[2])/delta); //m/s
        } else {
            //default to 0 as no prev reading
            cur_speed_reading.setX(0d);
            cur_speed_reading.setY(0d);
            cur_speed_reading.setZ(0d);
        }
        previousEcefXyz = xyz;
        previousReadingTime = readTime;
        data.setEcef_vel(cur_speed_reading);

        LlaCoor<Double> cur_lla_reading = new LlaCoor<>();
        PprzGeodeticDouble.lla_of_ecef_d(cur_lla_reading, cur_pos_reading);
        data.setLla_pos(cur_lla_reading);

        double cur_hmsl_reading = altitude;

        data.setHmsl(cur_hmsl_reading);

        data.setNext_update(data.getNext_update()+NPS_GPS_DT);
        data.setData_available(true);
        SensorLoggings.setGpsReadings(data);
    }
    // from https://gist.github.com/klucar/1536194/49053140bb9df5956c5e9d8782e1d987cd2ae4c0
    // WGS84 ellipsoid constants
    private static final double a = 6378137; // radius
    private static final double e = 8.1819190842622e-2;  // eccentricity

    private static final double esq = Math.pow(e,2);

    private static double[] lla2ecef(double latD, double lonD, double alt){
        double lat = Math.toRadians(latD);
        double lon = Math.toRadians(lonD);
        double N = a / Math.sqrt(1 - esq * Math.pow(Math.sin(lat),2) );

        double x = (N+alt) * Math.cos(lat) * Math.cos(lon);
        double y = (N+alt) * Math.cos(lat) * Math.sin(lon);
        double z = ((1-esq) * N + alt) * Math.sin(lat);

        double[] ret = {x, y, z};
        return ret;
    }

    @Override
    public void init() {
        ArdupilotBridge.initGps();
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

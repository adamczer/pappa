package juav.simulator.tasks.sensors.readings;

import ub.juav.airborne.math.structs.algebra.Vect3;
import ub.juav.airborne.math.structs.geodetic.EcefCoor;
import ub.juav.airborne.math.structs.geodetic.LlaCoor;

/**
 * Created by adamczer on 2/29/16.
 */
public class GpsReading {
    EcefCoor<Double> ecef_pos;
    EcefCoor<Double> ecef_vel;
    LlaCoor<Double>  lla_pos;
    double hmsl;
    Vect3<Double> pos_noise_std_dev;
    Vect3<Double>  speed_noise_std_dev;
    Vect3<Double>  pos_bias_initial;
    Vect3<Double>  pos_bias_random_walk_std_dev;
    Vect3<Double>  pos_bias_random_walk_value;
    double pos_latency;
    double speed_latency;
//    GSList *hmsl_history; TODO I dont think this is used
//    GSList *pos_history;  TODO I dont think this is used
//    GSList *lla_history;  TODO I dont think this is used
//    GSList *speed_history;TODO I dont think this is used
    double next_update;
    boolean data_available;
}

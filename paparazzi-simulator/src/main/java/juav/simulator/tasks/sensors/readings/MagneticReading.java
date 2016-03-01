package juav.simulator.tasks.sensors.readings;

import ub.juav.airborne.math.structs.algebra.Vect3;

/**
 * Created by adamczer on 2/29/16.
 */
public class MagneticReading {
    Vect3<Double> value;
    int min;
    int max;
    Vect3<Double> sensitivity;
    Vect3<Double> neutral;
    Vect3<Double> noise_std_dev;
    Vect3<Double>  imu_to_sensor_rmat;
    double       next_update;
    boolean       data_available;
}

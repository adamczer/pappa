package juav.simulator.tasks.sensors.readings;

import ub.juav.airborne.math.structs.algebra.Mat33;
import ub.juav.airborne.math.structs.algebra.Vect3;

/**
 * Created by adamczer on 2/29/16.
 */
public class GyroReading {
    Vect3<Double> value;
    int min;
    int max;
    Mat33<Double> sensitivity;
    Mat33<Double> neutral;
    Mat33<Double> noise_std_dev;
    Mat33<Double> bias_initial;
    Mat33<Double> bias_random_walk_std_dev;
    Mat33<Double> bias_random_walk_value;
    double next_update;
    boolean data_available;

}

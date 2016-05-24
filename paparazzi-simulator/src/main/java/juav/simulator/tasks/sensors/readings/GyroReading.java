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
    Vect3<Double> neutral;
    Vect3<Double> noise_std_dev;
    Vect3<Double> bias_initial;
    Vect3<Double> bias_random_walk_std_dev;
    Vect3<Double> bias_random_walk_value;
    double next_update;
    boolean data_available;

    public Vect3<Double> getValue() {
        return value;
    }

    public void setValue(Vect3<Double> value) {
        this.value = value;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Mat33<Double> getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(Mat33<Double> sensitivity) {
        this.sensitivity = sensitivity;
    }

    public Vect3<Double> getNeutral() {
        return neutral;
    }

    public void setNeutral(Vect3<Double> neutral) {
        this.neutral = neutral;
    }

    public Vect3<Double> getNoise_std_dev() {
        return noise_std_dev;
    }

    public void setNoise_std_dev(Vect3<Double> noise_std_dev) {
        this.noise_std_dev = noise_std_dev;
    }

    public Vect3<Double> getBias_initial() {
        return bias_initial;
    }

    public void setBias_initial(Vect3<Double> bias_initial) {
        this.bias_initial = bias_initial;
    }

    public Vect3<Double> getBias_random_walk_std_dev() {
        return bias_random_walk_std_dev;
    }

    public void setBias_random_walk_std_dev(Vect3<Double> bias_random_walk_std_dev) {
        this.bias_random_walk_std_dev = bias_random_walk_std_dev;
    }

    public Vect3<Double> getBias_random_walk_value() {
        return bias_random_walk_value;
    }

    public void setBias_random_walk_value(Vect3<Double> bias_random_walk_value) {
        this.bias_random_walk_value = bias_random_walk_value;
    }

    public double getNext_update() {
        return next_update;
    }

    public void setNext_update(double next_update) {
        this.next_update = next_update;
    }

    public boolean isData_available() {
        return data_available;
    }

    public void setData_available(boolean data_available) {
        this.data_available = data_available;
    }
}

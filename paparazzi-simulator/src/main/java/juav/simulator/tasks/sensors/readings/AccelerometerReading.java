package juav.simulator.tasks.sensors.readings;

import ub.juav.airborne.math.structs.algebra.Mat33;
import ub.juav.airborne.math.structs.algebra.Vect3;

/**
 * Created by adamczer on 2/29/16.
 */
public class AccelerometerReading {
    Vect3<Double> value;
    double min;
    double max;
    Mat33<Double> sensitivity;
    Vect3<Double>  neutral;
    Vect3<Double>  noise_std_dev;
    Vect3<Double>  bias;
    double next_update;
    boolean data_available;

    public Vect3<Double> getValue() {
        return value;
    }

    public void setValue(Vect3<Double> value) {
        this.value = value;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
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

    public Vect3<Double> getBias() {
        return bias;
    }

    public void setBias(Vect3<Double> bias) {
        this.bias = bias;
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

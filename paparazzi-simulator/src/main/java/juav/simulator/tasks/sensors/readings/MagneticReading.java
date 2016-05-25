package juav.simulator.tasks.sensors.readings;

import ub.juav.airborne.math.structs.algebra.Mat33;
import ub.juav.airborne.math.structs.algebra.RMat;
import ub.juav.airborne.math.structs.algebra.Vect3;

/**
 * Created by adamczer on 2/29/16.
 */
public class MagneticReading {
    Vect3<Double> value;
    int min;
    int max;
    Mat33<Double> sensitivity;
    Vect3<Double> neutral;
    Vect3<Double> noise_std_dev;
    RMat<Double> imu_to_sensor_rmat;
    double       next_update;
    boolean       data_available;

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

    public RMat<Double> getImu_to_sensor_rmat() {
        return imu_to_sensor_rmat;
    }

    public void setImu_to_sensor_rmat(RMat<Double> imu_to_sensor_rmat) {
        this.imu_to_sensor_rmat = imu_to_sensor_rmat;
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

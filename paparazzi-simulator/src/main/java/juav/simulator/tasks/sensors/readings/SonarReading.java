package juav.simulator.tasks.sensors.readings;

/**
 * Created by adamczer on 2/29/16.
 */
public class SonarReading {
    double value;          ///< sonar reading in meters
    double offset;         ///< offset in meters
    double noise_std_dev;  ///< noise standard deviation
    double next_update;
    boolean data_available;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getOffset() {
        return offset;
    }

    public void setOffset(double offset) {
        this.offset = offset;
    }

    public double getNoise_std_dev() {
        return noise_std_dev;
    }

    public void setNoise_std_dev(double noise_std_dev) {
        this.noise_std_dev = noise_std_dev;
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

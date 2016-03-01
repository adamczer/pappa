package juav.simulator.tasks.sensors.readings;

/**
 * Created by adamczer on 2/29/16.
 */
public class BarometricReading {
    double  value;          ///< pressure in Pascal
    double  noise_std_dev;  ///< noise standard deviation
    double  next_update;
    boolean  data_available;
}

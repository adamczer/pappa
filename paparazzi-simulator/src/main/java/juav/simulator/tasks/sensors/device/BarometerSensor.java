package juav.simulator.tasks.sensors.device;

import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.BarometricReading;

/**
 * Created by adamczer on 2/29/16.
 */
public class BarometerSensor extends ISensor<BarometricReading> {
    @Override
    protected void executePeriodic() {
        BarometricReading reading = null;

    }

    @Override
    public void init() {

    }
}

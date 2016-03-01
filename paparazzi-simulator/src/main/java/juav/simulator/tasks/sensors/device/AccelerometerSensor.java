package juav.simulator.tasks.sensors.device;

import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.AccelerometerReading;

/**
 * Created by adamczer on 2/29/16.
 */
public class AccelerometerSensor extends ISensor<AccelerometerReading>{

    @Override
    protected void executePeriodic() {
        AccelerometerReading accel = null;

        setReading(accel);
    }
}

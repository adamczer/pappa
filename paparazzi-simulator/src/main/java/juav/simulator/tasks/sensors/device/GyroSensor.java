package juav.simulator.tasks.sensors.device;

import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.GyroReading;

/**
 * Created by adamczer on 2/29/16.
 */
public class GyroSensor extends ISensor<GyroReading> {
    @Override
    protected void executePeriodic() {
        GyroReading reading = null;

    }

    @Override
    public void init() {

    }
}

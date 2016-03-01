package juav.simulator.tasks.sensors.device;

import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.SonarReading;

/**
 * Created by adamczer on 2/29/16.
 */
public class SonarSensor extends ISensor<SonarReading> {
    @Override
    protected void executePeriodic() {
        SonarReading reading = null;

        setReading(reading);
    }
}

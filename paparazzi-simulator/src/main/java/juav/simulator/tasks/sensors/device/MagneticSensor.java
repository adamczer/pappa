package juav.simulator.tasks.sensors.device;

import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.MagneticReading;

/**
 * Created by adamczer on 2/29/16.
 */
public class MagneticSensor extends ISensor<MagneticReading> {
    @Override
    protected void executePeriodic() {
        MagneticReading reading = null;

        setReading(reading);
    }

    @Override
    public void init() {

    }
}

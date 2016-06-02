package juav.autopilot.gps;

import juav.simulator.tasks.sensors.readings.GpsReading;

/**
 * Created by adamczer on 6/1/16.
 */
public interface IGpsNps {
    void gpsFeedValue(GpsReading reading);
}

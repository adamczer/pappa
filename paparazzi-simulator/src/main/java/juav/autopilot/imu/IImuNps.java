package juav.autopilot.imu;

import juav.simulator.tasks.sensors.readings.AccelerometerReading;
import juav.simulator.tasks.sensors.readings.GyroReading;
import juav.simulator.tasks.sensors.readings.MagneticReading;

/**
 * Created by adamczer on 6/1/16.
 */
public interface IImuNps {
    void imuFeedGyro(GyroReading reading);
    void imuFeedAccel(AccelerometerReading reading);
    void imuFeedMag(MagneticReading reading);
    void imuPeriodic();
    void imuImplInit();
}

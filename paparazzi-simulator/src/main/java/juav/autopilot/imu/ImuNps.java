package juav.autopilot.imu;

import juav.simulator.tasks.sensors.readings.AccelerometerReading;
import juav.simulator.tasks.sensors.readings.GyroReading;
import juav.simulator.tasks.sensors.readings.MagneticReading;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;

/**
 * Created by adamczer on 5/30/16.
 */
public class ImuNps extends Imu{
    private boolean gyro_available = false;
    private boolean mag_available = false;
    private boolean accel_available = false;

    GyroReading gyroReading;
    AccelerometerReading accReading;
    MagneticReading magReading;

    public void imu_feed_gyro_accel() {
        PprzAlgebra.RATES_ASSIGN(gyro_unscaled, gyroReading.getValue().getX(),gyroReading.getValue().getY(),gyroReading.getValue().getZ());
        PprzAlgebra.VECT3_ASSIGN(accel_unscaled, accReading.getValue().getX(), accReading.getValue().getY(), accReading.getValue().getZ());

        accel_available = true;
        gyro_available = true;
    }

    public void imu_feed_mag() {
        PprzAlgebra.VECT3_ASSIGN(mag_unscaled,magReading.getValue().getX(),magReading.getValue().getY(),magReading.getValue().getZ());
        mag_available = true;
    }

    // does not do anything
    public void imu_periodic()
    {
    }

    void imu_impl_init(void)
    {

        gyro_available = false;
        mag_available = false;
        accel_available = false;

    }
}

package juav.autopilot.imu;

import juav.simulator.tasks.sensors.readings.AccelerometerReading;
import juav.simulator.tasks.sensors.readings.GyroReading;
import juav.simulator.tasks.sensors.readings.MagneticReading;
import ub.cse.juav.jni.imu.ImuNative;
import ub.cse.juav.jni.imu.ImuWrapper;

/**
 * Created by adamczer on 5/30/16.
 */
public class JniImuNps extends Imu implements IImuNps{
    boolean gyro_available = false;
    boolean accel_available = false;
    boolean mag_available = false;

    public void imuFeedGyro(GyroReading gyroReading) {
        ImuWrapper.imuFeedGyro(gyroReading.getValue().getX(),gyroReading.getValue().getY(),gyroReading.getValue().getZ());
//        PprzAlgebra.RATES_ASSIGN(imuReading.gyro_unscaled, gyroReading.getValue().getX(),gyroReading.getValue().getY(),gyroReading.getValue().getZ());
        gyro_available = true;
    }

    public void imuFeedAccel(AccelerometerReading accReading) {
//        System.out.println("Accel Reading xyz = "+accReading.getValue().getX()+","+ accReading.getValue().getY()+","+ accReading.getValue().getZ());
        ImuWrapper.imuFeedAccel(accReading.getValue().getX(), accReading.getValue().getY(), accReading.getValue().getZ());
//        PprzAlgebra.VECT3_ASSIGN(imuReading.accel_unscaled, accReading.getValue().getX(), accReading.getValue().getY(), accReading.getValue().getZ());
        accel_available = true;
    }

    public void imuFeedMag(MagneticReading magReading) {
        ImuWrapper.imuFeedMag(magReading.getValue().getX(),magReading.getValue().getY(),magReading.getValue().getZ());
//        PprzAlgebra.VECT3_ASSIGN(imuReading.mag_unscaled,magReading.getValue().getX(),magReading.getValue().getY(),magReading.getValue().getZ());
        mag_available = true;
    }

    // does not do anything
    public void imuPeriodic()
    {
    }

    public void imuImplInit()
    {
        gyro_available = false;
        mag_available = false;
        accel_available = false;
    }
}

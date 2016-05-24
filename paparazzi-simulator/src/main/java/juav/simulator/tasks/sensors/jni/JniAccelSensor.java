package juav.simulator.tasks.sensors.jni;

import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.AccelerometerReading;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.Mat33;
import ub.juav.airborne.math.structs.algebra.Vect3;

/**
 * Created by adamczer on 5/24/16.
 */
public class JniAccelSensor extends ISensor<AccelerometerReading> {


    private static final int IMU_ACCEL_X_SIGN = -1;//imu_b2.h
    private static final int IMU_ACCEL_Y_SIGN = -1; //imu_b2.h
    private static final int IMU_ACCEL_Z_SIGN = -1;//imu_b2.h

    private static final double IMU_ACCEL_X_SENS = 37.91;//imu_nps.h
    private static final double IMU_ACCEL_Y_SENS = 37.91;//imu_nps.h
    private static final double IMU_ACCEL_Z_SENS = 39.24;//imu_nps.h


    private static final int IMU_ACCEL_X_NEUTRAL = 11;//airframe.h in lisam_2
    private static final int IMU_ACCEL_Y_NEUTRAL = 11;//airframe.h in lisam_2
    private static final int IMU_ACCEL_Z_NEUTRAL = -25;//airframe.h in lisam_2
    //nps-sensor-params-default
    /*
 * Accelerometer
 */
/* ADXL345 configured to +-16g with 13bit resolution */
    private static final int NPS_ACCEL_MIN = -4095;
    private static final int NPS_ACCEL_MAX = 4095;
    /* ms-2 */
/* aka 2^10/ACCEL_X_SENS  */
    private static final double NPS_ACCEL_SENSITIVITY_XX = (IMU_ACCEL_X_SIGN * PprzAlgebraInt.ACCEL_BFP_OF_REAL(1. / IMU_ACCEL_X_SENS));
    private static final double NPS_ACCEL_SENSITIVITY_YY = (IMU_ACCEL_Y_SIGN * PprzAlgebraInt.ACCEL_BFP_OF_REAL(1. / IMU_ACCEL_Y_SENS));
    private static final double NPS_ACCEL_SENSITIVITY_ZZ = (IMU_ACCEL_Z_SIGN * PprzAlgebraInt.ACCEL_BFP_OF_REAL(1. / IMU_ACCEL_Z_SENS));
    //
    private static final int NPS_ACCEL_NEUTRAL_X = IMU_ACCEL_X_NEUTRAL;
    private static final int NPS_ACCEL_NEUTRAL_Y = IMU_ACCEL_Y_NEUTRAL;
    private static final int NPS_ACCEL_NEUTRAL_Z = IMU_ACCEL_Z_NEUTRAL;
    ///* m2s-4 */
    private static final double NPS_ACCEL_NOISE_STD_DEV_X = 5.e-2;
    private static final double NPS_ACCEL_NOISE_STD_DEV_Y = 5.e-2;
    private static final double NPS_ACCEL_NOISE_STD_DEV_Z = 5.e-2;
    ///* ms-2 */
    private static final int NPS_ACCEL_BIAS_X = 0;
    private static final int NPS_ACCEL_BIAS_Y = 0;
    private static final int NPS_ACCEL_BIAS_Z = 0;
    ///* s */
    private static final double NPS_ACCEL_DT = (1. / 512.);

    @Override
    protected void executePeriodic() {

    }

    @Override
    public void init() {
        data = new AccelerometerReading();
        Vect3<Double> value = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(value, 0, 0, 0);
        data.setValue(value);

        data.setMin(NPS_ACCEL_MIN);
        data.setMax(NPS_ACCEL_MAX);

        Mat33<Double> sensitivity = new Mat33<>();
        PprzAlgebra.MAT33_DIAG(sensitivity, NPS_ACCEL_SENSITIVITY_XX, NPS_ACCEL_SENSITIVITY_YY, NPS_ACCEL_SENSITIVITY_ZZ);
        data.setSensitivity(sensitivity);

        Vect3<Double> neutral = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(neutral, NPS_ACCEL_NEUTRAL_X, NPS_ACCEL_NEUTRAL_Y, NPS_ACCEL_NEUTRAL_Z);
        data.setNeutral(neutral);

        Vect3<Double> noise_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(noise_std_dev, NPS_ACCEL_NOISE_STD_DEV_X, NPS_ACCEL_NOISE_STD_DEV_Y, NPS_ACCEL_NOISE_STD_DEV_Z);
        data.setNoise_std_dev(noise_std_dev);

        Vect3<Double> bias = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(bias, NPS_ACCEL_BIAS_X, NPS_ACCEL_BIAS_Y, NPS_ACCEL_BIAS_Z);
        data.setBias(bias);

        data.setNext_update(0);
        data.setData_available(false);
    }
}

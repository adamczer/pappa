package juav.simulator.tasks.sensors.device.jni;

import jive.logging.StateTransitions;
import juav.logging.JiveStateLog;
import juav.simulator.nps.random.NpsRandom;
import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.AccelerometerReading;
import ub.cse.juav.jni.fdm.FdmWrapper;
import ub.cse.juav.jni.fdm.JniFdm;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraDouble;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.Mat33;
import ub.juav.airborne.math.structs.algebra.RMat;
import ub.juav.airborne.math.structs.algebra.Vect3;

/**
 * Created by adamczer on 5/24/16.
 */
public class JniAccelSensor extends ISensor<AccelerometerReading> {


    private static final double IMU_ACCEL_X_SIGN = 1.d;
    private static final double IMU_ACCEL_Y_SIGN = 1.d;
    private static final double IMU_ACCEL_Z_SIGN = 1.d;

    private static final double IMU_ACCEL_X_SENS = 37.91d;
    private static final double IMU_ACCEL_Y_SENS = 37.91d;
    private static final double IMU_ACCEL_Z_SENS = 39.24d;


    private static final double IMU_ACCEL_X_NEUTRAL =  26.095821;
    private static final double IMU_ACCEL_Y_NEUTRAL =  26.095821;
    private static final double IMU_ACCEL_Z_NEUTRAL =  26.095821;
    //nps-sensor-params-default
    /*
 * Accelerometer
 */
/* ADXL345 configured to +-16g with 13bit resolution */
    private static final double NPS_ACCEL_MIN = -4095;
    private static final double NPS_ACCEL_MAX = 4095;
    /* ms-2 */
/* aka 2^10/ACCEL_X_SENS  */
    private static final double NPS_ACCEL_SENSITIVITY_XX = (IMU_ACCEL_X_SIGN * PprzAlgebraInt.ACCEL_BFP_OF_REAL(1. / IMU_ACCEL_X_SENS));
    private static final double NPS_ACCEL_SENSITIVITY_YY = (IMU_ACCEL_Y_SIGN * PprzAlgebraInt.ACCEL_BFP_OF_REAL(1. / IMU_ACCEL_Y_SENS));
    private static final double NPS_ACCEL_SENSITIVITY_ZZ = (IMU_ACCEL_Z_SIGN * PprzAlgebraInt.ACCEL_BFP_OF_REAL(1. / IMU_ACCEL_Z_SENS));
    //
    private static final double NPS_ACCEL_NEUTRAL_X = IMU_ACCEL_X_NEUTRAL;
    private static final double NPS_ACCEL_NEUTRAL_Y = IMU_ACCEL_Y_NEUTRAL;
    private static final double NPS_ACCEL_NEUTRAL_Z = IMU_ACCEL_Z_NEUTRAL;
    ///* m2s-4 */
    private static final double NPS_ACCEL_NOISE_STD_DEV_X = 5.e-2;
    private static final double NPS_ACCEL_NOISE_STD_DEV_Y = 5.e-2;
    private static final double NPS_ACCEL_NOISE_STD_DEV_Z = 5.e-2;
    ///* ms-2 */
    private static final double NPS_ACCEL_BIAS_X = 0.05;
    private static final double NPS_ACCEL_BIAS_Y = 0.05;
    private static final double NPS_ACCEL_BIAS_Z = 0.05;
    ///* s */
    private static final double NPS_ACCEL_DT = (1. / 512.);

    @Override
    protected void executePeriodic() {
        double time = PaparazziNpsWrapper.getNpsMainSimTime();
//        NativeTasksWrapper.npsSensorFdmCopyAccel(time); if (true)return;

        if(time<data.getNext_update()) {
            return;
        }
        StateTransitions.instance.add_transition(new String[]{"Copy Accel"});
        JiveStateLog.setjniSensors("AccelSensor_execute_Periodic");

        RMat<Double> bodyToImu = RMat.RMatDouble();
        for(int i = 0; i<3; i++)
            for(int j = 0; j<3 ; j++)
                bodyToImu.setElement(FdmWrapper.getFdmBodyToImu(i,j),i,j);
//        System.out.println("bodytoimu = \n"+ bodyToImu);

// aquire required vector over jni
        Vect3<Double> bodyAccel = new Vect3<>();
        bodyAccel.setX(FdmWrapper.getFdmBodyAccelX());
        bodyAccel.setY(FdmWrapper.getFdmBodyAccelY());
        bodyAccel.setZ(FdmWrapper.getFdmBodyAccelZ());

  /* transform to imu frame */
        Vect3<Double> accelero_imu = new Vect3<>();
        PprzAlgebra.MAT33_VECT3_MULT(accelero_imu, bodyToImu, bodyAccel);


  /* compute accelero readings */
        PprzAlgebra.MAT33_VECT3_MULT(data.getValue(), data.getSensitivity(), accelero_imu);
        PprzAlgebra.VECT3_ADD(data.getValue(), data.getNeutral());

  /* Compute sensor error */
        Vect3<Double> accelero_error = new Vect3<>();
  /* constant bias */
        PprzAlgebra.VECT3_COPY(accelero_error, data.getBias());
  /* white noise   */
        NpsRandom.double_vect3_add_gaussian_noise(accelero_error, data.getNoise_std_dev());
  /* scale */
        Vect3<Double> gain = new Vect3<>();
        gain.setX(data.getSensitivity().getElement(0,0));
        gain.setY(data.getSensitivity().getElement(1,1));
        gain.setZ(data.getSensitivity().getElement(2,2));

        PprzAlgebra.VECT3_EW_MUL(accelero_error, accelero_error, gain);
  /* add error */
        PprzAlgebra.VECT3_ADD(data.getValue(), accelero_error);

  /* round signal to account for adc discretisation */
        PprzAlgebraDouble.DOUBLE_VECT3_ROUND(data.getValue());
  /* saturate                                       */
        PprzAlgebra.VECT3_BOUND_CUBE(data.getValue(), data.getMin(), data.getMax());

        data.setNext_update(data.getNext_update()+ NPS_ACCEL_DT);
        data.setData_available(true);
    }

    @Override
    public void init() {
    	JiveStateLog.setjniSensors("AccelSensor_init");
//        NativeTasksWrapper.npsSensorInitAccel(0);
        data = new AccelerometerReading();
        Vect3<Double> value = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(value, 0.d, 0.d, 0.d);
        data.setValue(value);

        data.setMin(NPS_ACCEL_MIN);
        data.setMax(NPS_ACCEL_MAX);

        Mat33<Double> sensitivity = Mat33.Mat33Double();
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

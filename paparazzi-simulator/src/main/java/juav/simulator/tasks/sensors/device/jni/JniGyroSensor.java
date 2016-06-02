package juav.simulator.tasks.sensors.device.jni;

import juav.simulator.nps.random.NpsRandom;
import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.GyroReading;
import ub.cse.juav.jni.fdm.JniFdm;
import ub.cse.juav.jni.nps.PaparazziNps;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraDouble;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.Mat33;
import ub.juav.airborne.math.structs.algebra.RMat;
import ub.juav.airborne.math.structs.algebra.Vect3;
import ub.juav.airborne.math.util.UtilityFunctions;

/**
 * Created by adamczer on 5/23/16.
 */
public class JniGyroSensor extends ISensor<GyroReading> {
    private static final double NPS_GYRO_MIN = -32767;
    private static final double NPS_GYRO_MAX =  32767;
    private static final double IMU_GYRO_P_SENS = 4.359; //imu_nps.h
    private static final double IMU_GYRO_Q_SENS = 4.359; //imu_nps.h
    private static final double IMU_GYRO_R_SENS = 4.359; //imu_nps.h
    private static final double NPS_GYRO_SENSITIVITY_PP = PprzAlgebraInt.RATE_BFP_OF_REAL(1. / IMU_GYRO_P_SENS);
    private static final double NPS_GYRO_SENSITIVITY_QQ = PprzAlgebraInt.RATE_BFP_OF_REAL(1. / IMU_GYRO_Q_SENS);
    private static final double NPS_GYRO_SENSITIVITY_RR = PprzAlgebraInt.RATE_BFP_OF_REAL(1. / IMU_GYRO_R_SENS);

    private static final int IMU_GYRO_Q_NEUTRAL =0;//imu_analog.h
    private static final int IMU_GYRO_R_NEUTRAL =0;//imu_analog.h
    private static final int IMU_GYRO_P_NEUTRAL = 0;//imu.h
    private static final double NPS_GYRO_NEUTRAL_P = IMU_GYRO_P_NEUTRAL;
    private static final double NPS_GYRO_NEUTRAL_Q = IMU_GYRO_Q_NEUTRAL;
    private static final double NPS_GYRO_NEUTRAL_R = IMU_GYRO_R_NEUTRAL;

    private static final double NPS_GYRO_NOISE_STD_DEV_P = UtilityFunctions.RadOfDeg(0.);
    private static final double NPS_GYRO_NOISE_STD_DEV_Q  = UtilityFunctions.RadOfDeg(0.);
    private static final double NPS_GYRO_NOISE_STD_DEV_R  = UtilityFunctions.RadOfDeg(0.);

    private static final double NPS_GYRO_BIAS_INITIAL_P =  UtilityFunctions.RadOfDeg( 0.0);
    private static final double NPS_GYRO_BIAS_INITIAL_Q =  UtilityFunctions.RadOfDeg( 0.0);
    private static final double NPS_GYRO_BIAS_INITIAL_R =  UtilityFunctions.RadOfDeg( 0.0);

    private static final double NPS_GYRO_BIAS_RANDOM_WALK_STD_DEV_P =UtilityFunctions.RadOfDeg(0.5);
    private static final double NPS_GYRO_BIAS_RANDOM_WALK_STD_DEV_Q =UtilityFunctions.RadOfDeg(0.5);
    private static final double NPS_GYRO_BIAS_RANDOM_WALK_STD_DEV_R =UtilityFunctions.RadOfDeg(0.5);
/* s */
    private static final double NPS_GYRO_DT =(1./512.);

    @Override
    protected void executePeriodic() {
        double time = PaparazziNps.getNpsMainSimTime();

        if(time<data.getNext_update())
            return;

        RMat<Double> bodyToImu = RMat.RMatDouble();
        for(int i = 0; i<3; i++)
            for(int j = 0; j<3 ; j++)
                bodyToImu.setElement(JniFdm.getFdmBodyToImu(i,j),i,j);

        /* transform body rates to IMU frame */
        Vect3<Double> rateBody = new Vect3<>();
        rateBody.setX(JniFdm.getFdmBodyInertialRotVelP());
        rateBody.setY(JniFdm.getFdmBodyInertialRotVelQ());
        rateBody.setZ(JniFdm.getFdmBodyInertialRotVelR());

        Vect3<Double> rateImu = new Vect3<>();
        PprzAlgebra.MAT33_VECT3_MULT(rateImu,bodyToImu,rateBody);
        /* compute gyros readings */
        PprzAlgebra.MAT33_VECT3_MULT(data.getValue(),data.getSensitivity(),rateImu);
        PprzAlgebra.VECT3_ADD(data.getValue(),data.getNeutral());
        /* compute gyro error readings */
        Vect3<Double> gyro_error = new Vect3<>();
        PprzAlgebra.VECT3_COPY(gyro_error, data.getBias_initial());
        NpsRandom.double_vect3_add_gaussian_noise(gyro_error, data.getNoise_std_dev());
        NpsRandom.double_vect3_update_random_walk(data.getBias_random_walk_value(), data.getBias_random_walk_std_dev(),
                NPS_GYRO_DT, 5.);
        PprzAlgebra.VECT3_ADD(gyro_error, data.getBias_random_walk_value());

        Vect3<Double> gain = new Vect3<>();
        gain.setX(data.getSensitivity().getElement(0,0));
        gain.setY(data.getSensitivity().getElement(1,1));
        gain.setZ(data.getSensitivity().getElement(2,2));
        PprzAlgebra.VECT3_EW_MUL(gyro_error, gyro_error, gain);

        PprzAlgebra.VECT3_ADD(data.getValue(), gyro_error);

  /* round signal to account for adc discretisation */
        PprzAlgebraDouble.DOUBLE_VECT3_ROUND(data.getValue());
  /* saturate                                       */
        PprzAlgebra.VECT3_BOUND_CUBE(data.getValue(), data.getMin(), data.getMax());

        data.setNext_update( data.getNext_update()+ NPS_GYRO_DT);
        data.setData_available(true);
    }

    @Override
    public void init() {
        data = new GyroReading();
        data.setMin(NPS_GYRO_MIN);
        data.setMax(NPS_GYRO_MAX);
        Mat33<Double> sensitivity = Mat33.Mat33Double();

        PprzAlgebra.MAT33_DIAG(sensitivity, NPS_GYRO_SENSITIVITY_PP, NPS_GYRO_SENSITIVITY_QQ, NPS_GYRO_SENSITIVITY_RR);
        data.setSensitivity(sensitivity);

        Vect3<Double> neutral = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(neutral, NPS_GYRO_NEUTRAL_P, NPS_GYRO_NEUTRAL_Q, NPS_GYRO_NEUTRAL_R);
        data.setNeutral(neutral);

        Vect3<Double> noise_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(noise_std_dev, NPS_GYRO_NOISE_STD_DEV_P, NPS_GYRO_NOISE_STD_DEV_Q, NPS_GYRO_NOISE_STD_DEV_R);
        data.setNoise_std_dev(noise_std_dev);

        Vect3<Double> bias_initial = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(bias_initial,NPS_GYRO_BIAS_INITIAL_P, NPS_GYRO_BIAS_INITIAL_Q, NPS_GYRO_BIAS_INITIAL_R);
        data.setBias_initial(bias_initial);

        Vect3<Double> bias_random_walk_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(bias_random_walk_std_dev,NPS_GYRO_BIAS_RANDOM_WALK_STD_DEV_P,NPS_GYRO_BIAS_RANDOM_WALK_STD_DEV_Q,NPS_GYRO_BIAS_RANDOM_WALK_STD_DEV_R);
        data.setBias_random_walk_std_dev(bias_random_walk_std_dev);

        Vect3<Double> bias_random_walk_value = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(bias_random_walk_value,0.d,0.d,0.d);
        data.setBias_random_walk_value(bias_random_walk_value);
        data.setNext_update(0);//initial start should be at 0
        data.setData_available(false);

        Vect3<Double> value = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(value,0.d,0.d,0.d);
        data.setValue(value);
    }
}

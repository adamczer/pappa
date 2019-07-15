package ub.cse.juav.ardupilot.sensors;

import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.device.jni.JniMagSensor;
import juav.simulator.tasks.sensors.readings.MagneticReading;
import ub.cse.juav.ardupilot.ArdupilotBridge;
import ub.cse.juav.ardupilot.time.ParameterizeTimer;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraDouble;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Mat33;
import ub.juav.airborne.math.structs.algebra.RMat;
import ub.juav.airborne.math.structs.algebra.Vect3;

public class ArdupilotJniMagSensor extends JniMagSensor {
    private static final double IMU_MAG_X_SIGN = 1;
    private static final double IMU_MAG_Y_SIGN = -1;
    private static final double IMU_MAG_Z_SIGN = 1;

    private static final double IMU_MAG_X_SENS =4.359;//airframe.h
    private static final double IMU_MAG_Y_SENS =4.359;//airframe.h
    private static final double IMU_MAG_Z_SENS =4.359;//airframe.h

    private static final int IMU_MAG_X_NEUTRAL =0;//airframe.h
    private static final int IMU_MAG_Y_NEUTRAL =0;//airframe.h
    private static final int IMU_MAG_Z_NEUTRAL =-180;//airframe.h

    /*
 *  Magnetometer
 */
 /* HMC5843 has 12 bit resolution */
    private static final int NPS_MAG_MIN = -2047;
    private static final int NPS_MAG_MAX = 2047;

    private static final double NPS_MAG_IMU_TO_SENSOR_PHI = 0.;
    private static final double NPS_MAG_IMU_TO_SENSOR_THETA = 0.;
    private static final double NPS_MAG_IMU_TO_SENSOR_PSI = 0.;

    private static final double NPS_MAG_SENSITIVITY_XX = (IMU_MAG_X_SIGN* PprzAlgebraInt.MAG_BFP_OF_REAL(1./IMU_MAG_X_SENS));
    private static final double NPS_MAG_SENSITIVITY_YY = (IMU_MAG_Y_SIGN*PprzAlgebraInt.MAG_BFP_OF_REAL(1./IMU_MAG_Y_SENS));
    private static final double NPS_MAG_SENSITIVITY_ZZ = (IMU_MAG_Z_SIGN*PprzAlgebraInt.MAG_BFP_OF_REAL(1./IMU_MAG_Z_SENS));

    private static final double  NPS_MAG_NEUTRAL_X = IMU_MAG_X_NEUTRAL;
    private static final double  NPS_MAG_NEUTRAL_Y = IMU_MAG_Y_NEUTRAL;
    private static final double  NPS_MAG_NEUTRAL_Z = IMU_MAG_Z_NEUTRAL;

    private static final double NPS_MAG_NOISE_STD_DEV_X = 2e-3;
    private static final double NPS_MAG_NOISE_STD_DEV_Y = 2e-3;
    private static final double NPS_MAG_NOISE_STD_DEV_Z = 2e-3;

    private static final double NPS_MAG_DT = (1. / 100.);

    @Override
    protected void executePeriodic() {

//        double time = PaparazziNpsWrapper.getNpsMainSimTime();
//
//        if(time<data.getNext_update()) {
//            return;
//        }

        if(!ParameterizeTimer.shouldReadMagSensor())
            return;

        ArdupilotBridge.updateCompass();
        Vect3<Double> magReading = new Vect3<>();
        magReading.setX(ArdupilotBridge.getMagX());
        magReading.setY(ArdupilotBridge.getMagY());
        magReading.setZ(ArdupilotBridge.getMagZ());
        data.setValue(magReading);
  /* round signal to account for adc discretisation */
        PprzAlgebraDouble.DOUBLE_VECT3_ROUND(data.getValue());
  /* saturate                                       */
        PprzAlgebra.VECT3_BOUND_CUBE(data.getValue(), (double)data.getMin(), (double)data.getMax());
//        System.out.println("Mag Time = "+System.currentTimeMillis());
//        System.out.println("Mag: X,Y,Z - "+data.getValue().getX()+","+data.getValue().getY()+","+data.getValue().getZ());
        data.setNext_update( data.getNext_update()+ NPS_MAG_DT);
    }

    @Override
    public void init() {
        ArdupilotBridge.initCompass();
        data = new MagneticReading();

        Vect3<Double> value = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(value, 0.d, 0.d, 0.d);
        data.setValue(value);

        data.setMin(NPS_MAG_MIN);
        data.setMax(NPS_MAG_MAX);

        Mat33<Double> sensitivity = Mat33.Mat33Double();
        PprzAlgebra.MAT33_DIAG( sensitivity, NPS_MAG_SENSITIVITY_XX, NPS_MAG_SENSITIVITY_YY, NPS_MAG_SENSITIVITY_ZZ);
        data.setSensitivity(sensitivity);

        Vect3<Double> neutral = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(neutral,NPS_MAG_NEUTRAL_X, NPS_MAG_NEUTRAL_Y, NPS_MAG_NEUTRAL_Z);
        data.setNeutral(neutral);

        Vect3<Double> noise_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(noise_std_dev,NPS_MAG_NOISE_STD_DEV_X, NPS_MAG_NOISE_STD_DEV_Y, NPS_MAG_NOISE_STD_DEV_Z);
        data.setNoise_std_dev(noise_std_dev);

        Eulers<Double> imu_to_sensor_eulers = new Eulers<>();
        imu_to_sensor_eulers.setPhi(NPS_MAG_IMU_TO_SENSOR_PHI);
        imu_to_sensor_eulers.setTheta(NPS_MAG_IMU_TO_SENSOR_THETA);
        imu_to_sensor_eulers.setPsi(NPS_MAG_IMU_TO_SENSOR_PSI);
        RMat<Double> imu_to_sensor_rmat = RMat.RMatDouble();
        PprzAlgebraDouble.double_rmat_of_eulers(imu_to_sensor_rmat, imu_to_sensor_eulers);
        data.setImu_to_sensor_rmat(imu_to_sensor_rmat);

        data.setNext_update(0);
        data.setData_available(false);
    }
}

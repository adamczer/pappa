package juav.simulator.tasks.sensors.device.jni;
import jive.logging.StateTransitions;
import juav.logging.JiveStateLog;
import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.MagneticReading;
import ub.cse.juav.jni.fdm.FdmWrapper;
import ub.cse.juav.jni.fdm.JniFdm;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraDouble;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.*;

/**
 * Created by adamczer on 5/24/16.
 */
public class JniMagSensor extends ISensor<MagneticReading> {

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

    private static final double NPS_MAG_SENSITIVITY_XX = (IMU_MAG_X_SIGN*PprzAlgebraInt.MAG_BFP_OF_REAL(1./IMU_MAG_X_SENS));
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
        double time = PaparazziNpsWrapper.getNpsMainSimTime();
//        NativeTasksWrapper.npsSensorFdmCopyMag(time); if (true) return;

        if(time<data.getNext_update()) {
            return;
        }
        StateTransitions.instance.add_transition(new String[]{"Copy Magnometer"});
        JiveStateLog.setjniSensors("MagSensor_execute_Periodic");

//        //TODO refactor this to be object updated prior to mag,gyro,accel and set on these sesors.
        RMat<Double> bodyToImu = RMat.RMatDouble();
        for(int i = 0; i<3; i++)
            for(int j = 0; j<3 ; j++)
                bodyToImu.setElement(FdmWrapper.getFdmBodyToImu(i,j),i,j);

  /* transform magnetic field to body frame */

        Quat<Double> fdmLtpToBodyQuat = new Quat<>();
        fdmLtpToBodyQuat.setQi(FdmWrapper.getFdmLtpToBodyQuatQi());
        fdmLtpToBodyQuat.setQx(FdmWrapper.getFdmLtpToBodyQuatQx());
        fdmLtpToBodyQuat.setQy(FdmWrapper.getFdmLtpToBodyQuatQy());
        fdmLtpToBodyQuat.setQz(FdmWrapper.getFdmLtpToBodyQuatQz());
        Vect3<Double> fdmLptH = new Vect3<>();
        fdmLptH.setX(FdmWrapper.getFdmLtpHX());
        fdmLptH.setY(FdmWrapper.getFdmLtpHY());
        fdmLptH.setZ(FdmWrapper.getFdmLtpHZ());

        Vect3<Double> hBody = new Vect3<>();
        PprzAlgebraDouble.double_quat_vmult(hBody, fdmLtpToBodyQuat, fdmLptH);

  /* transform to imu frame */
        Vect3<Double> h_imu = new Vect3<>();
        PprzAlgebra.MAT33_VECT3_MULT(h_imu, bodyToImu, hBody);

  /* transform to sensor frame */
        Vect3<Double> h_sensor = new Vect3<>();
        PprzAlgebra.MAT33_VECT3_MULT(h_sensor, data.getImu_to_sensor_rmat(), h_imu);

  /* compute magnetometer reading */
        PprzAlgebra.MAT33_VECT3_MULT(data.getValue(), data.getSensitivity(), h_sensor);
        PprzAlgebra.VECT3_ADD(data.getValue(), data.getNeutral());
  /* FIXME: ADD error reading */

  /* round signal to account for adc discretisation */
        PprzAlgebraDouble.DOUBLE_VECT3_ROUND(data.getValue());
  /* saturate                                       */
        PprzAlgebra.VECT3_BOUND_CUBE(data.getValue(), (double)data.getMin(), (double)data.getMax());

        data.setNext_update(data.getNext_update()+ NPS_MAG_DT);
        data.setData_available(true);
    }

    @Override
    public void init() {
    	JiveStateLog.setjniSensors("MagSensor_init");
//        NativeTasksWrapper.npsSensorInitMag(0);
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

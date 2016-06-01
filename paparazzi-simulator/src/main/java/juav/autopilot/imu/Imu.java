package juav.autopilot.imu;

import ub.juav.airborne.math.structs.algebra.Rates;
import ub.juav.airborne.math.structs.algebra.Vect3;
import ub.juav.airborne.math.structs.orientation.OrientationReps;

/**
 * Created by adamczer on 5/30/16.
 */
public class Imu {
        protected Rates<Integer> gyro;             ///< gyroscope measurements in rad/s in BFP with #INT32_RATE_FRAC
    protected Vect3<Integer> accel;            ///< accelerometer measurements in m/s^2 in BFP with #INT32_ACCEL_FRAC
    protected Vect3<Integer> mag;              ///< magnetometer measurements scaled to 1 in BFP with #INT32_MAG_FRAC
    protected Rates<Integer> gyro_prev;        ///< previous gyroscope measurements
    protected  Vect3<Integer> accel_prev;       ///< previous accelerometer measurements
    protected Rates<Integer> gyro_neutral;     ///< gyroscope bias
    protected Vect3<Integer> accel_neutral;    ///< accelerometer bias
    protected Vect3<Integer> mag_neutral;      ///< magnetometer neutral readings (bias)
    protected Rates<Integer> gyro_unscaled;    ///< unscaled gyroscope measurements
    protected Vect3<Integer> accel_unscaled;   ///< unscaled accelerometer measurements
    protected Vect3<Integer> mag_unscaled;     ///< unscaled magnetometer measurements
    protected OrientationReps body_to_imu; ///< rotation from body to imu frame
        /** flag for adjusting body_to_imu via settings.
         * if FALSE, reset to airframe values, if TRUE set current roll/pitch
         */
        protected boolean b2i_set_current;

    public Imu() {
        gyro = new Rates<>();
        accel = new Vect3<>();
        mag = new Vect3<>();
        gyro_prev = new Rates<>();
        accel_prev = new Vect3<>();
        gyro_neutral = new Rates<>();
        accel_neutral = new Vect3<>();
        mag_neutral = new Vect3<>();
        gyro_unscaled = new Rates<>();
        accel_unscaled = new Vect3<>();
        mag_unscaled = new Vect3<>();
        body_to_imu = new OrientationReps();
        b2i_set_current = false;
    }


}

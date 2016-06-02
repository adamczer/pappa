package juav.autopilot.imu;

import ub.juav.airborne.math.structs.algebra.Rates;
import ub.juav.airborne.math.structs.algebra.Vect3;
import ub.juav.airborne.math.structs.orientation.OrientationReps;

/**
 * Created by adamczer on 5/30/16.
 */
public class ImuReading {
        protected Rates<Integer> gyro;             ///< gyroscope measurements in rad/s in BFP with #INT32_RATE_FRAC
    protected Vect3<Integer> accel;            ///< accelerometer measurements in m/s^2 in BFP with #INT32_ACCEL_FRAC
    protected Vect3<Integer> mag;              ///< magnetometer measurements scaled to 1 in BFP with #INT32_MAG_FRAC
    protected Rates<Integer> gyro_prev;        ///< previous gyroscope measurements
    protected Vect3<Integer> accel_prev;       ///< previous accelerometer measurements
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

    public ImuReading() {
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

    public Rates<Integer> getGyro() {
        return gyro;
    }

    public void setGyro(Rates<Integer> gyro) {
        this.gyro = gyro;
    }

    public Vect3<Integer> getAccel() {
        return accel;
    }

    public void setAccel(Vect3<Integer> accel) {
        this.accel = accel;
    }

    public Vect3<Integer> getMag() {
        return mag;
    }

    public void setMag(Vect3<Integer> mag) {
        this.mag = mag;
    }

    public Rates<Integer> getGyro_prev() {
        return gyro_prev;
    }

    public void setGyro_prev(Rates<Integer> gyro_prev) {
        this.gyro_prev = gyro_prev;
    }

    public Vect3<Integer> getAccel_prev() {
        return accel_prev;
    }

    public void setAccel_prev(Vect3<Integer> accel_prev) {
        this.accel_prev = accel_prev;
    }

    public Rates<Integer> getGyro_neutral() {
        return gyro_neutral;
    }

    public void setGyro_neutral(Rates<Integer> gyro_neutral) {
        this.gyro_neutral = gyro_neutral;
    }

    public Vect3<Integer> getAccel_neutral() {
        return accel_neutral;
    }

    public void setAccel_neutral(Vect3<Integer> accel_neutral) {
        this.accel_neutral = accel_neutral;
    }

    public Vect3<Integer> getMag_neutral() {
        return mag_neutral;
    }

    public void setMag_neutral(Vect3<Integer> mag_neutral) {
        this.mag_neutral = mag_neutral;
    }

    public Rates<Integer> getGyro_unscaled() {
        return gyro_unscaled;
    }

    public void setGyro_unscaled(Rates<Integer> gyro_unscaled) {
        this.gyro_unscaled = gyro_unscaled;
    }

    public Vect3<Integer> getAccel_unscaled() {
        return accel_unscaled;
    }

    public void setAccel_unscaled(Vect3<Integer> accel_unscaled) {
        this.accel_unscaled = accel_unscaled;
    }

    public Vect3<Integer> getMag_unscaled() {
        return mag_unscaled;
    }

    public void setMag_unscaled(Vect3<Integer> mag_unscaled) {
        this.mag_unscaled = mag_unscaled;
    }

    public OrientationReps getBody_to_imu() {
        return body_to_imu;
    }

    public void setBody_to_imu(OrientationReps body_to_imu) {
        this.body_to_imu = body_to_imu;
    }

    public boolean isB2i_set_current() {
        return b2i_set_current;
    }

    public void setB2i_set_current(boolean b2i_set_current) {
        this.b2i_set_current = b2i_set_current;
    }
}

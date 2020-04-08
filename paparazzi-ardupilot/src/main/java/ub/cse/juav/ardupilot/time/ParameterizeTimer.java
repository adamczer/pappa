package ub.cse.juav.ardupilot.time;

import org.joda.time.DateTime;

import static juav.autopilot.stabilization.StabilizationAttitudeQuatInt.PERIODIC_FREQUENCY;

public class ParameterizeTimer {
    private static final int multiplier = 1000000000;
    public static final int MAIN_PERIODIC_FREQUENCY = (int) ((1/512) * multiplier);

    public static long last_main_periodic_time_ns = 0;

    public static boolean sysTimeCheckAndAckTimerMainPeriodic() {
        long nowNano = System.nanoTime();
        if(last_main_periodic_time_ns==0) {
            last_main_periodic_time_ns = nowNano;
            return true;
        } else if ((nowNano - last_main_periodic_time_ns) > MAIN_PERIODIC_FREQUENCY) {
            last_main_periodic_time_ns = nowNano;
            return true;
        }
        return false;
    }

    public static final int ACCEL_FREQUENCY = (int) ((1/512) *multiplier);
    public static long last_accel_time_ns = 0;
    public static boolean shouldReadAccelSensor() {
        long nowNano = System.nanoTime();
        if(last_accel_time_ns==0) {
            last_accel_time_ns = nowNano;
            return true;
        } else if ((nowNano - last_accel_time_ns) > ACCEL_FREQUENCY) {
            last_accel_time_ns = nowNano;
            return true;
        }
        return false;
    }

    public static final int BARO_FREQUENCY = (int) ((1/50) * multiplier);
    public static long last_baro_time_ns = 0;
    public static boolean shouldReadBaroSensor() {
        long nowNano = System.nanoTime();
        if(last_baro_time_ns==0) {
            last_baro_time_ns = nowNano;
            return true;
        } else if ((nowNano - last_baro_time_ns) > BARO_FREQUENCY) {
            last_baro_time_ns = nowNano;
            return true;
        }
        return false;
    }

    public static final int GPS_FREQUENCY = (int) ((1/4) * multiplier);
    public static long last_gps_time_ns = 0;
    public static boolean shouldReadGpsSensor() {
        long nowNano = System.nanoTime();
        if(last_gps_time_ns==0) {
            last_gps_time_ns = nowNano;
            return true;
        } else if ((nowNano - last_gps_time_ns) > GPS_FREQUENCY) {
            last_gps_time_ns = nowNano;
            return true;
        }
        return false;
    }


    public static final int GYRO_FREQUENCY = (int) ((1/512) * multiplier);
    public static long last_gyro_time_ns = 0;
    public static boolean shouldReadGyroSensor() {
        long nowNano = System.nanoTime();
        if(last_gyro_time_ns==0) {
            last_gyro_time_ns = nowNano;
            return true;
        } else if ((nowNano - last_gyro_time_ns) > GYRO_FREQUENCY) {
            last_gyro_time_ns = nowNano;
            return true;
        }
        return false;
    }

    public static final int MAG_FREQUENCY = (int) ((1/100) * multiplier);
    public static long last_mag_time_ns = 0;
    public static boolean shouldReadMagSensor() {
        long nowNano = System.nanoTime();
        if(last_mag_time_ns==0) {
            last_mag_time_ns = nowNano;
            return true;
        } else if ((nowNano - last_mag_time_ns) > MAG_FREQUENCY) {
            last_mag_time_ns = nowNano;
            return true;
        }
        return false;
    }


}

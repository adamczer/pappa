package ub.cse.juav.ardupilot;

public class ArdupilotBridge {
    private static final Object lock = new Object();
    private static boolean baseInited = false;
    private static boolean inertialInited = false;
    private static boolean gpsInited = false;
    private static native boolean init_base();
    private static native boolean init_inertial();
    /**
     * includes Gyro, Accelerometer, and Temperature
     * @return
     */
    public static boolean initInertial() {
        if(!inertialInited) {
            synchronized (lock) {
                if(!inertialInited) {
                    initBase();
                    init_inertial();
                    inertialInited = true;
                }
            }
        }
        return true;
    }

    public static void updateInertial() {
        update_inertial();
    }

    public static native void update_inertial();

    private static boolean initBase() {
        if(!baseInited) {
            synchronized (lock) {
                if (!baseInited) {
                    init_base();
                    baseInited = true;
                }
            }
        }
        return true;
    }
    public static native double getFdmBodyAccelX();
    public static native double getFdmBodyAccelY();
    public static native double getFdmBodyAccelZ();

    // gps
    public static boolean initGps() {
        if(!gpsInited) {
            synchronized (lock) {
                if(!gpsInited) {
                    initBase();
                    init_gps();
                    gpsInited = true;
                }
            }
        }
        return true;
    }

    public static native boolean init_gps();
    public static native void updateGps();
    public static native long getGpsReadTime();
    public static native double getGpsLatitude();
    public static native double getGpsLongitude();
    public static native double getGpsAltitude();

    //Gyro
    public static native double getFdmBodyInertialRotVelP();
    public static native double getFdmBodyInertialRotVelQ();
    public static native double getFdmBodyInertialRotVelR();

    //magnomiter ie compass
    public static native double getMagX();
    public static native double getMagY();
    public static native double getMagZ();

    public static native boolean init_compass();

    public static void initCompass() {
        initBase();
        init_compass();
    }

    public static native void updateCompass();
}

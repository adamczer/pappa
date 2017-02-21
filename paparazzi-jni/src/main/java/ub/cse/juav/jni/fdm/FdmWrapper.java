package ub.cse.juav.jni.fdm;

import ub.cse.juav.fijiorjni.NativeSwitch;

/**
 * Created by adamczer on 2/20/17.
 */
public class FdmWrapper {

    //TODO used to get components over jni for roational veloicty ie gyro
    public static double getFdmBodyInertialRotVelP() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmBodyInertialRotVelP();
        } else {
            return JniFdm.getFdmBodyInertialRotVelP();
        }
    }

    public static double getFdmBodyInertialRotVelQ() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmBodyInertialRotVelQ();
        } else {
            return JniFdm.getFdmBodyInertialRotVelQ();
        }
    }

    public static double getFdmBodyInertialRotVelR() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmBodyInertialRotVelR();
        } else {
            return JniFdm.getFdmBodyInertialRotVelR();
        }
    }

    //TODO used to get components over jni for liniar motion ie acceleration
    public static double getFdmBodyAccelX() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmBodyAccelX();
        } else {
            return JniFdm.getFdmBodyAccelX();
        }
    }

    public static double getFdmBodyAccelY() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmBodyAccelY();
        } else {
            return JniFdm.getFdmBodyAccelY();
        }
    }

    public static double getFdmBodyAccelZ() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmBodyAccelZ();
        } else {
            return JniFdm.getFdmBodyAccelZ();
        }
    }

    //TODO used to get components over jni for liniar motion ie acceleration
    public static double getFdmBodyToImu(int row, int col) {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmBodyToImu(row,col);
        } else {
            return JniFdm.getFdmBodyToImu(row, col);
        }
    }

    //TODO used to get components over jni for magnetics
    public static double getFdmLtpToBodyQuatQi() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmLtpToBodyQuatQi();
        } else {
            return JniFdm.getFdmLtpToBodyQuatQi();
        }
    }

    public static double getFdmLtpToBodyQuatQx() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmLtpToBodyQuatQx();
        } else {
            return JniFdm.getFdmLtpToBodyQuatQx();
        }
    }

    public static double getFdmLtpToBodyQuatQy() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmLtpToBodyQuatQy();
        } else {
            return JniFdm.getFdmLtpToBodyQuatQy();
        }
    }

    public static double getFdmLtpToBodyQuatQz() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmLtpToBodyQuatQz();
        } else {
            return JniFdm.getFdmLtpToBodyQuatQz();
        }
    }

    //TODO used to get components over jni for magnetics
    public static double getFdmLtpHX() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmLtpHX();
        } else {
            return JniFdm.getFdmLtpHX();
        }
    }

    public static double getFdmLtpHY() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmLtpHY();
        } else {
            return JniFdm.getFdmLtpHY();
        }
    }

    public static double getFdmLtpHZ() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmLtpHZ();
        } else {
            return JniFdm.getFdmLtpHZ();
        }
    }

    //TODO used to get components over jni for barometer
    public static double getHmsl() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getHmsl();
        } else {
            return JniFdm.getHmsl();
        }
    }

    public static double getFdmEcefEcefVelX() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmEcefEcefVelX();
        } else {
            return JniFdm.getFdmEcefEcefVelX();
        }
    }

    public static double getFdmEcefEcefVelY() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmEcefEcefVelY();
        } else {
            return JniFdm.getFdmEcefEcefVelY();
        }
    }

    public static double getFdmEcefEcefVelZ() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmEcefEcefVelZ();
        } else {
            return JniFdm.getFdmEcefEcefVelZ();
        }
    }


    public static double getFdmEcefPosX() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmEcefPosX();
        } else {
            return JniFdm.getFdmEcefPosX();
        }
    }

    public static double getFdmEcefPosY() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmEcefPosY();
        } else {
            return JniFdm.getFdmEcefPosY();
        }
    }

    public static double getFdmEcefPosZ() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmEcefPosZ();
        } else {
            return JniFdm.getFdmEcefPosZ();
        }
    }

    public static double getFdmAgl() {
        if (NativeSwitch.isFiji()) {
            return FijiFdm.getFdmAgl();
        } else {
            return JniFdm.getFdmAgl();
        }
    }
}

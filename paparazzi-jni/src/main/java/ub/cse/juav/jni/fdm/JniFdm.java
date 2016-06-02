package ub.cse.juav.jni.fdm;

/**
 * Created by adamczer on 3/1/16.
 */
public class JniFdm {

    //TODO used to get components over jni for roational veloicty ie gyro
    public static native double getFdmBodyInertialRotVelP();
    public static native double getFdmBodyInertialRotVelQ();
    public static native double getFdmBodyInertialRotVelR();

    //TODO used to get components over jni for liniar motion ie acceleration
    public static native double getFdmBodyAccelX();
    public static native double getFdmBodyAccelY();
    public static native double getFdmBodyAccelZ();

    //TODO used to get components over jni for liniar motion ie acceleration
    public static native double getFdmBodyToImu(int row, int col);

    //TODO used to get components over jni for magnetics
    public static native double getFdmLtpToBodyQuatQi();
    public static native double getFdmLtpToBodyQuatQx();
    public static native double getFdmLtpToBodyQuatQy();
    public static native double getFdmLtpToBodyQuatQz();

    //TODO used to get components over jni for magnetics
    public static native double getFdmLtpHX();
    public static native double getFdmLtpHY();
    public static native double getFdmLtpHZ();

    //TODO used to get components over jni for barometer
    public static native double getHmsl();

    public static native double getFdmEcefEcefVelX();
    public static native double getFdmEcefEcefVelY();
    public static native double getFdmEcefEcefVelZ();


    public static native double getFdmEcefPosX();
    public static native double getFdmEcefPosY();
    public static native double getFdmEcefPosZ();

    public static native double getFdmAgl();
}

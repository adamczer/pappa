package ub.cse.juav.jni.fdm;

/**
 * Created by adamczer on 3/1/16.
 */
public class JniFdm {
    public static native void FGFDMExecInit();

    //TODO used to get components over jni for roational veloicty ie gyro
    public static native Double getFdmBodyInertialRotVelP();
    public static native Double getFdmBodyInertialRotVelQ();
    public static native Double getFdmBodyInertialRotVelR();

    //TODO used to get components over jni for liniar motion ie acceleration
    public static native Double getFdmBodyAccelX();
    public static native Double getFdmBodyAccelY();
    public static native Double getFdmBodyAccelZ();

    public static native Double getFdmBodyToImu(int row, int col);

}

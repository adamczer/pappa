package ub.cse.juav.jni.fdm;

/**
 * Created by adamczer on 3/1/16.
 */
public class JniFdm {
    public static native void FGFDMExecInit();

    public static native Double getFdmBodyInertialRotVelP();
    public static native Double getFdmBodyInertialRotVelQ();
    public static native Double getFdmBodyInertialRotVelR();

    public static native Double getFdmBodyToImu(int row, int col);

}

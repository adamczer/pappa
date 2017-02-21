package ub.cse.juav.fijiorjni;

/**
 * Created by adamczer on 2/19/17.
 */
public class NativeSwitch {
    private static boolean isFiji;

    public static void setIsFiji(boolean isFijiVal) {
        isFiji = isFijiVal;
    }

    public static boolean isFiji() {
        return isFiji;
    }
}

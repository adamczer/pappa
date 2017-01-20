package juav.autopilot.stabilization;

import ub.cse.juav.jni.tasks.NativeTasks;

/**
 * Created by adamczer on 10/28/16.
 */
public class Stabilization {

//    public static int[] stabilization_cmd = new int[COMMANDS_NB];
//
    public static void stabilization_init() {
        init();
    }
    public static void init()
    {
//        for (int i = 0; i < COMMANDS_NB; i++) {
//            stabilization_cmd[i] = 0;
//        }
    }

    public static void setStabilizationCommand(int index, int newValue) {
        NativeTasks.setStabilizationCmd(index,newValue);
    }

    public static int getStabilizationCommand(int index) {
        return NativeTasks.getStabilizationCmd(index);
    }

}

package juav.autopilot.stabilization;

import static juav.autopilot.commands.Commands.COMMANDS_NB;

/**
 * Created by adamczer on 10/28/16.
 */
public class Stabilization {

    public static int[] stabilization_cmd = new int[COMMANDS_NB];
//
    public static void stabilization_init() {
        init();
    }
    public static void init()
    {
        for (int i = 0; i < COMMANDS_NB; i++) {
            stabilization_cmd[i] = 0;
        }
    }

}

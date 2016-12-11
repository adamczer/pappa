package juav.autopilot.commands;

/**
 * Created by adamczer on 10/28/16.
 */
public class Commands {
    public static final int COMMAND_ROLL =0;
    public static final int COMMAND_PITCH =1;
    public static final int COMMAND_YAW =2;
    public static final int COMMAND_THRUST =3;
    public static final int COMMANDS_NB =4;

//    pprz_t is just an int
    public static int[] commands = new int[COMMANDS_NB];
    public static int[] commands_failsafe = new int[]{0,0,0,0};

    public static void SetCommands(int[] t) {
        int i;
        for(i = 0; i < COMMANDS_NB; i++) commands[i] = t[i];
    }
}

package juav.autopilot.commands;

import ub.cse.juav.jni.tasks.NativeTasks;

/**
 * Created by adamczer on 10/28/16.
 */
public class Commands {
    public static final int COMMAND_ROLL =0;
    public static final int COMMAND_PITCH =1;
    public static final int COMMAND_YAW =2;
    public static final int COMMAND_THRUST =3;
    public static final int COMMANDS_NB =4;

    public static int[] commands_failsafe = new int[]{0,0,0,0};

    public static void SetCommands(int[] t) {
        int i;
        for(i = 0; i < COMMANDS_NB; i++)
            setCommand(i,t[i]);
    }
    public static void setCommand(int commandIndex, int command) {
        NativeTasks.setStabilizationCommand(commandIndex,command);
    }

    public static void SetRotorcraftCommands(int[] _cmd, boolean _in_flight,  boolean _motor_on) {
        if (!(_in_flight)) { _cmd[COMMAND_YAW] = 0; }
        if (!(_motor_on)) { _cmd[COMMAND_THRUST] = 0; }
        setCommand(COMMAND_ROLL,_cmd[COMMAND_ROLL]);
        setCommand(COMMAND_PITCH, _cmd[COMMAND_PITCH]);
        setCommand(COMMAND_YAW, _cmd[COMMAND_YAW]);
        setCommand(COMMAND_THRUST, _cmd[COMMAND_THRUST]);
    }}

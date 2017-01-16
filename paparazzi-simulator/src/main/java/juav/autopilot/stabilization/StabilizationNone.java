package juav.autopilot.stabilization;

import juav.autopilot.radiocontrol.RadioControl;
import ub.juav.airborne.math.structs.algebra.Rates;

import static juav.autopilot.commands.Commands.*;
import static juav.autopilot.radiocontrol.RadioControl.*;
import static juav.autopilot.stabilization.Stabilization.stabilization_cmd;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.INT_RATES_ZERO;

/**
 * Created by adamczer on 11/5/16.
 */
public class StabilizationNone {
    public static Rates<Integer> stabilization_none_rc_cmd = Rates.newInteger();

    public static void stabilization_none_init()
    {
        stabilization_none_rc_cmd = Rates.newInteger();
    }

    public static void stabilization_none_read_rc()
    {
        //TODO
        stabilization_none_rc_cmd.p = radio_control.getValue(RADIO_ROLL);
        stabilization_none_rc_cmd.q = radio_control.getValue(RADIO_PITCH);
        stabilization_none_rc_cmd.r = radio_control.getValue(RADIO_YAW);
    }

    public static void stabilization_none_enter()
    {
        INT_RATES_ZERO(stabilization_none_rc_cmd);
    }

    void stabilization_none_run(boolean in_flight/*UNUSED*/)
    {
  /* just directly pass rc commands through */
        stabilization_cmd[COMMAND_ROLL]  = stabilization_none_rc_cmd.p;
        stabilization_cmd[COMMAND_PITCH] = stabilization_none_rc_cmd.q;
        stabilization_cmd[COMMAND_YAW]   = stabilization_none_rc_cmd.r;
    }
}

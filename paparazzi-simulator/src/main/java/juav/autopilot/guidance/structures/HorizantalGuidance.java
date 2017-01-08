package juav.autopilot.guidance.structures;

import ub.juav.airborne.math.structs.algebra.Eulers;

/**
 * Created by adamczer on 10/10/16.
 */
public class HorizantalGuidance {
    public short mode;
    /* configuration options */
    public boolean use_ref;
    public boolean approx_force_by_thrust;
    /* gains */
    public HorizontalGuidanceGains gains;

    public HorizontalGuidanceSetpoint sp; ///< setpoints
    public HorizontalGuidanceReference ref; ///< reference calculated from setpoints

    public Eulers<Integer> rc_sp;    ///< with #INT32_ANGLE_FRAC

    public HorizantalGuidance() {
        sp = new HorizontalGuidanceSetpoint();
        ref =new HorizontalGuidanceReference();
        rc_sp = Eulers.newInteger();
        gains = new HorizontalGuidanceGains();
    }
}

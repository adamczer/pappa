package juav.autopilot.guidance.structures;

import ub.juav.airborne.math.structs.algebra.Vect2;

/**
 * Created by adamczer on 10/10/16.
 */
public class HorizontalGuidanceReference {
    public Vect2<Integer> pos;     ///< with #INT32_POS_FRAC
    public Vect2<Integer> speed;   ///< with #INT32_SPEED_FRAC
    public Vect2<Integer> accel;   ///< with #INT32_ACCEL_FRAC


}

package juav.autopilot.stabilization.attitude;

import ub.juav.airborne.math.structs.algebra.Rates;

/**
 * Created by adamczer on 12/11/16.
 */
public class RefSat<T extends Number> {
    //TODO
    public Rates<T> max_rate;
    public Rates<T> max_accel;
    public static RefSat<Integer> newRefSatInteger() {
        RefSat<Integer> ret = new RefSat<>();
        ret.max_accel = new Rates<>();
        ret.max_rate = new Rates<>();
        return ret;
    }
}

package juav.autopilot.stabilization.attitude;

import ub.juav.airborne.math.structs.algebra.Rates;

/**
 * Created by adamczer on 12/11/16.
 */
public class RefModel {
    public Rates<Float> omega;
    public Rates<Float> zeta;
    /* cached intermediate values in int */
    public Rates<Integer> two_zeta_omega;
    public Rates<Integer> two_omega2;
    public static RefModel newRefModel() {
        RefModel ret = new RefModel();
        ret.omega = Rates.newFloat();
        ret.zeta = Rates.newFloat();
        ret.two_omega2 = Rates.newInteger();
        ret.two_zeta_omega = Rates.newInteger();
        return ret;
    }
}

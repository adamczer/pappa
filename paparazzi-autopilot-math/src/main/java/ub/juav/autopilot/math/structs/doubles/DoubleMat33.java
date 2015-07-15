package ub.juav.autopilot.math.structs.doubles;

import ub.juav.autopilot.math.structs.MatNxN;

/**
 * Created by adamczer on 6/22/15.
 */
public class DoubleMat33 extends MatNxN<Double> {
    public DoubleMat33() {
        setMatrix(new Double[3][3]);
    }
}

package ub.juav.autopilot.math.structs.algebra.doubles;

import ub.juav.autopilot.math.structs.algebra.Mat33;

/**
 * Created by adamczer on 6/22/15.
 */
public class DoubleMat33 extends Mat33<Double> {
    public DoubleMat33() {
        setMatrix(new Double[3][3]);
    }
    @Override
    public void zero() {
        setMatrix(new Double[getMatrix().length][getMatrix()[0].length]);
    }
}

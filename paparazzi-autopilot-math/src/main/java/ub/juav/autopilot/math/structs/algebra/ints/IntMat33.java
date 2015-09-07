package ub.juav.autopilot.math.structs.algebra.ints;

import ub.juav.autopilot.math.structs.algebra.Mat33;

/**
 * Created by adamczer on 7/12/15.
 */
public class IntMat33 extends Mat33<Integer> {
    public IntMat33() {
        setMatrix(new Integer[3][3]);
    }
    @Override
    public void zero() {
        setMatrix(new Integer[getMatrix().length][getMatrix()[0].length]);
    }
}

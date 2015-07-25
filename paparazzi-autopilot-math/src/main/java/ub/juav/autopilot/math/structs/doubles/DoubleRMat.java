package ub.juav.autopilot.math.structs.doubles;

import ub.juav.autopilot.math.structs.RMat;

/**
 * Created by adamczer on 6/22/15.
 */
public class DoubleRMat extends RMat<Double>{

    @Override
    public void zero() {
        setMatrix(new Double[getMatrix().length][getMatrix()[0].length]);
    }
}

package ub.juav.autopilot.math.structs.floats;

import ub.juav.autopilot.math.structs.RMat;

/**
 * Created by adamczer on 7/25/15.
 */
public class FloatRMat extends RMat<Float> {
    @Override
    public void zero() {
        setMatrix(new Float[getMatrix().length][getMatrix()[0].length]);
    }
}

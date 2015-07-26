package ub.juav.autopilot.math.structs.floats;

import ub.juav.autopilot.math.structs.Mat33;

/**
 * Created by adamczer on 7/26/15.
 */
public class FloatMat33 extends Mat33<Float> {
    @Override
    public void zero() {
        setMatrix(new Float[getMatrix().length][getMatrix()[0].length]);
    }
}

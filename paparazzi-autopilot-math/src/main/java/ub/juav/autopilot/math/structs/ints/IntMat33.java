package ub.juav.autopilot.math.structs.ints;

import ub.juav.autopilot.math.structs.MatNxN;

/**
 * Created by adamczer on 7/12/15.
 */
public class IntMat33 extends MatNxN<Integer> {
    public IntMat33() {
        setMatrix(new Integer[3][3]);
    }
}

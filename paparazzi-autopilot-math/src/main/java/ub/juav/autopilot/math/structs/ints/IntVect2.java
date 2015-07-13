package ub.juav.autopilot.math.structs.ints;

import ub.juav.autopilot.math.structs.Vect2;

/**
 * Created by adamczer on 7/12/15.
 */
public class IntVect2 implements Vect2<Integer>{
    private int x, y;

    public Integer getX() {
        return x;
    }
    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }
}

package ub.juav.autopilot.math.structs.ints;

import ub.juav.autopilot.math.structs.Vect3;

/**
 * Created by adamczer on 7/12/15.
 */
public class IntVect3 implements Vect3<Integer> {
    private Integer x,y,z;

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

    public Integer getZ() {
        return z;
    }

    public void setZ(Integer z) {
        this.z = z;
    }
}

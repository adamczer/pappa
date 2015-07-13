package ub.juav.autopilot.math.structs.doubles;

import ub.juav.autopilot.math.structs.Vect3;

/**
 * Created by adamczer on 6/22/15.
 */
public class DoubleVect3 implements Vect3<Double>{

    private Double x;
    private Double y;
    private Double z;

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }
}

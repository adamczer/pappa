package ub.juav.autopilot.math.structs.doubles;

import ub.juav.autopilot.math.structs.Vect2;

/**
 * Created by adamczer on 6/22/15.
 */
public class DoubleVect2 implements Vect2<Double>{
    private double x;
    private double y;

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
}

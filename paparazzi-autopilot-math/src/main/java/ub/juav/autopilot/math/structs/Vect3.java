package ub.juav.autopilot.math.structs;

/**
 * Created by adamczer on 7/12/15.
 */
public abstract class Vect3<T> {
    private T x,y,z;

    public Number getX() {
        return (Number) x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public Number getY() {
        return (Number) y;
    }

    public void setY(T y) {
        this.y = y;
    }

    public Number getZ() {
        return (Number) z;
    }

    public void setZ(T z) {
        this.z = z;
    }
}

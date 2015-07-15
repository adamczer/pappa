package ub.juav.autopilot.math.structs;

/**
 * Created by adamczer on 7/12/15.
 */
public abstract class Vect3<T> {
    private T x,y,z;

    public T getX() {
        return x;
    }

    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    public T getZ() {
        return z;
    }

    public void setZ(T z) {
        this.z = z;
    }
}

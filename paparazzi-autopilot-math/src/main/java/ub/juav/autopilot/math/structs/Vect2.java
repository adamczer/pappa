package ub.juav.autopilot.math.structs;

/**
 * Created by adamczer on 7/12/15.
 */
public abstract class Vect2<T> {
    private T x, y;

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
}

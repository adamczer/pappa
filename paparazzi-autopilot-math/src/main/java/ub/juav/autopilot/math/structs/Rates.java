package ub.juav.autopilot.math.structs;

/**
 * Created by adamczer on 7/15/15.
 */
public abstract class Rates<T> {
    private T p;
    private T q;
    private T r;

    public T getP() {
        return p;
    }

    public void setP(T p) {
        this.p = p;
    }

    public T getQ() {
        return q;
    }

    public void setQ(T q) {
        this.q = q;
    }

    public T getR() {
        return r;
    }

    public void setR(T r) {
        this.r = r;
    }
}

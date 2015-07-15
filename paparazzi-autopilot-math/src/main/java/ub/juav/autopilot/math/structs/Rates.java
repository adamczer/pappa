package ub.juav.autopilot.math.structs;

/**
 * Created by adamczer on 7/15/15.
 */
public abstract class Rates<T> {
    private T p;
    private T q;
    private T r;

    public Number getP() {
        return (Number) p;
    }

    public void setP(T p) {
        this.p = p;
    }

    public Number getQ() {
        return (Number) q;
    }

    public void setQ(T q) {
        this.q = q;
    }

    public Number getR() {
        return (Number) r;
    }

    public void setR(T r) {
        this.r = r;
    }
}

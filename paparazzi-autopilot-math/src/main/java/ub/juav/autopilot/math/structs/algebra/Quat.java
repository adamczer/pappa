package ub.juav.autopilot.math.structs.algebra;

/**
 * Created by adamczer on 7/15/15.
 */
public abstract class Quat<T extends Number> {
    private T qi;
    private T qx;
    private T qy;
    private T qz;

    public T getQi() {
        return qi;
    }

    public void setQi(T qi) {
        this.qi = qi;
    }

    public T getQx() {
        return qx;
    }

    public void setQx(T qx) {
        this.qx = qx;
    }

    public T getQy() {
        return qy;
    }

    public void setQy(T qy) {
        this.qy = qy;
    }

    public T getQz() {
        return qz;
    }

    public void setQz(T qz) {
        this.qz = qz;
    }
}

package ub.juav.autopilot.math.structs;

/**
 * Created by adamczer on 7/15/15.
 */
public abstract class Quat<T> {
    private T qi;
    private T qx;
    private T qy;
    private T qz;

    public Number getQi() {
        return (Number) qi;
    }

    public void setQi(T qi) {
        this.qi = qi;
    }

    public Number getQx() {
        return (Number) qx;
    }

    public void setQx(T qx) {
        this.qx = qx;
    }

    public Number getQy() {
        return (Number) qy;
    }

    public void setQy(T qy) {
        this.qy = qy;
    }

    public Number getQz() {
        return (Number) qz;
    }

    public void setQz(T qz) {
        this.qz = qz;
    }
}

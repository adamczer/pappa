package ub.juav.autopilot.math.structs;

/**
 * Created by adamczer on 7/15/15.
 */
public abstract class Eulers<T> {
    private T phi;
    private T theta;
    private T psi;

    public Number getPhi() {
        return (Number) phi;
    }

    public void setPhi(T phi) {
        this.phi = phi;
    }

    public Number getTheta() {
        return (Number) theta;
    }

    public void setTheta(T theta) {
        this.theta = theta;
    }

    public Number getPsi() {
        return (Number) psi;
    }

    public void setPsi(T psi) {
        this.psi = psi;
    }
}

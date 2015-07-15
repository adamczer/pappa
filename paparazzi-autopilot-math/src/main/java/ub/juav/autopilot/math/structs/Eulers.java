package ub.juav.autopilot.math.structs;

/**
 * Created by adamczer on 7/15/15.
 */
public abstract class Eulers<T> {
    private T phi;
    private T theta;
    private T psi;

    public T getPhi() {
        return phi;
    }

    public void setPhi(T phi) {
        this.phi = phi;
    }

    public T getTheta() {
        return theta;
    }

    public void setTheta(T theta) {
        this.theta = theta;
    }

    public T getPsi() {
        return psi;
    }

    public void setPsi(T psi) {
        this.psi = psi;
    }
}

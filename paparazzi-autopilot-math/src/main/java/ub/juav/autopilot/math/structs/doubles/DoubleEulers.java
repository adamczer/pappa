package ub.juav.autopilot.math.structs.doubles;

/**
 * Euler angles
 * units are in radians
 */
public class DoubleEulers {
    private double phi;
    private double theta;
    private double psi;

    public double getPhi() {
        return phi;
    }

    public void setPhi(double phi) {
        this.phi = phi;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getPsi() {
        return psi;
    }

    public void setPsi(double psi) {
        this.psi = psi;
    }
}

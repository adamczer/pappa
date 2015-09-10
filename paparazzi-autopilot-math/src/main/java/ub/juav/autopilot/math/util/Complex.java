package ub.juav.autopilot.math.util;

public class Complex {
    private float re,im;

    public Complex(float lambda_, float ll_) {
        re=lambda_;
        im=ll_;
    }

    public float getRe() {
        return re;
    }

    public void setRe(float re) {
        this.re = re;
    }

    public float getIm() {
        return im;
    }

    public void setIm(float im) {
        this.im = im;
    }
}
package ub.juav.autopilot.math.structs.geodetic;

import ub.juav.autopilot.math.structs.algebra.RMat;

/**
 * Created by adamczer on 9/7/15.
 */
public abstract class LtpDef<T extends Number> {
    private EcefCoor<T> ecefCoor;
    private LlaCoor<T> llaCoor;
    private RMat<T> ltp_of_ecef;
    private T hmsl;

    public EcefCoor<T> getEcefCoor() {
        return ecefCoor;
    }

    public void setEcefCoor(EcefCoor<T> ecefCoor) {
        this.ecefCoor = ecefCoor;
    }

    public LlaCoor<T> getLlaCoor() {
        return llaCoor;
    }

    public void setLlaCoor(LlaCoor<T> llaCoor) {
        this.llaCoor = llaCoor;
    }

    public RMat<T> getLtp_of_ecef() {
        return ltp_of_ecef;
    }

    public void setLtp_of_ecef(RMat<T> ltp_of_ecef) {
        this.ltp_of_ecef = ltp_of_ecef;
    }

    public Number getHmsl() {
        return hmsl;
    }

    public void setHmsl(T hmsl) {
        this.hmsl = hmsl;
    }
}

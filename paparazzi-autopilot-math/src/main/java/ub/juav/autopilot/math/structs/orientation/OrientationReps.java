package ub.juav.autopilot.math.structs.orientation;

import ub.juav.autopilot.math.primitive.wrappers.PrimitiveWrapper;
import ub.juav.autopilot.math.structs.algebra.Eulers;
import ub.juav.autopilot.math.structs.algebra.Quat;
import ub.juav.autopilot.math.structs.algebra.RMat;

/**
 * Created by adamczer on 11/22/15.
 */
public class OrientationReps {
    /**
     * Holds the status bits for all orientation representations.
     * When the corresponding bit is set, the representation
     * is already computed.
     */
    PrimitiveWrapper<Integer> status = new PrimitiveWrapper<>(0);

    /**
     * Orientation quaternion.
     * Units: #INT32_QUAT_FRAC
     */
    Quat<Integer> quat_i;

    /**
     * Orientation in zyx euler angles.
     * Units: rad in BFP with #INT32_ANGLE_FRAC
     */
    Eulers<Integer> eulers_i;

    /**
     * Orientation rotation matrix.
     * Units: rad in BFP with #INT32_TRIG_FRAC
     */
    RMat<Integer> rmat_i;

    /**
     * Orientation as quaternion.
     * Units: unit length quaternion
     */
    Quat<Float> quat_f;

    /**
     * Orienation in zyx euler angles.
     * Units: rad
     */
    Eulers<Float> eulers_f;

    /**
     * Orientation rotation matrix.
     * Units: rad
     */
    RMat<Float> rmat_f;

    public PrimitiveWrapper<Integer> getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status.setPrimitive(status);
    }

    public Quat<Integer> getQuat_i() {
        return quat_i;
    }

    public void setQuat_i(Quat<Integer> quat_i) {
        this.quat_i = quat_i;
    }

    public Eulers<Integer> getEulers_i() {
        return eulers_i;
    }

    public void setEulers_i(Eulers<Integer> eulers_i) {
        this.eulers_i = eulers_i;
    }

    public RMat<Integer> getRmat_i() {
        return rmat_i;
    }

    public void setRmat_i(RMat<Integer> rmat_i) {
        this.rmat_i = rmat_i;
    }

    public Quat<Float> getQuat_f() {
        return quat_f;
    }

    public void setQuat_f(Quat<Float> quat_f) {
        this.quat_f = quat_f;
    }

    public Eulers<Float> getEulers_f() {
        return eulers_f;
    }

    public void setEulers_f(Eulers<Float> eulers_f) {
        this.eulers_f = eulers_f;
    }

    public RMat<Float> getRmat_f() {
        return rmat_f;
    }

    public void setRmat_f(RMat<Float> rmat_f) {
        this.rmat_f = rmat_f;
    }
}

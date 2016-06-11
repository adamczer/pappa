package juav.autopilot.stabilization.attitude;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.Rates;

/**
 * Created by adamczer on 6/10/16.
 */
public class AttitudeRef<T extends Number> {
//    Eulers<T> euler;
    Quat<T> quat;
    Rates<T> rate;
    Rates<T> accel;
//    struct IntRefModel model;
//    struct Int32RefSat saturation;

    public static AttitudeRef<Integer> getIntegerFromJni() {
        AttitudeRef<Integer> ret = new AttitudeRef<>();
//        ret.euler = new Eulers<>();
//        ret.euler.setPsi(NativeTasks.getAttitudeRefEulerPsi());
//        ret.euler.setPhi(NativeTasks.getAttitudeRefEulerPhi());
//        ret.euler.setTheta(NativeTasks.getAttitudeRefEulerTheta());
        ret.quat = new Quat<>();
        ret.quat.setQi(NativeTasks.getAttitudeRefQuatQi());
        ret.quat.setQx(NativeTasks.getAttitudeRefQuatQx());
        ret.quat.setQy(NativeTasks.getAttitudeRefQuatQy());
        ret.quat.setQz(NativeTasks.getAttitudeRefQuatQz());
        ret.rate = new Rates<>();
        ret.rate.setP(NativeTasks.getAttitudeRefRateP());
        ret.rate.setQ(NativeTasks.getAttitudeRefRateQ());
        ret.rate.setR(NativeTasks.getAttitudeRefRateR());
        ret.accel = new Rates<>();
        ret.accel.setP(NativeTasks.getAttitudeRefAccelP());
        ret.accel.setQ(NativeTasks.getAttitudeRefAccelQ());
        ret.accel.setR(NativeTasks.getAttitudeRefAccelR());
        return ret;
    }

    public Quat<T> getQuat() {
        return quat;
    }

    public void setQuat(Quat<T> quat) {
        this.quat = quat;
    }

    public Rates<T> getRate() {
        return rate;
    }

    public void setRate(Rates<T> rate) {
        this.rate = rate;
    }

    public Rates<T> getAccel() {
        return accel;
    }

    public void setAccel(Rates<T> accel) {
        this.accel = accel;
    }
}

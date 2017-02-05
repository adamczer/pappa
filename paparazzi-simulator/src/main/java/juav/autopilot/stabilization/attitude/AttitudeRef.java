package juav.autopilot.stabilization.attitude;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.Rates;

/**
 * Created by adamczer on 6/10/16.
 */
public class AttitudeRef<T extends Number> {
    public Quat<T> quat;
    public Rates<T> rate;
    public Rates<T> accel;
    public Eulers<T> euler;
    public RefModel model;
    public RefSat<T> saturation;

    public static AttitudeRef<Integer> getIntegerFromJni() {
        AttitudeRef<Integer> ret = new AttitudeRef<>();
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
        ret.euler = new Eulers<>();
//        ret.euler.setPsi(NativeTasks.getAttitudeRefEulerPsi());
//        ret.euler.setPhi(NativeTasks.getAttitudeRefEulerPhi());
//        ret.euler.setTheta(NativeTasks.getAttitudeRefEulerTheta());
//        ret.model = RefModel.newRefModel();
//        ret.model.two_zeta_omega = Rates.newInteger();
//        ret.model.two_zeta_omega.p = NativeTasks.getAttitudeRefModelTwoZetaOmegaP();
//        ret.model.two_zeta_omega.q = NativeTasks.getAttitudeRefModelTwoZetaOmegaQ();
//        ret.model.two_zeta_omega.r = NativeTasks.getAttitudeRefModelTwoZetaOmegaR();
//        ret.model.two_omega2 = Rates.newInteger();
//        ret.model.two_omega2.p = NativeTasks.getAttitudeRefModelTwoOmega2P();
//        ret.model.two_omega2.q = NativeTasks.getAttitudeRefModelTwoOmega2Q();
//        ret.model.two_omega2.r = NativeTasks.getAttitudeRefModelTwoOmega2R();
//        ret.model.zeta = Rates.newFloat();
//        ret.model.zeta.p = NativeTasks.getAttitudeRefModelZetaP();
//        ret.model.zeta.q = NativeTasks.getAttitudeRefModelZetaQ();
//        ret.model.zeta.r = NativeTasks.getAttitudeRefModelZetaR();
//        ret.model.omega = Rates.newFloat();
//        ret.model.omega.p = NativeTasks.getAttitudeRefModelOmegaP();
//        ret.model.omega.q = NativeTasks.getAttitudeRefModelOmegaQ();
//        ret.model.omega.r = NativeTasks.getAttitudeRefModelOmegaR();
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

    public static AttitudeRef<Integer> newInteger() {
        AttitudeRef<Integer> ret = new AttitudeRef<>();
        ret.rate = Rates.newInteger();
        ret.accel = Rates.newInteger();
        ret.euler = Eulers.newInteger();
        ret.quat = Quat.newInteger();
        ret.model = RefModel.newRefModel();
        ret.saturation = RefSat.newRefSatInteger();
        return ret;
    }
}

package juav.autopilot.stabilization.attitude;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;
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
        ret.quat.setQi(NativeTasksWrapper.getAttitudeRefQuatQi());
        ret.quat.setQx(NativeTasksWrapper.getAttitudeRefQuatQx());
        ret.quat.setQy(NativeTasksWrapper.getAttitudeRefQuatQy());
        ret.quat.setQz(NativeTasksWrapper.getAttitudeRefQuatQz());
        ret.rate = new Rates<>();
        ret.rate.setP(NativeTasksWrapper.getAttitudeRefRateP());
        ret.rate.setQ(NativeTasksWrapper.getAttitudeRefRateQ());
        ret.rate.setR(NativeTasksWrapper.getAttitudeRefRateR());
        ret.accel = new Rates<>();
        ret.accel.setP(NativeTasksWrapper.getAttitudeRefAccelP());
        ret.accel.setQ(NativeTasksWrapper.getAttitudeRefAccelQ());
        ret.accel.setR(NativeTasksWrapper.getAttitudeRefAccelR());
        ret.euler = new Eulers<>();
        ret.euler.setPsi(NativeTasksWrapper.getAttitudeRefEulerPsi());
        ret.euler.setPhi(NativeTasksWrapper.getAttitudeRefEulerPhi());
        ret.euler.setTheta(NativeTasksWrapper.getAttitudeRefEulerTheta());
        ret.model = RefModel.newRefModel();
        ret.model.two_zeta_omega = Rates.newInteger();
        ret.model.two_zeta_omega.p = NativeTasksWrapper.getAttitudeRefModelTwoZetaOmegaP();
        ret.model.two_zeta_omega.q = NativeTasksWrapper.getAttitudeRefModelTwoZetaOmegaQ();
        ret.model.two_zeta_omega.r = NativeTasksWrapper.getAttitudeRefModelTwoZetaOmegaR();
        ret.model.two_omega2 = Rates.newInteger();
        ret.model.two_omega2.p = NativeTasksWrapper.getAttitudeRefModelTwoOmega2P();
        ret.model.two_omega2.q = NativeTasksWrapper.getAttitudeRefModelTwoOmega2Q();
        ret.model.two_omega2.r = NativeTasksWrapper.getAttitudeRefModelTwoOmega2R();
        ret.model.zeta = Rates.newFloat();
        ret.model.zeta.p = NativeTasksWrapper.getAttitudeRefModelZetaP();
        ret.model.zeta.q = NativeTasksWrapper.getAttitudeRefModelZetaQ();
        ret.model.zeta.r = NativeTasksWrapper.getAttitudeRefModelZetaR();
        ret.model.omega = Rates.newFloat();
        ret.model.omega.p = NativeTasksWrapper.getAttitudeRefModelOmegaP();
        ret.model.omega.q = NativeTasksWrapper.getAttitudeRefModelOmegaQ();
        ret.model.omega.r = NativeTasksWrapper.getAttitudeRefModelOmegaR();
        ret.saturation = RefSat.newRefSatInteger();
        ret.saturation.max_accel = Rates.newInteger();
        ret.saturation.max_accel.p = NativeTasksWrapper.getAttitudeRefSaturationMaxAccelP();
        ret.saturation.max_accel.q = NativeTasksWrapper.getAttitudeRefSaturationMaxAccelQ();
        ret.saturation.max_accel.r = NativeTasksWrapper.getAttitudeRefSaturationMaxAccelR();
        ret.saturation.max_rate = Rates.newInteger();
        ret.saturation.max_rate.p = NativeTasksWrapper.getAttitudeRefSaturationMaxRateP();
        ret.saturation.max_rate.q = NativeTasksWrapper.getAttitudeRefSaturationMaxRateQ();
        ret.saturation.max_rate.r = NativeTasksWrapper.getAttitudeRefSaturationMaxRateR();
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

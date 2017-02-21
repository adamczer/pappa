package juav.autopilot.state;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;
import ub.juav.airborne.math.structs.algebra.*;

/**
 * Created by adamczer on 10/28/16.
 */
public class State {
    public static Vect3<Integer> stateGetPositionNed_i() {
        Vect3<Integer> ret = Vect3.newInteger();
        ret.setX(NativeTasksWrapper.stateGetPositionNedIX());
        ret.setY(NativeTasksWrapper.stateGetPositionNedIY());
        ret.setZ(NativeTasksWrapper.stateGetPositionNedIZ());
        return ret;
    }

    public static Vect3<Integer> stateGetSpeedNed_i() {
        Vect3<Integer> ret = Vect3.newInteger();
        ret.setX(NativeTasksWrapper.stateGetSpeedNedIX());
        ret.setY(NativeTasksWrapper.stateGetSpeedNedIY());
        ret.setZ(NativeTasksWrapper.stateGetSpeedNedIZ());
        return ret;
    }

    public static RMat<Integer> stateGetNedToBodyRMat_i() {
        RMat<Integer> ret = RMat.RMatInteger();
        ret.setFlattendElement(0,NativeTasksWrapper.stateGetNedToBodyRMatI_0());
        ret.setFlattendElement(1,NativeTasksWrapper.stateGetNedToBodyRMatI_1());
        ret.setFlattendElement(2,NativeTasksWrapper.stateGetNedToBodyRMatI_2());
        ret.setFlattendElement(3,NativeTasksWrapper.stateGetNedToBodyRMatI_3());
        ret.setFlattendElement(4,NativeTasksWrapper.stateGetNedToBodyRMatI_4());
        ret.setFlattendElement(5,NativeTasksWrapper.stateGetNedToBodyRMatI_5());
        ret.setFlattendElement(6,NativeTasksWrapper.stateGetNedToBodyRMatI_6());
        ret.setFlattendElement(7,NativeTasksWrapper.stateGetNedToBodyRMatI_7());
        ret.setFlattendElement(8,NativeTasksWrapper.stateGetNedToBodyRMatI_8());
        return ret;
    }

    public static Rates<Integer> stateGetBodyRates_i() {
        Rates<Integer> ret = Rates.newInteger();
        ret.setP(NativeTasksWrapper.stateGetBodyRatesIP());
        ret.setQ(NativeTasksWrapper.stateGetBodyRatesIQ());
        ret.setR(NativeTasksWrapper.stateGetBodyRatesIR());
        return ret;
    }

    public static Eulers<Integer> stateGetNedToBodyEulers_i() {
        Eulers<Integer> ret = Eulers.newInteger();
        ret.setPsi(NativeTasksWrapper.stateGetNedToBodyEulersIPsiInt());
        ret.setTheta(NativeTasksWrapper.stateGetNedToBodyEulersITheataInt());
        ret.setPhi(NativeTasksWrapper.stateGetNedToBodyEulersIPhiInt());
        return ret;
    }

    public static Eulers<Float> stateGetNedToBodyEulers_f() {
        Eulers<Float> ret = Eulers.newFloat();
        ret.setPsi(NativeTasksWrapper.stateGetNedToBodyEulersIPsiFloat());
        ret.setTheta(NativeTasksWrapper.stateGetNedToBodyEulersITheataFloat());
        ret.setPhi(NativeTasksWrapper.stateGetNedToBodyEulersIPhiFloat());
        return ret;
    }

    public static Vect3<Integer> stateGetAccelNed_i() {
        Vect3<Integer> ret = Vect3.newInteger();
        ret.setX(NativeTasksWrapper.stateGetAccelNedIX());
        ret.setY(NativeTasksWrapper.stateGetAccelNedIY());
        ret.setZ(NativeTasksWrapper.stateGetAccelNedIZ());
        return ret;
    }

    public static Vect3<Float> stateGetSpeedNed_f() {
        Vect3<Float> ret = Vect3.newFloat();
        ret.setX(NativeTasksWrapper.stateGetSpeedNedFX());
        ret.setY(NativeTasksWrapper.stateGetSpeedNedFY());
        ret.setZ(NativeTasksWrapper.stateGetSpeedNedFZ());
        return ret;
    }

    public static Vect3<Float> stateGetAccelNed_f() {
        Vect3<Float> ret = Vect3.newFloat();
        ret.setX(NativeTasksWrapper.stateGetAccelNedFX());
        ret.setY(NativeTasksWrapper.stateGetAccelNedFY());
        ret.setZ(NativeTasksWrapper.stateGetAccelNedFZ());
        return ret;
    }

    public static boolean stateIsAttitudeValid() {
        return NativeTasksWrapper.stateIsAttitudeValid();
    }

    public static Quat<Integer> getNedToBodyQuatI() {
        Quat<Integer> att_quat = Quat.newInteger();
        att_quat.setQi(NativeTasksWrapper.stateGetNedToBodyQuatIQi());
        att_quat.setQx(NativeTasksWrapper.stateGetNedToBodyQuatIQx());
        att_quat.setQy(NativeTasksWrapper.stateGetNedToBodyQuatIQy());
        att_quat.setQz(NativeTasksWrapper.stateGetNedToBodyQuatIQz());
        return att_quat;
    }
}

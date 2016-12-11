package juav.autopilot.state;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.structs.algebra.*;

/**
 * Created by adamczer on 10/28/16.
 */
public class State {
    public static Vect3<Integer> stateGetPositionNed_i() {
        Vect3<Integer> ret = Vect3.newInteger();
        ret.setX(NativeTasks.stateGetPositionNedIX());
        ret.setY(NativeTasks.stateGetPositionNedIY());
        ret.setZ(NativeTasks.stateGetPositionNedIZ());
        return ret;
    }

    public static Vect3<Integer> stateGetSpeedNed_i() {
        Vect3<Integer> ret = Vect3.newInteger();
        ret.setX(NativeTasks.stateGetSpeedNedIX());
        ret.setY(NativeTasks.stateGetSpeedNedIY());
        ret.setZ(NativeTasks.stateGetSpeedNedIZ());
        return ret;
    }

    public static RMat<Integer> stateGetNedToBodyRMat_i() {
        RMat<Integer> ret = RMat.RMatInteger();
        ret.setFlattendElement(0,NativeTasks.stateGetNedToBodyRMatI_0());
        ret.setFlattendElement(1,NativeTasks.stateGetNedToBodyRMatI_1());
        ret.setFlattendElement(2,NativeTasks.stateGetNedToBodyRMatI_2());
        ret.setFlattendElement(3,NativeTasks.stateGetNedToBodyRMatI_3());
        ret.setFlattendElement(4,NativeTasks.stateGetNedToBodyRMatI_4());
        ret.setFlattendElement(5,NativeTasks.stateGetNedToBodyRMatI_5());
        ret.setFlattendElement(6,NativeTasks.stateGetNedToBodyRMatI_6());
        ret.setFlattendElement(7,NativeTasks.stateGetNedToBodyRMatI_7());
        ret.setFlattendElement(8,NativeTasks.stateGetNedToBodyRMatI_8());
        return ret;
    }

    public static Rates<Integer> stateGetBodyRates_i() {
        Rates<Integer> ret = Rates.newInteger();
        ret.setP(NativeTasks.stateGetBodyRatesIP());
        ret.setQ(NativeTasks.stateGetBodyRatesIQ());
        ret.setR(NativeTasks.stateGetBodyRatesIR());
        return ret;
    }

    public static Eulers<Integer> stateGetNedToBodyEulers_i() {
        Eulers<Integer> ret = Eulers.newInteger();
        ret.setPsi(NativeTasks.stateGetNedToBodyEulersIPsiInt());
        ret.setTheta(NativeTasks.stateGetNedToBodyEulersITheataInt());
        ret.setPhi(NativeTasks.stateGetNedToBodyEulersIPhiInt());
        return ret;
    }

    public static Eulers<Float> stateGetNedToBodyEulers_f() {
        Eulers<Float> ret = Eulers.newFloat();
        ret.setPsi(NativeTasks.stateGetNedToBodyEulersIPsiFloat());
        ret.setTheta(NativeTasks.stateGetNedToBodyEulersITheataFloat());
        ret.setPhi(NativeTasks.stateGetNedToBodyEulersIPhiFloat());
        return ret;
    }

    public static Vect3<Integer> stateGetAccelNed_i() {
        Vect3<Integer> ret = Vect3.newInteger();
        ret.setX(NativeTasks.stateGetAccelNedIX());
        ret.setY(NativeTasks.stateGetAccelNedIY());
        ret.setZ(NativeTasks.stateGetAccelNedIZ());
        return ret;
    }

    public static Vect3<Float> stateGetSpeedNed_f() {
        Vect3<Float> ret = Vect3.newFloat();
        ret.setX(NativeTasks.stateGetSpeedNedFX());
        ret.setY(NativeTasks.stateGetSpeedNedFY());
        ret.setZ(NativeTasks.stateGetSpeedNedFZ());
        return ret;
    }

    public static Vect3<Float> stateGetAccelNed_f() {
        Vect3<Float> ret = Vect3.newFloat();
        ret.setX(NativeTasks.stateGetAccelNedFX());
        ret.setY(NativeTasks.stateGetAccelNedFY());
        ret.setZ(NativeTasks.stateGetAccelNedFZ());
        return ret;
    }

    public static boolean stateIsAttitudeValid() {
        return NativeTasks.stateIsAttitudeValid();
    }
}

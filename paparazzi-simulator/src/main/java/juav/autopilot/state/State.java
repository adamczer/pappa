package juav.autopilot.state;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.RMat;
import ub.juav.airborne.math.structs.algebra.Rates;
import ub.juav.airborne.math.structs.algebra.Vect2;

/**
 * Created by adamczer on 10/28/16.
 */
public class State {
    public static Vect2<Integer> stateGetPositionNed_i() {
        Vect2<Integer> ret = Vect2.newIntVect2();
        ret.setX(NativeTasks.stateGetPositionNedIX());
        ret.setY(NativeTasks.stateGetPositionNedIY());
        return ret;
    }

    public static Vect2<Integer> stateGetSpeedNed_i() {
        Vect2<Integer> ret = Vect2.newIntVect2();
        ret.setX(NativeTasks.stateGetSpeedNedIX());
        ret.setY(NativeTasks.stateGetSpeedNedIY());
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
 }

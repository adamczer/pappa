package juav.autopilot.stabilization.attitude;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.structs.algebra.Vect3;

/**
 * Created by adamczer on 6/10/16.
 */
public class AttitudeGains<T extends Number> {
    Vect3<T> p;
    Vect3<T> d;
    Vect3<T> dd;
    Vect3<T> i;

    public Vect3<T> getP() {
        return p;
    }

    public void setP(Vect3<T> p) {
        this.p = p;
    }

    public Vect3<T> getD() {
        return d;
    }

    public void setD(Vect3<T> d) {
        this.d = d;
    }

    public Vect3<T> getDd() {
        return dd;
    }

    public void setDd(Vect3<T> dd) {
        this.dd = dd;
    }

    public Vect3<T> getI() {
        return i;
    }

    public void setI(Vect3<T> i) {
        this.i = i;
    }

    public static AttitudeGains<Integer> newInteger() {
        AttitudeGains<Integer> ret =  new AttitudeGains<>();
        ret.p = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(ret.p,0,0,0);
        ret.d = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(ret.d,0,0,0);
        ret.dd = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(ret.dd,0,0,0);
        ret.i = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(ret.i,0,0,0);
        return ret;
    }

    public static AttitudeGains<Integer> getIntegerFromJni() {
        AttitudeGains<Integer> gains = newInteger();
        gains.p.setX(NativeTasksWrapper.getAttitudeGainPX());
        gains.p.setY(NativeTasksWrapper.getAttitudeGainPY());
        gains.p.setZ(NativeTasksWrapper.getAttitudeGainPZ());
        gains.d.setX(NativeTasksWrapper.getAttitudeGainDX());
        gains.d.setY(NativeTasksWrapper.getAttitudeGainDY());
        gains.d.setZ(NativeTasksWrapper.getAttitudeGainDZ());
        gains.dd.setX(NativeTasksWrapper.getAttitudeGainDdX());
        gains.dd.setY(NativeTasksWrapper.getAttitudeGainDdY());
        gains.dd.setZ(NativeTasksWrapper.getAttitudeGainDdZ());
        gains.i.setX(NativeTasksWrapper.getAttitudeGainIX());
        gains.i.setY(NativeTasksWrapper.getAttitudeGainIY());
        gains.i.setZ(NativeTasksWrapper.getAttitudeGainIZ());
        return gains;
    }
}

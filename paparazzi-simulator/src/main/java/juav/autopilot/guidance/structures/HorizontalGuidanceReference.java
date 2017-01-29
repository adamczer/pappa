package juav.autopilot.guidance.structures;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.structs.algebra.Vect2;

/**
 * Created by adamczer on 10/10/16.
 */
public class HorizontalGuidanceReference {
//    public Vect2<Integer> speed;   ///< with #INT32_SPEED_FRAC
//    public Vect2<Integer> accel;   ///< with #INT32_ACCEL_FRAC

    public HorizontalGuidanceReference () {
//        speed = Vect2.newIntVect2();
//        accel = Vect2.newIntVect2();
    }

    public Vect2<Integer> getPos() {
        Vect2<Integer> ret = Vect2.newIntVect2();
        ret.setX(NativeTasks.getHorizantialGuidanceReferencePosX());
        ret.setY(NativeTasks.getHorizantialGuidanceReferencePosY());
        return ret;
    }
    public void setPos(Vect2<Integer> newPos) {
        NativeTasks.setHorizantialGuidanceReferencePosX(newPos.getX());
        NativeTasks.setHorizantialGuidanceReferencePosY(newPos.getY());
    }
    public Vect2<Integer> getSpeed() {
        Vect2<Integer> ret = Vect2.newIntVect2();
        ret.setX(NativeTasks.getHorizantialGuidanceReferenceSpeedX());
        ret.setY(NativeTasks.getHorizantialGuidanceReferenceSpeedY());
        return ret;
    }
    public void setSpeed(Vect2<Integer> newSpeed) {
        NativeTasks.setHorizantialGuidanceReferenceSpeedX(newSpeed.getX());
        NativeTasks.setHorizantialGuidanceReferenceSpeedY(newSpeed.getY());
    }
    public Vect2<Integer> getAccel() {
        Vect2<Integer> ret = Vect2.newIntVect2();
        ret.setX(NativeTasks.getHorizantialGuidanceReferenceAccelX());
        ret.setY(NativeTasks.getHorizantialGuidanceReferenceAccelY());
        return ret;
    }
    public void setAccel(Vect2<Integer> newAccel) {
        NativeTasks.setHorizantialGuidanceReferenceAccelX(newAccel.getX());
        NativeTasks.setHorizantialGuidanceReferenceAccelY(newAccel.getY());
    }


}

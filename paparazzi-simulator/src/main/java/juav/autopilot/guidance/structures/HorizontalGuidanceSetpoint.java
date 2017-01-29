package juav.autopilot.guidance.structures;

import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.structs.algebra.Vect2;

/**
 * Created by adamczer on 10/10/16.
 */
public class HorizontalGuidanceSetpoint {
    /** horizontal position setpoint in NED.
     *  fixed point representation: Q23.8
     *  accuracy 0.0039, range 8388km
     */
//    public Vect2<Integer> pos = Vect2.newIntVect2();
//    public Vect2<Integer> speed = Vect2.newIntVect2();  ///< only used if GUIDANCE_H_USE_SPEED_REF
    public int heading = 0;          ///< with #INT32_ANGLE_FRAC
    public Vect2<Integer> getPos() {
        Vect2<Integer> ret = Vect2.newIntVect2();
        ret.setX(NativeTasks.getHorizantialGuidanceSetPointPosX());
        ret.setY(NativeTasks.getHorizantialGuidanceSetPointPosY());
        return ret;
    }
    public void setPos(Vect2<Integer> newPos) {
        NativeTasks.setHorizantialGuidanceSetPointPosX(newPos.getX());
        NativeTasks.setHorizantialGuidanceSetPointPosY(newPos.getY());
    }

    public Vect2<Integer> getSpeed() {
        Vect2<Integer> ret = Vect2.newIntVect2();
        ret.setX(NativeTasks.getHorizantialGuidanceSetPointSpeedX());
        ret.setY(NativeTasks.getHorizantialGuidanceSetPointSpeedY());
        return ret;
    }
}

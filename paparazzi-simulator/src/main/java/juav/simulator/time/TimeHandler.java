package juav.simulator.time;

import ub.cse.juav.jni.nps.PaparazziNpsWrapper;

/**
 * Created by adamczer on 1/24/16.
 */
public class TimeHandler implements ITimeHandler {


    @Override
    public void init() {
//        initialized in jni
    }

    @Override
    public double getTime() {
        return PaparazziNpsWrapper.getNpsMainSimTime();
    }

    @Override
    public void setScaleFactor(double scaleFactor) {
//        initialized in jni
    }
}

package juav.simulator.time;

import ub.cse.juav.jni.nps.PaparazziNps;

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
        return PaparazziNps.getNpsMainSimTime();
    }

    @Override
    public void setScaleFactor(double scaleFactor) {
//        initialized in jni
    }
}

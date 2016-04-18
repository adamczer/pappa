package juav.simulator.tasks.jni;

import juav.simulator.tasks.PeriodicTask;
import ub.cse.juav.jni.tasks.NativeTasks;

/**
 * Created by adamczer on 4/17/16.
 */
public class JniNpsAtmosphereUpdate extends PeriodicTask {

    @Override
    public void execute() {
        NativeTasks.atmosphereUpdate();
    }

    @Override
    public void init() {

    }
}

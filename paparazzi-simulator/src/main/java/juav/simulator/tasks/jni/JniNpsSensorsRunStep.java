package juav.simulator.tasks.jni;

import juav.simulator.tasks.PeriodicTask;
import ub.cse.juav.jni.nps.PaparazziNps;
import ub.cse.juav.jni.tasks.NativeTasks;

/**
 * Created by adamczer on 4/17/16.
 */
public class JniNpsSensorsRunStep extends PeriodicTask {

    @Override
    public void execute() {
        NativeTasks.sensorsRunStep(PaparazziNps.getNpsMainSimTime());
    }

    @Override
    public void init() {

    }
}

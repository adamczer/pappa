package juav.simulator.tasks.jni;

import juav.simulator.tasks.PeriodicTask;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;

/**
 * Created by adamczer on 4/17/16.
 */
public class JniNpsFdmRunStep extends PeriodicTask {
    @Override
    public void execute() {
        NativeTasksWrapper.fdmRunStep();
    }

    @Override
    public void init() {

    }
}

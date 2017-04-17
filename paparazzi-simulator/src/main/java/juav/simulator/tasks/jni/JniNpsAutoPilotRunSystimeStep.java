package juav.simulator.tasks.jni;

import jive.logging.StateTransitions;
import juav.simulator.tasks.PeriodicTask;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;

/**
 * Created by adamczer on 4/17/16.
 */
public class JniNpsAutoPilotRunSystimeStep extends PeriodicTask {
    @Override
    public void execute() {
        NativeTasksWrapper.autoPilotRunSystimeStep();
        StateTransitions.instance.add_transition(new String[]{"Run Sys Time"});
    }

    @Override
    public void init() {

    }
}

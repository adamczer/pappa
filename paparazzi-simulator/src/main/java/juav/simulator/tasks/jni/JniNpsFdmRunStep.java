package juav.simulator.tasks.jni;

import jive.logging.StateTransitions;
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
        StateTransitions.instance.add_transition(new String[]{"Run FDM"});
    }

    @Override
    public void init() {

    }
}

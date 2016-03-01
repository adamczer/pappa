package juav.simulator.nps.cyclic;

import juav.simulator.nps.AbstractNpsImpl;
import juav.simulator.tasks.ITask;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamczer on 1/24/16.
 */
public class NpsCyclicImpl extends AbstractNpsImpl {

    /**
     * Cyclic execution of the of the periodic step functions that are
     * available followed by the set of periodic tasks
     */
    @Override
    public void run() {
        if(timeHandler==null) {
            throw new IllegalStateException("Time handler must be set on Nps simulator.");
        }
        while(run.get()) {
            DateTime now = timeHandler.getTime();
            for (ITask task : tasks) {
                if (task.isAvailiable()) {
                    task.execute();
                }
            }
        }
    }

    @Override
    public void init() {
        List<ITask> taskList = new ArrayList<>();
//        TODO
        // Atmosphere // not needed

        // JSBsim JNI call step state

        // FDM

        // Sensors

        // Compute commands and send to jsb sim
        setTasks(taskList);

//        init c++ jni things

    }

    public static void main(String[] args) {
        NpsCyclicImpl nps = new NpsCyclicImpl();
        nps.init();
        nps.run();
    }

}

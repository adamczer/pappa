package juav.simulator.nps.cyclic;

import juav.simulator.tasks.IFeedableTask;
import juav.simulator.tasks.IPeriodicTask;
import juav.simulator.nps.AbstractNpsImpl;
import juav.simulator.time.JodaTimeHandler;
import org.joda.time.DateTime;

import java.util.ArrayList;

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
            for (IFeedableTask task : stepFunctions) {
                if (task.isAvailiable()) {
                    task.execute();
                }
            }
            for(IPeriodicTask periodicTask : periodicTasks) {
                if(periodicTask.isAvailiable())
                    periodicTask.execute();
            }
        }
    }

    @Override
    public void init() {

    }

    public static void main(String[] args) {
        NpsCyclicImpl nps = new NpsCyclicImpl();
        //TODO initialize nps
//        ....

        nps.run();
    }

}

package juav.simulator.nps.nps.cyclic;

import juav.airborne.firmwars.rotorcraft.periodic.IPeriodicTask;
import juav.airborne.firmwars.rotorcraft.step.IStepFunction;
import juav.simulator.nps.nps.AbstractNpsImpl;
import org.joda.time.DateTime;

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
            for (IStepFunction stepFunction : stepFunctions) {
                if (stepFunction.isAvailable(now)) {
                    stepFunction.feedData();
                    stepFunction.mainEvent();
                }
            }
            for(IPeriodicTask periodicTask : periodicTasks) {
                if(periodicTask.shouldExecute(now))
                    periodicTask.execute();
            }
        }
    }

    @Override
    public void init() {

    }

}

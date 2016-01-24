package juav.simulator.nps;

import juav.simulator.tasks.IFeedableTask;
import juav.simulator.tasks.IPeriodicTask;
import juav.simulator.time.ITimeHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by adamczer on 1/24/16.
 */
public abstract class AbstractNpsImpl implements INps{
    protected List<IFeedableTask> stepFunctions;
    protected List<IPeriodicTask> periodicTasks;
    protected AtomicBoolean run = new AtomicBoolean(true);
    protected ITimeHandler timeHandler;

    /**
     * sets periodic step functions that will execute prior to the set of
     * periodic tasks .
     * @param stepFunctionList
     */
    @Override
    public void setFeedableTasks(List<IFeedableTask> stepFunctionList) {
        this.stepFunctions = stepFunctionList;
    }

    /**
     * sets periodic task functions that will execute after to the set of
     * periodic step functions .
     * @param periodicTasks
     */
    @Override
    public void setPeriodicTasks(List<IPeriodicTask> periodicTasks) {
        this.periodicTasks = periodicTasks;
    }

    /**
     * cause the execution to stop.
     */
    @Override
    public void stopExecution() {
        run.set(false);
    }

    @Override
    public void setTimeHandler(ITimeHandler timeHandler) {
        this.timeHandler = timeHandler;
    }
}

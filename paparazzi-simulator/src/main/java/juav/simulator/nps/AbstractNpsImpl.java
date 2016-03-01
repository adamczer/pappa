package juav.simulator.nps;

import juav.simulator.tasks.ITask;
import juav.simulator.time.ITimeHandler;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by adamczer on 1/24/16.
 */
public abstract class AbstractNpsImpl implements INps{
    protected List<ITask> tasks;
    protected AtomicBoolean run = new AtomicBoolean(true);
    protected ITimeHandler timeHandler;

    /**
     * sets periodic tasks that will execute \ .
     * @param tasks
     */
    @Override
    public void setTasks(List<ITask> tasks) {
        this.tasks = tasks;
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

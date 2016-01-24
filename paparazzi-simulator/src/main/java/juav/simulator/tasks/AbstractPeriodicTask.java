package juav.simulator.tasks;

/**
 * Created by adamczer on 1/24/16.
 */
public abstract class AbstractPeriodicTask implements IPeriodicTask{
    protected int interval;

    @Override
    public void setInterval(int interval) {
        this.interval=interval;
    }

    @Override
    public void execute() {
        executePeriodic();
    }

    protected abstract void executePeriodic();
}

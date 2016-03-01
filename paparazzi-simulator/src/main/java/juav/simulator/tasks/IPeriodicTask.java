package juav.simulator.tasks;

/**
 * Created by adamczer on 1/24/16.
 */
public interface IPeriodicTask extends ITask {
    /**
     * set the interval that must be elapsed in for an execution to be required
     */
    void setInterval(int interval);
}

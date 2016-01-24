package juav.simulator.tasks;

/**
 * Created by adamczer on 1/24/16.
 */
public interface Task {
    /**
     * This begins the execution of a task that will
     * execute for a given task.
     */
    void execute();

    /**
     * returns true iff the periodic task should execute most likely determined
     * by the amount of elapsed time
     */
    boolean isAvailiable();
}

package juav.simulator.tasks;

/**
 * Created by adamczer on 1/24/16.
 */
public interface ITask {
    /**
     * This begins the execution of a device that will
     * execute for a given device.
     */
    void execute();

    /**
     * returns true iff the periodic device should execute most likely determined
     * by the amount of elapsed time
     */
    boolean isAvailiable();
}

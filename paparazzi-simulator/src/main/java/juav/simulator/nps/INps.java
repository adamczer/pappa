package juav.simulator.nps;

import juav.simulator.tasks.ITask;
import juav.simulator.time.ITimeHandler;

import java.util.List;

/**
 * Created by adamczer on 1/24/16.
 */
public interface INps {

    /**
     * sets the periodic tasks that will be run
     * @param tasks
     */
    void setTasks(List<ITask> tasks);

    /**
     * Begin the execution of the simulation
     */
    void run();

    /**
     * Stop the execution of the simulation.
     */
    void stopExecution();

    /**
     * Set the timeHandler used by the system
     * @param timeHandler
     */
    void setTimeHandler(ITimeHandler timeHandler);

    /**
     * Initializes underlying components prior to run
     */
    void init();
}

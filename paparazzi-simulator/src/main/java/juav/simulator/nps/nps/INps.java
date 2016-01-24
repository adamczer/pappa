package juav.simulator.nps.nps;

import juav.airborne.firmwars.rotorcraft.periodic.IPeriodicTask;
import juav.airborne.firmwars.rotorcraft.step.IStepFunction;
import juav.simulator.nps.time.ITimeHandler;

import java.util.List;

/**
 * Created by adamczer on 1/24/16.
 */
public interface INps {
    /**
     * sets the periodic functions that run
     * @param stepFunctionList
     */
    void setStepFunctions(List<IStepFunction> stepFunctionList);

    /**
     * sets the periodic tasks that will be run
     * @param periodicTasks
     */
    void setPeriodicTasks(List<IPeriodicTask> periodicTasks);

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

package juav.airborne.firmwars.rotorcraft.periodic;

import juav.simulator.nps.time.ITimeHandler;
import org.joda.time.DateTime;

/**
 * Created by adamczer on 1/24/16.
 */
public interface IPeriodicTask {
    /**
     * The task that will execute for a given periodic task.
     */
    void execute();

    /**
     * returns true iff the periodic task should execute most likely determined
     * by the amount of elapsed time
     */
    boolean shouldExecute(DateTime currentTime);

    /**
     * set the interval that must be elapsed in for an execution to be required
     */
    void setInterval(int milliSeconds);
}

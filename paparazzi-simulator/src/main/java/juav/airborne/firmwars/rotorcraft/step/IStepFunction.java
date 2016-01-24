package juav.airborne.firmwars.rotorcraft.step;

import juav.simulator.nps.time.ITimeHandler;
import org.joda.time.DateTime;

/**
 * Created by adamczer on 1/24/16.
 */
public interface IStepFunction {

    /**
     * used to check if the periodic step function is available and
     * should execute for each iteration.
     *
     * @return
     */
    boolean isAvailable(DateTime dateTime);

    /**
     * Function used to feed the values that will be pulled in the main event
     * function below.
     */
    void feedData();

    /**
     * Execute task based on the the new values that have been feed
     * to the object used for decision making in the main event.
     */
    void mainEvent();
}

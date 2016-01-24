package juav.simulator.nps.time;

import org.joda.time.DateTime;

/**
 * Created by adamczer on 1/24/16.
 */
public interface ITimeHandler {
    /**
     * Initializes the time handler
     */
    void init();

    /**
     * returns the current simulation time for the system to use
     * @return
     */
    DateTime getTime();

    /**
     * Set the factor for wich the time is scaled for the system time
     * conversions to simulation time.
     * @param scaleFactor
     */
    void setScaleFactor(double scaleFactor);
}

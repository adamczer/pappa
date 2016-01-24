package juav.simulator.time;

import org.joda.time.DateTime;

/**
 * Created by adamczer on 1/24/16.
 */
public class JodaTimeHandler implements ITimeHandler {
    DateTime systemInitTime;
    DateTime simulationInitTime;
    double scaleFactor;


    @Override
    public void init() {
        systemInitTime = DateTime.now();
        simulationInitTime = systemInitTime;
    }

    @Override
    public DateTime getTime() {
        return new DateTime(systemInitTime.getMillis() + (DateTime.now().minus(systemInitTime.getMillis()).getMillis()*scaleFactor));
    }

    @Override
    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
    }
}

package juav.simulator.tasks;

import juav.simulator.time.ITimeHandler;
import org.joda.time.DateTime;

/**
 * Created by adamczer on 1/24/16.
 */
public abstract class TimerBasedPeriodicTask extends AbstractPeriodicTask {
    protected ITimeHandler timeHadler;
    protected double lastExecution = -1;
    @Override
    public boolean isAvailiable() {
        double now = timeHadler.getTime();
        if(lastExecution <0 || (now-lastExecution)>interval) {
            return true;
        }
        return false;
    }

    public void setTimeHandler(ITimeHandler timeHandler) {
        this.timeHadler = timeHandler;
    }

    @Override
    public void execute() {
        lastExecution = timeHadler.getTime();
        super.execute();
    }

}

package juav.simulator.tasks;

import juav.simulator.time.ITimeHandler;
import org.joda.time.DateTime;

/**
 * Created by adamczer on 1/24/16.
 */
public abstract class TimerBasedPeriodicTask extends AbstractPeriodicTask {
    protected ITimeHandler timeHadler;
    protected DateTime lastExecution;
    @Override
    public boolean isAvailiable() {
        DateTime now = timeHadler.getTime();
        if(lastExecution ==null || (now.getMillis()-lastExecution.getMillis())>interval) {
            return true;
        }
        return false;
    }

    void setTimeHandler(ITimeHandler timeHandler) {
        this.timeHadler = timeHandler;
    }

    @Override
    public void execute() {
        lastExecution = timeHadler.getTime();
        super.execute();
    }

}

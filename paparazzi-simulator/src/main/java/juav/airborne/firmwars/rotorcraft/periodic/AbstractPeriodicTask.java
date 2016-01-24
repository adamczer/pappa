package juav.airborne.firmwars.rotorcraft.periodic;

import juav.simulator.nps.time.ITimeHandler;
import org.joda.time.DateTime;

/**
 * Created by adamczer on 1/24/16.
 */
public abstract class AbstractPeriodicTask implements IPeriodicTask{
    protected long interval;
    protected DateTime lastExecution;

    @Override
    public void setInterval(int milliSeconds) {
        interval=milliSeconds;
    }

    @Override
    public boolean shouldExecute(DateTime now) {
        if(lastExecution==null || (now.getMillis() -lastExecution.getMillis()) > interval ) {
            return true;
        }
        else
            return false;
    }

    /**
     * This function should be overridden by the implementation
     * and it implementation should call super.
     */
    @Override
    public void execute() {
        lastExecution = DateTime.now();
    }


}

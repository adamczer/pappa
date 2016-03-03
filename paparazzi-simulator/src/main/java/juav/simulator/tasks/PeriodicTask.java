package juav.simulator.tasks;

/**
 * Created by adamczer on 1/24/16.
 */
public abstract class PeriodicTask implements IPeriodicTask{
    @Override
    public boolean isAvailiable() {
        return true;
    }

    @Override
    public void setInterval(int interval) {
        interval = 0;
    }
}

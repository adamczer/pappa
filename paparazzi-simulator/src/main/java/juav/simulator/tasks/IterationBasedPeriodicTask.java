package juav.simulator.tasks;

/**
 * Created by adamczer on 1/24/16.
 */
public abstract class IterationBasedPeriodicTask extends AbstractPeriodicTask{
    protected int iteration = 0;
    @Override
    public boolean isAvailiable() {
        return iteration++==interval;
    }

    @Override
    public void execute() {
        iteration = 0;
        super.execute();
    }
}

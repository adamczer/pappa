package juav.simulator.tasks.runonceevery;

/**
 * Created by adamczer on 12/11/16.
 */
public abstract class RunOnceEvery {
    int counter = 0;
    public void runOnceEvery(int threshold) {
         if(counter++>=threshold) {
             work();
             counter=0;
         }
    }
    protected abstract void work();
}

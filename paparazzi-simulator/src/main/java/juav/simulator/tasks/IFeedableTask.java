package juav.simulator.tasks;

/**
 * Created by adamczer on 1/24/16.
 */
public interface IFeedableTask<T> extends Task {
    void feedData(T data);
}

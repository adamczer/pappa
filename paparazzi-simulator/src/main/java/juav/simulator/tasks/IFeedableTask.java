package juav.simulator.tasks;

/**
 * Created by adamczer on 1/24/16.
 */
@Deprecated
public interface IFeedableTask<T> extends ITask {
    void feedData(T data);
}

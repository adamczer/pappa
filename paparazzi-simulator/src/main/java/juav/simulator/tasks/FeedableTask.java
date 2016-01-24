package juav.simulator.tasks;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by adamczer on 1/24/16.
 */
public abstract class FeedableTask<T> implements IFeedableTask<T>{
    private Queue<T> data = new LinkedList<>();
    @Override
    public void execute() {
        mainEvent(data.poll());
    }

    @Override
    public boolean isAvailiable() {
        return data.isEmpty();
    }

    @Override
    public void feedData(T data) {
        this.data.add(data);
    }

    /**
     * Execute task based on the the new values that have been feed
     * to the object used for decision making in the main event.
     * @param poll
     */
    protected abstract void mainEvent(T poll);

}

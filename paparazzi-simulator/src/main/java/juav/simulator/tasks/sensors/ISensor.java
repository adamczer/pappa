package juav.simulator.tasks.sensors;

import juav.simulator.tasks.TimerBasedPeriodicTask;

/**
 * Created by adamczer on 2/29/16.
 */
public abstract class ISensor<T> extends TimerBasedPeriodicTask {
    T data;
    protected T getReading() {
        if(data==null) {
            throw new RuntimeException("The sensor has not been polled yet.");
        }
        return data;
    }

    protected void setReading(T newData) {
        this.data = newData;
    }
}

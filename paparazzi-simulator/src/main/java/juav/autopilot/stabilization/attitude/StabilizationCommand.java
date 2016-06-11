package juav.autopilot.stabilization.attitude;

/**
 * Created by adamczer on 6/10/16.
 */
public class StabilizationCommand<T> {
    T yaw;
    T pitch;
    T roll;

    public T getYaw() {
        return yaw;
    }

    public void setYaw(T yaw) {
        this.yaw = yaw;
    }

    public T getPitch() {
        return pitch;
    }

    public void setPitch(T pitch) {
        this.pitch = pitch;
    }

    public T getRoll() {
        return roll;
    }

    public void setRoll(T roll) {
        this.roll = roll;
    }

    public static StabilizationCommand<Integer> newInteger() {
        StabilizationCommand<Integer> ret = new StabilizationCommand<>();
        ret.yaw = 0;
        ret.pitch = 0;
        ret.roll = 0;
        return ret;
    }
}

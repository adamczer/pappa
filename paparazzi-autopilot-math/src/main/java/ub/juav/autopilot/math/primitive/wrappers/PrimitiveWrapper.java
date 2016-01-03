package ub.juav.autopilot.math.primitive.wrappers;

/**
 * Created by adamczer on 1/3/16.
 */
public class PrimitiveWrapper<T> {
    private T primitive;

    public PrimitiveWrapper(T t) {
        primitive = t;
    }

    public T getPrimitive() {
        return primitive;
    }

    public void setPrimitive(T primitive) {
        this.primitive = primitive;
    }
}

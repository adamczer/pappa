package ub.juav.autopilot.math.structs.geodetic;

/**
 * Created by adamczer on 9/7/15.
 */
public abstract class LlaCoor<T extends Number> {
    private T lat;
    private T lon;
    private T alt;

    public T getLat() {
        return lat;
    }

    public void setLat(T lat) {
        this.lat = lat;
    }

    public T getLon() {
        return lon;
    }

    public void setLon(T lon) {
        this.lon = lon;
    }

    public T getAlt() {
        return alt;
    }

    public void setAlt(T alt) {
        this.alt = alt;
    }
}

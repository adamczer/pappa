package ub.juav.autopilot.math.structs.geodetic;

/**
 * Created by adamczer on 9/7/15.
 */
public abstract class UtmCoor<T extends Number> {
    private T north;
    private T east;
    private T alt;
    private int zone;

    public Number getNorth() {
        return north;
    }

    public void setNorth(T north) {
        this.north = north;
    }

    public Number getEast() {
        return east;
    }

    public void setEast(T east) {
        this.east = east;
    }

    public Number getAlt() {
        return alt;
    }

    public void setAlt(T alt) {
        this.alt = alt;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }
}

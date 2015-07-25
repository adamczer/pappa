package ub.juav.autopilot.math.util;

/**
 * Created by adamczer on 7/25/15.
 */
public class LlDiv {
    private final long rem, quot;

    private LlDiv(long numer, long dnonm) {
        rem = numer%dnonm;
        quot = numer/dnonm;
    }

    public long getRem() {
        return rem;
    }

    public long getQuot() {
        return quot;
    }

    public static LlDiv lldiv(long numer,long denom) {
        return new LlDiv(numer,denom);
    }
}

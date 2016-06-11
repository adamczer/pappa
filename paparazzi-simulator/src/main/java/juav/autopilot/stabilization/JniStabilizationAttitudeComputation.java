package juav.autopilot.stabilization;

/**
 * Created by adamczer on 6/9/16.
 */
public class JniStabilizationAttitudeComputation {
    public static void stabilizationAttitudeRun(boolean inFlight) {
        StabilizationAttitudeQuatInt.stabilization_attitude_run(inFlight);
    }
}

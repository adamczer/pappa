package juav.autopilot.telemetry;

/**
 * Created by adamczer on 11/6/16.
 */
public class PeriodicTelemetry {
    public short nb;                     ///< number of messages
    public TelemetryCbSlots[] cbs ; ///< array of callbacks defined through TELEMETRY_MSG

    public class TelemetryCbSlots {
        public short id;
        TelemetryCb[] slots;
    }
}

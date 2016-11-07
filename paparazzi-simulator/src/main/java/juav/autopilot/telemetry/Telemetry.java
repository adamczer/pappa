package juav.autopilot.telemetry;

import juav.simulator.tasks.PeriodicTask;

/**
 * Created by adamczer on 11/6/16.
 */
public class Telemetry {
    public static final int TELEMETRY_NB_CBS = 4;
    public static PeriodicTelemetry DefaultPeriodic = new PeriodicTelemetry();

    /** Register a telemetry callback function.
     * @param _pt periodic telemetry structure to register
     * @param _id message ID (use PPRZ_MSG_ID_<message_name> define)
     * @param _cb callback function, called according to telemetry mode and specified period
     * @return -1 on failure to register, index of callback otherwise
     */
    public static short register_periodic_telemetry(PeriodicTelemetry _pt, short _id, TelemetryCb _cb)
    {
        short i, j;
        // return if NULL is passed as periodic_telemetry
        if (_pt == null) { return -1; }
        // check if message with id _msgn has a periodic entery in telemetry file
        for (i = 0; i < _pt.nb; i++) {
            if (_pt.cbs[i].id == _id) {
                // msg found, register another callback if not all TELEMETRY_NB_CBS slots taken
                for (j = 0; j < TELEMETRY_NB_CBS; j++) {
                    if (_pt.cbs[i].slots[j] == null) {
                        _pt.cbs[i].slots[j] = _cb;
                        return j;
                    }
                }
                // message matched but no more empty slots available
                return -1;
            }
        }
        // message is not in telemetry file
        return -1;
    }

//    #if USE_PERIODIC_TELEMETRY_REPORT
//
//    #include "subsystems/datalink/downlink.h"

    /** Send an error report when trying to send message that as not been register
     * @param _process telemetry process id
     * @param _mode telemetry mode
     * @param _id id of the message in telemetry system (see var/<AC>/generated/periodic_telemetry.h)
     */
//  TODO not used yet  void periodic_telemetry_err_report(short _process, short _mode, short _id)
//    {
//        short process = _process;
//        short mode = _mode;
//        short id = _id;
//        DOWNLINK_SEND_PERIODIC_TELEMETRY_ERR(DefaultChannel, DefaultDevice, process, mode, id);
//    }
}

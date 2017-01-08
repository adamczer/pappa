package juav.autopilot.telemetry;

import ub.cse.juav.jni.tasks.NativeTasks;

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


    public static void registerPeriodicTelemetrySendGh() {
        NativeTasks.periodicTelemetrySendGh();
    }

    public static void registerPeriodicTelemetrySendHoverLoop() {
        NativeTasks.periodicTelemetrySendHoverLoop();
    }

    public static void registerPeriodicTelemetrySendHref() {
        NativeTasks.periodicTelemetrySendHref();
    }

    public static void registerPeriodicTelemetrySendTuneHover() {
        NativeTasks.periodicTelemetrySendTuneHover();
    }
// Autopilot
    public static void registerPeriodicTelemetrySendAutopilotVersion() {
        NativeTasks.periodicTelemetrySendAutopilotVersion();
    }

    public static void registerPeriodicTelemetrySendAlive() {
        NativeTasks.periodicTelemetrySendAlive();
    }

    public static void registerPeriodicTelemetrySendAttitude() {
        NativeTasks.periodicTelemetrySendAttitude();
    }

    public static void registerPeriodicTelemetrySendEnergy() {
        NativeTasks.periodicTelemetrySendEnergy();
    }

    public static void registerPeriodicTelemetrySendFp() {
        NativeTasks.periodicTelemetrySendFp();
    }

    public static void registerPeriodicTelemetrySendRotorcraftCmd() {
        NativeTasks.periodicTelemetrySendRotorcraftCmd();
    }

    public static void registerPeriodicTelemetrySendDlValue() {
        NativeTasks.periodicTelemetrySendDlValue();
    }

    public static void registerPeriodicTelemetrySendActuators() {
        NativeTasks.periodicTelemetrySendActuators();
    }

    public static void registerPeriodicTelemetrySendRc() {
        NativeTasks.periodicTelemetrySendRc();
    }

    public static void registerPeriodicTelemetrySendRotorcraftRc() {
        NativeTasks.periodicTelemetrySendRotorcraftRc();
    }
//Guidance V
    public static void registerPeriodicTelemetrySendVertLoop() {
        NativeTasks.periodicTelemetrySendVertLoop();
    }

    public static void registerPeriodicTelemetrySendTuneVert() {
        NativeTasks.periodicTelemetrySendTuneVert();
    }
//    Stabiliztaion Attitude Quat Int

    public static void registerPeriodicTelemetrySendAtt() {
        NativeTasks.periodicTelemetrySendAtt();
    }

    public static void registerPeriodicTelemetrySendAttRef() {
        NativeTasks.periodicTelemetrySendAttRef();
    }

    public static void registerPeriodicTelemetrySendAhrsRefQuat() {
        NativeTasks.periodicTelemetrySendAhrsRefQuat();
    }

// Stabilization Rate
    public static void registerPeriodicTelemetrySendRate() {
        NativeTasks.periodicTelemetrySendRate();
    }

}

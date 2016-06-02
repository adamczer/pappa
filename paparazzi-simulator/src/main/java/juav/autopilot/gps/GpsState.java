package juav.autopilot.gps;

import ub.juav.airborne.math.structs.geodetic.EcefCoor;
import ub.juav.airborne.math.structs.geodetic.LlaCoor;
import ub.juav.airborne.math.structs.geodetic.NedCoor;
import ub.juav.airborne.math.structs.geodetic.UtmCoor;

/**
 * Created by adamczer on 6/1/16.
 */
public class GpsState {
    EcefCoor<Integer> ecef_pos;    ///< position in ECEF in cm
    LlaCoor<Integer> lla_pos;      ///< position in LLA (lat,lon: deg*1e7; alt: mm over ellipsoid)
    UtmCoor<Integer> utm_pos;      ///< position in UTM (north,east: cm; alt: mm over ellipsoid)
    int hmsl;                  ///< height above mean sea level in mm
    EcefCoor<Integer> ecef_vel;    ///< speed ECEF in cm/s
    NedCoor<Integer> ned_vel;      ///< speed NED in cm/s
    int gspeed;               ///< norm of 2d ground speed in cm/s
    int speed_3d;             ///< norm of 3d speed in cm/s
    long course;                ///< GPS course over ground in rad*1e7, [0, 2*Pi]*1e7 (CW/north)
    long pacc;                 ///< position accuracy in cm
    long sacc;                 ///< speed accuracy in cm/s
    long cacc;                 ///< course accuracy in rad*1e7
    int pdop;                 ///< position dilution of precision scaled by 100
    short num_sv;                ///< number of sat in fix
    short fix;                   ///< status of fix
    int week;                 ///< GPS week
    long tow;                  ///< GPS time of week in ms

    short nb_channels;           ///< Number of scanned satellites
//    struct SVinfo svinfos[GPS_NB_CHANNELS]; ///< holds information from the Space Vehicles (Satellites)

    long last_3dfix_ticks;     ///< cpu time ticks at last valid 3D fix
    long last_3dfix_time;      ///< cpu time in sec at last valid 3D fix
    long last_msg_ticks;       ///< cpu time ticks at last received GPS message
    long last_msg_time;        ///< cpu time in sec at last received GPS message
    int reset;                ///< hotstart, warmstart, coldstart

}

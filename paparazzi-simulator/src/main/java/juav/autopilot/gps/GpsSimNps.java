package juav.autopilot.gps;

import juav.simulator.tasks.sensors.readings.GpsReading;
import ub.cse.juav.jni.gps.GpsNative;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;
import ub.juav.airborne.math.functions.geodetic.PprzGeodeticDouble;
import ub.juav.airborne.math.structs.geodetic.LtpDef;
import ub.juav.airborne.math.structs.geodetic.NedCoor;
import ub.juav.airborne.math.util.UtilityFunctions;

/**
 * Created by adamczer on 5/30/16.
 */
public class GpsSimNps implements IGpsNps{
    @Override
    public void gpsFeedValue(GpsReading reading) {
        GpsNative.gps_feed_value_week_juav(1794);
        GpsNative.gps_feed_value_tow_juav(PaparazziNpsWrapper.getNpsMainSimTime());
        GpsNative.gps_feed_value_ecef_pos_juav(reading.getEcef_pos().getX()*100.d,reading.getEcef_pos().getY()*100.d,reading.getEcef_pos().getZ()*100.d);
        GpsNative.gps_feed_value_ecef_vel_juav(reading.getEcef_vel().getX()*100.d,reading.getEcef_vel().getY()*100.d,reading.getEcef_vel().getZ()*100.d);
        GpsNative.gps_feed_value_lla_pos_juav(UtilityFunctions.DegOfRad(reading.getLla_pos().getLat())* 1e7,UtilityFunctions.DegOfRad(reading.getLla_pos().getLon())* 1e7,reading.getLla_pos().getAlt()*1000.d);
        GpsNative.gps_feed_value_hmsl_juav(reading.getHmsl()*1000.d);
////
////        /* calc NED speed from ECEF */
        LtpDef<Double> ref_ltp = LtpDef.LtpDefDouble();
//
        PprzGeodeticDouble.ltp_def_from_ecef_d(ref_ltp, reading.getEcef_pos());
        NedCoor<Double> ned_vel_d = new NedCoor<>();
        PprzGeodeticDouble.ned_of_ecef_vect_d(ned_vel_d, ref_ltp, reading.getEcef_vel());
        GpsNative.gps_feed_value_ned_speed(ned_vel_d.getX(),ned_vel_d.getY(),ned_vel_d.getZ());
        GpsNative.gps_feed_value_finalize_juav();
    }
}

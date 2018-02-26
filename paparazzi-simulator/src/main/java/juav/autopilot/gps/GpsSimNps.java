package juav.autopilot.gps;

import jive.StateTransitions;
import juav.gpsLogging.gpsLogging;
import juav.logging.JiveStateLog;
import juav.simulator.tasks.sensors.readings.GpsReading;
import ub.cse.juav.jni.gps.GpsNative;
import ub.cse.juav.jni.gps.GpsNativeWrapper;
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
    	JiveStateLog.setGpsSimNps("Gps_SimNps_gpsFeedValue");
        GpsNativeWrapper.gps_feed_value_week_juav(1794);
        GpsNativeWrapper.gps_feed_value_tow_juav(PaparazziNpsWrapper.getNpsMainSimTime());
        GpsNativeWrapper.gps_feed_value_ecef_pos_juav(reading.getEcef_pos().getX()*100.d,reading.getEcef_pos().getY()*100.d,reading.getEcef_pos().getZ()*100.d);
        GpsNativeWrapper.gps_feed_value_ecef_vel_juav(reading.getEcef_vel().getX()*100.d,reading.getEcef_vel().getY()*100.d,reading.getEcef_vel().getZ()*100.d);

        double lat = UtilityFunctions.DegOfRad(reading.getLla_pos().getLat())* 1e7;
        double lon = UtilityFunctions.DegOfRad(reading.getLla_pos().getLon())* 1e7;
        double alt = reading.getLla_pos().getAlt()*1000.d;
        
      
      //Oct StateTransitions.instance.add_points(lat,lon,alt);
       // JiveStateLog.setPosition(lat, lon, alt);
        gpsLogging.setPosition(lat, lon, alt);
        
        
        GpsNativeWrapper.gps_feed_value_lla_pos_juav(lat,lon,alt);
        GpsNativeWrapper.gps_feed_value_hmsl_juav(reading.getHmsl()*1000.d);
////
////        /* calc NED speed from ECEF */
        LtpDef<Double> ref_ltp = LtpDef.LtpDefDouble();
//
        PprzGeodeticDouble.ltp_def_from_ecef_d(ref_ltp, reading.getEcef_pos());
        NedCoor<Double> ned_vel_d = new NedCoor<>();
        PprzGeodeticDouble.ned_of_ecef_vect_d(ned_vel_d, ref_ltp, reading.getEcef_vel());
        GpsNativeWrapper.gps_feed_value_ned_speed(ned_vel_d.getX(),ned_vel_d.getY(),ned_vel_d.getZ());
        GpsNativeWrapper.gps_feed_value_finalize_juav();
    }
}

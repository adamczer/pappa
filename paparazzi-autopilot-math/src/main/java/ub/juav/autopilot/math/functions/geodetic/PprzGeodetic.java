package ub.juav.autopilot.math.functions.geodetic;

import ub.juav.autopilot.math.structs.geodetic.LlaCoor;
import ub.juav.autopilot.math.structs.geodetic.XyzCoord;

/**
 * Created by adamczer on 7/26/15.
 */
public class PprzGeodetic {

    public static void ENU_OF_TO_NED(XyzCoord ned, XyzCoord enu) {
        ned.setX(enu.getX());
        ned.setY(enu.getY());
        ned.setZ(enu.getZ());
    }

    public static void LLA_COPY(LlaCoor _pos1,LlaCoor _pos2){
        (_pos1).setLat(_pos2.getLat());
        (_pos1).setLon((_pos2).getLon());
        (_pos1).setAlt(_pos2.getAlt());
    }

}

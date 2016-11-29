package juav.autopilot.navigation;

import ub.juav.airborne.math.structs.geodetic.EnuCoor;

/**
 * Created by adamczer on 11/28/16.
 */
public class Navigation {

    public static final int HORIZONTAL_MODE_WAYPOINT = 0;
    public static final int  HORIZONTAL_MODE_ROUTE =    1;
    public static final int  HORIZONTAL_MODE_CIRCLE =   2;
    public static final int  HORIZONTAL_MODE_ATTITUDE = 3;

    public static short horizontal_mode;
    public static int nav_roll;
    public static int nav_pitch;
    public static int nav_heading;


    public static EnuCoor<Integer> navigation_carrot;


}

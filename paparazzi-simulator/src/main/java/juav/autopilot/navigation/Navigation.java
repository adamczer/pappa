package juav.autopilot.navigation;

import juav.simulator.tasks.runonceevery.RunOnceEvery;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.structs.geodetic.EnuCoor;

/**
 * Created by adamczer on 11/28/16.
 */
public class Navigation {
    public static final int VERTICAL_MODE_MANUAL  =    0;
    public static final int VERTICAL_MODE_CLIMB  =     1;
    public static final int VERTICAL_MODE_ALT    =     2;

    public static final int HORIZONTAL_MODE_WAYPOINT = 0;
    public static final int  HORIZONTAL_MODE_ROUTE =    1;
    public static final int  HORIZONTAL_MODE_CIRCLE =   2;
    public static final int  HORIZONTAL_MODE_ATTITUDE = 3;

    public static final int NAV_FREQ = 16;

    public static float dist2_to_home;
    public static boolean too_far_from_home = false;
    public static float failsafe_mode_dist2; ///< maximum squared distance to home wp before going to failsafe mode

    public static short horizontal_mode;
    public static int nav_roll;
    public static int nav_pitch;
    public static int nav_heading;
    public static int vertical_mode;

    public static int nav_climb, nav_altitude, nav_flight_altitude;

    public static int nav_throttle;


    public static EnuCoor<Integer> navigation_carrot;

    public static RunOnceEvery nav_periodic_task = new RunOnceEvery() {
        @Override
        protected void work() {
            NativeTasks.navPeriodicTask();
        }
    };

    public static RunOnceEvery nav_home = new RunOnceEvery() {
        @Override
        protected void work() {
            NativeTasks.navHome();
        }
    };

    public static RunOnceEvery compute_dist2_to_home = new RunOnceEvery() {
        @Override
        protected void work() {
            NativeTasks.computeDist2ToHome();
        }
    };


}

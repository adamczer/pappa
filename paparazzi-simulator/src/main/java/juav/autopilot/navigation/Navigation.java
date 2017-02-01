package juav.autopilot.navigation;

import juav.simulator.tasks.runonceevery.RunOnceEvery;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.juav.airborne.math.structs.geodetic.EnuCoor;

/**
 * Created by adamczer on 11/28/16.
 */
public class Navigation {
    public static final float MAX_DIST_FROM_HOME = 150;
    public static final float failsafe_mode_dist2 = MAX_DIST_FROM_HOME * MAX_DIST_FROM_HOME;
    public static final int VERTICAL_MODE_MANUAL = 0;
    public static final int VERTICAL_MODE_CLIMB = 1;
    public static final int VERTICAL_MODE_ALT = 2;

    public static final int HORIZONTAL_MODE_WAYPOINT = 0;
    public static final int HORIZONTAL_MODE_ROUTE = 1;
    public static final int HORIZONTAL_MODE_CIRCLE = 2;
    public static final int HORIZONTAL_MODE_ATTITUDE = 3;

    public static final int NAV_FREQ = 16;

    public static float getDist2ToHome() {
        return NativeTasks.navigationGetDist2ToHome();
    }

    public static boolean getTooFarFromHome() {
        return NativeTasks.navigationGetTooFarFromHome();
    }

    public static short getHorizantalMode() {
        return NativeTasks.navigationGetHorizontalMode();
    }

    public static int getNavRoll() {
        return NativeTasks.navigationGetNavRoll();
    }

    public static int getNavPitch() {
        return NativeTasks.navigationGetNavPitch();
    }

    public static int getNavHeading() {
        return NativeTasks.navigationGetNavHeading();
    }

    public static void setNavHeading(int newHeading) {
        NativeTasks.navigationSetNavHeading(newHeading);
    }

    public static int getNavVerticleMode() {
        return NativeTasks.navigationGetNavVerticleMode();
    }

    public static int getNavClimb() {
        return NativeTasks.navigationGetNavClimb();
    }

    public static int getNavFlightAltitude() {
        return NativeTasks.navigationGetNavFlightAltitude();
    }

    public static int getNavThrottle() {
        return NativeTasks.navigationGetNavThrottle();
    }

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

    public static void nav_init() {
        NativeTasks.navInit();
    }

    public static EnuCoor<Integer> getNavigationCarrot() {
        EnuCoor<Integer> ret = EnuCoor.newInteger();
        ret.setX(NativeTasks.getNavigationCarrotX());
        ret.setY(NativeTasks.getNavigationCarrotY());
        ret.setZ(NativeTasks.getNavigationCarrotZ());
//        System.out.println("NavigationCarrot x,y,z = "+ret.getX()+","+ret.getY()+","+ret.getZ());
        return ret;
    }


}

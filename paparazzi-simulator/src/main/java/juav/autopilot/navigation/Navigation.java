package juav.autopilot.navigation;
import jive.logging.StateTransitions;
import juav.logging.JiveStateLog;
import juav.simulator.tasks.runonceevery.RunOnceEvery;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;
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
    
    public static int counter1 = 0, counter3 = 0, counter4 = 0,counter5 = 0;
    
    public static final int NAV_FREQ = 16;
    public static int counter2 = 0;
    public static String navigationStateLog;

    public static float getDist2ToHome() {
    	// StateTransitions.instance.add_transition(new String[]{"nav_get_dist_to_home"});
    	JiveStateLog.setnavigationStateLog("navi_get_dist_to_home");
        return NativeTasksWrapper.navigationGetDist2ToHome();
    }

    public static boolean getTooFarFromHome() {
    	//StateTransitions.instance.add_transition(new String[]{"nav_too_far_from_home"});
    	JiveStateLog.setnavigationStateLog("navi_too_far_from_home");
        return NativeTasksWrapper.navigationGetTooFarFromHome();
    }

    public static short getHorizantalMode() {
    	// StateTransitions.instance.add_transition(new String[]{"nav_get_HMode"});
    	JiveStateLog.setnavigationStateLog("get_horizontal_mode");
        return NativeTasksWrapper.navigationGetHorizontalMode();
    }

    public static int getNavRoll() {
    	// StateTransitions.instance.add_transition(new String[]{"nav_getNavRoll"});
    	JiveStateLog.setnavigationStateLog("navi_getnavRoll");
        return NativeTasksWrapper.navigationGetNavRoll();
    }

    public static int getNavPitch() {
    	// StateTransitions.instance.add_transition(new String[]{"nav_getNavPitch"});
    	JiveStateLog.setnavigationStateLog("navi_getNavPitch");
        return NativeTasksWrapper.navigationGetNavPitch();
    }

    public static int getNavHeading() {
    	// StateTransitions.instance.add_transition(new String[]{"nav_getNavHeading"});
    	JiveStateLog.setnavigationStateLog("navi_getNavHeading");
        return NativeTasksWrapper.navigationGetNavHeading();
    }

    public static void setNavHeading(int newHeading) {
    	// StateTransitions.instance.add_transition(new String[]{"nav_setNavHeading"});
    	JiveStateLog.setnavigationStateLog("navi_setNavHeading");
        NativeTasksWrapper.navigationSetNavHeading(newHeading);
    }

    public static int getNavVerticleMode() {
    	// StateTransitions.instance.add_transition(new String[]{"nav_getNav_VMode"});
    	StateTransitions.instance.add_iteration("getNav_VMode");
    	JiveStateLog.setnavigationStateLog("navi_getNav_Verticle_mode");
        return NativeTasksWrapper.navigationGetNavVerticleMode();
    }

    public static int getNavClimb() {
    	// StateTransitions.instance.add_transition(new String[]{"nav_getNavClimb"});
    	StateTransitions.instance.add_iteration("Nav_getClimb");
    	JiveStateLog.setnavigationStateLog("navi_getNavClimb");
        return NativeTasksWrapper.navigationGetNavClimb();
    }

    public static int getNavFlightAltitude() {
    	// StateTransitions.instance.add_transition(new String[]{"nav_get_nav_flight_altitude"});
    	JiveStateLog.setnavigationStateLog("navi_get_Nav_Flight_Altitude");
    	StateTransitions.instance.add_iteration("Nav_Flight_Altitude");
        return NativeTasksWrapper.navigationGetNavFlightAltitude();
    }

    public static int getNavThrottle() {
    	 //StateTransitions.instance.add_transition(new String[]{"nav_getThrottle"});
    	StateTransitions.instance.add_iteration("Nav_getThrottle");
    	JiveStateLog.setnavigationStateLog("navi_getNavThrottle");
        return NativeTasksWrapper.navigationGetNavThrottle();
    }

    public static RunOnceEvery nav_periodic_task = new RunOnceEvery() {
        @Override
        protected void work() {
        	JiveStateLog.setnavigationStateLog("nav_periodic_task");
        	 //StateTransitions.instance.add_transition(new String[]{"run navi_periodic"});
        	StateTransitions.instance.add_iteration("Navigation");
            NativeTasksWrapper.navPeriodicTask();
        }
    };

    public static RunOnceEvery nav_home = new RunOnceEvery() {
        @Override
        protected void work() {
        	// StateTransitions.instance.add_transition(new String[]{"nav_home"});
        	JiveStateLog.setnavigationStateLog("nav_home");
            NativeTasksWrapper.navHome();
        }
    };

    public static RunOnceEvery compute_dist2_to_home = new RunOnceEvery() {
        @Override
        protected void work() {
        //	 StateTransitions.instance.add_transition(new String[]{"navi_compute_dist_to_home"});
        	JiveStateLog.setnavigationStateLog("navi_compute_dist_to_home");
            NativeTasksWrapper.computeDist2ToHome();
        }
    };

    public static void nav_init() {
    	JiveStateLog.setnavigationStateLog("navi_init");
        NativeTasksWrapper.navInit();
    }

    public static EnuCoor<Integer> getNavigationCarrot() {
    	JiveStateLog.setnavigationStateLog("navi_get_navi_carrot_x_y_z");
    	 //StateTransitions.instance.add_transition(new String[]{"navi_get_x_y_z"});
        EnuCoor<Integer> ret = EnuCoor.newInteger();
        ret.setX(NativeTasksWrapper.getNavigationCarrotX());
        ret.setY(NativeTasksWrapper.getNavigationCarrotY());
        ret.setZ(NativeTasksWrapper.getNavigationCarrotZ());
//        System.out.println("NavigationCarrot x,y,z = "+ret.getX()+","+ret.getY()+","+ret.getZ());
        return ret;
    }


}

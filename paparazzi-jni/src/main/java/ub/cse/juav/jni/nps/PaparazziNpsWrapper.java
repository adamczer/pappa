package ub.cse.juav.jni.nps;

import ub.cse.juav.fijiorjni.NativeSwitch;


/**
 * Created by adamczer on 2/19/17.
 */
public class PaparazziNpsWrapper {

    public static void npsInit() {
        if(NativeSwitch.isFiji()) {
            PaparazziNpsFiji.npsInit();
        } else {
            PaparazziNps.npsInit();
        }
    }

    public static void npsMainPeriodicJuavNative() {
        if(NativeSwitch.isFiji()) {
            PaparazziNpsFiji.npsMainPeriodicJuavNative();
        } else {
            PaparazziNps.npsMainPeriodicJuavNative();
        }
    }

    public static void npsMainDisplay() {
        if(NativeSwitch.isFiji()) {
            PaparazziNpsFiji.npsMainDisplay();
        } else {
            PaparazziNps.npsMainDisplay();
        }
    }
    public static void setNpsMainSimTime(double newSimTime) {
        if(NativeSwitch.isFiji()) {
            PaparazziNpsFiji.setNpsMainSimTime(newSimTime);
        } else {
            PaparazziNps.setNpsMainSimTime(newSimTime);
        }
    }

    public static double getNpsMainSimTime() {
        if(NativeSwitch.isFiji()) {
           return PaparazziNpsFiji.getNpsMainSimTime();
        } else {
            return PaparazziNps.getNpsMainSimTime();
        }
    }
    public static double getNpsMainHostTimeElapsed() {
        if(NativeSwitch.isFiji()) {
           return PaparazziNpsFiji.getNpsMainHostTimeElapsed();
        } else {
           return PaparazziNps.getNpsMainHostTimeElapsed();
        }
    }

    public static double getNpsMainSimDt() {
        if(NativeSwitch.isFiji()) {
            return PaparazziNpsFiji.getNpsMainSimDt();
        } else {
            return PaparazziNps.getNpsMainSimDt();
        }
    }
    public static double getNpsMainRealInitialTime() {
        if(NativeSwitch.isFiji()) {
            return PaparazziNpsFiji.getNpsMainRealInitialTime();
        } else {
            return PaparazziNps.getNpsMainRealInitialTime();
        }

    }

    public static double getHostTimeNow() {
        if(NativeSwitch.isFiji()) {
            return PaparazziNpsFiji.getHostTimeNow();
        } else {
            return PaparazziNps.getHostTimeNow();
        }
    }

    public static double getNpsMainDisplayTime() {
        if(NativeSwitch.isFiji()) {
            return PaparazziNpsFiji.getNpsMainDisplayTime();
        } else {
            return PaparazziNps.getNpsMainDisplayTime();
        }
    }

    public static double getNpsMainDisplayDt() {
        if(NativeSwitch.isFiji()) {
            return PaparazziNpsFiji.getNpsMainDisplayDt();
        } else {
            return PaparazziNps.getNpsMainDisplayDt();
        }
    }

    public static void setNpsMainDisplayTime(double newDisplayTime) {
        if(NativeSwitch.isFiji()) {
            PaparazziNpsFiji.setNpsMainDisplayTime(newDisplayTime);
        } else {
            PaparazziNps.setNpsMainDisplayTime(newDisplayTime);
        }
    }


    // mainEvent function located within the rotorcraft`s main.c

    public static void mainEvent() {
        if(NativeSwitch.isFiji()) {
            PaparazziNpsFiji.mainEvent();
        } else {
            PaparazziNps.mainEvent();
        }
    } //full main event

    public static void mainEventPrior() {
        if(NativeSwitch.isFiji()) {
            PaparazziNpsFiji.mainEventPrior();
        } else {
            PaparazziNps.mainEventPrior();
        }
    } //beginning main event up to autopilot rc

    public static void mainEventPost() {
        if(NativeSwitch.isFiji()) {
            PaparazziNpsFiji.mainEventPost();
        } else {
            PaparazziNps.mainEventPost();
        }
    } //main event  after autopilot rc

}

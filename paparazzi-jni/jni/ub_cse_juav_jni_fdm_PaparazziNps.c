#include <jni.h>
#include <stdio.h>
#include "ub_cse_juav_jni_nps_PaparazziNps.h"
#include <nps_main.h>
//#include <main.h>
//
#include <nps_fdm.h>

#define SIM_DT (1./1000)
#define DISPLAY_DT (1./30.)


JNIEXPORT void JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_npsInit
  (JNIEnv *env, jclass thisClass) {
    nps_main_parse_options_juav();
  	nps_main_init_juav();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_npsMainPeriodicJuavNative
  (JNIEnv *env, jclass thisClass) {
    nps_main_periodic_juav_native();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_npsMainDisplay
  (JNIEnv *env, jclass thisClass) {
    nps_main_display_juav();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_setNpsMainSimTime
  (JNIEnv *env, jclass thisClass, jdouble dbl) {
  set_nps_sim_time_juav(dbl);
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_getNpsMainSimTime
  (JNIEnv *env, jclass thisClass) {
    return get_nps_sim_time_juav();
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_getNpsMainHostTimeElapsed
  (JNIEnv *env, jclass thisClass) {
  return get_nps_host_time_elapsed_juav();
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_getNpsMainSimDt
  (JNIEnv *env, jclass thisClass) {
   return SIM_DT;
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_getNpsMainRealInitialTime
  (JNIEnv *env, jclass thisClass) {
    return get_nps_main_real_initial_time_juav();
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_getHostTimeNow
  (JNIEnv *env, jclass thisClass) {
    return get_nps_host_time_now_juav();
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_getNpsMainDisplayTime
  (JNIEnv *env, jclass thisClass) {
   return get_nps_display_time_juav();
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_getNpsMainDisplayDt
  (JNIEnv *env, jclass thisClass) {
    return DISPLAY_DT;
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_setNpsMainDisplayTime
  (JNIEnv *env, jclass thisClass, jdouble dub) {
    set_nps_display_time_juav(dub);
  }

  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_npsMainRunSimStepTest
    (JNIEnv *env, jclass thisClass) {
     nps_main_run_sim_step_juav();
    }

    JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_fakeNpsPeriodic
      (JNIEnv *env, jclass thisClass) {
        return fake_nps_main_periodic();
      }

  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_nps_PaparazziNps_mainEvent
    (JNIEnv *env, jclass thisClass) {
    	main_event_juav();
    }


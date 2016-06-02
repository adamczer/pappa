#include <jni.h>
#include <stdio.h>
#include "ub_cse_juav_jni_gps_GpsNative.h"
#include <nps_sensors.h>

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1value_1week_1juav
  (JNIEnv *env, jclass thisClass, jint week) {
	gps_feed_value_week_juav(week);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1value_1tow_1juav
  (JNIEnv *env, jclass thisClass, jdouble time) {
  	gps_feed_value_tow_juav(time);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1value_1ecef_1pos_1juav
  (JNIEnv *env, jclass thisClass, jdouble x, jdouble y, jdouble z) {
	gps_feed_value_ecef_pos_juav(x,y,z);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1value_1ecef_1vel_1juav
  (JNIEnv *env, jclass thisClass, jdouble x, jdouble y, jdouble z) {
  	gps_feed_value_ecef_vel_juav(x,y,z);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1value_1lla_1pos_1juav
  (JNIEnv *env, jclass thisClass, jdouble lat, jdouble lon, jdouble alt){
  	gps_feed_value_lla_pos_juav(lat,lon,alt);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1value_1hmsl_1juav
  (JNIEnv *env, jclass thisClass, jdouble hmsl) {
	gps_feed_value_hmsl_juav(hmsl);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1value_1ned_1speed
  (JNIEnv *env, jclass thisClass, jdouble x, jdouble y, jdouble z) {
	gps_feed_value_ned_speed(x,y,z);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1value_1finalize_1juav
  (JNIEnv *env, jclass thisClass) {
	gps_feed_value_finalize_juav();
  }


JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1latency_1speed_1juav
  (JNIEnv *env, jclass thisClass, jdouble time, jdouble x, jdouble y, jdouble z) {
	UpdateSensorLatencySpeedJuav(time, x, y, z);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1latency_1pos_1juav
  (JNIEnv *env, jclass thisClass, jdouble time, jdouble x, jdouble y, jdouble z) {
    UpdateSensorLatencyPosJuav( time, x, y, z);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1latency_1lla_1juav
  (JNIEnv *env, jclass thisClass, jdouble time, jdouble lat, jdouble lon, jdouble alt) {
    UpdateSensorLatencyLlaJuav( time, lat, lon, alt);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_gps_GpsNative_gps_1feed_1latency_1hmsl_1juav
  (JNIEnv *env, jclass thisClass, jdouble time, jdouble hmsl){
	UpdateSensorLatency_Single_Hmsl(time, hmsl);
  }


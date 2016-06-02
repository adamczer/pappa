#include <jni.h>
#include <stdio.h>
#include "ub_cse_juav_jni_imu_ImuNative.h"
#include <imu_nps.h>

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_imu_ImuNative_imuFeedGyro
  (JNIEnv *env, jclass thisClass, jdouble x, jdouble y, jdouble z) {
	imu_feed_gyro_juav(x,y,z);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_imu_ImuNative_imuFeedAccel
  (JNIEnv *env, jclass thisClass, jdouble x, jdouble y, jdouble z) {
	imu_feed_accel_juav(x,y,z);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_imu_ImuNative_imuFeedMag
  (JNIEnv *env, jclass thisClass, jdouble x, jdouble y, jdouble z) {
	imu_feed_mag_juav(x,y,z);
  }
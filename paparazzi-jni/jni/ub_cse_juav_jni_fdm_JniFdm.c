#include <jni.h>
#include <stdio.h>
#include "ub_cse_juav_jni_fdm_JniFdm.h"
#include <nps_main.h>
  
  ///////////////////////////////////////////
  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmBodyInertialRotVelP
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_body_inetrial_rot_vel_p_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmBodyInertialRotVelQ
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_body_inetrial_rot_vel_q_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmBodyInertialRotVelR
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_body_inetrial_rot_vel_r_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmBodyAccelX
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_body_accel_x_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmBodyAccelY
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_body_accel_y_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmBodyAccelZ
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_body_accel_z_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmBodyToImu
    (JNIEnv *env, jclass thisClass , jint row, jint col) {
    	return get_fdm_body_to_imu_juav(row,col);
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmLtpToBodyQuatQi
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ltp_to_body_quat_qi_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmLtpToBodyQuatQx
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ltp_to_body_quat_qx_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmLtpToBodyQuatQy
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ltp_to_body_quat_qy_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmLtpToBodyQuatQz
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ltp_to_body_quat_qz_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmLtpHX
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ltp_h_x_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmLtpHY
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ltp_h_y_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmLtpHZ
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ltp_h_z_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getHmsl
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_hmsl_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmEcefEcefVelX
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ecef_ecef_vel_x_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmEcefEcefVelY
    (JNIEnv *env, jclass thisClass) {
		get_fdm_ecef_ecef_vel_y_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmEcefEcefVelZ
    (JNIEnv *env, jclass thisClass) {
		get_fdm_ecef_ecef_vel_z_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmEcefPosX
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ecef_pos_x_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmEcefPosY
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ecef_pos_y_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmEcefPosZ
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_ecef_pos_z_juav();
    }

  JNIEXPORT jdouble JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_getFdmAgl
    (JNIEnv *env, jclass thisClass) {
		return get_fdm_agl_juav();
    }




#include <jni.h>
#include <stdio.h>
#include "ub_cse_juav_jni_tasks_NativeTasks.h"
#include <nps_main.h>
#include <nps_fdm.h>
#include <nps_autopilot.h>
#include <guidance.h>
#include <main.h>
#include <autopilot.h>
#include <stabilization_attitude_quat_int.h>
//#include <radio_control.h>

#define SIM_DT (1./1000)

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_atmosphereUpdate
  (JNIEnv *env, jclass thisClass) {
    nps_atmosphere_update(SIM_DT);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_autoPilotRunSystimeStep
  (JNIEnv *env, jclass thisClass) {
    nps_autopilot_run_systime_step();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_fdmRunStep
  (JNIEnv *env, jclass thisClass) {
    nps_fdm_run_step_juav();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_sensorsRunStep
  (JNIEnv *env, jclass thisClass, jdouble doub) {
nps_sensors_run_step(doub);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_autoPilotRunStep
  (JNIEnv *env, jclass thisClass, jdouble doub) {
nps_autopilot_run_step(doub);
  }

  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepRadio
    (JNIEnv *env, jclass thisClass,jdouble simTime) {
 		nps_autopilot_run_step_radio_juav(simTime);
    }

    JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepOverwriteIns
      (JNIEnv *env, jclass thisClass) {
      	sim_overwrite_ins_juav();
      }

    JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepOverwriteAhrs
      (JNIEnv *env, jclass thisClass) {
		sim_overwrite_ahrs_juav();
      }

      JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepHandelPeriodicTasks
        (JNIEnv *env, jclass thisClass) {
        handle_periodic_tasks_juav();
        }

      JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands
        (JNIEnv *env, jclass thisClass) {
		convert_motor_mixing_commands_to_autopilot_commands();
        }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsElectricalRunStep
  (JNIEnv *env, jclass thisClass, jdouble time) {
  	nps_electrical_run_step_juav(time);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_sendBarometricReading
  (JNIEnv *env, jclass thisClass, jfloat pressure) {
  nps_send_baro_reading_juav(pressure);
  }

  //////////////////////////////////////////// Sensor skip
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorInitGyro
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_gyro_init_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorInitAccel
   * Signature: (D)V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorInitAccel
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_accel_init_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorInitMag
   * Signature: (D)V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorInitMag
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_mag_init_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorInitBaro
   * Signature: (D)V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorInitBaro
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_baro_init_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorInitGps
   * Signature: (D)V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorInitGps
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_gps_init_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFdmCopyGyro
   * Signature: (D)V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFdmCopyGyro
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_gyro_run_step_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFdmCopyAccel
   * Signature: (D)V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFdmCopyAccel
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_accel_run_step_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFdmCopyMag
   * Signature: (D)V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFdmCopyMag
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_mag_run_step_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFdmCopyBaro
   * Signature: (D)V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFdmCopyBaro
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_baro_run_step_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFdmCopyGps
   * Signature: (D)V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFdmCopyGps
    (JNIEnv *env, jclass thisClass, jdouble time) {
		nps_sensor_gps_run_step_juav(time);
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFeedStepGyro
   * Signature: ()V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFeedStepGyro
    (JNIEnv *env, jclass thisClass) {
		npsGyroFeedStepJuav();
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFeedStepAccel
   * Signature: ()V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFeedStepAccel
    (JNIEnv *env, jclass thisClass) {
		npsAccelFeedStepJuav();
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFeedStepMag
   * Signature: ()V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFeedStepMag
    (JNIEnv *env, jclass thisClass) {
		npsMagFeedStepJuav();
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFeedStepBaro
   * Signature: ()V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFeedStepBaro
    (JNIEnv *env, jclass thisClass) {
		npsBaroFeedStepJuav();
    }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    npsSensorFeedStepGps
   * Signature: ()V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsSensorFeedStepGps
    (JNIEnv *env, jclass thisClass) {
		npsGpsFeedStepJuav();
    }


/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    mainPeriodicJuavAutopilotPrior
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_mainPeriodicJuavAutopilotPrior
  (JNIEnv *env, jclass thisClass) {
  	main_periodic_juav_autopilot_prior();
  }

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    sysTimeCheckAndAckTimerMainPeriodicJuav
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_sysTimeCheckAndAckTimerMainPeriodicJuav
  (JNIEnv *env, jclass thisClass) {
  	return sys_time_check_and_ack_timer_main_periodic_juav();
  }

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    handlePeriodicTasksFollowingMainPeriodicJuav
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_handlePeriodicTasksFollowingMainPeriodicJuav
  (JNIEnv *env, jclass thisClass) {
  	handle_periodic_tasks_following_main_periodic_juav();
  }

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    autopilotPeriodicPriorJuav
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_autopilotPeriodicPriorJuav
  (JNIEnv *env, jclass thisClass) {
  	autopilot_periodic_prior_juav();
  }

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    isAutopilotModeApModeKillJuav
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_isAutopilotModeApModeKillJuav
  (JNIEnv *env, jclass thisClass) {
  	return is_autopilot_mode_ap_mode_kill_juav();
  }

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    autopilotPeriodicPostJuav
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_autopilotPeriodicPostJuav
  (JNIEnv *env, jclass thisClass) {
  	autopilot_periodic_post_juav();
  }

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    guidanceHRunJuav
 * Signature: (Z)V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_guidanceHRunJuav
  (JNIEnv *env, jclass thisClass, jboolean in_flight) {
  	guidance_h_run_juav(in_flight);
  }


/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    getAutopilotInFlightJuav
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAutopilotInFlightJuav
  (JNIEnv *env, jclass thisClass) {
  	return get_autopilot_in_flight_juav();
  }


/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    runStabilizationAttitudeRunJuav
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_runStabilizationAttitudeRunJuav
  (JNIEnv *env, jclass thisClass) {
  	return run_stabilization_attitude_run_juav();
  }

  /*
   * Class:     ub_cse_juav_jni_tasks_NativeTasks
   * Method:    guidanceHRunNativeTestJuav
   * Signature: ()V
   */
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_guidanceHRunNativeTestJuav
    (JNIEnv *env, jclass thisClass, jboolean in_flight) {
    	guidance_h_run_native_test_juav(in_flight);
    }


/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    mainPeriodicJuavAutopilotPost
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_mainPeriodicJuavAutopilotPost
  (JNIEnv *env, jclass thisClass) {
  	main_periodic_juav_autopilot_post();
  }

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    mainPeriodicJuavTest
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_mainPeriodicJuavTest
  (JNIEnv *env, jclass thisClass) {
    main_periodic_juav_test();
  }


JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_attitudeRefQuatIntUpdateJuav
  (JNIEnv *env, jclass thisClass, jfloat time) {
  	attitude_ref_quat_int_update_juav(time);
  }


JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getStabilizationAttSumErrQuatQi
  (JNIEnv *env, jclass thisClass) {
  	return get_stabilization_att_sum_err_quat_i_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getStabilizationAttSumErrQuatQx
  (JNIEnv *env, jclass thisClass) {
  	return get_stabilization_att_sum_err_quat_x_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getStabilizationAttSumErrQuatQy
  (JNIEnv *env, jclass thisClass) {
  	return get_stabilization_att_sum_err_quat_y_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getStabilizationAttSumErrQuatQz
  (JNIEnv *env, jclass thisClass) {
  	return get_stabilization_att_sum_err_quat_z_juav();
  }

  // Attitude ref

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefQuatQi
  (JNIEnv *env, jclass thisClass) {
	return get_att_ref_quat_i_quat_qi_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefQuatQx
  (JNIEnv *env, jclass thisClass) {
	return get_att_ref_quat_i_quat_qx_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefQuatQy
  (JNIEnv *env, jclass thisClass) {
	return get_att_ref_quat_i_quat_qy_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefQuatQz
  (JNIEnv *env, jclass thisClass) {
	return get_att_ref_quat_i_quat_qz_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefRateP
  (JNIEnv *env, jclass thisClass) {
	return get_att_ref_quat_i_rate_p_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefRateQ
  (JNIEnv *env, jclass thisClass) {
	return get_att_ref_quat_i_rate_q_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefRateR
  (JNIEnv *env, jclass thisClass) {
	return get_att_ref_quat_i_rate_r_juav();
  }

  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefAccelP
    (JNIEnv *env, jclass thisClass) {
    	return get_att_ref_quat_i_accel_p_juav();
    }
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefAccelQ
    (JNIEnv *env, jclass thisClass) {
    	return get_att_ref_quat_i_accel_q_juav();
    }
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeRefAccelR
    (JNIEnv *env, jclass thisClass) {
    	return get_att_ref_quat_i_accel_r_juav();
    }


JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainPX
  (JNIEnv *env, jclass thisClass) {
  	return get_stabilization_gains_p_x_juav();
  }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainPY
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_p_y_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainPZ
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_p_z_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainDX
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_d_x_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainDY
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_d_y_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainDZ
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_d_z_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainDdX
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_dd_x_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainDdY
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_dd_y_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainDdZ
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_dd_z_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainIX
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_i_x_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainIY
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_i_y_juav();
    }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAttitudeGainIZ
  (JNIEnv *env, jclass thisClass) {
	return get_stabilization_gains_i_z_juav();
    }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setStabilizationAttSumErrQuatQi
  (JNIEnv *env, jclass thisClass, jint qi) {
  set_stabilization_att_sum_err_quat_i_juav(qi);
  return 0;
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setStabilizationAttSumErrQuatQx
  (JNIEnv *env, jclass thisClass, jint qx) {
  set_stabilization_att_sum_err_quat_x_juav(qx);
  return 0;
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setStabilizationAttSumErrQuatQy
  (JNIEnv *env, jclass thisClass, jint qy) {
  set_stabilization_att_sum_err_quat_y_juav(qy);
  return 0;
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setStabilizationAttSumErrQuatQz
  (JNIEnv *env, jclass thisClass, jint qz) {
  set_stabilization_att_sum_err_quat_z_juav(qz);
  return 0;
  }


JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIQuatQi
  (JNIEnv *env, jclass thisClass, jint qi) {
  set_att_ref_quat_i_quat_qi_juav(qi);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIQuatQx
  (JNIEnv *env, jclass thisClass, jint qx) {
  set_att_ref_quat_i_quat_qx_juav(qx);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIQuatQy
  (JNIEnv *env, jclass thisClass, jint qy) {
  set_att_ref_quat_i_quat_qy_juav(qy);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIQuatQz
  (JNIEnv *env, jclass thisClass, jint qz) {
  set_att_ref_quat_i_quat_qz_juav(qz);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIRateP
  (JNIEnv *env, jclass thisClass, jint p){
  set_att_ref_quat_i_rate_p_juav(p);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIRateQ
  (JNIEnv *env, jclass thisClass, jint q){
  set_att_ref_quat_i_rate_q_juav(q);
  }
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIRateR
  (JNIEnv *env, jclass thisClass, jint r){
  set_att_ref_quat_i_rate_r_juav(r);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIAccelP
  (JNIEnv *env, jclass thisClass, jint p) {
  	set_att_ref_quat_i_accel_p_juav(p);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIAccelQ
  (JNIEnv *env, jclass thisClass, jint q) {
	set_att_ref_quat_i_accel_q_juav(q);
  }
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAttRefQuatIAccelR
  (JNIEnv *env, jclass thisClass, jint r) {
	set_att_ref_quat_i_accel_r_juav(r);
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyQuatIQi
  (JNIEnv *env, jclass thisClass) {
  return get_stateGetNedToBodyQuat_i_Qi_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyQuatIQx
  (JNIEnv *env, jclass thisClass) {
  return get_stateGetNedToBodyQuat_i_Qx_juav();
  }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyQuatIQy
  (JNIEnv *env, jclass thisClass) {
  return get_stateGetNedToBodyQuat_i_Qy_juav();
  }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyQuatIQz
  (JNIEnv *env, jclass thisClass) {
  return get_stateGetNedToBodyQuat_i_Qz_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetBodyRatesIP
  (JNIEnv *env, jclass thisClass) {
	return get_stateGetBodyRates_i_p_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetBodyRatesIQ
  (JNIEnv *env, jclass thisClass) {
	return get_stateGetBodyRates_i_q_juav();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetBodyRatesIR
  (JNIEnv *env, jclass thisClass) {
	return get_stateGetBodyRates_i_r_juav();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setStabilizationCommands
  (JNIEnv *env, jclass thisClass, jint yaw, jint pitch, jint roll) {
  	set_stabilization_cmd(yaw, pitch, roll);
  }


JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetPositionNedIX
  (JNIEnv *env, jclass thisClass) {
	return stateGetPositionNed_i()->x;
  }
//
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetPositionNedIY
  (JNIEnv *env, jclass thisClass) {
	return stateGetPositionNed_i()->y;
  }
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetPositionNedIZ
  (JNIEnv *env, jclass thisClass) {
  	return stateGetPositionNed_i()->z;
    }
//
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetSpeedNedIX
  (JNIEnv *env, jclass thisClass) {
	return stateGetSpeedNed_i()->x;
  }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetSpeedNedIY
  (JNIEnv *env, jclass thisClass) {
	return stateGetSpeedNed_i()->y;
  }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetSpeedNedIZ
  (JNIEnv *env, jclass thisClass) {
  	return stateGetSpeedNed_i()->z;
    }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyRMatI_10
  (JNIEnv *env, jclass thisClass) {
	struct Int32RMat tmp = *stateGetNedToBodyRMat_i();
	return tmp.m[0];
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyRMatI_11
  (JNIEnv *env, jclass thisClass) {
	struct Int32RMat tmp = *stateGetNedToBodyRMat_i();
	return tmp.m[1];
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyRMatI_12
  (JNIEnv *env, jclass thisClass) {
struct Int32RMat tmp = *stateGetNedToBodyRMat_i();
	return tmp.m[2];
  }
//
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyRMatI_13
  (JNIEnv *env, jclass thisClass) {
struct Int32RMat tmp = *stateGetNedToBodyRMat_i();
	return tmp.m[3];
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyRMatI_14
  (JNIEnv *env, jclass thisClass) {
struct Int32RMat tmp = *stateGetNedToBodyRMat_i();
	return tmp.m[4];
  }
//
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyRMatI_15
  (JNIEnv *env, jclass thisClass) {
struct Int32RMat tmp = *stateGetNedToBodyRMat_i();
	return tmp.m[5];
  }
//
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyRMatI_16
  (JNIEnv *env, jclass thisClass) {
struct Int32RMat tmp = *stateGetNedToBodyRMat_i();
	return tmp.m[6];
  }
//
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyRMatI_17
  (JNIEnv *env, jclass thisClass) {
struct Int32RMat tmp = *stateGetNedToBodyRMat_i();
	return tmp.m[7];
  }
//
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyRMatI_18
  (JNIEnv *env, jclass thisClass) {
struct Int32RMat tmp = *stateGetNedToBodyRMat_i();
	return tmp.m[8];
  }

  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyEulersIPsiInt
    (JNIEnv *env, jclass thisClass) {
 		struct Int32Eulers temp = *stateGetNedToBodyEulers_i();
 		return temp.psi;
    }

  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyEulersITheataInt
    (JNIEnv *env, jclass thisClass) {
 		struct Int32Eulers temp = *stateGetNedToBodyEulers_i();
 		return temp.theta;
    }

  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyEulersIPhiInt
    (JNIEnv *env, jclass thisClass) {
 		struct Int32Eulers temp = *stateGetNedToBodyEulers_i();
 		return temp.phi;
    }

  JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyEulersIPsiFloat
    (JNIEnv *env, jclass thisClass) {
 		struct FloatEulers temp = *stateGetNedToBodyEulers_f();
 		return temp.psi;
    }

  JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyEulersITheataFloat
    (JNIEnv *env, jclass thisClass) {
    	struct FloatEulers temp = *stateGetNedToBodyEulers_f();
     	return temp.theta;
    }

  JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetNedToBodyEulersIPhiFloat
    (JNIEnv *env, jclass thisClass) {
    	struct FloatEulers temp = *stateGetNedToBodyEulers_f();
     	return temp.phi;
    }

    JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetAccelNedIX
      (JNIEnv *env, jclass thisClass) {
	return stateGetAccelNed_i()->x;
      }

    JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetAccelNedIY
      (JNIEnv *env, jclass thisClass) {
      return stateGetAccelNed_i()->y;
      }

    JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetAccelNedIZ
      (JNIEnv *env, jclass thisClass) {
      return stateGetAccelNed_i()->z;
      }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navPeriodicTask
  (JNIEnv *env, jclass thisClass) {
        nav_periodic_task();
        }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navHome
  (JNIEnv *env, jclass thisClass) {
        nav_home();
        }
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_computeDist2ToHome
  (JNIEnv *env, jclass thisClass) {
        compute_dist2_to_home();
        }
JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetSpeedNedFX
  (JNIEnv *env, jclass thisClass) {
			return stateGetSpeedNed_f()->x;
        }
JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetSpeedNedFY
  (JNIEnv *env, jclass thisClass) {
			return stateGetSpeedNed_f()->y;
        }

JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetSpeedNedFZ
  (JNIEnv *env, jclass thisClass) {
			return stateGetSpeedNed_f()->z;
        }
JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetAccelNedFX
  (JNIEnv *env, jclass thisClass) {
  			return stateGetAccelNed_f()->x;
        }

JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetAccelNedFY
  (JNIEnv *env, jclass thisClass) {
  			return stateGetAccelNed_f()->y;
        }

JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateGetAccelNedFZ
  (JNIEnv *env, jclass thisClass) {
  			return stateGetAccelNed_f()->z;
        }

JNIEXPORT jboolean JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_stateIsAttitudeValid
  (JNIEnv *env, jclass thisClass) {
  return stateIsAttitudeValid();
        }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navInit
  (JNIEnv *env, jclass thisClass) {
        nav_init();
}

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendHoverLoop
  (JNIEnv *env, jclass thisClass) {
  juav_register_periodic_telemetry_send_hover_loop();
}
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendHref
  (JNIEnv *env, jclass thisClass) {
  juav_register_periodic_telemetry_send_href();
}
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendGh
  (JNIEnv *env, jclass thisClass) {
  juav_register_periodic_telemetry_send_gh();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendTuneHover
  (JNIEnv *env, jclass thisClass) {
  juav_register_periodic_telemetry_send_tune_hover();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendAutopilotVersion
  (JNIEnv *env, jclass thisClass) {
	juav_register_periodic_telemetry_send_autopilot_version();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendAlive
  (JNIEnv *env, jclass thisClass) {
juav_register_periodic_telemetry_send_alive();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendAttitude
  (JNIEnv *env, jclass thisClass) {
      juav_register_periodic_telemetry_send_attitude();
}

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendStatus
  (JNIEnv *env, jclass thisClass) {
   juav_register_periodic_telemetry_send_status();
  }
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendEnergy
  (JNIEnv *env, jclass thisClass) {
      juav_register_periodic_telemetry_send_energy();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendFp
  (JNIEnv *env, jclass thisClass) {
      juav_register_periodic_telemetry_send_fp();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendRotorcraftCmd
  (JNIEnv *env, jclass thisClass) {
	juav_register_periodic_telemetry_send_rotorcraft_cmd();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendDlValue
  (JNIEnv *env, jclass thisClass) {
      juav_register_periodic_telemetry_send_dl_value();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendActuators
  (JNIEnv *env, jclass thisClass) {
juav_register_periodic_telemetry_send_actuators();
}

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendRc
  (JNIEnv *env, jclass thisClass) {
        juav_register_periodic_telemetry_send_rc();
}

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendRotorcraftRc
  (JNIEnv *env, jclass thisClass) {
        juav_register_periodic_telemetry_send_rotorcraft_rc();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendVertLoop
  (JNIEnv *env, jclass thisClass) {
        juav_register_periodic_telemetry_send_vert_loop();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendTuneVert
  (JNIEnv *env, jclass thisClass) {
        juav_register_periodic_telemetry_send_tune_vert();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendAtt
  (JNIEnv *env, jclass thisClass) {
juav_register_periodic_telemetry_send_att();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendAttRef
  (JNIEnv *env, jclass thisClass) {
        juav_register_periodic_telemetry_send_att_ref();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendAhrsRefQuat
  (JNIEnv *env, jclass thisClass) {
        juav_register_periodic_telemetry_send_ahrs_ref_quat();
}
//
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_periodicTelemetrySendRate
  (JNIEnv *env, jclass thisClass) {
juav_register_periodic_telemetry_send_rate();
}

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getRadioControlValue
    (JNIEnv *env, jclass thisClass, jint index) {
    int val = juav_get_radio_control_value(index);
//    printf("val was %d\n",val);
  	return val;
  }

  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getNavigationCarrotX
    (JNIEnv *env, jclass thisClass) {
    struct EnuCoor_i tmp = juav_get_navigation_carrot();
    return tmp.x;
    }
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getNavigationCarrotY
    (JNIEnv *env, jclass thisClass) {
    struct EnuCoor_i tmp = juav_get_navigation_carrot();
    return tmp.y;
    }
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getNavigationCarrotZ
    (JNIEnv *env, jclass thisClass) {
    struct EnuCoor_i tmp = juav_get_navigation_carrot();
    return tmp.z;
    }
JNIEXPORT jboolean JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunRadioStepAndShouldRunMainEvent
  (JNIEnv *env, jclass thisClass, jdouble time) {
  	return nps_autopilot_run_step_radio_juav_no_main_event(time);
  }

JNIEXPORT jshort JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getRadioControlStatus
  (JNIEnv *env, jclass thisClass) {
    return juav_get_radio_control_status();
  }

  JNIEXPORT jfloat JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetDist2ToHome
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_dist2_to_home();
    }

  JNIEXPORT jboolean JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetTooFarFromHome
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_too_far_from_home();
    }
//
  JNIEXPORT jshort JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetHorizontalMode
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_horizontal_mode();
    }
//
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetNavRoll
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_nav_roll();
    }
//
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetNavPitch
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_nav_pitch();
    }
//
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetNavHeading
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_nav_heading();
    }
//
  JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationSetNavHeading
    (JNIEnv *env, jclass thisClass, jint new_heading) {
    	juav_set_nav_heading(new_heading);
    }
//
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetNavVerticleMode
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_vertical_mode();
    }
//
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetNavClimb
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_nav_climb();
    }
//
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetNavFlightAltitude
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_nav_flight_altitude();
    }
//
  JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_navigationGetNavThrottle
    (JNIEnv *env, jclass thisClass) {
    	return juav_get_nav_throttle();
    }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setGuidanceHMode
  (JNIEnv *env, jclass thisClass, jshort newMode) {
	juav_set_guidance_h_mode(newMode);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setGuidanceVMode
  (JNIEnv *env, jclass thisClass, jshort newMode) {
  	juav_set_guidance_v_mode(newMode);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setAutopilotMode
  (JNIEnv *env, jclass thisClass, jshort newMode) {
    juav_set_autopilot_mode(newMode);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setGuidanceVRcDeltaT
  (JNIEnv *env, jclass thisClass, jint newValue) {
  	juav_set_guidance_v_rc_delta_t(newValue);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setGuidanceVRcZdSp
  (JNIEnv *env, jclass thisClass, jint newValue) {
  	juav_set_guidance_v_rc_zd_sp(newValue);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setStabilizationCommand
  (JNIEnv *env, jclass thisClass, jint commandIndex, jint command) {
	juav_set_stabilization_command(commandIndex, command);
  }

JNIEXPORT jboolean JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getAutopilotMotorsOnJuav
  (JNIEnv *env, jclass thisClass) {
    return juav_get_autopilot_motors_on();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_juavSetAutopilotMotorsOn
  (JNIEnv *env, jclass thisClass, jboolean newValue) {
  	juav_set_autopilot_motors_on(newValue);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_juavSetAutopilotCheckMotorStatus
  (JNIEnv *env, jclass thisClass, jint newValue) {
	juav_set_autopilot_check_motor_status(newValue);
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_juavGetAutopilotCheckMotorStatus
  (JNIEnv *env, jclass thisClass) {
  return juav_get_autopilot_check_motor_status();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_juavSetAutopilotMotorsOnCounter
  (JNIEnv *env, jclass thisClass, jint newValue) {
	juav_set_autopilot_motors_on_counter(newValue);
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_juavGetAutopilotMotorsOnCounter
  (JNIEnv *env, jclass thisClass) {
  	return juav_get_autopilot_motors_on_counter();
  }
JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getStabilizationCmd
  (JNIEnv *env, jclass thisClass, jint index) {
    return juav_get_stabilization_cmd(index);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setStabilizationCmd
  (JNIEnv *env, jclass thisClass, jint index, jint newValue) {
	juav_set_stabilization_cmd(index, newValue);
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceSetPointPosX
  (JNIEnv *env, jclass thisClass) {
  return juav_get_guidance_h_sp_pos_x();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceSetPointPosY
  (JNIEnv *env, jclass thisClass) {
  return juav_get_guidance_h_sp_pos_y();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setHorizantialGuidanceSetPointPosX
  (JNIEnv *env, jclass thisClass, jint x) {
  juav_set_guidance_h_sp_pos_x(x);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setHorizantialGuidanceSetPointPosY
  (JNIEnv *env, jclass thisClass, jint y) {
  juav_set_guidance_h_sp_pos_y(y);
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceSetPointSpeedX
  (JNIEnv *env, jclass thisClass) {
  return juav_get_guidance_h_sp_speed_x();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceSetPointSpeedY
  (JNIEnv *env, jclass thisClass) {
  return juav_get_guidance_h_sp_speed_y();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceReferencePosX
  (JNIEnv *env, jclass thisClass) {
  return juav_get_guidance_h_ref_pos_x();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceReferencePosY
  (JNIEnv *env, jclass thisClass) {
  return juav_get_guidance_h_ref_pos_y();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setHorizantialGuidanceReferencePosX
  (JNIEnv *env, jclass thisClass, jint x) {
  juav_set_guidance_h_ref_pos_x(x);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setHorizantialGuidanceReferencePosY
  (JNIEnv *env, jclass thisClass, jint y) {
  juav_set_guidance_h_ref_pos_y(y);
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceReferenceSpeedX
  (JNIEnv *env, jclass thisClass) {
  return juav_get_guidance_h_ref_speed_x();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceReferenceSpeedY
  (JNIEnv *env, jclass thisClass) {
  return juav_get_guidance_h_ref_speed_y();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setHorizantialGuidanceReferenceSpeedX
  (JNIEnv *env, jclass thisClass, jint x) {
  juav_set_guidance_h_ref_speed_x(x);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setHorizantialGuidanceReferenceSpeedY
  (JNIEnv *env, jclass thisClass, jint y) {
  juav_set_guidance_h_ref_speed_y(y);
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceReferenceAccelX
  (JNIEnv *env, jclass thisClass) {
  return juav_get_guidance_h_ref_accel_x();
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceReferenceAccelY
  (JNIEnv *env, jclass thisClass){
  return juav_get_guidance_h_ref_accel_y();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setHorizantialGuidanceReferenceAccelX
  (JNIEnv *env, jclass thisClass, jint x) {
  juav_set_guidance_h_ref_accel_x(x);
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setHorizantialGuidanceReferenceAccelY
  (JNIEnv *env, jclass thisClass, jint y) {
  juav_set_guidance_h_ref_accel_y(y);
  }

JNIEXPORT jint JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_getHorizantialGuidanceHeading
  (JNIEnv *env, jclass thisClass) {
	return juav_get_guidance_h_sp_heading();
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_setHorizantialGuidanceHeading
  (JNIEnv *env, jclass thisClass, jint newHeading) {
    juav_set_guidance_h_sp_heading(newHeading);
  }

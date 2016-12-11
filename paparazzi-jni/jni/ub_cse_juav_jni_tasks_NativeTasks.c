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
//#include <state.h>


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
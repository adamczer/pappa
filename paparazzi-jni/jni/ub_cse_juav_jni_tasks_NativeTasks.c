#include <jni.h>
#include <stdio.h>
#include "ub_cse_juav_jni_tasks_NativeTasks.h"
#include <nps_main.h>
#include <nps_fdm.h>
#include <nps_autopilot.h>
#include <guidance.h>
#include <main.h>
#include <autopilot.h>


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
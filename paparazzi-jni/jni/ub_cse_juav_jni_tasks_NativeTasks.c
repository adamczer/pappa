#include <jni.h>
#include <stdio.h>
#include "ub_cse_juav_jni_tasks_NativeTasks.h"
#include <nps_main.h>
#include <nps_fdm.h>
#include <nps_autopilot.h>

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
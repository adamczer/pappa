/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class ub_cse_juav_jni_tasks_NativeTasks */

#ifndef _Included_ub_cse_juav_jni_tasks_NativeTasks
#define _Included_ub_cse_juav_jni_tasks_NativeTasks
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    atmosphereUpdate
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_atmosphereUpdate
  (JNIEnv *, jclass);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    autoPilotRunSystimeStep
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_autoPilotRunSystimeStep
  (JNIEnv *, jclass);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    fdmRunStep
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_fdmRunStep
  (JNIEnv *, jclass);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    sensorsRunStep
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_sensorsRunStep
  (JNIEnv *, jclass, jdouble);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    autoPilotRunStep
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_autoPilotRunStep
  (JNIEnv *, jclass, jdouble);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    npsAutopilotRunStepRadio
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepRadio
  (JNIEnv *, jclass, jdouble);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    npsAutopilotRunStepOverwriteIns
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepOverwriteIns
  (JNIEnv *, jclass);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    npsAutopilotRunStepOverwriteAhrs
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepOverwriteAhrs
  (JNIEnv *, jclass);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    npsAutopilotRunStepHandelPeriodicTasks
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepHandelPeriodicTasks
  (JNIEnv *, jclass);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands
  (JNIEnv *, jclass);

/*
 * Class:     ub_cse_juav_jni_tasks_NativeTasks
 * Method:    npsElectricalRunStep
 * Signature: (D)V
 */
JNIEXPORT void JNICALL Java_ub_cse_juav_jni_tasks_NativeTasks_npsElectricalRunStep
  (JNIEnv *, jclass, jdouble);

#ifdef __cplusplus
}
#endif
#endif

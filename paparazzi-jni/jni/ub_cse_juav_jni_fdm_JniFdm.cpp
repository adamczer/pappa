#include <jni.h>
#include <stdio.h>
#include "ub_cse_juav_jni_fdm_JniFdm.h"
#include <FGFDMExec.h>

using namespace JSBSim;
using namespace std;

static FGFDMExec *FDMExec;

JNIEXPORT void JNICALL Java_ub_cse_juav_jni_fdm_JniFdm_FGFDMExecInit
  (JNIEnv *env, jclass thisClass) {
    printf("This is a Test!\n");
    FDMExec = new FGFDMExec();
  	return;
  }




#include <jni.h>
#include <stdio.h>
#include "jniexample_juav_NativeHelloworld.h"
#include "native_example.h"
#include <nps_fdm.h>

#define SIM_DT (1./1000)

// ignore stupid warnings in JSBSim                                             
#pragma GCC diagnostic push                                                     
#pragma GCC diagnostic ignored "-Wunused-parameter"

JNIEXPORT void JNICALL Java_jniexample_juav_NativeHelloworld_nativePrint1(JNIEnv *env,
    jobject thisObj, jstring str){
    const char* s = (*env)->GetStringUTFChars(env, str, NULL);
    printf("%s\n", s);
    nps_fdm_init(SIM_DT); 
    return;
}

JNIEXPORT void JNICALL Java_jniexample_juav_NativeHelloworld_nativePrint2(JNIEnv *env,
        jobject thisObj, jstring str){
    const char* s = (*env)->GetStringUTFChars(env, str, NULL);
    printf("%s\n", s);
    int r = sum(s, 5, 10);
    printf("sum: %d\n", r);
    return;
}




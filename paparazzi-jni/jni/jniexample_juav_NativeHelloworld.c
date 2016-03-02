#include <jni.h>
#include <stdio.h>
#include "jniexample_juav_NativeHelloworld.h"
//#include "native_example.h"

JNIEXPORT void JNICALL Java_jniexample_juav_NativeHelloworld_nativePrint1(JNIEnv *env,
    jobject thisObj, jstring str){
    const char* s = (*env)->GetStringUTFChars(env, str, NULL);
    printf("%s\n", s);
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




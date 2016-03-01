MY_JNI_HEADER=./jni/includes
JDK=/usr/lib/jvm/java-7-openjdk-amd64
BIN=./bin

all:
#	gcc -O3 -fPIC -shared -I${JDK}/include/ -I${JDK}/include/linux/ -I${MY_JNI_HEADER}
#	jni/jniexample_juav_NativeHelloworld.c -o ${BIN}/JNI_HelloWorld.so

	gcc -O3 -fPIC -shared -I${JDK}/include/ -I${JDK}/include/linux/ \
    	-I${MY_JNI_HEADER} jni/jniexample_juav_NativeHelloworld.c \
    	-o ${BIN}/JNI_HelloWorld.so

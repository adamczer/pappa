ARDU_SRC=/home/erle/ardupilot
JAVA_HOME=/home/erle/program_files/jdk1.8.0_191/

all: jni

jni:
	g++ -L./lib -fPIC -std=c++11 -I"./includes" -I"${JAVA_HOME}/include" -I"${JAVA_HOME}/include/linux" -I"${ARDU_SRC}/libraries/" -I"${ARDU_SRC}/libraries/AP_HAL/" -I"${ARDU_SRC}/build/erlebrain2/libraries/GCS_MAVLink/" ub_cse_juav_ardupilot_ArdupilotBridge.cpp -lpthread -ldl -lArduCopter -Wl,--no-undefined -shared -o libArduJni.so 

clean:
	-rm libArduJni.so

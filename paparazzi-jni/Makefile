LOCAL_JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
JNI_FLAGS=-I$(LOCAL_JAVA_HOME)/include -I$(LOCAL_JAVA_HOME)/include/linux/
PPRZ_HOME=${PAPARAZZI_HOME}

ROOT=$(shell pwd)
#JNI_SRCS  := $(wildcard $(ROOT)/jni/*.c)
#JNI_OBJS  := $(addprefix $(ROOT)/bin/,$(notdir $(JNI_SRCS:.c=.o)))
JNI_SRCS  := $(wildcard jni/*.c)
JNI_OBJS  := $(addprefix bin/,$(notdir $(JNI_SRCS:.c=.o)))
PAPA_SRCS := $(wildcard $(ROOT)/native/*.c)
PAPA_OBJS := $(addprefix $(ROOT)/bin/,$(notdir $(PAPA_SRCS:.c=.o)))
OUTPUT_DIR=$(ROOT)/bin

CFLAGS=-I$(PPRZ_HOME)/modules -I$(PPRZ_HOME)/arch/sim/modules \
	   -I$(PPRZ_HOME)/sw/include/ \
	   -I$(PPRZ_HOME)/firmwares/rotorcraft -I$(PPRZ_HOME)/boards/pc \
	   -I$(PPRZ_HOME)/sw/airborne/arch/sim \
	   -I$(PPRZ_HOME)/boards/pc -I$(PPRZ_HOME)/sw/simulator \
	   -I$(PPRZ_HOME)/sw/simulator/nps \
	   -I$(PPRZ_HOME)/conf/simulator/nps \
	   -I$(PPRZ_HOME)/sw/airborne/firmwares/rotorcraft \
	   -I$(PPRZ_HOME)/sw/airborne \
	   -I$(PPRZ_HOME)/sw/airborne/subsystems/imu \
	   -I$(PPRZ_HOME)/var/aircrafts/Quad_LisaM_2/nps/generated \
	   -I$(PPRZ_HOME)/var/aircrafts/Quad_LisaM_2/nps \
	   -I$(PPRZ_HOME)/sw/airborne/ \
	   -I/usr/include/JSBSim -I/usr/lib/x86_64-linux-gnu/glib-2.0/include \
	   -I/usr/include/glib-2.0 -DBOARD_CONFIG="$(PPRZ_HOME)/sw/airborne/boards/pc_sim.h" \
	   -L./libs/

LDFLAGS=-lglib-2.0 -lm -lglibivy -lpcre -lgsl -lgslcblas -L/usr/lib/x86_64-linux-gnu -lSDL -lJSBSim

paparazzi-port: libpapa_native.so 

libpapa_native.so: $(PAPA_OBJS) $(JNI_OBJS)
	gcc -shared -Wl,-soname,libpapa_native.so \
		-I$(ROOT)/native/includes $(JNI_FLAGS) $(CFLAGS)\
		-o $@ $^ -lpprz $(LDFLAGS)
	mv libpapa_native.so ./bin/

# Compile native functions
$(PAPA_OBJS): $(PAPA_SRCS)
	gcc -Wall -c -fPIC $(CFLAGS) -I$(ROOT)/native/includes $(LDFLAGS) -o $@ $<

jni: $(JNI_OBJS)

# Compile JNI interfaces
$(JNI_OBJS): bin/%.o: jni/%.c
	gcc -Wall -c -fPIC $(CFLAGS) -I$(ROOT)/native/includes -I$(ROOT)/jni/includes \
		$(JNI_FLAGS) $(LDFLAGS) -o $@ $<

install:
	cp ./bin/libpapa_native.so ../paparazzi-deploy/	

clean:
	rm ./bin/*

.PHONY: clean paparazzi-port

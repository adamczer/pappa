LOCAL_JAVA_HOME=/usr/lib/jvm/java-7-openjdk-amd64
JNI_FLAGS=-I$(LOCAL_JAVA_HOME)/include -I$(LOCAL_JAVA_HOME)/include/linux/

ROOT=$(shell pwd)
JNI_SRCS  := $(wildcard jni/*.c)
JNI_OBJS  := $(addprefix bin/,$(notdir $(JNI_SRCS:.c=.o)))
PAPA_SRCS := $(wildcard $(ROOT)/native/*.c)
PAPA_OBJS := $(addprefix $(ROOT)/bin/,$(notdir $(PAPA_SRCS:.c=.o)))
OUTPUT_DIR=$(ROOT)/bin

paparazzi-port: libpapa_native.so 

libpapa_native.so: $(PAPA_OBJS) $(JNI_OBJS)
	gcc -shared -Wl,-soname,libpapa_native.so -I$(ROOT)/native/includes \
		$(JNI_FLAGS) -o $@ $^
	mv libpapa_native.so ./bin/

$(PAPA_OBJS): $(PAPA_SRCS)
	gcc -Wall -c -fPIC -I$(ROOT)/native/includes -o $@ $<

jni: $(JNI_OBJS)

$(JNI_OBJS): bin/%.o: jni/%.c
	gcc -Wall -c -fPIC -I$(ROOT)/native/includes -I$(ROOT)/jni/includes \
		$(JNI_FLAGS) -o $@ $<

install:
	cp ./bin/libpapa_native.so ../paparazzi-deploy/	

clean:
	rm ./bin/*

.PHONY: clean paparazzi-port

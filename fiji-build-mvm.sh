#!/usr/bin/env bash
rm -rf juav-fiji-mvm
mkdir juav-fiji-mvm
cd juav-fiji-mvm
mkdir juav-jars
cp ../paparazzi-airborne/target/paparazzi-airborne-1.0-SNAPSHOT.jar juav-jars/
cp ../paparazzi-jni/target/paparazzi-jni-1.0-SNAPSHOT.jar juav-jars/
cp ../paparazzi-simulator/target/paparazzi-nps-1.0-SNAPSHOT.jar juav-jars/
cd juav-jars
unzip paparazzi-airborne-1.0-SNAPSHOT.jar
rm -rf META-INF
unzip paparazzi-jni-1.0-SNAPSHOT.jar
rm -rf META-INF
unzip paparazzi-nps-1.0-SNAPSHOT.jar
rm -rf META-INF
cp $FIJI_HOME/lib/rtsj.jar .
unzip rtsj.jar
cd ..
mkdir build
find juav-jars/ -name \*.class -exec cp {} build/ \;

mkdir libs
#cp ../paparazzi-jni/bin/libpapa_native.so  libs/
#cp ../paparazzi-jni/libs/libpprz.so libs/
cp ${PAPARAZZI_HOME}/var/aircrafts/Quad_LisaM_2/nps/libpprz.a libs/

$FIJI_HOME/bin/fivmc \
--more-opt \
--c-opt SPEED \
--g-def-max-mem 8601560 \
--gc CMR  --64 \
--payload \
--rt-library=RTSJ \
-o JuavFiji ./build/*.class
#sed "/\tranlib libfivm.a/\
#\t$(AR) cr libfivm.a $(TARGS) /home/adamczer/Desktop/libs-pprz/*.o\n
#\t$(AR) cr libfivm.a $(TARGS) /home/adamczer/Desktop/libs-jsbsim/*.o\n
#\t$(AR) cr libfivm.a $(TARGS) /home/adamczer/Desktop/ivy-libs/*.o\n
#\tranlib libfivm.a/" JuavFiji.a.build/Makefile
# This is the apps that you produced
cp ../mvm/app/Apps.java .
cp ../mvm/app/VMConfig.java .
javac -cp ${FIJI_HOME}/lib/rtsj.jar Apps.java
javac -cp ${FIJI_HOME}/lib/rtsj.jar:${FIJI_HOME}/lib/fivmr.jar:${FIJI_HOME}/lib/fivmcommon.jar VMConfig.java
${FIJI_HOME}/bin/fivmc --rt-library=RTSJ --64 --payload -o apps Apps.class
${FIJI_HOME}/bin/fivmc -o mvm --sys-libs "-lpthread -ldl -lm -lstdc++ -lglib-2.0 -lpcre -lSDL -lgsl -lgslcblas" --64 --link-payload JuavFiji --link-payload apps VMConfig.class
##--link-dynamic pprz
#export LD_LIBRARY_PATH=/lib:/usr/lib:/usr/local/lib
#sudo cp libs/libpprz.so /usr/local/lib/
echo "su as root then set PAPARAZZI_HOME then run ./mvm"
#!/usr/bin/env bash
rm -rf juav-fiji
mkdir juav-fiji
cd juav-fiji
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
cp ${PAPARAZZI_HOME}/var/aircrafts/Quad_LisaM_2/nps/libpprz.so libs/

mkdir includes
cp $PAPARAZZI_HOME/sw/simulator/nps/nps_main.h includes/
cp $PAPARAZZI_HOME/sw/simulator/nps/nps_autopilot.h includes/
cp $PAPARAZZI_HOME/sw/simulator/nps/nps_fdm.h includes/
cp $PAPARAZZI_HOME/sw/airborne/firmwares/rotorcraft/autopilot.h includes/
cp $PAPARAZZI_HOME/sw/airborne/firmwares/rotorcraft/stabilization/stabilization_attitude_quat_int.h includes/
$FIJI_HOME/bin/fivmc \
--extra-include-dir includes \
--extra-include-dir $PAPARAZZI_HOME/modules \
--extra-include-dir $PAPARAZZI_HOME/arch/sim/modules \
--extra-include-dir $PAPARAZZI_HOME/sw/include/ \
--extra-include-dir $PAPARAZZI_HOME/firmwares/rotorcraft \
--extra-include-dir $PAPARAZZI_HOME/boards/pc \
--extra-include-dir $PAPARAZZI_HOME/sw/airborne/arch/sim \
--extra-include-dir $PAPARAZZI_HOME/boards/pc \
--extra-include-dir $PAPARAZZI_HOME/sw/simulator \
--extra-include-dir $PAPARAZZI_HOME/sw/simulator/nps \
--extra-include-dir $PAPARAZZI_HOME/conf/simulator/nps \
--extra-include-dir $PAPARAZZI_HOME/sw/airborne/firmwares/rotorcraft \
--extra-include-dir $PAPARAZZI_HOME/sw/airborne/firmwares/rotorcraft/stabilization \
--extra-include-dir $PAPARAZZI_HOME/sw/airborne \
--extra-include-dir $PAPARAZZI_HOME/sw/airborne/subsystems/imu \
--extra-include-dir $PAPARAZZI_HOME/sw/airborne/subsystems/gps \
--extra-include-dir $PAPARAZZI_HOME/var/aircrafts/Quad_LisaM_2/nps/generated \
--extra-include-dir $PAPARAZZI_HOME/var/aircrafts/Quad_LisaM_2/nps \
--extra-include-dir $PAPARAZZI_HOME/sw/airborne/ \
--extra-include-dir $PAPARAZZI_HOME/sw/airborne/subsystems/datalink/ \
--extra-include-dir $PAPARAZZI_HOME/var/include/ \
--extra-include-dir $PAPARAZZI_HOME/var/aircrafts/Quad_LisaM_2/nps/ \
--extra-include-dir $PAPARAZZI_HOME/sw/airborne/modules/ \
--more-opt \
--c-opt SPEED \
--g-def-max-mem 200M \
--g-def-immortal-mem 100M \
--gc CMR  \
--payload \
--rt-library=RTSJ \
-o JuavFiji ./build/*.class --extra-include 'nps_main.h' --link-dynamic pprz --extra-include 'nps_autopilot.h' --extra-include 'nps_fdm.h' --extra-include 'autopilot.h' --extra-include 'stabilization_attitude_quat_int.h'
# This is the apps that you produced
cp /home/adamczer/mvm-work/test-app/Apps.java .
cp /home/adamczer/mvm-work/test-app/VMConfig.java .
javac -cp ${FIJI_HOME}/lib/rtsj.jar Apps.java
javac -cp ${FIJI_HOME}/lib/rtsj.jar:${FIJI_HOME}/lib/fivmr.jar:${FIJI_HOME}/lib/fivmcommon.jar VMConfig.java
${FIJI_HOME}/bin/fivmc --g-def-immortal-mem 100M --g-def-max-mem 200M --rt-library=RTSJ --payload -o apps Apps.class
${FIJI_HOME}/bin/fivmc --g-def-immortal-mem 256M --g-def-max-mem 512M -o mvm --link-dynamic pprz --link-payload JuavFiji --link-payload apps VMConfig.class
#--link-dynamic pprz

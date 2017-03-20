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
cp /home/adamczer/juav/fiji/fivm/lib/rtsj.jar .
unzip rtsj.jar
cd ..
mkdir build
find juav-jars/ -name \*.class -exec cp {} build/ \;

mkdir libs
#cp ../paparazzi-jni/bin/libpapa_native.so  libs/
cp ../paparazzi-jni/libs/libpprz.so libs/

mkdir includes
cp $PAPARAZZI_HOME/sw/simulator/nps/nps_main.h includes/
cp $PAPARAZZI_HOME/sw/simulator/nps/nps_autopilot.h includes/
cp $PAPARAZZI_HOME/sw/simulator/nps/nps_fdm.h includes/
cp $PAPARAZZI_HOME/sw/airborne/firmwares/rotorcraft/autopilot.h includes/
cp $PAPARAZZI_HOME/sw/airborne/firmwares/rotorcraft/stabilization/stabilization_attitude_quat_int.h includes/
$FIJI_HOME/bin/fivmc \
--extra-include-dir /home/adamczer/juav/working-code-02-13-2017/juav-autopilot-fiji/juav-fiji/includes \
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
--g-def-max-mem 8601560 \
--gc CMR  \
--link-dir /home/adamczer/juav/working-code-02-13-2017/juav-autopilot-fiji/juav-fiji/libs -o JuavFiji ./build/*.class --extra-include 'nps_main.h' --link-dynamic pprz --extra-include 'nps_autopilot.h' --link-dynamic pprz --extra-include 'nps_fdm.h' --link-dynamic pprz --extra-include 'autopilot.h' --link-dynamic pprz --extra-include 'stabilization_attitude_quat_int.h' --link-dynamic pprz
export LD_LIBRARY_PATH=/lib:/usr/lib:/usr/local/lib
sudo cp libs/libpprz.so /usr/local/lib/

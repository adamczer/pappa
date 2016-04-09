#! /bin/bash

export LD_LIBRARY_PATH=.

echo "> Copyign jar and so files ..."
cp ../paparazzi-jni/target/paparazzi-jni-1.0-SNAPSHOT.jar .
cp ../paparazzi-jni/bin/*.so .
cp ../paparazzi-simulator/target/paparazzi-nps-1.0-SNAPSHOT.jar .


echo "> java -cp paparazzi-jni-1.0-SNAPSHOT.jar:paparazzi-nps-1.0-SNAPSHOT.jar"
echo ">      juav.simulator.nps.cyclic.NpsCyclicImpl"

echo ">>Execution Outputs <<"

java -Djava.library.path=/home/gassa/uav/jUAV/paparazzi-deploy/libs \
    -cp paparazzi-jni-1.0-SNAPSHOT.jar:paparazzi-nps-1.0-SNAPSHOT.jar \
    juav.simulator.nps.cyclic.NpsCyclicImpl

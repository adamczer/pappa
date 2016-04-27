#! /bin/bash

export LD_LIBRARY_PATH=.

echo "> Copyign jar and so files ..."
cp ../paparazzi-jni/target/paparazzi-jni-1.0-SNAPSHOT.jar .
cp ../paparazzi-jni/bin/*.so .
cp ../paparazzi-simulator/target/paparazzi-nps-1.0-SNAPSHOT.jar .


echo ">>Execution Outputs <<"

java -Djava.library.path=../paparazzi-jni/libs \
    -cp paparazzi-jni-1.0-SNAPSHOT.jar:paparazzi-nps-1.0-SNAPSHOT.jar \
    juav.simulator.nps.cyclic.NpsCyclicImpl

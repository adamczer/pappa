#! /bin/bash

export LD_LIBRARY_PATH=paparazzi-deploy/libs

echo "> Copyign jar and so files ..."
cp ./paparazzi-jni/target/paparazzi-jni-1.0-SNAPSHOT.jar ./paparazzi-deploy/
cp ./paparazzi-jni/bin/*.so ./paparazzi-deploy/
cp ./paparazzi-simulator/target/paparazzi-nps-1.0-SNAPSHOT.jar ./paparazzi-deploy/


echo "> java -cp paparazzi-jni-1.0-SNAPSHOT.jar:paparazzi-nps-1.0-SNAPSHOT.jar"
echo ">      juav.simulator.nps.cyclic.NpsCyclicImpl"

echo ">>Execution Outputs <<"

java -Djava.library.path=paparazzi-deploy/libs \
    -cp paparazzi-deploy/paparazzi-jni-1.0-SNAPSHOT.jar:paparazzi-deploy/paparazzi-nps-1.0-SNAPSHOT.jar \
    juav.simulator.nps.cyclic.NpsCyclicImpl

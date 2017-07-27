#! /bin/bash

#export PAPARAZZI_HOME=~/paparazzi
export LD_LIBRARY_PATH=./paparazzi-deploy/

ulimit -r 10

echo "> Copying jar and so files ..."
cp paparazzi-jni/target/paparazzi-jni-1.0-SNAPSHOT.jar ./paparazzi-deploy/
cp paparazzi-jni/bin/*.so ./paparazzi-deploy
cp paparazzi-jni/libs/*.so ./paparazzi-deploy
cp paparazzi-simulator/target/paparazzi-nps-1.0-SNAPSHOT.jar ./paparazzi-deploy/
cp paparazzi-airborne/target/paparazzi-airborne-1.0-SNAPSHOT.jar ./paparazzi-deploy/
cp $FIJI_HOME/lib/fivmcommon.jar ./paparazzi-deploy/
cp $FIJI_HOME/lib/rtsj.jar ./paparazzi-deploy/

echo ">>Execution Outputs <<"

java -Djava.library.path=./paparazzi-deploy/libs \
    -cp paparazzi-deploy/paparazzi-jni-1.0-SNAPSHOT.jar:paparazzi-deploy/paparazzi-nps-1.0-SNAPSHOT.jar:paparazzi-deploy/paparazzi-airborne-1.0-SNAPSHOT.jar:paparazzi-deploy/rtsj.jar:paparazzi-deploy/fivmcommon.jar \
    juav.simulator.nps.cyclic.NpsCyclicImpl

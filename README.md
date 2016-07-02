## How to checkout jUAV repo with development branch
```
git clone git@github.com:adamczer/pappa.git
git checkout -b development origin/development
modification; git add ...; git commit ...
git pull origin development <-- before each git push
git push origin development <-- push to remote development repo
```

## How to checkout paparazzi-juav repo with development branch
```
cd ~ 
git clone https://github.com/adamczer/paparazzi-native-jni.git paparazzi
git checkout -b development origin/development
...
git pull origin development <-- before each git push                            
git push origin development <-- push to remote development repo
```

## Configure local machine for development
- modify /etc/security/limits.conf with the following line:
```
<your_username> hard rtprio 99
```
- create file /etc/ld.so.conf.d/juav.conf with the following line:
```
<your_juav_repo_root>/paparazzi-jni/lib
```

## How to run jUAV with paparrazi's GUI
* make sure your paparazzi is compiled correctly. [https://wiki.paparazziuav.org/wiki/Installation]. we will use a modified paparazzi, here is a break-down of the commands you need to run, once you checkout our paparazzi repo with development branch.
```
sudo apt-get -f -y install paparazzi-dev paparazzi-jsbsim gcc-arm-none-eabi
sudo cp conf/system/udev/rules/*.rules /etc/udev/rules.d/
make clean && make
```
* run paparazzi by executing ```./paparazzi```
* build A/C: Quad_LisaM_2, Target:nps

Once built the paparazzi code will have produced the required shared library
located at var/aircrafts/Quad_LisaM_2/nps/libpapa.so. This library is required by
jUAV to allow for the execution of any required JNI calls in the hybrid Java-C autopilot.

This compiled lib needs to be placed in paparazzi-jni/libs/
Once there you can now build jUAV linking against the shared library

## Building jUAV
cd <jUAV_root_dir>
mvn compile
mvn install
cd paparazzi-jni
make clean
make
cd ..
./paparazzi-deploy/project-root-run.sh


Once jUAV is built return to the Paparazzi UI and
you can now run using the jUAV code.


* click `Execute` button to run simulation
* `stop` the "Simulator"
* change the command in "Simulator" command:
```
/home/gassa/paparazzi/sw/simulator/pprzsim-launch -a Quad_LisaM_2 -t nps --java yes

```






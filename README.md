modify /etc/security/limits.conf with the following line:
<your_username> hard rtprio 99

create file /etc/ld.so.conf.d/juav.conf with the following line:
<your_juav_repo_root>/paparazzi-jni/lib

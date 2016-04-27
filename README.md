## How to checkout repo with development branch
```
git clone git@github.com:adamczer/pappa.git
git checkout -b development origin/development
modification; git add ...; git commit ...
git pull origin development <-- before each git push
git push origin development <-- push to remote development repo
```

## Configure local machine for development
- modify /etc/security/limits.conf with the following line:
```<your_username> hard rtprio 99```

- create file /etc/ld.so.conf.d/juav.conf with the following line:
```<your_juav_repo_root>/paparazzi-jni/lib```

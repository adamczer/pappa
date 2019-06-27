package ub.cse.juav.ardupilot;

import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.simulator.nps.cyclic.NpsCyclicImpl;
import juav.simulator.tasks.ITask;
import juav.simulator.tasks.sensors.device.jni.*;
import juav.simulator.time.TimeHandler;
import ub.cse.juav.ardupilot.sensors.*;
import ub.cse.juav.ardupilot.time.HardwareTimeHandler;
import ub.cse.juav.fijiorjni.NativeSwitch;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArdupilotCyclicImpl extends NpsCyclicImpl {

    /**
     * Cyclic execution of the of the periodic step functions that are
     * available followed by the set of periodic tasks
     */
    @Override
    public void run() {

        do {
                /**vv*** Entry point for periodic tasks***vv**/
                for (ITask task : tasks) {
                    if (task.isAvailiable()) {
                        task.execute();
                    }
                }
                /**^^*** Entry point for periodic tasks***^^**/
        } while (!NativeSwitch.isFiji() && run);
    }


    @Override
    public void init() {
        PaparazziNpsWrapper.npsInit();
        List<ITask> taskList = new ArrayList<>();
        TimeHandler timeHandler = new HardwareTimeHandler();
//        JniGpsSensor gpsSensor = new ArdupilotJniGpsSensor();
        JniGpsSensor gpsSensor = new JniGpsSensor();
//        JniAccelSensor accelSensor = new ArdupilotJniAccelSensor();
        JniAccelSensor accelSensor = new JniAccelSensor();
//        JniGyroSensor gyroSensor = new ArdupilotJniGyroSensor();
        JniGyroSensor gyroSensor = new JniGyroSensor();
//        JniMagSensor magSensor = new ArdupilotJniMagSensor();
        JniMagSensor magSensor = new JniMagSensor();
//        JniBaroSensor baroSensor = new ArdupilotJniBaroSensor();
        JniBaroSensor baroSensor = new JniBaroSensor();
        gpsSensor.setTimeHandler(timeHandler);
        accelSensor.setTimeHandler(timeHandler);
        gyroSensor.setTimeHandler(timeHandler);
        magSensor.setTimeHandler(timeHandler);
        baroSensor.setTimeHandler(timeHandler);
        taskList.add(gpsSensor);
        taskList.add(accelSensor);
        taskList.add(gyroSensor);
        taskList.add(magSensor);
        taskList.add(baroSensor);
        new ITask(){

            @Override
            public void execute() {

            }

            @Override
            public boolean isAvailiable() {
                return false;
            }

            @Override
            public void init() {

            }
        };
        ArdupilotAutopilotRotorcraft npsAutoPilotRotorCraft = new ArdupilotAutopilotRotorcraft();
        npsAutoPilotRotorCraft.setAccelSensor(accelSensor);
        npsAutoPilotRotorCraft.setGpsSensor(gpsSensor);
        npsAutoPilotRotorCraft.setGyroSensor(gyroSensor);
        npsAutoPilotRotorCraft.setMagSensor(magSensor);
        npsAutoPilotRotorCraft.setBaroSensor(baroSensor);
        npsAutoPilotRotorCraft.setGpsSimNps(new GpsSimNps());
        npsAutoPilotRotorCraft.setJniImuNps(new JniImuNps());
        taskList.add(npsAutoPilotRotorCraft);

        // Initialize all tasks
        for (ITask task:taskList) {
            task.init();
        }
        setTasks(taskList);
    }

    public static void runJuav(boolean isFiji) {
        if(!isFiji) {
            String juavSrc = System.getenv("JUAV_SRC"); /* SET ENVIRONMENT VARIABLE TO jUAV project root*/
            File pprzLib = new File(juavSrc+"/paparazzi-jni/libs/libpprz.so");
            System.load(pprzLib.getAbsolutePath());
            File lib = new File(juavSrc + "/paparazzi-jni/bin/libpapa_native.so");
            System.load(lib.getAbsolutePath());
            File ardulib = new File(juavSrc + "/paparazzi-ardupilot/native/lib/libArduCopter.so");
            System.load(ardulib.getAbsolutePath());
            File ardujni = new File(juavSrc + "/paparazzi-ardupilot/native/libArduJni.so");
            System.load(ardujni.getAbsolutePath());
            isFiji = false;
        }
        NativeSwitch.setIsFiji(isFiji);

        ArdupilotCyclicImpl ardupilotCyclic = new ArdupilotCyclicImpl();
        ardupilotCyclic.init();
        ardupilotCyclic.run();
    }

    public static void main(String[] args) {
        if(args.length==1) {
            runJuav(false);
        } else {
            RelativeTime rt =
                    new RelativeTime( 0 , 1953125 ) ;
            RealtimeThread t = new RealtimeThread(new PriorityParameters( 80) ,
                    new PeriodicParameters( rt )) {
                @Override
                public void run() {
                    NativeSwitch.setIsFiji(true);
                    NpsCyclicImpl nps = new NpsCyclicImpl();
                    nps.init();
                    while (true) {
                        nps.run();
                        waitForNextPeriod();
                    }
                }
            };
            t.start();
        }
    }


}

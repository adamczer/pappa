package juav.simulator.nps.cyclic;

import com.fiji.fivm.ThreadPriority;
import juav.autopilot.NpsAutoPilotRotorCraft;
import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.simulator.nps.AbstractNpsImpl;
import juav.simulator.tasks.ITask;
import juav.simulator.tasks.jni.JniNpsAtmosphereUpdate;
import juav.simulator.tasks.jni.JniNpsAutoPilotRunSystimeStep;
import juav.simulator.tasks.jni.JniNpsFdmRunStep;
import juav.simulator.tasks.sensors.device.jni.*;
import juav.simulator.time.TimeHandler;
import ub.cse.juav.fijiorjni.NativeSwitch;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;

import javax.realtime.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamczer on 1/24/16.
 */
public class NpsCyclicImpl extends AbstractNpsImpl {
    private static int iter =1;
    private static final boolean logMainLoop = false;

    private static int prev_cnt = 0;
    private static int grow_cnt = 0;
    /**
     * Cyclic execution of the of the periodic step functions that are
     * available followed by the set of periodic tasks
     */
    @Override
    public void run() {
        if(timeHandler==null) {
            throw new IllegalStateException("Time handler must be set on Nps simulator.");
        }
        do {
            PaparazziNpsWrapper.npsMainPeriodicJuavNative();
            int cnt = 0;
            while (PaparazziNpsWrapper.getNpsMainSimTime() <= PaparazziNpsWrapper.getNpsMainHostTimeElapsed()) {
                long cyclicExecutiveStart = System.nanoTime();
                /**vv*** Entry point for periodic tasks***vv**/
                long start;
                if(logMainLoop)
                    start= System.nanoTime();
                for (ITask task : tasks) {
                    if (task.isAvailiable()) {
                        task.execute();
                    }
                }
                if(logMainLoop) {
                    long finish = System.nanoTime();
//                    try {
//                        log.write(""+(iter++)+" "+(finish-start)+"\n");
//                        log.flush();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                }
                long cyclicExecutiveEnd = System.nanoTime();
                try {
                    cyclicExecutiveLog.write(((cyclicExecutiveEnd - cyclicExecutiveStart) + "\n").getBytes());
                    cyclicExecutiveLog.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /**^^*** Entry point for periodic tasks***^^**/
                PaparazziNpsWrapper.setNpsMainSimTime(PaparazziNpsWrapper.getNpsMainSimTime() + PaparazziNpsWrapper.getNpsMainSimDt());

                if (PaparazziNpsWrapper.getNpsMainDisplayTime() < (PaparazziNpsWrapper.getHostTimeNow() - PaparazziNpsWrapper.getNpsMainRealInitialTime())) {
                    PaparazziNpsWrapper.npsMainDisplay();
                    PaparazziNpsWrapper.setNpsMainDisplayTime(PaparazziNpsWrapper.getNpsMainDisplayTime() + PaparazziNpsWrapper.getNpsMainDisplayDt());
                }
                cnt++;
            }

  /* Check to make sure the simulation doesn't get too far behind real time looping */
            if (cnt > (prev_cnt)) {
                grow_cnt++;
            } else {
                grow_cnt--;
            }
            if (grow_cnt < 0) {
                grow_cnt = 0;
            }
            prev_cnt = cnt;

            if (grow_cnt > 10) {
                System.out.println("Warning: The time factor is too large for efficient operation! Please reduce the time factor.\n");
            }
        } while (!NativeSwitch.isFiji() && run);
    }


    /**
     * creates periodic tasks and ensures that they exist in the list
     * in the order they should execute.
     */
    private FileOutputStream cyclicExecutiveLog;
    @Override
    public void init() {
        try {
            cyclicExecutiveLog = new FileOutputStream("cyclic-executive.log");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PaparazziNpsWrapper.npsInit();

        timeHandler = new TimeHandler();

        List<ITask> taskList = new ArrayList<>();
        taskList.add(new JniNpsAtmosphereUpdate());
        taskList.add(new JniNpsAutoPilotRunSystimeStep());
        taskList.add(new JniNpsFdmRunStep());
        JniGpsSensor gpsSensor = new JniGpsSensor();
        gpsSensor.setTimeHandler(timeHandler);
        JniAccelSensor accelSensor = new JniAccelSensor();
        accelSensor.setTimeHandler(timeHandler);
        JniGyroSensor gyroSensor = new JniGyroSensor();
        gyroSensor.setTimeHandler(timeHandler);
        JniMagSensor magSensor = new JniMagSensor();
        magSensor.setTimeHandler(timeHandler);
        JniBaroSensor baroSensor = new JniBaroSensor();
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
        NpsAutoPilotRotorCraft npsAutoPilotRotorCraft = new NpsAutoPilotRotorCraft();
        npsAutoPilotRotorCraft.setAccelSensor(accelSensor);
        npsAutoPilotRotorCraft.setGpsSensor(gpsSensor);
        npsAutoPilotRotorCraft.setGyroSensor(gyroSensor);
        npsAutoPilotRotorCraft.setMagSensor(magSensor);
        npsAutoPilotRotorCraft.setBaroSensor(baroSensor);
        npsAutoPilotRotorCraft.setGpsSimNps(new GpsSimNps());
        npsAutoPilotRotorCraft.setJniImuNps(new JniImuNps());
        taskList.add(npsAutoPilotRotorCraft);

//        taskList.add(new JniNpsAutoPilotRunStep());



        // Initialize all tasks
        for (ITask task:taskList) {
            task.init();
        }

        setTasks(taskList);

//        init c++ jni things

    }

    public static void main(String[] args) {
        if(args.length==1) {
            runSimulation(false);
        } else {
            RelativeTime rt =
                    new RelativeTime( 0 , 1953125 ) ;
//            RealtimeThread t = new RealtimeThread(new PriorityParameters(108 ) ,
                    RealtimeThread t = new RealtimeThread(new PriorityParameters( 80) ,
                    new PeriodicParameters ( rt )) {
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

    public static void runSimulation(boolean isFiji) {
        if(!isFiji) {
            File pprzLib = new File("/home/adamczer/workspace/juav-autopilot-fiji/paparazzi-jni/libs/libpprz.so");
            System.load(pprzLib.getAbsolutePath());
            File lib = new File("/home/adamczer/workspace/juav-autopilot-fiji/paparazzi-jni/bin/libpapa_native.so");
            System.load(lib.getAbsolutePath());
            isFiji = false;
        }
        NativeSwitch.setIsFiji(isFiji);
        NpsCyclicImpl nps = new NpsCyclicImpl();
        nps.init();
        nps.run();
    }

}

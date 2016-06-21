package juav.simulator.nps.cyclic;

import juav.autopilot.NpsAutoPilotRotorCraft;
import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.simulator.nps.AbstractNpsImpl;
import juav.simulator.tasks.ITask;
import juav.simulator.tasks.jni.*;
import juav.simulator.tasks.sensors.device.jni.*;
import juav.simulator.time.TimeHandler;
import ub.cse.juav.jni.nps.PaparazziNps;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamczer on 1/24/16.
 */
public class NpsCyclicImpl extends AbstractNpsImpl {
    private static FileWriter log;
    private static int iter =1;
    private static final boolean logMainLoop = false;

    static  {
        if(logMainLoop) {
            try {
                log = new FileWriter("juav-main-periodic.data");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
        while(run.get()) {
            PaparazziNps.npsMainPeriodicJuavNative();
            int cnt = 0;
            while (PaparazziNps.getNpsMainSimTime() <= PaparazziNps.getNpsMainHostTimeElapsed()) {
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
                    try {
                        log.write(""+(iter++)+" "+(finish-start)+"\n");
                        log.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                /**^^*** Entry point for periodic tasks***^^**/
                PaparazziNps.setNpsMainSimTime(PaparazziNps.getNpsMainSimTime() + PaparazziNps.getNpsMainSimDt());

                if (PaparazziNps.getNpsMainDisplayTime() < (PaparazziNps.getHostTimeNow() - PaparazziNps.getNpsMainRealInitialTime())) {
                    PaparazziNps.npsMainDisplay();
                    PaparazziNps.setNpsMainDisplayTime(PaparazziNps.getNpsMainDisplayTime() + PaparazziNps.getNpsMainDisplayDt());
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

        }
    }


    /**
     * creates periodic tasks and ensures that they exist in the list
     * in the order they should execute.
     */
    @Override
    public void init() {
        PaparazziNps.npsInit();

        timeHandler = new TimeHandler();

        List<ITask> taskList = new ArrayList<>();
        taskList.add(new JniNpsAtmosphereUpdate());
        taskList.add(new JniNpsAutoPilotRunSystimeStep());
        taskList.add(new JniNpsFdmRunStep());
//        taskList.add(new JniNpsSensorsRunStep());
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
        File lib = new File("paparazzi-jni/bin/libpapa_native.so");
        System.load(lib.getAbsolutePath());
        File pprzLib = new File("paparazzi-jni/libs/libpprz.so");
        System.load(pprzLib.getAbsolutePath());
        NpsCyclicImpl nps = new NpsCyclicImpl();
        nps.init();
        nps.run();
    }

}

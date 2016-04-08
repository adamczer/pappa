package juav.simulator.nps.cyclic;

import jniexample.juav.NativeHelloworld;
import juav.simulator.nps.AbstractNpsImpl;
import juav.simulator.tasks.ITask;
import juav.simulator.tasks.fdm.FlightDynamicModelJsbSim;
import juav.simulator.time.JodaTimeHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamczer on 1/24/16.
 */
public class NpsCyclicImpl extends AbstractNpsImpl {

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
            for (ITask task : tasks) {
                if (task.isAvailiable()) {
                    task.execute();
                }
            }
        }
    }

    /**
     * creates periodic tasks and ensures that they exist in the list
     * in the order they should execute.
     */
    @Override
    public void init() {
        timeHandler = new JodaTimeHandler();
        List<ITask> taskList = new ArrayList<>();
//        TODO
        // Atmosphere // not needed

        // Auto pilot time sync

        // send commands computed in previous iter
        // and FDM poll params to fdm object needed to populate sensors
        int sysTimeFreq = 2*512;
        double simDt = 1/sysTimeFreq;
        FlightDynamicModelJsbSim flightDynamicModelJsbSim = new FlightDynamicModelJsbSim();
        flightDynamicModelJsbSim.setTimeStep(simDt);
        taskList.add(flightDynamicModelJsbSim);


        // Sensors populate sensor values from FDM

        // Autopilot Compute commands to be sent to jsb sim based on current state


        // Initialize all tasks
        for (ITask task:taskList) {
            System.out.println("sfdsfsdfds");
            task.init();
        }

        setTasks(taskList);

//        init c++ jni things

    }

    public static void main(String[] args) {
        File lib = new File("libpapa_native.so");
        System.load(lib.getAbsolutePath());
        File pprzLib = new File("libpprz.so");
        System.load(pprzLib.getAbsolutePath());
        NativeHelloworld.nativePrint1("1:helloworld");
        NativeHelloworld.nativePrint2("2:helloworld");
        NpsCyclicImpl nps = new NpsCyclicImpl();
        nps.init();
        nps.run();
    }

}

package juav.simulator.nps.cyclic;

import javax.realtime.PeriodicParameters;
import javax.realtime.PriorityParameters;
import javax.realtime.RealtimeThread;
import javax.realtime.RelativeTime;
import java.util.concurrent.atomic.AtomicBoolean;

public class NoisyApp extends RealtimeThread {
    public static final int NoisyThreadPriority = 11;
    public static final long NoisyThreadPeriodic = 200; //ms
    public NoisyApp() {
    }

    public void run() {
        do {
            if(System.currentTimeMillis() %2 == 0) {
                //Gregory-Leibniz series for 2000 iterations
                System.out.println("NoisyApp Computaion start");
                double pi = 0.0f;
                double denominator = 1;
                int count = 2000;
                for (int x = 0; x < count; x++) {
                    if (x % 2 == 0) {
                        pi = pi + (1 / denominator);
                    } else {
                        pi = pi - (1 / denominator);
                    }
                    denominator = denominator + 2;
                }
                pi = pi * 4;
                System.out.println("NoisyApp Computaion end");
            }
            else /*GARBAGE PRESSURE*/{
                System.out.println("NoisyApp Garbage Pressure start");
                //allocate 512 * 4 bytes
                int[] localArrary = new int[512];
                for (int i = 0; i < localArrary.length; i++)
                    localArrary[i] = 100;
                System.out.println("NoisyApp Garbage Pressure end");
            }
        } while (RealtimeThread.currentRealtimeThread().waitForNextPeriod());
    }

    public static void main(String[] args) {
        System.out.println("Start the NoisyApp Executions...");
        RealtimeThread app = new NoisyApp();
        RelativeTime relativeTime = new RelativeTime(NoisyThreadPeriodic, 0);
        app.setSchedulingParameters(new PriorityParameters(NoisyThreadPriority));
        app.setReleaseParameters(new PeriodicParameters(relativeTime));
        app.start();
        System.out.println("Finish the NoisyApp Executions...");
    }
}

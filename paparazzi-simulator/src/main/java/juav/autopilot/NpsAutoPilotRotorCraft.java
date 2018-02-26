package juav.autopilot;
import juav.logging.JiveStateLog;
import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.sensors.device.jni.*;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;
import ub.cse.juav.jni.tasks.NativeTasks;
import ub.cse.juav.jni.tasks.NativeTasksWrapper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import jive.StateTransitions;

/**
 * Created by adamczer on 5/30/16.
 */
public class NpsAutoPilotRotorCraft extends PeriodicTask {
    JniGyroSensor gyroSensor;
    JniMagSensor magSensor;
    JniGpsSensor gpsSensor;
    JniAccelSensor accelSensor;
    JniBaroSensor baroSensor;
    JniImuNps jniImuNps;
    GpsSimNps gpsSimNps;
    Autopilot autopilot;

    String sensorState;
    int counter = 0;
    @Override
    public void execute() {
        double time = PaparazziNpsWrapper.getNpsMainSimTime();
        //StateTransitions.instance.add_transition(new String[]{"Run NpsAutopilot"});
        npsAutopilotRunStep(time);
    }

    private void npsAutopilotRunStep(double time) {
    	sensorState = "nps_autopilot_run_step";
        // Bfore BCI JiveStateLog.setsensorState(sensorState);
        //Not needed it should decrement battery
        NativeTasksWrapper.npsElectricalRunStep(time);


        if(NativeTasksWrapper.npsAutopilotRunRadioStepAndShouldRunMainEvent(time)) {
            main_event();
        }

        
//        NativeTasksWrapper.npsSensorFeedStepGyro();
        if (gyroSensor.getData().isData_available()) {
        	sensorState = "gyroSensor reading available";
        	JiveStateLog.setsensorState(sensorState);
            jniImuNps.imuFeedGyro(gyroSensor.getData());
            //StateTransitions.instance.add_transition(new String[]{"Feed Sensor Values"});
            //StateTransitions.instance.add_transition(new String[]{"Feed Gyro"});
            main_event();
            gyroSensor.getData().setData_available(false);
        }

//        NativeTasksWrapper.npsSensorFeedStepAccel();
        if (accelSensor.getData().isData_available()) {
        	sensorState = "accelSensor reading available";
        	JiveStateLog.setsensorState(sensorState);
            jniImuNps.imuFeedAccel(accelSensor.getData());
           // StateTransitions.instance.add_transition(new String[]{"Feed Sensor Values"});
            //StateTransitions.instance.add_transition(new String[]{"Feed Accel"});
            main_event();
            accelSensor.getData().setData_available(false);
        }


//        NativeTasksWrapper.npsSensorFeedStepMag();
        if (magSensor.getData().isData_available()) {
        	sensorState = "magSensor reading available";
        	JiveStateLog.setsensorState(sensorState);
            jniImuNps.imuFeedMag(magSensor.getData());
            //StateTransitions.instance.add_transition(new String[]{"Feed Sensor Values"});
            //StateTransitions.instance.add_transition(new String[]{"Feed Magnometer"});
            main_event();
            magSensor.getData().setData_available(false);
        }

//        NativeTasksWrapper.npsSensorFeedStepBaro();
        if (baroSensor.getData().isData_available()) {
        	sensorState = "magSensor reading available";
        	JiveStateLog.setsensorState(sensorState);
            float pressure = (float) baroSensor.getData().getValue();
            NativeTasksWrapper.sendBarometricReading(pressure);
            //StateTransitions.instance.add_transition(new String[]{"F"});
            //StateTransitions.instance.add_transition(new String[]{"Feed Barometer"});
            main_event();
            baroSensor.getData().setData_available(false);
        }

        //not used
//            #if USE_SONAR
//            if (nps_sensors_sonar_available()) {
//                float dist = (float) sensors.sonar.value;
//                AbiSendMsgAGL(AGL_SONAR_NPS_ID, dist);
//
//                uint16_t foo = 0;
//                DOWNLINK_SEND_SONAR(DefaultChannel, DefaultDevice, &foo, &dist);
//
//                main_event();
//            }
//            #endif

//        NativeTasksWrapper.npsSensorFeedStepGps();
        if (gpsSensor.getData().isData_available()) {
        	sensorState = "gpsSensor reading available";
        	JiveStateLog.setsensorState(sensorState);
            gpsSimNps.gpsFeedValue(gpsSensor.getData());
            //StateTransitions.instance.add_transition(new String[]{"Feed GPS"});
            main_event();
            gpsSensor.getData().setData_available(false);
        }

        NativeTasksWrapper.npsAutopilotRunStepOverwriteAhrs();
        //StateTransitions.instance.add_transition(new String[]{"Overwrite AHRS"});
        NativeTasksWrapper.npsAutopilotRunStepOverwriteIns();
        //StateTransitions.instance.add_transition(new String[]{"Overwrite Ins"});
/***************************************************************/
        long mainPeriodicStart = System.nanoTime();
        
        if(NativeTasksWrapper.sysTimeCheckAndAckTimerMainPeriodicJuav()) {
        	//StateTransitions.instance.add_transition(new String[]{"Main Periodic"});
        	JiveStateLog.setBCIStateLog("Main Periodic");
            newControlLoop();
            NativeTasksWrapper.mainPeriodicJuavAutopilotPost();
//            NativeTasksWrapper.handlePeriodicTasksFollowingMainPeriodicJuav();
            long startTime = System.nanoTime();
            
            NativeTasksWrapper.npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();
            
           //Oct  StateTransitions.instance.add_transition(new String[]{"Commands to FDM"});
            long interval = (System.nanoTime()-startTime)/1000000; //In ms
            if(interval > 0){
            System.out.println("Commands to FDM: " +interval);
            JiveStateLog.setBCIStateLog("Commands to FDM");
            }
            
        }
        
        long mainPeriodicEnd = System.nanoTime();
        try {
            fis.write((""+(iterationCount++)+" "+mainPeriodicStart+" "+mainPeriodicEnd+" "+(mainPeriodicEnd-mainPeriodicStart)+"\n").getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(NativeTasksWrapper.handelPeriodicTaskModulesJuav()) {
            //TODO true
        	JiveStateLog.setBCIStateLog("HwWatchDogs");
        	//StateTransitions.instance.add_transition(new String[]{"HwWatchDogs"});
        }
        if(NativeTasksWrapper.handelPeriodicTaskRadioJuav()) {
            //TODO true
        	JiveStateLog.setBCIStateLog("Radio Control");
        	//StateTransitions.instance.add_transition(new String[]{"Radio Control"});
        }
        if(NativeTasksWrapper.handelPeriodicTaskFailsafeJuav()) {
            //TODO true
        	JiveStateLog.setBCIStateLog("FailSafe");
        	//StateTransitions.instance.add_transition(new String[]{"FailSafe"});
        }
        if(NativeTasksWrapper.handelPeriodicTaskElectricalJuav()) {
            //TODO true
        	JiveStateLog.setBCIStateLog("Electrical");
        	//StateTransitions.instance.add_transition(new String[]{"Electrical"});
        }
        if(NativeTasksWrapper.handelPeriodicTaskTelemetryJuav()) {
            //TODO true
        	JiveStateLog.setBCIStateLog("Telemetry");
        	//StateTransitions.instance.add_transition(new String[]{"Telemetry"});
        }
        if(NativeTasksWrapper.handelPeriodicTaskBaroJuav()) {
            //TODO true
        }
        
        
//        oldControlLoop();
    }

    private void oldControlLoop() {
        NativeTasksWrapper.mainPeriodicJuavAutopilotPrior();
        if(NativeTasksWrapper.sysTimeCheckAndAckTimerMainPeriodicJuav()) {
//            NativeTasksWrapper.mainPeriodicJuavTest();//TEST main periodic
            NativeTasksWrapper.autopilotPeriodicPriorJuav();
            if(!NativeTasksWrapper.isAutopilotModeApModeKillJuav()) {
                boolean inFlight = NativeTasksWrapper.getAutopilotInFlightJuav();
                NativeTasksWrapper.guidanceHRunJuav(inFlight);
                if(NativeTasksWrapper.runStabilizationAttitudeRunJuav()) {
                    NativeTasksWrapper.guidanceHRunNativeTestJuav(inFlight); // test plumbing
//                    NativeTasksWrapper.guidanceHRunJuav(inFlight);
//                    stabilization_attitude_run_old(inFlight);
                }
            }
            NativeTasksWrapper.autopilotPeriodicPostJuav();//finaizes after guidance_h.c run
        }
    }

    private void newControlLoop() {
    	sensorState = "AP ControlLoop";
    	JiveStateLog.setsensorState(sensorState);
    	//StateTransitions.instance.add_transition(new String[]{"Autopilot"});
    	//StateTransitions.instance.add_iteration("AutoPilot");
    	long startTime = System.nanoTime();
        autopilot.autopilot_periodic();
        long interval = (System.nanoTime()-startTime)/1000000; //In ms
        if(interval > 0){
        System.out.println("Autopilot: " +interval);
        }
//        NativeTasksWrapper.juavAutopilotPeriodic();
    }

    private void main_event() {
        PaparazziNpsWrapper.mainEventPrior();
        //sensorState = "nps_autopilot_main_event";
        autopilot.autopilot_on_rc_frame();
        PaparazziNpsWrapper.mainEventPost();
//        PaparazziNpsWrapper.mainEvent();
    }

    private FileOutputStream fis;
    private long iterationCount;
    @Override
    public void init() {
    	
    	 sensorState = "nps_autopilot_init";
         JiveStateLog.setsensorState(sensorState);
        
         iterationCount =0;
        try {
            fis = new FileOutputStream("main_periodic.log");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        autopilot = new Autopilot();
        autopilot.autopilot_init();
    }

    public void setGyroSensor(JniGyroSensor gyroSensor) {
    	sensorState = "nps_setGyroSensor";
        this.gyroSensor = gyroSensor;
    }

    public void setMagSensor(JniMagSensor magSensor) {
    	sensorState = "nps_setMagSensor";
        this.magSensor = magSensor;
    }

    public void setGpsSensor(JniGpsSensor gpsSensor) {
    	sensorState = "nps_setGpsSensor";
        this.gpsSensor = gpsSensor;
    }

    public void setAccelSensor(JniAccelSensor accelSensor) {
    	sensorState = "nps_setAccelSensor";
        this.accelSensor = accelSensor;
    }

    public void setJniImuNps(JniImuNps jniImuNps) {
    	sensorState = "nps_setJniImuNps";
        this.jniImuNps = jniImuNps;
    }

    public void setGpsSimNps(GpsSimNps gpsSimNps) {
    	sensorState = "nps_setGpsSimNps";
        this.gpsSimNps = gpsSimNps;
    }

    public void setBaroSensor(JniBaroSensor baroSensor) {
    	sensorState = "nps_setBaroSensor";
        this.baroSensor = baroSensor;
    }
}

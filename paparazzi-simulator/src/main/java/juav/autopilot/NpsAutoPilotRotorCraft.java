package juav.autopilot;

import juav.autopilot.commands.Commands;
import juav.autopilot.gps.GpsSimNps;
import juav.autopilot.imu.JniImuNps;
import juav.autopilot.guidance.GuidanceH;
import juav.autopilot.stabilization.Stabilization;
import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.sensors.device.jni.*;
import ub.cse.juav.jni.nps.PaparazziNps;
import ub.cse.juav.jni.tasks.NativeTasks;

import static juav.autopilot.Autopilot.*;
import static juav.autopilot.commands.Commands.*;
import static juav.autopilot.guidance.GuidanceH.stabilizationAttitudeRun;

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

    @Override
    public void execute() {
        double time = PaparazziNps.getNpsMainSimTime();
        npsAutopilotRunStep(time);
    }

    private void npsAutopilotRunStep(double time) {
        //Not needed it should decrement battery
        NativeTasks.npsElectricalRunStep(time);


        if(NativeTasks.npsAutopilotRunRadioStepAndShouldRunMainEvent(time)) {
            main_event();
        }

//        NativeTasks.npsSensorFeedStepGyro();
        if (gyroSensor.getData().isData_available()) {
            jniImuNps.imuFeedGyro(gyroSensor.getData());
            main_event();
            gyroSensor.getData().setData_available(false);
        }

//        NativeTasks.npsSensorFeedStepAccel();
        if (accelSensor.getData().isData_available()) {
            jniImuNps.imuFeedAccel(accelSensor.getData());
            main_event();
            gyroSensor.getData().setData_available(false);
        }


//        NativeTasks.npsSensorFeedStepMag();
        if (magSensor.getData().isData_available()) {
            jniImuNps.imuFeedMag(magSensor.getData());
            main_event();
            magSensor.getData().setData_available(false);
        }

//        NativeTasks.npsSensorFeedStepBaro();
        if (baroSensor.getData().isData_available()) {
            float pressure = (float) baroSensor.getData().getValue();
            NativeTasks.sendBarometricReading(pressure);
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

//        NativeTasks.npsSensorFeedStepGps();
        if (gpsSensor.getData().isData_available()) {
            gpsSimNps.gpsFeedValue(gpsSensor.getData());
            main_event();
            gpsSensor.getData().setData_available(false);
        }


        NativeTasks.npsAutopilotRunStepOverwriteAhrs();

        NativeTasks.npsAutopilotRunStepOverwriteIns();
/***************************************************************/
//        NativeTasks.npsAutopilotRunStepHandelPeriodicTasks(); //-> all c for this task
//        below -> guidance attitude compuatation in java then passed back.
//        NativeTasks.mainPeriodicJuavAutopilotPrior();
//        if(NativeTasks.sysTimeCheckAndAckTimerMainPeriodicJuav()) {
//            NativeTasks.mainPeriodicJuavTest();//TEST main periodic
//            vvv First paper
//            NativeTasks.autopilotPeriodicPriorJuav();
//            if(!NativeTasks.isAutopilotModeApModeKillJuav()) {
//                boolean inFlight = NativeTasks.getAutopilotInFlightJuav();
////                NativeTasks.guidanceHRunJuav(inFlight);
//                if(NativeTasks.runStabilizationAttitudeRunJuav()) {
//                    NativeTasks.guidanceHRunNativeTestJuav(inFlight); // test plumbing
//                    NativeTasks.guidanceHRunJuav(inFlight);
//                    stabilizationAttitudeRun(inFlight);
////                    GuidanceH.stabilizationAttitudeRun(inFlight);
//                }
//            }
//            NativeTasks.autopilotPeriodicPostJuav();//finaizes after guidance_h.c run
//        }
        //          ^^^ First paper
//            Second paper

//            1. Autopilot flow
        autopilot.autopilot_periodic();
//            2. set commands
        NativeTasks.mainPeriodicJuavAutopilotPost();

        if (autopilot_mode != AP_MODE_KILL) {
            boolean inFlight =getAutopilotInFlight(), motorsOn =getAutopilotMotorsOn();
            int[] cmd = new int[4];
            cmd[COMMAND_ROLL] = Stabilization.getStabilizationCommand(COMMAND_ROLL);
            cmd[COMMAND_PITCH] = Stabilization.getStabilizationCommand(COMMAND_PITCH);
            cmd[COMMAND_YAW] = Stabilization.getStabilizationCommand(COMMAND_YAW);
            cmd[COMMAND_THRUST] = Stabilization.getStabilizationCommand(COMMAND_THRUST);
          System.out.println("COMMAND_ROLL ->"+ cmd[COMMAND_ROLL]);
          System.out.println("COMMAND_PITCH ->"+ cmd[COMMAND_PITCH]);
          System.out.println("COMMAND_YAW ->"+ cmd[COMMAND_YAW]);
          System.out.println("COMMAND_THRUST ->"+ cmd[COMMAND_THRUST]);
            Commands.SetRotorcraftCommands(cmd,inFlight,motorsOn );
        }
        NativeTasks.handlePeriodicTasksFollowingMainPeriodicJuav();
/***************************************************************/
        NativeTasks.npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands();
    }

    private void main_event() {
        PaparazziNps.mainEventPrior();
        autopilot.autopilot_on_rc_frame();
        PaparazziNps.mainEventPost();
    }


    @Override
    public void init() {
        autopilot = new Autopilot();
        autopilot.autopilot_init();
    }

    public void setGyroSensor(JniGyroSensor gyroSensor) {
        this.gyroSensor = gyroSensor;
    }

    public void setMagSensor(JniMagSensor magSensor) {
        this.magSensor = magSensor;
    }

    public void setGpsSensor(JniGpsSensor gpsSensor) {
        this.gpsSensor = gpsSensor;
    }

    public void setAccelSensor(JniAccelSensor accelSensor) {
        this.accelSensor = accelSensor;
    }

    public void setJniImuNps(JniImuNps jniImuNps) {
        this.jniImuNps = jniImuNps;
    }

    public void setGpsSimNps(GpsSimNps gpsSimNps) {
        this.gpsSimNps = gpsSimNps;
    }

    public void setBaroSensor(JniBaroSensor baroSensor) {
        this.baroSensor = baroSensor;
    }
}

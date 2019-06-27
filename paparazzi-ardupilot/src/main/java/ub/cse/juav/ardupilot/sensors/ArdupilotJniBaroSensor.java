package ub.cse.juav.ardupilot.sensors;

import juav.simulator.nps.random.NpsRandom;
import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.device.jni.JniBaroSensor;
import juav.simulator.tasks.sensors.readings.BarometricReading;
import ub.cse.juav.ardupilot.ArdupilotBridge;
import ub.cse.juav.jni.fdm.FdmWrapper;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;
import ub.juav.airborne.math.functions.isa.Pprz_isa;

public class ArdupilotJniBaroSensor extends JniBaroSensor {
    private static final double NPS_BARO_NOISE_STD_DEV = 0;
    private static final double NPS_BARO_DT = 1./50.;// nps_sensor_params_default.h
    @Override
    protected void executePeriodic() {
//        double time = PaparazziNpsWrapper.getNpsMainSimTime();
//
////        NativeTasksWrapper.npsSensorFdmCopyBaro(time);if (true)return;
//        if(time<data.getNext_update()) {
//            return;
//        }

  /* pressure in Pascal TODO this was a float*/
        double tmp = Pprz_isa.pprz_isa_pressure_of_altitude(ArdupilotBridge.getGpsAltitude());
  /* add noise with std dev Pascal */
        data.setValue(tmp + NpsRandom.get_gaussian_noise() * data.getNoise_std_dev());

        data.setNext_update( data.getNext_update() + NPS_BARO_DT);
        data.setData_available(true);
    }

    @Override
    public void init() {
        // no barometer so using altitude from gps to approximate.
        ArdupilotBridge.initGps();
        data = new BarometricReading();
        data.setValue(0.);
        data.setNoise_std_dev(NPS_BARO_NOISE_STD_DEV);
        data.setNext_update(0);
        data.setData_available(false);
    }
}

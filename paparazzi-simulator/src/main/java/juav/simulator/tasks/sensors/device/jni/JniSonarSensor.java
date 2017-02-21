package juav.simulator.tasks.sensors.device.jni;

import juav.simulator.nps.random.NpsRandom;
import juav.simulator.tasks.sensors.ISensor;
import juav.simulator.tasks.sensors.readings.SonarReading;
import ub.cse.juav.jni.fdm.FdmWrapper;
import ub.cse.juav.jni.fdm.JniFdm;
import ub.cse.juav.jni.nps.PaparazziNpsWrapper;

/**
 * Created by adamczer on 5/25/16.
 */
public class JniSonarSensor extends ISensor<SonarReading>{
    private static final double NPS_SONAR_OFFSET = 0;
    private static final double NPS_SONAR_NOISE_STD_DEV = .01;
    private static final double NPS_SONAR_DT = (1./10.);
    @Override
    protected void executePeriodic() {
        double time = PaparazziNpsWrapper.getNpsMainSimTime();

        if(time<data.getNext_update())
            return;

  /* agl in meters */
        data.setValue(FdmWrapper.getFdmAgl()+data.getOffset());
  /* add noise with std dev meters */
        data.setValue(data.getValue() + NpsRandom.get_gaussian_noise() * data.getNoise_std_dev());

        data.setNext_update(data.getNext_update()+ NPS_SONAR_DT);
        data.setData_available(true);
    }

    @Override
    public void init() {
        data = new SonarReading();
        data.setValue(0.);
        data.setOffset(NPS_SONAR_OFFSET);
        data.setNoise_std_dev(NPS_SONAR_NOISE_STD_DEV);
        data.setNext_update(0);
        data.setData_available(false);
    }
}

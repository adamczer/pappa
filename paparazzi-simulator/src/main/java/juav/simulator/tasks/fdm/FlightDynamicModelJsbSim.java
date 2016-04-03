package juav.simulator.tasks.fdm;

import juav.simulator.tasks.PeriodicTask;
import juav.simulator.tasks.fdm.data.FlightData;
import ub.cse.juav.jni.fdm.JniFdm;
import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.structs.geodetic.EcefCoor;

/**
 * Created by adamczer on 3/1/16.
 */
public class FlightDynamicModelJsbSim extends PeriodicTask {
    FlightData fdm;
    // Offset between ecef in geodetic and geocentric coordinates
    private EcefCoor<Double> offset;
    double minDt;
    private double timeStep;
    /** Minimum JSBSim timestep
     * Around 1/10000 seems to be good for ground impacts
     */
    private static final double MIN_DT = (1.0/10240.0);

    public FlightDynamicModelJsbSim() {
    }

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

    /**
     * Initializes JSBSim.
     *
     * Sets up the JSBSim executive and loads initial conditions
     * Exits NPS with -1 if models or ICs fail to load
     *
     * @param dt   The desired simulation timestep
     *
     * @warning Needs PAPARAZZI_HOME defined to find the config files
     */
    private void initJsbsim(double dt) {
        String rootDir = System.getenv("PAPARAZZI_HOME");
        System.out.println("PAPARAZZI_HOME="+rootDir);
        System.out.println("calling JNI");
        JniFdm.FGFDMExecInit();
        System.out.println("called JNI");
    }

    @Override
    public void execute() {

    }

    @Override
    public void init() {
        fdm = new FlightData();
        fdm.setInitDt(timeStep);
        fdm.setCurrDt(timeStep);
        //Sets up the high fidelity timestep as a multiple of the normal timestep
        for (minDt = (1.0 / timeStep); minDt < (1 / MIN_DT); minDt += (1 / timeStep)) {
        }
        minDt = (1 / minDt);
        fdm.setNanCount(0);
        offset = new EcefCoor<>();
        PprzAlgebra.VECT3_ASSIGN(offset, 0., 0., 0.);

        initJsbsim(timeStep);

//        FDMExec->RunIC();
//
//        init_ltp();

//        System.out.println("ssssssssss");
//        JniFdm.init();
    }
}

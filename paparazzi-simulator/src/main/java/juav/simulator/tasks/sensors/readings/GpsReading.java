package juav.simulator.tasks.sensors.readings;

import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.structs.algebra.Vect3;
import ub.juav.airborne.math.structs.geodetic.EcefCoor;
import ub.juav.airborne.math.structs.geodetic.LlaCoor;

/**
 * Created by adamczer on 2/29/16.
 */
public class GpsReading {
    EcefCoor<Double> ecef_pos ;
    EcefCoor<Double> ecef_vel;
    LlaCoor<Double>  lla_pos;
    double hmsl;
    Vect3<Double> pos_noise_std_dev;
    Vect3<Double>  speed_noise_std_dev;
    Vect3<Double>  pos_bias_initial;
    Vect3<Double>  pos_bias_random_walk_std_dev;
    Vect3<Double>  pos_bias_random_walk_value;
    double pos_latency;
    double speed_latency;
//    GSList *hmsl_history; TODO I dont think this is used
//    GSList *pos_history;  TODO I dont think this is used
//    GSList *lla_history;  TODO I dont think this is used
//    GSList *speed_history;TODO I dont think this is used
    double next_update;
    boolean data_available;

    public GpsReading() {
        ecef_pos = new EcefCoor<>();
        PprzAlgebra.VECT3_ASSIGN(ecef_pos,0.d,0.d,0.d);
        ecef_vel = new EcefCoor<>();
        PprzAlgebra.VECT3_ASSIGN(ecef_vel,0.d,0.d,0.d);
        lla_pos = new LlaCoor<>();
        lla_pos.setAlt(0.d);
        lla_pos.setLat(0.d);
        lla_pos.setLon(0.d);

        double hmsl = 0.d;
        pos_noise_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(pos_noise_std_dev,0.d,0.d,0.d);
        speed_noise_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(speed_noise_std_dev,0.d,0.d,0.d);

        pos_bias_initial = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(pos_bias_initial,0.d,0.d,0.d);
        pos_bias_random_walk_std_dev = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(pos_bias_random_walk_std_dev,0.d,0.d,0.d);
        pos_bias_random_walk_value = new Vect3<>();
        PprzAlgebra.VECT3_ASSIGN(pos_bias_random_walk_value,0.d,0.d,0.d);
        double pos_latency = 0.d;
        double speed_latency = 0.d;
//    GSList *hmsl_history; TODO I dont think this is used
//    GSList *pos_history;  TODO I dont think this is used
//    GSList *lla_history;  TODO I dont think this is used
//    GSList *speed_history;TODO I dont think this is used
        double next_update = 0.d;
        boolean data_available = false;
    }

    public EcefCoor<Double> getEcef_pos() {
        return ecef_pos;
    }

    public void setEcef_pos(EcefCoor<Double> ecef_pos) {
        this.ecef_pos = ecef_pos;
    }

    public EcefCoor<Double> getEcef_vel() {
        return ecef_vel;
    }

    public void setEcef_vel(EcefCoor<Double> ecef_vel) {
        this.ecef_vel = ecef_vel;
    }

    public LlaCoor<Double> getLla_pos() {
        return lla_pos;
    }

    public void setLla_pos(LlaCoor<Double> lla_pos) {
        this.lla_pos = lla_pos;
    }

    public double getHmsl() {
        return hmsl;
    }

    public void setHmsl(double hmsl) {
        this.hmsl = hmsl;
    }

    public Vect3<Double> getPos_noise_std_dev() {
        return pos_noise_std_dev;
    }

    public void setPos_noise_std_dev(Vect3<Double> pos_noise_std_dev) {
        this.pos_noise_std_dev = pos_noise_std_dev;
    }

    public Vect3<Double> getSpeed_noise_std_dev() {
        return speed_noise_std_dev;
    }

    public void setSpeed_noise_std_dev(Vect3<Double> speed_noise_std_dev) {
        this.speed_noise_std_dev = speed_noise_std_dev;
    }

    public Vect3<Double> getPos_bias_initial() {
        return pos_bias_initial;
    }

    public void setPos_bias_initial(Vect3<Double> pos_bias_initial) {
        this.pos_bias_initial = pos_bias_initial;
    }

    public Vect3<Double> getPos_bias_random_walk_std_dev() {
        return pos_bias_random_walk_std_dev;
    }

    public void setPos_bias_random_walk_std_dev(Vect3<Double> pos_bias_random_walk_std_dev) {
        this.pos_bias_random_walk_std_dev = pos_bias_random_walk_std_dev;
    }

    public Vect3<Double> getPos_bias_random_walk_value() {
        return pos_bias_random_walk_value;
    }

    public void setPos_bias_random_walk_value(Vect3<Double> pos_bias_random_walk_value) {
        this.pos_bias_random_walk_value = pos_bias_random_walk_value;
    }

    public double getPos_latency() {
        return pos_latency;
    }

    public void setPos_latency(double pos_latency) {
        this.pos_latency = pos_latency;
    }

    public double getSpeed_latency() {
        return speed_latency;
    }

    public void setSpeed_latency(double speed_latency) {
        this.speed_latency = speed_latency;
    }

    public double getNext_update() {
        return next_update;
    }

    public void setNext_update(double next_update) {
        this.next_update = next_update;
    }

    public boolean isData_available() {
        return data_available;
    }

    public void setData_available(boolean data_available) {
        this.data_available = data_available;
    }
}

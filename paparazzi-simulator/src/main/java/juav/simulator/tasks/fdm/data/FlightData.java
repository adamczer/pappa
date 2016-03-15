package juav.simulator.tasks.fdm.data;

import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.Rates;
import ub.juav.airborne.math.structs.algebra.Vect3;
import ub.juav.airborne.math.structs.geodetic.EcefCoor;
import ub.juav.airborne.math.structs.geodetic.LlaCoor;
import ub.juav.airborne.math.structs.geodetic.NedCoor;

/**
 * Created by adamczer on 3/1/16.
 */
public class FlightData {
    public static final int FG_NET_FDM_MAX_ENGINES = 4;

    double time;
    double initDt;
    double currDt;
    boolean onGround;
    int nanCount;
    /*position*/
    EcefCoor<Double> ecefPos;
    NedCoor<Double> ltpprzPos;
    LlaCoor<Double> llaPos;
    double hmsl;
    /* For Debugging */
    LlaCoor<Double> llaPosPprz; //lla converted by pprz from ecef
    LlaCoor<Double> llaPosGeod; //geodetic lla from jsbsim
    LlaCoor<Double> llaPosGeoc; //geocentric lla from jsbsim
    double agl; //AGL from jsbsim in m

    /** velocity in ECEF frame, wrt ECEF frame */
    EcefCoor<Double>  ecef_ecef_vel;
    /** acceleration in ECEF frame, wrt ECEF frame */
    EcefCoor<Double>  ecef_ecef_accel;

    /** velocity in body frame, wrt ECEF frame */
    Vect3<Double> body_ecef_vel;   /* aka UVW */
    /** acceleration in body frame, wrt ECEF frame */
    Vect3<Double> body_ecef_accel;

    /** velocity in LTP frame, wrt ECEF frame */
    NedCoor<Double> ltp_ecef_vel;
    /** acceleration in LTP frame, wrt ECEF frame */
    NedCoor<Double> ltp_ecef_accel;

    /** velocity in ltppprz frame, wrt ECEF frame */
    NedCoor<Double> ltpprz_ecef_vel;
    /** accel in ltppprz frame, wrt ECEF frame */
    NedCoor<Double> ltpprz_ecef_accel;

    /** acceleration in body frame, wrt ECI inertial frame */
    Vect3<Double> body_inertial_accel;

    /** acceleration in body frame as measured by an accelerometer (incl. gravity) */
    Vect3<Double> body_accel;

    /* attitude */
    Quat<Double> ecef_to_body_quat;
    Quat<Double>   ltp_to_body_quat;
    Eulers<Double> ltp_to_body_eulers;
    Quat<Double>   ltpprz_to_body_quat;
    Eulers<Double> ltpprz_to_body_eulers;

    /*  angular velocity and acceleration in body frame, wrt ECEF frame */
    Rates<Double> body_ecef_rotvel;
    Rates<Double>  body_ecef_rotaccel;

    /*  angular velocity and acceleration in body frame, wrt inertial ECI frame */
    Rates<Double>  body_inertial_rotvel;
    Rates<Double>  body_inertial_rotaccel;

    Vect3<Double> ltp_g;
    Vect3<Double> ltp_h;

    Vect3<Double> wind; ///< velocity in m/s in NED

    // Control surface positions (normalized values)
    float elevator;
    float flap;
    float left_aileron;
    float right_aileron;
    float rudder;

    //engine state for first engine
    long num_engines;
    long[] eng_state = new long[FG_NET_FDM_MAX_ENGINES];// Engine state (off, cranking, running)
    float[] rpm = new float[FG_NET_FDM_MAX_ENGINES];       // Engine RPM rev/min

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getInitDt() {
        return initDt;
    }

    public void setInitDt(double initDt) {
        this.initDt = initDt;
    }

    public double getCurrDt() {
        return currDt;
    }

    public void setCurrDt(double currDt) {
        this.currDt = currDt;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }

    public int getNanCount() {
        return nanCount;
    }

    public void setNanCount(int nanCount) {
        this.nanCount = nanCount;
    }

    public EcefCoor<Double> getEcefPos() {
        return ecefPos;
    }

    public void setEcefPos(EcefCoor<Double> ecefPos) {
        this.ecefPos = ecefPos;
    }

    public NedCoor<Double> getLtpprzPos() {
        return ltpprzPos;
    }

    public void setLtpprzPos(NedCoor<Double> ltpprzPos) {
        this.ltpprzPos = ltpprzPos;
    }

    public LlaCoor<Double> getLlaPos() {
        return llaPos;
    }

    public void setLlaPos(LlaCoor<Double> llaPos) {
        this.llaPos = llaPos;
    }

    public double getHmsl() {
        return hmsl;
    }

    public void setHmsl(double hmsl) {
        this.hmsl = hmsl;
    }

    public LlaCoor<Double> getLlaPosPprz() {
        return llaPosPprz;
    }

    public void setLlaPosPprz(LlaCoor<Double> llaPosPprz) {
        this.llaPosPprz = llaPosPprz;
    }

    public LlaCoor<Double> getLlaPosGeod() {
        return llaPosGeod;
    }

    public void setLlaPosGeod(LlaCoor<Double> llaPosGeod) {
        this.llaPosGeod = llaPosGeod;
    }

    public LlaCoor<Double> getLlaPosGeoc() {
        return llaPosGeoc;
    }

    public void setLlaPosGeoc(LlaCoor<Double> llaPosGeoc) {
        this.llaPosGeoc = llaPosGeoc;
    }

    public double getAgl() {
        return agl;
    }

    public void setAgl(double agl) {
        this.agl = agl;
    }

    public EcefCoor<Double> getEcef_ecef_vel() {
        return ecef_ecef_vel;
    }

    public void setEcef_ecef_vel(EcefCoor<Double> ecef_ecef_vel) {
        this.ecef_ecef_vel = ecef_ecef_vel;
    }

    public EcefCoor<Double> getEcef_ecef_accel() {
        return ecef_ecef_accel;
    }

    public void setEcef_ecef_accel(EcefCoor<Double> ecef_ecef_accel) {
        this.ecef_ecef_accel = ecef_ecef_accel;
    }

    public Vect3<Double> getBody_ecef_vel() {
        return body_ecef_vel;
    }

    public void setBody_ecef_vel(Vect3<Double> body_ecef_vel) {
        this.body_ecef_vel = body_ecef_vel;
    }

    public Vect3<Double> getBody_ecef_accel() {
        return body_ecef_accel;
    }

    public void setBody_ecef_accel(Vect3<Double> body_ecef_accel) {
        this.body_ecef_accel = body_ecef_accel;
    }

    public NedCoor<Double> getLtp_ecef_vel() {
        return ltp_ecef_vel;
    }

    public void setLtp_ecef_vel(NedCoor<Double> ltp_ecef_vel) {
        this.ltp_ecef_vel = ltp_ecef_vel;
    }

    public NedCoor<Double> getLtp_ecef_accel() {
        return ltp_ecef_accel;
    }

    public void setLtp_ecef_accel(NedCoor<Double> ltp_ecef_accel) {
        this.ltp_ecef_accel = ltp_ecef_accel;
    }

    public NedCoor<Double> getLtpprz_ecef_vel() {
        return ltpprz_ecef_vel;
    }

    public void setLtpprz_ecef_vel(NedCoor<Double> ltpprz_ecef_vel) {
        this.ltpprz_ecef_vel = ltpprz_ecef_vel;
    }

    public NedCoor<Double> getLtpprz_ecef_accel() {
        return ltpprz_ecef_accel;
    }

    public void setLtpprz_ecef_accel(NedCoor<Double> ltpprz_ecef_accel) {
        this.ltpprz_ecef_accel = ltpprz_ecef_accel;
    }

    public Vect3<Double> getBody_inertial_accel() {
        return body_inertial_accel;
    }

    public void setBody_inertial_accel(Vect3<Double> body_inertial_accel) {
        this.body_inertial_accel = body_inertial_accel;
    }

    public Vect3<Double> getBody_accel() {
        return body_accel;
    }

    public void setBody_accel(Vect3<Double> body_accel) {
        this.body_accel = body_accel;
    }

    public Quat<Double> getEcef_to_body_quat() {
        return ecef_to_body_quat;
    }

    public void setEcef_to_body_quat(Quat<Double> ecef_to_body_quat) {
        this.ecef_to_body_quat = ecef_to_body_quat;
    }

    public Quat<Double> getLtp_to_body_quat() {
        return ltp_to_body_quat;
    }

    public void setLtp_to_body_quat(Quat<Double> ltp_to_body_quat) {
        this.ltp_to_body_quat = ltp_to_body_quat;
    }

    public Eulers<Double> getLtp_to_body_eulers() {
        return ltp_to_body_eulers;
    }

    public void setLtp_to_body_eulers(Eulers<Double> ltp_to_body_eulers) {
        this.ltp_to_body_eulers = ltp_to_body_eulers;
    }

    public Quat<Double> getLtpprz_to_body_quat() {
        return ltpprz_to_body_quat;
    }

    public void setLtpprz_to_body_quat(Quat<Double> ltpprz_to_body_quat) {
        this.ltpprz_to_body_quat = ltpprz_to_body_quat;
    }

    public Eulers<Double> getLtpprz_to_body_eulers() {
        return ltpprz_to_body_eulers;
    }

    public void setLtpprz_to_body_eulers(Eulers<Double> ltpprz_to_body_eulers) {
        this.ltpprz_to_body_eulers = ltpprz_to_body_eulers;
    }

    public Rates<Double> getBody_ecef_rotvel() {
        return body_ecef_rotvel;
    }

    public void setBody_ecef_rotvel(Rates<Double> body_ecef_rotvel) {
        this.body_ecef_rotvel = body_ecef_rotvel;
    }

    public Rates<Double> getBody_ecef_rotaccel() {
        return body_ecef_rotaccel;
    }

    public void setBody_ecef_rotaccel(Rates<Double> body_ecef_rotaccel) {
        this.body_ecef_rotaccel = body_ecef_rotaccel;
    }

    public Rates<Double> getBody_inertial_rotvel() {
        return body_inertial_rotvel;
    }

    public void setBody_inertial_rotvel(Rates<Double> body_inertial_rotvel) {
        this.body_inertial_rotvel = body_inertial_rotvel;
    }

    public Rates<Double> getBody_inertial_rotaccel() {
        return body_inertial_rotaccel;
    }

    public void setBody_inertial_rotaccel(Rates<Double> body_inertial_rotaccel) {
        this.body_inertial_rotaccel = body_inertial_rotaccel;
    }

    public Vect3<Double> getLtp_g() {
        return ltp_g;
    }

    public void setLtp_g(Vect3<Double> ltp_g) {
        this.ltp_g = ltp_g;
    }

    public Vect3<Double> getLtp_h() {
        return ltp_h;
    }

    public void setLtp_h(Vect3<Double> ltp_h) {
        this.ltp_h = ltp_h;
    }

    public Vect3<Double> getWind() {
        return wind;
    }

    public void setWind(Vect3<Double> wind) {
        this.wind = wind;
    }

    public float getElevator() {
        return elevator;
    }

    public void setElevator(float elevator) {
        this.elevator = elevator;
    }

    public float getFlap() {
        return flap;
    }

    public void setFlap(float flap) {
        this.flap = flap;
    }

    public float getLeft_aileron() {
        return left_aileron;
    }

    public void setLeft_aileron(float left_aileron) {
        this.left_aileron = left_aileron;
    }

    public float getRight_aileron() {
        return right_aileron;
    }

    public void setRight_aileron(float right_aileron) {
        this.right_aileron = right_aileron;
    }

    public float getRudder() {
        return rudder;
    }

    public void setRudder(float rudder) {
        this.rudder = rudder;
    }

    public long getNum_engines() {
        return num_engines;
    }

    public void setNum_engines(long num_engines) {
        this.num_engines = num_engines;
    }

    public long[] getEng_state() {
        return eng_state;
    }

    public void setEng_state(long[] eng_state) {
        this.eng_state = eng_state;
    }

    public float[] getRpm() {
        return rpm;
    }

    public void setRpm(float[] rpm) {
        this.rpm = rpm;
    }
}

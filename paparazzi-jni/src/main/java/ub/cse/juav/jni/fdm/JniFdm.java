package ub.cse.juav.jni.fdm;

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
public class JniFdm {

    public static native double fetchTime();
    public static native float fetchRudder();
    public static native float fetchLeftAileron();
    public static native float fetchRightAileron();
    public static native float fetchElevator();
    public static native float fetchFlap();
    public static native boolean fetchOnGround();
    public static native EcefCoor<Double> fetchEcefPos();
    public static native double fetchHmsl();
    public static native Vect3<Double> fetchBodyEcefVel();
    public static native Vect3<Double> fetchBodyEcefAccel();
    public static native Vect3<Double> fetchBodyInertialAccel();
    public static native Vect3<Double> fetchBodyAccel();
    public static native Vect3<Double> fetchLtpEcefVel();
    public static native Vect3<Double> fetchLtpEcefAccel();

    public static native EcefCoor<Double> fetchEcefEcefVel();
    public static native EcefCoor<Double> fetchEcefEcefAccel();

    public static native NedCoor<Double> fetchLtpprzPos();
    public static native NedCoor<Double> fetchLtpprzVel();
    public static native NedCoor<Double> fetchLtpprzAccel();

    public static native LlaCoor<Double> fetchLlaPos();
    public static native LlaCoor<Double> fetchLlaPosGeod();
    public static native LlaCoor<Double> fetchLlaPosGeoc();

    public static native LlaCoor<Double> fetchLlaPosPprz();
    public static native double fetchAgl();

    public static native Quat<Double> fetchLtpToBodyQuat();
    public static native Eulers<Double> fetchLtpToBodyEulers();

    public static native Eulers<Double> fetchLtpprzToBodyEulers();
    public static native Quat<Double> fetchLtpprzToBodyQuat();

    public static native Rates<Double> fetchBodyEcefRotvel();
    public static native Rates<Double> fetchBodyEcefRotaccel();
    public static native Rates<Double> fetchBodyEcefInertialRotvel();
    public static native Rates<Double> fetchBodyEcefInertialRotaccel();

    public static native Vect3<Double> fetchWind();

    public static native int fetchNumberOfEngines();

    public static native int[] fetchEngineState();

    public static native float[] fetchEngineRpm();











}

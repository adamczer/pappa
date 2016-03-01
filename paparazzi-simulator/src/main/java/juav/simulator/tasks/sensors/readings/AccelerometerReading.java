package juav.simulator.tasks.sensors.readings;

import ub.juav.airborne.math.structs.algebra.Vect3;

/**
 * Created by adamczer on 2/29/16.
 */
public class AccelerometerReading {
    double qnh;         ///< barometric pressure at sea level in Pascal
    double wind_speed;  ///< horizontal wind magnitude in m/s
    double wind_dir;    ///< horitzontal wind direction in radians north=0, increasing CCW
    Vect3<Double> wind; ///< wind speed in NED in m/s
    int turbulence_severity; ///< turbulence severity from 0-7
}

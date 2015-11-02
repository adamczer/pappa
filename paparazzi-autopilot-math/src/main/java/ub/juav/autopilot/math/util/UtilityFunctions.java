package ub.juav.autopilot.math.util;

/**
 * Created by adamczer on 9/9/15.
 */
public class UtilityFunctions{
    public static double RadOfDeg(double d) {
        return ((d) * (Constants.M_PI/180.));
    }

    public static double RAD_OF_EM7DEG(double _r) {
        return (RadOfDeg(_r)/1e7);
    }

    public static double M_OF_MM(double mm) {
        return mm/1000;
    }

    public static double M_OF_CM(double x) {
        return x/100;
    }

    public static double EM7DEG_OF_RAD(double rad) {
        return rad * 10000000;
    }


    public static double MM_OF_M(double m) {
        return m*1000;
    }


    public static double CM_OF_M(double m) {
        return m*100;
    }

    public static float DegOfRad(float rad) {
        return (float) (rad * (180. / Constants.M_PI));
    }

    public static float Bound(float _x, float _min, float _max) {
        if (_x > (_max))
            return (_max);
        else if (_x < (_min))
            return (_min);
        else return _x;
    }
}

package ub.juav.autopilot.math.functions.geodetic;

import ub.juav.autopilot.math.functions.algebra.PprzAlgebra;
import ub.juav.autopilot.math.structs.algebra.Vect3;
import ub.juav.autopilot.math.structs.algebra.doubles.DoubleVect3;
import ub.juav.autopilot.math.structs.geodetic.EcefCoor;
import ub.juav.autopilot.math.structs.geodetic.LlaCoor;
import ub.juav.autopilot.math.structs.geodetic.doubles.EcefCoorDouble;
import ub.juav.autopilot.math.structs.geodetic.doubles.LlaCoorDouble;
import ub.juav.autopilot.math.structs.geodetic.doubles.LtpDefDouble;

/**
 * Created by adamczer on 7/12/15.
 */
public class PprzGeodeticDouble {
    // h file defines only structs
    // begining c file

    void ltp_def_from_ecef_d(LtpDefDouble def, EcefCoorDouble ecef)
    {

  /* store the origin of the tangeant plane       */
        EcefCoorDouble v = new EcefCoorDouble();
        PprzAlgebra.VECT3_COPY(v, ecef);
        def.setEcefCoor(ecef);
  /* compute the lla representation of the origin */
        lla_of_ecef_d(def.getLlaCoor(), def.getEcefCoor());
  /* store the rotation matrix                    */
        double sin_lat = Math.sin(def.getLlaCoor().getLat().doubleValue());
        double cos_lat = Math.cos(def.getLlaCoor().getLat().doubleValue());
        double sin_lon = Math.sin(def.getLlaCoor().getLon().doubleValue());
        double cos_lon = Math.cos(def.getLlaCoor().getLon().doubleValue());
        def.getLtp_of_ecef().setFlattendElement(0,-sin_lon);
        def.getLtp_of_ecef().setFlattendElement(1,cos_lon);
        def.getLtp_of_ecef().setFlattendElement(2,0.);
        def.getLtp_of_ecef().setFlattendElement(3,-sin_lat * cos_lon);
        def.getLtp_of_ecef().setFlattendElement(4,-sin_lat * sin_lon);
        def.getLtp_of_ecef().setFlattendElement(5,cos_lat);
        def.getLtp_of_ecef().setFlattendElement(6,cos_lat * cos_lon);
        def.getLtp_of_ecef().setFlattendElement(7,cos_lat * sin_lon);
        def.getLtp_of_ecef().setFlattendElement(8,sin_lat);

    }

    /* http://en.wikipedia.org/wiki/Geodetic_system */
    void lla_of_ecef_d(LlaCoor<Double> lla, EcefCoor<Double> ecef)
    {

        // FIXME : make an ellipsoid struct
        double a = 6378137.0;           /* earth semimajor axis in meters */
        double f = 1. / 298.257223563;  /* reciprocal flattening          */
        double b = a * (1. - f);               /* semi-minor axis                */
        double b2 = b * b;

        double e2 = 2.*f - (f * f);            /* first eccentricity squared     */
        double ep2 = f * (2. - f) / ((1. - f) * (1. - f)); /* second eccentricity squared    */
        double E2 = a * a - b2;


        double z2 = ecef.getZ() * ecef.getZ();
        double r2 = ecef.getX() * ecef.getX() + ecef.getY() * ecef.getY();
        double r = Math.sqrt(r2);
        double F = 54.*b2 * z2;
        double G = r2 + (1 - e2) * z2 - e2 * E2;
        double c = (e2 * e2 * F * r2) / (G * G * G);
        double s = Math.pow((1 + c + Math.sqrt(c * c + 2 * c)), 1. / 3.);
        double s1 = 1 + s + 1 / s;
        double P = F / (3 * s1 * s1 * G * G);
        double Q = Math.sqrt(1 + 2 * e2 * e2 * P);
        double ro = -(e2 * P * r) / (1 + Q) + Math.sqrt((a * a / 2) * (1 + 1 / Q) - ((1 - e2) * P * z2) / (Q *
                (1 + Q)) - P * r2 / 2);
        double tmp = (r - e2 * ro) * (r - e2 * ro);
        double U = Math.sqrt(tmp + z2);
        double V = Math.sqrt(tmp + (1 - e2) * z2);
        double zo = (b2 * ecef.getZ()) / (a * V);

        lla.setAlt(U * (1 - b2 / (a * V)));
        lla.setLat(Math.atan((ecef.getZ() + ep2 * zo) / r));
        lla.setLon(Math.atan2(ecef.getY(), ecef.getX()));

    }
}

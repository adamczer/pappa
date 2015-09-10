package ub.juav.autopilot.math.functions.geodetic;

import ub.juav.autopilot.math.functions.algebra.PprzAlgebra;
import ub.juav.autopilot.math.structs.algebra.doubles.DoubleRMat;
import ub.juav.autopilot.math.structs.geodetic.EcefCoor;
import ub.juav.autopilot.math.structs.geodetic.LlaCoor;
import ub.juav.autopilot.math.structs.geodetic.doubles.EcefCoorDouble;
import ub.juav.autopilot.math.structs.geodetic.floats.*;
import ub.juav.autopilot.math.util.Complex;
import ub.juav.autopilot.math.util.Constants;

/**
 * Created by adamczer on 9/9/15.
 */
public class PprzGeodeticFloat {
    //h file only included structs

    public static void ltp_def_from_ecef_f(LtpDefFloat def, EcefCoorFloat ecef)
    {

  /* store the origin of the tangeant plane       */
        PprzAlgebra.VECT3_COPY(def.getEcefCoor(), ecef);
  /* compute the lla representation of the origin */
        lla_of_ecef_f(def.getLlaCoor(), def.getEcefCoor());
  /* store the rotation matrix                    */
        float sin_lat = (float) Math.sin(def.getLlaCoor().getLat());
        float cos_lat = (float) Math.cos(def.getLlaCoor().getLat());
        float sin_lon = (float) Math.sin(def.getLlaCoor().getLon());
        float cos_lon = (float) Math.cos(def.getLlaCoor().getLon());
        def.getLtp_of_ecef().setFlattendElement(0, -sin_lon);
        def.getLtp_of_ecef().setFlattendElement(1, cos_lon);
  /* this element is always zero http://en.wikipedia.org/wiki/Geodetic_system#From_ECEF_to_ENU */
        def.getLtp_of_ecef().setFlattendElement(2, (float) 0.0);
        def.getLtp_of_ecef().setFlattendElement(3,-sin_lat * cos_lon);
        def.getLtp_of_ecef().setFlattendElement(4,-sin_lat * sin_lon);
        def.getLtp_of_ecef().setFlattendElement(5, cos_lat);
        def.getLtp_of_ecef().setFlattendElement(6, cos_lat * cos_lon);
        def.getLtp_of_ecef().setFlattendElement(7, cos_lat * sin_lon);
        def.getLtp_of_ecef().setFlattendElement(8, sin_lat);

    }

    public static void ltp_def_from_lla_f(LtpDefFloat def, LlaCoorFloat lla)
    {
  /* store the origin of the tangeant plane */
        PprzGeodetic.LLA_COPY(def.getLlaCoor(), lla);
  /* compute the ecef representation of the origin */
        ecef_of_lla_f(def.getEcefCoor(), def.getLlaCoor());

  /* store the rotation matrix                    */
        float sin_lat = (float) Math.sin(def.getLlaCoor().getLat());
        float cos_lat = (float) Math.cos(def.getLlaCoor().getLat());
        float sin_lon = (float) Math.sin(def.getLlaCoor().getLon());
        float cos_lon = (float) Math.cos(def.getLlaCoor().getLon());

        def.getLtp_of_ecef().setFlattendElement(0, -sin_lon);
        def.getLtp_of_ecef().setFlattendElement(1,  cos_lon);
  /* this element is always zero http://en.wikipedia.org/wiki/Geodetic_system#From_ECEF_to_ENU */
        def.getLtp_of_ecef().setFlattendElement(2, (float) 0.);
        def.getLtp_of_ecef().setFlattendElement(3, -sin_lat * cos_lon);
        def.getLtp_of_ecef().setFlattendElement(4, -sin_lat * sin_lon);
        def.getLtp_of_ecef().setFlattendElement(5,  cos_lat);
        def.getLtp_of_ecef().setFlattendElement(6,  cos_lat * cos_lon);
        def.getLtp_of_ecef().setFlattendElement(7,  cos_lat * sin_lon);
        def.getLtp_of_ecef().setFlattendElement(8, sin_lat);
    }

    public static void enu_of_ecef_point_f(EnuCoorFloat enu, LtpDefFloat def, EcefCoorFloat ecef)
    {
        EcefCoorFloat delta = new EcefCoorFloat();
        PprzAlgebra.VECT3_DIFF(delta, ecef, def.getEcefCoor());
        PprzAlgebra.MAT33_VECT3_MULT(enu, def.getLtp_of_ecef(), delta);
    }

    public static void ned_of_ecef_point_f(NedCoorFloat ned, LtpDefFloat def, EcefCoorFloat ecef)
    {
        EnuCoorFloat enu = new EnuCoorFloat();
        enu_of_ecef_point_f(enu, def, ecef);
        PprzGeodetic.ENU_OF_TO_NED(ned, enu);
    }


    public static void enu_of_ecef_vect_f(EnuCoorFloat enu, LtpDefFloat def, EcefCoorFloat ecef)
    {
       PprzAlgebra.MAT33_VECT3_MULT(enu, def.getLtp_of_ecef(), ecef);
    }

    public static void ned_of_ecef_vect_f(NedCoorFloat ned, LtpDefFloat def, EcefCoorFloat ecef)
    {
        EnuCoorFloat enu = new EnuCoorFloat();
        enu_of_ecef_vect_f(enu, def, ecef);
        PprzGeodetic.ENU_OF_TO_NED(ned, enu);
    }

    public static void enu_of_lla_point_f(EnuCoorFloat enu, LtpDefFloat def, LlaCoorFloat lla)
    {
        EcefCoorFloat ecef = new EcefCoorFloat();
        ecef_of_lla_f(ecef, lla);
        enu_of_ecef_point_f(enu, def, ecef);
    }

    public static void ned_of_lla_point_f(NedCoorFloat ned, LtpDefFloat def, LlaCoorFloat lla)
    {
        EcefCoorFloat ecef = new EcefCoorFloat();
        ecef_of_lla_f(ecef, lla);
        ned_of_ecef_point_f(ned, def, ecef);
    }

    /*
     * not enought precision with float - use double
     */
    public static void ecef_of_enu_point_f(EcefCoorFloat ecef, LtpDefFloat def, EnuCoorFloat enu)
    {
  /* convert used floats to double */
        DoubleRMat ltp_of_ecef_d = new DoubleRMat();
        ltp_of_ecef_d.setFlattendElement(0,(double) def.getLtp_of_ecef().getFlattendElement(0));
        ltp_of_ecef_d.setFlattendElement(1, (double) def.getLtp_of_ecef().getFlattendElement(1));
        ltp_of_ecef_d.setFlattendElement(2, (double) def.getLtp_of_ecef().getFlattendElement(2));
        ltp_of_ecef_d.setFlattendElement(3, (double) def.getLtp_of_ecef().getFlattendElement(3));
        ltp_of_ecef_d.setFlattendElement(4, (double) def.getLtp_of_ecef().getFlattendElement(4));
        ltp_of_ecef_d.setFlattendElement(5, (double) def.getLtp_of_ecef().getFlattendElement(5));
        ltp_of_ecef_d.setFlattendElement(6, (double) def.getLtp_of_ecef().getFlattendElement(6));
        ltp_of_ecef_d.setFlattendElement(7, (double) def.getLtp_of_ecef().getFlattendElement(7));
        ltp_of_ecef_d.setFlattendElement(8, (double) def.getLtp_of_ecef().getFlattendElement(8));
        EnuCoorFloat enu_d = new EnuCoorFloat();
        enu_d.setX(enu.getX());
        enu_d.setY(enu.getY());
        enu_d.setZ(enu.getZ());

  /* compute in double */
        EcefCoorDouble ecef_d = new EcefCoorDouble();
        PprzAlgebra.MAT33_VECT3_TRANSP_MUL(ecef_d, ltp_of_ecef_d, enu_d);

  /* convert result back to float and add it*/
        ecef.setX((float) (ecef_d.getX() + def.getEcefCoor().getX()));
        ecef.setY((float) (ecef_d.getY() + def.getEcefCoor().getY()));
        ecef.setZ((float) (ecef_d.getZ() + def.getEcefCoor().getZ()));
    }

    public static void ecef_of_ned_point_f(EcefCoorFloat ecef, LtpDefFloat def, NedCoorFloat ned)
    {
        EnuCoorFloat enu = new EnuCoorFloat();
        PprzGeodetic.ENU_OF_TO_NED(enu, ned);
        ecef_of_enu_point_f(ecef, def, enu);
    }

    public static void ecef_of_enu_vect_f(EcefCoorFloat ecef, LtpDefFloat def, EnuCoorFloat enu)
    {
  /* convert used floats to double */
        DoubleRMat ltp_of_ecef_d = new DoubleRMat();
        ltp_of_ecef_d.setFlattendElement(0,(double) def.getLtp_of_ecef().getFlattendElement(0));
        ltp_of_ecef_d.setFlattendElement(1,(double) def.getLtp_of_ecef().getFlattendElement(1));
        ltp_of_ecef_d.setFlattendElement(2,(double) def.getLtp_of_ecef().getFlattendElement(2));
        ltp_of_ecef_d.setFlattendElement(3,(double) def.getLtp_of_ecef().getFlattendElement(3));
        ltp_of_ecef_d.setFlattendElement(4,(double) def.getLtp_of_ecef().getFlattendElement(4));
        ltp_of_ecef_d.setFlattendElement(5,(double) def.getLtp_of_ecef().getFlattendElement(5));
        ltp_of_ecef_d.setFlattendElement(6,(double) def.getLtp_of_ecef().getFlattendElement(6));
        ltp_of_ecef_d.setFlattendElement(7,(double) def.getLtp_of_ecef().getFlattendElement(7));
        ltp_of_ecef_d.setFlattendElement(8,(double) def.getLtp_of_ecef().getFlattendElement(8));
        EnuCoorFloat enu_d = new EnuCoorFloat();
        enu_d.setX(enu.getX());
        enu_d.setY(enu.getY());
        enu_d.setZ(enu.getZ());

  /* compute in double */
        EcefCoorDouble ecef_d = new EcefCoorDouble();
        PprzAlgebra.MAT33_VECT3_TRANSP_MUL(ecef_d, ltp_of_ecef_d, enu_d);
        
  /* convert result back to float*/
        ecef.setX((float) (ecef_d.getX()+0)); //TODO this seems odd
        ecef.setY((float) (ecef_d.getY()+0));
        ecef.setZ((float) (ecef_d.getZ()+0));
    }

    public static void ecef_of_ned_vect_f(EcefCoorFloat ecef, LtpDefFloat def, NedCoorFloat ned)
    {
        EnuCoorFloat enu = new EnuCoorFloat();
        PprzGeodetic.ENU_OF_TO_NED(enu, ned);
        ecef_of_enu_vect_f(ecef, def, enu);
    }
/* end use double versions */

    /* http://en.wikipedia.org/wiki/Geodetic_system */
    public static void lla_of_ecef_f(LlaCoor<Float> out, EcefCoor<Float> in)
    {

        // FIXME : make an ellipsoid struct
        float a = (float) 6378137.0;           /* earth semimajor axis in meters */
        float f = (float) (1. / 298.257223563);  /* reciprocal flattening          */
        float b = (float) (a * (1. - f));               /* semi-minor axis                */
        float b2 = b * b;

        float e2 = (float) (2.*f - (f * f));            /* first eccentricity squared     */
        float ep2 = (float) (f * (2. - f) / ((1. - f) * (1. - f))); /* second eccentricity squared    */
        float E2 = a * a - b2;


        float z2 = in.getZ() * in.getZ();
        float r2 = in.getX() * in.getX() + in.getY() * in.getY();
        float r = (float) Math.sqrt(r2);
        float F = (float) (54.*b2 * z2);
        float G = r2 + (1 - e2) * z2 - e2 * E2;
        float c = (e2 * e2 * F * r2) / (G * G * G);
        float s = (float) Math.pow((1 + c + Math.sqrt(c * c + 2 * c)), 1. / 3.);
        float s1 = 1 + s + 1 / s;
        float P = F / (3 * s1 * s1 * G * G);
        float Q = (float) Math.sqrt(1 + 2 * e2 * e2 * P);
        float ro = (float) (-(e2 * P * r) / (1 + Q) + Math.sqrt((a * a / 2) * (1 + 1 / Q) - ((1 - e2) * P * z2) / (Q *
                (1 + Q)) - P * r2 / 2));
        float tmp = (r - e2 * ro) * (r - e2 * ro);
        float U = (float) Math.sqrt(tmp + z2);
        float V = (float) Math.sqrt(tmp + (1 - e2) * z2);
        float zo = (b2 * in.getZ()) / (a * V);

        out.setAlt(U * (1 - b2 / (a * V)));
        out.setLat((float) Math.atan((in.getZ() + ep2 * zo) / r));
        out.setLon((float) Math.atan2(in.getY(), in.getX()));

    }

    public static void ecef_of_lla_f(EcefCoor<Float> out, LlaCoor<Float> in)
    {

        // FIXME : make an ellipsoid struct
        float a = (float) 6378137.0;           /* earth semimajor axis in meters */
        float f = (float) (1. / 298.257223563);  /* reciprocal flattening          */
        float e2 = (float) (2.*f - (f * f));            /* first eccentricity squared     */

        float sin_lat = (float) Math.sin(in.getLat());
        float cos_lat = (float) Math.cos(in.getLat());
        float sin_lon = (float) Math.sin(in.getLon());
        float cos_lon = (float) Math.cos(in.getLon());
        float chi = (float) Math.sqrt(1. - e2 * sin_lat * sin_lat);
        float a_chi = a / chi;

        out.setX(a_chi + in.getAlt() * cos_lat * cos_lon);
        out.setY(a_chi + in.getAlt() * cos_lat * sin_lon);
        out.setZ((float) (a_chi * (1. - e2) + in.getAlt() * sin_lat));
    }




//    #include "math/pprz_geodetic_utm.h"

    public static void CScal(float k, Complex z) { z.setRe(z.getRe()*k);  z.setIm(z.getIm()*k); }
    public static void CAdd(Complex z1, Complex z2) { z2.setRe(z2.getRe()+ z1.getRe());  z2.setIm(z2.getIm()+ z1.getIm()); }
    public static void CSub(Complex z1, Complex z2) { z2.setRe(z2.getRe() - z1.getRe());  z2.setIm(z2.getIm() - z1.getIm()); }
    public static void CI(Complex z) { float tmp = z.getRe(); z.setRe(- z.getIm()); z.setIm(tmp); }
    public static void CExp(Complex z) { float e = (float) Math.exp(z.getRe()); z.setRe((float) (e*Math.cos(z.getIm()))); z.setIm((float) (e*Math.sin(z.getIm()))); }
/* Expanded #define CSin(z) { CI(z); complex _z = {-z.re, -z.im}; CExp(z); CExp(_z); CSub(_z, z); CScal(-0.5, z); CI(z); } */

    public static void CSin(Complex z) { CI(z); Complex _z = new Complex(-z.getRe(), -z.getIm()); float e = (float) Math.exp(z.getRe()); float cos_z_im = (float) Math.cos(z.getIm()); z.setRe(e*cos_z_im); float sin_z_im = (float) Math.sin(z.getIm()); z.setIm(e*sin_z_im); _z.setRe(cos_z_im/e); _z.setIm(-sin_z_im/e); CSub(_z, z); CScal((float) -0.5, z); CI(z); }


    public static float isometric_latitude_f(float phi, float e)
    {
        return (float) (Math.log(Math.tan(Constants.M_PI_4 + phi / 2.0)) - e / 2.0 * Math.log((1.0 + e * Math.sin(phi)) / (1.0 - e * Math.sin(phi))));
    }

    public static float isometric_latitude_fast_f(float phi)
    {
        return (float) Math.log(Math.tan(Constants.M_PI_4 + phi / 2.0));
    }

    public static float inverse_isometric_latitude_f(float lat, double e, float epsilon)
    {
        float exp_l = (float) Math.exp(lat);
        float phi0 = (float) (2 * Math.atan(exp_l) - Constants.M_PI_2);
        float phi_;
        int max_iter = 3; /* To be sure to return */

        do {
            phi_ = phi0;
            float sin_phi = (float) (e * Math.sin(phi_));
            phi0 = (float) (2 * Math.atan(Math.pow((1 + sin_phi) / (1. - sin_phi), e / 2.) * exp_l) - Constants.M_PI_2);
            max_iter--;
        } while ((max_iter > 0 && ((phi_ - phi0) > epsilon || -(phi_ - phi0) > epsilon)));
        return phi0;
    }

    void utm_of_lla_f(UtmCoorFloat utm, LlaCoorFloat lla)
    {
        float lambda_c = (float) PprzGeodeticUtm.LambdaOfUtmZone(utm.getZone());
        float ll = isometric_latitude_f(lla.getLat() , (float) PprzGeodeticUtm.E);
        float dl = lla.getLon() - lambda_c;
        float phi_ = (float) Math.asin(Math.sin(dl) / Math.cosh(ll));
        float ll_ = isometric_latitude_fast_f(phi_);
        float lambda_ = (float) Math.atan(Math.sinh(ll) / Math.cos(dl));
        Complex z_ = new Complex(lambda_,  ll_);
        CScal(PprzGeodeticUtm.serie_coeff_proj_mercator[0], z_);
        int k;
        for (k = 1; k < 3; k++) {
            Complex z = new Complex(lambda_,  ll_);
            CScal(2 * k, z);
            CSin(z);
            CScal(PprzGeodeticUtm.serie_coeff_proj_mercator[k], z);
            CAdd(z, z_);
        }
        CScal((float) PprzGeodeticUtm.N, z_);
        utm.setEast((float) (PprzGeodeticUtm.DELTA_EAST + z_.getIm()));
        utm.setNorth((float) (PprzGeodeticUtm.DELTA_NORTH + z_.getRe()));

        // copy alt above reference ellipsoid
        utm.setAlt(lla.getAlt());
    }

    void lla_of_utm_f(LlaCoorFloat lla, UtmCoorFloat utm)
    {
        float scale = (float) (1 / PprzGeodeticUtm.N / PprzGeodeticUtm.serie_coeff_proj_mercator[0]);
        float real = (float) ((utm.getNorth() - PprzGeodeticUtm.DELTA_NORTH) * scale);
        float img = (float) ((utm.getEast() - PprzGeodeticUtm.DELTA_EAST) * scale);
        Complex z = new Complex(real, img);

        int k;
        for (k = 1; k < 2; k++) {
            Complex z_ = new Complex(real, img );
            CScal(2 * k, z_);
            CSin(z_);
            CScal(PprzGeodeticUtm.serie_coeff_proj_mercator_inverse[k], z_);
            CSub(z_, z);
        }

        float lambda_c = (float) PprzGeodeticUtm.LambdaOfUtmZone(utm.getZone());
        lla.setLon((float) (lambda_c + Math.atan(Math.sinh(z.getIm()) / Math.cos(z.getRe()))));
        float phi_ = (float) Math.asin(Math.sin(z.getRe()) / Math.cosh(z.getIm()));
        float il = isometric_latitude_fast_f(phi_);
        lla.setLat(inverse_isometric_latitude_f(il, PprzGeodeticUtm.E, (float) 1e-8));

        // copy alt above reference ellipsoid
        lla.setAlt(utm.getAlt());
    }
    
}

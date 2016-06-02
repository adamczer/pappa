/* 2015 University at Buffalo
 * This file is a java port of the Paparazzi Teams
 * code
 * Copyright (C) 2009-2013 The Paparazzi Team
 *
 * This file is part of paparazzi.
 *
 * paparazzi is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * paparazzi is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with paparazzi; see the file COPYING.  If not, write to
 * the Free Software Foundation, 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */
package ub.juav.airborne.math.functions.geodetic;

import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.structs.algebra.Vect2;
import ub.juav.airborne.math.structs.algebra.Vect3;
import ub.juav.airborne.math.structs.geodetic.*;
import ub.juav.airborne.math.util.Constants;

/**
 * Created by adamczer on 7/12/15.
 */
public class PprzGeodeticDouble {
    // h file defines only structs
    // begining c file

    public static void ltp_def_from_ecef_d(LtpDef<Double> def, EcefCoor<Double> ecef)
    {

  /* store the origin of the tangeant plane       */
        EcefCoor<Double> v = new EcefCoor<Double>();
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
    public static void lla_of_ecef_d(LlaCoor<Double> lla, Vect3<Double> ecef)
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

    public static void ecef_of_lla_d(EcefCoor<Double> ecef, LlaCoor<Double> lla)
    {

        // FIXME : make an ellipsoid struct
        double a = 6378137.0;           /* earth semimajor axis in meters */
        double f = 1. / 298.257223563;  /* reciprocal flattening          */
        double e2 = 2.*f - (f * f);            /* first eccentricity squared     */

        double sin_lat = Math.sin(lla.getLat());
        double cos_lat = Math.cos(lla.getLat());
        double sin_lon = Math.sin(lla.getLon());
        double cos_lon = Math.cos(lla.getLon());
        double chi = Math.sqrt(1. - e2 * sin_lat * sin_lat);
        double a_chi = a / chi;

        ecef.setX(a_chi + lla.getAlt() * cos_lat * cos_lon);
        ecef.setY(a_chi + lla.getAlt() * cos_lat * sin_lon);
        ecef.setZ(a_chi * (1. - e2) + lla.getAlt() * sin_lat);
    }

    public static void enu_of_ecef_point_d(EnuCoor<Double> enu, LtpDef<Double> def, EcefCoor<Double> ecef)
    {
        EcefCoor<Double> delta = new EcefCoor<Double>();
        PprzAlgebra.VECT3_DIFF(delta, ecef, def.getEcefCoor());
        PprzAlgebra.MAT33_VECT3_MULT(enu, def.getLtp_of_ecef(), delta);
    }

    public static void ned_of_ecef_point_d(NedCoor<Double> ned, LtpDef<Double> def, EcefCoor<Double> ecef)
    {
        EnuCoor<Double> enu = new EnuCoor<Double>();
        enu_of_ecef_point_d(enu, def, ecef);
        PprzGeodetic.ENU_OF_TO_NED(ned, enu);
    }

    public static void enu_of_ecef_vect_d(EnuCoor<Double> enu, LtpDef<Double> def, EcefCoor<Double> ecef)
    {
        PprzAlgebra.MAT33_VECT3_MULT(enu, def.getLtp_of_ecef(), ecef);
    }

    public static void ned_of_ecef_vect_d(NedCoor<Double> ned, LtpDef<Double> def, EcefCoor<Double> ecef)
    {
        EnuCoor<Double> enu = new EnuCoor<Double>();
        enu_of_ecef_vect_d(enu, def, ecef);
        PprzGeodetic.ENU_OF_TO_NED(ned, enu);
    }



    public static void ecef_of_enu_point_d(EcefCoor<Double> ecef, LtpDef<Double> def, EnuCoor<Double> enu)
    {
        PprzAlgebra.MAT33_VECT3_TRANSP_MUL(ecef, def.getLtp_of_ecef(), enu);
        PprzAlgebra.VECT3_ADD(ecef, def.getEcefCoor());
    }

    public static void ecef_of_ned_point_d(EcefCoor<Double> ecef, LtpDef<Double> def, NedCoor<Double> ned)
    {
        EnuCoor<Double> enu = new EnuCoor<Double>();
        PprzGeodetic.ENU_OF_TO_NED(enu, ned);
        ecef_of_enu_point_d(ecef, def, enu);
    }

    public static void ecef_of_enu_vect_d(EcefCoor<Double> ecef, LtpDef<Double> def, EnuCoor<Double> enu)
    {
        PprzAlgebra.MAT33_VECT3_TRANSP_MUL(ecef, def.getLtp_of_ecef(), enu);
    }

    public static void ecef_of_ned_vect_d(EcefCoor<Double> ecef, LtpDef<Double> def, NedCoor<Double> ned)
    {
        EnuCoor<Double> enu = new EnuCoor<Double>();
        PprzGeodetic.ENU_OF_TO_NED(enu, ned);
        ecef_of_enu_vect_d(ecef, def, enu);
    }


    public static void enu_of_lla_point_d(EnuCoor<Double> enu, LtpDef<Double> def, LlaCoor<Double> lla)
    {
        EcefCoor<Double> ecef = new EcefCoor<Double>();
        ecef_of_lla_d(ecef, lla);
        enu_of_ecef_point_d(enu, def, ecef);
    }

    public static void ned_of_lla_point_d(NedCoor<Double> ned, LtpDef<Double> def, LlaCoor<Double> lla)
    {
        EcefCoor<Double> ecef = new EcefCoor<Double>();
        ecef_of_lla_d(ecef, lla);
        ned_of_ecef_point_d(ned, def, ecef);
    }


    /* geocentric latitude of geodetic latitude */
    public static double gc_of_gd_lat_d(double gd_lat, double hmsl)
    {
        double a = 6378137.0;           /* earth semimajor axis in meters */
        double f = 1. / 298.257223563;  /* reciprocal flattening          */
        double c2 = (1. - f) * (1. - f);
  /* geocentric latitude at the planet surface */
        double ls = Math.atan(c2 * Math.tan(gd_lat));
        return Math.atan2(hmsl * Math.sin(gd_lat) + a * Math.sin(ls), hmsl * Math.cos(gd_lat) + a * Math.cos(ls));
    }


//    #include "math/pprz_geodetic_utm.h"


    public static double isometric_latitude_d(double phi, double e) {
        return Math.log(Math.tan(Constants.M_PI_4 + phi / 2.0)) - e / 2.0 * Math.log((1.0 + e * Math.sin(phi)) / (1.0 - e * Math.sin(phi)));
    }

    public static double isometric_latitude_fast_d(double phi) {
        return Math.log(Math.tan(Constants.M_PI_4 + phi / 2.0));
    }

    public static double inverse_isometric_latitude_d(double lat, double e, double epsilon) {
        double exp_l = Math.exp(lat);
        double phi0 = 2 * Math.atan(exp_l) - Constants.M_PI_2;
        double phi_;
        int max_iter = 3; /* To be sure to return */

        do {
            phi_ = phi0;
            double sin_phi = e * Math.sin(phi_);
            phi0 = 2 * Math.atan(Math.pow((1 + sin_phi) / (1. - sin_phi), e / 2.) * exp_l) - Constants.M_PI_2;
            max_iter--;
        } while (max_iter > 0 && Math.abs(phi_ - phi0) > epsilon); //TODO this may be wrong

        return phi0;
    }

    public static void CI(Vect2<Double> v) {
        double tmp = v.getX();
        v.setX(-v.getY());
        v.setY(tmp);
    }

    public static void CExp(Vect2<Double> v) {
        double e = Math.exp(v.getX());
        v.setX(e*Math.cos(v.getY()));
        v.setY(e*Math.sin(v.getY()));
    }

    public static void CSin(Vect2<Double> v) {
        CI(v);
        Vect2<Double> vstar = new Vect2<Double>();
        vstar.setX(-v.getX());
        vstar.setY(-v.getY());
        CExp(v);
        CExp(vstar);
        PprzAlgebra.VECT2_SUB(v, vstar);
        PprzAlgebra.VECT2_SMUL(v, v, -0.5);
        CI(v);
    }

    public static void lla_of_utm_d(LlaCoor<Double> lla, UtmCoor<Double> utm)
    {
        Vect2<Double> v = new Vect2<Double>();
        v.setX(utm.getNorth() - PprzGeodeticUtm.DELTA_NORTH); //TODO is this correct
        v.setY(utm.getEast() - PprzGeodeticUtm.DELTA_EAST); //TODO is this correct
        double scale = 1 / PprzGeodeticUtm.N / PprzGeodeticUtm.serie_coeff_proj_mercator[0];
        PprzAlgebra.VECT2_SMUL(v, v, scale);

        // first order taylor serie of something ?
        Vect2<Double> v1 = new Vect2<Double>();
        PprzAlgebra.VECT2_SMUL(v1, v, 2.);
        CSin(v1);
        PprzAlgebra.VECT2_SMUL(v1, v1, PprzGeodeticUtm.serie_coeff_proj_mercator[1]);
        PprzAlgebra.VECT2_SUB(v, v1);

        double lambda_c = PprzGeodeticUtm.LambdaOfUtmZone(utm.getZone());
        lla.setLon(lambda_c + Math.atan(Math.sinh(v.getY()) / Math.cos(v.getX())));
        double phi = Math.asin(Math.sin(v.getX()) / Math.cosh(v.getY()));
        double il = isometric_latitude_fast_d(phi);
        lla.setLat(inverse_isometric_latitude_d(il, PprzGeodeticUtm.E, 1e-8));

        // copy alt above reference ellipsoid
        lla.setAlt(utm.getAlt());
    }

}

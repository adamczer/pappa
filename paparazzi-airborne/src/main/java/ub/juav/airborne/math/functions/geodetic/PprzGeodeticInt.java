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
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.RMat;
import ub.juav.airborne.math.structs.geodetic.*;
import ub.juav.airborne.math.util.Constants;
import ub.juav.airborne.math.util.UtilityFunctions;

/**
 * Created by adamczer on 9/10/15.
 */
public class PprzGeodeticInt {
    //h file
//    #define CM_OF_M(_m)  ((_m)*1e2)
//            #define M_OF_CM(_cm) ((_cm)/1e2)
//            #define MM_OF_M(_m)  ((_m)*1e3)
//            #define M_OF_MM(_mm) ((_mm)/1e3)
//            #define EM7RAD_OF_RAD(_r) ((_r)*1e7)
//            #define RAD_OF_EM7RAD(_r) ((_r)/1e7)
//            #define EM7DEG_OF_RAD(_r) (DegOfRad(_r)*1e7)
//            #define RAD_OF_EM7DEG(_r) (RadOfDeg(_r)/1e7)
//
    public static final int HIGH_RES_TRIG_FRAC = 20;
//
//            #define VECT3_ENU_OF_NED(_o, _i) {
//        (_o).x = (_i).y;
//        (_o).y = (_i).x;
//        (_o).z = -(_i).z;
//    }
//
//    #define VECT3_NED_OF_ENU(_o, _i) VECT3_ENU_OF_NED(_o,_i)
//            #define INT32_VECT3_NED_OF_ENU(_o, _i) VECT3_ENU_OF_NED(_o,_i)
//            #define INT32_VECT3_ENU_OF_NED(_o, _i) VECT3_ENU_OF_NED(_o,_i)
//
//            #define ECEF_BFP_OF_REAL(_o, _i) {
//        (_o).x = (int)CM_OF_M((_i).x);
//        (_o).y = (int)CM_OF_M((_i).y);
//        (_o).z = (int)CM_OF_M((_i).z);
//    }
//
//    #define ECEF_FLOAT_OF_BFP(_o, _i) {
//        (_o).x = (float)M_OF_CM((_i).x);
//        (_o).y = (float)M_OF_CM((_i).y);
//        (_o).z = (float)M_OF_CM((_i).z);
//    }
//
//    #define ECEF_DOUBLE_OF_BFP(_o, _i) {
//        (_o).x = (double)M_OF_CM((_i).x);
//        (_o).y = (double)M_OF_CM((_i).y);
//        (_o).z = (double)M_OF_CM((_i).z);
//    }
//    #define LLA_BFP_OF_REAL(_o, _i) {
//        (_o).lat = (int)EM7DEG_OF_RAD((_i).lat);
//        (_o).lon = (int)EM7DEG_OF_RAD((_i).lon);
//        (_o).alt = (int)MM_OF_M((_i).alt);
//    }
//
//    #define LLA_FLOAT_OF_BFP(_o, _i) {
//        (_o).lat = RAD_OF_EM7DEG((float)(_i).lat);
//        (_o).lon = RAD_OF_EM7DEG((float)(_i).lon);
//        (_o).alt = M_OF_MM((float)(_i).alt);
//    }
//
//    #define LLA_DOUBLE_OF_BFP(_o, _i) {
//        (_o).lat = RAD_OF_EM7DEG((double)(_i).lat);
//        (_o).lon = RAD_OF_EM7DEG((double)(_i).lon);
//        (_o).alt = M_OF_MM((double)(_i).alt);
//    }
//    #define NED_BFP_OF_REAL(_o, _i) {
//        (_o).x = POS_BFP_OF_REAL((_i).x);
//        (_o).y = POS_BFP_OF_REAL((_i).y);
//        (_o).z = POS_BFP_OF_REAL((_i).z);
//    }
//
//    #define ENU_BFP_OF_REAL(_o, _i) NED_BFP_OF_REAL(_o, _i)
//
//            #define NED_FLOAT_OF_BFP(_o, _i){
//        (_o).x = POS_FLOAT_OF_BFP((_i).x);
//        (_o).y = POS_FLOAT_OF_BFP((_i).y);
//        (_o).z = POS_FLOAT_OF_BFP((_i).z);
//    }
//
//    #define ENU_FLOAT_OF_BFP(_o, _i) NED_FLOAT_OF_BFP(_o, _i)
//
//            #define INT32_VECT2_ENU_OF_NED(_o, _i) {
//        (_o).x = (_i).y;
//        (_o).y = (_i).x;
//    }
//
//    #define INT32_VECT2_NED_OF_ENU(_o, _i) INT32_VECT2_ENU_OF_NED(_o,_i)
//
//            #define HIGH_RES_RMAT_BFP_OF_REAL(_ei, _ef) {
//        (_ei).m[0] = BFP_OF_REAL((_ef).m[0], HIGH_RES_TRIG_FRAC);
//        (_ei).m[1] = BFP_OF_REAL((_ef).m[1], HIGH_RES_TRIG_FRAC);
//        (_ei).m[2] = BFP_OF_REAL((_ef).m[2], HIGH_RES_TRIG_FRAC);
//        (_ei).m[3] = BFP_OF_REAL((_ef).m[3], HIGH_RES_TRIG_FRAC);
//        (_ei).m[4] = BFP_OF_REAL((_ef).m[4], HIGH_RES_TRIG_FRAC);
//        (_ei).m[5] = BFP_OF_REAL((_ef).m[5], HIGH_RES_TRIG_FRAC);
//        (_ei).m[6] = BFP_OF_REAL((_ef).m[6], HIGH_RES_TRIG_FRAC);
//        (_ei).m[7] = BFP_OF_REAL((_ef).m[7], HIGH_RES_TRIG_FRAC);
//        (_ei).m[8] = BFP_OF_REAL((_ef).m[8], HIGH_RES_TRIG_FRAC);
//    }
//
//    #define HIGH_RES_RMAT_FLOAT_OF_BFP(_ef, _ei) {
//        (_ef).m[0] = FLOAT_OF_BFP((_ei).m[0], HIGH_RES_TRIG_FRAC);
//        (_ef).m[1] = FLOAT_OF_BFP((_ei).m[1], HIGH_RES_TRIG_FRAC);
//        (_ef).m[2] = FLOAT_OF_BFP((_ei).m[2], HIGH_RES_TRIG_FRAC);
//        (_ef).m[3] = FLOAT_OF_BFP((_ei).m[3], HIGH_RES_TRIG_FRAC);
//        (_ef).m[4] = FLOAT_OF_BFP((_ei).m[4], HIGH_RES_TRIG_FRAC);
//        (_ef).m[5] = FLOAT_OF_BFP((_ei).m[5], HIGH_RES_TRIG_FRAC);
//        (_ef).m[6] = FLOAT_OF_BFP((_ei).m[6], HIGH_RES_TRIG_FRAC);
//        (_ef).m[7] = FLOAT_OF_BFP((_ei).m[7], HIGH_RES_TRIG_FRAC);
//        (_ef).m[8] = FLOAT_OF_BFP((_ei).m[8], HIGH_RES_TRIG_FRAC);
//    }
//
//    #define HIGH_RES_RMAT_DOUBLE_OF_BFP(_ef, _ei) {
//        (_ef).m[0] = DOUBLE_OF_BFP((_ei).m[0], HIGH_RES_TRIG_FRAC);
//        (_ef).m[1] = DOUBLE_OF_BFP((_ei).m[1], HIGH_RES_TRIG_FRAC);
//        (_ef).m[2] = DOUBLE_OF_BFP((_ei).m[2], HIGH_RES_TRIG_FRAC);
//        (_ef).m[3] = DOUBLE_OF_BFP((_ei).m[3], HIGH_RES_TRIG_FRAC);
//        (_ef).m[4] = DOUBLE_OF_BFP((_ei).m[4], HIGH_RES_TRIG_FRAC);
//        (_ef).m[5] = DOUBLE_OF_BFP((_ei).m[5], HIGH_RES_TRIG_FRAC);
//        (_ef).m[6] = DOUBLE_OF_BFP((_ei).m[6], HIGH_RES_TRIG_FRAC);
//        (_ef).m[7] = DOUBLE_OF_BFP((_ei).m[7], HIGH_RES_TRIG_FRAC);
//        (_ef).m[8] = DOUBLE_OF_BFP((_ei).m[8], HIGH_RES_TRIG_FRAC);
//    }

    //c file

    public static void ltp_of_ecef_rmat_from_lla_i(RMat<Integer> ltp_of_ecef, LlaCoor<Integer> lla)
    {
        int sin_lat,cos_lat,sin_lon,cos_lon;
        if (Constants.USE_DOUBLE_PRECISION_TRIG) {
            sin_lat = (int) Math.rint(PprzAlgebraInt.BFP_OF_REAL(Math.sin(UtilityFunctions.RAD_OF_EM7DEG((double) lla.getLat())), HIGH_RES_TRIG_FRAC));
            cos_lat = (int) Math.rint(PprzAlgebraInt.BFP_OF_REAL(Math.cos(UtilityFunctions.RAD_OF_EM7DEG((double) lla.getLat())), HIGH_RES_TRIG_FRAC));
            sin_lon = (int) Math.rint(PprzAlgebraInt.BFP_OF_REAL(Math.sin(UtilityFunctions.RAD_OF_EM7DEG((double) lla.getLon())), HIGH_RES_TRIG_FRAC));
            cos_lon = (int) Math.rint(PprzAlgebraInt.BFP_OF_REAL(Math.cos(UtilityFunctions.RAD_OF_EM7DEG((double) lla.getLon())), HIGH_RES_TRIG_FRAC));
        }
        else {
            sin_lat = (int) Math.rint(PprzAlgebraInt.BFP_OF_REAL(Math.sin(UtilityFunctions.RAD_OF_EM7DEG((float) lla.getLat())), HIGH_RES_TRIG_FRAC));
            cos_lat = (int) Math.rint(PprzAlgebraInt.BFP_OF_REAL(Math.cos(UtilityFunctions.RAD_OF_EM7DEG((float) lla.getLat())), HIGH_RES_TRIG_FRAC));
            sin_lon = (int) Math.rint(PprzAlgebraInt.BFP_OF_REAL(Math.sin(UtilityFunctions.RAD_OF_EM7DEG((float) lla.getLon())), HIGH_RES_TRIG_FRAC));
            cos_lon = (int) Math.rint(PprzAlgebraInt.BFP_OF_REAL(Math.cos(UtilityFunctions.RAD_OF_EM7DEG((float) lla.getLon())), HIGH_RES_TRIG_FRAC));
        }

        ltp_of_ecef.setFlattendElement(0, -sin_lon);
        ltp_of_ecef.setFlattendElement(1, cos_lon);
        ltp_of_ecef.setFlattendElement(2, 0); /* this element is always zero http://en.wikipedia.org/wiki/Geodetic_system#From_ECEF_to_ENU */
        ltp_of_ecef.setFlattendElement(3,(int)((-(long)sin_lat * (long)cos_lon) >> HIGH_RES_TRIG_FRAC));
        ltp_of_ecef.setFlattendElement(4,(int)((-(long)sin_lat * (long)sin_lon) >> HIGH_RES_TRIG_FRAC));
        ltp_of_ecef.setFlattendElement(5, cos_lat);
        ltp_of_ecef.setFlattendElement(6,(int)(((long) cos_lat * (long)cos_lon) >> HIGH_RES_TRIG_FRAC));
        ltp_of_ecef.setFlattendElement(7,(int)(((long) cos_lat * (long)sin_lon) >> HIGH_RES_TRIG_FRAC));
        ltp_of_ecef.setFlattendElement(8, sin_lat);
    }

    public static void ltp_def_from_ecef_i(LtpDef<Integer> def, EcefCoor<Integer> ecef)
    {

  /* store the origin of the tangeant plane */
        PprzAlgebra.VECT3_COPY(def.getEcefCoor(), ecef);
  /* compute the lla representation of the origin */
        lla_of_ecef_i(def.getLlaCoor(), def.getEcefCoor());
  /* store the rotation matrix                    */
        ltp_of_ecef_rmat_from_lla_i(def.getLtp_of_ecef(), def.getLlaCoor());

    }

    public static void ltp_def_from_lla_i(LtpDef<Integer> def, LlaCoor<Integer> lla)
    {

  /* store the origin of the tangeant plane */
        PprzGeodetic.LLA_COPY(def.getLlaCoor(), lla);
  /* compute the ecef representation of the origin */
        ecef_of_lla_i(def.getEcefCoor(), def.getLlaCoor());
  /* store the rotation matrix                    */
        ltp_of_ecef_rmat_from_lla_i(def.getLtp_of_ecef(), def.getLlaCoor());
    }


    /** Convert a point from ECEF to local ENU.
     * @param[out] enu  ENU point in cm
     * @param[in]  def  local coordinate system definition
     * @param[in]  ecef ECEF point in cm
     */
    public static void enu_of_ecef_point_i(EnuCoor<Integer> enu, LtpDef<Integer> def, EcefCoor<Integer> ecef)
    {

        EcefCoor<Integer> delta = new EcefCoor<>();
        PprzAlgebra.VECT3_DIFF(delta, ecef, def.getEcefCoor());
        long tmpx = (long)def.getLtp_of_ecef().getFlattendElement(0) * delta.getX() +
            (long)def.getLtp_of_ecef().getFlattendElement(1) * delta.getY() +
                    0; /* this element is always zero http://en.wikipedia.org/wiki/Geodetic_system#From_ECEF_to_ENU */
        enu.setX(((int)tmpx >> HIGH_RES_TRIG_FRAC));
        long tmpy = (long)def.getLtp_of_ecef().getFlattendElement(3) * delta.getX() +
            (long)def.getLtp_of_ecef().getFlattendElement(4) * delta.getY() +
                    (long)def.getLtp_of_ecef().getFlattendElement(5) * delta.getZ();
        enu.setY((int)(tmpy >> HIGH_RES_TRIG_FRAC));
        long tmpz = (long)def.getLtp_of_ecef().getFlattendElement(6) * delta.getX() +
            (long)def.getLtp_of_ecef().getFlattendElement(7) * delta.getY() +
                    (long)def.getLtp_of_ecef().getFlattendElement(8) * delta.getZ();
        enu.setZ((int)(tmpz >> HIGH_RES_TRIG_FRAC));

    }


    /** Convert a point from ECEF to local NED.
     * @param[out] ned  NED point in cm
     * @param[in]  def  local coordinate system definition
     * @param[in]  ecef ECEF point in cm
     */
    public static void ned_of_ecef_point_i(NedCoor<Integer> ned, LtpDef<Integer> def, EcefCoor<Integer> ecef)
    {
        EnuCoor<Integer> enu = new EnuCoor<>();
        enu_of_ecef_point_i(enu, def, ecef);
        PprzGeodetic.ENU_OF_TO_NED(ned, enu);
    }


    /** Convert a ECEF position to local ENU.
     * @param[out] enu  ENU position in meter << #INT32_POS_FRAC
     * @param[in]  def  local coordinate system definition
     * @param[in]  ecef ECEF position in cm
     */
    public static void enu_of_ecef_pos_i(EnuCoor<Integer> enu, LtpDef<Integer> def, EcefCoor<Integer> ecef)
    {
        EnuCoor<Integer> enu_cm = new EnuCoor<>();
        enu_of_ecef_point_i(enu_cm, def, ecef);

  /* enu = (enu_cm / 100) << INT32_POS_FRAC
   * to loose less range:
   * enu_cm = enu << (INT32_POS_FRAC-2) / 25
   * which puts max enu output Q23.8 range to 8388km / 25 = 335km
   */
        PprzAlgebraInt.INT32_VECT3_LSHIFT(enu, enu_cm, PprzAlgebraInt.INT32_POS_FRAC - 2);
        PprzAlgebra.VECT3_SDIV(enu, enu, 25);
    }


    /** Convert a ECEF position to local NED.
     * @param[out] ned  NED position in meter << #INT32_POS_FRAC
     * @param[in]  def  local coordinate system definition
     * @param[in]  ecef ECEF position in cm
     */
    public static void ned_of_ecef_pos_i(NedCoor<Integer> ned, LtpDef<Integer> def, EcefCoor<Integer> ecef)
    {
        EnuCoor<Integer> enu = new EnuCoor<>();
        enu_of_ecef_pos_i(enu, def, ecef);
        PprzGeodetic.ENU_OF_TO_NED(ned, enu);
    }


    /** Rotate a vector from ECEF to ENU.
     * @param[out] enu  vector in ENU coordinate system
     * @param[in]  def  local coordinate system definition
     * @param[in]  ecef vector in ECEF coordinate system
     */
    public static void enu_of_ecef_vect_i(EnuCoor<Integer> enu, LtpDef<Integer> def, EcefCoor<Integer> ecef)
    {

        long tmpx = (long)def.getLtp_of_ecef().getFlattendElement(0) * ecef.getX() +
            (long)def.getLtp_of_ecef().getFlattendElement(1) * ecef.getY() +
                    0; /* this element is always zero http://en.wikipedia.org/wiki/Geodetic_system#From_ECEF_to_ENU */
        enu.setX((int)(tmpx >> HIGH_RES_TRIG_FRAC));
        long tmpy = (long)def.getLtp_of_ecef().getFlattendElement(3) * ecef.getX() +
            (long)def.getLtp_of_ecef().getFlattendElement(4) * ecef.getY() +
                    (long)def.getLtp_of_ecef().getFlattendElement(5) * ecef.getZ();
        enu.setY((int)(tmpy >> HIGH_RES_TRIG_FRAC));
        long tmpz = (long)def.getLtp_of_ecef().getFlattendElement(6) * ecef.getX() +
            (long)def.getLtp_of_ecef().getFlattendElement(7) * ecef.getY() +
                    (long)def.getLtp_of_ecef().getFlattendElement(8) * ecef.getZ();
        enu.setZ((int)(tmpz >> HIGH_RES_TRIG_FRAC));

    }


    /** Rotate a vector from ECEF to NED.
     * @param[out] ned  vector in NED coordinate system
     * @param[in]  def  local coordinate system definition
     * @param[in]  ecef vector in ECEF coordinate system
     */
    public static void ned_of_ecef_vect_i(NedCoor<Integer> ned, LtpDef<Integer> def, EcefCoor<Integer> ecef)
    {
        EnuCoor<Integer> enu = new EnuCoor<>();
        enu_of_ecef_vect_i(enu, def, ecef);
        PprzGeodetic.ENU_OF_TO_NED(ned, enu);
    }


    /** Rotate a vector from ENU to ECEF.
     * @param[out] ecef vector in ECEF coordinate system
     * @param[in]  def  local coordinate system definition
     * @param[in]  enu  vector in ENU coordinate system
     */
    public static void ecef_of_enu_vect_i(EcefCoor<Integer> ecef, LtpDef<Integer> def, EnuCoor<Integer> enu)
    {

        long tmpx = (long)def.getLtp_of_ecef().getFlattendElement(0) * enu.getX() +
            (long)def.getLtp_of_ecef().getFlattendElement(3) * enu.getY() +
                    (long)def.getLtp_of_ecef().getFlattendElement(6) * enu.getZ();
        ecef.setX((int)(tmpx >> HIGH_RES_TRIG_FRAC));

        long tmpy = (long)def.getLtp_of_ecef().getFlattendElement(1) * enu.getX() +
            (long)def.getLtp_of_ecef().getFlattendElement(4) * enu.getY() +
                    (long)def.getLtp_of_ecef().getFlattendElement(7) * enu.getZ();
        ecef.setY((int) (tmpy >> HIGH_RES_TRIG_FRAC));

  /* first element is always zero http://en.wikipedia.org/wiki/Geodetic_system#From_ENU_to_ECEF */
        long tmpz = (long)def.getLtp_of_ecef().getFlattendElement(5) * enu.getY() +
            (long)def.getLtp_of_ecef().getFlattendElement(8) * enu.getZ();
        ecef.setZ((int)(tmpz >> HIGH_RES_TRIG_FRAC));

    }


    /** Rotate a vector from NED to ECEF.
     * @param[out] ecef vector in ECEF coordinate system
     * @param[in]  def  local coordinate system definition
     * @param[in]  ned  vector in NED coordinate system
     */
    public static void ecef_of_ned_vect_i(EcefCoor<Integer> ecef, LtpDef<Integer> def, NedCoor<Integer> ned)
    {
        EnuCoor<Integer> enu = new EnuCoor<>();
        PprzGeodetic.ENU_OF_TO_NED(enu, ned);
        ecef_of_enu_vect_i(ecef, def, enu);
    }


    /** Convert a point in local ENU to ECEF.
     * @param[out] ecef ECEF point in cm
     * @param[in]  def  local coordinate system definition
     * @param[in]  enu  ENU point in cm
     */
    public static void ecef_of_enu_point_i(EcefCoor<Integer> ecef, LtpDef<Integer> def, EnuCoor<Integer> enu)
    {
        ecef_of_enu_vect_i(ecef, def, enu);
        PprzAlgebra.VECT3_ADD(ecef, def.getEcefCoor());
    }


    /** Convert a point in local NED to ECEF.
     * @param[out] ecef ECEF point in cm
     * @param[in]  def  local coordinate system definition
     * @param[in]  ned  NED point in cm
     */
    public static void ecef_of_ned_point_i(EcefCoor<Integer> ecef, LtpDef<Integer> def, NedCoor<Integer> ned)
    {
        EnuCoor<Integer> enu = new EnuCoor<>();
        PprzGeodetic.ENU_OF_TO_NED(enu, ned);
        ecef_of_enu_point_i(ecef, def, enu);
    }


    /** Convert a local ENU position to ECEF.
     * @param[out] ecef ECEF position in cm
     * @param[in]  def  local coordinate system definition
     * @param[in]  enu  ENU position in meter << #INT32_POS_FRAC
     */
    public static void ecef_of_enu_pos_i(EcefCoor<Integer> ecef, LtpDef<Integer> def, EnuCoor<Integer> enu)
    {
  /* enu_cm = (enu * 100) >> INT32_POS_FRAC
   * to loose less range:
   * enu_cm = (enu * 25) >> (INT32_POS_FRAC-2)
   * which puts max enu input Q23.8 range to 8388km / 25 = 335km
   */
        EnuCoor<Integer> enu_cm = new EnuCoor<>();
        PprzAlgebra.VECT3_SMUL(enu_cm, enu, 25);
        PprzAlgebraInt.INT32_VECT3_RSHIFT(enu_cm, enu_cm, PprzAlgebraInt.INT32_POS_FRAC - 2);
        ecef_of_enu_vect_i(ecef, def, enu_cm);
        PprzAlgebra.VECT3_ADD(ecef, def.getEcefCoor());
    }


    /** Convert a local NED position to ECEF.
     * @param[out] ecef ECEF position in cm
     * @param[in]  def  local coordinate system definition
     * @param[in]  ned  NED position in meter << #INT32_POS_FRAC
     */
    public static void ecef_of_ned_pos_i(EcefCoor<Integer> ecef, LtpDef<Integer> def, NedCoor<Integer> ned)
    {
        EnuCoor<Integer> enu = new EnuCoor<>();
        PprzGeodetic.ENU_OF_TO_NED(enu, ned);
        ecef_of_enu_pos_i(ecef, def, enu);
    }


    public static void enu_of_lla_point_i(EnuCoor<Integer> enu, LtpDef<Integer> def, LlaCoor<Integer> lla)
    {
        EcefCoor<Integer> ecef = new EcefCoor<>();
        ecef_of_lla_i(ecef, lla);
        enu_of_ecef_point_i(enu, def, ecef);
    }

    public static void ned_of_lla_point_i(NedCoor<Integer> ned, LtpDef<Integer> def, LlaCoor<Integer> lla)
    {
        EcefCoor<Integer> ecef = new EcefCoor<>();
        ecef_of_lla_i(ecef, lla);
        ned_of_ecef_point_i(ned, def, ecef);
    }

    public static void enu_of_lla_vect_i(EnuCoor<Integer> enu, LtpDef<Integer> def, LlaCoor<Integer> lla)
    {
        EcefCoor<Integer> ecef = new EcefCoor<>();
        ecef_of_lla_i(ecef, lla);
        enu_of_ecef_vect_i(enu, def, ecef);
    }

    public static void ned_of_lla_vect_i(NedCoor<Integer> ned, LtpDef<Integer> def, LlaCoor<Integer> lla)
    {
        EcefCoor<Integer> ecef = new EcefCoor<>();
        ecef_of_lla_i(ecef, lla);
        ned_of_ecef_vect_i(ned, def, ecef);
    }

/*
   For now we cheat and call the floating point version
   Anyone up for writing it in fixed point ?
*/
//    #include "pprz_geodetic_float.h"
//            #include "pprz_geodetic_double.h"

    public static void lla_of_ecef_i(LlaCoor<Integer> out, EcefCoor<Integer> in)
    {

  /* convert our input to floating point */
        EcefCoor<Double> in_d = new EcefCoor<>();
        in_d.setX(UtilityFunctions.M_OF_CM((double) in.getX()));
        in_d.setY(UtilityFunctions.M_OF_CM((double) in.getY()));
        in_d.setZ(UtilityFunctions.M_OF_CM((double) in.getZ()));
  /* calls the floating point transformation */
        LlaCoor<Double> out_d = new LlaCoor<Double>();
        PprzGeodeticDouble.lla_of_ecef_d(out_d, in_d);
  /* convert the output to fixed point       */
        out.setLon((int)Math.rint(UtilityFunctions.EM7DEG_OF_RAD(out_d.getLon())));
        out.setLat((int) Math.rint(UtilityFunctions.EM7DEG_OF_RAD(out_d.getLat())));
        out.setAlt((int) UtilityFunctions.MM_OF_M(out_d.getAlt()));

    }

    public static void ecef_of_lla_i(EcefCoor<Integer> out, LlaCoor<Integer> in)
    {

  /* convert our input to floating point */
        LlaCoor<Double> in_d = new LlaCoor<Double>();
        in_d.setLon(UtilityFunctions.RAD_OF_EM7DEG((double) in.getLon()));
        in_d.setLat(UtilityFunctions.RAD_OF_EM7DEG((double) in.getLat()));
        in_d.setAlt(UtilityFunctions.M_OF_MM((double) in.getAlt()));
  /* calls the floating point transformation */
        EcefCoor<Double> out_d = new EcefCoor<Double>();
        PprzGeodeticDouble.ecef_of_lla_d(out_d, in_d);
  /* convert the output to fixed point       */
        out.setX((int) UtilityFunctions.CM_OF_M(out_d.getX()));
        out.setY((int)UtilityFunctions.CM_OF_M(out_d.getY()));
        out.setZ((int)UtilityFunctions.CM_OF_M(out_d.getZ()));
    }

}

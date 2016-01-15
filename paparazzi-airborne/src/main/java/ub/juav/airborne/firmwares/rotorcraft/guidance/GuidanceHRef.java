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

package ub.juav.airborne.firmwares.rotorcraft.guidance;

import ub.juav.airborne.Definitions;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.util.UtilityFunctions;

/**
 * Created by adamczer on 1/3/16.
 */
public class GuidanceHRef {
    /** reference model vertical accel in meters/s^2 (output)
     *  fixed point representation with #GV_ZDD_REF_FRAC
     *  Q23.8 : accuracy 0.0039 , range 8388km/s^2
     */
    private static int gv_zdd_ref;

    /** reference model vertical speed in meters/sec (output)
     *  fixed point representation with #GV_ZD_REF_FRAC
     *  Q14.17 : accuracy 0.0000076 , range 16384m/s2
     */
    private static int gv_zd_ref;

    /** reference model altitude in meters (output)
     *  fixed point representation with #GV_Z_REF_FRAC
     *  Q37.26 :
     */
    private static long gv_z_ref;


/* Saturations definition */
    static {
    //h
    if (Definitions.GUIDANCE_V_REF_MIN_ZD!=0) {
        Definitions.GUIDANCE_V_REF_MIN_ZD=-3.0;
    }
    if (Definitions.GUIDANCE_V_REF_MAX_ZD!=0) {
        Definitions.GUIDANCE_V_REF_MAX_ZD=3.0;
    }
    Definitions.GV_ZDD_REF_FRAC = 8;
    Definitions.GV_FREQ_FRAC = 9;
    Definitions.GV_FREQ = (1<<Definitions.GV_FREQ_FRAC);
    Definitions.GV_ZD_REF_FRAC = (Definitions.GV_ZDD_REF_FRAC + Definitions.GV_FREQ_FRAC);
    Definitions.GV_Z_REF_FRAC = (Definitions.GV_ZD_REF_FRAC + Definitions.GV_FREQ_FRAC);
    //c
    if(Definitions.GUIDANCE_V_REF_MIN_ZDD!=0) {
        Definitions.GUIDANCE_V_REF_MIN_ZDD =  (-2.0 * 9.81);
    }
    Definitions.GV_MIN_ZDD = PprzAlgebraInt.BFP_OF_REAL(Definitions.GUIDANCE_V_REF_MIN_ZDD,Definitions.GV_ZDD_REF_FRAC);

    if(Definitions.GUIDANCE_V_REF_MAX_ZDD!=0) {
        Definitions.GUIDANCE_V_REF_MAX_ZDD = (0.8 * 9.81);
    }
    Definitions.GV_MAX_ZDD = PprzAlgebraInt.BFP_OF_REAL(Definitions.GUIDANCE_V_REF_MAX_ZDD, Definitions.GV_ZDD_REF_FRAC);

/** maximum distance altitude setpoint is advanced in climb mode */
    if(Definitions.GUIDANCE_V_REF_MAX_Z_DIFF!=0) {
        Definitions.GUIDANCE_V_REF_MAX_Z_DIFF = 2.0;
    }
    Definitions.GV_MAX_Z_DIFF = PprzAlgebraInt.BFP_OF_REAL(Definitions.GUIDANCE_V_REF_MAX_Z_DIFF, Definitions.GV_Z_REF_FRAC);
    Definitions.GV_MIN_ZD = PprzAlgebraInt.BFP_OF_REAL(Definitions.GUIDANCE_V_REF_MIN_ZD, Definitions.GV_ZD_REF_FRAC);
    Definitions.GV_MAX_ZD = PprzAlgebraInt.BFP_OF_REAL(Definitions.GUIDANCE_V_REF_MAX_ZD, Definitions.GV_ZD_REF_FRAC);

/* second order model natural frequency and damping */
    if(Definitions.GUIDANCE_V_REF_OMEGA!=0) {
        Definitions.GUIDANCE_V_REF_OMEGA = UtilityFunctions.RadOfDeg(100.);
    }
    if(Definitions.GUIDANCE_V_REF_ZETA!=0) {
        Definitions.GUIDANCE_V_REF_ZETA = 0.85;
    }
    Definitions.GV_ZETA_OMEGA_FRAC = 10;
    Definitions.GV_ZETA_OMEGA = PprzAlgebraInt.BFP_OF_REAL((Definitions.GUIDANCE_V_REF_ZETA * Definitions.GUIDANCE_V_REF_OMEGA), Definitions.GV_ZETA_OMEGA_FRAC);
    Definitions.GV_OMEGA_2_FRAC = 7;
    Definitions.GV_OMEGA_2 = PprzAlgebraInt.BFP_OF_REAL((Definitions.GUIDANCE_V_REF_OMEGA * Definitions.GUIDANCE_V_REF_OMEGA), Definitions.GV_OMEGA_2_FRAC);

/* first order time constant */
    Definitions.GV_REF_THAU_F = 0.25;
    Definitions.GV_REF_INV_THAU_FRAC = 16;
    Definitions.GV_REF_INV_THAU = PprzAlgebraInt.BFP_OF_REAL((1. / 0.25), Definitions.GV_REF_INV_THAU_FRAC);
}
    public static void gv_set_ref(int alt, int speed, int accel)
    {
        long new_z = ((long)alt) << (Definitions.GV_Z_REF_FRAC - PprzAlgebraInt.INT32_POS_FRAC);
        gv_z_ref   = new_z;
        gv_zd_ref  = speed >> (PprzAlgebraInt.INT32_SPEED_FRAC - Definitions.GV_ZD_REF_FRAC);
        gv_zdd_ref = accel >> (PprzAlgebraInt.INT32_ACCEL_FRAC - Definitions.GV_ZDD_REF_FRAC);
    }

    public static void gv_update_ref_from_z_sp(int z_sp)
    {

        gv_z_ref  += gv_zd_ref;
        gv_zd_ref += gv_zdd_ref;

        // compute the "speed part" of zdd = -2*zeta*omega*zd -omega^2(z_sp - z)
        int zd_zdd_res = gv_zd_ref >> (Definitions.GV_ZD_REF_FRAC - Definitions.GV_ZDD_REF_FRAC);
        int zdd_speed = ((-2 * Definitions.GV_ZETA_OMEGA) * zd_zdd_res) >> (Definitions.GV_ZETA_OMEGA_FRAC);
        // compute z error in z_sp resolution
        int z_err_sp = z_sp - (int)(gv_z_ref >> (Definitions.GV_Z_REF_FRAC - PprzAlgebraInt.INT32_POS_FRAC));
        // convert to accel resolution
        int z_err_accel = z_err_sp >> (PprzAlgebraInt.INT32_POS_FRAC - Definitions.GV_ZDD_REF_FRAC);
        int zdd_pos = ((Definitions.GV_OMEGA_2) * z_err_accel) >> Definitions.GV_OMEGA_2_FRAC;
        gv_zdd_ref = zdd_speed + zdd_pos;

  /* Saturate accelerations */
        UtilityFunctions.Bound(gv_zdd_ref, Definitions.GV_MIN_ZDD, Definitions.GV_MAX_ZDD);

  /* Saturate speed and adjust acceleration accordingly */
        if (gv_zd_ref <= Definitions.GV_MIN_ZD) {
            gv_zd_ref = Definitions.GV_MIN_ZD;
            if (gv_zdd_ref < 0) {
                gv_zdd_ref = 0;
            }
        } else if (gv_zd_ref >= Definitions.GV_MAX_ZD) {
            gv_zd_ref = Definitions.GV_MAX_ZD;
            if (gv_zdd_ref > 0) {
                gv_zdd_ref = 0;
            }
        }
    }


    public static void gv_update_ref_from_zd_sp(int zd_sp, int z_pos)
    {

        gv_z_ref  += gv_zd_ref;
        gv_zd_ref += gv_zdd_ref;

  /* limit z_ref to GUIDANCE_V_REF_MAX_Z_DIFF from current z pos */
        long cur_z = ((long)z_pos) << (Definitions.GV_Z_REF_FRAC - PprzAlgebraInt.INT32_POS_FRAC);
        UtilityFunctions.Bound(gv_z_ref, cur_z - Definitions.GV_MAX_Z_DIFF, cur_z + Definitions.GV_MAX_Z_DIFF);

        int zd_err = gv_zd_ref - (zd_sp >> (PprzAlgebraInt.INT32_SPEED_FRAC - Definitions.GV_ZD_REF_FRAC));
        int zd_err_zdd_res = zd_err >> (Definitions.GV_ZD_REF_FRAC - Definitions.GV_ZDD_REF_FRAC);
        gv_zdd_ref = (-Definitions.GV_REF_INV_THAU * zd_err_zdd_res) >> Definitions.GV_REF_INV_THAU_FRAC;

  /* Saturate accelerations */
        UtilityFunctions.Bound(gv_zdd_ref, Definitions.GV_MIN_ZDD, Definitions.GV_MAX_ZDD);

  /* Saturate speed and adjust acceleration accordingly */
        if (gv_zd_ref <= Definitions.GV_MIN_ZD) {
            gv_zd_ref = Definitions.GV_MIN_ZD;
            if (gv_zdd_ref < 0) {
                gv_zdd_ref = 0;
            }
        } else if (gv_zd_ref >= Definitions.GV_MAX_ZD) {
            gv_zd_ref = Definitions.GV_MAX_ZD;
            if (gv_zdd_ref > 0) {
                gv_zdd_ref = 0;
            }
        }
    }
}

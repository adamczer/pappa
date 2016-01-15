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

import ub.juav.airborne.math.util.UtilityFunctions;

/**
 * Created by adamczer on 9/9/15.
 */
public class PprzGeodeticUtm {
    public static final double DELTA_EAST = 500000.0;
    public static final double DELTA_NORTH = 0.0;
    public static final double E = 0.08181919106;
    public static final double K0 = 0.9996;
    public static final double A = 6378137.0;
    public static final double N = (K0*A);

    public static double LambdaOfUtmZone(int utm_zone) {return UtilityFunctions.RadOfDeg((utm_zone-1)*6-180+3);}

    public static float[] serie_coeff_proj_mercator = {
            (float) 0.99832429842242842444,
            (float) 0.00083632803657738403,
            (float) 0.00000075957783563707,
            (float) 0.00000000119563131778,
            (float) 0.00000000000241079916
    };

    static  float[] serie_coeff_proj_mercator_inverse = {
            (float) 0.998324298422428424,
            (float) 0.000837732168742475825,
            (float) 5.90586914811817062e-08,
            (float) 1.6734091890305064e-10,
            (float) 2.13883575853313883e-13
    };
}

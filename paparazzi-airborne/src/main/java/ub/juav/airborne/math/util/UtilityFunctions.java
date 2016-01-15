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
package ub.juav.airborne.math.util;

import ub.juav.airborne.math.wrappers.PrimitiveWrapper;

import java.math.BigInteger;

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

    public static boolean bit_is_set(PrimitiveWrapper<Integer> status, int orrepQuatI) {
        return BigInteger.valueOf(status.getPrimitive()).testBit(orrepQuatI);
    }

    public static void SetBit(PrimitiveWrapper<Integer> status, int orrepRmatI) {
        status.setPrimitive(BigInteger.valueOf(status.getPrimitive()).setBit(orrepRmatI).intValue());
    }
}

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

package ub.juav.airborne.math.functions.stat;

/**
 * Created by adamczer on 1/3/16.
 */
public class PprzStat {
    /** Compute the variance of an array of values (float).
     *  The variance is a measure of how far a set of numbers is spread out
     *  V(X) = E[(X-E[X])^2] = E[X^2] - E[X]^2
     *  where E[X] is the expected value of X
     *
     *  @param array pointer to an array of float
     *  @param nb numbre of values in the array, must be >0
     *  @return variance
     */
    public static float variance_float(float[] array, int nb)
    {
        float me = (float) 0.;
        float see = (float) 0.;
        for (int i = 0; i < nb; i++) {
            me += array[i];
            see += array[i] * array[i];
        }
        me /= nb;
        return (see / nb - me * me);
    }

    /** Compute the variance of an array of values (integer).
     *  The variance is a measure of how far a set of numbers is spread out
     *  V(X) = E[(X-E[X])^2] = E[X^2] - E[X]^2
     *  where E[X] is the expected value of X
     *
     *  @param array pointer to an array of integer
     *  @param nb numbre of values in the array, must be >0
     *  @return variance
     */
    public static int variance_int(int[] array, int nb)
    {
        float me = 0;
        float see = 0;
        for (int i = 0; i < nb; i++) {
            me += (float)array[i];
            see += (float)(array[i] * array[i]);
        }
        me /= nb;
        return Math.round(see / nb - me * me);
    }
}

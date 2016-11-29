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

package ub.juav.airborne.math.structs.algebra;

/**
 * Created by adamczer on 7/12/15.
 */
public class Vect2<T extends Number> {
    public T x, y;

    public static Vect2<Integer> newIntVect2() {
        Vect2<Integer> ret = new Vect2<>();
        zero(ret);
        return ret;
    }

    public T getX() {
        return x;
    }
    public void setX(T x) {
        this.x = x;
    }

    public T getY() {
        return y;
    }

    public void setY(T y) {
        this.y = y;
    }

    public static Vect2<Long> newLongVect2() {
        Vect2<Long> ret = new Vect2<>();
        zero(ret);
        return ret;
    }

    private static void zero(Vect2 ret) {
        ret.setX(0);
        ret.setY(0);
    }

    public static Vect2<Float> newFloat(float x, float y) {
        Vect2<Float> ret = new Vect2<>();
        ret.setX(x);
        ret.setY(y);
        return ret;
    }

    public static Vect2<Float> newFloat() {
        return newFloat(0,0);
    }
}

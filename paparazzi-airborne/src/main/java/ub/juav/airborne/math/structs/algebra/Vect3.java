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
public class Vect3<T extends Number> extends Vect2<T>{
    public T z;

    public T getZ() {
        return z;
    }

    public void setZ(T z) {
        this.z = z;
    }

    public static Vect3<Float> newFloat(float x, float y, float z) {
        Vect3<Float> ret = new Vect3<Float>();
        ret.setX(x);
        ret.setY(y);
        ret.setZ(z);
        return ret;
    }

    public static Vect3<Float> newFloat() {
        return newFloat(0,0,0);
    }

    public static Vect3<Integer> newInteger() {
        return newInteger(0,0,0);
    }

    private static Vect3<Integer> newInteger(int x, int y, int z) {
        Vect3<Integer> ret = new Vect3<>();
        ret.setX(x);
        ret.setY(y);
        ret.setZ(z);
        return ret;
    }

    @Override
    public String toString() {
        return x+", "+y+", "+z;
    }
}

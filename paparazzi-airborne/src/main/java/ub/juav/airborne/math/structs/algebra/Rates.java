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
 * Created by adamczer on 7/15/15.
 */
public class Rates<T extends Number> {
    public T p;
    public T q;
    public T r;

    public T getP() {
        return p;
    }

    public void setP(T p) {
        this.p = p;
    }

    public T getQ() {
        return q;
    }

    public void setQ(T q) {
        this.q = q;
    }

    public T getR() {
        return r;
    }

    public void setR(T r) {
        this.r = r;
    }

    public static Rates<Integer> newInteger() {
        Rates<Integer> ret = new Rates<>();
        ret.setR(0);
        ret.setQ(0);
        ret.setP(0);
        return ret;
    }

    public static Rates<Float> newFloat() {
        return newFloat(0,0,0);
    }

    public static Rates<Float> newFloat(float r, float q, float p) {
        Rates<Float> ret = new Rates<>();
        ret.setR(r);
        ret.setQ(q);
        ret.setP(p);
        return ret;
    }

    public static Rates<Integer> newInteger(int p, int q, int r) {
        Rates<Integer> ret = new Rates<>();
        ret.setR(r);
        ret.setQ(q);
        ret.setP(p);
        return ret;
    }

    @Override
    public String toString() {
        return p+","+q+","+r;
    }
}

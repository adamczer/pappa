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
public class Quat<T extends Number> {
    public T qi;
    public T qx;
    public T qy;
    public T qz;

    public T getQi() {
        return qi;
    }

    public void setQi(T qi) {
        this.qi = qi;
    }

    public T getQx() {
        return qx;
    }

    public void setQx(T qx) {
        this.qx = qx;
    }

    public T getQy() {
        return qy;
    }

    public void setQy(T qy) {
        this.qy = qy;
    }

    public T getQz() {
        return qz;
    }

    public void setQz(T qz) {
        this.qz = qz;
    }

    public static Quat<Integer> newInteger() {
        Quat<Integer> ret = new Quat<>();
        ret.setQz(0);
        ret.setQy(0);
        ret.setQi(0);
        ret.setQx(0);
        return ret;
    }

    public static Quat<Float> newFloat() {
        Quat<Float> ret = new Quat<>();
        ret.setQz(0f);
        ret.setQy(0f);
        ret.setQi(0f);
        ret.setQx(0f);
        return ret;
    }

    @Override
    public String toString() {
        return "{qz="+qz+",qy="+qy+",qi="+qi+",qx="+qx+"}";
    }
}

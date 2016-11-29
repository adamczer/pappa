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
public class Eulers<T extends Number> {
    public T phi;
    public T theta;
    public T psi;

    public T getPhi() {
        return phi;
    }

    public void setPhi(T phi) {
        this.phi = phi;
    }

    public T getTheta() {
        return theta;
    }

    public void setTheta(T theta) {
        this.theta = theta;
    }

    public T getPsi() {
        return psi;
    }

    public void setPsi(T psi) {
        this.psi = psi;
    }

    public static Eulers<Integer> newInteger() {
        Eulers<Integer> ret = new Eulers<>();
        ret.setPhi(0);
        ret.setPsi(0);
        ret.setTheta(0);
        return ret;
    }

    public static Eulers<Float> newFloat() {
        Eulers<Float> ret = new Eulers<>();
        ret.setPhi(0f);
        ret.setPsi(0f);
        ret.setTheta(0f);
        return ret;
    }
}

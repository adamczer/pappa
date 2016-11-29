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

package ub.juav.airborne.math.structs.orientation;

import ub.juav.airborne.math.wrappers.PrimitiveWrapper;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.RMat;

/**
 * Created by adamczer on 11/22/15.
 */
public class OrientationReps {
    /**
     * Holds the status bits for all orientation representations.
     * When the corresponding bit is set, the representation
     * is already computed.
     */
    PrimitiveWrapper<Integer> status = new PrimitiveWrapper<>(0);

    /**
     * Orientation quaternion.
     * Units: #INT32_QUAT_FRAC
     */
    Quat<Integer> quat_i;

    /**
     * Orientation in zyx euler angles.
     * Units: rad in BFP with #INT32_ANGLE_FRAC
     */
    Eulers<Integer> eulers_i;

    /**
     * Orientation rotation m.
     * Units: rad in BFP with #INT32_TRIG_FRAC
     */
    RMat<Integer> rmat_i;

    /**
     * Orientation as quaternion.
     * Units: unit length quaternion
     */
    Quat<Float> quat_f;

    /**
     * Orienation in zyx euler angles.
     * Units: rad
     */
    Eulers<Float> eulers_f;

    /**
     * Orientation rotation m.
     * Units: rad
     */
    RMat<Float> rmat_f;

    public PrimitiveWrapper<Integer> getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status.setPrimitive(status);
    }

    public Quat<Integer> getQuat_i() {
        return quat_i;
    }

    public void setQuat_i(Quat<Integer> quat_i) {
        this.quat_i = quat_i;
    }

    public Eulers<Integer> getEulers_i() {
        return eulers_i;
    }

    public void setEulers_i(Eulers<Integer> eulers_i) {
        this.eulers_i = eulers_i;
    }

    public RMat<Integer> getRmat_i() {
        return rmat_i;
    }

    public void setRmat_i(RMat<Integer> rmat_i) {
        this.rmat_i = rmat_i;
    }

    public Quat<Float> getQuat_f() {
        return quat_f;
    }

    public void setQuat_f(Quat<Float> quat_f) {
        this.quat_f = quat_f;
    }

    public Eulers<Float> getEulers_f() {
        return eulers_f;
    }

    public void setEulers_f(Eulers<Float> eulers_f) {
        this.eulers_f = eulers_f;
    }

    public RMat<Float> getRmat_f() {
        return rmat_f;
    }

    public void setRmat_f(RMat<Float> rmat_f) {
        this.rmat_f = rmat_f;
    }
}

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

package ub.juav.airborne.math.structs.geodetic;

import ub.juav.airborne.math.structs.algebra.RMat;

/**
 * Created by adamczer on 9/7/15.
 */
public abstract class LtpDef<T extends Number> {
    private EcefCoor<T> ecefCoor;
    private LlaCoor<T> llaCoor;
    private RMat<T> ltp_of_ecef;
    private T hmsl;

    public EcefCoor<T> getEcefCoor() {
        return ecefCoor;
    }

    public void setEcefCoor(EcefCoor<T> ecefCoor) {
        this.ecefCoor = ecefCoor;
    }

    public LlaCoor<T> getLlaCoor() {
        return llaCoor;
    }

    public void setLlaCoor(LlaCoor<T> llaCoor) {
        this.llaCoor = llaCoor;
    }

    public RMat<T> getLtp_of_ecef() {
        return ltp_of_ecef;
    }

    public void setLtp_of_ecef(RMat<T> ltp_of_ecef) {
        this.ltp_of_ecef = ltp_of_ecef;
    }

    public Number getHmsl() {
        return hmsl;
    }

    public void setHmsl(T hmsl) {
        this.hmsl = hmsl;
    }
}

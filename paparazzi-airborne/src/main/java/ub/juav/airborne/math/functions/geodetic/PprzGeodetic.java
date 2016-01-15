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

import ub.juav.airborne.math.structs.geodetic.LlaCoor;
import ub.juav.airborne.math.structs.geodetic.XyzCoord;

/**
 * Created by adamczer on 7/26/15.
 */
public class PprzGeodetic {

    public static void ENU_OF_TO_NED(XyzCoord ned, XyzCoord enu) {
        ned.setX(enu.getX());
        ned.setY(enu.getY());
        ned.setZ(enu.getZ());
    }

    public static void LLA_COPY(LlaCoor _pos1,LlaCoor _pos2){
        (_pos1).setLat(_pos2.getLat());
        (_pos1).setLon((_pos2).getLon());
        (_pos1).setAlt(_pos2.getAlt());
    }

}

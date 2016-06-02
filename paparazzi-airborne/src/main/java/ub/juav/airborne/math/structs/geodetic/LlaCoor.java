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

/**
 * Created by adamczer on 9/7/15.
 */
public class LlaCoor<T extends Number> {

    public static LlaCoor<Double> LlaCoorDouble() {
        LlaCoor<Double> ret = new LlaCoor<>();
        ret.setLat(0.d);
        ret.setLon(0.d);
        ret.setAlt(0.d);
        return ret;
    }

    private T lat;
    private T lon;
    private T alt;

    public T getLat() {
        return lat;
    }

    public void setLat(T lat) {
        this.lat = lat;
    }

    public T getLon() {
        return lon;
    }

    public void setLon(T lon) {
        this.lon = lon;
    }

    public T getAlt() {
        return alt;
    }

    public void setAlt(T alt) {
        this.alt = alt;
    }
}

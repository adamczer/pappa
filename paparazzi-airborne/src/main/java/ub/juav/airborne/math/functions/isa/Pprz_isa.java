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

package ub.juav.airborne.math.functions.isa;

/**
 * Created by adamczer on 11/1/15.
 */
public class Pprz_isa {

    // Standard Atmosphere constants
/** ISA sea level standard atmospheric pressure in Pascal */
    public static final float PPRZ_ISA_SEA_LEVEL_PRESSURE = (float) 101325.0;
/** ISA sea level standard temperature in Kelvin */
    public static final float PPRZ_ISA_SEA_LEVEL_TEMP = (float) 288.15;
/** temperature laps rate in K/m */
    public static final float PPRZ_ISA_TEMP_LAPS_RATE = (float) 0.0065;
/** earth-surface gravitational acceleration in m/s^2 */
    public static final float PPRZ_ISA_GRAVITY = (float) 9.80665;
/** universal gas constant in J/(mol*K) */
    public static final float PPRZ_ISA_GAS_CONSTANT = (float) 8.31447;
/** molar mass of dry air in kg/mol */
    public static final float PPRZ_ISA_MOLAR_MASS = (float) 0.0289644;
/** universal gas constant / molar mass of dry air in J*kg/K */
    public static final float PPRZ_ISA_AIR_GAS_CONSTANT = (PPRZ_ISA_GAS_CONSTANT/PPRZ_ISA_MOLAR_MASS);
/** standard air density in kg/m^3 */
    public static final float PPRZ_ISA_AIR_DENSITY = (float) 1.225;
    public static final float PPRZ_ISA_M_OF_P_CONST = (PPRZ_ISA_AIR_GAS_CONSTANT *PPRZ_ISA_SEA_LEVEL_TEMP / PPRZ_ISA_GRAVITY);

/**
 * Get absolute altitude from pressure (using simplified equation).
 * Referrence pressure is standard pressure at sea level
 *
 * @param pressure current pressure in Pascal (Pa)
 * @return altitude in m in ISA conditions
 */
    public static float pprz_isa_altitude_of_pressure(float pressure)
    {
        if (pressure > 0.) {
            return (PPRZ_ISA_M_OF_P_CONST * (float)Math.log(PPRZ_ISA_SEA_LEVEL_PRESSURE / pressure));
        } else {
            return (float) 0.;
        }
    }

/**
 * Get relative altitude from pressure (using simplified equation).
 * Given the current pressure and a reference pressure (at height=0),
 * calculate the height above the reference in meters.
 * If you pass QNH as reference pressure, you get the height above sea level.
 * Using QFE as reference pressure, you get height above the airfield.
 *
 * @param pressure current pressure in Pascal (Pa)
 * @param ref_p reference pressure (QFE) when height=0 or QNH at sea level
 * @return height in m above reference in ISA conditions
 */
    public static float pprz_isa_height_of_pressure(float pressure, float ref_p)
    {
        if (pressure > 0. && ref_p > 0.) {
            return (PPRZ_ISA_M_OF_P_CONST * (float)Math.log(ref_p / pressure));
        } else {
            return (float)0.;
        }
    }

/**
 * Get pressure in Pa from absolute altitude (using simplified equation).
 *
 * @param altitude current absolute altitude in meters
 * @return static pressure in Pa at given altitude in ISA conditions
 */
    public static float pprz_isa_pressure_of_altitude(float altitude)
    {
        return (PPRZ_ISA_SEA_LEVEL_PRESSURE * (float)Math.exp((-1. / PPRZ_ISA_M_OF_P_CONST) * altitude));
    }

/**
 * Get pressure in Pa from height (using simplified equation).
 *
 * @param height current height over reference (relative altitude) in meters
 * @param ref_p reference pressure (QFE or QNH) when height = 0
 * @return static pressure in Pa at given height in ISA conditions
 */
    public static float pprz_isa_pressure_of_height(float height, float ref_p)
    {
        return (ref_p * (float)Math.exp((-1. / PPRZ_ISA_M_OF_P_CONST) * height));
    }


/**
 * Get relative altitude from pressure (using full equation).
 * Given the current pressure and a reference pressure (at height=0),
 * calculate the height above the reference in meters.
 * If you pass QNH as reference pressure, you get the height above sea level.
 * Using QFE as reference pressure, you get height above the airfield.
 *
 * @param pressure current pressure in Pascal (Pa)
 * @param ref_p reference pressure (QFE or QNH) in Pa
 * @return height above reference in m in ISA conditions
 */
    public static float pprz_isa_height_of_pressure_full(float pressure, float ref_p)
    {
        if (ref_p > 0.) {
            float prel = pressure / ref_p;
            float inv_expo = PPRZ_ISA_GAS_CONSTANT * PPRZ_ISA_TEMP_LAPS_RATE /
                    PPRZ_ISA_GRAVITY / PPRZ_ISA_MOLAR_MASS;
            return (1 - (float)Math.pow(prel, inv_expo)) * PPRZ_ISA_SEA_LEVEL_TEMP / PPRZ_ISA_TEMP_LAPS_RATE;
        } else {
            return (float)0.;
        }
    }

/**
 * Get reference pressure (QFE or QNH) from current pressure and height.
 * (using full equation)
 *
 * @param pressure current pressure in Pascal (Pa)
 * @param height height above referece (sea level for QNH, airfield alt for QFE) in m
 * @return reference pressure at height=0 in Pa
 */
    public static float pprz_isa_ref_pressure_of_height_full(float pressure, float height)
    {
        //  Trel = 1 - L*h/T0;
        float Trel = (float)1.0 - PPRZ_ISA_TEMP_LAPS_RATE * height / PPRZ_ISA_SEA_LEVEL_TEMP;
        float expo = PPRZ_ISA_GRAVITY * PPRZ_ISA_MOLAR_MASS / PPRZ_ISA_GAS_CONSTANT /
            PPRZ_ISA_TEMP_LAPS_RATE;
        return pressure / (float)Math.pow(Trel, expo);
    }

}

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
public class RMat<T extends Number> extends Mat33<T> {
    public static RMat<Double> RMatDouble() {
        RMat<Double> ret = new RMat<>();
        ret.setMatrix(new Double[3][3]);
        for(int i = 0; i<3;i++)
            for(int j = 0; j<3;j++)
                ret.getMatrix()[i][j]=0.d;
        return ret;
    }
    public void setFlattendElement(int index, T val) {
        int x=-1,y=-1;
        int count = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (count==index) {
                    x = i;
                    y = j;
                    break;
                }
                else {
                    count++;
                }
            }
            if (count==index) {
                break;
            }
        }
        setElement(val,x,y);
    }

    public T getFlattendElement(int index) {
        int x=-1,y=-1;
        int count = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (count==index) {
                    return getElement(i,j);
                }
                else {
                    count++;
                }
            }
            if (count==index) {
                break;
            }
        }
        throw new IllegalArgumentException("Invalid index.");
    }
}

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
public class MatNxN<T extends Number> {
    private T[][] matrix;

    public T[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(T[][] matrix) {
        this.matrix = matrix;
    }

    public void setElement(T d, int row, int col) {
        matrix[row][col] = d;
    }

    public T getElement(int row,int column) {
        return matrix[row][column];
    }

    public void zero() {

    };
}

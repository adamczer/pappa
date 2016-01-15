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

package ub.juav.airborne.math.functions.matrix;

import ub.juav.airborne.math.util.Constants;
import ub.juav.airborne.math.util.NumberMath;

/**
 * Created by adamczer on 1/3/16.
 */
public class PprzSimpleMatrix {
//
// C = A*B   A:(i,k) B:(k,j) C:(i,j)
//
    public static void MAT_MUL(int _i, int _k, int _j, Number[][] C, Number[][] A, Number[][] B) {
        int l,c,m;
        for (l=0; l<_i; l++)
        for (c=0; c<_j; c++) {
            C[l][c] = 0.;
            for (m=0; m<_k; m++)
            C[l][c] = NumberMath.add(C[l][c],NumberMath.mul(A[l][m],B[m][c]));
        }
    }

//
// C = A*B'   A:(i,k) B:(j,k) C:(i,j)
//
    public static void MAT_MUL_T(int _i, int _k, int _j, Number[][] C, Number[][] A, Number[][] B) {
        int l,c,m;
        for (l=0; l<_i; l++)
        for (c=0; c<_j; c++) {
            C[l][c] = 0.;
            for (m=0; m<_k; m++)
            C[l][c] = NumberMath.add(C[l][c], NumberMath.mul(A[l][m], B[c][m]));
        }
    }


//
// C = A-B
//
    public static void MAT_SUB(int _i, int _j, Number[][] C, Number[][] A, Number[][] B) {
        int l,c;
        for (l=0; l<_i; l++)
        for (c=0; c<_j; c++)
        C[l][c] = NumberMath.sub(A[l][c], B[l][c]);
    }




//
// invS = 1/det(S) com(S)'
//
    public static void MAT_INV33(Float [][] _invS, Float [][] _S) {
        float m00 = _S[1][1]*_S[2][2] - _S[1][2]*_S[2][1];
        float m10 = _S[0][1]*_S[2][2] - _S[0][2]*_S[2][1];
        float m20 = _S[0][1]*_S[1][2] - _S[0][2]*_S[1][1];
        float m01 = _S[1][0]*_S[2][2] - _S[1][2]*_S[2][0];
        float m11 = _S[0][0]*_S[2][2] - _S[0][2]*_S[2][0];
        float m21 = _S[0][0]*_S[1][2] - _S[0][2]*_S[1][0];
        float m02 = _S[1][0]*_S[2][1] - _S[1][1]*_S[2][0];
        float m12 = _S[0][0]*_S[2][1] - _S[0][1]*_S[2][0];
        float m22 = _S[0][0]*_S[1][1] - _S[0][1]*_S[1][0];
        float det = _S[0][0]*m00 - _S[1][0]*m10 + _S[2][0]*m20;
        if (Math.abs(det) < Constants.FLT_EPSILON) {
      /* If the determinant is too small then set it to epsilon preserving sign. */
            System.out.println("warning: %s:%d MAT_INV33 trying to invert non-invertable matrix '%s' and put result in '%s'.\n"+_S+ _invS);
            if(det<0)
                det = -1*Constants.FLT_EPSILON;
            else
                det = Constants.FLT_EPSILON;
//            det = copysignf(FLT_EPSILON, det);
        }
        _invS[0][0] =  m00 / det;
        _invS[1][0] = -m01 / det;
        _invS[2][0] =  m02 / det;
        _invS[0][1] = -m10 / det;
        _invS[1][1] =  m11 / det;
        _invS[2][1] = -m12 / det;
        _invS[0][2] =  m20 / det;
        _invS[1][2] = -m21 / det;
        _invS[2][2] =  m22 / det;
    }
}

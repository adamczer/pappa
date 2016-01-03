package ub.juav.autopilot.math.functions.matrix;

import ub.juav.autopilot.math.functions.algebra.PprzAlgebraFloat;
import ub.juav.autopilot.math.primitive.wrappers.PrimitiveWrapper;

/**
 * Created by adamczer on 11/1/15.
 */
public class PPrzMatrixDcompFloat {
//These heavily leverage pointers.. need to think about this a bit
    /** Cholesky decomposition
     *
     * http://rosettacode.org/wiki/Cholesky_decomposition#C
     *
     * @param out pointer to the output array [n x n]
     * @param in pointer to the input array [n x n]
     * @param n dimension of the matrix
     */
    void pprz_cholesky_float(PrimitiveWrapper<Float[][]> out, PrimitiveWrapper<Float[][]> in, int n)
    {
        int i, j, k;
//        float[][] _o = new float[n][n];
//        MAKE_MATRIX_PTR(o, _o, n);

//        float_mat_zero(o, n, n);
        PrimitiveWrapper<Float[][]> o = new PrimitiveWrapper<>(new Float[n][n]);
        for (i = 0; i < n; i++) {
            for (j = 0; j < (i + 1); j++) {
                float s = 0;
                for (k = 0; k < j; k++) {
                    s += o.getPrimitive()[i][k] * o.getPrimitive()[j][k];
                }
                o.getPrimitive()[i][j] = (i == j) ?
                        (float) Math.sqrt(in.getPrimitive()[i][i] - s) :
                        ((float)1.0 / o.getPrimitive()[j][j] * (in.getPrimitive()[i][j] - s));
            }
        }
        PprzAlgebraFloat.float_mat_copy(out, o, n, n);
    }

    /** QR decomposition
     *
     * using Householder method
     *
     * http://rosettacode.org/wiki/QR_decomposition#C
     *
     * @param Q square orthogonal matrix Q [m x m]
     * @param R upper triangular matrix R [m x n]
     * @param in pointer to the input array [m x n]
     * @param m number of rows of the input matrix
     * @param n number of column of the input matrix
     */
    void pprz_qr_float(PrimitiveWrapper<Float [][]> Q, PrimitiveWrapper<Float [][]> R, PrimitiveWrapper<Float [][]> in, int m, int n)
    {
        int i, k;
        float[][][] _q = new float[m][m][m];
//        float[][] q = new float[m][m]; ie float *q[m][m];
//        float[][][] q = new float[][][]{};
        PrimitiveWrapper<Float[][][]> q = new PrimitiveWrapper<>(new Float[m][m][m]);
//        float[][] _z = new float[m][n];
//        MAKE_MATRIX_PTR(z, _z, m);
//        float[][] z = new float[][]{};
        PrimitiveWrapper<Float[][]> z = new PrimitiveWrapper<>(new Float[m][n]);
//        float[][] _z1 = new float[m][n];
//        MAKE_MATRIX_PTR(z1, _z1, m);
//        float[][] z1 = new float[][]{};
        PrimitiveWrapper<Float[][]> z1 = new PrimitiveWrapper<>(new Float[m][n]);
//        float[][] _z2 = new float[m][m];
//        MAKE_MATRIX_PTR(z2, _z2, m);
//        float[][] z2 = new float[][]{};
        PrimitiveWrapper<Float[][]> z2 = new PrimitiveWrapper<>(new Float[m][m]);
        //for (i = 0;  i < m; i++) for (k = 0; k < m; k++) { q[i][k] = _q[i][k][0]; } TODO
        for (i = 0;  i < m; i++) for (k = 0; k < m; k++) { q.getPrimitive()[0][i][k] = _q[i][k][0]; }
        PprzAlgebraFloat.float_mat_copy(z, in, m, n);
        for (k = 0; k < n && k < m - 1; k++) {
            PrimitiveWrapper<Float[]> e = new PrimitiveWrapper<>(new Float[m]);
            PrimitiveWrapper<Float[]> x = new PrimitiveWrapper<>(new Float[m]);
            float a, b;
            PprzAlgebraFloat.float_mat_minor(z1, z, m, n, k);
            PprzAlgebraFloat.float_mat_col(x, z1, m, k);
            a = PprzAlgebraFloat.float_vect_norm(x, m);
            if (in.getPrimitive()[k][k] > 0) { a = -a; }
            for (i = 0; i < m; i++) {
                e.getPrimitive()[i] = Float.valueOf((i == k) ? 1 : 0);
                e.getPrimitive()[i] = x.getPrimitive()[i] + a * e.getPrimitive()[i];
            }
            b = PprzAlgebraFloat.float_vect_norm(e, m);
            PprzAlgebraFloat.float_vect_sdiv(e, e, b, m);
            PrimitiveWrapper<Float[][]> temp = new PrimitiveWrapper<>(q.getPrimitive()[k]);
            PprzAlgebraFloat.float_mat_vmul(temp, e, m);
            q.getPrimitive()[k] = temp.getPrimitive();
            temp = new PrimitiveWrapper<>(q.getPrimitive()[k]);
            PprzAlgebraFloat.float_mat_mul(z, temp, z1, m, m, n);
            q.getPrimitive()[k] = temp.getPrimitive();
        }
        PrimitiveWrapper<Float[][]> temp = new PrimitiveWrapper<>(q.getPrimitive()[0]);
        PprzAlgebraFloat.float_mat_copy(Q, temp, m, m);
        q.getPrimitive()[0] = temp.getPrimitive();
        for (i = 1; i < n && i < m - 1; i++) {
            temp = new PrimitiveWrapper<>(q.getPrimitive()[i]);
            PprzAlgebraFloat.float_mat_mul(z2, temp, Q, m, m, m);
            q.getPrimitive()[i] = temp.getPrimitive();
            PprzAlgebraFloat.float_mat_copy(Q, z2, m, m);
        }
        PprzAlgebraFloat.float_mat_mul(R, Q, in, m, m, n);
        PprzAlgebraFloat.float_mat_transpose(Q, m);
    }

/** Some SVD decomposition utility macros and functions
 */

//Computes sqrt(a*a + b*b) without destructive underflow or overflow
    public static float pythag(float a, float b)
    {
        float absa, absb;
        absa = Math.abs(a);
        absb = Math.abs(b);
        if (absa > absb) {
            return (absa * (float)Math.sqrt(1.0 + (absb / absa) * (absb / absa)));
        } else if (absb == 0.0) {
            return (float) 0.0;
        } else {
            return (absb * (float)Math.sqrt(1.0 + (absa / absb) * (absa / absb)));
        }
    }


    /** SVD decomposition
     *
     * --------------------------------------------------------------------- *
     * Reference:  "Numerical Recipes By W.H. Press, B. P. Flannery,         *
     *              S.A. Teukolsky and W.T. Vetterling, Cambridge            *
     *              University Press, 1986" [BIBLI 08].                      *
     * --------------------------------------------------------------------- *
     *
     * Given a matrix a(m,n), this routine computes its singular value decomposition,
     * A = U · W · Vt. The matrix U replaces a on output. The diagonal matrix of singular
     * values W is output as a vector w(n). The matrix V (not the transpose Vt) is output
     * as v(n,n).
     *
     * @param a input matrix [m x n] and output matrix U [m x n]
     * @param w output diagonal vector of matrix W [n]
     * @param v output square matrix V [n x n]
     * @param m number of rows of input the matrix
     * @param n number of columns of the input matrix
     * @return 0 (false) if convergence failed, 1 (true) if decomposition succed
     */
    public static int pprz_svd_float(PrimitiveWrapper<Float[][]> a, PrimitiveWrapper<Float[]> w, PrimitiveWrapper<Float[][]> v, int m, int n)
    {
  /* Householder reduction to bidiagonal form. */
        boolean flag;
        int i;
        int its;
        int j;
        int jj;
        int k;
        int l = 0;
        int NM = 0;
        float C, F, H, S, X, Y, Z, tmp;
        float G = (float) 0.0;
        float Scale = (float) 0.0;
        float ANorm = (float) 0.0;
        float[] rv1 = new float[n];

        for (i = 0; i < n; ++i) {
            l = i + 1;
            rv1[i] = Scale * G;
            G = (float) 0.0;
            S = (float) 0.0;
            Scale = (float) 0.0;
            if (i < m) {
                for (k = i; k < m; ++k) {
                    Scale = Scale + Math.abs(a.getPrimitive()[k][i]);
                }
                if (Scale != 0.0) {
                    for (k = i; k < m; ++k) {
                        a.getPrimitive()[k][i] = a.getPrimitive()[k][i] / Scale;
                        S = S + a.getPrimitive()[k][i] * a.getPrimitive()[k][i];
                    }
                    F = a.getPrimitive()[i][i];
                    G = (float) Math.sqrt(S);
                    if (F > 0.0) {
                        G = -G;
                    }
                    H = F * G - S;
                    a.getPrimitive()[i][i] = F - G;
                    if (i != (n - 1)) {
                        for (j = l; j < n; ++j) {
                            S = (float) 0.0;
                            for (k = i; k < m; ++k) {
                                S = S + a.getPrimitive()[k][i] * a.getPrimitive()[k][j];
                            }
                            F = S / H;
                            for (k = i; k < m; ++k) {
                                a.getPrimitive()[k][j] = a.getPrimitive()[k][j] + F * a.getPrimitive()[k][i];
                            }
                        }
                    }
                    for (k = i; k < m; ++k) {
                        a.getPrimitive()[k][i] = Scale * a.getPrimitive()[k][i];
                    }
                }
            }

            w.getPrimitive()[i] = Scale * G;
            G = (float) 0.0;
            S = (float) 0.0;
            Scale = (float) 0.0;
            if ((i < m) && (i != (n - 1))) {
                for (k = l; k < n; ++k) {
                    Scale = Scale + Math.abs(a.getPrimitive()[i][k]);
                }
                if (Scale != 0.0) {
                    for (k = l; k < n; ++k) {
                        a.getPrimitive()[i][k] = a.getPrimitive()[i][k] / Scale;
                        S = S + a.getPrimitive()[i][k] * a.getPrimitive()[i][k];
                    }
                    F = a.getPrimitive()[i][l];
                    G = (float) Math.sqrt(S);
                    if (F > 0.0) {
                        G = -G;
                    }
                    H = F * G - S;
                    a.getPrimitive()[i][l] = F - G;
                    for (k = l; k < n; ++k) {
                        rv1[k] = a.getPrimitive()[i][k] / H;
                    }
                    if (i != (m - 1)) {
                        for (j = l; j < m; ++j) {
                            S = (float) 0.0;
                            for (k = l; k < n; ++k) {
                                S = S + a.getPrimitive()[j][k] * a.getPrimitive()[i][k];
                            }
                            for (k = l; k < n; ++k) {
                                a.getPrimitive()[j][k] = a.getPrimitive()[j][k] + S * rv1[k];
                            }
                        }
                    }
                    for (k = l; k < n; ++k) {
                        a.getPrimitive()[i][k] = Scale * a.getPrimitive()[i][k];
                    }
                }
            }
            tmp = Math.abs(w.getPrimitive()[i]) + Math.abs(rv1[i]);
            if (tmp > ANorm) {
                ANorm = tmp;
            }
        }

  /* Accumulation of right-hand transformations. */
        for (i = n - 1; i >= 0; --i) {
            if (i < (n - 1)) {
                if (G != 0.0) {
                    for (j = l; j < n; ++j) {
                        v.getPrimitive()[j][i] = (a.getPrimitive()[i][j] / a.getPrimitive()[i][l]) / G;
                    }
                    for (j = l; j < n; ++j) {
                        S = (float) 0.0;
                        for (k = l; k < n; ++k) {
                            S = S + a.getPrimitive()[i][k] * v.getPrimitive()[k][j];
                        }
                        for (k = l; k < n; ++k) {
                            v.getPrimitive()[k][j] = v.getPrimitive()[k][j] + S * v.getPrimitive()[k][i];
                        }
                    }
                }
                for (j = l; j < n; ++j) {
                    v.getPrimitive()[i][j] = (float) 0.0;
                    v.getPrimitive()[j][i] = (float) 0.0;
                }
            }
            v.getPrimitive()[i][i] = (float) 1.0;
            G = rv1[i];
            l = i;
        }

  /* Accumulation of left-hand transformations. */
        for (i = n - 1; i >= 0; --i) {
            l = i + 1;
            G = w.getPrimitive()[i];
            if (i < (n - 1)) {
                for (j = l; j < n; ++j) {
                    a.getPrimitive()[i][j] = (float) 0.0;
                }
            }
            if (G != 0.0) {
                G = (float) (1.0 / G);
                if (i != (n - 1)) {
                    for (j = l; j < n; ++j) {
                        S = (float) 0.0;
                        for (k = l; k < m; ++k) {
                            S = S + a.getPrimitive()[k][i] * a.getPrimitive()[k][j];
                        }
                        F = (S / a.getPrimitive()[i][i]) * G;
                        for (k = i; k < m; ++k) {
                            a.getPrimitive()[k][j] = a.getPrimitive()[k][j] + F * a.getPrimitive()[k][i];
                        }
                    }
                }
                for (j = i; j < m; ++j) {
                    a.getPrimitive()[j][i] = a.getPrimitive()[j][i] * G;
                }
            } else {
                for (j = i; j < m; ++j) {
                    a.getPrimitive()[j][i] = (float) 0.0;
                }
            }
            a.getPrimitive()[i][i] = (float) (a.getPrimitive()[i][i] + 1.0);
        }

  /* Diagonalization of the bidiagonal form.
     Loop over singular values. */
        for (k = (n - 1); k >= 0; --k) {
    /* Loop over allowed iterations. */
            for (its = 1; its <= 30; ++its) {
      /* Test for splitting.
         Note that rv1[0] is always zero. */
                flag = true;
                for (l = k; l >= 0; --l) {
                    NM = l - 1;
                    if ((Math.abs(rv1[l]) + ANorm) == ANorm) {
                        flag = false;
                        break;
                    } else if ((Math.abs(w.getPrimitive()[NM]) + ANorm) == ANorm) {
                        break;
                    }
                }

      /* Cancellation of rv1[l], if l > 0; */
                if (flag) {
                    C = (float) 0.0;
                    S = (float) 1.0;
                    for (i = l; i <= k; ++i) {
                        F = S * rv1[i];
                        if ((Math.abs(F) + ANorm) != ANorm) {
                            G = w.getPrimitive()[i];
                            //H = sqrtf( F * F + G * G );
                            H = pythag(F, G);
                            w.getPrimitive()[i] = H;
                            H = (float) (1.0 / H);
                            C = (G * H);
                            S = -(F * H);
                            for (j = 0; j < m; ++j) {
                                Y = a.getPrimitive()[j][NM];
                                Z = a.getPrimitive()[j][i];
                                a.getPrimitive()[j][NM] = (Y * C) + (Z * S);
                                a.getPrimitive()[j][i] = -(Y * S) + (Z * C);
                            }
                        }
                    }
                }
                Z = w.getPrimitive()[k];
      /* Convergence. */
                if (l == k) {
        /* Singular value is made nonnegative. */
                    if (Z < 0.0) {
                        w.getPrimitive()[k] = -Z;
                        for (j = 0; j < n; ++j) {
                            v.getPrimitive()[j][k] = -v.getPrimitive()[j][k];
                        }
                    }
                    break;
                }

                if (its >= 30) {
                    // No convergence in 30 iterations
                    return 0;
                }

                X = w.getPrimitive()[l];
                NM = k - 1;
                Y = w.getPrimitive()[NM];
                G = rv1[NM];
                H = rv1[k];
                F = (float) (((Y - Z) * (Y + Z) + (G - H) * (G + H)) / (2.0 * H * Y));
                //G = sqrtf( F * F + 1.0 );
                G = pythag(F, (float) 1.0);
                tmp = G;
                if (F < 0.0) {
                    tmp = -tmp;
                }
                F = ((X - Z) * (X + Z) + H * ((Y / (F + tmp)) - H)) / X;

      /* Next QR transformation. */
                C = (float) 1.0;
                S = (float) 1.0;
                for (j = l; j <= NM; ++j) {
                    i = j + 1;
                    G = rv1[i];
                    Y = w.getPrimitive()[i];
                    H = S * G;
                    G = C * G;
                    //Z = sqrtf( F * F + H * H );
                    Z = pythag(F, H);
                    rv1[j] = Z;
                    C = F / Z;
                    S = H / Z;
                    F = (X * C) + (G * S);
                    G = -(X * S) + (G * C);
                    H = Y * S;
                    Y = Y * C;
                    for (jj = 0; jj < n; ++jj) {
                        X = v.getPrimitive()[jj][j];
                        Z = v.getPrimitive()[jj][i];
                        v.getPrimitive()[jj][j] = (X * C) + (Z * S);
                        v.getPrimitive()[jj][i] = -(X * S) + (Z * C);
                    }
                    //Z = sqrtf( F * F + H * H );
                    Z = pythag(F, H);
                    w.getPrimitive()[j] = Z;

        /* Rotation can be arbitrary if Z = 0. */
                    if (Z != 0.0) {
                        Z = (float) (1.0 / Z);
                        C = F * Z;
                        S = H * Z;
                    }
                    F = (C * G) + (S * Y);
                    X = -(S * G) + (C * Y);
                    for (jj = 0; jj < m; ++jj) {
                        Y = a.getPrimitive()[jj][j];
                        Z = a.getPrimitive()[jj][i];
                        a.getPrimitive()[jj][j] = (Y * C) + (Z * S);
                        a.getPrimitive()[jj][i] = -(Y * S) + (Z * C);
                    }
                }
                rv1[l] = (float) 0.0;
                rv1[k] = F;
                w.getPrimitive()[k] = X;
            }
        }

        return 1;
    }

    /** SVD based linear solver
     *
     * Solves A · X = B for a vector X,
     * where A is specified by the arrays u, w, v as returned by pprz_svd_float.
     * m and n are the dimensions of a.
     * b(m) is the input right-hand side.
     * x(n) is the output solution vector.
     * No input quantities are destroyed, so the routine may be called sequentially with different b's.
     *
     * @param x solution of the system ([n x l] matrix)
     * @param u U matrix from SVD decomposition
     * @param w diagonal of the W matrix from the SVD decomposition
     * @param v V matrrix from SVD decomposition
     * @param b right-hand side input matrix from system to solve (column vector [m x l])
     * @param m number of rows of the matrix A
     * @param n number of columns of the matrix A
     * @param l number of columns of the matrix B
     */
    public static void pprz_svd_solve_float(PrimitiveWrapper<Float[][]> x, PrimitiveWrapper<Float[][]> u, PrimitiveWrapper<Float[]> w, PrimitiveWrapper<Float[][]> v, PrimitiveWrapper<Float[][]> b, int m, int n, int l)
    {
        int i, j, jj, k;
        float s;
        float[] tmp= new float[n];
        for (k = 0; k < l; k++) { //Iterate on all column of b
            for (j = 0; j < n; j++) { //Calculate UTB
                s = (float) 0.0;
                if (w.getPrimitive()[j] != 0.0) {   //Nonzero result only if wj is nonzero
                    for (i = 0; i < m; i++) { s += u.getPrimitive()[i][j] * b.getPrimitive()[i][k]; }
                    s /= w.getPrimitive()[j];         //This is the divide by wj
                }
                tmp[j] = s;
            }
            for (j = 0; j < n; j++) { //Matrix multiply by V to get answer
                s = (float) 0.0;
                for (jj = 0; jj < n; jj++) { s += v.getPrimitive()[j][jj] * tmp[jj]; }
                x.getPrimitive()[j][k] = s;
            }
        }
    }

}

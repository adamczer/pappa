package ub.juav.autopilot.math.functions.polyfit;

import ub.juav.autopilot.math.functions.algebra.PprzAlgebraFloat;
import ub.juav.autopilot.math.functions.matrix.PPrzMatrixDcompFloat;
import ub.juav.autopilot.math.primitive.wrappers.PrimitiveWrapper;

/**
 * Created by adamczer on 1/3/16.
 */
public class PprzPolyfitFloat {

    /** Polynomial regression
     *
     * Polynomial regression is a form of linear regression in which the relationship between
     * the independent variable x and the dependent variable y is modelled as an nth order polynomial.
     *
     * Considering the regression model:
     *  @f[
     *  y_i = c_0 + c_1 x_i + ... + c_p x_i^p + \epsilon_i  (i = 1 ... n)
     *  @f]
     * in matrix form
     *  @f[
     *  y = X.c + \epsilon
     *  @f]
     * where
     *  @f[
     *  X_{ij} = x_i^j (i = 1 ... n; j = 1 ... p)
     *  @f]
     * The vector of estimated polynomial regression coefficients using ordinary least squares estimation is
     *  @f[
     *  c = (X' X)^{-1} X' y
     *  @f]
     *
     * http://en.wikipedia.org/wiki/Polynomial_regression
     * http://fr.wikipedia.org/wiki/R%C3%A9gression_polynomiale
     * http://www.arachnoid.com/sage/polynomial.html
     *
     * @param[in] x pointer to the input array of independent variable X [n]
     * @param[in] y pointer to the input array of dependent variable Y [n]
     * @param[in] n number of input measurments
     * @param[in] p degree of the output polynomial
     * @param[out] c pointer to the output array of polynomial coefficients [p+1]
     */
    public static void pprz_polyfit_float(PrimitiveWrapper<Float[]> x, PrimitiveWrapper<Float[]> y, int n, int p, PrimitiveWrapper<Float[]> c)
    {
        int i, j, k;

        // Instead of solving directly (X'X)^-1 X' y
        // let's build the matrices (X'X) and (X'y)
        // Then element ij in (X'X) matrix is sum_{k=0,n-1} x_k^(i+j)
        // and element i in (X'y) vector is sum_{k=0,n-1} x_k^i * y_k
        // Finally we can solve the linear system (X'X).c = (X'y) using SVD decomposition

        // First build a table of element S_i = sum_{k=0,n-1} x_k^i of dimension 2*p+1
        PrimitiveWrapper<Float[]> S = new PrimitiveWrapper<>(new Float[2 * p + 1]);
        PprzAlgebraFloat.float_vect_zero(S, 2 * p + 1);
        // and a table of element T_i = sum_{k=0,n-1} x_k^i*y_k of dimension p+1
        // make it a matrix for later use
//        float _T[p + 1][1];
//        MAKE_MATRIX_PTR(T, _T, p + 1);
        PrimitiveWrapper<Float[][]> T = new PrimitiveWrapper<>(new Float[p+1][1]);
        PprzAlgebraFloat.float_mat_zero(T, p + 1, 1);
        S.getPrimitive()[0] = (float)n; // S_0 is always the number of input measurements
        for (k = 0; k < n; k++) {
            float x_tmp = x.getPrimitive()[k];
            T.getPrimitive()[0][0] += y.getPrimitive()[k];
            for (i = 1; i < 2 * p + 1; i++) {
                S.getPrimitive()[i] += x_tmp; // add element to S_i
                if (i < p + 1) {
                    T.getPrimitive()[i][0] += x_tmp * y.getPrimitive()[k];  // add element to T_i if i < p+1
                }
                x_tmp *= x.getPrimitive()[k]; // multiply x_tmp by current value of x
            }
        }
        // Then build a [p+1 x p+1] matrix corresponding to (X'X) based on the S_i
        // element ij of (X'X) is S_(i+j)
//        float _XtX[p + 1][p + 1];
//        MAKE_MATRIX_PTR(XtX, _XtX, p + 1);
        PrimitiveWrapper<Float[][]> XtX = new PrimitiveWrapper<>(new Float[p+1][p+1]);
        for (i = 0; i < p + 1; i++) {
            for (j = 0; j < p + 1; j++) {
                XtX.getPrimitive()[i][j] = S.getPrimitive()[i + j];
            }
        }
        // Solve linear system XtX.c = T after performing a SVD decomposition of XtX
        // which is probably a bit overkill but looks really cool
//        float w[p + 1], _v[p + 1][p + 1];
//        MAKE_MATRIX_PTR(v, _v, p + 1);
        PrimitiveWrapper<Float[]> w = new PrimitiveWrapper<>(new Float[p+1]);
        PrimitiveWrapper<Float[][]> v = new PrimitiveWrapper<>(new Float[p+1][p+1]);
        PPrzMatrixDcompFloat.pprz_svd_float(XtX, w, v, p + 1, p + 1);
//        float _c[p + 1][1];
//        MAKE_MATRIX_PTR(c_tmp, _c, p + 1);
        PrimitiveWrapper<Float[][]> c_tmp = new PrimitiveWrapper<>(new Float[p+1][1]);
        PPrzMatrixDcompFloat.pprz_svd_solve_float(c_tmp, XtX, w, v, T, p + 1, p + 1, 1);
        // set output vector
        for (i = 0; i < p + 1; i++) {
            c.getPrimitive()[i] = c_tmp.getPrimitive()[i][0];
        }

    }

}

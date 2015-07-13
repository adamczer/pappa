package ub.juav.autopilot.math.functions;

import ub.juav.autopilot.math.structs.Vect2;
import ub.juav.autopilot.math.structs.doubles.*;
import ub.juav.autopilot.math.structs.ints.IntVect2;

/**
 * Created by adamczer on 7/12/15.
 */
public class PprzAlgebra{

    /************** 2D Vectors *************/
    /* a =  {x, y} */
    public static void VECT2_ASSIGN(Vect2 a, double x, double y) {
        a.setX(x);
        a.setY(y);
    }
    /* a = b */
    public static void VECT2_COPY(DoubleVect2 a, DoubleVect2 b) {
        a.setX(b.getX());
        a.setY(b.getY());
    }
    /* a -= b */
    public static void VECT2_SUB(DoubleVect2 a, DoubleVect2 b) {
        a.setX(a.getX() - b.getX());
        a.setY(a.getY() - b.getY());
    }
    /* a += b */
    public static void VECT2_ADD(DoubleVect2 a, DoubleVect2 b) {
        a.setX(a.getX() + b.getX());
        a.setY(a.getY() + b.getY());
    }
    /* c = a + b */
    public static void VECT2_SUM(DoubleVect2 c, DoubleVect2 a, DoubleVect2 b) {
        c.setX(a.getX()+b.getX());
        c.setY(a.getY()+b.getY());
    }
    /* c = a - b */
    public static void VECT2_DIFF(DoubleVect2 c, DoubleVect2 a, DoubleVect2 b) {
        c.setX(a.getX()-b.getX());
        c.setY(a.getY()-b.getY());
    }
    /* vo = vi * s */
    public static void VECT2_SMUL(DoubleVect2 vo, DoubleVect2 vi, double s) {
        vo.setX(vi.getX()*s);
        vo.setY(vi.getY()*s);
    }
    /* vo =  vi / s */
    public static void VECT2_SDIV(DoubleVect2 vo, DoubleVect2 vi, double s) {
        vo.setX(vi.getX()/s);
        vo.setY(vi.getY()/s);
    }
    /* v = bound (v,min,max)*/
    public static void VECT2_STRIM(DoubleVect2 v, double min, double max) {
        v.setX(v.getX() < min ? min : v.getX() > max ? max : v.getX());
        v.setY(v.getY() < min ? min : v.getY() > max ? max : v.getY());
    }
    /* _vo=v1*v2 */
    public static double VECT2_DOT_PRODUCT(DoubleVect2 v1, DoubleVect2 v2) {
        return v1.getX()*v2.getX() + v1.getY()*v2.getY();
    }

    public static double VECT2_NORM(DoubleVect2 v) {
        return v.getX()+v.getX() + v.getY()*v.getY();
    }
    public static double VECT2_NORM(IntVect2 v) {
        return v.getX()+v.getX() + v.getY()*v.getY();
    }
    /***************3D Vectors *************/
    /* a =  {x, y, z} */
    public static void VECT3_ASSIGN(DoubleVect3 a, double x, double y, double z) {
        a.setX(x);
        a.setY(y);
        a.setZ(z);
    }
    public static void VECT3_ASSIGN_ABS(DoubleVect3 a, double x, double y, double z) {
        a.setX(Math.abs(x));
        a.setY(Math.abs(y));
        a.setZ(Math.abs(z));
    }
    /* a = b */
    public static void VECT3_COPY(DoubleVect3 a, DoubleVect3 b) {
        a.setX(b.getX());
        a.setY(b.getY());
        a.setZ(b.getZ());
    }
    /* a += b */
    public static void VECT3_ADD(DoubleVect3 a, DoubleVect3 b) {
        a.setX(a.getX() + b.getX());
        a.setY(a.getY() + b.getY());
        a.setZ(a.getZ() + b.getZ());
    }
    /* a -= b */
    public static void VECT3_SUB(DoubleVect3 a, DoubleVect3 b) {
        a.setX(a.getX() - b.getX());
        a.setY(a.getY() - b.getY());
        a.setZ(a.getZ() - b.getZ());
    }
    /* c = a + b */
    public static void VECT3_SUM(DoubleVect3 c, DoubleVect3 a, DoubleVect3 b) {
        c.setX(a.getX()+b.getX());
        c.setY(a.getY()+b.getY());
        c.setZ(a.getZ()+b.getZ());
    }
    /* c = a + _s * b */
    public static void VECT3_ADD_SCALED(DoubleVect3 a, DoubleVect3 b, double s) {
        a.setX(a.getX() + b.getX() * s);
        a.setY(a.getY() + b.getY() * s);
        a.setZ(a.getZ() + b.getZ() * s);
    }
    /* c = a + _s * b */
    public static void VECT3_SUM_SCALED(DoubleVect3 c, DoubleVect3 a, DoubleVect3 b, double s) {
        c.setX(a.getX() + b.getX() * s);
        c.setY(a.getY() + b.getY() * s);
        c.setZ(a.getZ() + b.getZ() * s);
    }

    /* c = a - b */
    public static void VECT3_DIFF(DoubleVect3 c, DoubleVect3 a, DoubleVect3 b) {
        c.setX(a.getX()-b.getX());
        c.setY(a.getY() - b.getY());
        c.setZ(a.getZ() - b.getZ());
    }
    /* vo = vi * s */
    public static void VECT3_SMUL(DoubleVect3 vo, DoubleVect3 vi, double s) {
        vo.setX(vi.getX()*s);
        vo.setY(vi.getY() * s);
        vo.setZ(vi.getZ() * s);
    }
    /* vo =  vi / s */
    public static void VECT3_SDIV(DoubleVect3 vo, DoubleVect3 vi, double s) {
        vo.setX(vi.getX()/s);
        vo.setY(vi.getY() / s);
        vo.setZ(vi.getZ() / s);
    }
    /* _v = Bound(_v, _min, _max) */
    public static void VECT3_STRIM(DoubleVect3 v, double min, double max) {
        v.setX(v.getX() < min ? min : v.getX() > max ? max : v.getX());
        v.setY(v.getY() < min ? min : v.getY() > max ? max : v.getY());
        v.setZ(v.getZ() < min ? min : v.getZ() > max ? max : v.getZ());
    }

    public static void VECT3_EW_DIV(DoubleVect3 vo, DoubleVect3 va, DoubleVect3 vb) {
        vo.setX(va.getX() / vb.getX());
        vo.setY(va.getY() / vb.getY());
        vo.setZ(va.getZ() / vb.getZ());
    }

    public static void VECT3_EW_MUL(DoubleVect3 vo, DoubleVect3 va, DoubleVect3 vb) {
        vo.setX(va.getX() * vb.getX());
        vo.setY(va.getY() * vb.getY());
        vo.setZ(va.getZ() * vb.getZ());
    }

    public static void VECT3_BOUND_CUBE(DoubleVect3 v, double min, double max) {
        if(v.getX()>max) v.setX(max); else if (v.getX()<min) v.setX(min);
        if(v.getY()>max) v.setY(max); else if (v.getY()<min) v.setY(min);
        if(v.getZ()>max) v.setZ(max); else if (v.getZ()<min) v.setZ(min);
    }

    public static void VECT3_BOUND_BOX(DoubleVect3 v, DoubleVect3 vMin, DoubleVect3 vMax) {
        if(v.getX() > vMax.getX())
            v.setX(vMax.getX());
        else if(v.getX() < vMin.getX())
            v.setX(vMin.getX());
        if(v.getY() > vMax.getY())
            v.setY(vMax.getY());
        else if(v.getY() < vMin.getY())
            v.setY(vMin.getY());
        if(v.getZ() > vMax.getZ())
            v.setZ(vMax.getZ());
        else if(v.getZ() < vMin.getZ())
            v.setZ(vMin.getZ());
    }

    public static void VECT3_ABS(DoubleVect3 vo, DoubleVect3 vi) {
        vo.setX(Math.abs(vi.getX()));
        vo.setY(Math.abs(vi.getY()));
        vo.setZ(Math.abs(vi.getZ()));
    }

    public static void VECT3_CROSS_PRODUCT(DoubleVect3 vo, DoubleVect3 v1, DoubleVect3 v2) {
        vo.setX(v1.getY()*v2.getZ() - v1.getZ()*v2.getY());
        vo.setY(v1.getZ() * v2.getX() - v1.getX() * v2.getZ());
        vo.setY(v1.getX() * v2.getY() - v1.getY() * v2.getX());
    }

    public static double VECT3_DOT_PRODUCT(DoubleVect3 v1, DoubleVect3 v2) {
        return v1.getX()*v2.getX() + v1.getY()*v2.getY() + v1.getZ()*v2.getZ();
    }

    public static double VECT3_NORM2 (DoubleVect3 v) {
        return v.getX()*v.getX()+v.getY()*v.getY()+v.getZ()*v.getZ();
    }

    public static void VECT3_RATES_CROSS_VECT3(DoubleVect3 vo, DoubleRates r, DoubleVect3 v2) {
        vo.setX(r.getQ() * v2.getZ() - r.getR()*v2.getY());
        vo.setY(r.getR() * v2.getX() - r.getP() * v2.getZ());
        vo.setZ(r.getP() * v2.getY() - r.getQ() * v2.getX());
    }

/********** EULER ANGLES *************/

    public static void EULERS_COPY(DoubleEulers a, DoubleEulers b) {
        a.setPhi(b.getPhi());
        a.setTheta(b.getTheta());
        a.setPsi(b.getPsi());
    }

    public static void EULERS_ASSIGN(DoubleEulers e, double phi, double theta, double psi) {
        e.setPsi(psi);
        e.setTheta(theta);
        e.setPhi(phi);
    }
    /* a += b */
    public static void EULERS_ADD(DoubleEulers a, DoubleEulers b) {
        a.setPhi(a.getPhi() + b.getPhi());
        a.setPsi(a.getPsi() + b.getPsi());
        a.setTheta(a.getTheta() + b.getTheta());
    }
    /* a -= b */
    public static void EULERS_SUB(DoubleEulers a, DoubleEulers b) {
        a.setPhi(a.getPhi() - b.getPhi());
        a.setPsi(a.getPsi() - b.getPsi());
        a.setTheta(a.getTheta() - b.getTheta());
    }
    /* c = a - b */
    public static void EULERS_DIFF(DoubleEulers c, DoubleEulers a, DoubleEulers b) {
        c.setPhi(a.getPhi()-b.getPhi());
        c.setTheta(a.getTheta() - b.getTheta());
        c.setPsi(a.getPsi() - b.getPsi());
    }
    /* _vo =  _vi * _s */
    public static void EULERS_SMUL(DoubleEulers eo, DoubleEulers ei, double s) {
        eo.setPhi(ei.getPhi()*s);
        eo.setTheta(ei.getTheta()*s);
        eo.setPsi(ei.getPsi()*s);
    }
    /* _vo =  _vi / _s */
    public static void EULERS_SDIV(DoubleEulers eo, DoubleEulers ei, double s) {
        eo.setPhi(ei.getPhi()/s);
        eo.setTheta(ei.getTheta()/s);
        eo.setPsi(ei.getPsi()/s);
    }
    /* _v = Bound(_v, _min, _max) */
    public static void  EULERS_BOUND_CUBE(DoubleEulers e, double min, double max) {
        if(e.getPhi()>max) e.setPhi(max); else if (e.getPhi()<min) e.setPhi(min);
        if(e.getTheta()>max) e.setTheta(max); else if (e.getTheta()<min) e.setTheta(min);
        if(e.getPsi()>max) e.setPsi(max); else if (e.getPsi()<min) e.setPsi(min);
    }

    /**************** RATES *****************/
    /* ra =  {p, q, r} */
    public static void RATES_ASSIGN(DoubleRates ra, double p, double q, double r) {
        ra.setP(p);
        ra.setQ(q);
        ra.setR(r);
    }
    /* a = b */
    public static void RATES_COPY(DoubleRates a, DoubleRates b) {
        a.setP(b.getP());
        a.setQ(b.getQ());
        a.setR(b.getR());
    }
    /* a += b */
    public static void RATES_ADD(DoubleRates a, DoubleRates b) {
        a.setP(a.getP()+b.getP());
        a.setQ(a.getQ()+b.getQ());
        a.setR(a.getR()+b.getR());
    }
    /* a += b */
    public static void RATES_SUB(DoubleRates a, DoubleRates b) {
        a.setP(a.getP()-b.getP());
        a.setQ(a.getQ()-b.getQ());
        a.setR(a.getR() - b.getR());
    }
    /* c = a + b */
    public static void RATES_SUM(DoubleRates c, DoubleRates a, DoubleRates b) {
        c.setP(a.getP()+b.getP());
        c.setQ(a.getQ()+b.getQ());
        c.setR(a.getR()+b.getR());
    }
    /* c = a + _s * b */
    public static void RATES_SUM_SCALED(DoubleRates c, DoubleRates a, DoubleRates b, double s) {
        c.setP(a.getP()+b.getP()*s);
        c.setQ(a.getQ()+b.getQ()*s);
        c.setR(a.getR()+b.getR()*s);
    }
    /* c = a - b */
    public static void RATES_DIFF(DoubleRates c, DoubleRates a, DoubleRates b) {
        c.setP(a.getP() - b.getP());
        c.setQ(a.getQ() - b.getQ());
        c.setR(a.getR() - b.getR());
    }
    /* _ro =  _ri * _s */
    public static void RATES_SMUL(DoubleRates ro, DoubleRates ri, double s) {
        ro.setP(ri.getP() * s);
        ro.setQ(ri.getQ() * s);
        ro.setR(ri.getR() * s);
    }
    /* _ro =  _ri / _s */
    public static void RATES_SDIV(DoubleRates ro, DoubleRates ri, double s) {
        ro.setP(ri.getP() / s);
        ro.setQ(ri.getQ() / s);
        ro.setR(ri.getR() / s);
    }
    /* Element wise vector multiplication */ //TODO how to shift double
//    public static void RATES_EWMULT_RSHIFT(DoubleRates c,DoubleRates a, DoubleRates b, int s) {
//        c.setP((a.getP() * b.getP()) >> s);
//        c.setQ((a.getQ() * b.getQ()) >> s);
//        c.setR((a.getR() * b.getR()) >> s);
//    }
    /* r = Bound(r, _min, _max) */
    public static void RATES_BOUND_CUBE(DoubleRates r, double min, double max) {
        r.setP(r.getP()<min ? min : r.getP()>max ? max : r.getP());
        r.setQ(r.getQ() < min ? min : r.getQ()>max ? max : r.getQ());
        r.setR(r.getR() < min ? min : r.getR()>max ? max : r.getR());
    }

    public static void RATES_BOUND_BOX(DoubleRates r, DoubleRates rMin, DoubleRates rMax) {
        if (r.getP() > rMax.getP()) r.setP(rMax.getP()); else if (r.getP() < rMin.getP()) r.setP(rMin.getP());
        if (r.getQ() > rMax.getQ()) r.setQ(rMax.getQ()); else if (r.getQ() < rMin.getQ()) r.setQ(rMin.getQ());
        if (r.getR() > rMax.getR()) r.setR(rMax.getR()); else if (r.getR() < rMin.getR()) r.setR(rMin.getR());
    }

    public static void RATES_ADD_SCALED_VECT(DoubleRates r, DoubleVect3 v , double s) {
        r.setP(r.getP() + v.getX() * s);
        r.setQ(r.getQ() + v.getY() * s);
        r.setR(r.getR() + v.getZ() * s);
    }

    /************* MATRIX **************/

    /**3x3 matrices **/
    // Wont work
//    public static Double MAT33_ELMT(DoubleMat33 m, int row, int col) {
//        return m.getMatrix()[row][col];
//    }

    public static void MAT33_COPY(DoubleMat33 mat1, DoubleMat33 mat2) {
        for(int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                mat1.setElement(mat2.getElement(i,j),i,j);
            }
        }
    }
    /* multiply _vin by _mat, store in _vout */
    public static void MAT33_VECT3_MULT(DoubleVect3 vo, DoubleMat33 mat, DoubleVect3 vi) {
        vo.setX(mat.getElement(0,0) * vi.getX() + mat.getElement(0,1) * vi.getY() + mat.getElement(0, 2) * vi.getZ());
        vo.setY(mat.getElement(1, 0) * vi.getX() + mat.getElement(1, 1) * vi.getY() + mat.getElement(1, 2) * vi.getZ());
        vo.setZ(mat.getElement(2, 0) * vi.getX() + mat.getElement(2, 1) * vi.getY() + mat.getElement(2,2) * vi.getZ());
    }
    /* multiply _vin by transpose of _mat, store in _vout */
    public static void MAT33_VECT3_TRANSP_MUL(DoubleVect3 vo, DoubleMat33 mat, DoubleVect3 vi) {
        vo.setX(mat.getElement(0,0) * vi.getX() + mat.getElement(1,0) * vi.getY() + mat.getElement(2,0) * vi.getZ());
        vo.setY(mat.getElement(0, 1) * vi.getX() + mat.getElement(1, 1) * vi.getY() + mat.getElement(2, 1) * vi.getZ());
        vo.setZ(mat.getElement(0, 2) * vi.getX() + mat.getElement(1, 2) * vi.getY() + mat.getElement(2,2) * vi.getZ());
    }
    //TODO may not be used
    /* invS = 1/det(S) com(S)' */
//    public static void MAT33_INV(DoubleMat33 matInverse, DoubleMat33 mat) {
//        double m00 = mat.getElement(1,1)*mat.getElement(2,2)-mat.getElement(1,2)*mat.getElement(2,1);
//        double m10 = mat.getElement(0,1)*mat.getElement(2,2)-mat.getElement(0,2)*mat.getElement(2,1);
//        double m20 = mat.getElement(0,1)*mat.getElement(1,2)-mat.getElement(0,2)*mat.getElement(1,1);
//        double m01 = mat.getElement(1,0)*mat.getElement(2,2)-mat.getElement(1,2)*mat.getElement(2,0);
//        double m11 = mat.getElement(0,0)*mat.getElement(2,2)-mat.getElement(0,2)*mat.getElement(2,0);
//        double m21 = mat.getElement(0,0)*mat.getElement(1,2)-mat.getElement(0,2)*mat.getElement(1,0);
//        double m02 = mat.getElement(1,0)*mat.getElement(2,1)-mat.getElement(1,1)*mat.getElement(2,0);
//        double m12 = mat.getElement(0,0)*mat.getElement(2,1)-mat.getElement(0,1)*mat.getElement(2,0);
//        double m22 = mat.getElement(0,0)*mat.getElement(1,1)-mat.getElement(0,1)*mat.getElement(1,0);
//        double det = mat.getElement(0,0)*m00 - mat.getElement(1,0)*m10 + mat.getElement(2,0)*m20;
////        if (fabs(det) > FLT_EPSILON) {          \
////            MAT33_ELMT((_minv),0,0) =  m00 / det;           \
////            MAT33_ELMT((_minv),1,0) = -m01 / det;           \
////            MAT33_ELMT((_minv),2,0) =  m02 / det;           \
////            MAT33_ELMT((_minv),0,1) = -m10 / det;           \
////            MAT33_ELMT((_minv),1,1) =  m11 / det;           \
////            MAT33_ELMT((_minv),2,1) = -m12 / det;           \
////            MAT33_ELMT((_minv),0,2) =  m20 / det;           \
////            MAT33_ELMT((_minv),1,2) = -m21 / det;           \
////            MAT33_ELMT((_minv),2,2) =  m22 / det;           \
////        }
//
//    }
    /* set _row of _mat with _vin multiplied by scalar _s */
    public static void MAT33_ROW_VECT3_SMUL(DoubleMat33 mat, int row, DoubleVect3 vi, double s) {
        mat.setElement(vi.getX()*s,row,0);
        mat.setElement(vi.getY()*s,row,1);
        mat.setElement(vi.getZ()*s,row,2);
    }

    /******** QUATERNION ALGEBRAS ********/
    /* _q = [_i _x _y _z] */
    public static void QUAT_ASSIGN(DoubleQuat q, double i, double x, double y, double z) {
        q.setQi(i);
        q.setQx(x);
        q.setQy(y);
        q.setQz(z);
    }
    /* _qc = _qa - _qc */
    public static void QUAT_DIFF(DoubleQuat qc, DoubleQuat qa, DoubleQuat qb) {
        qc.setQi(qa.getQi() - qb.getQi());
        qc.setQx(qa.getQx() - qb.getQx());
        qc.setQy(qa.getQy() - qb.getQy());
        qc.setQz(qa.getQz() - qb.getQz());
    }
    /* _qo = _qi */
    public static void QUAT_COPY(DoubleQuat qo, DoubleQuat qi) {
        qo.setQi(qi.getQi());
        qo.setQx(qi.getQx());
        qo.setQy(qi.getQy());
        qo.setQz(qi.getQz());
    }

    public static void QUAT_EXPLEMENTARY(DoubleQuat b, DoubleQuat a) {
        b.setQi(-a.getQi());
        b.setQx(-a.getQx());
        b.setQy(-a.getQy());
        b.setQz(-a.getQz());
    }
    /* _qo = _qi * _s */
    public static void QUAT_SMUL(DoubleQuat qo, DoubleQuat qi, double s) {
        qo.setQi(qi.getQi() * s);
        qo.setQx(qi.getQx() * s);
        qo.setQy(qi.getQy() * s);
        qo.setQz(qi.getQz() * s);
    }
    /* _qo = _qo + _qi */
    public static void QUAT_ADD(DoubleQuat qo, DoubleQuat qi) {
        qo.setQi(qo.getQi()+qi.getQi());
        qo.setQx(qo.getQx()+qi.getQx());
        qo.setQy(qo.getQy()+qi.getQy());
        qo.setQz(qo.getQz()+qi.getQz());
    }
    /* _qo = [qi -qx -qy -qz] */
    public static void QUAT_INVERT(DoubleQuat qo, DoubleQuat qi) {
        qo.setQi(qi.getQi());
        qo.setQx(-qi.getQx());
        qo.setQy(-qi.getQy());
        qo.setQz(-qi.getQz());
    }
    /* _vo=[ qx qy qz] */
    public static void QUAT_EXTRACT_Q(DoubleVect3 vo, DoubleQuat qi) {
        vo.setX(qi.getQx());
        vo.setY(qi.getQy());
        vo.setZ(qi.getQz());
    }
    /* _qo = _qo / _s */
    public static void QUAT_SDIV(DoubleQuat qo, DoubleQuat qi, double s) {
        qo.setQi(qi.getQi() / s);
        qo.setQx(qi.getQx() / s);
        qo.setQy(qi.getQy() / s);
        qo.setQz(qi.getQz() / s);
    }

    /************* ROTATION MATRICIES **************/

    /* accessor : row and col range from 0 to 2 */
    // not needed
    //    #define RMAT_ELMT(_rm, _row, _col) MAT33_ELMT(_rm, _row, _col)

    public static double RMAT_TRACE(DoubleRMat rMat) {
        return rMat.getElement(0,0)+rMat.getElement(1,1)+rMat.getElement(2,2);
    }
    //TODO does this seem correct?
    public static void RMAT_DIFF(DoubleRMat c, DoubleRMat a, DoubleRMat b) {
        for(int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                c.setElement(a.getElement(i,j)-b.getElement(i,j),i,j);
            }
        }
    }

    public static void RAMT_VECT3_MUL(DoubleVect3 vo, DoubleRMat rmat, DoubleVect3 vi) {
        MAT33_VECT3_MULT(vo,rmat,vi);
    }

    public static void RMAT_VECT3_TRANSP_MUL(DoubleVect3 vo, DoubleRMat rmat, DoubleVect3 vi) {
        MAT33_VECT3_TRANSP_MUL(vo,rmat,vi);
    }

    public static void RMAT_COPY(DoubleRMat o, DoubleRMat i) {
        MAT33_COPY(o,i);
    }

    /********MORE Todo these seem to depend on algebra int********/
//    #define EULERS_FLOAT_OF_BFP(_ef, _ei) {     \
//        (_ef).phi   = ANGLE_FLOAT_OF_BFP((_ei).phi);  \
//        (_ef).theta = ANGLE_FLOAT_OF_BFP((_ei).theta);  \
//        (_ef).psi   = ANGLE_FLOAT_OF_BFP((_ei).psi);  \
//    }
//
//    #define EULERS_BFP_OF_REAL(_ei, _ef) {      \
//        (_ei).phi   = ANGLE_BFP_OF_REAL((_ef).phi);   \
//        (_ei).theta = ANGLE_BFP_OF_REAL((_ef).theta); \
//        (_ei).psi   = ANGLE_BFP_OF_REAL((_ef).psi);   \
//    }
//
//    #define RMAT_BFP_OF_REAL(_ei, _ef) {      \
//        (_ei).m[0] = TRIG_BFP_OF_REAL((_ef).m[0]);    \
//        (_ei).m[1] = TRIG_BFP_OF_REAL((_ef).m[1]);    \
//        (_ei).m[2] = TRIG_BFP_OF_REAL((_ef).m[2]);    \
//        (_ei).m[3] = TRIG_BFP_OF_REAL((_ef).m[3]);    \
//        (_ei).m[4] = TRIG_BFP_OF_REAL((_ef).m[4]);    \
//        (_ei).m[5] = TRIG_BFP_OF_REAL((_ef).m[5]);    \
//        (_ei).m[6] = TRIG_BFP_OF_REAL((_ef).m[6]);    \
//        (_ei).m[7] = TRIG_BFP_OF_REAL((_ef).m[7]);    \
//        (_ei).m[8] = TRIG_BFP_OF_REAL((_ef).m[8]);    \
//    }
//
//    #define RMAT_FLOAT_OF_BFP(_ef, _ei) {     \
//        (_ef).m[0] = TRIG_FLOAT_OF_BFP((_ei).m[0]);   \
//        (_ef).m[1] = TRIG_FLOAT_OF_BFP((_ei).m[1]);   \
//        (_ef).m[2] = TRIG_FLOAT_OF_BFP((_ei).m[2]);   \
//        (_ef).m[3] = TRIG_FLOAT_OF_BFP((_ei).m[3]);   \
//        (_ef).m[4] = TRIG_FLOAT_OF_BFP((_ei).m[4]);   \
//        (_ef).m[5] = TRIG_FLOAT_OF_BFP((_ei).m[5]);   \
//        (_ef).m[6] = TRIG_FLOAT_OF_BFP((_ei).m[6]);   \
//        (_ef).m[7] = TRIG_FLOAT_OF_BFP((_ei).m[7]);   \
//        (_ef).m[8] = TRIG_FLOAT_OF_BFP((_ei).m[8]);   \
//    }
//
//    #define QUAT_FLOAT_OF_BFP(_qf, _qi) {     \
//        (_qf).qi = QUAT1_FLOAT_OF_BFP((_qi).qi);    \
//        (_qf).qx = QUAT1_FLOAT_OF_BFP((_qi).qx);    \
//        (_qf).qy = QUAT1_FLOAT_OF_BFP((_qi).qy);    \
//        (_qf).qz = QUAT1_FLOAT_OF_BFP((_qi).qz);    \
//    }
//
//    #define QUAT_BFP_OF_REAL(_qi, _qf) {      \
//        (_qi).qi = QUAT1_BFP_OF_REAL((_qf).qi);   \
//        (_qi).qx = QUAT1_BFP_OF_REAL((_qf).qx);   \
//        (_qi).qy = QUAT1_BFP_OF_REAL((_qf).qy);   \
//        (_qi).qz = QUAT1_BFP_OF_REAL((_qf).qz);   \
//    }
//
//    #define RATES_FLOAT_OF_BFP(_rf, _ri) {      \
//        (_rf).p = RATE_FLOAT_OF_BFP((_ri).p);   \
//        (_rf).q = RATE_FLOAT_OF_BFP((_ri).q);   \
//        (_rf).r = RATE_FLOAT_OF_BFP((_ri).r);   \
//    }
//
//    #define RATES_BFP_OF_REAL(_ri, _rf) {     \
//        (_ri).p = RATE_BFP_OF_REAL((_rf).p);    \
//        (_ri).q = RATE_BFP_OF_REAL((_rf).q);    \
//        (_ri).r = RATE_BFP_OF_REAL((_rf).r);    \
//    }
//
//    #define POSITIONS_FLOAT_OF_BFP(_ef, _ei) {      \
//        (_ef).x = POS_FLOAT_OF_BFP((_ei).x);    \
//        (_ef).y = POS_FLOAT_OF_BFP((_ei).y);    \
//        (_ef).z = POS_FLOAT_OF_BFP((_ei).z);    \
//    }
//
//    #define POSITIONS_BFP_OF_REAL(_ef, _ei) { \
//        (_ef).x = POS_BFP_OF_REAL((_ei).x);   \
//        (_ef).y = POS_BFP_OF_REAL((_ei).y);   \
//        (_ef).z = POS_BFP_OF_REAL((_ei).z);   \
//    }
//
//    #define SPEEDS_FLOAT_OF_BFP(_ef, _ei) {     \
//        (_ef).x = SPEED_FLOAT_OF_BFP((_ei).x);    \
//        (_ef).y = SPEED_FLOAT_OF_BFP((_ei).y);    \
//        (_ef).z = SPEED_FLOAT_OF_BFP((_ei).z);    \
//    }
//
//    #define SPEEDS_BFP_OF_REAL(_ef, _ei) {      \
//        (_ef).x = SPEED_BFP_OF_REAL((_ei).x);   \
//        (_ef).y = SPEED_BFP_OF_REAL((_ei).y);   \
//        (_ef).z = SPEED_BFP_OF_REAL((_ei).z);   \
//    }
//
//    #define ACCELS_FLOAT_OF_BFP(_ef, _ei) {     \
//        (_ef).x = ACCEL_FLOAT_OF_BFP((_ei).x);    \
//        (_ef).y = ACCEL_FLOAT_OF_BFP((_ei).y);    \
//        (_ef).z = ACCEL_FLOAT_OF_BFP((_ei).z);    \
//    }
//
//    #define ACCELS_BFP_OF_REAL(_ef, _ei) {      \
//        (_ef).x = ACCEL_BFP_OF_REAL((_ei).x);   \
//        (_ef).y = ACCEL_BFP_OF_REAL((_ei).y);   \
//        (_ef).z = ACCEL_BFP_OF_REAL((_ei).z);   \
//    }
//
//    #define MAGS_FLOAT_OF_BFP(_ef, _ei) {     \
//        (_ef).x = MAG_FLOAT_OF_BFP((_ei).x);    \
//        (_ef).y = MAG_FLOAT_OF_BFP((_ei).y);    \
//        (_ef).z = MAG_FLOAT_OF_BFP((_ei).z);    \
//    }
//
//    #define MAGS_BFP_OF_REAL(_ef, _ei) {      \
//        (_ef).x = MAG_BFP_OF_REAL((_ei).x);   \
//        (_ef).y = MAG_BFP_OF_REAL((_ei).y);   \
//        (_ef).z = MAG_BFP_OF_REAL((_ei).z);   \
//    }



}

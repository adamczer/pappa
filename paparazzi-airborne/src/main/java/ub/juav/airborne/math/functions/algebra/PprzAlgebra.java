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

package ub.juav.airborne.math.functions.algebra;

import ub.juav.airborne.math.structs.algebra.*;
import ub.juav.airborne.math.util.NumberMath;

/**
 * Created by adamczer on 7/12/15.
 */
public class PprzAlgebra{

    /************** 2D Vectors *************/
    /* a =  {x, y} */
    public static void VECT2_ASSIGN(Vect2 a, Number x, Number y) {
        a.setX(x);
        a.setY(y);
    }
    /* a = b */
    public static void VECT2_COPY(Vect2 a, Vect2 b) {
        a.setX(b.getX());
        a.setY(b.getY());
    }
    /* a -= b */
    public static void VECT2_SUB(Vect2 a, Vect2 b) {
        a.setX(NumberMath.sub(a.getX(), b.getX()));
        a.setY(NumberMath.sub(a.getY(), b.getY()));
    }
    /* a += b */
    public static void VECT2_ADD(Vect2 a, Vect2 b) {
        a.setX(NumberMath.add(a.getX(), b.getX()));
        a.setY(NumberMath.add(a.getY() , b.getY()));
    }
    /* c = a + b */
    public static void VECT2_SUM(Vect2 c, Vect2 a, Vect2 b) {
        c.setX(NumberMath.add(a.getX(),b.getX()));
        c.setY(NumberMath.add(a.getY(),b.getY()));
    }
    /* c = a - b */
    public static void VECT2_DIFF(Vect2 c, Vect2 a, Vect2 b) {
        c.setX(NumberMath.sub(a.getX(), b.getX()));
        c.setY(NumberMath.sub(a.getY(), b.getY()));
    }
    /* vo = vi * s */
    public static void VECT2_SMUL(Vect2 vo, Vect2 vi, Number s) {
        vo.setX(NumberMath.mul(vi.getX(), s));
        vo.setY(NumberMath.mul(vi.getY(), s));
    }
    /* vo =  vi / s */
    public static void VECT2_SDIV(Vect2 vo, Vect2 vi, Number s) {
        vo.setX(NumberMath.div(vi.getX(),s));
        vo.setY(NumberMath.div(vi.getY(),s));
    }
    /* v = bound (v,min,max)*/
    public static void VECT2_STRIM(Vect2 v, Number min, Number max) {
        v.setX(NumberMath.lessThan(v.getX() , min) ? min : NumberMath.greaterThan(v.getX() , max) ? max : v.getX());
        v.setY(NumberMath.lessThan(v.getY() , min) ? min : NumberMath.greaterThan(v.getY() , max) ? max : v.getY());
    }
    /* _vo=v1*v2 */
    public static Number VECT2_DOT_PRODUCT(Vect2 v1, Vect2 v2) {
        return NumberMath.add(NumberMath.mul(v1.getX(),v2.getX()) , NumberMath.mul(v1.getY(),v2.getY()));
    }

    public static Number VECT2_NORM2(Vect2 v) {
        return NumberMath.add(NumberMath.mul(v.getX(),v.getX()) , NumberMath.mul(v.getY(),v.getY()));
    }

    public static Number VECT2_NORM(Vect2 v) {
        return NumberMath.sqrt(VECT2_NORM2(v));
    }
    /***************3D Vectors *************/
    /* a =  {x, y, z} */
    public static void VECT3_ASSIGN(Vect3 a, Number x, Number y, Number z) {
        a.setX(x);
        a.setY(y);
        a.setZ(z);
    }
    public static void VECT3_ASSIGN_ABS(Vect3 a, Number x, Number y, Number z) {
        a.setX(NumberMath.abs(x));
        a.setY(NumberMath.abs(y));
        a.setZ(NumberMath.abs(z));
    }
    /* a = b */
    public static void VECT3_COPY(Vect3 a, Vect3 b) {
        a.setX(b.getX());
        a.setY(b.getY());
        a.setZ(b.getZ());
    }
    /* a += b */
    public static void VECT3_ADD(Vect3 a, Vect3 b) {
        a.setX(NumberMath.add(a.getX() , b.getX()));
        a.setY(NumberMath.add(a.getY() , b.getY()));
        a.setZ(NumberMath.add(a.getZ() , b.getZ()));
    }
    /* a -= b */
    public static void VECT3_SUB(Vect3 a, Vect3 b) {
        a.setX(NumberMath.sub(a.getX() , b.getX()));
        a.setY(NumberMath.sub(a.getY() , b.getY()));
        a.setZ(NumberMath.sub(a.getZ() , b.getZ()));
    }
    /* c = a + b */
    public static void VECT3_SUM(Vect3 c, Vect3 a, Vect3 b) {
        c.setX(NumberMath.add(a.getX(),b.getX()));
        c.setY(NumberMath.add(a.getY(),b.getY()));
        c.setZ(NumberMath.add(a.getZ(),b.getZ()));
    }
    /* c = a + _s * b */
    public static void VECT3_ADD_SCALED(Vect3 a, Vect3 b, Number s) {
        a.setX(NumberMath.add(a.getX() , NumberMath.mul(b.getX(), s)));
        a.setY(NumberMath.add(a.getY() , NumberMath.mul(b.getY() , s)));
        a.setZ(NumberMath.add(a.getZ() , NumberMath.mul(b.getZ() , s)));
    }
    /* c = a + _s * b */
    public static void VECT3_SUM_SCALED(Vect3 c, Vect3 a, Vect3 b, Number s) {
        c.setX(NumberMath.add(a.getX() , NumberMath.mul(b.getX(), s)));
        c.setY(NumberMath.add(a.getY() , NumberMath.mul(b.getY() , s)));
        c.setZ(NumberMath.add(a.getZ() , NumberMath.mul(b.getZ() , s)));
    }

    /* c = a - b */
    public static void VECT3_DIFF(Vect3 c, Vect3 a, Vect3 b) {
        c.setX(NumberMath.sub(a.getX(),b.getX()));
        c.setY(NumberMath.sub(a.getY() , b.getY()));
        c.setZ(NumberMath.sub(a.getZ() , b.getZ()));
    }
    /* vo = vi * s */
    public static void VECT3_SMUL(Vect3 vo, Vect3 vi, Number s) {
        vo.setX(NumberMath.mul(vi.getX(),s));
        vo.setY(NumberMath.mul(vi.getY() , s));
        vo.setZ(NumberMath.mul(vi.getZ() , s));
    }

    public static void main(String[] args) {
        Vect3<Double> v1 = new Vect3<>();
        System.out.println(v1.getX().getClass());
    }
    /* vo =  vi / s */
    public static void VECT3_SDIV(Vect3 vo, Vect3 vi, Number s) {
        vo.setX(NumberMath.div(vi.getX(), s));
        vo.setY(NumberMath.div(vi.getY(), s));
        vo.setZ(NumberMath.div(vi.getZ(), s));
    }
    /* _v = Bound(_v, _min, _max) */
    public static void VECT3_STRIM(Vect3 v, Number min, Number max) {
        v.setX(NumberMath.lessThan(v.getX() , min) ? min : NumberMath.greaterThan(v.getX() , max) ? max : v.getX());
        v.setY(NumberMath.lessThan(v.getY() , min) ? min : NumberMath.greaterThan(v.getY() , max) ? max : v.getY());
        v.setZ(NumberMath.lessThan(v.getZ() , min )? min : NumberMath.greaterThan(v.getZ() , max) ? max : v.getZ());
    }

    public static void VECT3_EW_DIV(Vect3 vo, Vect3 va, Vect3 vb) {
        vo.setX(NumberMath.div(va.getX() , vb.getX()));
        vo.setY(NumberMath.div(va.getY() , vb.getY()));
        vo.setZ(NumberMath.div(va.getZ() , vb.getZ()));
    }

    public static void VECT3_EW_MUL(Vect3 vo, Vect3 va, Vect3 vb) {
        vo.setX(NumberMath.mul(va.getX(), vb.getX()));
        vo.setY(NumberMath.mul(va.getY(), vb.getY()));
        vo.setZ(NumberMath.mul(va.getZ(), vb.getZ()));
    }

    public static void VECT3_BOUND_CUBE(Vect3 v, Number min, Number max) {
        if(NumberMath.greaterThan(v.getX(),max)) v.setX(max); else if (NumberMath.lessThan(v.getX(),min)) v.setX(min);
        if(NumberMath.greaterThan(v.getY(),max)) v.setY(max); else if (NumberMath.lessThan(v.getY(),min)) v.setY(min);
        if(NumberMath.greaterThan(v.getZ(),max)) v.setZ(max); else if (NumberMath.lessThan(v.getZ(),min)) v.setZ(min);
    }

    public static void VECT3_BOUND_BOX(Vect3 v, Vect3 vMin, Vect3 vMax) {
        if(NumberMath.greaterThan(v.getX() , vMax.getX()))
            v.setX(vMax.getX());
        else if(NumberMath.lessThan(v.getX() , vMin.getX()))
            v.setX(vMin.getX());
        if(NumberMath.greaterThan(v.getY() , vMax.getY()))
            v.setY(vMax.getY());
        else if(NumberMath.lessThan(v.getY() , vMin.getY()))
            v.setY(vMin.getY());
        if(NumberMath.greaterThan(v.getZ() , vMax.getZ()))
            v.setZ(vMax.getZ());
        else if(NumberMath.lessThan(v.getZ() , vMin.getZ()))
            v.setZ(vMin.getZ());
    }

    public static void VECT3_ABS(Vect3 vo, Vect3 vi) {
        vo.setX(NumberMath.abs(vi.getX()));
        vo.setY(NumberMath.abs(vi.getY()));
        vo.setZ(NumberMath.abs(vi.getZ()));
    }

    public static void VECT3_CROSS_PRODUCT(Vect3 vo, Vect3 v1, Vect3 v2) {
        vo.setX(NumberMath.sub(NumberMath.mul(v1.getY(), v2.getZ()) , NumberMath.mul(v1.getZ(), v2.getY())));
        vo.setY(NumberMath.sub(NumberMath.mul(v1.getZ(), v2.getX()) , NumberMath.mul(v1.getX(), v2.getZ())));
        vo.setY(NumberMath.sub(NumberMath.mul(v1.getX(), v2.getY()) , NumberMath.mul(v1.getY(), v2.getX())));
    }

    public static Number VECT3_DOT_PRODUCT(Vect3 v1, Vect3 v2) {
        return NumberMath.sum(NumberMath.mul(v1.getX(), v2.getX()), NumberMath.mul(v1.getY(), v2.getY()), NumberMath.mul(v1.getZ(), v2.getZ()));
    }

//    public static Float VECT3_DOT_PRODUCT(Vect3<Float> v1, Vect3<Float> v2) {
//        return VECT3_DOT_PRODUCT((Vect3)v1,(Vect3)v2).floatValue();
//    }

    public static Number VECT3_NORM2 (Vect3 v) {
        return NumberMath.sum(NumberMath.sq(v.getX()),NumberMath.sq(v.getY()),NumberMath.sq(v.getZ()));
    }

    public static Number VECT3_NORM (Vect3 v) {
        return NumberMath.sqrt(VECT3_NORM2(v));
    }

//    public static Float VECT3_NORM (FloatVect3 v) {
//        return VECT3_NORM((Vect3)v).floatValue();
//    }

    public static void VECT3_RATES_CROSS_VECT3(Vect3 vo, Rates r, Vect3 v2) {
        vo.setX(NumberMath.sub(NumberMath.mul(r.getQ() , v2.getZ()), NumberMath.mul(r.getR(), v2.getY())));
        vo.setY(NumberMath.sub(NumberMath.mul(r.getR() , v2.getX()), NumberMath.mul(r.getP(), v2.getZ())));
        vo.setZ(NumberMath.sub(NumberMath.mul(r.getP() , v2.getY()), NumberMath.mul(r.getQ(), v2.getX())));
    }

/********** EULER ANGLES *************/

    public static void EULERS_COPY(Eulers a, Eulers b) {
        a.setPhi(b.getPhi());
        a.setTheta(b.getTheta());
        a.setPsi(b.getPsi());
    }

    public static void EULERS_ASSIGN(Eulers e, Number phi, Number theta, Number psi) {
        e.setPsi(psi);
        e.setTheta(theta);
        e.setPhi(phi);
    }

    public static void INT_EULERS_ZERO(Eulers e) {
        e.setPsi(0);
        e.setTheta(0);
        e.setPhi(0);
    }
    /* a += b */
    public static void EULERS_ADD(Eulers a, Eulers b) {
        a.setPhi(NumberMath.add(a.getPhi(), b.getPhi()));
        a.setPsi(NumberMath.add(a.getPsi(), b.getPsi()));
        a.setTheta(NumberMath.add(a.getTheta(), b.getTheta()));
    }
    /* a -= b */
    public static void EULERS_SUB(Eulers a, Eulers b) {
        a.setPhi(NumberMath.sub(a.getPhi() , b.getPhi()));
        a.setPsi(NumberMath.sub(a.getPsi() , b.getPsi()));
        a.setTheta(NumberMath.sub(a.getTheta() , b.getTheta()));
    }
    /* c = a - b */
    public static void EULERS_DIFF(Eulers c, Eulers a, Eulers b) {
        c.setPhi(NumberMath.sub(a.getPhi() , b.getPhi()));
        c.setPsi(NumberMath.sub(a.getPsi(), b.getPsi()));
        c.setTheta(NumberMath.sub(a.getTheta(), b.getTheta()));
    }
    /* _vo =  _vi * _s */
    public static void EULERS_SMUL(Eulers eo, Eulers ei, Number s) {
        eo.setPhi(NumberMath.mul(ei.getPhi(),s));
        eo.setTheta(NumberMath.mul(ei.getTheta(), s));
        eo.setPsi(NumberMath.mul(ei.getPsi(),s));
    }
    /* _vo =  _vi / _s */
    public static void EULERS_SDIV(Eulers eo, Eulers ei, Number s) {
        eo.setPhi(NumberMath.div(ei.getPhi(), s));
        eo.setTheta(NumberMath.div(ei.getTheta(), s));
        eo.setPsi(NumberMath.div(ei.getPsi(), s));
    }
    /* _v = Bound(_v, _min, _max) */
    public static void  EULERS_BOUND_CUBE(Eulers e, Number min, Number max) {
        if(NumberMath.greaterThan(e.getPhi(),max)) e.setPhi(max); else if (NumberMath.lessThan(e.getPhi() , min)) e.setPhi(min);
        if(NumberMath.greaterThan(e.getTheta(),max)) e.setTheta(max); else if (NumberMath.lessThan(e.getTheta(),min)) e.setTheta(min);
        if(NumberMath.greaterThan(e.getPsi(),max)) e.setPsi(max); else if (NumberMath.lessThan(e.getPsi(),min)) e.setPsi(min);
    }

    /**************** RATES *****************/
    /* ra =  {p, q, r} */
    public static void RATES_ASSIGN(Rates ra, Number p, Number q, Number r) {
        ra.setP(p);
        ra.setQ(q);
        ra.setR(r);
    }
    /* a = b */
    public static void RATES_COPY(Rates a, Rates b) {
        a.setP(b.getP());
        a.setQ(b.getQ());
        a.setR(b.getR());
    }
    /* a += b */
    public static void RATES_ADD(Rates a, Rates b) {
        a.setP(NumberMath.add(a.getP(),b.getP()));
        a.setQ(NumberMath.add(a.getQ(),b.getQ()));
        a.setR(NumberMath.add(a.getR(),b.getR()));
    }
    /* a += b */
    public static void RATES_SUB(Rates a, Rates b) {
        a.setP(NumberMath.sub(a.getP(), b.getP()));
        a.setQ(NumberMath.sub(a.getQ(), b.getQ()));
        a.setR(NumberMath.sub(a.getR(), b.getR()));
    }
    /* c = a + b */
    public static void RATES_SUM(Rates c, Rates a, Rates b) {
        c.setP(NumberMath.add(a.getP(),b.getP()));
        c.setQ(NumberMath.add(a.getQ(),b.getQ()));
        c.setR(NumberMath.add(a.getR(),b.getR()));
    }
    /* c = a + _s * b */
    public static void RATES_SUM_SCALED(Rates c, Rates a, Rates b, Number s) {
        c.setP(NumberMath.add(a.getP(),NumberMath.mul(b.getP(),s)));
        c.setQ(NumberMath.add(a.getQ(),NumberMath.mul(b.getQ(),s)));
        c.setR(NumberMath.add(a.getR(),NumberMath.mul(b.getR(),s)));
    }
    /* c = a - b */
    public static void RATES_DIFF(Rates c, Rates a, Rates b) {
        c.setP(NumberMath.sub(a.getP(), b.getP()));
        c.setQ(NumberMath.sub(a.getQ(), b.getQ()));
        c.setR(NumberMath.sub(a.getR(), b.getR()));
    }
    /* _ro =  _ri * _s */
    public static void RATES_SMUL(Rates ro, Rates ri, Number s) {
        ro.setP(NumberMath.mul(ri.getP() , s));
        ro.setQ(NumberMath.mul(ri.getQ() , s));
        ro.setR(NumberMath.mul(ri.getR() , s));
    }
    /* _ro =  _ri / _s */
    public static void RATES_SDIV(Rates ro, Rates ri, Number s) {
        ro.setP(NumberMath.div(ri.getP(), s));
        ro.setQ(NumberMath.div(ri.getQ(), s));
        ro.setR(NumberMath.div(ri.getR(), s));
    }
    /* Element wise vector multiplication */ //TODO how to shift Number
//    public static void RATES_EWMULT_RSHIFT(Rates c,Rates a, Rates b, int s) {
//        c.setP((a.getP() * b.getP()) >> s);
//        c.setQ((a.getQ() * b.getQ()) >> s);
//        c.setR((a.getR() * b.getR()) >> s);
//    }
    /* r = Bound(r, _min, _max) */
    public static void RATES_BOUND_CUBE(Rates r, Number min, Number max) {
        r.setP(NumberMath.lessThan(r.getP(),min) ? min : NumberMath.greaterThan(r.getP(),max) ? max : r.getP());
        r.setQ(NumberMath.lessThan(r.getQ(), min) ? min : NumberMath.greaterThan(r.getQ(),max) ? max : r.getQ());
        r.setR(NumberMath.lessThan(r.getR(), min) ? min : NumberMath.greaterThan(r.getR(),max) ? max : r.getR());
    }

    public static void RATES_BOUND_BOX(Rates r, Rates rMin, Rates rMax) {
        if (NumberMath.greaterThan(r.getP(), rMax.getP())) r.setP(rMax.getP()); else if (NumberMath.lessThan(r.getP(), rMin.getP())) r.setP(rMin.getP());
        if (NumberMath.greaterThan(r.getQ(), rMax.getQ())) r.setQ(rMax.getQ()); else if (NumberMath.lessThan(r.getQ(), rMin.getQ())) r.setQ(rMin.getQ());
        if (NumberMath.greaterThan(r.getR(), rMax.getR())) r.setR(rMax.getR()); else if (NumberMath.lessThan(r.getR(), rMin.getR())) r.setR(rMin.getR());
    }

    public static void RATES_ADD_SCALED_VECT(Rates r, Vect3 v , Number s) {
        r.setP(NumberMath.add(r.getP(), NumberMath.mul(v.getX(), s)));
        r.setQ(NumberMath.add(r.getQ(), NumberMath.mul(v.getY(), s)));
        r.setR(NumberMath.add(r.getR(), NumberMath.mul(v.getZ(), s)));
    }

    /************* MATRIX **************/

    /**3x3 matrices **/
    // Wont work
//    public static Double MAT33_ELMT(Mat33 m, int row, int col) {
//        return m.getMatrix()[row][col];
//    }
    public static void MAT33_DIAG(Mat33 mat, Number d00,Number d11,Number d22) {
        mat.zero();
        mat.setElement(d00, 0, 0);
        mat.setElement(d11,1,1);
        mat.setElement(d22,2,2);
    }

    public static void MAT33_COPY(Mat33 mat1, Mat33 mat2) {
        for(int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                mat1.setElement(mat2.getElement(i,j),i,j);
            }
        }
    }
    /* multiply _vin by _mat, store in _vout */
    public static void MAT33_VECT3_MULT(Vect3 vo, Mat33 mat, Vect3 vi) {
        vo.setX(NumberMath.sum(NumberMath.mul(mat.getElement(0,0), vi.getX()), NumberMath.mul(mat.getElement(0,1), vi.getY()), NumberMath.mul(mat.getElement(0, 2), vi.getZ())));
        vo.setY(NumberMath.sum(NumberMath.mul(mat.getElement(1, 0), vi.getX()), NumberMath.mul(mat.getElement(1, 1), vi.getY()), NumberMath.mul(mat.getElement(1, 2), vi.getZ())));
        vo.setZ(NumberMath.sum(NumberMath.mul(mat.getElement(2, 0), vi.getX()), NumberMath.mul(mat.getElement(2, 1), vi.getY()), NumberMath.mul(mat.getElement(2,2), vi.getZ())));
    }

    /* multiply _vin by transpose of _mat, store in _vout */
    public static void MAT33_VECT3_TRANSP_MUL(Vect3 vo, Mat33 mat, Vect3 vi) {
        vo.setX(NumberMath.sum(NumberMath.mul(mat.getElement(0,0) , vi.getX()) , NumberMath.mul(mat.getElement(1,0), vi.getY()), NumberMath.mul(mat.getElement(2,0), vi.getZ())));
        vo.setY(NumberMath.sum(NumberMath.mul(mat.getElement(0, 1) , vi.getX()) , NumberMath.mul(mat.getElement(1, 1), vi.getY()), NumberMath.mul(mat.getElement(2, 1), vi.getZ())));
        vo.setZ(NumberMath.sum(NumberMath.mul(mat.getElement(0, 2) , vi.getX()) , NumberMath.mul(mat.getElement(1, 2), vi.getY()), NumberMath.mul(mat.getElement(2, 2), vi.getZ())));
    }
    //TODO may not be used
    /* invS = 1/det(S) com(S)' */
//    public static void MAT33_INV(Mat33 matInverse, Mat33 mat) {
//        Number m00 = mat.getElement(1,1)*mat.getElement(2,2)-mat.getElement(1,2)*mat.getElement(2,1);
//        Number m10 = mat.getElement(0,1)*mat.getElement(2,2)-mat.getElement(0,2)*mat.getElement(2,1);
//        Number m20 = mat.getElement(0,1)*mat.getElement(1,2)-mat.getElement(0,2)*mat.getElement(1,1);
//        Number m01 = mat.getElement(1,0)*mat.getElement(2,2)-mat.getElement(1,2)*mat.getElement(2,0);
//        Number m11 = mat.getElement(0,0)*mat.getElement(2,2)-mat.getElement(0,2)*mat.getElement(2,0);
//        Number m21 = mat.getElement(0,0)*mat.getElement(1,2)-mat.getElement(0,2)*mat.getElement(1,0);
//        Number m02 = mat.getElement(1,0)*mat.getElement(2,1)-mat.getElement(1,1)*mat.getElement(2,0);
//        Number m12 = mat.getElement(0,0)*mat.getElement(2,1)-mat.getElement(0,1)*mat.getElement(2,0);
//        Number m22 = mat.getElement(0,0)*mat.getElement(1,1)-mat.getElement(0,1)*mat.getElement(1,0);
//        Number det = mat.getElement(0,0)*m00 - mat.getElement(1,0)*m10 + mat.getElement(2,0)*m20;
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
    public static void MAT33_ROW_VECT3_SMUL(Mat33 mat, int row, Vect3 vi, Number s) {
        mat.setElement(NumberMath.mul(vi.getX(),s),row,0);
        mat.setElement(NumberMath.mul(vi.getY(),s),row,1);
        mat.setElement(NumberMath.mul(vi.getZ(),s),row,2);
    }

    /******** QUATERNION ALGEBRAS ********/
    /* _q = [_i _x _y _z] */
    public static void QUAT_ASSIGN(Quat q, Number i, Number x, Number y, Number z) {
        q.setQi(i);
        q.setQx(x);
        q.setQy(y);
        q.setQz(z);
    }
    /* _qc = _qa - _qc */
    public static void QUAT_DIFF(Quat qc, Quat qa, Quat qb) {
        qc.setQi(NumberMath.sub(qa.getQi(), qb.getQi()));
        qc.setQx(NumberMath.sub(qa.getQx(), qb.getQx()));
        qc.setQy(NumberMath.sub(qa.getQy(), qb.getQy()));
        qc.setQz(NumberMath.sub(qa.getQz(), qb.getQz()));
    }
    /* _qo = _qi */
    public static void QUAT_COPY(Quat qo, Quat qi) {
        qo.setQi(qi.getQi());
        qo.setQx(qi.getQx());
        qo.setQy(qi.getQy());
        qo.setQz(qi.getQz());
    }

    public static void QUAT_EXPLEMENTARY(Quat b, Quat a) {
        b.setQi(NumberMath.negate(a.getQi()));
        b.setQx(NumberMath.negate(a.getQx()));
        b.setQy(NumberMath.negate(a.getQy()));
        b.setQz(NumberMath.negate(a.getQz()));
    }
    /* _qo = _qi * _s */
    public static void QUAT_SMUL(Quat qo, Quat qi, Number s) {
        qo.setQi(NumberMath.mul(qi.getQi(), s));
        qo.setQx(NumberMath.mul(qi.getQx(), s));
        qo.setQy(NumberMath.mul(qi.getQy(), s));
        qo.setQz(NumberMath.mul(qi.getQz(), s));
    }
    /* _qo = _qo + _qi */
    public static void QUAT_ADD(Quat qo, Quat qi) {
        qo.setQi(NumberMath.add(qo.getQi(),qi.getQi()));
        qo.setQx(NumberMath.add(qo.getQx(),qi.getQx()));
        qo.setQy(NumberMath.add(qo.getQy(),qi.getQy()));
        qo.setQz(NumberMath.add(qo.getQz(),qi.getQz()));
    }
    /* _qo = [qi -qx -qy -qz] */
    public static void QUAT_INVERT(Quat qo, Quat qi) {
        qo.setQi(qi.getQi());
        qo.setQx(NumberMath.negate(qi.getQx()));
        qo.setQy(NumberMath.negate(qi.getQy()));
        qo.setQz(NumberMath.negate(qi.getQz()));
    }
    /* _vo=[ qx qy qz] */
    public static void QUAT_EXTRACT_Q(Vect3 vo, Quat qi) {
        vo.setX(qi.getQx());
        vo.setY(qi.getQy());
        vo.setZ(qi.getQz());
    }
    /* _qo = _qo / _s */
    public static void QUAT_SDIV(Quat qo, Quat qi, Number s) {
        qo.setQi(NumberMath.div(qi.getQi(), s));
        qo.setQx(NumberMath.div(qi.getQx(), s));
        qo.setQy(NumberMath.div(qi.getQy(), s));
        qo.setQz(NumberMath.div(qi.getQz(), s));
    }

    /************* ROTATION MATRICIES **************/

    /* accessor : row and col range from 0 to 2 */
    // not needed
    //    #define RMAT_ELMT(_rm, _row, _col) MAT33_ELMT(_rm, _row, _col)

    public static Number RMAT_TRACE(RMat rMat) {
        return NumberMath.sum(rMat.getElement(0, 0), rMat.getElement(1, 1), rMat.getElement(2, 2));
    }

    //TODO does this seem correct?
    public static void RMAT_DIFF(RMat c, RMat a, RMat b) {
        for(int i = 0; i < 3 ; i++) {
            for (int j = 0; j < 3; j++) {
                c.setElement(NumberMath.sub(a.getElement(i,j),b.getElement(i,j)),i,j);
            }
        }
    }

    public static void RAMT_VECT3_MUL(Vect3 vo, RMat rmat, Vect3 vi) {
        MAT33_VECT3_MULT(vo,rmat,vi);
    }

    public static void RMAT_VECT3_TRANSP_MUL(Vect3 vo, RMat rmat, Vect3 vi) {
        MAT33_VECT3_TRANSP_MUL(vo,rmat,vi);
    }

    public static void RMAT_COPY(RMat o, RMat i) {
        MAT33_COPY(o,i);
    }

    /********MORE Todo these seem to depend on algebra int********/
    public static void EULERS_FLOAT_OF_BFP(Eulers<Float> ef, Eulers<Integer> ei) {
        ef.setPhi(PprzAlgebraInt.ANGLE_FLOAT_OF_BFP((ei.getPhi().intValue())));
        ef.setTheta(PprzAlgebraInt.ANGLE_FLOAT_OF_BFP((ei.getTheta().intValue())));
        ef.setPsi(PprzAlgebraInt.ANGLE_FLOAT_OF_BFP((ei.getPsi().intValue())));
    }
//
    public static void EULERS_BFP_OF_REAL(Eulers<Integer> ei, Eulers<Float> ef) {
        ei.setPhi(PprzAlgebraInt.ANGLE_BFP_OF_REAL(ef.getPhi().doubleValue()));
        ei.setTheta(PprzAlgebraInt.ANGLE_BFP_OF_REAL(ef.getTheta().doubleValue()));
        ei.setPsi(PprzAlgebraInt.ANGLE_BFP_OF_REAL(ef.getPsi().doubleValue()));
    }
//
    public static void RMAT_BFP_OF_REAL(RMat<Integer> ei, RMat<Float> ef) {
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++) {
                ei.setElement(PprzAlgebraInt.TRIG_BFP_OF_REAL(ef.getElement(i,j).doubleValue()),i,j);
            }
        }
    }
//
public static void RMAT_FLOAT_OF_BFP(RMat<Float> ef, RMat<Integer> ei) {
    for(int i = 0; i<3; i++){
        for(int j = 0; j<3; j++) {
            ef.setElement(PprzAlgebraInt.TRIG_FLOAT_OF_BFP(ei.getElement(i, j).intValue()),i,j);
        }
    }
}

//
    public static void QUAT_FLOAT_OF_BFP(Quat<Float> qf, Quat<Integer> qi) {
        qf.setQi(PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(qi.getQi().intValue()));
        qf.setQx(PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(qi.getQx().intValue()));
        qf.setQy(PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(qi.getQi().intValue()));
        qf.setQz(PprzAlgebraInt.QUAT1_FLOAT_OF_BFP(qi.getQz().intValue()));
    }
//
    public static void QUAT_BFP_OF_REAL(Quat<Integer> qi, Quat<Float> qf) {
        qi.setQi(PprzAlgebraInt.QUAT1_BFP_OF_REAL(qf.getQi().floatValue()));
        qi.setQx(PprzAlgebraInt.QUAT1_BFP_OF_REAL(qf.getQx().floatValue()));
        qi.setQy(PprzAlgebraInt.QUAT1_BFP_OF_REAL(qf.getQy().floatValue()));
        qi.setQz(PprzAlgebraInt.QUAT1_BFP_OF_REAL(qf.getQz().floatValue()));
    }
//
    public static void RATES_FLOAT_OF_BFP(Rates<Float> rf, Rates<Integer> ri) {
        rf.setP(PprzAlgebraInt.RATE_FLOAT_OF_BFP(ri.getP().intValue()));
        rf.setQ(PprzAlgebraInt.RATE_FLOAT_OF_BFP(ri.getQ().intValue()));
        rf.setR(PprzAlgebraInt.RATE_FLOAT_OF_BFP(ri.getR().intValue()));
    }
//
    public static void RATES_BFP_OF_REAL(Rates<Integer> ri, Rates<Double> rf) {
        ri.setP(PprzAlgebraInt.RATE_BFP_OF_REAL(rf.getP().doubleValue()));
        ri.setQ(PprzAlgebraInt.RATE_BFP_OF_REAL(rf.getQ().doubleValue()));
        ri.setR(PprzAlgebraInt.RATE_BFP_OF_REAL(rf.getR().doubleValue()));
    }
//
    public static void POSITIONS_FLOAT_OF_BFP(Vect3<Float> ef, Vect3<Integer> ei) {
        ef.setX(PprzAlgebraInt.POS_FLOAT_OF_BFP(ei.getX().intValue()));
        ef.setY(PprzAlgebraInt.POS_FLOAT_OF_BFP(ei.getY().intValue()));
        ef.setZ(PprzAlgebraInt.POS_FLOAT_OF_BFP(ei.getZ().intValue()));
    }
//
    public static void POSITIONS_BFP_OF_REAL(Vect3<Integer> ef, Vect3<Double> ei) {
        ef.setX(PprzAlgebraInt.POS_BFP_OF_REAL(ei.getX().doubleValue()));
        ef.setY(PprzAlgebraInt.POS_BFP_OF_REAL(ei.getY().doubleValue()));
        ef.setZ(PprzAlgebraInt.POS_BFP_OF_REAL(ei.getZ().doubleValue()));
    }
//
    public static void SPEEDS_FLOAT_OF_BFP(Vect3<Float> ef, Vect3<Integer> ei) {
        ef.setX(PprzAlgebraInt.SPEED_FLOAT_OF_BFP(ei.getX().intValue()));
        ef.setY(PprzAlgebraInt.SPEED_FLOAT_OF_BFP(ei.getY().intValue()));
        ef.setZ(PprzAlgebraInt.SPEED_FLOAT_OF_BFP(ei.getZ().intValue()));
    }
//
    public static void SPEEDS_BFP_OF_REAL(Vect3<Integer> ef, Vect3<Double> ei) {
        ef.setX(PprzAlgebraInt.SPEED_BFP_OF_REAL(ei.getX().doubleValue()));
        ef.setY(PprzAlgebraInt.SPEED_BFP_OF_REAL(ei.getY().doubleValue()));
        ef.setZ(PprzAlgebraInt.SPEED_BFP_OF_REAL(ei.getZ().doubleValue()));
    }
//
    public static void ACCELS_FLOAT_OF_BFP(Vect3<Float> ef, Vect3<Integer> ei) {
        ef.setX(PprzAlgebraInt.ACCEL_FLOAT_OF_BFP(ei.getX().intValue()));
        ef.setY(PprzAlgebraInt.ACCEL_FLOAT_OF_BFP(ei.getY().intValue()));
        ef.setZ(PprzAlgebraInt.ACCEL_FLOAT_OF_BFP(ei.getZ().intValue()));
    }
//
    public static void ACCELS_BFP_OF_REAL(Vect3<Integer> ef, Vect3<Double> ei) {
        ef.setX(PprzAlgebraInt.ACCEL_BFP_OF_REAL(ei.getX().doubleValue()));
        ef.setY(PprzAlgebraInt.ACCEL_BFP_OF_REAL(ei.getY().doubleValue()));
        ef.setZ(PprzAlgebraInt.ACCEL_BFP_OF_REAL(ei.getZ().doubleValue()));
    }
//
    public static void MAGS_FLOAT_OF_BFP(Vect3<Float> ef, Vect3<Integer> ei) {
        ef.setX(PprzAlgebraInt.MAG_FLOAT_OF_BFP(ei.getX().intValue()));
        ef.setY(PprzAlgebraInt.MAG_FLOAT_OF_BFP(ei.getY().intValue()));
        ef.setZ(PprzAlgebraInt.MAG_FLOAT_OF_BFP(ei.getZ().intValue()));
    }
//
    public static void MAGS_BFP_OF_REAL(Vect3<Integer> ef, Vect3<Double> ei) {
        ef.setX(PprzAlgebraInt.MAG_BFP_OF_REAL(ei.getX().doubleValue()));
        ef.setY(PprzAlgebraInt.MAG_BFP_OF_REAL(ei.getY().doubleValue()));
        ef.setZ(PprzAlgebraInt.MAG_BFP_OF_REAL(ei.getZ().doubleValue()));
    }


    public static Float RATES_NORM2(Rates<Float> w) {
        return NumberMath.sum(NumberMath.sq(w.getP()), NumberMath.sq(w.getQ()), NumberMath.sq(w.getR())).floatValue();
    }

    public static Float RATES_NORM(Rates<Float> w) {
        return NumberMath.sqrt(RATES_NORM2(w)).floatValue();
    }

    public static Number QUAT_NORM2(Quat q) {
        return NumberMath.sum(NumberMath.sq(q.getQi()),NumberMath.sq(q.getQx()),NumberMath.sq(q.getQy()),NumberMath.sq(q.getQz()));
    }

    public static Number QUAT_NORM(Quat q) {
        return NumberMath.sqrt(QUAT_NORM2(q));
    }

    public static Number EULERS_NORM(Eulers e) {
        return NumberMath.sqrt(EULERS_NORM2(e));
    }

    public static Number EULERS_NORM2(Eulers e) {
        return NumberMath.sum(NumberMath.sq(e.getPhi()),NumberMath.sq(e.getPsi()),NumberMath.sq(e.getTheta()));
    }
}

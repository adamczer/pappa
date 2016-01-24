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

import ub.juav.airborne.math.functions.trig.PprzTrig;
import ub.juav.airborne.math.structs.algebra.*;
import ub.juav.airborne.math.util.LlDiv;

/**
 * Created by adamczer on 7/12/15.
 */
public class PprzAlgebraInt {
    public static final int  INT32_POS_FRAC =8;
    private static final double  INT32_POS_OF_CM =2.56;
    private static final int  INT32_POS_OF_CM_NUM =64;
    private static final int  INT32_POS_OF_CM_DEN =25;

    public static final int  INT32_SPEED_FRAC =19;
    private static final double  INT32_SPEED_OF_CM_S =5242.88;
    private static final int  INT32_SPEED_OF_CM_S_NUM =41943;
    private static final int  INT32_SPEED_OF_CM_S_DEN =8;

    public static final int  INT32_ACCEL_FRAC =10;
    private static final int  INT32_MAG_FRAC =11;

    private static final int  INT32_PERCENTAGE_FRAC =10;
    private static final int INT32_QUAT_FRAC =15;

    private static final int INT32_TRIG_FRAC =14;

    /* Euler angles                                 */
    public static final int  INT32_ANGLE_FRAC =12;
    private static final int  INT32_RATE_FRAC =12;
    public static final int  INT32_ANGLE_PI_4  = (int)ANGLE_BFP_OF_REAL(   0.7853981633974483096156608458198757);
    public static final int  INT32_ANGLE_PI_2 =  (int)ANGLE_BFP_OF_REAL(   1.5707963267948966192313216916397514);
    public static final int  INT32_ANGLE_PI   =  (int)ANGLE_BFP_OF_REAL(   3.1415926535897932384626433832795029);
    private static final int  INT32_ANGLE_2_PI =  (int)ANGLE_BFP_OF_REAL(2.*3.1415926535897932384626433832795029);

    private static int  INT32_RAD_OF_DEG(int deg) {
        return (int)(((long)(deg) * 14964008)/857374503);
    }
    private static int  INT32_DEG_OF_RAD(int rad) {
        return (int)(((long)(rad) * 857374503)/14964008);
    }
    public static int INT32_ANGLE_NORMALIZE(int angle) {
        while (angle > INT32_ANGLE_PI)
            angle -= INT32_ANGLE_2_PI;
        while (angle < INT32_ANGLE_PI)
            angle += INT32_ANGLE_2_PI;
        return angle;
    }
    private static int INT32_COURSE_NORMALIZE(int angle) {
        while (angle < 0)
            angle += INT32_ANGLE_2_PI;
        while (angle >= INT32_ANGLE_2_PI)
            angle -= INT32_ANGLE_2_PI;
        return angle;
    }

    // Real (floating point) ->  Binary Fixed Point  (int)
    public static int BFP_OF_REAL(double vr, int frac) {
        return (int) ((vr) * (1 << (frac)));
    }
    public static float FLOAT_OF_BFP(int vbfp, int frac) {
        return ((float)(vbfp)/(1<<(frac)));
    }
    public static double DOUBLE_OF_BFP(int vbfp, int frac) {
        return ((double) (vbfp) / (1 << (frac)));
    }
    public static int RATE_BFP_OF_REAL(double af)  {
        return BFP_OF_REAL((af), INT32_RATE_FRAC);
    }
    public static float RATE_FLOAT_OF_BFP(int af)  {
        return FLOAT_OF_BFP((af), INT32_RATE_FRAC);
    }
    public static int ANGLE_BFP_OF_REAL(double af)  {
        return BFP_OF_REAL((af), INT32_ANGLE_FRAC);
    }
    public static int ANGLE_BFP_OF_REAL(float af)  {
        return BFP_OF_REAL((af), INT32_ANGLE_FRAC);
    }
    public static float ANGLE_FLOAT_OF_BFP(int af)  {
        return FLOAT_OF_BFP((af), INT32_ANGLE_FRAC);
    }
    public static int INT_MULT_RSHIFT(int a, int b, int r) {
        return (((a)*(b))>>(r));
    }

    /**
     * old behavior modified output as a parameter insted now returns result
     * @param in
     * @return
     */
    public static int INT32_SQRT(int in) {
        return (int) Math.sqrt((double)in);
    }

    /********** 2D Vectors **********/
    public static void INT_VECT2_ZERO(Vect2<Integer> v) {
        PprzAlgebra.VECT2_ASSIGN(v, 0, 0);
    }
    public static int INT32_VECT2_NORM(Vect2<Integer> v) {
        return PprzAlgebraInt.int32_sqrt(PprzAlgebra.VECT2_NORM2(v).intValue());
    }

    public static int int32_vect2_norm2(Vect2<Integer> v) {
        return INT32_VECT2_NORM(v);
    }

    public static void int32_vect2_normalize(Vect2<Integer> v, int frac) {
        int n = int32_vect2_norm2(v);
        if (n>0) {
            int f = BFP_OF_REAL(1.,frac);
            v.setX(v.getX()+ (f/n));
            v.setY(v.getY()+ (f/n));
        }
    }

    public static void INT32_VECT2_NORMALIZE(Vect2<Integer> v, int frac) {
        int32_vect2_normalize(v,frac);
    }

    public static void INT32_VECT2_RSHIFT(Vect2<Integer> o, Vect2<Integer> i, int r) {
        o.setX(i.getX()>>r);
        o.setY(i.getY()>>r);
    }

    public static void INT32_VECT2_LSHIFT(Vect2<Integer> o, Vect2<Integer> i, int r) {
        o.setX(i.getX()<<r);
        o.setY(i.getY()<<r);
    }

    public static void INT32_VECT2_SCALE_2(Vect2<Integer> a, Vect2<Integer> b, int num, int den) {
        a.setX(b.getX()*num/den);
        a.setY(b.getY()*num/den);
    }

    /***************** 3D VECTORS *****************/

    public static void INT32_VECT3_SCALE_2(Vect3<Integer> a, Vect3<Integer> b, int num, int den) {
        a.setX(b.getX()*num/den);
        a.setY(b.getY() * num / den);
        a.setZ(b.getZ() * num / den);
    }

    public static int INT32_VECT2_NORM(Vect3<Integer> v) {
        return PprzAlgebraInt.int32_sqrt(PprzAlgebra.VECT3_NORM2(v).intValue());
    }

    public static void INT32_VECT3_RSHIFT(Vect3<Integer> o, Vect3<Integer> i, int r) {
        o.setX(i.getX()>>r);
        o.setY(i.getY() >> r);
        o.setZ(i.getZ() >> r);
    }

    public static void INT32_VECT3_LSHIFT(Vect3<Integer> o, Vect3<Integer> i, int r) {
        o.setX(i.getX()<<r);
        o.setY(i.getY() << r);
        o.setZ(i.getZ() << r);
    }

    public static void INT_VECT3_ZERO(Vect3<Integer> v) {
        PprzAlgebra.VECT3_ASSIGN(v, 0, 0, 0);
    }
    public static void INT32_VECT3_ZERO(Vect3<Integer> v) {
        PprzAlgebra.VECT3_ASSIGN(v, 0, 0, 0);
    }

    /********** 3x3 Matricies **********/

    public static void INT32_MAT33_ZERO(Mat33<Integer> mat) {
        mat.zero();
    }

    public static void INT32_MAT33_DIAG(Mat33<Integer> mat, int d00,int d11,int d22) {
        PprzAlgebra.MAT33_DIAG(mat,d00,d11,d22);
    }


    /************** Rotation Matricies ***************/

    /* initialises a rotation matrix to identity */
    public static void int32_rmat_identity(RMat<Integer> mat) {
        PprzAlgebra.MAT33_DIAG(mat, TRIG_BFP_OF_REAL(1.), TRIG_BFP_OF_REAL(1.), TRIG_BFP_OF_REAL(1.));
    }

    public static int QUAT1_BFP_OF_REAL(double qf)  {return BFP_OF_REAL((qf), INT32_QUAT_FRAC);}
    public static float QUAT1_FLOAT_OF_BFP(int qi) { return FLOAT_OF_BFP((qi), INT32_QUAT_FRAC);}
    public static int TRIG_BFP_OF_REAL(double tf)   {return BFP_OF_REAL((tf), INT32_TRIG_FRAC);}
    public static float TRIG_FLOAT_OF_BFP(int ti) {return FLOAT_OF_BFP((ti),INT32_TRIG_FRAC);}
    public static int POS_BFP_OF_REAL(double af)  { return BFP_OF_REAL((af), INT32_POS_FRAC);}
    public static float POS_FLOAT_OF_BFP(int ai) { return FLOAT_OF_BFP((ai), INT32_POS_FRAC);}
    public static int SPEED_BFP_OF_REAL(double af)  {return BFP_OF_REAL((af), INT32_SPEED_FRAC);}
    public static float SPEED_FLOAT_OF_BFP(int ai) {return FLOAT_OF_BFP((ai), INT32_SPEED_FRAC);}
    public static int ACCEL_BFP_OF_REAL(double af)  {return BFP_OF_REAL((af), INT32_ACCEL_FRAC);}
    public static float ACCEL_FLOAT_OF_BFP(int ai) {return FLOAT_OF_BFP((ai), INT32_ACCEL_FRAC);}
    public static int MAG_BFP_OF_REAL(double af) {return BFP_OF_REAL((af), INT32_MAG_FRAC);}
    public static float MAG_FLOAT_OF_BFP(int ai) {return FLOAT_OF_BFP((ai), INT32_MAG_FRAC);}

/** pprz_algebra_int.c */

    private static final int INT32_SQRT_MAX_ITER = 40;

    public static int int32_sqrt(int in) {
        if (in ==0)
            return 0;
        else {
            int s1, s2 = in, iter =0;
            do {
                s1=s2;
                s2 = in/s1;
                s2+=s1;
                s2/=2;
                iter++;
            } while (((s1 - s2) > 1) && (iter < INT32_SQRT_MAX_ITER));
            return s2;
        }
    }

    /*** Rotation Matricies ***/

    /** Composition (multiplication) of two rotation matrices.
     * _m_a2c = _m_a2b comp _m_b2c , aka  _m_a2c = _m_b2c * _m_a2b
     */
    public static void int32_rmat_comp(RMat<Integer> ma2c, RMat<Integer> ma2b, RMat<Integer> mb2c) {
        ma2c.setElement((mb2c.getElement(0, 0)*ma2b.getElement(0, 0)+mb2c.getElement(0, 1)*ma2b.getElement(1, 0)+mb2c.getElement(0, 2)*ma2b.getElement(2, 0)) >> INT32_TRIG_FRAC,0,0);
        ma2c.setElement((mb2c.getElement(0, 0)*ma2b.getElement(0, 1)+mb2c.getElement(0, 1)*ma2b.getElement(1, 1)+mb2c.getElement(0, 2)*ma2b.getElement(2, 1)) >> INT32_TRIG_FRAC,0,1);
        ma2c.setElement((mb2c.getElement(0, 0)*ma2b.getElement(0, 2)+mb2c.getElement(0, 1)*ma2b.getElement(1, 2)+mb2c.getElement(0, 2)*ma2b.getElement(2, 2)) >> INT32_TRIG_FRAC,0,2);
        ma2c.setElement((mb2c.getElement(1, 0)*ma2b.getElement(0, 0)+mb2c.getElement(1, 1)*ma2b.getElement(1, 0)+mb2c.getElement(1, 2)*ma2b.getElement(2, 0)) >> INT32_TRIG_FRAC,1,0);
        ma2c.setElement((mb2c.getElement(1, 0)*ma2b.getElement(0, 1)+mb2c.getElement(1, 1)*ma2b.getElement(1, 1)+mb2c.getElement(1, 2)*ma2b.getElement(2, 1)) >> INT32_TRIG_FRAC,1,1);
        ma2c.setElement((mb2c.getElement(1, 0)*ma2b.getElement(0, 2)+mb2c.getElement(1, 1)*ma2b.getElement(1, 2)+mb2c.getElement(1, 2)*ma2b.getElement(2, 2)) >> INT32_TRIG_FRAC,1,2);
        ma2c.setElement((mb2c.getElement(2, 0)*ma2b.getElement(0, 0)+mb2c.getElement(2, 1)*ma2b.getElement(1, 0)+mb2c.getElement(2, 2)*ma2b.getElement(2, 0)) >> INT32_TRIG_FRAC,2,0);
        ma2c.setElement((mb2c.getElement(2, 0)*ma2b.getElement(0, 1)+mb2c.getElement(2, 1)*ma2b.getElement(1, 1)+mb2c.getElement(2, 2)*ma2b.getElement(2, 1)) >> INT32_TRIG_FRAC,2,1);
        ma2c.setElement((mb2c.getElement(2, 0)*ma2b.getElement(0, 2)+mb2c.getElement(2, 1)*ma2b.getElement(1, 2)+mb2c.getElement(2, 2)*ma2b.getElement(2, 2)) >> INT32_TRIG_FRAC,2,2);
    }

    /** Composition (multiplication) of two rotation matrices.
     * _m_a2b = _m_a2c comp_inv _m_b2c , aka  _m_a2b = inv(_m_b2c) * _m_a2c
     */
    public static void int32_rmat_comp_inv(RMat<Integer> ma2b, RMat<Integer> ma2c, RMat<Integer> mb2c) {
        ma2b.setElement((mb2c.getElement(0, 0)*ma2c.getElement(0, 0)+mb2c.getElement(1, 0)*ma2c.getElement(1, 0)+mb2c.getElement(2, 0)*ma2c.getElement(2, 0)) >> INT32_TRIG_FRAC,0,0);
        ma2b.setElement((mb2c.getElement(0, 0)*ma2c.getElement(0, 1)+mb2c.getElement(1, 0)*ma2c.getElement(1, 1)+mb2c.getElement(2, 0)*ma2c.getElement(2, 1)) >> INT32_TRIG_FRAC,0,1);
        ma2b.setElement((mb2c.getElement(0, 0)*ma2c.getElement(0, 2)+mb2c.getElement(1, 0)*ma2c.getElement(1, 2)+mb2c.getElement(2, 0)*ma2c.getElement(2, 2)) >> INT32_TRIG_FRAC,0,2);
        ma2b.setElement((mb2c.getElement(0, 1)*ma2c.getElement(0, 0)+mb2c.getElement(1, 1)*ma2c.getElement(1, 0)+mb2c.getElement(2, 1)*ma2c.getElement(2, 0)) >> INT32_TRIG_FRAC,1,0);
        ma2b.setElement((mb2c.getElement(0, 1)*ma2c.getElement(0, 1)+mb2c.getElement(1, 1)*ma2c.getElement(1, 1)+mb2c.getElement(2, 1)*ma2c.getElement(2, 1)) >> INT32_TRIG_FRAC,1,1);
        ma2b.setElement((mb2c.getElement(0, 1)*ma2c.getElement(0, 2)+mb2c.getElement(1, 1)*ma2c.getElement(1, 2)+mb2c.getElement(2, 1)*ma2c.getElement(2, 2)) >> INT32_TRIG_FRAC,1,2);
        ma2b.setElement((mb2c.getElement(0, 2)*ma2c.getElement(0, 0)+mb2c.getElement(1, 2)*ma2c.getElement(1, 0)+mb2c.getElement(2, 2)*ma2c.getElement(2, 0)) >> INT32_TRIG_FRAC,2,0);
        ma2b.setElement((mb2c.getElement(0, 2)*ma2c.getElement(0, 1)+mb2c.getElement(1, 2)*ma2c.getElement(1, 1)+mb2c.getElement(2, 2)*ma2c.getElement(2, 1)) >> INT32_TRIG_FRAC,2,1);
        ma2b.setElement((mb2c.getElement(0, 2)*ma2c.getElement(0, 2)+mb2c.getElement(1, 2)*ma2c.getElement(1, 2)+mb2c.getElement(2, 2)*ma2c.getElement(2, 2)) >> INT32_TRIG_FRAC,2,2);
    }

    /** rotate 3D vector by rotation matrix.
     * vb = m_a2b * va
     */
    public static void int32_rmat_vmult(Vect3<Integer> vb, RMat<Integer> ma2b, Vect3<Integer> va) {
        vb.setX((ma2b.getElement(0, 0)*va.getX()+ma2b.getElement(0, 1) * va.getY() + ma2b.getElement(0, 2)*va.getZ())>> INT32_TRIG_FRAC);
        vb.setY((ma2b.getElement(1, 0)*va.getX()+ma2b.getElement(1, 1) * va.getY() + ma2b.getElement(1, 2)*va.getZ())>> INT32_TRIG_FRAC);
        vb.setZ((ma2b.getElement(2, 0)*va.getX()+ma2b.getElement(2, 1) * va.getY() + ma2b.getElement(2, 2)*va.getZ())>> INT32_TRIG_FRAC);
    }

    /** rotate 3D vector by transposed rotation matrix.
     * vb = m_b2a^T * va
     */
    public static void int32_rmat_transp_vmult(Vect3<Integer> vb, RMat<Integer> ma2b, Vect3<Integer> va) {
        vb.setX((ma2b.getElement(0, 0)*va.getX()+ma2b.getElement(1, 0) * va.getY() + ma2b.getElement(2, 0)*va.getZ())>> INT32_TRIG_FRAC);
        vb.setY((ma2b.getElement(0, 1)*va.getX()+ma2b.getElement(1, 1) * va.getY() + ma2b.getElement(2, 1)*va.getZ())>> INT32_TRIG_FRAC);
        vb.setZ((ma2b.getElement(0, 2)*va.getX()+ma2b.getElement(1, 2) * va.getY() + ma2b.getElement(2, 2)*va.getZ())>> INT32_TRIG_FRAC);
    }

    /** rotate anglular rates by rotation matrix.
     * rb = m_a2b * ra
     */
    public static void int32_rmat_ratemult(Rates<Integer> rb, RMat<Integer> ma2b, Rates<Integer> ra) {
        rb.setP((ma2b.getElement(0, 0) * ra.getP() + ma2b.getElement(0, 1) * ra.getQ() + ma2b.getElement(0, 2) * ra.getR())>> INT32_TRIG_FRAC);
        rb.setQ((ma2b.getElement(1, 0) * ra.getP() + ma2b.getElement(1, 1) * ra.getQ() + ma2b.getElement(1, 2) * ra.getR())>> INT32_TRIG_FRAC);
        rb.setR((ma2b.getElement(2, 0) * ra.getP() + ma2b.getElement(2, 1) * ra.getQ() + ma2b.getElement(2, 2) * ra.getR())>> INT32_TRIG_FRAC);
    }

    /** rotate anglular rates by transposed rotation matrix.
     * rb = m_b2a^T * ra
     */
    public static void int32_rmat_transp_ratemult(Rates<Integer> rb, RMat<Integer> mb2a, Rates<Integer> ra) {
        rb.setP((mb2a.getElement(0, 0) * ra.getP() + mb2a.getElement(1, 0) * ra.getQ() + mb2a.getElement(2, 0) * ra.getR()) >> INT32_TRIG_FRAC);
        rb.setQ((mb2a.getElement(0, 1) * ra.getP() + mb2a.getElement(1, 1) * ra.getQ() + mb2a.getElement(2, 1) * ra.getR()) >> INT32_TRIG_FRAC);
        rb.setR((mb2a.getElement(0, 2) * ra.getP() + mb2a.getElement(1, 2) * ra.getQ() + mb2a.getElement(2, 2) * ra.getR()) >> INT32_TRIG_FRAC);
    }

    /** Convert unit quaternion to rotation matrix.
     * http://www.mathworks.com/access/helpdesk_r13/help/toolbox/aeroblks/quaternionstodirectioncosinematrix.html
     * @param rm
     * @param q
     */
    public static void int32_rmat_of_quat(RMat<Integer> rm, Quat<Integer> q) {
        int _2qi2_m1 =  INT_MULT_RSHIFT(q.getQi(), q.getQi(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1) - TRIG_BFP_OF_REAL(1);
        rm.setElement(INT_MULT_RSHIFT(q.getQx(), q.getQx(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),0,0);
        rm.setElement(INT_MULT_RSHIFT(q.getQy(), q.getQy(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1), 1, 1);
        rm.setElement(INT_MULT_RSHIFT(q.getQz(), q.getQz(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),2,2);

        int _2qiqx = INT_MULT_RSHIFT(q.getQi(), q.getQx(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1);
        int _2qiqy = INT_MULT_RSHIFT(q.getQi(), q.getQy(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1);
        int _2qiqz = INT_MULT_RSHIFT(q.getQi(), q.getQz(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1);

        rm.setElement(INT_MULT_RSHIFT(q.getQx(), q.getQy(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),0,1);
        rm.setElement(INT_MULT_RSHIFT(q.getQx(), q.getQz(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),0,2);
        rm.setElement(INT_MULT_RSHIFT(q.getQy(), q.getQz(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),1,2);

        rm.setElement(rm.getElement(0, 0) + _2qi2_m1,0,0);
        rm.setElement(rm.getElement(0, 1) - _2qiqz,1,0);
        rm.setElement(rm.getElement(0, 2)+_2qiqy,2,0);
        rm.setElement(rm.getElement(1, 2)-_2qiqx,2,1);
        rm.setElement(rm.getElement(1, 1)+_2qi2_m1,1,1);
        rm.setElement(rm.getElement(0, 1)+_2qiqz,0,1);
        rm.setElement(rm.getElement(0, 2)-_2qiqy,0,2);
        rm.setElement(rm.getElement(1, 2)+_2qiqx,1,2);
        rm.setElement(rm.getElement(2, 2)+_2qi2_m1,2,2);
    }

    /** Rotation matrix from 321 Euler angles.
     * http://www.mathworks.com/access/helpdesk_r13/help/toolbox/aeroblks/euleranglestodirectioncosinematrix.html
     */
    public static void int32_rmat_of_eulers_321(RMat<Integer> rm, Eulers<Integer> e) {
        int sphi = PprzTrig.PPRZ_ITRIG_SIN(e.getPhi());
        int cphi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi());
        int stheta = PprzTrig.PPRZ_ITRIG_SIN(e.getTheta());
        int ctheta = PprzTrig.PPRZ_ITRIG_COS(e.getTheta());
        int spsi = PprzTrig.PPRZ_ITRIG_SIN(e.getPsi());
        int cpsi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi());

        int ctheta_cpsi = INT_MULT_RSHIFT(ctheta, cpsi,   INT32_TRIG_FRAC);
        int ctheta_spsi = INT_MULT_RSHIFT(ctheta, spsi,   INT32_TRIG_FRAC);
        int cphi_spsi   = INT_MULT_RSHIFT(cphi,   spsi,   INT32_TRIG_FRAC);
        int cphi_cpsi   = INT_MULT_RSHIFT(cphi,   cpsi,   INT32_TRIG_FRAC);
        int cphi_ctheta = INT_MULT_RSHIFT(cphi,   ctheta, INT32_TRIG_FRAC);
        int cphi_stheta = INT_MULT_RSHIFT(cphi,   stheta, INT32_TRIG_FRAC);
        int sphi_ctheta = INT_MULT_RSHIFT(sphi,   ctheta, INT32_TRIG_FRAC);
        int sphi_stheta = INT_MULT_RSHIFT(sphi,   stheta, INT32_TRIG_FRAC);
        int sphi_spsi   = INT_MULT_RSHIFT(sphi,   spsi,   INT32_TRIG_FRAC);
        int sphi_cpsi   = INT_MULT_RSHIFT(sphi,   cpsi,   INT32_TRIG_FRAC);

        int sphi_stheta_cpsi = INT_MULT_RSHIFT(sphi_stheta, cpsi, INT32_TRIG_FRAC);
        int sphi_stheta_spsi = INT_MULT_RSHIFT(sphi_stheta, spsi, INT32_TRIG_FRAC);
        int cphi_stheta_cpsi = INT_MULT_RSHIFT(cphi_stheta, cpsi, INT32_TRIG_FRAC);
        int cphi_stheta_spsi = INT_MULT_RSHIFT(cphi_stheta, spsi, INT32_TRIG_FRAC);

        rm.setElement(ctheta_cpsi,0,0);
        rm.setElement(ctheta_spsi,0,1);
        rm.setElement(-stheta,0,2);
        rm.setElement(sphi_stheta_cpsi - cphi_spsi,1,0);
        rm.setElement(sphi_stheta_spsi + cphi_cpsi,1,1);
        rm.setElement(sphi_ctheta,1,2);
        rm.setElement(cphi_stheta_cpsi + sphi_spsi,2,0);
        rm.setElement(cphi_stheta_spsi - sphi_cpsi,2,1);
        rm.setElement(cphi_ctheta,2,2);
    }
    public static void int32_rmat_of_eulers(RMat<Integer> rm, Eulers<Integer> e) {
        int32_rmat_of_eulers_312(rm,e);
    }
    public static void int32_rmat_of_eulers_312(RMat<Integer> rm, Eulers<Integer> e) {
        int sphi = PprzTrig.PPRZ_ITRIG_SIN(e.getPhi());
        int cphi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi());
        int stheta = PprzTrig.PPRZ_ITRIG_SIN(e.getTheta());
        int ctheta = PprzTrig.PPRZ_ITRIG_COS(e.getTheta());
        int spsi = PprzTrig.PPRZ_ITRIG_SIN(e.getPsi());
        int cpsi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi());

        int ctheta_cpsi = INT_MULT_RSHIFT(ctheta, cpsi,   INT32_TRIG_FRAC);
        int ctheta_spsi = INT_MULT_RSHIFT(ctheta, spsi,   INT32_TRIG_FRAC);
        int cphi_spsi   = INT_MULT_RSHIFT(cphi,   spsi,   INT32_TRIG_FRAC);
        int cphi_cpsi   = INT_MULT_RSHIFT(cphi,   cpsi,   INT32_TRIG_FRAC);
        int cphi_ctheta = INT_MULT_RSHIFT(cphi,   ctheta, INT32_TRIG_FRAC);
        int cphi_stheta = INT_MULT_RSHIFT(cphi,   stheta, INT32_TRIG_FRAC);
        int stheta_cpsi = INT_MULT_RSHIFT(stheta, cpsi,   INT32_TRIG_FRAC);
        int sphi_stheta = INT_MULT_RSHIFT(sphi,   stheta, INT32_TRIG_FRAC);
        int stheta_spsi = INT_MULT_RSHIFT(stheta, spsi,   INT32_TRIG_FRAC);
        int sphi_ctheta = INT_MULT_RSHIFT(sphi,   ctheta, INT32_TRIG_FRAC);

        int sphi_stheta_cpsi = INT_MULT_RSHIFT(sphi_stheta, cpsi, INT32_TRIG_FRAC);
        int sphi_stheta_spsi = INT_MULT_RSHIFT(sphi_stheta, spsi, INT32_TRIG_FRAC);
        int sphi_ctheta_spsi = INT_MULT_RSHIFT(sphi_ctheta, spsi, INT32_TRIG_FRAC);
        int sphi_ctheta_cpsi = INT_MULT_RSHIFT(sphi_ctheta, cpsi, INT32_TRIG_FRAC);;

        rm.setElement(ctheta_cpsi - sphi_stheta_spsi,0,0);
        rm.setElement(ctheta_spsi + sphi_stheta_cpsi,0,1);
        rm.setElement(-cphi_stheta,0,2);
        rm.setElement(-cphi_spsi,1,0);
        rm.setElement(cphi_cpsi,1,1);
        rm.setElement(sphi,1,2);
        rm.setElement(stheta_cpsi + sphi_ctheta_spsi,2,0);
        rm.setElement(stheta_spsi - sphi_ctheta_cpsi,2,1);
        rm.setElement(cphi_ctheta,2,2);
    }

    /******** Quaternions ********/

    public static void int32_quat_comp(Quat<Integer> a2c, Quat<Integer> a2b, Quat<Integer> b2c) {
        a2c.setQi((
                a2b.getQi() * b2c.getQi() -
                a2b.getQx() * b2c.getQx() -
                a2b.getQy() * b2c.getQy() -
                a2b.getQz() * b2c.getQz())
                >> INT32_QUAT_FRAC);
        a2c.setQx((
                        a2b.getQi() * b2c.getQx() +
                        a2b.getQx() * b2c.getQi() +
                        a2b.getQy() * b2c.getQz() -
                        a2b.getQz() * b2c.getQy())
                >> INT32_QUAT_FRAC);
        a2c.setQy((
                        a2b.getQi() * b2c.getQy() -
                        a2b.getQx() * b2c.getQz() +
                        a2b.getQy() * b2c.getQi() +
                        a2b.getQz() * b2c.getQx())
                >> INT32_QUAT_FRAC);
        a2c.setQz((
                        a2b.getQi() * b2c.getQz() +
                        a2b.getQx() * b2c.getQy() -
                        a2b.getQy() * b2c.getQx() +
                        a2b.getQz() * b2c.getQi())
                >> INT32_QUAT_FRAC);
    }
    public static void int32_quat_comp_inv(Quat<Integer> a2b, Quat<Integer> a2c, Quat<Integer> b2c) {
        a2b.setQi((
                        a2c.getQi() * b2c.getQi() +
                        a2c.getQx() * b2c.getQx() +
                        a2c.getQy() * b2c.getQy() +
                        a2c.getQz() * b2c.getQz())
                >> INT32_QUAT_FRAC);
        a2b.setQx((
                        a2c.getQi() * b2c.getQx() +
                        a2c.getQx() * b2c.getQi() -
                        a2c.getQy() * b2c.getQz() +
                        a2c.getQz() * b2c.getQy())
                >> INT32_QUAT_FRAC);
        a2b.setQy((
                        a2c.getQi() * b2c.getQy() +
                        a2c.getQx() * b2c.getQz() +
                        a2c.getQy() * b2c.getQi() -
                        a2c.getQz() * b2c.getQx())
                >> INT32_QUAT_FRAC);
        a2b.setQz((
                        a2c.getQi() * b2c.getQz() -
                        a2c.getQx() * b2c.getQy() +
                        a2c.getQy() * b2c.getQx() +
                        a2c.getQz() * b2c.getQi())
                >> INT32_QUAT_FRAC);
    }

    public static void int32_quat_inv_comp(Quat<Integer> b2c, Quat<Integer> a2b, Quat<Integer> a2c) {
        b2c.setQi((
                        a2b.getQi() * a2c.getQi() +
                        a2b.getQx() * a2c.getQx() +
                        a2b.getQy() * a2c.getQy() +
                        a2b.getQz() * a2c.getQz())
                >> INT32_QUAT_FRAC);
        b2c.setQx((
                        a2b.getQi() * a2c.getQx() -
                        a2b.getQx() * a2c.getQi() -
                        a2b.getQy() * a2c.getQz() +
                        a2b.getQz() * a2c.getQy())
                >> INT32_QUAT_FRAC);
        b2c.setQy((
                        a2b.getQi() * a2c.getQy() +
                        a2b.getQx() * a2c.getQz() -
                        a2b.getQy() * a2c.getQi() -
                        a2b.getQz() * a2c.getQx())
                >> INT32_QUAT_FRAC);
        b2c.setQz((
                        a2b.getQi() * a2c.getQz() -
                        a2b.getQx() * a2c.getQy() +
                        a2b.getQy() * a2c.getQx() -
                        a2b.getQz() * a2c.getQi())
                >> INT32_QUAT_FRAC);
    }

    public static void int32_quat_comp_norm_shortest(Quat<Integer> a2c, Quat<Integer> a2b, Quat<Integer> b2c) {
        int32_quat_comp(a2c, a2b, b2c);
        int32_quat_wrap_shortest(a2c);
        int32_quat_normalize(a2c);
    }

    private static void int32_quat_normalize(Quat<Integer> q) {
        int n = int32_quat_norm(q);
        if (n > 0) {
            q.setQi(q.getQi()*QUAT1_BFP_OF_REAL(1));
            q.setQx(q.getQx() * QUAT1_BFP_OF_REAL(1));
            q.setQy(q.getQy() * QUAT1_BFP_OF_REAL(1));
            q.setQz(q.getQz() * QUAT1_BFP_OF_REAL(1));
        }
    }

    private static int int32_quat_norm(Quat<Integer> q) {
        return int32_sqrt((q.getQi()*q.getQi())+(q.getQx()*q.getQx())+(q.getQy()*q.getQy())+(q.getQz()*q.getQz()));
    }

    private static void int32_quat_wrap_shortest(Quat<Integer> quat) {
        if (quat.getQi() < 0) {
            PprzAlgebra.QUAT_EXPLEMENTARY(quat, quat);
        }
    }

    public static void int32_quat_comp_inv_norm_shortest(Quat<Integer> a2b, Quat<Integer> a2c, Quat<Integer> b2c) {
        int32_quat_comp_inv(a2b, a2c, b2c);
        int32_quat_wrap_shortest(a2b);
        int32_quat_normalize(a2b);
    }

    public static void int32_quat_inv_comp_norm_shortest(Quat<Integer> b2c, Quat<Integer> a2b, Quat<Integer> a2c) {
        int32_quat_inv_comp(b2c, a2b, a2c);
        int32_quat_wrap_shortest(b2c);
        int32_quat_normalize(b2c);
    }

    /** Quaternion derivative from rotational velocity.
     * qd = -0.5*omega(r) * q
     * or equally:
     * qd = 0.5 * q * omega(r)
     * Multiplication with 0.5 is done by shifting one more bit to the right.
     */

    public static void int32_quat_derivative(Quat<Integer> qd, Rates<Integer> r, Quat<Integer> q) {
        qd.setQi((-(r.getP()*q.getQx()+r.getQ()*q.getQy()+r.getR()*q.getQz())) >> (INT32_RATE_FRAC+1));
        qd.setQx((-(-r.getP() * q.getQi() - r.getR() * q.getQy() + r.getQ() * q.getQz())) >> (INT32_RATE_FRAC + 1));
        qd.setQy((-(-r.getP() * q.getQi() + r.getR() * q.getQx() - r.getP() * q.getQz())) >> (INT32_RATE_FRAC + 1));
        qd.setQz((-(-r.getP() * q.getQi() - +r.getQ() * q.getQx() + r.getP() * q.getQy())) >> (INT32_RATE_FRAC + 1));
    }

    /** in place quaternion first order integration with constant rotational velocity. */
    public static void int32_quat_integrate_fi(Quat<Integer> q, Quat<Long> hr, Rates<Integer> omega, int freq) {
        hr.setQi(omega.getP().longValue()*q.getQx().longValue() - omega.getQ().longValue() * q.getQy().longValue() - omega.getR().longValue()*q.getQz().longValue());
        hr.setQx(omega.getP().longValue() * q.getQi().longValue() - omega.getR().longValue() * q.getQy().longValue() - omega.getQ().longValue() * q.getQz().longValue());
        hr.setQy(omega.getQ().longValue() * q.getQi().longValue() - omega.getR().longValue() * q.getQx().longValue() - omega.getP().longValue() * q.getQz().longValue());
        hr.setQz(omega.getR().longValue() * q.getQi().longValue() - omega.getQ().longValue() * q.getQx().longValue() - omega.getP().longValue() * q.getQy().longValue());

        LlDiv div = LlDiv.lldiv(hr.getQi().longValue(), ((1 << INT32_RATE_FRAC) * freq * 2));
        q.setQi((int) div.getQuot());
        hr.setQi(div.getRem());

        div = LlDiv.lldiv(hr.getQx().longValue(),((1 << INT32_RATE_FRAC) * freq * 2));
        q.setQx((int) div.getQuot());
        hr.setQx(div.getRem());

        div = LlDiv.lldiv(hr.getQy().longValue(),((1 << INT32_RATE_FRAC) * freq * 2));
        q.setQy((int) div.getQuot());
        hr.setQx(div.getRem());

        div = LlDiv.lldiv(hr.getQz().longValue(),((1 << INT32_RATE_FRAC) * freq * 2));
        q.setQz((int) div.getQuot());
        hr.setQz(div.getRem());
    }

    public static void int32_quat_vmult(Vect3<Integer> vOut, Quat<Integer> q, Vect3<Integer> vIn) {
        int _2qi2_m1 = ((q.getQi() * q.getQi()) >> (INT32_QUAT_FRAC - 1)) - QUAT1_BFP_OF_REAL(1);
        int _2qx2    = (q.getQx() * q.getQx()) >> (INT32_QUAT_FRAC - 1);
        int _2qy2    = (q.getQy() * q.getQy()) >> (INT32_QUAT_FRAC - 1);
        int _2qz2    = (q.getQz() * q.getQz()) >> (INT32_QUAT_FRAC - 1);
        int _2qiqx   = (q.getQi() * q.getQx()) >> (INT32_QUAT_FRAC - 1);
        int _2qiqy   = (q.getQi() * q.getQy()) >> (INT32_QUAT_FRAC - 1);
        int _2qiqz   = (q.getQi() * q.getQz()) >> (INT32_QUAT_FRAC - 1);
        int m01 = ((q.getQx() * q.getQy()) >> (INT32_QUAT_FRAC - 1)) + _2qiqz;
        int m02 = ((q.getQx() * q.getQz()) >> (INT32_QUAT_FRAC - 1)) - _2qiqy;
        int m12 = ((q.getQy() * q.getQz()) >> (INT32_QUAT_FRAC - 1)) + _2qiqx;
        vOut.setX((_2qi2_m1 * vIn.getX() + _2qx2 * vIn.getX() + m01 * vIn.getY() +  m02 * vIn.getZ()) >> INT32_QUAT_FRAC);
        vOut.setY((_2qi2_m1*vIn.getY() + m01 * vIn.getX() - 2 * _2qiqz * vIn.getX() + _2qy2 * vIn.getY() + m12 * vIn.getZ()) >>
        INT32_QUAT_FRAC);
        vOut.setZ((_2qi2_m1 * vIn.getZ() + m02 * vIn.getX() + 2 * _2qiqy * vIn.getX() + m12 * vIn.getY() - 2 * _2qiqx * vIn.getY() + _2qz2 *
                vIn.getZ()) >> INT32_QUAT_FRAC);
    }

    /*
 * http://www.mathworks.com/access/helpdesk_r13/help/toolbox/aeroblks/euleranglestoquaternions.html
 */
    public static void int32_quat_of_eulers(Quat<Integer> q, Eulers<Integer> e) {
        int phi2   = e.getPhi()   / 2;
        int theta2 = e.getTheta() / 2;
        int psi2   = e.getPsi()   / 2;

        int s_phi2 = PprzTrig.PPRZ_ITRIG_SIN(phi2);
        int c_phi2 = PprzTrig.PPRZ_ITRIG_COS(phi2);
        int s_theta2 = PprzTrig.PPRZ_ITRIG_SIN(theta2);
        int c_theta2 = PprzTrig.PPRZ_ITRIG_COS(theta2);
        int s_psi2 = PprzTrig.PPRZ_ITRIG_SIN(psi2);
        int c_psi2 = PprzTrig.PPRZ_ITRIG_COS(psi2);

        int c_th_c_ps = INT_MULT_RSHIFT(c_theta2, c_psi2, INT32_TRIG_FRAC);
        int c_th_s_ps = INT_MULT_RSHIFT(c_theta2, s_psi2, INT32_TRIG_FRAC);
        int s_th_s_ps = INT_MULT_RSHIFT(s_theta2, s_psi2, INT32_TRIG_FRAC);
        int s_th_c_ps = INT_MULT_RSHIFT(s_theta2, c_psi2, INT32_TRIG_FRAC);

        q.setQi( INT_MULT_RSHIFT(c_phi2, c_th_c_ps, INT32_TRIG_FRAC + INT32_TRIG_FRAC - INT32_QUAT_FRAC) +
                INT_MULT_RSHIFT(s_phi2, s_th_s_ps, INT32_TRIG_FRAC + INT32_TRIG_FRAC - INT32_QUAT_FRAC));
        q.setQx( INT_MULT_RSHIFT(-c_phi2, s_th_s_ps, INT32_TRIG_FRAC + INT32_TRIG_FRAC - INT32_QUAT_FRAC) +
                INT_MULT_RSHIFT(s_phi2, c_th_c_ps, INT32_TRIG_FRAC + INT32_TRIG_FRAC - INT32_QUAT_FRAC));
        q.setQy(INT_MULT_RSHIFT(c_phi2, s_th_c_ps, INT32_TRIG_FRAC + INT32_TRIG_FRAC - INT32_QUAT_FRAC) +
                INT_MULT_RSHIFT(s_phi2, c_th_s_ps, INT32_TRIG_FRAC + INT32_TRIG_FRAC - INT32_QUAT_FRAC));
        q.setQz(INT_MULT_RSHIFT(c_phi2, c_th_s_ps, INT32_TRIG_FRAC + INT32_TRIG_FRAC - INT32_QUAT_FRAC) +
                INT_MULT_RSHIFT(-s_phi2, s_th_c_ps, INT32_TRIG_FRAC + INT32_TRIG_FRAC - INT32_QUAT_FRAC));
    }

    public static void int32_quat_of_axis_angle(Quat<Integer> q, Vect3<Integer> uv, int angle) {
        int san2 = PprzTrig.PPRZ_ITRIG_SIN((angle / 2));
        int can2 = PprzTrig.PPRZ_ITRIG_COS((angle / 2));
        q.setQi(can2);
        q.setQx(san2 * uv.getX());
        q.setQy(san2 * uv.getY());
        q.setQz(san2 * uv.getZ());
    }

    public static void int32_quat_of_rmat(Quat<Integer> q, RMat<Integer> r) {
        int tr = PprzAlgebra.RMAT_TRACE(r).intValue();
        if (tr > 0) {
            int two_qi_two = TRIG_BFP_OF_REAL(1.) + tr;
            long two_qi = int32_sqrt(two_qi_two << INT32_TRIG_FRAC);
            two_qi = two_qi << (INT32_QUAT_FRAC - INT32_TRIG_FRAC);
            q.setQi((int) (two_qi / 2));
            q.setQx((int) (((r.getElement(1, 2) - r.getElement(2, 1)) <<
                        (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                        / two_qi));
            q.setQy((int) (((r.getElement(2, 0) - r.getElement(0, 2)) <<
                        (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                        / two_qi));
            q.setQz((int) (((r.getElement(0, 1) - r.getElement(1, 0)) <<
                        (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                        / two_qi));
        } else {
            if (r.getElement(0, 0) > r.getElement(1, 1) &&
            r.getElement(0, 0) > r.getElement(2, 2)) {
                int two_qx_two = r.getElement(0, 0) - r.getElement(1, 1)
                - r.getElement(2, 2) + TRIG_BFP_OF_REAL(1.);
                long two_qx = int32_sqrt(two_qx_two << INT32_TRIG_FRAC);
                two_qx = two_qx << (INT32_QUAT_FRAC - INT32_TRIG_FRAC);
                q.setQi((int) (((r.getElement(1, 2) - r.getElement(2, 1)) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qx));
                q.setQx((int) (two_qx / 2));
                q.setQy((int) (((r.getElement(0, 1) + r.getElement(1, 0)) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qx));
                q.setQz((int) (((r.getElement(2, 0) + r.getElement(0, 2)) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qx));
            } else if (r.getElement(1, 1) > r.getElement(2, 2)) {
                int two_qy_two = r.getElement(1, 1) - r.getElement(0, 0)
                - r.getElement(2, 2) + TRIG_BFP_OF_REAL(1.);
                long two_qy = int32_sqrt(two_qy_two << INT32_TRIG_FRAC);
                two_qy = two_qy << (INT32_QUAT_FRAC - INT32_TRIG_FRAC);
                q.setQi((int) (((r.getElement(2, 0) - r.getElement(0, 2)) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qy));
                q.setQx((int) (((r.getElement(0, 1) + r.getElement(1, 0)) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qy));
                q.setQy((int) (two_qy / 2));
                q.setQz((int) (((r.getElement(1, 2) + r.getElement(2, 1)) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qy));
            } else {
                int two_qz_two = r.getElement(2, 2) - r.getElement(0, 0)
                - r.getElement(1, 1) + TRIG_BFP_OF_REAL(1.);
                long two_qz = int32_sqrt(two_qz_two << INT32_TRIG_FRAC);
                two_qz = two_qz << (INT32_QUAT_FRAC - INT32_TRIG_FRAC);
                q.setQi((int) (((r.getElement(0, 1) - r.getElement(1, 0)) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qz));
                q.setQx((int) (((r.getElement(2, 0) + r.getElement(0, 2)) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qz));
                q.setQy((int) (((r.getElement(1, 2) + r.getElement(2, 1)) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qz));
                q.setQz((int) (two_qz / 2));
            }
        }
    }

    /********* Euler Angles *********/

    public static void int32_eulers_of_rmat(Eulers<Integer> e, RMat<Integer> rm) {
        float dcm00 = TRIG_FLOAT_OF_BFP(rm.getElement(0, 0));
        float dcm01 = TRIG_FLOAT_OF_BFP(rm.getElement(0, 1));
        float dcm02 = TRIG_FLOAT_OF_BFP(rm.getElement(0, 2));
        float dcm12 = TRIG_FLOAT_OF_BFP(rm.getElement(1, 2));
        float dcm22 = TRIG_FLOAT_OF_BFP(rm.getElement(2, 2));
        float phi   = atan2f(dcm12, dcm22);
        float theta = -asinf(dcm02);
        float psi   = atan2f(dcm01, dcm00);
        e.setPsi(ANGLE_BFP_OF_REAL(phi));
        e.setTheta(ANGLE_BFP_OF_REAL(theta));
        e.setPsi(ANGLE_BFP_OF_REAL(psi));
    }

    private static float asinf(float y) {
        return (float) Math.asin(y);
    }


    private static float atan2f(float y, float x) {
        return (float) Math.atan2(y,x);
    }

    public static void int32_eulers_of_quat(Eulers<Integer> e, Quat<Integer> q) {
        int qx2  = INT_MULT_RSHIFT(q.getQx(), q.getQx(), INT32_QUAT_FRAC);
        int qy2  = INT_MULT_RSHIFT(q.getQy(), q.getQy(), INT32_QUAT_FRAC);
        int qz2  = INT_MULT_RSHIFT(q.getQz(), q.getQz(), INT32_QUAT_FRAC);
        int qiqx = INT_MULT_RSHIFT(q.getQi(), q.getQx(), INT32_QUAT_FRAC);
        int qiqy = INT_MULT_RSHIFT(q.getQi(), q.getQy(), INT32_QUAT_FRAC);
        int qiqz = INT_MULT_RSHIFT(q.getQi(), q.getQz(), INT32_QUAT_FRAC);
        int qxqy = INT_MULT_RSHIFT(q.getQx(), q.getQy(), INT32_QUAT_FRAC);
        int qxqz = INT_MULT_RSHIFT(q.getQx(), q.getQz(), INT32_QUAT_FRAC);
        int qyqz = INT_MULT_RSHIFT(q.getQy(), q.getQz(), INT32_QUAT_FRAC);
        int one = TRIG_BFP_OF_REAL(1);
        int two = TRIG_BFP_OF_REAL(2);

        /* dcm00 = 1.0 - 2.*(  qy2 +  qz2 ); */
        int idcm00 =  one - INT_MULT_RSHIFT(two, (qy2 + qz2),
                INT32_TRIG_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC);
  /* dcm01 =       2.*( qxqy + qiqz ); */
        int idcm01 = INT_MULT_RSHIFT(two, (qxqy + qiqz),
                INT32_TRIG_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC);
  /* dcm02 =       2.*( qxqz - qiqy ); */
        int idcm02 = INT_MULT_RSHIFT(two, (qxqz - qiqy),
                INT32_TRIG_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC);
  /* dcm12 =       2.*( qyqz + qiqx ); */
        int idcm12 = INT_MULT_RSHIFT(two, (qyqz + qiqx),
                INT32_TRIG_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC);
  /* dcm22 = 1.0 - 2.*(  qx2 +  qy2 ); */
        int idcm22 = one - INT_MULT_RSHIFT(two, (qx2 + qy2),
                INT32_TRIG_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC);
        float dcm00 = (float)idcm00 / (1 << INT32_TRIG_FRAC);
        float dcm01 = (float)idcm01 / (1 << INT32_TRIG_FRAC);
        float dcm02 = (float)idcm02 / (1 << INT32_TRIG_FRAC);
        float dcm12 = (float)idcm12 / (1 << INT32_TRIG_FRAC);
        float dcm22 = (float)idcm22 / (1 << INT32_TRIG_FRAC);

        float phi   = atan2f(dcm12, dcm22);
        float theta = -asinf(dcm02);
        float psi   = atan2f(dcm01, dcm00);
        e.setPhi(ANGLE_BFP_OF_REAL(phi));
        e.setTheta(ANGLE_BFP_OF_REAL(theta));
        e.setPsi(ANGLE_BFP_OF_REAL(psi));
    }

    /****** Rotational Speeds *******/
    public static void int32_rates_of_eulers_dot_321(Rates<Integer> r, Eulers<Integer> e, Eulers<Integer> ed) {
        int sphi = PprzTrig.PPRZ_ITRIG_SIN(e.getPhi());
        int cphi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi());
        int stheta = PprzTrig.PPRZ_ITRIG_SIN(e.getTheta());
        int ctheta = PprzTrig.PPRZ_ITRIG_COS(e.getTheta());

        int cphi_ctheta = INT_MULT_RSHIFT(cphi,   ctheta, INT32_TRIG_FRAC);
        int sphi_ctheta = INT_MULT_RSHIFT(sphi,   ctheta, INT32_TRIG_FRAC);

        r.setP(- INT_MULT_RSHIFT(stheta, ed.getPsi(), INT32_TRIG_FRAC) + ed.getPhi());
        r.setQ(INT_MULT_RSHIFT(sphi_ctheta, ed.getPsi(), INT32_TRIG_FRAC) + INT_MULT_RSHIFT(cphi, ed.getTheta(), INT32_TRIG_FRAC));
        r.setR(INT_MULT_RSHIFT(cphi_ctheta, ed.getPsi(), INT32_TRIG_FRAC) - INT_MULT_RSHIFT(sphi, ed.getTheta(), INT32_TRIG_FRAC));
    }

    public static void int32_eulers_dot_321_of_rates(Eulers<Integer> ed, Eulers<Integer> e, Rates<Integer> r) {
        int sphi = PprzTrig.PPRZ_ITRIG_SIN(e.getPhi());
        int cphi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi());
        int stheta = PprzTrig.PPRZ_ITRIG_SIN(e.getTheta());
        long ctheta = PprzTrig.PPRZ_ITRIG_COS(e.getTheta());

        if (ctheta != 0) {
            long cphi_stheta = INT_MULT_RSHIFT(cphi, stheta, INT32_TRIG_FRAC);
            long sphi_stheta = INT_MULT_RSHIFT(sphi, stheta, INT32_TRIG_FRAC);

            ed.setPhi(r.getP() + (int)((sphi_stheta * (long)r.getQ()) / ctheta) + (int)((cphi_stheta * (long)r.getR()) / ctheta));
            ed.setTheta(INT_MULT_RSHIFT(cphi, r.getQ(), INT32_TRIG_FRAC) - INT_MULT_RSHIFT(sphi, r.getR(), INT32_TRIG_FRAC));
            ed.setPsi((int)(((long)sphi * (long)r.getQ()) / ctheta) + (int)(((long)cphi * (long)r.getR()) / ctheta));
        }
  /* FIXME: What do you wanna do when you hit the singularity ? */
  /* probably not return an uninitialized variable, or ?        */
        else {
            PprzAlgebra.EULERS_ASSIGN(ed,0,0,0);
        }
    }

//    Int Algebra .h file

}

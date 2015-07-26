package ub.juav.autopilot.math.functions;

import ub.juav.autopilot.math.structs.ints.*;
import ub.juav.autopilot.math.structs.longs.LongQuat;
import ub.juav.autopilot.math.util.LlDiv;
import ub.juav.autopilot.math.util.NumberMath;

/**
 * Created by adamczer on 7/12/15.
 */
public class PprzAlgebraInt {
    private static final int  INT32_POS_FRAC =8;
    private static final double  INT32_POS_OF_CM =2.56;
    private static final int  INT32_POS_OF_CM_NUM =64;
    private static final int  INT32_POS_OF_CM_DEN =25;

    private static final int  INT32_SPEED_FRAC =19;
    private static final double  INT32_SPEED_OF_CM_S =5242.88;
    private static final int  INT32_SPEED_OF_CM_S_NUM =41943;
    private static final int  INT32_SPEED_OF_CM_S_DEN =8;

    private static final int  INT32_ACCEL_FRAC =10;
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
    public static void INT_VECT2_ZERO(IntVect2 v) {
        PprzAlgebra.VECT2_ASSIGN(v, 0, 0);
    }
    public static int INT32_VECT2_NORM(IntVect2 v) {
        return PprzAlgebraInt.int32_sqrt(PprzAlgebra.VECT2_NORM2(v).intValue());
    }

    public static int int32_vect2_norm2(IntVect2 v) {
        return INT32_VECT2_NORM(v);
    }

    public static void int32_vect2_normalize(IntVect2 v, int frac) {
        int n = int32_vect2_norm2(v);
        if (n>0) {
            int f = BFP_OF_REAL(1.,frac);
            v.setX(v.getX().intValue()+ (f/n));
            v.setY(v.getY().intValue()+ (f/n));
        }
    }

    public static void INT32_VECT2_NORMALIZE(IntVect2 v, int frac) {
        int32_vect2_normalize(v,frac);
    }

    public static void INT32_VECT2_RSHIFT(IntVect2 o, IntVect2 i, int r) {
        o.setX(i.getX().intValue()>>r);
        o.setY(i.getY().intValue()>>r);
    }

    public static void INT32_VECT2_LSHIFT(IntVect2 o, IntVect2 i, int r) {
        o.setX(i.getX().intValue()<<r);
        o.setY(i.getY().intValue()<<r);
    }

    public static void INT32_VECT2_SCALE_2(IntVect2 a, IntVect2 b, int num, int den) {
        a.setX(b.getX().intValue()*num/den);
        a.setY(b.getY().intValue()*num/den);
    }

    /***************** 3D VECTORS *****************/

    public static void INT32_VECT3_SCALE_2(IntVect3 a, IntVect3 b, int num, int den) {
        a.setX(b.getX().intValue()*num/den);
        a.setY(b.getY().intValue() * num / den);
        a.setZ(b.getZ().intValue() * num / den);
    }

    public static int INT32_VECT2_NORM(IntVect3 v) {
        return PprzAlgebraInt.int32_sqrt(PprzAlgebra.VECT3_NORM2(v).intValue());
    }

    public static void INT32_VECT3_RSHIFT(IntVect3 o, IntVect3 i, int r) {
        o.setX(i.getX().intValue()>>r);
        o.setY(i.getY().intValue() >> r);
        o.setZ(i.getZ().intValue() >> r);
    }

    public static void INT32_VECT3_LSHIFT(IntVect3 o, IntVect3 i, int r) {
        o.setX(i.getX().intValue()<<r);
        o.setY(i.getY().intValue() << r);
        o.setZ(i.getZ().intValue() << r);
    }

    public static void INT_VECT3_ZERO(IntVect3 v) {
        PprzAlgebra.VECT3_ASSIGN(v, 0, 0, 0);
    }
    public static void INT32_VECT3_ZERO(IntVect3 v) {
        PprzAlgebra.VECT3_ASSIGN(v, 0, 0, 0);
    }

    /********** 3x3 Matricies **********/

    public static void INT32_MAT33_ZERO(IntMat33 mat) {
        mat.zero();
    }

    public static void INT32_MAT33_DIAG(IntMat33 mat, int d00,int d11,int d22) {
        PprzAlgebra.MAT33_DIAG(mat,d00,d11,d22);
    }


    /************** Rotation Matricies ***************/

    /* initialises a rotation matrix to identity */
    public static void int32_rmat_identity(IntRMat mat) {
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
    public static void int32_rmat_comp(IntRMat ma2c, IntRMat ma2b, IntRMat mb2c) {
        ma2c.setElement((mb2c.getElement(0,0).intValue()*ma2b.getElement(0, 0).intValue()+mb2c.getElement(0, 1).intValue()*ma2b.getElement(1, 0).intValue()+mb2c.getElement(0, 2).intValue()*ma2b.getElement(2,0).intValue()) >> INT32_TRIG_FRAC,0,0);
        ma2c.setElement((mb2c.getElement(0,0).intValue()*ma2b.getElement(0, 1).intValue()+mb2c.getElement(0, 1).intValue()*ma2b.getElement(1, 1).intValue()+mb2c.getElement(0, 2).intValue()*ma2b.getElement(2,1).intValue()) >> INT32_TRIG_FRAC,0,1);
        ma2c.setElement((mb2c.getElement(0,0).intValue()*ma2b.getElement(0, 2).intValue()+mb2c.getElement(0, 1).intValue()*ma2b.getElement(1, 2).intValue()+mb2c.getElement(0, 2).intValue()*ma2b.getElement(2,2).intValue()) >> INT32_TRIG_FRAC,0,2);
        ma2c.setElement((mb2c.getElement(1,0).intValue()*ma2b.getElement(0, 0).intValue()+mb2c.getElement(1, 1).intValue()*ma2b.getElement(1, 0).intValue()+mb2c.getElement(1, 2).intValue()*ma2b.getElement(2,0).intValue()) >> INT32_TRIG_FRAC,1,0);
        ma2c.setElement((mb2c.getElement(1,0).intValue()*ma2b.getElement(0,1).intValue()+mb2c.getElement(1,1).intValue()*ma2b.getElement(1,1).intValue()+mb2c.getElement(1,2).intValue()*ma2b.getElement(2,1).intValue()) >> INT32_TRIG_FRAC,1,1);
        ma2c.setElement((mb2c.getElement(1,0).intValue()*ma2b.getElement(0,2).intValue()+mb2c.getElement(1,1).intValue()*ma2b.getElement(1,2).intValue()+mb2c.getElement(1,2).intValue()*ma2b.getElement(2,2).intValue()) >> INT32_TRIG_FRAC,1,2);
        ma2c.setElement((mb2c.getElement(2,0).intValue()*ma2b.getElement(0,0).intValue()+mb2c.getElement(2,1).intValue()*ma2b.getElement(1,0).intValue()+mb2c.getElement(2,2).intValue()*ma2b.getElement(2,0).intValue()) >> INT32_TRIG_FRAC,2,0);
        ma2c.setElement((mb2c.getElement(2,0).intValue()*ma2b.getElement(0,1).intValue()+mb2c.getElement(2,1).intValue()*ma2b.getElement(1,1).intValue()+mb2c.getElement(2,2).intValue()*ma2b.getElement(2,1).intValue()) >> INT32_TRIG_FRAC,2,1);
        ma2c.setElement((mb2c.getElement(2,0).intValue()*ma2b.getElement(0,2).intValue()+mb2c.getElement(2,1).intValue()*ma2b.getElement(1,2).intValue()+mb2c.getElement(2,2).intValue()*ma2b.getElement(2,2).intValue()) >> INT32_TRIG_FRAC,2,2);
    }

    /** Composition (multiplication) of two rotation matrices.
     * _m_a2b = _m_a2c comp_inv _m_b2c , aka  _m_a2b = inv(_m_b2c) * _m_a2c
     */
    public static void int32_rmat_comp_inv(IntRMat ma2b, IntRMat ma2c, IntRMat mb2c) {
        ma2b.setElement((mb2c.getElement(0,0).intValue()*ma2c.getElement(0, 0).intValue()+mb2c.getElement(1, 0).intValue()*ma2c.getElement(1, 0).intValue()+mb2c.getElement(2, 0).intValue()*ma2c.getElement(2,0).intValue()) >> INT32_TRIG_FRAC,0,0);
        ma2b.setElement((mb2c.getElement(0,0).intValue()*ma2c.getElement(0, 1).intValue()+mb2c.getElement(1, 0).intValue()*ma2c.getElement(1, 1).intValue()+mb2c.getElement(2, 0).intValue()*ma2c.getElement(2,1).intValue()) >> INT32_TRIG_FRAC,0,1);
        ma2b.setElement((mb2c.getElement(0,0).intValue()*ma2c.getElement(0, 2).intValue()+mb2c.getElement(1, 0).intValue()*ma2c.getElement(1, 2).intValue()+mb2c.getElement(2, 0).intValue()*ma2c.getElement(2,2).intValue()) >> INT32_TRIG_FRAC,0,2);
        ma2b.setElement((mb2c.getElement(0,1).intValue()*ma2c.getElement(0, 0).intValue()+mb2c.getElement(1, 1).intValue()*ma2c.getElement(1, 0).intValue()+mb2c.getElement(2, 1).intValue()*ma2c.getElement(2,0).intValue()) >> INT32_TRIG_FRAC,1,0);
        ma2b.setElement((mb2c.getElement(0,1).intValue()*ma2c.getElement(0,1).intValue()+mb2c.getElement(1,1).intValue()*ma2c.getElement(1,1).intValue()+mb2c.getElement(2,1).intValue()*ma2c.getElement(2,1).intValue()) >> INT32_TRIG_FRAC,1,1);
        ma2b.setElement((mb2c.getElement(0,1).intValue()*ma2c.getElement(0,2).intValue()+mb2c.getElement(1,1).intValue()*ma2c.getElement(1,2).intValue()+mb2c.getElement(2,1).intValue()*ma2c.getElement(2,2).intValue()) >> INT32_TRIG_FRAC,1,2);
        ma2b.setElement((mb2c.getElement(0,2).intValue()*ma2c.getElement(0,0).intValue()+mb2c.getElement(1,2).intValue()*ma2c.getElement(1,0).intValue()+mb2c.getElement(2,2).intValue()*ma2c.getElement(2,0).intValue()) >> INT32_TRIG_FRAC,2,0);
        ma2b.setElement((mb2c.getElement(0,2).intValue()*ma2c.getElement(0,1).intValue()+mb2c.getElement(1,2).intValue()*ma2c.getElement(1,1).intValue()+mb2c.getElement(2,2).intValue()*ma2c.getElement(2,1).intValue()) >> INT32_TRIG_FRAC,2,1);
        ma2b.setElement((mb2c.getElement(0,2).intValue()*ma2c.getElement(0,2).intValue()+mb2c.getElement(1,2).intValue()*ma2c.getElement(1,2).intValue()+mb2c.getElement(2,2).intValue()*ma2c.getElement(2,2).intValue()) >> INT32_TRIG_FRAC,2,2);
    }

    /** rotate 3D vector by rotation matrix.
     * vb = m_a2b * va
     */
    public static void int32_rmat_vmult(IntVect3 vb, IntRMat ma2b, IntVect3 va) {
        vb.setX((ma2b.getElement(0,0).intValue()*va.getX().intValue()+ma2b.getElement(0,1).intValue() * va.getY().intValue() + ma2b.getElement(0,2).intValue()*va.getZ().intValue())>> INT32_TRIG_FRAC);
        vb.setY((ma2b.getElement(1,0).intValue()*va.getX().intValue()+ma2b.getElement(1,1).intValue() * va.getY().intValue() + ma2b.getElement(1,2).intValue()*va.getZ().intValue())>> INT32_TRIG_FRAC);
        vb.setZ((ma2b.getElement(2,0).intValue()*va.getX().intValue()+ma2b.getElement(2,1).intValue() * va.getY().intValue() + ma2b.getElement(2,2).intValue()*va.getZ().intValue())>> INT32_TRIG_FRAC);
    }

    /** rotate 3D vector by transposed rotation matrix.
     * vb = m_b2a^T * va
     */
    public static void int32_rmat_transp_vmult(IntVect3 vb, IntRMat ma2b, IntVect3 va) {
        vb.setX((ma2b.getElement(0,0).intValue()*va.getX().intValue()+ma2b.getElement(1,0).intValue() * va.getY().intValue() + ma2b.getElement(2,0).intValue()*va.getZ().intValue())>> INT32_TRIG_FRAC);
        vb.setY((ma2b.getElement(0,1).intValue()*va.getX().intValue()+ma2b.getElement(1,1).intValue() * va.getY().intValue() + ma2b.getElement(2,1).intValue()*va.getZ().intValue())>> INT32_TRIG_FRAC);
        vb.setZ((ma2b.getElement(0,2).intValue()*va.getX().intValue()+ma2b.getElement(1,2).intValue() * va.getY().intValue() + ma2b.getElement(2,2).intValue()*va.getZ().intValue())>> INT32_TRIG_FRAC);
    }

    /** rotate anglular rates by rotation matrix.
     * rb = m_a2b * ra
     */
    public static void int32_rmat_ratemult(IntRates rb, IntRMat ma2b, IntRates ra) {
        rb.setP((ma2b.getElement(0, 0).intValue() * ra.getP().intValue() + ma2b.getElement(0, 1).intValue() * ra.getQ().intValue() + ma2b.getElement(0, 2).intValue() * ra.getR().intValue())>> INT32_TRIG_FRAC);
        rb.setQ((ma2b.getElement(1, 0).intValue() * ra.getP().intValue() + ma2b.getElement(1, 1).intValue() * ra.getQ().intValue() + ma2b.getElement(1, 2).intValue() * ra.getR().intValue())>> INT32_TRIG_FRAC);
        rb.setR((ma2b.getElement(2, 0).intValue() * ra.getP().intValue() + ma2b.getElement(2, 1).intValue() * ra.getQ().intValue() + ma2b.getElement(2, 2).intValue() * ra.getR().intValue())>> INT32_TRIG_FRAC);
    }

    /** rotate anglular rates by transposed rotation matrix.
     * rb = m_b2a^T * ra
     */
    public static void int32_rmat_transp_ratemult(IntRates rb, IntRMat mb2a, IntRates ra) {
        rb.setP((mb2a.getElement(0, 0).intValue() * ra.getP().intValue() + mb2a.getElement(1, 0).intValue() * ra.getQ().intValue() + mb2a.getElement(2, 0).intValue() * ra.getR().intValue()) >> INT32_TRIG_FRAC);
        rb.setQ((mb2a.getElement(0, 1).intValue() * ra.getP().intValue() + mb2a.getElement(1, 1).intValue() * ra.getQ().intValue() + mb2a.getElement(2, 1).intValue() * ra.getR().intValue()) >> INT32_TRIG_FRAC);
        rb.setR((mb2a.getElement(0, 2).intValue() * ra.getP().intValue() + mb2a.getElement(1, 2).intValue() * ra.getQ().intValue() + mb2a.getElement(2, 2).intValue() * ra.getR().intValue()) >> INT32_TRIG_FRAC);
    }

    /** Convert unit quaternion to rotation matrix.
     * http://www.mathworks.com/access/helpdesk_r13/help/toolbox/aeroblks/quaternionstodirectioncosinematrix.html
     */
    public static void int32_rmat_of_quat(IntRMat rm, IntQuat q) {
        int _2qi2_m1 =  INT_MULT_RSHIFT(q.getQi().intValue(), q.getQi().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1) - TRIG_BFP_OF_REAL(1);
        rm.setElement(INT_MULT_RSHIFT(q.getQx().intValue(), q.getQx().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),0,0);
        rm.setElement(INT_MULT_RSHIFT(q.getQy().intValue(), q.getQy().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1), 1, 1);
        rm.setElement(INT_MULT_RSHIFT(q.getQz().intValue(), q.getQz().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),2,2);

        int _2qiqx = INT_MULT_RSHIFT(q.getQi().intValue(), q.getQx().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1);
        int _2qiqy = INT_MULT_RSHIFT(q.getQi().intValue(), q.getQy().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1);
        int _2qiqz = INT_MULT_RSHIFT(q.getQi().intValue(), q.getQz().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1);

        rm.setElement(INT_MULT_RSHIFT(q.getQx().intValue(), q.getQy().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),0,1);
        rm.setElement(INT_MULT_RSHIFT(q.getQx().intValue(), q.getQz().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),0,2);
        rm.setElement(INT_MULT_RSHIFT(q.getQy().intValue(), q.getQz().intValue(), INT32_QUAT_FRAC + INT32_QUAT_FRAC - INT32_TRIG_FRAC - 1),1,2);

        rm.setElement(rm.getElement(0,0).intValue() + _2qi2_m1,0,0);
        rm.setElement(rm.getElement(0,1).intValue() - _2qiqz,1,0);
        rm.setElement(rm.getElement(0,2).intValue()+_2qiqy,2,0);
        rm.setElement(rm.getElement(1,2).intValue()-_2qiqx,2,1);
        rm.setElement(rm.getElement(1,1).intValue()+_2qi2_m1,1,1);
        rm.setElement(rm.getElement(0,1).intValue()+_2qiqz,0,1);
        rm.setElement(rm.getElement(0,2).intValue()-_2qiqy,0,2);
        rm.setElement(rm.getElement(1,2).intValue()+_2qiqx,1,2);
        rm.setElement(rm.getElement(2,2).intValue()+_2qi2_m1,2,2);
    }

    /** Rotation matrix from 321 Euler angles.
     * http://www.mathworks.com/access/helpdesk_r13/help/toolbox/aeroblks/euleranglestodirectioncosinematrix.html
     */
    public static void int32_rmat_of_eulers_321(IntRMat rm, IntEulers e) {
        int sphi = PprzTrig.PPRZ_ITRIG_SIN(e.getPhi().intValue());
        int cphi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi().intValue());
        int stheta = PprzTrig.PPRZ_ITRIG_SIN(e.getTheta().intValue());
        int ctheta = PprzTrig.PPRZ_ITRIG_COS(e.getTheta().intValue());
        int spsi = PprzTrig.PPRZ_ITRIG_SIN(e.getPsi().intValue());
        int cpsi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi().intValue());

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

    public static void int32_rmat_of_eulers_312(IntRMat rm, IntEulers e) {
        int sphi = PprzTrig.PPRZ_ITRIG_SIN(e.getPhi().intValue());
        int cphi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi().intValue());
        int stheta = PprzTrig.PPRZ_ITRIG_SIN(e.getTheta().intValue());
        int ctheta = PprzTrig.PPRZ_ITRIG_COS(e.getTheta().intValue());
        int spsi = PprzTrig.PPRZ_ITRIG_SIN(e.getPsi().intValue());
        int cpsi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi().intValue());

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

    public static void int32_quat_comp(IntQuat a2c, IntQuat a2b, IntQuat b2c) {
        a2c.setQi((
                a2b.getQi().intValue() * b2c.getQi().intValue() -
                a2b.getQx().intValue() * b2c.getQx().intValue() -
                a2b.getQy().intValue() * b2c.getQy().intValue() -
                a2b.getQz().intValue() * b2c.getQz().intValue())
                >> INT32_QUAT_FRAC);
        a2c.setQx((
                        a2b.getQi().intValue() * b2c.getQx().intValue() +
                        a2b.getQx().intValue() * b2c.getQi().intValue() +
                        a2b.getQy().intValue() * b2c.getQz().intValue() -
                        a2b.getQz().intValue() * b2c.getQy().intValue())
                >> INT32_QUAT_FRAC);
        a2c.setQy((
                        a2b.getQi().intValue() * b2c.getQy().intValue() -
                        a2b.getQx().intValue() * b2c.getQz().intValue() +
                        a2b.getQy().intValue() * b2c.getQi().intValue() +
                        a2b.getQz().intValue() * b2c.getQx().intValue())
                >> INT32_QUAT_FRAC);
        a2c.setQz((
                        a2b.getQi().intValue() * b2c.getQz().intValue() +
                        a2b.getQx().intValue() * b2c.getQy().intValue() -
                        a2b.getQy().intValue() * b2c.getQx().intValue() +
                        a2b.getQz().intValue() * b2c.getQi().intValue())
                >> INT32_QUAT_FRAC);
    }
    public static void int32_quat_comp_inv(IntQuat a2b, IntQuat a2c, IntQuat b2c) {
        a2b.setQi((
                        a2c.getQi().intValue() * b2c.getQi().intValue() +
                        a2c.getQx().intValue() * b2c.getQx().intValue() +
                        a2c.getQy().intValue() * b2c.getQy().intValue() +
                        a2c.getQz().intValue() * b2c.getQz().intValue())
                >> INT32_QUAT_FRAC);
        a2b.setQx((
                        a2c.getQi().intValue() * b2c.getQx().intValue() +
                        a2c.getQx().intValue() * b2c.getQi().intValue() -
                        a2c.getQy().intValue() * b2c.getQz().intValue() +
                        a2c.getQz().intValue() * b2c.getQy().intValue())
                >> INT32_QUAT_FRAC);
        a2b.setQy((
                        a2c.getQi().intValue() * b2c.getQy().intValue() +
                        a2c.getQx().intValue() * b2c.getQz().intValue() +
                        a2c.getQy().intValue() * b2c.getQi().intValue() -
                        a2c.getQz().intValue() * b2c.getQx().intValue())
                >> INT32_QUAT_FRAC);
        a2b.setQz((
                        a2c.getQi().intValue() * b2c.getQz().intValue() -
                        a2c.getQx().intValue() * b2c.getQy().intValue() +
                        a2c.getQy().intValue() * b2c.getQx().intValue() +
                        a2c.getQz().intValue() * b2c.getQi().intValue())
                >> INT32_QUAT_FRAC);
    }

    public static void int32_quat_inv_comp(IntQuat b2c, IntQuat a2b, IntQuat a2c) {
        b2c.setQi((
                        a2b.getQi().intValue() * a2c.getQi().intValue() +
                        a2b.getQx().intValue() * a2c.getQx().intValue() +
                        a2b.getQy().intValue() * a2c.getQy().intValue() +
                        a2b.getQz().intValue() * a2c.getQz().intValue())
                >> INT32_QUAT_FRAC);
        b2c.setQx((
                        a2b.getQi().intValue() * a2c.getQx().intValue() -
                        a2b.getQx().intValue() * a2c.getQi().intValue() -
                        a2b.getQy().intValue() * a2c.getQz().intValue() +
                        a2b.getQz().intValue() * a2c.getQy().intValue())
                >> INT32_QUAT_FRAC);
        b2c.setQy((
                        a2b.getQi().intValue() * a2c.getQy().intValue() +
                        a2b.getQx().intValue() * a2c.getQz().intValue() -
                        a2b.getQy().intValue() * a2c.getQi().intValue() -
                        a2b.getQz().intValue() * a2c.getQx().intValue())
                >> INT32_QUAT_FRAC);
        b2c.setQz((
                        a2b.getQi().intValue() * a2c.getQz().intValue() -
                        a2b.getQx().intValue() * a2c.getQy().intValue() +
                        a2b.getQy().intValue() * a2c.getQx().intValue() -
                        a2b.getQz().intValue() * a2c.getQi().intValue())
                >> INT32_QUAT_FRAC);
    }

    public static void int32_quat_comp_norm_shortest(IntQuat a2c, IntQuat a2b, IntQuat b2c) {
        int32_quat_comp(a2c, a2b, b2c);
        int32_quat_wrap_shortest(a2c);
        int32_quat_normalize(a2c);
    }

    private static void int32_quat_normalize(IntQuat q) {
        int n = int32_quat_norm(q);
        if (n > 0) {
            q.setQi(q.getQi().intValue()*QUAT1_BFP_OF_REAL(1));
            q.setQx(q.getQx().intValue() * QUAT1_BFP_OF_REAL(1));
            q.setQy(q.getQy().intValue() * QUAT1_BFP_OF_REAL(1));
            q.setQz(q.getQz().intValue() * QUAT1_BFP_OF_REAL(1));
        }
    }

    private static int int32_quat_norm(IntQuat q) {
        return int32_sqrt((NumberMath.sq(q.getQi()).intValue()+NumberMath.sq(q.getQx()).intValue()+NumberMath.sq(q.getQy()).intValue()+NumberMath.sq(q.getQz()).intValue()));
    }

    private static void int32_quat_wrap_shortest(IntQuat quat) {
        if (quat.getQi().intValue() < 0) {
            PprzAlgebra.QUAT_EXPLEMENTARY(quat, quat);
        }
    }

    public static void int32_quat_comp_inv_norm_shortest(IntQuat a2b, IntQuat a2c, IntQuat b2c) {
        int32_quat_comp_inv(a2b, a2c, b2c);
        int32_quat_wrap_shortest(a2b);
        int32_quat_normalize(a2b);
    }

    public static void int32_quat_inv_comp_norm_shortest(IntQuat b2c, IntQuat a2b, IntQuat a2c) {
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

    public static void int32_quat_derivative(IntQuat qd, IntRates r, IntQuat q) {
        qd.setQi((-(r.getP().intValue()*q.getQx().intValue()+r.getQ().intValue()*q.getQy().intValue()+r.getR().intValue()*q.getQz().intValue())) >> (INT32_RATE_FRAC+1));
        qd.setQx((-(-r.getP().intValue() * q.getQi().intValue() - r.getR().intValue() * q.getQy().intValue() + r.getQ().intValue() * q.getQz().intValue())) >> (INT32_RATE_FRAC + 1));
        qd.setQy((-(-r.getP().intValue() * q.getQi().intValue() + r.getR().intValue() * q.getQx().intValue() - r.getP().intValue() * q.getQz().intValue())) >> (INT32_RATE_FRAC + 1));
        qd.setQz((-(-r.getP().intValue() * q.getQi().intValue() - +r.getQ().intValue() * q.getQx().intValue() + r.getP().intValue() * q.getQy().intValue())) >> (INT32_RATE_FRAC + 1));
    }

    /** in place quaternion first order integration with constant rotational velocity. */
    public static void int32_quat_integrate_fi(IntQuat q, LongQuat hr, IntRates omega, int freq) {
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

    public static void int32_quat_vmult(IntVect3 vOut, IntQuat q, IntVect3 vIn) {
        int _2qi2_m1 = ((q.getQi().intValue() * q.getQi().intValue()) >> (INT32_QUAT_FRAC - 1)) - QUAT1_BFP_OF_REAL(1);
        int _2qx2    = (q.getQx().intValue() * q.getQx().intValue()) >> (INT32_QUAT_FRAC - 1);
        int _2qy2    = (q.getQy().intValue() * q.getQy().intValue()) >> (INT32_QUAT_FRAC - 1);
        int _2qz2    = (q.getQz().intValue() * q.getQz().intValue()) >> (INT32_QUAT_FRAC - 1);
        int _2qiqx   = (q.getQi().intValue() * q.getQx().intValue()) >> (INT32_QUAT_FRAC - 1);
        int _2qiqy   = (q.getQi().intValue() * q.getQy().intValue()) >> (INT32_QUAT_FRAC - 1);
        int _2qiqz   = (q.getQi().intValue() * q.getQz().intValue()) >> (INT32_QUAT_FRAC - 1);
        int m01 = ((q.getQx().intValue() * q.getQy().intValue()) >> (INT32_QUAT_FRAC - 1)) + _2qiqz;
        int m02 = ((q.getQx().intValue() * q.getQz().intValue()) >> (INT32_QUAT_FRAC - 1)) - _2qiqy;
        int m12 = ((q.getQy().intValue() * q.getQz().intValue()) >> (INT32_QUAT_FRAC - 1)) + _2qiqx;
        vOut.setX((_2qi2_m1 * vIn.getX().intValue() + _2qx2 * vIn.getX().intValue() + m01 * vIn.getY().intValue() +  m02 * vIn.getZ().intValue()) >> INT32_QUAT_FRAC);
        vOut.setY((_2qi2_m1*vIn.getY().intValue() + m01 * vIn.getX().intValue() - 2 * _2qiqz * vIn.getX().intValue() + _2qy2 * vIn.getY().intValue() + m12 * vIn.getZ().intValue()) >>
        INT32_QUAT_FRAC);
        vOut.setZ((_2qi2_m1 * vIn.getZ().intValue() + m02 * vIn.getX().intValue() + 2 * _2qiqy * vIn.getX().intValue() + m12 * vIn.getY().intValue() - 2 * _2qiqx * vIn.getY().intValue() + _2qz2 *
                vIn.getZ().intValue()) >> INT32_QUAT_FRAC);
    }

    /*
 * http://www.mathworks.com/access/helpdesk_r13/help/toolbox/aeroblks/euleranglestoquaternions.html
 */
    public static void int32_quat_of_eulers(IntQuat q, IntEulers e) {
        int phi2   = e.getPhi().intValue()   / 2;
        int theta2 = e.getTheta().intValue() / 2;
        int psi2   = e.getPsi().intValue()   / 2;

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

    public static void int32_quat_of_axis_angle(IntQuat q, IntVect3 uv, int angle) {
        int san2 = PprzTrig.PPRZ_ITRIG_SIN((angle / 2));
        int can2 = PprzTrig.PPRZ_ITRIG_COS((angle / 2));
        q.setQi(can2);
        q.setQx(san2 * uv.getX().intValue());
        q.setQy(san2 * uv.getY().intValue());
        q.setQz(san2 * uv.getZ().intValue());
    }

    public static void int32_quat_of_rmat(IntQuat q, IntRMat r) {
        int tr = PprzAlgebra.RMAT_TRACE(r).intValue();
        if (tr > 0) {
            int two_qi_two = TRIG_BFP_OF_REAL(1.) + tr;
            long two_qi = int32_sqrt(two_qi_two << INT32_TRIG_FRAC);
            two_qi = two_qi << (INT32_QUAT_FRAC - INT32_TRIG_FRAC);
            q.setQi((int) (two_qi / 2));
            q.setQx((int) (((r.getElement(1, 2).intValue() - r.getElement(2, 1).intValue()) <<
                        (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                        / two_qi));
            q.setQy((int) (((r.getElement(2, 0).intValue() - r.getElement(0, 2).intValue()) <<
                        (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                        / two_qi));
            q.setQz((int) (((r.getElement(0, 1).intValue() - r.getElement(1, 0).intValue()) <<
                        (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                        / two_qi));
        } else {
            if (r.getElement(0, 0).intValue() > r.getElement(1, 1).intValue() &&
            r.getElement(0, 0).intValue() > r.getElement( 2, 2).intValue()) {
                int two_qx_two = r.getElement(0, 0).intValue() - r.getElement(1, 1).intValue()
                - r.getElement(2, 2).intValue() + TRIG_BFP_OF_REAL(1.);
                long two_qx = int32_sqrt(two_qx_two << INT32_TRIG_FRAC);
                two_qx = two_qx << (INT32_QUAT_FRAC - INT32_TRIG_FRAC);
                q.setQi((int) (((r.getElement(1, 2).intValue() - r.getElement(2, 1).intValue()) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qx));
                q.setQx((int) (two_qx / 2));
                q.setQy((int) (((r.getElement(0, 1).intValue() + r.getElement(1, 0).intValue()) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qx));
                q.setQz((int) (((r.getElement(2, 0).intValue() + r.getElement(0, 2).intValue()) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qx));
            } else if (r.getElement(1, 1).intValue() > r.getElement(2, 2).intValue()) {
                int two_qy_two = r.getElement(1, 1).intValue() - r.getElement(0, 0).intValue()
                - r.getElement(2, 2).intValue() + TRIG_BFP_OF_REAL(1.);
                long two_qy = int32_sqrt(two_qy_two << INT32_TRIG_FRAC);
                two_qy = two_qy << (INT32_QUAT_FRAC - INT32_TRIG_FRAC);
                q.setQi((int) (((r.getElement(2, 0).intValue() - r.getElement(0, 2).intValue()) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qy));
                q.setQx((int) (((r.getElement(0, 1).intValue() + r.getElement(1, 0).intValue()) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qy));
                q.setQy((int) (two_qy / 2));
                q.setQz((int) (((r.getElement(1, 2).intValue() + r.getElement(2, 1).intValue()) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qy));
            } else {
                int two_qz_two = r.getElement(2, 2).intValue() - r.getElement(0, 0).intValue()
                - r.getElement(1, 1).intValue() + TRIG_BFP_OF_REAL(1.);
                long two_qz = int32_sqrt(two_qz_two << INT32_TRIG_FRAC);
                two_qz = two_qz << (INT32_QUAT_FRAC - INT32_TRIG_FRAC);
                q.setQi((int) (((r.getElement(0, 1).intValue() - r.getElement(1, 0).intValue()) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qz));
                q.setQx((int) (((r.getElement(2, 0).intValue() + r.getElement(0, 2).intValue()) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qz));
                q.setQy((int) (((r.getElement(1, 2).intValue() + r.getElement(2, 1).intValue()) <<
                                (INT32_QUAT_FRAC - INT32_TRIG_FRAC + INT32_QUAT_FRAC - 1))
                                / two_qz));
                q.setQz((int) (two_qz / 2));
            }
        }
    }

    /********* Euler Angles *********/

    public static void int32_eulers_of_rmat(IntEulers e, IntRMat rm) {
        float dcm00 = TRIG_FLOAT_OF_BFP(rm.getElement(0,0).intValue());
        float dcm01 = TRIG_FLOAT_OF_BFP(rm.getElement(0,1).intValue());
        float dcm02 = TRIG_FLOAT_OF_BFP(rm.getElement(0,2).intValue());
        float dcm12 = TRIG_FLOAT_OF_BFP(rm.getElement(1,2).intValue());
        float dcm22 = TRIG_FLOAT_OF_BFP(rm.getElement(2,2).intValue());
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

    public static void int32_eulers_of_quat(IntEulers e, IntQuat q) {
        int qx2  = INT_MULT_RSHIFT(q.getQx().intValue(), q.getQx().intValue(), INT32_QUAT_FRAC);
        int qy2  = INT_MULT_RSHIFT(q.getQy().intValue(), q.getQy().intValue(), INT32_QUAT_FRAC);
        int qz2  = INT_MULT_RSHIFT(q.getQz().intValue(), q.getQz().intValue(), INT32_QUAT_FRAC);
        int qiqx = INT_MULT_RSHIFT(q.getQi().intValue(), q.getQx().intValue(), INT32_QUAT_FRAC);
        int qiqy = INT_MULT_RSHIFT(q.getQi().intValue(), q.getQy().intValue(), INT32_QUAT_FRAC);
        int qiqz = INT_MULT_RSHIFT(q.getQi().intValue(), q.getQz().intValue(), INT32_QUAT_FRAC);
        int qxqy = INT_MULT_RSHIFT(q.getQx().intValue(), q.getQy().intValue(), INT32_QUAT_FRAC);
        int qxqz = INT_MULT_RSHIFT(q.getQx().intValue(), q.getQz().intValue(), INT32_QUAT_FRAC);
        int qyqz = INT_MULT_RSHIFT(q.getQy().intValue(), q.getQz().intValue(), INT32_QUAT_FRAC);
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
    public static void int32_rates_of_eulers_dot_321(IntRates r, IntEulers e, IntEulers ed) {
        int sphi = PprzTrig.PPRZ_ITRIG_SIN(e.getPhi().intValue());
        int cphi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi().intValue());
        int stheta = PprzTrig.PPRZ_ITRIG_SIN(e.getTheta().intValue());
        int ctheta = PprzTrig.PPRZ_ITRIG_COS(e.getTheta().intValue());

        int cphi_ctheta = INT_MULT_RSHIFT(cphi,   ctheta, INT32_TRIG_FRAC);
        int sphi_ctheta = INT_MULT_RSHIFT(sphi,   ctheta, INT32_TRIG_FRAC);

        r.setP(- INT_MULT_RSHIFT(stheta, ed.getPsi().intValue(), INT32_TRIG_FRAC) + ed.getPhi().intValue());
        r.setQ(INT_MULT_RSHIFT(sphi_ctheta, ed.getPsi().intValue(), INT32_TRIG_FRAC) + INT_MULT_RSHIFT(cphi, ed.getTheta().intValue(), INT32_TRIG_FRAC));
        r.setR(INT_MULT_RSHIFT(cphi_ctheta, ed.getPsi().intValue(), INT32_TRIG_FRAC) - INT_MULT_RSHIFT(sphi, ed.getTheta().intValue(), INT32_TRIG_FRAC));
    }

    public static void int32_eulers_dot_321_of_rates(IntEulers ed, IntEulers e, IntRates r) {
        int sphi = PprzTrig.PPRZ_ITRIG_SIN(e.getPhi().intValue());
        int cphi = PprzTrig.PPRZ_ITRIG_COS(e.getPhi().intValue());
        int stheta = PprzTrig.PPRZ_ITRIG_SIN(e.getTheta().intValue());
        long ctheta = PprzTrig.PPRZ_ITRIG_COS(e.getTheta().intValue());

        if (ctheta != 0) {
            long cphi_stheta = INT_MULT_RSHIFT(cphi, stheta, INT32_TRIG_FRAC);
            long sphi_stheta = INT_MULT_RSHIFT(sphi, stheta, INT32_TRIG_FRAC);

            ed.setPhi(r.getP().intValue() + (int)((sphi_stheta * (long)r.getQ().intValue()) / ctheta) + (int)((cphi_stheta * (long)r.getR().intValue()) / ctheta));
            ed.setTheta(INT_MULT_RSHIFT(cphi, r.getQ().intValue(), INT32_TRIG_FRAC) - INT_MULT_RSHIFT(sphi, r.getR().intValue(), INT32_TRIG_FRAC));
            ed.setPsi((int)(((long)sphi * (long)r.getQ().intValue()) / ctheta) + (int)(((long)cphi * (long)r.getR().intValue()) / ctheta));
        }
  /* FIXME: What do you wanna do when you hit the singularity ? */
  /* probably not return an uninitialized variable, or ?        */
        else {
            PprzAlgebra.EULERS_ASSIGN(ed,0,0,0);
        }
    }

}

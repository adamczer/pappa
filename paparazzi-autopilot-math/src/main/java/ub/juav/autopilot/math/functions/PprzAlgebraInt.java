package ub.juav.autopilot.math.functions;

import ub.juav.autopilot.math.structs.ints.IntMat33;
import ub.juav.autopilot.math.structs.ints.IntRMat;
import ub.juav.autopilot.math.structs.ints.IntVect2;
import ub.juav.autopilot.math.structs.ints.IntVect3;

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
    private static final int  INT32_ANGLE_FRAC =12;
    private static final int  INT32_RATE_FRAC =12;
    private static final int  INT32_ANGLE_PI_4  = (int)ANGLE_BFP_OF_REAL(   0.7853981633974483096156608458198757);
    private static final int  INT32_ANGLE_PI_2 =  (int)ANGLE_BFP_OF_REAL(   1.5707963267948966192313216916397514);
    private static final int  INT32_ANGLE_PI   =  (int)ANGLE_BFP_OF_REAL(   3.1415926535897932384626433832795029);
    private static final int  INT32_ANGLE_2_PI =  (int)ANGLE_BFP_OF_REAL(2.*3.1415926535897932384626433832795029);

    private static int  INT32_RAD_OF_DEG(int deg) {
        return (int)(((long)(deg) * 14964008)/857374503);
    }
    private static int  INT32_DEG_OF_RAD(int rad) {
        return (int)(((long)(rad) * 857374503)/14964008);
    }
    private static int INT32_ANGLE_NORMALIZE(int angle) {
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
        return (int) Math.sqrt((Integer)PprzAlgebra.VECT2_NORM(v));
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
        return (int) Math.sqrt((Integer)PprzAlgebra.VECT3_NORM2(v));
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
        mat.zero();
        mat.setElement(d00, 0, 0);
        mat.setElement(d11,1,1);
        mat.setElement(d22,2,2);
    }

    /************** Rotation Matricies ***************/

    /* initialises a rotation matrix to identity */
    public static void int32_rmat_identity(IntRMat mat) {
        INT32_MAT33_DIAG(mat,TRIG_BFP_OF_REAL(1.),TRIG_BFP_OF_REAL(1.),TRIG_BFP_OF_REAL(1.));
    }

    /** Composition (multiplication) of two rotation matrices.
     * m_a2c = m_a2b comp m_b2c , aka  m_a2c = m_b2c * m_a2b
     */
    public static void int32_rmat_comp(IntRMat matA2c, IntRMat matA2b, IntRMat matB2c) {
        //TODO
        throw new IllegalStateException("int32_rmat_comp is unimplemented");
    }

    /*
//   /** Composition (multiplication) of two rotation matrices.
// * m_a2b = m_a2c comp_inv m_b2c , aka  m_a2b = inv(_m_b2c) * m_a2c
// */
//    extern void int32_rmat_comp_inv(struct Int32RMat *m_a2b, struct Int32RMat *m_a2c,
//                                    struct Int32RMat *m_b2c);
//
///** rotate 3D vector by rotation matrix.
// * vb = m_a2b * va
// */
//    extern void int32_rmat_vmult(struct Int32Vect3 *vb, struct Int32RMat *m_a2b,
//                                 struct Int32Vect3 *va);
//
///** rotate 3D vector by transposed rotation matrix.
// * vb = m_b2a^T * va
// */
//    extern void int32_rmat_transp_vmult(struct Int32Vect3 *vb, struct Int32RMat *m_b2a,
//                                        struct Int32Vect3 *va);
//
///** rotate anglular rates by rotation matrix.
// * rb = m_a2b * ra
// */
//    extern void int32_rmat_ratemult(struct Int32Rates *rb, struct Int32RMat *m_a2b,
//                                    struct Int32Rates *ra);
//
///** rotate anglular rates by transposed rotation matrix.
// * rb = m_b2a^T * ra
// */
//    extern void int32_rmat_transp_ratemult(struct Int32Rates *rb, struct Int32RMat *m_b2a,
//                                           struct Int32Rates *ra);
//
///// Convert unit quaternion to rotation matrix.
//    extern void int32_rmat_of_quat(struct Int32RMat *rm, struct Int32Quat *q);

//    /** Rotation matrix from 321 Euler angles (int).
//     * The Euler angles are interpreted as zy'x'' (intrinsic) rotation.
//     * First rotate around z with psi, then around the new y' with theta,
//     * then around new x'' with phi.
//     * This is the same as a xyz (extrinsic) rotation,
//     * rotating around the fixed x, then y then z axis.
//     * - psi range: -pi < psi <= pi
//     * - theta range: -pi/2 <= theta <= pi/2
//     * - phi range: -pi < phi <= pi
//     * @param[out] rm pointer to rotation matrix
//     * @param[in]  e pointer to Euler angles
//     */
//    extern void int32_rmat_of_eulers_321(struct Int32RMat *rm, struct Int32Eulers *e);
//
///// Rotation matrix from 312 Euler angles.
//    extern void int32_rmat_of_eulers_312(struct Int32RMat *rm, struct Int32Eulers *e);


//  todo
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

}

package ub.juav.autopilot.math.functions;

import ub.juav.autopilot.math.structs.ints.IntVect2;

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
    public static double BFP_OF_REAL(double vr, int frac) {
        return ((vr) * (1 << (frac)));
    }
    public static float FLOAT_OF_BFP(float vbfp, int frac) {
        return ((float)(vbfp)/(1<<(frac)));
    }
    public static double DOUBLE_OF_BFP(float vbfp, int frac) {
        return ((double) (vbfp) / (1 << (frac)));
    }
    public static double RATE_BFP_OF_REAL(double af)  {
        return BFP_OF_REAL((af), INT32_RATE_FRAC);
    }
    public static float RATE_FLOAT_OF_BFP(int af)  {
        return FLOAT_OF_BFP((af), INT32_RATE_FRAC);
    }
    public static double ANGLE_BFP_OF_REAL(double af)  {
        return BFP_OF_REAL((af), INT32_ANGLE_FRAC);
    }
    public static double ANGLE_FLOAT_OF_BFP(int af)  {
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
        return (int) Math.sqrt((double)PprzAlgebra.VECT2_NORM(v));
    }
//  todo
//    #define QUAT1_BFP_OF_REAL(_qf)  BFP_OF_REAL((_qf), INT32_QUAT_FRAC)
//    #define QUAT1_FLOAT_OF_BFP(_qi) FLOAT_OF_BFP((_qi), INT32_QUAT_FRAC)
//    #define TRIG_BFP_OF_REAL(_tf)   BFP_OF_REAL((_tf), INT32_TRIG_FRAC)
//    #define TRIG_FLOAT_OF_BFP(_ti)  FLOAT_OF_BFP((_ti),INT32_TRIG_FRAC)
//    #define POS_BFP_OF_REAL(_af)    BFP_OF_REAL((_af), INT32_POS_FRAC)
//    #define POS_FLOAT_OF_BFP(_ai)   FLOAT_OF_BFP((_ai), INT32_POS_FRAC)
//    #define SPEED_BFP_OF_REAL(_af)  BFP_OF_REAL((_af), INT32_SPEED_FRAC)
//    #define SPEED_FLOAT_OF_BFP(_ai) FLOAT_OF_BFP((_ai), INT32_SPEED_FRAC)
//    #define ACCEL_BFP_OF_REAL(_af)  BFP_OF_REAL((_af), INT32_ACCEL_FRAC)
//    #define ACCEL_FLOAT_OF_BFP(_ai) FLOAT_OF_BFP((_ai), INT32_ACCEL_FRAC)
//    #define MAG_BFP_OF_REAL(_af)    BFP_OF_REAL((_af), INT32_MAG_FRAC)
//    #define MAG_FLOAT_OF_BFP(_ai)   FLOAT_OF_BFP((_ai), INT32_MAG_FRAC)

}

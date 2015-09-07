package ub.juav.autopilot.math.functions.algebra;

import ub.juav.autopilot.math.structs.algebra.doubles.DoubleEulers;
import ub.juav.autopilot.math.structs.algebra.doubles.DoubleQuat;
import ub.juav.autopilot.math.structs.algebra.doubles.DoubleRMat;
import ub.juav.autopilot.math.structs.algebra.doubles.DoubleVect3;
import ub.juav.autopilot.math.util.NumberMath;

/**
 * Created by adamczer on 7/12/15.
 */
public class PprzAlgebraDouble {

    /******* c file ********/
    public static void double_rmat_of_eulers_321(DoubleRMat rm, DoubleEulers e) {
        double sphi   = Math.sin(e.getPhi());
        double cphi   = Math.cos(e.getPhi());
        double stheta = Math.sin(e.getTheta());
        double ctheta = Math.cos(e.getTheta());
        double spsi   = Math.sin(e.getPsi());
        double cpsi   = Math.cos(e.getPsi());

        rm.setElement(ctheta * cpsi,0,0);
        rm.setElement(ctheta * spsi,0,1);
        rm.setElement(-stheta,0,2);
        rm.setElement(sphi * stheta * cpsi - cphi * spsi,1,0);
        rm.setElement(sphi * stheta * spsi + cphi * cpsi,1,1);
        rm.setElement(sphi * ctheta,1,2);
        rm.setElement(cphi * stheta * cpsi + sphi * spsi,2,0);
        rm.setElement(cphi * stheta * spsi - sphi * cpsi,2,1);
        rm.setElement(cphi * ctheta,2,2);
    }

    public static void double_quat_of_eulers(DoubleQuat q, DoubleEulers e) {
        double phi2   = e.getPhi() / 2.0;
        double theta2 = e.getTheta() / 2.0;
        double psi2   = e.getPsi() / 2.0;

        double s_phi2   = Math.sin(phi2);
        double c_phi2   = Math.cos(phi2);
        double s_theta2 = Math.sin(theta2);
        double c_theta2 = Math.cos(theta2);
        double s_psi2   = Math.sin(psi2);
        double c_psi2   = Math.cos(psi2);

        q.setQi(c_phi2 * c_theta2 * c_psi2 + s_phi2 * s_theta2 * s_psi2);
        q.setQx(-c_phi2 * s_theta2 * s_psi2 + s_phi2 * c_theta2 * c_psi2);
        q.setQy(c_phi2 * s_theta2 * c_psi2 + s_phi2 * c_theta2 * s_psi2);
        q.setQz(c_phi2 * c_theta2 * s_psi2 - s_phi2 * s_theta2 * c_psi2);
    }

    public static void double_eulers_of_quat(DoubleEulers e, DoubleQuat q) {
        double qx2  = q.getQx() * q.getQx();
        double qy2  = q.getQy() * q.getQy();
        double qz2  = q.getQz() * q.getQz();
        double qiqx = q.getQi() * q.getQx();
        double qiqy = q.getQi() * q.getQy();
        double qiqz = q.getQi() * q.getQz();
        double qxqy = q.getQx() * q.getQy();
        double qxqz = q.getQx() * q.getQz();
        double qyqz = q.getQy() * q.getQz();
        double dcm00 = 1.0 - 2.*(qy2 +  qz2);
        double dcm01 =       2.*(qxqy + qiqz);
        double dcm02 =       2.*(qxqz - qiqy);
        double dcm12 =       2.*(qyqz + qiqx);
        double dcm22 = 1.0 - 2.*(qx2 +  qy2);

        e.setPhi(Math.atan2(dcm12, dcm22));
        e.setTheta(-Math.asin(dcm02));
        e.setPsi(Math.atan2(dcm01, dcm00));
    }

    public static void double_quat_vmult(DoubleVect3 vOut, DoubleQuat q, DoubleVect3 vIn) {
        double qi2_M1_2  = q.getQi() * q.getQi() - 0.5;
        double qiqx = q.getQi() * q.getQx();
        double qiqy = q.getQi() * q.getQy();
        double qiqz = q.getQi() * q.getQz();
        double m01  = q.getQx() * q.getQy(); /* aka qxqy */
        double m02  = q.getQx() * q.getQz(); /* aka qxqz */
        double m12  = q.getQy() * q.getQz(); /* aka qyqz */

        double m00  = qi2_M1_2 + q.getQx() * q.getQx();
        double m10  = m01 - qiqz;
        double m20  = m02 + qiqy;
        double m21  = m12 - qiqx;
        m01 += qiqz;
        m02 -= qiqy;
        m12 += qiqx;
        double m11  = qi2_M1_2 + q.getQy() * q.getQy();
        double m22  = qi2_M1_2 + q.getQz() * q.getQz();
        vOut.setX(2 * (m00 * vIn.getX() + m01 * vIn.getY() + m02 * vIn.getZ()));
        vOut.setX(2 * (m10 * vIn.getX() + m11 * vIn.getY() + m12 * vIn.getZ()));
        vOut.setX(2 * (m20 * vIn.getX() + m21 * vIn.getY() + m22 * vIn.getZ()));
    }

    /******* h file ********/

    public static void DOUBLE_VECT3_ROUND(DoubleVect3 v) {
        DOUBLE_VECT3_RINT(v,v);
    }

    public static void DOUBLE_VECT3_RINT(DoubleVect3 vOut, DoubleVect3 vIn) {
        vOut.setX(Math.rint(vIn.getX()));
        vOut.setY(Math.rint(vIn.getY()));
        vOut.setZ(Math.rint(vIn.getZ()));
    }

    public static double double_vect3_norm(DoubleVect3 v) {
        return Math.sqrt(PprzAlgebra.VECT3_NORM2(v).doubleValue());
    }

    /** normalize 3D vector in place */
    public static void double_vect3_normalize(DoubleVect3 v) {
        double n = double_vect3_norm(v);
        if (n > 0) {
            v.setX(v.getX() / n);
            v.setY(v.getY() / n);
            v.setZ(v.getZ() / n);
        }
    }

    /** initialises a quaternion to identity */
    public static void double_quat_identity(DoubleQuat q)
    {
        q.setQi(1.0);
        q.setQx(0.0);
        q.setQy(0.0);
        q.setQz(0.0);
    }

    public static  double double_quat_norm(DoubleQuat q)
    {
        return Math.sqrt((q.getQi()*q.getQi()) + (q.getQx()*q.getQx()) + (q.getQy()*q.getQy())  + (q.getQz()*q.getQz()));
    }

    public static void double_quat_normalize(DoubleQuat q) {
        double qnorm = double_quat_norm(q);
        if (qnorm > PprzAlgebraFloat.FLT_MIN) {
            q.setQi(q.getQi() / qnorm);
            q.setQx(q.getQx() / qnorm);
            q.setQy(q.getQy() / qnorm);
            q.setQz(q.getQz() / qnorm);
        }
    }

    public static void double_rmat_of_eulers(DoubleRMat rm, DoubleEulers e) {
        double_rmat_of_eulers_321(rm, e);
    }

    /* TODO defines for backwards compatibility */
//    DOUBLE_RMAT_OF_EULERS(_rm, _e) double_rmat_of_eulers(&(_rm), &(_e))
//    DOUBLE_RMAT_OF_EULERS_321(_rm, _e) double_rmat_of_eulers(&(_rm), &(_e))
//    DOUBLE_QUAT_OF_EULERS(_q, _e) double_quat_of_eulers(&(_q), &(_e))
//    DOUBLE_EULERS_OF_QUAT(_e, _q) double_eulers_of_quat(&(_e), &(_q))
//    DOUBLE_QUAT_VMULT(v_out, q, v_in) double_quat_vmult(&(v_out), &(q), &(v_in))
}

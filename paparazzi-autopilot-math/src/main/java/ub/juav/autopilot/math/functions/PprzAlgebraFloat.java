package ub.juav.autopilot.math.functions;

import ub.juav.autopilot.math.structs.floats.*;
import ub.juav.autopilot.math.util.NumberMath;

/**
 * Created by adamczer on 7/12/15.
 */
public class PprzAlgebraFloat {
    /** in place first order integration of a 3D-vector */
    public static void float_vect3_integrate_fi(FloatVect3 vec, FloatVect3 dv, float dt)
    {
        vec.setX(vec.getX().floatValue() + dv.getX().floatValue() * dt);
        vec.setY(vec.getY().floatValue() + dv.getY().floatValue() * dt);
        vec.setZ(vec.getZ().floatValue() + dv.getZ().floatValue() * dt);
    }

    /** in place first order integration of angular rates */
    public static void float_rates_integrate_fi(FloatRates r, FloatRates dr, float dt)
    {
        r.setP(r.getP().floatValue() + dr.getP().floatValue() * dt);
        r.setQ(r.getQ().floatValue() + dr.getQ().floatValue() * dt);
        r.setR(r.getR().floatValue() + dr.getR().floatValue() * dt);
    }

    void float_rates_of_euler_dot(FloatRates r, FloatEulers e, FloatEulers edot)
    {
        r.setP(edot.getPhi().floatValue() - (float)Math.sin(e.getTheta().floatValue()) * edot.getPsi().floatValue());
        r.setQ((float)Math.cos(e.getPhi().floatValue()) * edot.getTheta().floatValue() + (float)Math.sin(e.getPhi().floatValue()) * (float)Math.cos(e.getTheta().floatValue()) * edot.getPsi().floatValue());
        r.setR(-(float)Math.sin(e.getPhi().floatValue()) * edot.getTheta().floatValue() + (float)Math.cos(e.getPhi().floatValue()) * (float)Math.cos(e.getTheta().floatValue()) * edot.getPsi().floatValue());
    }

    public static void float_rmat_inv(FloatRMat b2a, FloatRMat a2b)
    {
        b2a.setElement(a2b.getElement( 0, 0), 0, 0);
        b2a.setElement(a2b.getElement( 1, 0), 0, 1);
        b2a.setElement(a2b.getElement( 2, 0), 0, 2);
        b2a.setElement(a2b.getElement( 0, 1), 1, 0);
        b2a.setElement(a2b.getElement( 1, 1), 1, 1);
        b2a.setElement(a2b.getElement( 2, 1), 1, 2);
        b2a.setElement(a2b.getElement( 0, 2), 2, 0);
        b2a.setElement(a2b.getElement( 1, 2), 2, 1);
        b2a.setElement(a2b.getElement(2, 2), 2, 2);
    }

    public static float float_rmat_norm(FloatRMat rm)
    {
        return (float) Math.sqrt(NumberMath.sum(NumberMath.sq(rm.getElement(0, 0)), NumberMath.sq(rm.getElement(0, 1)), NumberMath.sq(rm.getElement(0, 2)),
                NumberMath.sq(rm.getElement(1, 0)), NumberMath.sq(rm.getElement(1, 1)), NumberMath.sq(rm.getElement(1, 2)),
                NumberMath.sq(rm.getElement(2, 0)), NumberMath.sq(rm.getElement(2, 1)), NumberMath.sq(rm.getElement(2, 2))).floatValue());
    }

    /** Composition (multiplication) of two rotation matrices.
     * m_a2c = m_a2b comp m_b2c , aka  m_a2c = m_b2c * m_a2b
     */
    public static void float_rmat_comp(FloatRMat m_a2c, FloatRMat m_a2b, FloatRMat m_b2c)
    {
        m_a2c.setElement(
                m_b2c.getElement(0,0).floatValue() * m_a2b.getElement(0,0).floatValue() +
                m_b2c.getElement(0,1).floatValue() * m_a2b.getElement(1,0).floatValue() +
                m_b2c.getElement(0,2).floatValue() * m_a2b.getElement(2,0).floatValue()
                ,0,0);
        m_a2c.setElement(
                m_b2c.getElement(0,0).floatValue() * m_a2b.getElement(0,1).floatValue() +
                m_b2c.getElement(0,1).floatValue() * m_a2b.getElement(1,1).floatValue() +
                m_b2c.getElement(0,2).floatValue() * m_a2b.getElement(2,1).floatValue()
                ,0,1);
        m_a2c.setElement(
                m_b2c.getElement(0,0).floatValue() * m_a2b.getElement(0,2).floatValue() +
                m_b2c.getElement(0,1).floatValue() * m_a2b.getElement(1,2).floatValue() +
                m_b2c.getElement(0,2).floatValue() * m_a2b.getElement(2,2).floatValue()
                ,0,2);
        m_a2c.setElement(
                m_b2c.getElement(1,0).floatValue() * m_a2b.getElement(0,0).floatValue() +
                m_b2c.getElement(1,1).floatValue() * m_a2b.getElement(1,1).floatValue() +
                m_b2c.getElement(1,2).floatValue() * m_a2b.getElement(2,0).floatValue()
                ,1,0);
        m_a2c.setElement(
                m_b2c.getElement(1,0).floatValue() * m_a2b.getElement(0,1).floatValue() +
                m_b2c.getElement(1,1).floatValue() * m_a2b.getElement(1,1).floatValue() +
                m_b2c.getElement(1,2).floatValue() * m_a2b.getElement(2,1).floatValue()
                ,1,1);
        m_a2c.setElement(
                m_b2c.getElement(1,0).floatValue() * m_a2b.getElement(0,2).floatValue() +
                m_b2c.getElement(1,1).floatValue() * m_a2b.getElement(1,2).floatValue() +
                m_b2c.getElement(1,2).floatValue() * m_a2b.getElement(2,2).floatValue()
                ,1,2);
        m_a2c.setElement(
                m_b2c.getElement(2,0).floatValue() * m_a2b.getElement(0,0).floatValue() +
                m_b2c.getElement(2,1).floatValue() * m_a2b.getElement(1,0).floatValue() +
                m_b2c.getElement(2,2).floatValue() * m_a2b.getElement(2,0).floatValue()
                ,2,0);
        m_a2c.setElement(
                m_b2c.getElement(2,0).floatValue() * m_a2b.getElement(0,1).floatValue() +
                m_b2c.getElement(2,1).floatValue() * m_a2b.getElement(1,1).floatValue() +
                m_b2c.getElement(2,2).floatValue() * m_a2b.getElement(2,1).floatValue()
                ,2,1);
        m_a2c.setElement(
                m_b2c.getElement(2,0).floatValue() * m_a2b.getElement(0,2).floatValue() +
                m_b2c.getElement(2,1).floatValue() * m_a2b.getElement(1,2).floatValue() +
                m_b2c.getElement(2,2).floatValue() * m_a2b.getElement(2,2).floatValue()
                ,2,2);
    }

    /** Composition (multiplication) of two rotation matrices.
     * m_a2b = m_a2c comp_inv m_b2c , aka  m_a2b = inv(_m_b2c) * m_a2c
     */
    void float_rmat_comp_inv(FloatRMat m_a2b, FloatRMat m_a2c, FloatRMat m_b2c)
    {
        m_a2b.setElement(
                m_b2c.getElement(0,0).floatValue() * m_a2c.getElement(0,0).floatValue() +
                        m_b2c.getElement(1,0).floatValue() * m_a2c.getElement(1,0).floatValue() +
                        m_b2c.getElement(2,0).floatValue() * m_a2c.getElement(2,0).floatValue()
                ,0,0);
        m_a2b.setElement(
                m_b2c.getElement(0,0).floatValue() * m_a2c.getElement(0,1).floatValue() +
                        m_b2c.getElement(1,0).floatValue() * m_a2c.getElement(1,1).floatValue() +
                        m_b2c.getElement(2,0).floatValue() * m_a2c.getElement(2,1).floatValue()
                ,0,1);
        m_a2b.setElement(
                m_b2c.getElement(0,0).floatValue() * m_a2c.getElement(0,2).floatValue() +
                        m_b2c.getElement(1,0).floatValue() * m_a2c.getElement(1,2).floatValue() +
                        m_b2c.getElement(2,0).floatValue() * m_a2c.getElement(2,2).floatValue()
                ,0,2);
        m_a2b.setElement(
                m_b2c.getElement(0,1).floatValue() * m_a2c.getElement(0,0).floatValue() +
                        m_b2c.getElement(1,1).floatValue() * m_a2c.getElement(1,0).floatValue() +
                        m_b2c.getElement(2,1).floatValue() * m_a2c.getElement(2,0).floatValue()
                ,1,0);
        m_a2b.setElement(
                m_b2c.getElement(0,1).floatValue() * m_a2c.getElement(0,1).floatValue() +
                        m_b2c.getElement(1,1).floatValue() * m_a2c.getElement(1,1).floatValue() +
                        m_b2c.getElement(2,1).floatValue() * m_a2c.getElement(2,1).floatValue()
                ,1,1);
        m_a2b.setElement(
                m_b2c.getElement(0,1).floatValue() * m_a2c.getElement(0,2).floatValue() +
                        m_b2c.getElement(1,1).floatValue() * m_a2c.getElement(1,2).floatValue() +
                        m_b2c.getElement(2,1).floatValue() * m_a2c.getElement(2,2).floatValue()
                ,1,2);
        m_a2b.setElement(
                m_b2c.getElement(0,2).floatValue() * m_a2c.getElement(0,0).floatValue() +
                        m_b2c.getElement(1,2).floatValue() * m_a2c.getElement(1,0).floatValue() +
                        m_b2c.getElement(2,2).floatValue() * m_a2c.getElement(2,0).floatValue()
                ,2,0);
        m_a2b.setElement(
                m_b2c.getElement(0,2).floatValue() * m_a2c.getElement(0,1).floatValue() +
                        m_b2c.getElement(1,2).floatValue() * m_a2c.getElement(1,1).floatValue() +
                        m_b2c.getElement(2,2).floatValue() * m_a2c.getElement(2,1).floatValue()
                ,2,1);
        m_a2b.setElement(
                m_b2c.getElement(0,2).floatValue() * m_a2c.getElement(0,2).floatValue() +
                        m_b2c.getElement(1,2).floatValue() * m_a2c.getElement(1,2).floatValue() +
                        m_b2c.getElement(2,2).floatValue() * m_a2c.getElement(2,2).floatValue()
                ,2,2);
    }

    /** rotate 3D vector by rotation matrix.
     * vb = m_a2b * va
     */
    public static void float_rmat_vmult(FloatVect3 vb, FloatRMat m_a2b, FloatVect3 va)
    {
        vb.setX(m_a2b.getElement(0,0).floatValue() * va.getX().floatValue() + m_a2b.getElement(0,1).floatValue() * va.getY().floatValue() + m_a2b.getElement(0,2).floatValue() * va.getZ().floatValue());
        vb.setY(m_a2b.getElement(1, 0).floatValue() * va.getX().floatValue() + m_a2b.getElement(1, 1).floatValue() * va.getY().floatValue() + m_a2b.getElement(1, 2).floatValue() * va.getZ().floatValue());
        vb.setZ(m_a2b.getElement(2,0).floatValue() * va.getX().floatValue() + m_a2b.getElement(2,1).floatValue() * va.getY().floatValue() + m_a2b.getElement(2,2).floatValue() * va.getZ().floatValue());
    }

    /** rotate 3D vector by transposed rotation matrix.
     * vb = m_b2a^T * va
     */
    public static void float_rmat_transp_vmult(FloatVect3 vb, FloatRMat m_b2a, FloatVect3 va)
    {
        vb.setX(m_b2a.getElement(0,0).floatValue() * va.getX().floatValue() + m_b2a.getElement(1,0).floatValue() * va.getY().floatValue() + m_b2a.getElement(2,0).floatValue() * va.getZ().floatValue());
        vb.setY(m_b2a.getElement(0,1).floatValue() * va.getX().floatValue() + m_b2a.getElement(1, 1).floatValue() * va.getY().floatValue() + m_b2a.getElement(2, 1).floatValue() * va.getZ().floatValue());
        vb.setZ(m_b2a.getElement(0,2).floatValue() * va.getX().floatValue() + m_b2a.getElement(1,2).floatValue() * va.getY().floatValue() + m_b2a.getElement(2,2).floatValue() * va.getZ().floatValue());
    }

    /** rotate anglular rates by rotation matrix.
     * rb = m_a2b * ra
     */
    public static void float_rmat_ratemult(FloatRates rb, FloatRMat m_a2b, FloatRates ra)
    {
        rb.setP(m_a2b.getElement(0, 0).floatValue() * ra.getP().floatValue() + m_a2b.getElement(0, 1).floatValue() * ra.getQ().floatValue() + m_a2b.getElement(0, 2).floatValue() * ra.getR().floatValue());
        rb.setQ(m_a2b.getElement(1, 0).floatValue() * ra.getP().floatValue() + m_a2b.getElement(1, 1).floatValue() * ra.getQ().floatValue() + m_a2b.getElement(1, 2).floatValue() * ra.getR().floatValue());
        rb.setR(m_a2b.getElement(2, 0).floatValue() * ra.getP().floatValue() + m_a2b.getElement(2, 1).floatValue() * ra.getQ().floatValue() + m_a2b.getElement(2, 2).floatValue() * ra.getR().floatValue());
    }

    /** rotate anglular rates by transposed rotation matrix.
     * rb = m_b2a^T * ra
     */
    public static void float_rmat_transp_ratemult(FloatRates rb, FloatRMat m_b2a, FloatRates ra)
    {
        rb.setP(m_b2a.getElement(0, 0).floatValue() * ra.getP().floatValue() + m_b2a.getElement(1, 0).floatValue() * ra.getQ().floatValue() + m_b2a.getElement(2, 0).floatValue() * ra.getR().floatValue());
        rb.setQ(m_b2a.getElement(0, 1).floatValue() * ra.getP().floatValue() + m_b2a.getElement(1, 1).floatValue() * ra.getQ().floatValue() + m_b2a.getElement(2, 1).floatValue() * ra.getR().floatValue());
        rb.setR(m_b2a.getElement(0, 2).floatValue() * ra.getP().floatValue() + m_b2a.getElement(1, 2).floatValue() * ra.getQ().floatValue() + m_b2a.getElement(2, 2).floatValue() * ra.getR().floatValue());
    }

    /** initialises a rotation matrix from unit vector axis and angle */
    public static void float_rmat_of_axis_angle(FloatRMat rm, FloatVect3 uv, float angle)
    {
        float ux2  = NumberMath.sq(uv.getX()).floatValue();
        float uy2  = NumberMath.sq(uv.getY()).floatValue();
        float uz2  = NumberMath.sq(uv.getZ()).floatValue();
        float uxuy = NumberMath.mul(uv.getX(),uv.getY()).floatValue();
        float uyuz = NumberMath.mul(uv.getY(),uv.getZ()).floatValue();
        float uxuz = NumberMath.mul(uv.getX(),uv.getZ()).floatValue();
        float can  = (float) Math.cos(angle);
        float san  = (float) Math.sin(angle);
        float one_m_can = (float) (1. - can);

        rm.setElement(ux2 + (1. - ux2) * can,0, 0);
        rm.setElement(uxuy * one_m_can + uv.getZ().floatValue() * san,0, 1);
        rm.setElement(uxuz * one_m_can - uv.getY().floatValue() * san,0, 2);
        rm.setElement(rm.getElement(0,1),1, 0);
        rm.setElement(uy2 + (1. - uy2) * can,1, 1);
        rm.setElement(uyuz * one_m_can + uv.getX().floatValue() * san,1, 2);
        rm.setElement(rm.getElement(0,2),2, 0);
        rm.setElement(rm.getElement(1,2),2, 1);
        rm.setElement(uz2 + (1. - uz2) * can,2, 2);
    }

    /* C n->b rotation matrix */
    public static void float_rmat_of_eulers_321(FloatRMat rm, FloatEulers e)
    {
        float sphi   = (float) Math.sin(e.getPhi().floatValue());
        float cphi   = (float) Math.cos(e.getPhi().floatValue());
        float stheta = (float) Math.sin(e.getTheta().floatValue());
        float ctheta = (float) Math.cos(e.getTheta().floatValue());
        float spsi   = (float) Math.sin(e.getPsi().floatValue());
        float cpsi   = (float) Math.cos(e.getPsi().floatValue());

        rm.setElement(ctheta * cpsi, 0, 0);
        rm.setElement(ctheta * spsi, 0, 1);
        rm.setElement(-stheta, 0, 2);
        rm.setElement(sphi * stheta * cpsi - cphi * spsi, 1, 0);
        rm.setElement(sphi * stheta * spsi + cphi * cpsi, 1, 1);
        rm.setElement(sphi * ctheta, 1, 2);
        rm.setElement(cphi * stheta * cpsi + sphi * spsi, 2, 0);
        rm.setElement(cphi * stheta * spsi - sphi * cpsi, 2, 1);
        rm.setElement(cphi * ctheta, 2, 2);
    }

    public static void float_rmat_of_eulers_312(FloatRMat rm, FloatEulers e)
    {
        float sphi   = (float) Math.sin(e.getPhi().floatValue());
        float cphi   = (float) Math.cos(e.getPhi().floatValue());
        float stheta = (float) Math.sin(e.getTheta().floatValue());
        float ctheta = (float) Math.cos(e.getTheta().floatValue());
        float spsi   = (float) Math.sin(e.getPhi().floatValue());
        float cpsi   = (float) Math.cos(e.getPsi().floatValue());

        rm.setElement(ctheta * cpsi - sphi * stheta * spsi, 0, 0);
        rm.setElement(ctheta * spsi + sphi * stheta * cpsi, 0, 1);
        rm.setElement(-cphi * stheta, 0, 2);
        rm.setElement(-cphi * spsi, 1, 0);
        rm.setElement(cphi * cpsi, 1, 1);
        rm.setElement(sphi, 1, 2);
        rm.setElement(stheta * cpsi + sphi * ctheta * spsi, 2, 0);
        rm.setElement(stheta * spsi - sphi * ctheta * cpsi, 2, 1);
        rm.setElement(cphi * ctheta, 2, 2);
    }

    /* C n->b rotation matrix */
    public static void float_rmat_of_quat(FloatRMat rm, FloatQuat q)
    {
        float _a = (float) (M_SQRT2 * q.getQi().floatValue());
        float _b = (float) (M_SQRT2 * q.getQx().floatValue());
        float _c = (float) (M_SQRT2 * q.getQy().floatValue());
        float _d = (float) (M_SQRT2 * q.getQz().floatValue());
        float a2_1 = _a * _a - 1;
        float ab = _a * _b;
        float ac = _a * _c;
        float ad = _a * _d;
        float bc = _b * _c;
        float bd = _b * _d;
        float cd = _c * _d;
        rm.setElement(a2_1 + _b * _b, 0, 0);
        rm.setElement(bc + ad, 0, 1);
        rm.setElement(bd - ac, 0, 2);
        rm.setElement(bc - ad, 1, 0);
        rm.setElement(a2_1 + _c * _c, 1, 1);
        rm.setElement(cd + ab, 1, 2);
        rm.setElement(bd + ac, 2, 0);
        rm.setElement(cd - ab, 2, 1);
        rm.setElement(a2_1 + _d * _d, 2, 2);
    }

    /** in place first order integration of a rotation matrix */
    public static void float_rmat_integrate_fi(FloatRMat rm, FloatRates omega, float dt)
    {
        rm.setMatrix(new Float[][]{
                {(float)1.0,dt * omega.getR().floatValue(), -dt * omega.getQ().floatValue()},
                {-dt * omega.getR().floatValue(),(float)1.0,dt*omega.getP().floatValue()},
                {dt * omega.getQ().floatValue(), -dt * omega.getP().floatValue(), (float)1.0},
        });
    }

    public static float renorm_factor(float n)
    {
        if (n < 1.5625f && n > 0.64f) {
            return (float) (.5 * (3 - n));
        } else if (n < 100.0f && n > 0.01f) {
            return (float) (1. / Math.sqrt(n));
        } else {
            return (float) 0.;
        }
    }

    public static float float_rmat_reorthogonalize(FloatRMat rm)
    {
        FloatVect3 r0 = new FloatVect3();
        r0.setX(rm.getElement(0,0).floatValue());
        r0.setY(rm.getElement(0,1).floatValue());
        r0.setZ(rm.getElement(0,2).floatValue());

        FloatVect3 r1 = new FloatVect3();
        r1.setX(rm.getElement(1,0).floatValue());
        r1.setY(rm.getElement(1,1).floatValue());
        r1.setZ(rm.getElement(1,2).floatValue());

        float _err = (float) (-0.5 * PprzAlgebra.VECT3_DOT_PRODUCT(r0, r1).floatValue());
        FloatVect3 r0_t = new FloatVect3();
        PprzAlgebra.VECT3_SUM_SCALED(r0_t, r0, r1, _err);
        FloatVect3 r1_t = new FloatVect3();
        PprzAlgebra.VECT3_SUM_SCALED(r1_t, r1, r0, _err);
        FloatVect3 r2_t = new FloatVect3();
        PprzAlgebra.VECT3_CROSS_PRODUCT(r2_t, r0_t, r1_t);
        float s = renorm_factor((float) PprzAlgebra.VECT3_NORM2(r0_t));
        PprzAlgebra.MAT33_ROW_VECT3_SMUL(rm, 0, r0_t, s);
        s = renorm_factor((float) PprzAlgebra.VECT3_NORM2(r1_t));
        PprzAlgebra.MAT33_ROW_VECT3_SMUL(rm, 1, r1_t, s);
        s = renorm_factor((float) PprzAlgebra.VECT3_NORM2(r2_t));
        PprzAlgebra.MAT33_ROW_VECT3_SMUL(rm, 2, r2_t, s);

        return _err;
    }

    public static final double M_SQRT2 = 1.41421356237309504880;

    /********* Quaternion functions **********/

    public static void float_quat_comp(FloatQuat a2c, FloatQuat a2b, FloatQuat b2c)
    {
        a2c->qi = NumberMath.mul(a2b.getQi() , b2c.getQi()) - NumberMath.mul(a2b.getQx() , b2c.getQx()) - a2b->qy * b2c->qy - a2b->qz * b2c->qz;
        a2c->qx = a2b->qi * b2c->qx + a2b->qx * b2c->qi + a2b->qy * b2c->qz - a2b->qz * b2c->qy;
        a2c->qy = a2b->qi * b2c->qy - a2b->qx * b2c->qz + a2b->qy * b2c->qi + a2b->qz * b2c->qx;
        a2c->qz = a2b->qi * b2c->qz + a2b->qx * b2c->qy - a2b->qy * b2c->qx + a2b->qz * b2c->qi;
    }

}

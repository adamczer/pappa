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
import ub.juav.airborne.math.wrappers.PrimitiveWrapper;
import ub.juav.airborne.math.util.NumberMath;

/**
 * Created by adamczer on 7/12/15.
 */
public class PprzAlgebraFloat {
    /******* c file *******/
    /** in place first order integration of a 3D-vector */
    public static void float_vect3_integrate_fi(Vect3<Float> vec, Vect3<Float> dv, float dt)
    {
        vec.setX(vec.getX() + dv.getX() * dt);
        vec.setY(vec.getY() + dv.getY() * dt);
        vec.setZ(vec.getZ() + dv.getZ() * dt);
    }

    /** in place first order integration of angular rates */
    public static void float_rates_integrate_fi(Rates<Float> r, Rates<Float> dr, float dt)
    {
        r.setP(r.getP() + dr.getP() * dt);
        r.setQ(r.getQ() + dr.getQ() * dt);
        r.setR(r.getR() + dr.getR() * dt);
    }

    public static void float_rates_of_euler_dot(Rates<Float> r, Eulers<Float> e, Eulers<Float> edot)
    {
        r.setP(edot.getPhi() - (float)Math.sin(e.getTheta()) * edot.getPsi());
        r.setQ((float)Math.cos(e.getPhi()) * edot.getTheta() + (float)Math.sin(e.getPhi()) * (float)Math.cos(e.getTheta()) * edot.getPsi());
        r.setR(-(float)Math.sin(e.getPhi()) * edot.getTheta() + (float)Math.cos(e.getPhi()) * (float)Math.cos(e.getTheta()) * edot.getPsi());
    }

    public static void float_rmat_inv(RMat<Float> b2a, RMat<Float> a2b)
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

    public static float float_rmat_norm(RMat<Float> rm)
    {
        return (float) Math.sqrt(NumberMath.sum(NumberMath.sq(rm.getElement(0, 0)), NumberMath.sq(rm.getElement(0, 1)), NumberMath.sq(rm.getElement(0, 2)),
                NumberMath.sq(rm.getElement(1, 0)), NumberMath.sq(rm.getElement(1, 1)), NumberMath.sq(rm.getElement(1, 2)),
                NumberMath.sq(rm.getElement(2, 0)), NumberMath.sq(rm.getElement(2, 1)), NumberMath.sq(rm.getElement(2, 2))).floatValue());
    }

    /** Composition (multiplication) of two rotation matrices.
     * m_a2c = m_a2b comp m_b2c , aka  m_a2c = m_b2c * m_a2b
     */
    public static void float_rmat_comp(RMat<Float> m_a2c, RMat<Float> m_a2b, RMat<Float> m_b2c)
    {
        m_a2c.setElement(
                m_b2c.getElement(0,0) * m_a2b.getElement(0,0) +
                m_b2c.getElement(0,1) * m_a2b.getElement(1,0) +
                m_b2c.getElement(0,2) * m_a2b.getElement(2,0)
                ,0,0);
        m_a2c.setElement(
                m_b2c.getElement(0,0) * m_a2b.getElement(0,1) +
                m_b2c.getElement(0,1) * m_a2b.getElement(1,1) +
                m_b2c.getElement(0,2) * m_a2b.getElement(2,1)
                ,0,1);
        m_a2c.setElement(
                m_b2c.getElement(0,0) * m_a2b.getElement(0,2) +
                m_b2c.getElement(0,1) * m_a2b.getElement(1,2) +
                m_b2c.getElement(0,2) * m_a2b.getElement(2,2)
                ,0,2);
        m_a2c.setElement(
                m_b2c.getElement(1,0) * m_a2b.getElement(0,0) +
                m_b2c.getElement(1,1) * m_a2b.getElement(1,1) +
                m_b2c.getElement(1,2) * m_a2b.getElement(2,0)
                ,1,0);
        m_a2c.setElement(
                m_b2c.getElement(1,0) * m_a2b.getElement(0,1) +
                m_b2c.getElement(1,1) * m_a2b.getElement(1,1) +
                m_b2c.getElement(1,2) * m_a2b.getElement(2,1)
                ,1,1);
        m_a2c.setElement(
                m_b2c.getElement(1,0) * m_a2b.getElement(0,2) +
                m_b2c.getElement(1,1) * m_a2b.getElement(1,2) +
                m_b2c.getElement(1,2) * m_a2b.getElement(2,2)
                ,1,2);
        m_a2c.setElement(
                m_b2c.getElement(2,0) * m_a2b.getElement(0,0) +
                m_b2c.getElement(2,1) * m_a2b.getElement(1,0) +
                m_b2c.getElement(2,2) * m_a2b.getElement(2,0)
                ,2,0);
        m_a2c.setElement(
                m_b2c.getElement(2,0) * m_a2b.getElement(0,1) +
                m_b2c.getElement(2,1) * m_a2b.getElement(1,1) +
                m_b2c.getElement(2,2) * m_a2b.getElement(2,1)
                ,2,1);
        m_a2c.setElement(
                m_b2c.getElement(2,0) * m_a2b.getElement(0,2) +
                m_b2c.getElement(2,1) * m_a2b.getElement(1,2) +
                m_b2c.getElement(2,2) * m_a2b.getElement(2,2)
                ,2,2);
    }

    /** Composition (multiplication) of two rotation matrices.
     * m_a2b = m_a2c comp_inv m_b2c , aka  m_a2b = inv(_m_b2c) * m_a2c
     */
    public static void float_rmat_comp_inv(RMat<Float> m_a2b, RMat<Float> m_a2c, RMat<Float> m_b2c)
    {
        m_a2b.setElement(
                m_b2c.getElement(0,0) * m_a2c.getElement(0,0) +
                        m_b2c.getElement(1,0) * m_a2c.getElement(1,0) +
                        m_b2c.getElement(2,0) * m_a2c.getElement(2,0)
                ,0,0);
        m_a2b.setElement(
                m_b2c.getElement(0,0) * m_a2c.getElement(0,1) +
                        m_b2c.getElement(1,0) * m_a2c.getElement(1,1) +
                        m_b2c.getElement(2,0) * m_a2c.getElement(2,1)
                ,0,1);
        m_a2b.setElement(
                m_b2c.getElement(0,0) * m_a2c.getElement(0,2) +
                        m_b2c.getElement(1,0) * m_a2c.getElement(1,2) +
                        m_b2c.getElement(2,0) * m_a2c.getElement(2,2)
                ,0,2);
        m_a2b.setElement(
                m_b2c.getElement(0,1) * m_a2c.getElement(0,0) +
                        m_b2c.getElement(1,1) * m_a2c.getElement(1,0) +
                        m_b2c.getElement(2,1) * m_a2c.getElement(2,0)
                ,1,0);
        m_a2b.setElement(
                m_b2c.getElement(0,1) * m_a2c.getElement(0,1) +
                        m_b2c.getElement(1,1) * m_a2c.getElement(1,1) +
                        m_b2c.getElement(2,1) * m_a2c.getElement(2,1)
                ,1,1);
        m_a2b.setElement(
                m_b2c.getElement(0,1) * m_a2c.getElement(0,2) +
                        m_b2c.getElement(1,1) * m_a2c.getElement(1,2) +
                        m_b2c.getElement(2,1) * m_a2c.getElement(2,2)
                ,1,2);
        m_a2b.setElement(
                m_b2c.getElement(0,2) * m_a2c.getElement(0,0) +
                        m_b2c.getElement(1,2) * m_a2c.getElement(1,0) +
                        m_b2c.getElement(2,2) * m_a2c.getElement(2,0)
                ,2,0);
        m_a2b.setElement(
                m_b2c.getElement(0,2) * m_a2c.getElement(0,1) +
                        m_b2c.getElement(1,2) * m_a2c.getElement(1,1) +
                        m_b2c.getElement(2,2) * m_a2c.getElement(2,1)
                ,2,1);
        m_a2b.setElement(
                m_b2c.getElement(0,2) * m_a2c.getElement(0,2) +
                        m_b2c.getElement(1,2) * m_a2c.getElement(1,2) +
                        m_b2c.getElement(2,2) * m_a2c.getElement(2,2)
                ,2,2);
    }

    /** rotate 3D vector by rotation matrix.
     * vb = m_a2b * va
     */
    public static void float_rmat_vmult(Vect3<Float> vb, RMat<Float> m_a2b, Vect3<Float> va)
    {
        vb.setX(m_a2b.getElement(0,0) * va.getX() + m_a2b.getElement(0,1) * va.getY() + m_a2b.getElement(0,2) * va.getZ());
        vb.setY(m_a2b.getElement(1, 0) * va.getX() + m_a2b.getElement(1, 1) * va.getY() + m_a2b.getElement(1, 2) * va.getZ());
        vb.setZ(m_a2b.getElement(2,0) * va.getX() + m_a2b.getElement(2,1) * va.getY() + m_a2b.getElement(2,2) * va.getZ());
    }

    /** rotate 3D vector by transposed rotation matrix.
     * vb = m_b2a^T * va
     */
    public static void float_rmat_transp_vmult(Vect3<Float> vb, RMat<Float> m_b2a, Vect3<Float> va)
    {
        vb.setX(m_b2a.getElement(0,0) * va.getX() + m_b2a.getElement(1,0) * va.getY() + m_b2a.getElement(2,0) * va.getZ());
        vb.setY(m_b2a.getElement(0,1) * va.getX() + m_b2a.getElement(1, 1) * va.getY() + m_b2a.getElement(2, 1) * va.getZ());
        vb.setZ(m_b2a.getElement(0,2) * va.getX() + m_b2a.getElement(1,2) * va.getY() + m_b2a.getElement(2,2) * va.getZ());
    }

    /** rotate anglular rates by rotation matrix.
     * rb = m_a2b * ra
     */
    public static void float_rmat_ratemult(Rates<Float> rb, RMat<Float> m_a2b, Rates<Float> ra)
    {
        rb.setP(m_a2b.getElement(0, 0) * ra.getP() + m_a2b.getElement(0, 1) * ra.getQ() + m_a2b.getElement(0, 2) * ra.getR());
        rb.setQ(m_a2b.getElement(1, 0) * ra.getP() + m_a2b.getElement(1, 1) * ra.getQ() + m_a2b.getElement(1, 2) * ra.getR());
        rb.setR(m_a2b.getElement(2, 0) * ra.getP() + m_a2b.getElement(2, 1) * ra.getQ() + m_a2b.getElement(2, 2) * ra.getR());
    }

    /** rotate anglular rates by transposed rotation matrix.
     * rb = m_b2a^T * ra
     */
    public static void float_rmat_transp_ratemult(Rates<Float> rb, RMat<Float> m_b2a, Rates<Float> ra)
    {
        rb.setP(m_b2a.getElement(0, 0) * ra.getP() + m_b2a.getElement(1, 0) * ra.getQ() + m_b2a.getElement(2, 0) * ra.getR());
        rb.setQ(m_b2a.getElement(0, 1) * ra.getP() + m_b2a.getElement(1, 1) * ra.getQ() + m_b2a.getElement(2, 1) * ra.getR());
        rb.setR(m_b2a.getElement(0, 2) * ra.getP() + m_b2a.getElement(1, 2) * ra.getQ() + m_b2a.getElement(2, 2) * ra.getR());
    }

    /** initialises a rotation matrix from unit vector axis and angle */
    public static void float_rmat_of_axis_angle(RMat<Float> rm, Vect3<Float> uv, float angle)
    {
        float ux2  = (uv.getX()*uv.getX());
        float uy2  = (uv.getY()*uv.getY());
        float uz2  = (uv.getZ()*uv.getZ());
        float uxuy = (uv.getX()*uv.getY());
        float uyuz = (uv.getY()*uv.getZ());
        float uxuz = (uv.getX()*uv.getZ());
        float can  = (float) Math.cos(angle);
        float san  = (float) Math.sin(angle);
        float one_m_can = (float) (1. - can);

        rm.setElement((float) (ux2 + (1. - ux2) * can),0, 0);
        rm.setElement(uxuy * one_m_can + uv.getZ() * san,0, 1);
        rm.setElement(uxuz * one_m_can - uv.getY() * san,0, 2);
        rm.setElement(rm.getElement(0,1),1, 0);
        rm.setElement((float) (uy2 + (1. - uy2) * can),1, 1);
        rm.setElement(uyuz * one_m_can + uv.getX() * san,1, 2);
        rm.setElement(rm.getElement(0,2),2, 0);
        rm.setElement(rm.getElement(1,2),2, 1);
        rm.setElement((float) (uz2 + (1. - uz2) * can),2, 2);
    }

    public static void float_rmat_of_eulers(RMat<Float> rm, Eulers<Float> e) {
        float_rmat_of_eulers_321(rm,e);
    }

    /* C n->b rotation matrix */
    public static void float_rmat_of_eulers_321(RMat<Float> rm, Eulers<Float> e)
    {
        float sphi   = (float) Math.sin(e.getPhi());
        float cphi   = (float) Math.cos(e.getPhi());
        float stheta = (float) Math.sin(e.getTheta());
        float ctheta = (float) Math.cos(e.getTheta());
        float spsi   = (float) Math.sin(e.getPsi());
        float cpsi   = (float) Math.cos(e.getPsi());

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

    public static void float_rmat_of_eulers_312(RMat<Float> rm, Eulers<Float> e)
    {
        float sphi   = (float) Math.sin(e.getPhi());
        float cphi   = (float) Math.cos(e.getPhi());
        float stheta = (float) Math.sin(e.getTheta());
        float ctheta = (float) Math.cos(e.getTheta());
        float spsi   = (float) Math.sin(e.getPhi());
        float cpsi   = (float) Math.cos(e.getPsi());

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
    public static void float_rmat_of_quat(RMat<Float> rm, Quat<Float> q)
    {
        float _a = (float) (M_SQRT2 * q.getQi());
        float _b = (float) (M_SQRT2 * q.getQx());
        float _c = (float) (M_SQRT2 * q.getQy());
        float _d = (float) (M_SQRT2 * q.getQz());
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
    public static void float_rmat_integrate_fi(RMat<Float> rm, Rates<Float> omega, float dt)
    {
        rm.setMatrix(new Float[][]{
                {(float)1.0,dt * omega.getR(), -dt * omega.getQ()},
                {-dt * omega.getR(),(float)1.0,dt*omega.getP()},
                {dt * omega.getQ(), -dt * omega.getP(), (float)1.0},
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

    public static float float_rmat_reorthogonalize(RMat<Float> rm)
    {
        Vect3<Float> r0 = new Vect3<Float>();
        r0.setX(rm.getElement(0,0));
        r0.setY(rm.getElement(0,1));
        r0.setZ(rm.getElement(0,2));

        Vect3<Float> r1 = new Vect3<Float>();
        r1.setX(rm.getElement(1,0));
        r1.setY(rm.getElement(1,1));
        r1.setZ(rm.getElement(1,2));

        float _err = (float) (-0.5 * PprzAlgebra.VECT3_DOT_PRODUCT(r0, r1).floatValue());
        Vect3<Float> r0_t = new Vect3<Float>();
        PprzAlgebra.VECT3_SUM_SCALED(r0_t, r0, r1, _err);
        Vect3<Float> r1_t = new Vect3<Float>();
        PprzAlgebra.VECT3_SUM_SCALED(r1_t, r1, r0, _err);
        Vect3<Float> r2_t = new Vect3<Float>();
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

    public static void float_quat_comp(Quat<Float> a2c, Quat<Float> a2b, Quat<Float> b2c)
    {
        a2c.setQi((a2b.getQi() * b2c.getQi()) - (a2b.getQx() * b2c.getQx()) - (a2b.getQy() * b2c.getQy()) - (a2b.getQz() * b2c.getQz()));
        a2c.setQx((a2b.getQi() * b2c.getQx()) + (a2b.getQx() * b2c.getQi()) + (a2b.getQy() * b2c.getQz()) - (a2b.getQz() * b2c.getQy()));
        a2c.setQy((a2b.getQi() * b2c.getQy()) - (a2b.getQx() * b2c.getQz()) + (a2b.getQy() * b2c.getQi()) + (a2b.getQz() * b2c.getQx()));
        a2c.setQz((a2b.getQi() * b2c.getQz()) + (a2b.getQx() * b2c.getQy()) - (a2b.getQy() * b2c.getQx()) + (a2b.getQz() * b2c.getQi()));
    }

    public static void float_quat_comp_inv(Quat<Float> a2b, Quat<Float> a2c, Quat<Float> b2c)
    {
        a2b.setQi((a2c.getQi() * b2c.getQi()) + (a2c.getQx() * b2c.getQx()) + (a2c.getQy() * b2c.getQy()) + (a2c.getQz() * b2c.getQz()));
        a2b.setQx((a2c.getQi() * b2c.getQx()) + (a2c.getQx() * b2c.getQi()) - (a2c.getQy() * b2c.getQz()) + (a2c.getQz() * b2c.getQy()));
        a2b.setQy((a2c.getQi() * b2c.getQy()) + (a2c.getQx() * b2c.getQz()) + (a2c.getQy() * b2c.getQi()) - (a2c.getQz() * b2c.getQx()));
        a2b.setQz((a2c.getQi() * b2c.getQz()) - (a2c.getQx() * b2c.getQy()) + (a2c.getQy() * b2c.getQx()) + (a2c.getQz() * b2c.getQi()));
    }

    public static void float_quat_inv_comp(Quat<Float> b2c, Quat<Float> a2b, Quat<Float> a2c)
    {
        b2c.setQi((a2b.getQi() * a2c.getQi()) + (a2b.getQx() * a2c.getQx()) + (a2b.getQy() * a2c.getQy()) + (a2b.getQz() * a2c.getQz()));
        b2c.setQx((a2b.getQi() * a2c.getQx()) - (a2b.getQx() * a2c.getQi()) - (a2b.getQy() * a2c.getQz()) + (a2b.getQz() * a2c.getQy()));
        b2c.setQy((a2b.getQi() * a2c.getQy()) + (a2b.getQx() * a2c.getQz()) - (a2b.getQy() * a2c.getQi()) - (a2b.getQz() * a2c.getQx()));
        b2c.setQz((a2b.getQi() * a2c.getQz()) - (a2b.getQx() * a2c.getQy()) + (a2b.getQy() * a2c.getQx()) - (a2b.getQz() * a2c.getQi()));
    }

    public static void float_quat_comp_norm_shortest(Quat<Float> a2c, Quat<Float> a2b, Quat<Float> b2c)
    {
        float_quat_comp(a2c, a2b, b2c);
        float_quat_wrap_shortest(a2c);
        float_quat_normalize(a2c);
    }

    public static void float_quat_comp_inv_norm_shortest(Quat<Float> a2b, Quat<Float> a2c, Quat<Float> b2c)
    {
        float_quat_comp_inv(a2b, a2c, b2c);
        float_quat_wrap_shortest(a2b);
        float_quat_normalize(a2b);
    }

    public static void float_quat_inv_comp_norm_shortest(Quat<Float> b2c, Quat<Float> a2b, Quat<Float> a2c)
    {
        float_quat_inv_comp(b2c, a2b, a2c);
        float_quat_wrap_shortest(b2c);
        float_quat_normalize(b2c);
    }

    public static void float_quat_differential(Quat<Float> q_out, Rates<Float> w, float dt)
    {
        float v_norm = NumberMath.sqrt(PprzAlgebra.RATES_NORM2(w)).floatValue();
        float c2 = (float) Math.cos(dt * v_norm / 2.0);
        float s2 = (float) Math.sin(dt * v_norm / 2.0);
        if (v_norm < 1e-8) {
            q_out.setQi((float) 1);
            q_out.setQx((float) 0);
            q_out.setQy((float) 0);
            q_out.setQz((float) 0);
        } else {
            q_out.setQi(c2);
            q_out.setQx(w.getP() / v_norm * s2);
            q_out.setQy(w.getQ() / v_norm * s2);
            q_out.setQz(w.getR() / v_norm * s2);
        }
    }

    /** in place first order quaternion integration with constant rotational velocity */
    public static void float_quat_integrate_fi(Quat<Float> q, Rates<Float> omega, float dt)
    {
        float qi = q.getQi();
        float qx = q.getQx();
        float qy = q.getQy();
        float qz = q.getQz();
        float dp = (float) (0.5 * dt * omega.getP());
        float dq = (float) (0.5 * dt * omega.getQ());
        float dr = (float) (0.5 * dt * omega.getR());
        q.setQi(qi    - dp * qx - dq * qy - dr * qz);
        q.setQx(dp * qi +    qx + dr * qy - dq * qz);
        q.setQy(dq * qi - dr * qx +    qy + dp * qz);
        q.setQz(dr * qi + dq * qx - dp * qy +    qz);
    }

    /** in place quaternion integration with constant rotational velocity */
    public static void float_quat_integrate(Quat<Float> q, Rates<Float> omega, float dt)
    {
        float no = NumberMath.sqrt(PprzAlgebra.RATES_NORM2(omega)).floatValue();
        if (no > FLT_MIN) {
            float a  = (float) (0.5 * no * dt);
            float ca = (float) Math.cos(a);
            float sa_ov_no = (float) ((float)Math.sin(a) / no);
            float dp = sa_ov_no * omega.getP();
            float dq = sa_ov_no * omega.getQ();
            float dr = sa_ov_no * omega.getR();
            float qi = q.getQi();
            float qx = q.getQx();
            float qy = q.getQy();
            float qz = q.getQz();
            q.setQi(ca * qi - dp * qx - dq * qy - dr * qz);
            q.setQx(dp * qi + ca * qx + dr * qy - dq * qz);
            q.setQy(dq * qi - dr * qx + ca * qy + dp * qz);
            q.setQz(dr * qi + dq * qx - dp * qy + ca * qz);
        }
    }

    public static void float_quat_vmult(Vect3<Float> v_out, Quat<Float> q, Vect3<Float> v_in)
    {
        float qi2_M1_2  = (float) (q.getQi() * q.getQi() - 0.5);
        float qiqx = q.getQi() * q.getQx();
        float qiqy = q.getQi() * q.getQy();
        float qiqz = q.getQi() * q.getQz();
        float m01  = q.getQx() * q.getQy(); /* aka qxqy */
        float m02  = q.getQx() * q.getQz(); /* aka qxqz */
        float m12  = q.getQy() * q.getQz(); /* aka qyqz */

        float m00  = qi2_M1_2 + q.getQx() * q.getQx();
        float m10  = m01 - qiqz;
        float m20  = m02 + qiqy;
        float m21  = m12 - qiqx;
        m01 += qiqz;
        m02 -= qiqy;
        m12 += qiqx;
        float m11  = qi2_M1_2 + q.getQy() * q.getQy();
        float m22  = qi2_M1_2 + q.getQz() * q.getQz();
        v_out.setX(2 * (m00 * v_in.getX() + m01 * v_in.getY() + m02 * v_in.getZ()));
        v_out.setY(2 * (m10 * v_in.getX() + m11 * v_in.getY() + m12 * v_in.getZ()));
        v_out.setZ(2 * (m20 * v_in.getX() + m21 * v_in.getY() + m22 * v_in.getZ()));
    }

    /** Quaternion derivative from rotational velocity.
     * qd = -0.5*omega(r) * q
     * or equally:
     * qd = 0.5 * q * omega(r)
     */
    public static void float_quat_derivative(Quat<Float> qd, Rates<Float> r, Quat<Float> q)
    {
        qd.setQi((float) (-0.5 * (r.getP() * q.getQx() + r.getQ() * q.getQy() + r.getR() * q.getQz())));
        qd.setQx((float) (-0.5 * (-r.getP() * q.getQi() - r.getR() * q.getQy() + r.getQ() * q.getQz())));
        qd.setQy((float) (-0.5 * (-r.getQ() * q.getQi() + r.getR() * q.getQx() - r.getP() * q.getQz())));
        qd.setQz((float) (-0.5 * (-r.getR() * q.getQi() - r.getQ() * q.getQx() + r.getP() * q.getQy())));
    }

    /** Quaternion derivative from rotational velocity.
     * qd = -0.5*omega(r) * q
     */
    public static void float_quat_derivative_lagrange(Quat<Float> qd, Rates<Float> r, Quat<Float> q)
    {
        float K_LAGRANGE = (float) 1.;
        float c = (float) (K_LAGRANGE * (1 - float_quat_norm(q)) / -0.5);
        qd.setQi((float) (-0.5 * (c * q.getQi() + r.getP() * q.getQx() + r.getQ() * q.getQy() + r.getR() * q.getQz())));
        qd.setQx((float) (-0.5 * (-r.getP() * q.getQi() +      c * q.getQx() - r.getR() * q.getQy() + r.getQ() * q.getQz())));
        qd.setQy((float) (-0.5 * (-r.getQ() * q.getQi() + r.getR() * q.getQx() +      c * q.getQy() - r.getP() * q.getQz())));
        qd.setQz((float) (-0.5 * (-r.getR() * q.getQi() - r.getQ() * q.getQx() + r.getP() * q.getQy() +      c * q.getQz())));
    }

    public static void float_quat_of_eulers(Quat<Float> q, Eulers<Float> e)
    {
        float phi2   = e.getPhi() / (float)2.0;
        float theta2 = e.getTheta() / (float)2.0;
        float psi2   = e.getPsi() / (float)2.0;

        float s_phi2   = (float)Math.sin(phi2);
        float c_phi2   = (float)Math.cos(phi2);
        float s_theta2 = (float)Math.sin(theta2);
        float c_theta2 = (float)Math.cos(theta2);
        float s_psi2   = (float)Math.sin(psi2);
        float c_psi2   = (float)Math.cos(psi2);

        q.setQi(c_phi2 * c_theta2 * c_psi2 + s_phi2 * s_theta2 * s_psi2);
        q.setQx(-c_phi2 * s_theta2 * s_psi2 + s_phi2 * c_theta2 * c_psi2);
        q.setQy(c_phi2 * s_theta2 * c_psi2 + s_phi2 * c_theta2 * s_psi2);
        q.setQz(c_phi2 * c_theta2 * s_psi2 - s_phi2 * s_theta2 * c_psi2);
    }

    public static void float_quat_of_axis_angle(Quat<Float> q, Vect3<Float> uv, float angle)
    {
        float san = (float) Math.sin(angle / 2.);
        q.setQi((float) Math.cos(angle/2.));
        q.setQx(san * uv.getX());
        q.setQy(san * uv.getY());
        q.setQz(san * uv.getZ());
    }

    public static void float_quat_of_orientation_vect(Quat<Float> q, Vect3<Float> ov)
    {
        float ov_norm = PprzAlgebra.VECT3_NORM(ov).floatValue();
        if (ov_norm < 1e-8) {
            q.setQi((float) 1);
            q.setQx((float) 0);
            q.setQy((float) 0);
            q.setQz((float) 0);
        } else {
            float s2_normalized = (float)Math.sin(ov_norm / 2.0) / ov_norm;
            q.setQi((float) Math.cos(ov_norm / 2.0));
            q.setQx(ov.getX() * s2_normalized);
            q.setQy(ov.getY() * s2_normalized);
            q.setQz(ov.getZ() * s2_normalized);
        }
    }

    public static void float_quat_of_rmat(Quat<Float> q, RMat<Float> rm)
    {
        float tr = PprzAlgebra.RMAT_TRACE(rm).floatValue();
        if (tr > 0) {
            float two_qi = (float) Math.sqrt(1. + tr);
            float four_qi = (float)2. * two_qi;
            q.setQi((float) (0.5 * two_qi));
            q.setQx((rm.getElement(1, 2) - rm.getElement(2, 1)) / four_qi);
            q.setQy((rm.getElement(2, 0) - rm.getElement(0, 2)) / four_qi);
            q.setQz((rm.getElement(0, 1) - rm.getElement(1, 0)) / four_qi);
    /*printf("tr > 0\n");*/
        } else {
            if (rm.getElement(0, 0) > rm.getElement(1, 1) &&
            rm.getElement(0, 0) > rm.getElement(2, 2)) {
                float two_qx = (float) Math.sqrt(rm.getElement(0, 0) - rm.getElement(1, 1)
                        - rm.getElement(2, 2) + 1);
                float four_qx = (float) 2. * two_qx;
                q.setQi((rm.getElement(1, 2) - rm.getElement(2, 1)) / four_qx);
                q.setQx((float)0.5 * two_qx);
                q.setQy((rm.getElement(0, 1) + rm.getElement(1, 0)) / four_qx);
                q.setQz((rm.getElement(2, 0) + rm.getElement(0, 2)) / four_qx);
      /*printf("m00 largest\n");*/
            } else if (rm.getElement(1, 1) > rm.getElement(2, 2)) {
                float two_qy =
                        (float) Math.sqrt(rm.getElement(1, 1) - rm.getElement(0, 0) - rm.getElement(2, 2) + 1);
                float four_qy = (float)2. * two_qy;
                q.setQi((rm.getElement(2, 0) - rm.getElement(0, 2)) / four_qy);
                q.setQx((rm.getElement(0, 1) + rm.getElement(1, 0)) / four_qy);
                q.setQy((float)0.5 * two_qy);
                q.setQz((rm.getElement(1, 2) + rm.getElement(2, 1)) / four_qy);
      /*printf("m11 largest\n");*/
            } else {
                float two_qz =
                        (float) Math.sqrt(rm.getElement(2, 2) - rm.getElement(0, 0) - rm.getElement(1, 1) + 1);
                float four_qz = (float)2. * two_qz;
                q.setQi((rm.getElement(0, 1) - rm.getElement(1, 0)) / four_qz);
                q.setQx((rm.getElement(2, 0) + rm.getElement(0, 2)) / four_qz);
                q.setQy((rm.getElement(1, 2) + rm.getElement(2, 1)) / four_qz);
                q.setQz((float)0.5 * two_qz);
      /*printf("m22 largest\n");*/
            }
        }
    }

    /** Euler angle functions ***/

    public static void float_eulers_of_rmat(Eulers<Float> e, RMat<Float> rm)
    {
        float dcm00 = rm.getElement(0,0);
        float dcm01 = rm.getElement(0,1);
        float dcm02 = rm.getElement(0,2);
        float dcm12 = rm.getElement(1,2);
        float dcm22 = rm.getElement(2,2);
        e.setPhi((float) Math.atan2(dcm12, dcm22));
        e.setTheta((float) -Math.asin(dcm02));
        e.setPsi((float) Math.atan2(dcm01, dcm00));
    }

    public static void float_eulers_of_quat(Eulers<Float> e, Quat<Float> q)
    {
        float qx2  = q.getQx() * q.getQx();
        float qy2  = q.getQy() * q.getQy();
        float qz2  = q.getQz() * q.getQz();
        float qiqx = q.getQi() * q.getQx();
        float qiqy = q.getQi() * q.getQy();
        float qiqz = q.getQi() * q.getQz();
        float qxqy = q.getQx() * q.getQy();
        float qxqz = q.getQx() * q.getQz();
        float qyqz = q.getQy() * q.getQz();
        float dcm00 = (float)1.0 - (float)2.*(qy2 +  qz2);
        float dcm01 =       (float)2.*(qxqy + qiqz);
        float dcm02 =       (float)2.*(qxqz - qiqy);
        float dcm12 =       (float)2.*(qyqz + qiqx);
        float dcm22 = (float)1.0 - (float)2.*(qx2 +  qy2);

        e.setPhi((float) Math.atan2(dcm12, dcm22));
        e.setTheta((float) -Math.asin(dcm02));
        e.setPsi((float) Math.atan2(dcm01, dcm00));
    }


    /******* h file ********/

    public static float FLOAT_VECT2_NORM(Vect2<Float> v) {
        return PprzAlgebra.VECT2_NORM(v).floatValue();
    }

    public static float float_vect2_norm2(Vect2<Float> v)
    {
        return PprzAlgebra.VECT2_NORM2(v).floatValue();
    }

    public static float float_vect2_norm(Vect2<Float> v)
    {
        return FLOAT_VECT2_NORM(v);
    }

    /** normalize 2D vector in place */
    public static void float_vect2_normalize(Vect2<Float> v)
    {
        float n = float_vect2_norm(v);
        if (n > 0) {
            v.setX(v.getX() / n);
            v.setY(v.getY() / n);
        }
    }

    public static void FLOAT_VECT2_NORMALIZE(Vect2<Float> v) {
        float_vect2_normalize(v);
    }

    /// 3d Vectors

    public static void FLOAT_VECT3_ZERO(Vect3<Float> v) {
        PprzAlgebra.VECT3_ASSIGN(v,0,0,0);
    }

    /* macros also usable if _v is not a Vect3<Float>, but a different struct with x,y,z members */
    public static float FLOAT_VECT3_NORM(Vect3<Float> v) {
        return PprzAlgebra.VECT3_NORM(v).floatValue();
    }

    public static float float_vect3_norm2(Vect3<Float> v)
    {
        return PprzAlgebra.VECT3_NORM2(v).floatValue();
    }

    public static float float_vect3_norm(Vect3<Float> v)
    {
        return PprzAlgebra.VECT3_NORM(v).floatValue();
    }

    /** normalize 3D vector in place */
    public static void float_vect3_normalize(Vect3<Float> v)
    {
        float n = float_vect3_norm(v);
        if (n > 0) {
            v.setX(v.getX() / n);
            v.setY(v.getY() / n);
            v.setZ(v.getZ() / n);
        }
    }

    public static void FLOAT_VECT3_NORMALIZE(Vect3<Float> v) {
        float_vect3_normalize(v);
    }

    public static void FLOAT_RATES_ZERO(Rates<Float> r) {
        PprzAlgebra.RATES_ASSIGN(r, 0., 0., 0.);
    }

    public static float FLOAT_RATES_NORM(Rates<Float> r) {
        return PprzAlgebra.RATES_NORM(r);
    }

    public static void FLOAT_RATES_LIN_CMB(Rates<Float> ro, Rates<Float> r1, float s1, Rates<Float> r2, float s2) {
        ro.setP(s1 * r1.getP() + s2 * r2.getP());
        ro.setQ(s1 * r1.getQ() + s2 * r2.getQ());
        ro.setR(s1 * r1.getR() + s2 * r2.getR());
    }

    /* defines for backwards compatibility */
    public static void FLOAT_VECT3_INTEGRATE_FI(Vect3<Float> vo, Vect3<Float> dv, float dt) {
        float_vect3_integrate_fi(vo, dv, dt);
    }
    public static void FLOAT_RATES_INTEGRATE_FI(Rates<Float> ra, Rates<Float> racc, float dt) {
        float_rates_integrate_fi(ra, racc, dt);
    }
    public static void FLOAT_RATES_OF_EULER_DOT(Rates<Float> ra, Eulers<Float> e, Eulers<Float> ed) {
        float_rates_of_euler_dot(ra, e, ed);
    }

    //** 3x3 Matrices *//
    public static void FLOAT_MAT33_ZERO(Mat33<Float> m) {
        m.zero();
    }

    public static void FLOAT_MAT33_DIAG(Mat33<Float> m,float d00, float d11, float d22) {
        PprzAlgebra.MAT33_DIAG(m,d00,d11,d22);
    }


    ///***Rotation Matricies***///

    /** initialises a rotation matrix to identity */
    public static void float_rmat_identity(RMat<Float> rm)
    {
        FLOAT_MAT33_DIAG(rm, (float)1., (float)1., (float)1.);
    }

    /* defines for backwards compatibility */
    public static void FLOAT_RMAT_INV(RMat<Float> _m_b2a, RMat<Float> _m_a2b) {float_rmat_inv(_m_b2a, _m_a2b);}
    public static void FLOAT_RMAT_NORM(RMat<Float>  _m) {float_rmat_norm(_m);}
    public static void FLOAT_RMAT_COMP(RMat<Float> _m_a2c,RMat<Float>  _m_a2b,RMat<Float>  _m_b2c) {float_rmat_comp(_m_a2c, _m_a2b, _m_b2c);}
    public static void FLOAT_RMAT_COMP_INV(RMat<Float> _m_a2b,RMat<Float>  _m_a2c,RMat<Float>  _m_b2c) {float_rmat_comp_inv(_m_a2b, _m_a2c, _m_b2c);}
    public static void FLOAT_RMAT_VMULT(Vect3<Float> _vb, RMat<Float> _m_a2b, Vect3<Float> _va) {float_rmat_vmult(_vb, _m_a2b, _va);}
    public static void FLOAT_RMAT_TRANSP_VMULT(Vect3<Float> _vb, RMat<Float> _m_b2a, Vect3<Float> _va) {float_rmat_transp_vmult(_vb, _m_b2a, _va);}
    public static void FLOAT_RMAT_RATEMULT(Rates<Float> _rb, RMat<Float> _m_a2b, Rates<Float> _ra) {float_rmat_ratemult(_rb, _m_a2b, _ra);}
    public static void FLOAT_RMAT_TRANSP_RATEMULT(Rates<Float> _rb, RMat<Float> _m_b2a, Rates<Float> _ra) {float_rmat_ratemult(_rb, _m_b2a, _ra);}
    public static void FLOAT_RMAT_OF_AXIS_ANGLE(RMat<Float> _rm, Vect3<Float> _uv, float _an) {float_rmat_of_axis_angle(_rm, _uv, _an);}
    public static void FLOAT_RMAT_OF_EULERS(RMat<Float> _rm, Eulers<Float> _e)     {float_rmat_of_eulers_321(_rm, _e);}
    public static void FLOAT_RMAT_OF_EULERS_321(RMat<Float> _rm, Eulers<Float> _e) {float_rmat_of_eulers_321(_rm, _e);}
    public static void FLOAT_RMAT_OF_EULERS_312(RMat<Float> _rm, Eulers<Float> _e) {float_rmat_of_eulers_312(_rm, _e);}
    public static void FLOAT_RMAT_OF_QUAT(RMat<Float> _rm, Quat<Float> _q)       {float_rmat_of_quat(_rm, _q);}
    public static void FLOAT_RMAT_INTEGRATE_FI(RMat<Float> _rm, Rates<Float> _omega, float _dt) {float_rmat_integrate_fi(_rm, _omega, _dt);}

    // Quaternion algebras

    /** initialises a quaternion to identity */
    public static void float_quat_identity(Quat<Float> q)
    {
        q.setQi((float) 1.0);
        q.setQx((float) 0);
        q.setQy((float) 0);
        q.setQz((float) 0);
    }

    public static float FLOAT_QUAT_NORM2(Quat<Float> q) {
        return PprzAlgebra.QUAT_NORM2(q).floatValue();
    }

    public static float float_quat_norm(Quat<Float> q) {
        return PprzAlgebra.QUAT_NORM(q).floatValue();
    }

    public static void float_quat_normalize(Quat<Float> q)
    {
        float qnorm = float_quat_norm(q);
        if (qnorm > FLT_MIN) {
            q.setQi(q.getQi() / qnorm);
            q.setQx(q.getQx() / qnorm);
            q.setQy(q.getQy() / qnorm);
            q.setQz(q.getQz() / qnorm);
        }
    }

    public static void float_quat_invert(Quat<Float> qo, Quat<Float> qi)
    {
        PprzAlgebra.QUAT_INVERT(qo, qi);
    }

    public static void float_quat_wrap_shortest(Quat<Float> q)
    {
        if (q.getQi() < 0.) {
            PprzAlgebra.QUAT_EXPLEMENTARY(q, q);
        }
    }

    public static void FLOAT_QUAT_EXTRACT (Vect3<Float> vo, Quat<Float> qi) {
        PprzAlgebra.QUAT_EXTRACT_Q(vo,qi);
    }

    /* defines for backwards compatibility */
    public static void FLOAT_QUAT_ZERO(Quat<Float> _q) {float_quat_identity(_q);}
    public static void FLOAT_QUAT_INVERT(Quat<Float> _qo, Quat<Float> _qi) {float_quat_invert(_qo, _qi);}
    public static void FLOAT_QUAT_WRAP_SHORTEST(Quat<Float> _q) {float_quat_wrap_shortest(_q);}
    public static void FLOAT_QUAT_NORM(Quat<Float> _q) {float_quat_norm(_q);}
    public static void FLOAT_QUAT_NORMALIZE(Quat<Float> _q) {float_quat_normalize(_q);}
    public static void FLOAT_QUAT_COMP(Quat<Float> _a2c, Quat<Float> _a2b, Quat<Float> _b2c) {float_quat_comp((_a2c), (_a2b), (_b2c));}
    public static void FLOAT_QUAT_MULT(Quat<Float> _a2c, Quat<Float> _a2b, Quat<Float> _b2c) {float_quat_comp((_a2c), (_a2b), (_b2c));}
    public static void FLOAT_QUAT_INV_COMP(Quat<Float> _b2c, Quat<Float> _a2b, Quat<Float> _a2c) {float_quat_inv_comp((_b2c), (_a2b), (_a2c));}
    public static void FLOAT_QUAT_COMP_INV(Quat<Float> _a2b, Quat<Float> _a2c, Quat<Float> _b2c) {float_quat_comp_inv((_a2b), (_a2c), (_b2c));}
    public static void FLOAT_QUAT_COMP_NORM_SHORTEST(Quat<Float> _a2c, Quat<Float> _a2b, Quat<Float> _b2c) {float_quat_comp_norm_shortest((_a2c), (_a2b), (_b2c));}
    public static void FLOAT_QUAT_COMP_INV_NORM_SHORTEST(Quat<Float> _a2b, Quat<Float> _a2c, Quat<Float> _b2c) {float_quat_comp_inv_norm_shortest((_a2b), (_a2c), (_b2c));}
    public static void FLOAT_QUAT_INV_COMP_NORM_SHORTEST(Quat<Float> _b2c, Quat<Float> _a2b, Quat<Float> _a2c) {float_quat_inv_comp_norm_shortest((_b2c), (_a2b), (_a2c));}
    public static void FLOAT_QUAT_DIFFERENTIAL(Quat<Float> q_out, Rates<Float> w, float dt) {float_quat_differential((q_out), (w), dt);}
    public static void FLOAT_QUAT_INTEGRATE(Quat<Float> _q, Rates<Float> _omega, float _dt) {float_quat_integrate((_q), (_omega), _dt);}
    public static void FLOAT_QUAT_VMULT(Vect3<Float> v_out, Quat<Float> q, Vect3<Float> v_in) {float_quat_vmult((v_out), (q), (v_in));}
    public static void FLOAT_QUAT_DERIVATIVE(Quat<Float> _qd, Rates<Float> _r, Quat<Float> _q) {float_quat_derivative((_qd), (_r), (_q));}
    public static void FLOAT_QUAT_DERIVATIVE_LAGRANGE(Quat<Float> _qd, Rates<Float> _r, Quat<Float> _q) {float_quat_derivative_lagrange((_qd), (_r), (_q));}
    public static void FLOAT_QUAT_OF_EULERS(Quat<Float> _q, Eulers<Float> _e) {float_quat_of_eulers((_q), (_e));}
    public static void FLOAT_QUAT_OF_AXIS_ANGLE(Quat<Float> _q, Vect3<Float> _uv, float _an) {float_quat_of_axis_angle((_q), (_uv), _an);}
    public static void FLOAT_QUAT_OF_ORIENTATION_VECT(Quat<Float> _q, Vect3<Float> _ov) {float_quat_of_orientation_vect((_q), (_ov));}
    public static void FLOAT_QUAT_OF_RMAT(Quat<Float> _q, RMat<Float> _r) {float_quat_of_rmat((_q), (_r));}

    // Euler angles

    public static void FLOAT_EULERS_ZERO(Eulers<Float> e) {
        PprzAlgebra.EULERS_ASSIGN(e,0,0,0);
    }

    public static float float_eulers_norm(Eulers<Float> e)
    {
        return PprzAlgebra.EULERS_NORM(e).floatValue();
    }

    /* defines for backwards compatibility */
    public static void FLOAT_EULERS_OF_RMAT(Eulers<Float> _e, RMat<Float> _rm) {float_eulers_of_rmat((_e), (_rm));}
    public static void FLOAT_EULERS_OF_QUAT(Eulers<Float> _e, Quat<Float> _q) {float_eulers_of_quat((_e), (_q));}
    public static void FLOAT_EULERS_NORM(Eulers<Float> _e) {float_eulers_norm((_e));}

//
// Generic vector algebra
//
// // TODO these seem to not be used .. they are untyped things vv

/** a = 0 */
    public static void float_vect_zero(PrimitiveWrapper<Float[]> a, int n)
    {
        int i;
        for (i = 0; i < n; i++) { a.getPrimitive()[i] = (float) 0.; }
    }

/** a = b */
    static void float_vect_copy(float[] a, float[] b, int n)
    {
        int i;
        for (i = 0; i < n; i++) { a[i] = b[i]; }
    }

/** o = a + b */
    static void float_vect_sum(float[] o, float[] a, float[] b, int n)
    {
        int i;
        for (i = 0; i < n; i++) { o[i] = a[i] + b[i]; }
    }

/** o = a - b */
    static void float_vect_diff(float[] o, float[] a, float[] b, int n)
    {
        int i;
        for (i = 0; i < n; i++) { o[i] = a[i] - b[i]; }
    }

/** o = a * b (element wise) */
    static void float_vect_mul(float[] o, float[] a, float[] b, int n)
    {
        int i;
        for (i = 0; i < n; i++) { o[i] = a[i] * b[i]; }
    }

/** a += b */
    static void float_vect_add(float[] a, float[] b, int n)
    {
        int i;
        for (i = 0; i < n; i++) { a[i] += b[i]; }
    }

/** a -= b */
    static void float_vect_sub(float[] a, float[] b, int n)
    {
        int i;
        for (i = 0; i < n; i++) { a[i] -= b[i]; }
    }

/** o = a * s */
    static void float_vect_smul(float[] o, float[] a, float s, int n)
    {
        int i;
        for (i = 0; i < n; i++) { o[i] = a[i] * s; }
    }

/** o = a / s */
    public static void float_vect_sdiv(PrimitiveWrapper<Float[]> o, PrimitiveWrapper<Float[]> a, float s, int n)
    {
        int i;
        if (Math.abs(s) > 1e-5) {
            for (i = 0; i < n; i++) { o.getPrimitive()[i] = a.getPrimitive()[i] / s; }
        }
    }

/** ||a|| */
    public static float float_vect_norm(PrimitiveWrapper<Float[]> a, int n)
    {
        int i;
        float sum = 0;
        for (i = 0; i < n; i++) { sum += a.getPrimitive()[i] * a.getPrimitive()[i]; }
        return (float) Math.sqrt(sum);
    }



//Generic matrix algebra



/** Make a pointer to a matrix of _rows lines */
//    TODO I dont think this is used
//    #define MAKE_MATRIX_PTR(_ptr, _mat, _rows) \
//    float * _ptr[_rows]; \
//    { \
//        int i; \
//        for (i = 0; i < _rows; i++) { _ptr[i] = &_mat[i][0]; } \
//    }

/** a = 0 */
    public static void float_mat_zero(PrimitiveWrapper<Float[][]> a, int m, int n)
    {
        int i, j;
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) { a.getPrimitive()[i][j] = (float) 0.; }
        }
    }

/** a = b */
    public static void float_mat_copy(PrimitiveWrapper<Float[][]> a, PrimitiveWrapper<Float[][]> b, int m, int n)
    {
        int i, j;
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) { a.getPrimitive()[i][j] = b.getPrimitive()[i][j]; }
        }
    }

/** o = a + b */
    static void float_mat_sum(float[][] o, float[][] a, float[][] b, int m, int n)
    {
        int i, j;
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) { o[i][j] = a[i][j] + b[i][j]; }
        }
    }

/** o = a - b */
    static void float_mat_diff(float[][] o, float[][] a, float[][] b, int m, int n)
    {
        int i, j;
        for (i = 0; i < m; i++) {
            for (j = 0; j < n; j++) { o[i][j] = a[i][j] - b[i][j]; }
        }
    }

/** transpose square matrix */
    public static void float_mat_transpose(PrimitiveWrapper<Float[][]> a, int n)
    {
        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < i; j++) {
                float t = a.getPrimitive()[i][j];
                a.getPrimitive()[i][j] = a.getPrimitive()[j][i];
                a.getPrimitive()[j][i] = t;
            }
        }
    }

/** o = a * b
*
* a: [m x n]
* b: [n x l]
* o: [m x l]
*/
    public static void float_mat_mul(PrimitiveWrapper<Float[][]> o, PrimitiveWrapper<Float[][]> a, PrimitiveWrapper<Float[][]> b, int m, int n, int l)
    {
        int i, j, k;
        for (i = 0; i < m; i++) {
            for (j = 0; j < l; j++) {
                o.getPrimitive()[i][j] = (float) 0.;
                for (k = 0; k < n; k++) {
                    o.getPrimitive()[i][j] += a.getPrimitive()[i][k] * b.getPrimitive()[k][j];
                }
            }
        }
    }

/** matrix minor
*
* a: [m x n]
* o: [I(d,d)     0     ]
*    [  0    a(d,m:d,n)]
*/
    public static void float_mat_minor(PrimitiveWrapper<Float[][]> o, PrimitiveWrapper<Float[][]> a, int m, int n, int d)
    {
        int i, j;
        float_mat_zero(o, m, n);
        for (i = 0; i < d; i++) { o.getPrimitive()[i][i] = (float) 1.0; }
        for (i = d; i < m; i++) {
            for (j = d; j < n; j++) {
                o.getPrimitive()[i][j] = a.getPrimitive()[i][j];
            }
        }
    }

/** o = I - v v^T */
    public static void float_mat_vmul(PrimitiveWrapper<Float[][]> o, PrimitiveWrapper<Float[]> v, int n)
    {
        int i, j;
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                o.getPrimitive()[i][j] = (float) (-2. *  v.getPrimitive()[i] * v.getPrimitive()[j]);
            }
        }
        for (i = 0; i < n; i++) {
            o.getPrimitive()[i][i] += (float)1.;
        }
    }

/** o = c-th column of matrix a[m x n] */
    public static void float_mat_col(PrimitiveWrapper<Float[]> o, PrimitiveWrapper<Float[][]> a, int m, int c)
    {
        int i;
        for (i = 0; i < m; i++) {
            o.getPrimitive()[i] = a.getPrimitive()[i][c];
        }
    }

    public static final float FLT_MIN = Float.MIN_VALUE;


}

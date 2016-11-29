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

package ub.juav.airborne.math.functions.orientation;

import ub.juav.airborne.math.functions.algebra.PprzAlgebra;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraFloat;
import ub.juav.airborne.math.functions.algebra.PprzAlgebraInt;
import ub.juav.airborne.math.structs.algebra.Eulers;
import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.RMat;
import ub.juav.airborne.math.structs.orientation.OrientationReps;
import ub.juav.airborne.math.util.UtilityFunctions;

/**
 * Created by adamczer on 11/22/15.
 */
public class PprzOrientationConversion {

    public static final int ORREP_QUAT_I = 0;  ///< Quaternion (BFP int)
    public static final int ORREP_EULER_I = 1;  ///< zyx Euler (BFP int)
    public static final int ORREP_RMAT_I = 2;  ///< Rotation Matrix (BFP int)
    public static final int ORREP_QUAT_F = 3;  ///< Quaternion (float)
    public static final int ORREP_EULER_F = 4;  ///< zyx Euler (float)
    public static final int ORREP_RMAT_F = 5;  ///< Rotation Matrix (float)

    /*********************** validity test functions ******************/
/// Test if orientations are valid.
    public static boolean orienationCheckValid(OrientationReps orientation)
    {
        return (orientation.getStatus().getPrimitive() == 1);//TODO is this right????
    }

/// Set vehicle body attitude from quaternion (int).
    public static void orientationSetQuat_i(OrientationReps orientation, Quat<Integer> quat)
    {
        PprzAlgebra.QUAT_COPY(orientation.getQuat_i(), quat);
  /* clear bits for all attitude representations and only set the new one */
        orientation.setStatus(1 << ORREP_QUAT_I);
    }

/// Set vehicle body attitude from rotation m (int).
    public static void orientationSetRMat_i(OrientationReps orientation, RMat<Integer> rmat)
    {
        PprzAlgebra.RMAT_COPY(orientation.getRmat_i(), rmat);
  /* clear bits for all attitude representations and only set the new one */
        orientation.setStatus(1 << ORREP_RMAT_I);
    }

/// Set vehicle body attitude from euler angles (int).
    public static void orientationSetEulers_i(OrientationReps orientation, Eulers<Integer> eulers)
    {
        PprzAlgebra.EULERS_COPY(orientation.getEulers_i(), eulers);
  /* clear bits for all attitude representations and only set the new one */
        orientation.setStatus(1 << ORREP_EULER_I);
    }

/// Set vehicle body attitude from quaternion (float).
    public static void orientationSetQuat_f(OrientationReps orientation, Quat<Float> quat)
    {
        PprzAlgebra.QUAT_COPY(orientation.getQuat_f(), quat);
  /* clear bits for all attitude representations and only set the new one */
        orientation.setStatus(1 << ORREP_QUAT_F);
    }

/// Set vehicle body attitude from rotation m (float).
    public static void orientationSetRMat_f(OrientationReps orientation, RMat<Float> rmat)
    {
        PprzAlgebra.RMAT_COPY(orientation.getRmat_f(), rmat);
  /* clear bits for all attitude representations and only set the new one */
        orientation.setStatus(1 << ORREP_RMAT_F);
    }

/// Set vehicle body attitude from euler angles (float).
    public static void orientationSetEulers_f(OrientationReps orientation, Eulers<Float> eulers)
    {
        PprzAlgebra.EULERS_COPY(orientation.getEulers_f(), eulers);
  /* clear bits for all attitude representations and only set the new one */
        orientation.setStatus(1 << ORREP_EULER_F);
    }


    /// Get vehicle body attitude quaternion (int).
    public static Quat<Integer> orientationGetQuat_i(OrientationReps orientation)
    {
        if (!UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_I)) {
            orientationCalcQuat_i(orientation);
        }
        return orientation.getQuat_i();
    }

    /// Get vehicle body attitude rotation m (int).
    public static RMat<Integer> orientationGetRMat_i(OrientationReps orientation)
    {
        if (!UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_I)) {
            orientationCalcRMat_i(orientation);
        }
        return orientation.getRmat_i();
    }

    /// Get vehicle body attitude euler angles (int).
    public static Eulers<Integer> orientationGetEulers_i(OrientationReps orientation)
    {
        if (!UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_I)) {
            orientationCalcEulers_i(orientation);
        }
        return orientation.getEulers_i();
    }

    /// Get vehicle body attitude quaternion (float).
    public static Quat<Float> orientationGetQuat_f(OrientationReps orientation)
    {
        if (!UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_F)) {
            orientationCalcQuat_f(orientation);
        }
        return orientation.getQuat_f();
    }

    /// Get vehicle body attitude rotation m (float).
    public static RMat<Float> orientationGetRMat_f(OrientationReps orientation)
    {
        if (!UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_F)) {
            orientationCalcRMat_f(orientation);
        }
        return orientation.getRmat_f();
    }

    /// Get vehicle body attitude euler angles (float).
    public static Eulers<Float> orientationGetEulers_f(OrientationReps orientation)
    {
        if (!UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_F)) {
            orientationCalcEulers_f(orientation);
        }
        return orientation.getEulers_f();
    }

    ///C file
    public static void orientationCalcQuat_i(OrientationReps orientation)
    {
        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_I)) {
            return;
        }

        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_F)) {
            PprzAlgebra.QUAT_BFP_OF_REAL(orientation.getQuat_i(), orientation.getQuat_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_I)) {
            PprzAlgebraInt.int32_quat_of_rmat(orientation.getQuat_i(), orientation.getRmat_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_I)) {
            PprzAlgebraInt.int32_quat_of_eulers(orientation.getQuat_i(), orientation.getEulers_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_F)) {
            PprzAlgebra.RMAT_BFP_OF_REAL(orientation.getRmat_i(), orientation.getRmat_f());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_RMAT_I);
            PprzAlgebraInt.int32_quat_of_rmat(orientation.getQuat_i(), orientation.getRmat_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_F)) {
            PprzAlgebra.EULERS_BFP_OF_REAL(orientation.getEulers_i(), orientation.getEulers_f());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_EULER_I);
            PprzAlgebraInt.int32_quat_of_eulers(orientation.getQuat_i(), orientation.getEulers_i());
        }
  /* set bit to indicate this representation is computed */
        UtilityFunctions.SetBit(orientation.getStatus(), ORREP_QUAT_I);
    }

    public static void orientationCalcRMat_i(OrientationReps orientation)
    {
        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_I)) {
            return;
        }

        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_F)) {
            PprzAlgebra.RMAT_BFP_OF_REAL(orientation.getRmat_i(), orientation.getRmat_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_I)) {
            PprzAlgebraInt.int32_rmat_of_quat((orientation.getRmat_i()), (orientation.getQuat_i()));
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_I)) {
            PprzAlgebraInt.int32_rmat_of_eulers(orientation.getRmat_i(), orientation.getEulers_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_F)) {
            PprzAlgebra.QUAT_BFP_OF_REAL(orientation.getQuat_i(), orientation.getQuat_f());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_QUAT_I);
            PprzAlgebraInt.int32_rmat_of_quat(orientation.getRmat_i(), orientation.getQuat_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_F)) {
            PprzAlgebra.EULERS_BFP_OF_REAL(orientation.getEulers_i(), orientation.getEulers_f());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_EULER_I);
            PprzAlgebraInt.int32_rmat_of_eulers(orientation.getRmat_i(), orientation.getEulers_i());
        }
  /* set bit to indicate this representation is computed */
        UtilityFunctions.SetBit(orientation.getStatus(), ORREP_RMAT_I);
    }

    public static void orientationCalcEulers_i(OrientationReps orientation)
    {
        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_I)) {
            return;
        }

        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_F)) {
            PprzAlgebra.EULERS_BFP_OF_REAL(orientation.getEulers_i(), orientation.getEulers_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_I)) {
            PprzAlgebraInt.int32_eulers_of_rmat(orientation.getEulers_i(), orientation.getRmat_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_I)) {
            PprzAlgebraInt.int32_eulers_of_quat(orientation.getEulers_i(), orientation.getQuat_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_F)) {
            PprzAlgebra.RMAT_BFP_OF_REAL(orientation.getRmat_i(), orientation.getRmat_f());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_RMAT_I);
            PprzAlgebraInt.int32_eulers_of_rmat(orientation.getEulers_i(), orientation.getRmat_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_F)) {
            PprzAlgebra.QUAT_BFP_OF_REAL(orientation.getQuat_i(), orientation.getQuat_f());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_QUAT_I);
            PprzAlgebraInt.int32_eulers_of_quat(orientation.getEulers_i(), orientation.getQuat_i());
        }
  /* set bit to indicate this representation is computed */
        UtilityFunctions.SetBit(orientation.getStatus(), ORREP_EULER_I);
    }

    public static void orientationCalcQuat_f(OrientationReps orientation)
    {
        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_F)) {
            return;
        }

        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_I)) {
            PprzAlgebra.QUAT_FLOAT_OF_BFP(orientation.getQuat_f(), orientation.getQuat_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_F)) {
            PprzAlgebraFloat.float_quat_of_rmat(orientation.getQuat_f(), orientation.getRmat_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_F)) {
            PprzAlgebraFloat.float_quat_of_eulers(orientation.getQuat_f(), orientation.getEulers_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_I)) {
            PprzAlgebra.RMAT_FLOAT_OF_BFP(orientation.getRmat_f(), orientation.getRmat_i());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_RMAT_F);
            PprzAlgebraFloat.float_quat_of_rmat(orientation.getQuat_f(), orientation.getRmat_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_I)) {
            PprzAlgebra.EULERS_FLOAT_OF_BFP(orientation.getEulers_f(), orientation.getEulers_i());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_EULER_F);
            PprzAlgebraFloat.float_quat_of_eulers(orientation.getQuat_f(), orientation.getEulers_f());
        }
  /* set bit to indicate this representation is computed */
        UtilityFunctions.SetBit(orientation.getStatus(), ORREP_QUAT_F);
    }

    public static void orientationCalcRMat_f(OrientationReps orientation)
    {
        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_F)) {
            return;
        }

        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_I)) {
            PprzAlgebra.RMAT_FLOAT_OF_BFP(orientation.getRmat_f(), orientation.getRmat_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_F)) {
            PprzAlgebraFloat.float_rmat_of_quat(orientation.getRmat_f(), orientation.getQuat_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_F)) {
            PprzAlgebraFloat.float_rmat_of_eulers(orientation.getRmat_f(), orientation.getEulers_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_I)) {
            PprzAlgebra.QUAT_FLOAT_OF_BFP(orientation.getQuat_f(), orientation.getQuat_i());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_QUAT_F);
            PprzAlgebraFloat.float_rmat_of_quat(orientation.getRmat_f(), orientation.getQuat_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_I)) {
            PprzAlgebra.EULERS_FLOAT_OF_BFP(orientation.getEulers_f(), orientation.getEulers_i());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_EULER_F);
            PprzAlgebraFloat.float_rmat_of_eulers(orientation.getRmat_f(), orientation.getEulers_f());
        }
  /* set bit to indicate this representation is computed */
        UtilityFunctions.SetBit(orientation.getStatus(), ORREP_RMAT_F);
    }

    public static void orientationCalcEulers_f(OrientationReps orientation)
    {
        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_F)) {
            return;
        }

        if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_EULER_I)) {
            PprzAlgebra.EULERS_FLOAT_OF_BFP(orientation.getEulers_f(), orientation.getEulers_i());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_F)) {
            PprzAlgebraFloat.float_eulers_of_rmat(orientation.getEulers_f(), orientation.getRmat_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_F)) {
            PprzAlgebraFloat.float_eulers_of_quat(orientation.getEulers_f(), orientation.getQuat_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_RMAT_I)) {
            PprzAlgebra.RMAT_FLOAT_OF_BFP(orientation.getRmat_f(), orientation.getRmat_i());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_RMAT_F);
            PprzAlgebraFloat.float_eulers_of_rmat(orientation.getEulers_f(), orientation.getRmat_f());
        } else if (UtilityFunctions.bit_is_set(orientation.getStatus(), ORREP_QUAT_I)) {
            PprzAlgebra.QUAT_FLOAT_OF_BFP(orientation.getQuat_f(), orientation.getQuat_i());
            UtilityFunctions.SetBit(orientation.getStatus(), ORREP_QUAT_F);
            PprzAlgebraFloat.float_eulers_of_quat(orientation.getEulers_f(), orientation.getQuat_f());
        }
  /* set bit to indicate this representation is computed */
        UtilityFunctions.SetBit(orientation.getStatus(), ORREP_EULER_F);
    }

}

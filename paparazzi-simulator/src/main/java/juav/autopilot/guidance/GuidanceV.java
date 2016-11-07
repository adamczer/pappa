package juav.autopilot.guidance;

import ub.juav.airborne.math.structs.algebra.RMat;

import static juav.autopilot.state.State.stateGetNedToBodyRMat_i;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.BFP_OF_REAL;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.INT32_TRIG_FRAC;

/**
 * Created by adamczer on 10/28/16.
 */
public class GuidanceV {

    /// get the cosine of the angle between thrust vector and gravity vector
    public static int get_vertical_thrust_coeff()
    {
        // cos(30°) = 0.8660254
        int max_bank_coef = BFP_OF_REAL(0.8660254f, INT32_TRIG_FRAC);

        RMat<Integer> att = stateGetNedToBodyRMat_i();
  /* thrust vector:
   *  int32_rmat_vmult(&thrust_vect, &att, &zaxis)
   * same as last colum of rmat with INT32_TRIG_FRAC
   * struct Int32Vect thrust_vect = {att.m[2], att.m[5], att.m[8]};
   *
   * Angle between two vectors v1 and v2:
   *  angle = acos(dot(v1, v2) / (norm(v1) * norm(v2)))
   * since here both are already of unit length:
   *  angle = acos(dot(v1, v2))
   * since we we want the cosine of the angle we simply need
   *  thrust_coeff = dot(v1, v2)
   * also can be simplified considering: v1 is zaxis with (0,0,1)
   *  dot(v1, v2) = v1.z * v2.z = v2.z
   */
        int coef = att.getFlattendElement(8);
        if (coef < max_bank_coef) {
            coef = max_bank_coef;
        }
        return coef;
    }
}

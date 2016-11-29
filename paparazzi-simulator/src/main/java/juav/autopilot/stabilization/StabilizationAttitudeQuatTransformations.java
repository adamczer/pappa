package juav.autopilot.stabilization;

import ub.juav.airborne.math.structs.algebra.Quat;
import ub.juav.airborne.math.structs.algebra.RMat;
import ub.juav.airborne.math.structs.algebra.Vect2;
import ub.juav.airborne.math.structs.algebra.Vect3;

import static ub.juav.airborne.math.functions.algebra.PprzAlgebra.*;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraFloat.*;
import static ub.juav.airborne.math.functions.algebra.PprzAlgebraInt.ANGLE_FLOAT_OF_BFP;

/**
 * Created by adamczer on 11/28/16.
 */
public class StabilizationAttitudeQuatTransformations {
    public static void quat_from_earth_cmd_i(Quat<Integer> quat, Vect2<Integer> cmd, int heading)
    {
        // use float conversion for now...
        Vect2<Float> cmd_f = Vect2.newFloat();
        cmd_f.x = ANGLE_FLOAT_OF_BFP(cmd.x);
        cmd_f.y = ANGLE_FLOAT_OF_BFP(cmd.y);
        float heading_f = ANGLE_FLOAT_OF_BFP(heading);

        Quat<Float> quat_f = Quat.newFloat();
        quat_from_earth_cmd_f(quat_f, cmd_f, heading_f);

        // convert back to fixed point
        QUAT_BFP_OF_REAL(quat, quat_f);
    }

    public static void quat_from_earth_cmd_f(Quat<Float> quat, Vect2<Float> cmd, float heading)
    {
//  printf("quat_from_earth_cmd_f\n");

  /* cmd_x is positive to north = negative pitch
   * cmd_y is positive to east = positive roll
   *
   * orientation vector describing simultaneous rotation of roll/pitch
   */
        Vect3<Float> ov = Vect3.newFloat(cmd.y, -cmd.x, 0.0f);
  /* quaternion from that orientation vector */
        Quat<Float> q_rp = Quat.newFloat();
        float_quat_of_orientation_vect(q_rp, ov);

  /* as rotation m */
        RMat<Float> R_rp = (RMat<Float>) RMat.newFloat();
        float_rmat_of_quat(R_rp, q_rp);
  /* body x-axis (before heading command) is first column */
        Vect3<Float> b_x = Vect3.newFloat();
        VECT3_ASSIGN(b_x, R_rp.getFlattendElement(0), R_rp.getFlattendElement(3), R_rp.getFlattendElement(6));
  /* body z-axis (thrust vect) is last column */
        Vect3<Float> thrust_vect = Vect3.newFloat();
        VECT3_ASSIGN(thrust_vect, R_rp.getFlattendElement(2), R_rp.getFlattendElement(5), R_rp.getFlattendElement(8));

        /// @todo optimize yaw angle calculation

  /*
   * Instead of using the psi setpoint angle to rotate around the body z-axis,
   * calculate the real angle needed to align the projection of the body x-axis
   * onto the horizontal plane with the psi setpoint.
   *
   * angle between two vectors a and b:
   * angle = atan2(norm(cross(a,b)), dot(a,b)) * sign(dot(cross(a,b), n))
   * where the normal n is the thrust vector (i.e. both a and b lie in that plane)
   */

        // desired heading vect in earth x-y plane
        Vect3 psi_vect = Vect3.newFloat((float) Math.cos(heading), (float) Math.sin(heading), 0.0f);

  /* projection of desired heading onto body x-y plane
   * b = v - dot(v,n)*n
   */
        float dot = VECT3_DOT_PRODUCT(psi_vect, thrust_vect).floatValue();
        Vect3<Float> dotn = Vect3.newFloat();
        VECT3_SMUL(dotn, thrust_vect, dot);

        // b = v - dot(v,n)*n
        Vect3<Float> b = Vect3.newFloat();
        VECT3_DIFF(b, psi_vect, dotn);
        dot = VECT3_DOT_PRODUCT(b_x, b).floatValue();
        Vect3 cross = Vect3.newFloat();
        VECT3_CROSS_PRODUCT(cross, b_x, b);
        // norm of the cross product
        float nc = FLOAT_VECT3_NORM(cross);
        // angle = atan2(norm(cross(a,b)), dot(a,b))
        float yaw2 = (float) (Math.atan2(nc, dot) / 2.0);

        // negative angle if needed
        // sign(dot(cross(a,b), n)
        float dot_cross_ab = VECT3_DOT_PRODUCT(cross, thrust_vect).floatValue();
        if (dot_cross_ab < 0) {
            yaw2 = -yaw2;
        }

  /* quaternion with yaw command */
        Quat<Float> q_yaw = Quat.newFloat();
        QUAT_ASSIGN(q_yaw, Math.cos(yaw2), 0.0, 0.0, Math.sin(yaw2));

  /* final setpoint: apply roll/pitch, then yaw around resulting body z-axis */
        float_quat_comp(quat, q_rp, q_yaw);
        float_quat_normalize(quat);
        float_quat_wrap_shortest(quat);

    }
}

package ub.cse.juav.jni.fdm;

import com.fiji.fivm.r1.GodGiven;
import com.fiji.fivm.r1.Import;

/**
 * Created by adamczer on 2/20/17.
 */
public class FijiFdm {
    //TODO used to get components over jni for roational veloicty ie gyro
    public static double getFdmBodyInertialRotVelP() {
        return get_fdm_body_inetrial_rot_vel_p_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_body_inetrial_rot_vel_p_juav();

    public static double getFdmBodyInertialRotVelQ() {
        return get_fdm_body_inetrial_rot_vel_q_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_body_inetrial_rot_vel_q_juav();

    public static double getFdmBodyInertialRotVelR() {
        return get_fdm_body_inetrial_rot_vel_r_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_body_inetrial_rot_vel_r_juav();

    //TODO used to get components over jni for liniar motion ie acceleration
    public static double getFdmBodyAccelX() {
        return get_fdm_body_accel_x_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_body_accel_x_juav();

    public static double getFdmBodyAccelY() {
        return get_fdm_body_accel_y_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_body_accel_y_juav();

    public static double getFdmBodyAccelZ() {
        return get_fdm_body_accel_z_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_body_accel_z_juav();

    //TODO used to get components over jni for liniar motion ie acceleration
    public static double getFdmBodyToImu(int row, int col) {
        return get_fdm_body_to_imu_juav(row,col);
    }
    @Import
    @GodGiven
    private static native double get_fdm_body_to_imu_juav(int row, int col);

    //TODO used to get components over jni for magnetics
    public static double getFdmLtpToBodyQuatQi() {
        return get_fdm_ltp_to_body_quat_qi_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ltp_to_body_quat_qi_juav();

    public static double getFdmLtpToBodyQuatQx() {
        return get_fdm_ltp_to_body_quat_qx_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ltp_to_body_quat_qx_juav();

    public static double getFdmLtpToBodyQuatQy() {
        return get_fdm_ltp_to_body_quat_qy_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ltp_to_body_quat_qy_juav();

    public static double getFdmLtpToBodyQuatQz() {
        return get_fdm_ltp_to_body_quat_qz_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ltp_to_body_quat_qz_juav();

    //TODO used to get components over jni for magnetics
    public static double getFdmLtpHX() {
        return get_fdm_ltp_h_x_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ltp_h_x_juav();

    public static double getFdmLtpHY() {
        return get_fdm_ltp_h_y_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ltp_h_y_juav();

    public static double getFdmLtpHZ() {
        return get_fdm_ltp_h_z_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ltp_h_z_juav();

    //TODO used to get components over jni for barometer
    public static double getHmsl() {
        return get_fdm_hmsl_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_hmsl_juav();

    public static double getFdmEcefEcefVelX() {
        return get_fdm_ecef_ecef_vel_x_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ecef_ecef_vel_x_juav();

    public static double getFdmEcefEcefVelY() {
        return get_fdm_ecef_ecef_vel_y_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ecef_ecef_vel_y_juav();

    public static double getFdmEcefEcefVelZ() {
        return get_fdm_ecef_ecef_vel_z_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ecef_ecef_vel_z_juav();


    public static double getFdmEcefPosX() {
        return get_fdm_ecef_pos_x_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ecef_pos_x_juav();

    public static double getFdmEcefPosY() {
        return get_fdm_ecef_pos_y_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ecef_pos_y_juav();

    public static double getFdmEcefPosZ() {
        return get_fdm_ecef_pos_z_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_ecef_pos_z_juav();

    public static double getFdmAgl() {
        return get_fdm_agl_juav();
    }
    @Import
    @GodGiven
    private static native double get_fdm_agl_juav();
}

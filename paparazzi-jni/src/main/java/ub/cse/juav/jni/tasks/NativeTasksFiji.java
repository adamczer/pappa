package ub.cse.juav.jni.tasks;

import com.fiji.fivm.r1.GodGiven;
import com.fiji.fivm.r1.Import;
import com.fiji.fivm.r1.Inline;

/**
 * Created by adamczer on 2/20/17.
 */
public class NativeTasksFiji {
    public static void atmosphereUpdate() {
        nps_atmosphere_update(1./1000.);
    }
    @Import
    @GodGiven
    private static native void nps_atmosphere_update(double simDt);
    public static void autoPilotRunSystimeStep() {
        nps_autopilot_run_systime_step();
    }
    @Import
    @GodGiven
    private static native void nps_autopilot_run_systime_step();

    public static void fdmRunStep() {
        nps_fdm_run_step_juav();
    }
    @Import
    @GodGiven
    private static native void nps_fdm_run_step_juav();

    public static void sensorsRunStep(double simTime) {
        nps_sensors_run_step(simTime);
    }
    @Import
    @GodGiven
    private static native void nps_sensors_run_step(double simTime);

    public static void autoPilotRunStep(double simTime) {
        nps_autopilot_run_step(simTime);
    }
    @Import
    @GodGiven
    private static native void nps_autopilot_run_step(double simTime);

    public static void npsAutopilotRunStepRadio(double simTime) {
        nps_autopilot_run_step_radio_juav(simTime);
    }
    @Import
    @GodGiven
    private static native void nps_autopilot_run_step_radio_juav(double simTime);

    public static void npsAutopilotRunStepOverwriteIns() {
        sim_overwrite_ins_juav();
    }
    @Import
    @GodGiven
    private static native void sim_overwrite_ins_juav();

    public static void npsAutopilotRunStepOverwriteAhrs() {
        sim_overwrite_ahrs_juav();
    }
    @Import
    @GodGiven
    private static native void sim_overwrite_ahrs_juav();

    public static void npsAutopilotRunStepHandelPeriodicTasks() {
        handle_periodic_tasks_juav();
    }
    @Import
    @GodGiven
    private static native void handle_periodic_tasks_juav();

    public static void npsAutopilotRunStepConvertMotorMixingCommandsToAutopilotCommands() {
        convert_motor_mixing_commands_to_autopilot_commands();
    }
    @Import
    @GodGiven
    private static native void convert_motor_mixing_commands_to_autopilot_commands();

    public static void npsElectricalRunStep(double time) {
        nps_electrical_run_step_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_electrical_run_step_juav(double time);

    public static void sendBarometricReading(float pressure) {
        nps_send_baro_reading_juav(pressure);
    }
    @Import
    @GodGiven
    private static native void nps_send_baro_reading_juav(float pressure);

    public static void npsSensorInitGyro(double time) {
        nps_sensor_gyro_init_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_gyro_init_juav(double time);

    public static void npsSensorInitAccel(double time) {
        nps_sensor_accel_init_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_accel_init_juav(double time);

    public static void npsSensorInitMag(double time) {
        nps_sensor_mag_init_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_mag_init_juav(double time);

    public static void npsSensorInitBaro(double time) {
        nps_sensor_baro_init_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_baro_init_juav(double time);

    public static void npsSensorInitGps(double time) {
        nps_sensor_gps_init_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_gps_init_juav(double time);

    public static void npsSensorFdmCopyGyro(double time) {
        nps_sensor_gyro_run_step_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_gyro_run_step_juav(double time);

    public static void npsSensorFdmCopyAccel(double time) {
        nps_sensor_accel_run_step_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_accel_run_step_juav(double time);

    public static void npsSensorFdmCopyMag(double time) {
        nps_sensor_mag_run_step_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_mag_run_step_juav(double time);

    public static void npsSensorFdmCopyBaro(double time) {
        nps_sensor_baro_run_step_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_baro_run_step_juav(double time);

    public static void npsSensorFdmCopyGps(double time) {
        nps_sensor_gps_run_step_juav(time);
    }
    @Import
    @GodGiven
    private static native void nps_sensor_gps_run_step_juav(double time);

    public static void npsSensorFeedStepGyro() {
        npsGyroFeedStepJuav();
    }
    @Import
    @GodGiven
    private static native void npsGyroFeedStepJuav();

    public static void npsSensorFeedStepAccel() {
        npsAccelFeedStepJuav();
    }
    @Import
    @GodGiven
    private static native void npsAccelFeedStepJuav();

    public static void npsSensorFeedStepMag() {
        npsMagFeedStepJuav();
    }
    @Import
    @GodGiven
    private static native void npsMagFeedStepJuav();

    public static void npsSensorFeedStepBaro() {
        npsBaroFeedStepJuav();
    }
    @Import
    @GodGiven
    private static native void npsBaroFeedStepJuav();

    public static void npsSensorFeedStepGps() {
        npsGpsFeedStepJuav();
    }
    @Import
    @GodGiven
    private static native void npsGpsFeedStepJuav();

    public static void mainPeriodicJuavAutopilotPrior() {
        main_periodic_juav_autopilot_prior();
    }
    @Import
    @GodGiven
    private static native void main_periodic_juav_autopilot_prior();

    public static boolean sysTimeCheckAndAckTimerMainPeriodicJuav() {
        return sys_time_check_and_ack_timer_main_periodic_juav();
    }
    @Import
    @GodGiven
    private static native boolean sys_time_check_and_ack_timer_main_periodic_juav();

    public static void handlePeriodicTasksFollowingMainPeriodicJuav() {
        handle_periodic_tasks_following_main_periodic_juav();
    }
    @Import
    @GodGiven
    private static native void handle_periodic_tasks_following_main_periodic_juav();

    public static void autopilotPeriodicPriorJuav() {
        autopilot_periodic_prior_juav();
    }
    @Import
    @GodGiven
    private static native void autopilot_periodic_prior_juav();

    public static boolean isAutopilotModeApModeKillJuav() {
        return is_autopilot_mode_ap_mode_kill_juav();
    }
    @Import
    @GodGiven
    private static native boolean is_autopilot_mode_ap_mode_kill_juav();

    public static void autopilotPeriodicPostJuav() {
        autopilot_periodic_post_juav();
    }
    @Import
    @GodGiven
    private static native void autopilot_periodic_post_juav();

    public static void guidanceHRunJuav(boolean inFlight) {
        guidance_h_run_juav(inFlight);
    }
    @Import
    @GodGiven
    private static native void guidance_h_run_juav(boolean inFlight);

    public static boolean getAutopilotInFlightJuav() {
        return get_autopilot_in_flight_juav();
    }
    @Import
    @GodGiven
    private static native boolean get_autopilot_in_flight_juav();

    public static boolean runStabilizationAttitudeRunJuav() {
        return run_stabilization_attitude_run_juav();
    }
    @Import
    @GodGiven
    private static native boolean run_stabilization_attitude_run_juav();

    public static void guidanceHRunNativeTestJuav(boolean inFlight) {
        guidance_h_run_native_test_juav(inFlight);
    }
    @Import
    @GodGiven
    private static native void guidance_h_run_native_test_juav(boolean inFlight);

    public static void mainPeriodicJuavAutopilotPost() {
        main_periodic_juav_autopilot_post();
    }
    @Import
    @GodGiven
    private static native void main_periodic_juav_autopilot_post();

    public static void mainPeriodicJuavTest() {
        main_periodic_juav_test();
    }
    @Import
    @GodGiven
    private static native void main_periodic_juav_test();

    public static int stateGetNedToBodyQuatIQi() {
        return get_stateGetNedToBodyQuat_i_Qi_juav();
    }
    @Import
    @GodGiven
    private static native int get_stateGetNedToBodyQuat_i_Qi_juav();

    public static int stateGetNedToBodyQuatIQx() {
        return get_stateGetNedToBodyQuat_i_Qx_juav();
    }
    @Import
    @GodGiven
    private static native int get_stateGetNedToBodyQuat_i_Qx_juav();

    public static int stateGetNedToBodyQuatIQy() {
        return get_stateGetNedToBodyQuat_i_Qy_juav();
    }
    @Import
    @GodGiven
    private static native int get_stateGetNedToBodyQuat_i_Qy_juav();

    public static int stateGetNedToBodyQuatIQz() {
        return get_stateGetNedToBodyQuat_i_Qz_juav();
    }
    @Import
    @GodGiven
    private static native int get_stateGetNedToBodyQuat_i_Qz_juav();

    public static int stateGetBodyRatesIP() {
        return get_stateGetBodyRates_i_p_juav();
    }
    @Import
    @GodGiven
    private static native int get_stateGetBodyRates_i_p_juav();

    public static int stateGetBodyRatesIQ() {
        return get_stateGetBodyRates_i_q_juav();
    }
    @Import
    @GodGiven
    private static native int get_stateGetBodyRates_i_q_juav();

    public static int stateGetBodyRatesIR() {
        return get_stateGetBodyRates_i_r_juav();
    }
    @Import
    @GodGiven
    private static native int get_stateGetBodyRates_i_r_juav();

    public static void attitudeRefQuatIntUpdateJuav(float dt) {
        attitude_ref_quat_int_update_juav(dt);
    }
    @Import
    @GodGiven
    private static native void attitude_ref_quat_int_update_juav(float dt);

    public static int getStabilizationAttSumErrQuatQi() {
        return get_stabilization_att_sum_err_quat_i_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_att_sum_err_quat_i_juav();

    public static int getStabilizationAttSumErrQuatQx() {
        return get_stabilization_att_sum_err_quat_x_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_att_sum_err_quat_x_juav();

    public static int getStabilizationAttSumErrQuatQy() {
        return get_stabilization_att_sum_err_quat_y_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_att_sum_err_quat_y_juav();

    public static int getStabilizationAttSumErrQuatQz() {
        return get_stabilization_att_sum_err_quat_z_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_att_sum_err_quat_z_juav();

    public static int getAttitudeRefQuatQi() {
        return get_att_ref_quat_i_quat_qi_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_quat_qi_juav();

    public static int getAttitudeRefQuatQx() {
        return get_att_ref_quat_i_quat_qx_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_quat_qx_juav();

    public static int getAttitudeRefQuatQy() {
        return get_att_ref_quat_i_quat_qy_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_quat_qy_juav();

    public static int getAttitudeRefQuatQz() {
        return get_att_ref_quat_i_quat_qz_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_quat_qz_juav();

    public static int getAttitudeRefRateP() {
        return get_att_ref_quat_i_rate_p_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_rate_p_juav();

    public static int getAttitudeRefRateQ() {
        return get_att_ref_quat_i_rate_q_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_rate_q_juav();

    public static int getAttitudeRefRateR() {
        return get_att_ref_quat_i_rate_r_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_rate_r_juav();

    public static int getAttitudeGainPX() {
        return get_stabilization_gains_p_x_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_p_x_juav();

    public static int getAttitudeGainPY() {
        return get_stabilization_gains_p_y_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_p_y_juav();

    public static int getAttitudeGainPZ() {
        return get_stabilization_gains_p_z_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_p_z_juav();

    public static int getAttitudeGainDX() {
        return get_stabilization_gains_d_x_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_d_x_juav();

    public static int getAttitudeGainDY() {
        return get_stabilization_gains_d_y_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_d_y_juav();

    public static int getAttitudeGainDZ() {
        return get_stabilization_gains_d_z_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_d_z_juav();

    public static int getAttitudeGainDdX() {
        return get_stabilization_gains_dd_x_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_dd_x_juav();

    public static int getAttitudeGainDdY() {
        return get_stabilization_gains_dd_y_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_dd_y_juav();

    public static int getAttitudeGainDdZ() {
        return get_stabilization_gains_dd_z_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_dd_z_juav();

    public static int getAttitudeGainIX() {
        return get_stabilization_gains_i_x_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_i_x_juav();

    public static int getAttitudeGainIY() {
        return get_stabilization_gains_i_y_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_i_y_juav();

    public static int getAttitudeGainIZ() {
        return get_stabilization_gains_i_z_juav();
    }
    @Import
    @GodGiven
    private static native int get_stabilization_gains_i_z_juav();

    public static int getAttitudeRefAccelP() {
        return get_att_ref_quat_i_accel_p_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_accel_p_juav();

    public static int getAttitudeRefAccelQ() {
        return get_att_ref_quat_i_accel_q_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_accel_q_juav();

    public static int getAttitudeRefAccelR() {
        return get_att_ref_quat_i_accel_r_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_accel_r_juav();

    public static void setStabilizationAttSumErrQuatQi(int qi) {
        set_stabilization_att_sum_err_quat_i_juav(qi);
    }
    @Import
    @GodGiven
    private static native void set_stabilization_att_sum_err_quat_i_juav(int qi);

    public static void setStabilizationAttSumErrQuatQx(int qx) {
        set_stabilization_att_sum_err_quat_x_juav(qx);
    }
    @Import
    @GodGiven
    private static native void set_stabilization_att_sum_err_quat_x_juav(int qx);

    public static void setStabilizationAttSumErrQuatQy(int qy) {
        set_stabilization_att_sum_err_quat_y_juav(qy);
    }
    @Import
    @GodGiven
    private static native void set_stabilization_att_sum_err_quat_y_juav(int qy);

    public static void setStabilizationAttSumErrQuatQz(int qz) {
        set_stabilization_att_sum_err_quat_z_juav(qz);
    }
    @Import
    @GodGiven
    private static native void set_stabilization_att_sum_err_quat_z_juav(int qz);

    public static void setAttRefQuatIQuatQi(int qi) {
        set_att_ref_quat_i_quat_qi_juav(qi);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_quat_qi_juav(int qi);

    public static void setAttRefQuatIQuatQx(int qx) {
        set_att_ref_quat_i_quat_qx_juav(qx);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_quat_qx_juav(int qx);

    public static void setAttRefQuatIQuatQy(int qy) {
        set_att_ref_quat_i_quat_qy_juav(qy);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_quat_qy_juav(int qy);

    public static void setAttRefQuatIQuatQz(int qz) {
        set_att_ref_quat_i_quat_qz_juav(qz);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_quat_qz_juav(int qz);

    public static void setAttRefQuatIRateP(int p) {
        set_att_ref_quat_i_rate_p_juav(p);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_rate_p_juav(int p);

    public static void setAttRefQuatIRateQ(int q) {
        set_att_ref_quat_i_rate_q_juav(q);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_rate_q_juav(int q);

    public static void setAttRefQuatIRateR(int r) {
        set_att_ref_quat_i_rate_r_juav(r);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_rate_r_juav(int r);

    public static void setAttRefQuatIAccelP(int p) {
        set_att_ref_quat_i_accel_p_juav(p);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_accel_p_juav(int p);

    public static void setAttRefQuatIAccelQ(int q) {
        set_att_ref_quat_i_accel_q_juav(q);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_accel_q_juav(int q);

    public static void setAttRefQuatIAccelR(int r) {
        set_att_ref_quat_i_accel_r_juav(r);
    }
    @Import
    @GodGiven
    private static native void set_att_ref_quat_i_accel_r_juav(int r);

    public static void setStabilizationCommands(int yaw, int pitch, int roll) {
        set_stabilization_cmd(yaw, pitch, roll);
    }
    @Import
    @GodGiven
    private static native void set_stabilization_cmd(int yaw, int pitch, int roll);

    /////////////
    public static int stateGetPositionNedIX() {
        return juav_fiji_stateGetPositionNedIX();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetPositionNedIX();

    public static int stateGetPositionNedIY() {
        return juav_fiji_stateGetPositionNedIY();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetPositionNedIY();

    public static int stateGetPositionNedIZ() {
        return juav_fiji_stateGetPositionNedIZ();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetPositionNedIZ();

    public static int stateGetSpeedNedIX() {
        return juav_fiji_stateGetSpeedNedIX();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetSpeedNedIX();

    public static int stateGetSpeedNedIY() {
        return juav_fiji_stateGetSpeedNedIY();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetSpeedNedIY();

    public static int stateGetSpeedNedIZ() {
        return juav_fiji_stateGetSpeedNedIZ();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetSpeedNedIZ();

    public static int stateGetNedToBodyRMatI_0() {
        return juav_fiji_stateGetNedToBodyRMatI_10();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyRMatI_10();

    public static int stateGetNedToBodyRMatI_1() {
        return juav_fiji_stateGetNedToBodyRMatI_11();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyRMatI_11();

    public static int stateGetNedToBodyRMatI_2() {
        return juav_fiji_stateGetNedToBodyRMatI_12();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyRMatI_12();

    public static int stateGetNedToBodyRMatI_3() {
        return juav_fiji_stateGetNedToBodyRMatI_13();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyRMatI_13();

    public static int stateGetNedToBodyRMatI_4() {
        return juav_fiji_stateGetNedToBodyRMatI_14();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyRMatI_14();

    public static int stateGetNedToBodyRMatI_5() {
        return juav_fiji_stateGetNedToBodyRMatI_15();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyRMatI_15();

    public static int stateGetNedToBodyRMatI_6() {
        return juav_fiji_stateGetNedToBodyRMatI_16();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyRMatI_16();

    public static int stateGetNedToBodyRMatI_7() {
        return juav_fiji_stateGetNedToBodyRMatI_17();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyRMatI_17();

    public static int stateGetNedToBodyRMatI_8() {
        return juav_fiji_stateGetNedToBodyRMatI_18();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyRMatI_18();

    public static int stateGetNedToBodyEulersIPsiInt() {
        return juav_fiji_stateGetNedToBodyEulersIPsiInt();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyEulersIPsiInt();

    public static int stateGetNedToBodyEulersITheataInt() {
        return juav_fiji_stateGetNedToBodyEulersITheataInt();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyEulersITheataInt();

    public static int stateGetNedToBodyEulersIPhiInt() {
        return juav_fiji_stateGetNedToBodyEulersIPhiInt();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetNedToBodyEulersIPhiInt();

    public static float stateGetNedToBodyEulersIPsiFloat() {
        return juav_fiji_stateGetNedToBodyEulersIPsiFloat();
    }
    @Import
    @GodGiven
    private static native float juav_fiji_stateGetNedToBodyEulersIPsiFloat();

    public static float stateGetNedToBodyEulersITheataFloat() {
        return juav_fiji_stateGetNedToBodyEulersITheataFloat();
    }
    @Import
    @GodGiven
    private static native float juav_fiji_stateGetNedToBodyEulersITheataFloat();

    public static float stateGetNedToBodyEulersIPhiFloat() {
        return juav_fiji_stateGetNedToBodyEulersIPhiFloat();
    }
    @Import
    @GodGiven
    private static native float juav_fiji_stateGetNedToBodyEulersIPhiFloat();

    public static int stateGetAccelNedIX() {
        return juav_fiji_stateGetAccelNedIX();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetAccelNedIX();

    public static int stateGetAccelNedIY() {
    return juav_fiji_stateGetAccelNedIY();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetAccelNedIY();

    public static int stateGetAccelNedIZ() {
        return juav_fiji_stateGetAccelNedIZ();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_stateGetAccelNedIZ();

    public static void navPeriodicTask() {
        nav_periodic_task();
    }
    @Import
    @GodGiven
    private static native void nav_periodic_task();

    public static void navHome() {
        nav_home();
    }
    @Import
    @GodGiven
    private static native void nav_home();

    public static void computeDist2ToHome() {
        compute_dist2_to_home();
    }
    @Import
    @GodGiven
    private static native void compute_dist2_to_home();

    public static float stateGetSpeedNedFX() {
        return juav_fiji_stateGetSpeedNedFX();
    }
    @Import
    @GodGiven
    private static native float juav_fiji_stateGetSpeedNedFX();

    public static float stateGetSpeedNedFY() {
        return juav_fiji_stateGetSpeedNedFY();
    }
    @Import
    @GodGiven
    private static native float juav_fiji_stateGetSpeedNedFY();

    public static float stateGetSpeedNedFZ() {
        return juav_fiji_stateGetSpeedNedFZ();
    }
    @Import
    @GodGiven
    private static native float juav_fiji_stateGetSpeedNedFZ();

    public static float stateGetAccelNedFX() {
        return juav_fiji_stateGetAccelNedFX();
    }
    @Import
    @GodGiven
    private static native float juav_fiji_stateGetAccelNedFX();

    public static float stateGetAccelNedFY() {
        return juav_fiji_stateGetAccelNedFY();
    }
    @Import
    @GodGiven
    private static native float juav_fiji_stateGetAccelNedFY();

    public static float stateGetAccelNedFZ() {
        return juav_fiji_stateGetAccelNedFZ();
    }
    @Import
    @GodGiven
    private static native float juav_fiji_stateGetAccelNedFZ();

    public static boolean stateIsAttitudeValidCall() {
        return stateIsAttitudeValid();
    }
    @Import
    @GodGiven
    private static native boolean stateIsAttitudeValid();

    public static void navInit() {
        nav_init();
    }
    @Import
    @GodGiven
    private static native void nav_init();

    //    Telemetry registeration
    public static void periodicTelemetrySendHoverLoop() {
        juav_register_periodic_telemetry_send_hover_loop();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_hover_loop();

    public static void periodicTelemetrySendHref() {
        juav_register_periodic_telemetry_send_href();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_href();

    public static void periodicTelemetrySendGh() {
        juav_register_periodic_telemetry_send_gh();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_gh();

    public static void periodicTelemetrySendTuneHover() {
        juav_register_periodic_telemetry_send_tune_hover();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_tune_hover();

    public static void periodicTelemetrySendAutopilotVersion() {
        juav_register_periodic_telemetry_send_autopilot_version();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_autopilot_version();

    public static void periodicTelemetrySendAlive() {
        juav_register_periodic_telemetry_send_alive();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_alive();

    public static void periodicTelemetrySendStatus() {
        juav_register_periodic_telemetry_send_status();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_status();

    public static void periodicTelemetrySendAttitude() {
        juav_register_periodic_telemetry_send_attitude();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_attitude();

    public static void periodicTelemetrySendEnergy() {
        juav_register_periodic_telemetry_send_energy();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_energy();

    public static void periodicTelemetrySendFp() {
        juav_register_periodic_telemetry_send_fp();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_fp();

    public static void periodicTelemetrySendRotorcraftCmd() {
        juav_register_periodic_telemetry_send_rotorcraft_cmd();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_rotorcraft_cmd();

    public static void periodicTelemetrySendDlValue() {
        juav_register_periodic_telemetry_send_dl_value();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_dl_value();

    public static void periodicTelemetrySendActuators() {
        juav_register_periodic_telemetry_send_actuators();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_actuators();

    public static void periodicTelemetrySendRc() {
        juav_register_periodic_telemetry_send_rc();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_rc();

    public static void periodicTelemetrySendRotorcraftRc() {
        juav_register_periodic_telemetry_send_rotorcraft_rc();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_rotorcraft_rc();

    public static void periodicTelemetrySendVertLoop() {
        juav_register_periodic_telemetry_send_vert_loop();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_vert_loop();

    public static void periodicTelemetrySendTuneVert() {
        juav_register_periodic_telemetry_send_tune_vert();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_tune_vert();

    public static void periodicTelemetrySendAtt() {
        juav_register_periodic_telemetry_send_att();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_att();

    public static void periodicTelemetrySendAttRef() {
        juav_register_periodic_telemetry_send_att_ref();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_att_ref();

    public static void periodicTelemetrySendAhrsRefQuat() {
        juav_register_periodic_telemetry_send_ahrs_ref_quat();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_ahrs_ref_quat();

    public static void periodicTelemetrySendRate() {
        juav_register_periodic_telemetry_send_rate();
    }
    @Import
    @GodGiven
    private static native void juav_register_periodic_telemetry_send_rate();

    public static int getRadioControlValue(int index) {
        return juav_get_radio_control_value(index);
    }
    @Import
    @GodGiven
    private static native int juav_get_radio_control_value(int index);

    public static int getNavigationCarrotX() {
        return juav_fiji_getNavigationCarrotX();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_getNavigationCarrotX();

    public static int getNavigationCarrotY() {
        return juav_fiji_getNavigationCarrotY();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_getNavigationCarrotY();

    public static int getNavigationCarrotZ() {
        return juav_fiji_getNavigationCarrotZ();
    }
    @Import
    @GodGiven
    private static native int juav_fiji_getNavigationCarrotZ();

    public static boolean npsAutopilotRunRadioStepAndShouldRunMainEvent(double time) {
        return nps_autopilot_run_step_radio_juav_no_main_event(time);
    }
    @Import
    @GodGiven
    private static native boolean nps_autopilot_run_step_radio_juav_no_main_event(double time);

    public static short getRadioControlStatus() {
        return juav_get_radio_control_status();
    }
    @Import
    @GodGiven
    private static native short juav_get_radio_control_status();

    public static float navigationGetDist2ToHome() {
        return juav_get_dist2_to_home();
    }
    @Import
    @GodGiven
    private static native float juav_get_dist2_to_home();

    public static boolean navigationGetTooFarFromHome() {
        return juav_get_too_far_from_home();
    }
    @Import
    @GodGiven
    private static native boolean juav_get_too_far_from_home();

    public static short navigationGetHorizontalMode() {
        return juav_get_horizontal_mode();
    }
    @Import
    @GodGiven
    private static native short juav_get_horizontal_mode();

    public static int navigationGetNavRoll() {
        return juav_get_nav_roll();
    }
    @Import
    @GodGiven
    private static native int juav_get_nav_roll();

    public static int navigationGetNavPitch() {
        return juav_get_nav_pitch();
    }
    @Import
    @GodGiven
    private static native int juav_get_nav_pitch();

    public static int navigationGetNavHeading() {
        return juav_get_nav_heading();
    }
    @Import
    @GodGiven
    private static native int juav_get_nav_heading();

    public static void navigationSetNavHeading(int newHeading) {
        juav_set_nav_heading(newHeading);
    }
    @Import
    @GodGiven
    private static native void juav_set_nav_heading(int newHeading);

    public static int navigationGetNavVerticleMode() {
        return juav_get_vertical_mode();
    }
    @Import
    @GodGiven
    private static native int juav_get_vertical_mode();

    public static int navigationGetNavClimb() {
        return juav_get_nav_climb();
    }
    @Import
    @GodGiven
    private static native int juav_get_nav_climb();

    public static int navigationGetNavFlightAltitude() {
        return juav_get_nav_flight_altitude();
    }
    @Import
    @GodGiven
    private static native int juav_get_nav_flight_altitude();

    public static int navigationGetNavThrottle() {
        return juav_get_nav_throttle();
    }
    @Import
    @GodGiven
    private static native int juav_get_nav_throttle();

    public static void setGuidanceHMode(short newMode) {
        juav_set_guidance_h_mode(newMode);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_mode(short newMode);

    public static void setGuidanceVMode(short newMode) {
        juav_set_guidance_v_mode(newMode);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_v_mode(short newMode);

    public static void setAutopilotMode(short new_autopilot_mode) {
        juav_set_autopilot_mode(new_autopilot_mode);
    }
    @Import
    @GodGiven
    private static native void juav_set_autopilot_mode(short new_autopilot_mode);

    public static void setGuidanceVRcDeltaT(int newValue) {
        juav_set_guidance_v_rc_delta_t(newValue);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_v_rc_delta_t(int newValue);

    public static void setGuidanceVRcZdSp(int newValue) {
        juav_set_guidance_v_rc_zd_sp(newValue);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_v_rc_zd_sp(int newValue);

    public static void setStabilizationCommand(int commandIndex, int command) {
        juav_set_stabilization_command(commandIndex, command);
    }
    @Import
    @GodGiven
    private static native void juav_set_stabilization_command(int commandIndex, int command);

    public static boolean getAutopilotMotorsOnJuav() {
        return juav_get_autopilot_motors_on();
    }
    @Import
    @GodGiven
    private static native boolean juav_get_autopilot_motors_on();

    public static void juavSetAutopilotMotorsOn(boolean b) {
        juav_set_autopilot_motors_on(b);
    }
    @Import
    @GodGiven
    private static native void juav_set_autopilot_motors_on(boolean b);

    public static void juavSetAutopilotCheckMotorStatus(int ordinal) {
        juav_set_autopilot_check_motor_status(ordinal);
    }
    @Import
    @GodGiven
    private static native void juav_set_autopilot_check_motor_status(int ordinal);

    public static int juavGetAutopilotCheckMotorStatus() {
        return juav_get_autopilot_check_motor_status();
    }
    @Import
    @GodGiven
    private static native int juav_get_autopilot_check_motor_status();

    public static void juavSetAutopilotMotorsOnCounter(int newCount) {
        juav_set_autopilot_motors_on_counter(newCount);
    }
    @Import
    @GodGiven
    private static native void juav_set_autopilot_motors_on_counter(int newCount);

    public static int juavGetAutopilotMotorsOnCounter() {
        return juav_get_autopilot_motors_on_counter();
    }
    @Import
    @GodGiven
    private static native int juav_get_autopilot_motors_on_counter();

    public static int getStabilizationCmd(int index) {
        return juav_get_stabilization_cmd(index);
    }
    @Import
    @GodGiven
    private static native int juav_get_stabilization_cmd(int index);

    public static void setStabilizationCmd(int index, int newValue) {
        juav_set_stabilization_cmd(index, newValue);
    }
    @Import
    @GodGiven
    private static native void juav_set_stabilization_cmd(int index, int newValue);

    public static int getHorizantialGuidanceSetPointPosX() {
        return juav_get_guidance_h_sp_pos_x();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_sp_pos_x();

    public static int getHorizantialGuidanceSetPointPosY() {
        return juav_get_guidance_h_sp_pos_y();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_sp_pos_y();

    public static void setHorizantialGuidanceSetPointPosX(int x) {
        juav_set_guidance_h_sp_pos_x(x);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_sp_pos_x(int x);

    public static void setHorizantialGuidanceSetPointPosY(int y) {
        juav_set_guidance_h_sp_pos_y(y);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_sp_pos_y(int y);

    public static int getHorizantialGuidanceSetPointSpeedX() {
        return juav_get_guidance_h_sp_speed_x();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_sp_speed_x();

    public static int getHorizantialGuidanceSetPointSpeedY() {
        return juav_get_guidance_h_sp_speed_y();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_sp_speed_y();

    public static int getHorizantialGuidanceReferencePosX() {
        return juav_get_guidance_h_ref_pos_x();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_ref_pos_x();

    public static int getHorizantialGuidanceReferencePosY() {
        return juav_get_guidance_h_ref_pos_y();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_ref_pos_y();

    public static void setHorizantialGuidanceReferencePosX(int x) {
        juav_set_guidance_h_ref_pos_x(x);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_ref_pos_x(int x);

    public static void setHorizantialGuidanceReferencePosY(int y) {
        juav_set_guidance_h_ref_pos_y(y);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_ref_pos_y(int y);

    public static int getHorizantialGuidanceReferenceSpeedX() {
        return juav_get_guidance_h_ref_speed_x();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_ref_speed_x();

    public static int getHorizantialGuidanceReferenceSpeedY() {
        return juav_get_guidance_h_ref_speed_y();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_ref_speed_y();

    public static void setHorizantialGuidanceReferenceSpeedX(int x) {
        juav_set_guidance_h_ref_speed_x(x);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_ref_speed_x(int x);

    public static void setHorizantialGuidanceReferenceSpeedY(int y) {
        juav_set_guidance_h_ref_speed_y(y);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_ref_speed_y(int y);

    public static int getHorizantialGuidanceReferenceAccelX() {
        return juav_get_guidance_h_ref_accel_x();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_ref_accel_x();

    public static int getHorizantialGuidanceReferenceAccelY() {
        return juav_get_guidance_h_ref_accel_y();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_ref_accel_y();

    public static void setHorizantialGuidanceReferenceAccelX(int x) {
        juav_set_guidance_h_ref_accel_x(x);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_ref_accel_x(int x);

    public static void setHorizantialGuidanceReferenceAccelY(int y) {
        juav_set_guidance_h_ref_accel_y(y);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_ref_accel_y(int y);

    public static int getHorizantialGuidanceHeading() {
        return juav_get_guidance_h_sp_heading();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_sp_heading();

    public static void setHorizantialGuidanceHeading(int newHeading) {
        juav_set_guidance_h_sp_heading(newHeading);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_sp_heading(int newHeading);

    public static void setGuidanceHCmdEarthX(int x) {
        juav_set_guidance_h_cmd_earth_x(x);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_cmd_earth_x(int x);

    public static void setGuidanceHCmdEarthY(int y) {
        juav_set_guidance_h_cmd_earth_y(y);
    }
    @Import
    @GodGiven
    private static native void juav_set_guidance_h_cmd_earth_y(int y);

    public static int getGuidanceHCmdEarthX() {
        return juav_get_guidance_h_cmd_earth_x();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_cmd_earth_x();

    public static int getGuidanceHCmdEarthY() {
        return juav_get_guidance_h_cmd_earth_y();
    }
    @Import
    @GodGiven
    private static native int juav_get_guidance_h_cmd_earth_y();

    public static int getStabilizationAttSpQuatQi() {
        return juav_get_stab_att_sp_quat_qi();
    }
    @Import
    @GodGiven
    private static native int juav_get_stab_att_sp_quat_qi();

    public static int getStabilizationAttSpQuatQx() {
        return juav_get_stab_att_sp_quat_qx();
    }
    @Import
    @GodGiven
    private static native int juav_get_stab_att_sp_quat_qx();

    public static int getStabilizationAttSpQuatQy() {
        return juav_get_stab_att_sp_quat_qy();
    }
    @Import
    @GodGiven
    private static native int juav_get_stab_att_sp_quat_qy();

    public static int getStabilizationAttSpQuatQz() {
        return juav_get_stab_att_sp_quat_qz();
    }
    @Import
    @GodGiven
    private static native int juav_get_stab_att_sp_quat_qz();

    public static void setStabilizationAttSpQuatQi(int qi) {
        juav_set_stab_att_sp_quat_qi(qi);
    }
    @Import
    @GodGiven
    private static native void juav_set_stab_att_sp_quat_qi(int qi);

    public static void setStabilizationAttSpQuatQx(int qx) {
        juav_set_stab_att_sp_quat_qx(qx);
    }
    @Import
    @GodGiven
    private static native void juav_set_stab_att_sp_quat_qx(int qx);

    public static void setStabilizationAttSpQuatQy(int qy) {
        juav_set_stab_att_sp_quat_qy(qy);
    }
    @Import
    @GodGiven
    private static native void juav_set_stab_att_sp_quat_qy(int qy);

    public static void setStabilizationAttSpQuatQz(int qz) {
        juav_set_stab_att_sp_quat_qz(qz);
    }
    @Import
    @GodGiven
    private static native void juav_set_stab_att_sp_quat_qz(int qz);

    public static void juavAutopilotPeriodic() {
        autopilot_periodic();
    }
    @Import
    @GodGiven
    private static native void autopilot_periodic();

    public static void setAutopilotGroundDetected(boolean b) {
        juav_fiji_setAutopilotGroundDetected(b);
    }
    @Import
    @GodGiven
    private static native void juav_fiji_setAutopilotGroundDetected(boolean b);

    public static void setAutopilotDetectGroundOnce(boolean b) {
        juav_fiji_setAutopilotDetectGroundOnce(b);
    }
    @Import
    @GodGiven
    private static native void juav_fiji_setAutopilotDetectGroundOnce(boolean b);

    public static void guidanceVRunJuav(boolean autopilotInFlight) {
        guidance_v_run_native_test_juav(autopilotInFlight);
    }
    @Import
    @GodGiven
    private static native void guidance_v_run_native_test_juav(boolean autopilotInFlight);

    public static void guidance_h_mode_changed_native(short new_mode) {
        juav_guidance_h_mode_changed(new_mode);
    }
    @Import
    @GodGiven
    private static native void juav_guidance_h_mode_changed(short new_mode);

    public static void guidance_v_mode_changed_native(short new_mode) {
        juav_guidance_v_mode_changed(new_mode);
    }
    @Import
    @GodGiven
    private static native void juav_guidance_v_mode_changed(short new_mode);

    public static void guidanceHReadRc(boolean in_flight) {
        juav_guidance_h_read_rc(in_flight);
    }
    @Import
    @GodGiven
    private static native void juav_guidance_h_read_rc(boolean in_flight);

    public static void setAutopilotModeNativeLogic(short new_autopilot_mode) {
        juav_autopilot_set_mode_native(new_autopilot_mode);
    }
    @Import
    @GodGiven
    private static native void juav_autopilot_set_mode_native(short new_autopilot_mode);

    public static void juavStabilizationAttitudeRunNative(boolean enable_integrator) {
        juav_stabilization_attitude_run_native(enable_integrator);
    }
    @Import
    @GodGiven
    private static native void juav_stabilization_attitude_run_native(boolean enable_integrator);

    public static void setStabilizationAttitudeSetRpySetpointI(int psi, int phi, int theta) {
        juav_stabilization_attitude_set_rpy_setpoint_i_native(psi,phi,theta);
    }
    @Import
    @GodGiven
    private static native void juav_stabilization_attitude_set_rpy_setpoint_i_native(int psi, int phi, int theta);

    public static void guidanceHUpdateReference() {
        juav_guidance_h_update_reference_native();
    }
    @Import
    @GodGiven
    private static native void juav_guidance_h_update_reference_native();

    public static void guidanceHNavEnter() {
        juav_guidance_h_nav_enter_native();
    }
    @Import
    @GodGiven
    private static native void juav_guidance_h_nav_enter_native();

    public static void guidanceHTrajRun(boolean inFlight) {
        juav_guidance_h_traj_run_native(inFlight);
    }
    @Import
    @GodGiven
    private static native void juav_guidance_h_traj_run_native(boolean inFlight);


    public static int getAttitudeRefEulerPsi() {
        return get_att_ref_quat_i_euler_psi_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_euler_psi_juav();

    public static int getAttitudeRefEulerPhi() {
        return get_att_ref_quat_i_euler_phi_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_euler_phi_juav();

    public static int getAttitudeRefEulerTheta() {
        return get_att_ref_quat_i_euler_theta_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_euler_theta_juav();

    public static int getAttitudeRefModelTwoZetaOmegaP() {
        return get_att_ref_quat_i_model_two_zeta_omega_p_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_model_two_zeta_omega_p_juav();

    public static int getAttitudeRefModelTwoZetaOmegaQ() {
        return get_att_ref_quat_i_model_two_zeta_omega_q_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_model_two_zeta_omega_q_juav();

    public static int getAttitudeRefModelTwoZetaOmegaR() {
        return get_att_ref_quat_i_model_two_zeta_omega_r_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_model_two_zeta_omega_r_juav();

    public static int getAttitudeRefModelTwoOmega2P() {
        return get_att_ref_quat_i_model_two_omega2_p_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_model_two_omega2_p_juav();

    public static int getAttitudeRefModelTwoOmega2Q() {
        return get_att_ref_quat_i_model_two_omega2_q_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_model_two_omega2_q_juav();

    public static int getAttitudeRefModelTwoOmega2R() {
        return get_att_ref_quat_i_model_two_omega2_r_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_model_two_omega2_r_juav();

    public static float getAttitudeRefModelZetaP() {
        return get_att_ref_quat_i_model_zeta_p_juav();
    }
    @Import
    @GodGiven
    private static native float get_att_ref_quat_i_model_zeta_p_juav();

    public static float getAttitudeRefModelZetaQ() {
        return get_att_ref_quat_i_model_zeta_q_juav();
    }
    @Import
    @GodGiven
    private static native float get_att_ref_quat_i_model_zeta_q_juav();

    public static float getAttitudeRefModelZetaR() {
        return get_att_ref_quat_i_model_zeta_r_juav();
    }
    @Import
    @GodGiven
    private static native float get_att_ref_quat_i_model_zeta_r_juav();

    public static float getAttitudeRefModelOmegaP() {
        return get_att_ref_quat_i_model_omega_p_juav();
    }
    @Import
    @GodGiven
    private static native float get_att_ref_quat_i_model_omega_p_juav();

    public static float getAttitudeRefModelOmegaQ() {
        return get_att_ref_quat_i_model_omega_q_juav();
    }
    @Import
    @GodGiven
    private static native float get_att_ref_quat_i_model_omega_q_juav();

    public static float getAttitudeRefModelOmegaR() {
        return get_att_ref_quat_i_model_omega_r_juav();
    }
    @Import
    @GodGiven
    private static native float get_att_ref_quat_i_model_omega_r_juav();

    public static int getAttitudeRefSaturationMaxAccelP() {
        return get_att_ref_quat_i_saturation_max_accel_p_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_saturation_max_accel_p_juav();

    public static int getAttitudeRefSaturationMaxAccelQ() {
        return get_att_ref_quat_i_saturation_max_accel_q_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_saturation_max_accel_q_juav();

    public static int getAttitudeRefSaturationMaxAccelR() {
        return get_att_ref_quat_i_saturation_max_accel_r_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_saturation_max_accel_r_juav();

    public static int getAttitudeRefSaturationMaxRateP() {
        return get_att_ref_quat_i_saturation_max_rate_p_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_saturation_max_rate_p_juav();

    public static int getAttitudeRefSaturationMaxRateQ() {
        return get_att_ref_quat_i_saturation_max_rate_q_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_saturation_max_rate_q_juav();

    public static int getAttitudeRefSaturationMaxRateR() {
        return get_att_ref_quat_i_saturation_max_rate_r_juav();
    }
    @Import
    @GodGiven
    private static native int get_att_ref_quat_i_saturation_max_rate_r_juav();

    public static void guidanceHRunJuavCaseModeNav(boolean inFlight) {
        juav_guidance_h_mode_nav_case_in_run(inFlight);
    }
    @Import
    @GodGiven
    private static native void juav_guidance_h_mode_nav_case_in_run(boolean inFlight);

}

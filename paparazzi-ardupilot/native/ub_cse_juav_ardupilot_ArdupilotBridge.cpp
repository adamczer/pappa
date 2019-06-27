#define CONFIG_HAL_BOARD HAL_BOARD_LINUX
#define CONFIG_HAL_BOARD_SUBTYPE HAL_BOARD_SUBTYPE_LINUX_ERLEBRAIN2
#include <jni.h>
#include "ub_cse_juav_ardupilot_ArdupilotBridge.h"

#include <AP_HAL/AP_HAL.h>
#include <AP_HAL_Linux/HAL_Linux_Class.h>
#include <AP_BoardConfig/AP_BoardConfig.h>
#include <AP_InertialSensor/AP_InertialSensor.h>
#include <AP_Compass/AP_Compass_AK8963.h>
#include <AP_HAL/RCOutput.h>
#include <AP_SerialManager/AP_SerialManager.h>
#include <AP_HAL_Linux/UARTDriver.h>
#include <AP_GPS/AP_GPS.h>

#include <unistd.h>             // for usleep
#include <iostream>    // C++ standard IO header
using namespace std;

const AP_HAL::HAL &hal = AP_HAL::get_HAL();

// inertial sensor section vv
#define INERTIAL_FREQ   (uint16_t)      100000  //frequency of inertial sensor readings
#define ACCEL_PICK              (uint8_t)       0       // which accelerometer to read from
#define GYRO_PICK               (uint8_t)       0       // which gyrometer to read from
#define TEMP_PICK               (uint8_t)       0       // which temp meter to read from
#define COMPASS_PICK    (uint8_t)       0       // which compass sensor to read from //TODO use get_primary() instead
uint16_t accel_count;
uint16_t gyro_count;

Vector3f accel_val;
Vector3f gyro_val;
float temp_val;

Vector3f accel_offsets;
Vector3f accel_scale;
Vector3f gyro_offsets;

void update_inertial();         // function to udpdate the global inertial sensor variables (blocking if data is not ready)

// inertial sensor section ^^

// rcout defs section vv
#define RCOUT_MOTORS_NUM        (uint8_t)       4       // number of motos TODO maybe read from lib
#define RCOUT_MIN_VAL           (uint16_t)      1000    // minimum value written to motors at start up

uint16_t rcout_pwm[RCOUT_MOTORS_NUM];

bool init_rcout();                      // function to initialize motors
void update_rcout(uint8_t channel, uint16_t val); // function to update global value for one motor
void flush_rcout();                     // function to send pwm values for all motors
// rcout defs section ^^

// Base initialization
JNIEXPORT jboolean JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_init_1base
  (JNIEnv *, jclass) {
    hal.gpio->init();
    hal.rcout->init();
    //  hal.rcin->init();
    hal.uartA->begin(115200);
    hal.uartE->begin(115200);
    hal.uartF->begin(115200);
    hal.uartG->begin(115200);
    hal.analogin->init();
    AP_BoardConfig *boardconfig = AP_BoardConfig::get_instance();
    boardconfig->init();
    init_rcout();
    return true;
}
// rcout init ensures ESCs do not skwak
bool init_rcout() {
cout << "============================" << endl;
        cout << "Initializing rcout motors   " << endl;
        cout << "----------------------------" << endl;
        for (uint16_t i = 0; i < RCOUT_MOTORS_NUM; i++) {
        hal.rcout->enable_ch(i);
                printf("motor[%d]....ENABLED!\n",i);
    }
        for (uint16_t i = 0; i < RCOUT_MOTORS_NUM; i++) {
                rcout_pwm[i] = RCOUT_MIN_VAL;
                printf("motor[%d] set to: %d\n",i, rcout_pwm[i]);
    }
        flush_rcout();

        cout << "----------------------------" << endl;
        cout << endl;
        //TODO ever false?
        return true;
}
void update_rcout(uint8_t channel, uint16_t val) {
        rcout_pwm[channel]=val;
        return;
}

void flush_rcout() {
        hal.rcout->cork();
        for (uint16_t i = 0; i < RCOUT_MOTORS_NUM; i++) {
        hal.rcout->write(i, rcout_pwm[i]);
    }
        hal.rcout->push();
}
// Inertial initialization ie Accel, Gyro, Temp
JNIEXPORT jboolean JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_init_1inertial
  (JNIEnv *, jclass) {
	cout << "============================" << endl;
        cout << "Initializing inertial sensor" << endl;
        cout << "----------------------------" << endl;

        AP_InertialSensor &ins = *AP_InertialSensor::get_instance();
        ins.init(INERTIAL_FREQ);
    accel_count = ins.get_accel_count();
    gyro_count = ins.get_gyro_count();

    //flushing out previous data?
    ins.update();
    // display number of detected accels/gyros
    cout << "Number of detected accels : "<< accel_count << endl;
    cout << "Number of detected gyros  : "<< gyro_count << endl;

        for (uint16_t i = 0; i < accel_count; i++){
                cout << "accel["<<i<<"].....";
            if (!ins.get_accel_health(i)) { cout << "not healthy!"; } else { cout << "OK!" ; }
                cout << endl;
        }
        for (uint16_t i = 0; i < gyro_count; i++){
                cout << "gyro["<<i<<"]......";
            if (!ins.get_gyro_health(i)) { cout << "not healthy!"; } else { cout << "OK!" ; }
                cout << endl;
        }

        const Vector3f &accel_offsets_local = ins.get_accel_offsets();
        const Vector3f &accel_scale_local = ins.get_accel_scale();
        const Vector3f &gyro_offsets_local = ins.get_gyro_offsets();

	accel_offsets = accel_offsets_local;
        accel_scale = accel_scale_local;
        gyro_offsets = gyro_offsets_local;

        update_inertial();
        //display_inertial();

        cout << "----------------------------" << endl;
        cout << endl;
        return true;
}

void update_inertial() {
        AP_InertialSensor &ins = *AP_InertialSensor::get_instance();
        ins.wait_for_sample();
        ins.update();
        accel_val = ins.get_accel(ACCEL_PICK);
        gyro_val  = ins.get_gyro(GYRO_PICK);
        temp_val  = ins.get_temperature(TEMP_PICK);
        return;
}

JNIEXPORT void JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_update_1inertial
  (JNIEnv *, jclass) {
	update_inertial();
	return;
}

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getFdmBodyAccelX
  (JNIEnv *, jclass) {
	return (double)accel_val.x;
}

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getFdmBodyAccelY
  (JNIEnv *, jclass) {
	return (double)accel_val.y;
}

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getFdmBodyAccelZ
  (JNIEnv *, jclass) {
	return (double)accel_val.z;
}

// GPS vv

#define GPS_DEV_PATH    "/dev/ttyS0"
#define GPS_MSG_BUFF_SIZE       (uint8_t)       8

Location gps_val;
int gps_num_sats;
int gps_status;
long unsigned int gps_last_msg;

JNIEXPORT jboolean JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_init_1gps
  (JNIEnv *, jclass) {
    cout << "============================" << endl;
    cout << "Initializing GPS sensor     " << endl;
    cout << "----------------------------" << endl;

    AP_SerialManager *serial_manager = AP_SerialManager::get_instance();
    ((Linux::UARTDriver*)hal.HAL::uartB)->set_device_path(GPS_DEV_PATH);
    serial_manager->init();
    AP_GPS &gps = AP::gps();
    gps.init(*serial_manager);

    ((Linux::UARTDriver*)hal.HAL::uartB)->_timer_tick();
    gps.update();

    cout << "----------------------------" << endl;
    cout << endl;
    //TODO ever false?
    return true;
}

JNIEXPORT void JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_updateGps
  (JNIEnv *, jclass) {
    AP_GPS &gps = AP::gps();
    gps.update();
    const Location &loc = gps.location();
    gps_val = loc;
    gps_num_sats = gps.num_sats();
    gps_status = gps.status();
    gps_last_msg = gps.last_message_time_ms();
    return;
}

JNIEXPORT jlong JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getGpsReadTime
  (JNIEnv *, jclass) {
    return gps_last_msg;
}

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getGpsLatitude
  (JNIEnv *, jclass) {
    return gps_val.lat;
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getGpsLongitude
  (JNIEnv *, jclass) {
    return gps_val.lng;
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getGpsAltitude
  (JNIEnv *, jclass) {
    return gps_val.alt * 0.01d;
  }


// Gyroscope


JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getFdmBodyInertialRotVelP
  (JNIEnv *, jclass) {
    return gyro_val.x;
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getFdmBodyInertialRotVelQ
  (JNIEnv *, jclass) {
    return gyro_val.y;
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getFdmBodyInertialRotVelR
  (JNIEnv *, jclass) {
    return gyro_val.z;
  }


// compass magnomiter

// compass sensor section
uint16_t compass_count;

float compass_heading;
Vector3f compass_val;

float compass_min[3];
float compass_max[3];
float compass_offset[3];

void update_compass();


JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getMagX
  (JNIEnv *, jclass) {
    return compass_val.x;
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getMagY
  (JNIEnv *, jclass) {
    return compass_val.y;
  }

JNIEXPORT jdouble JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_getMagZ
  (JNIEnv *, jclass) {
    return compass_val.z;
  }

JNIEXPORT jboolean JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_init_1compass
  (JNIEnv *, jclass) {
    cout << "============================" << endl;
    cout << "Initializing compass sensor " << endl;
    cout << "----------------------------" << endl;

    Compass *compass = Compass::get_singleton();
    compass->init();

    compass_count = compass->get_count();
    cout << "Number of detected compass: "<< compass_count << endl;

    compass->set_and_save_offsets(0, 0, 0, 0);
    compass->set_declination(ToRad(0.0f));
    cout << "compass......";
    if (!compass->healthy()) { cout << "not healthy!"; } else { cout << "OK!" ; }
    cout << endl;

    update_compass();

    cout << "----------------------------" << endl;
    cout << endl;
    //TODO ever false?
    return true;
  }

JNIEXPORT void JNICALL Java_ub_cse_juav_ardupilot_ArdupilotBridge_updateCompass
  (JNIEnv *, jclass) {
  update_compass();
  }

void update_compass() {
        Compass *compass = Compass::get_singleton();

        compass->read();

        Matrix3f dcm_matrix;
        // use roll = 0, pitch = 0 for this example
        dcm_matrix.from_euler(0, 0, 0);
        compass_heading = compass->calculate_heading(dcm_matrix, COMPASS_PICK);
        const Vector3f &mag = compass->get_field(COMPASS_PICK);
        compass_val = mag;
        // capture min
        compass_min[0] = MIN(mag.x, compass_min[0]);
        compass_min[1] = MIN(mag.y, compass_min[1]);
        compass_min[2] = MIN(mag.z, compass_min[2]);
        // capture max
        compass_max[0] = MAX(mag.x, compass_max[0]);
        compass_max[1] = MAX(mag.y, compass_max[1]);
        compass_max[2] = MAX(mag.z, compass_max[2]);
        // calculate offsets
        compass_offset[0] = -(compass_max[0] + compass_min[0]) / 2;
        compass_offset[1] = -(compass_max[1] + compass_min[1]) / 2;
        compass_offset[2] = -(compass_max[2] + compass_min[2]) / 2;
}
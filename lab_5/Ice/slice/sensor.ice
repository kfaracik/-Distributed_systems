#ifndef SENSOR_ICE
#define SENSOR_ICE

module SensorDemo {

    exception NoInput {};
    exception EmptySequenceError {};
    exception IllegalArgumentException {};

    interface SensorIce {
        float getSensorTemp(long id) throws IllegalArgumentException;
        float getSensorHumidity(long id) throws IllegalArgumentException;
        float getSensorBrightness(long id) throws IllegalArgumentException;
    };
};

#endif
#ifndef GLOBAL_ICE
#define GLOBAL_ICE

module GlobalDemo {
    enum deviceType { FRIDGE, HEATER, SENSOR };
//    sequence<int, deviceType> seqOfDevices;

    exception NoInput {};
    exception EmptySequenceError {};

    interface GlobalIce {
//        seqOfDevices getDevices();
        string getDevices();
    };
};

#endif
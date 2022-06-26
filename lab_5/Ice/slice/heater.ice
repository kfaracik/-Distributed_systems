#ifndef HEATER_ICE
#define HEATER_ICE

module HeaterDemo {

    exception NoInput {};
    exception EmptySequenceError {};
    exception IllegalArgumentException {};

    struct Temperature {
        long id;
        float temp;
    };

    struct TemperaturePlan {
        long id;
        float temp;
        string startTime;
        string endTime;
    };

    interface HeaterIce {
        void setHeat(Temperature data) throws IllegalArgumentException;
        void setHeatingPlan(TemperaturePlan data) throws IllegalArgumentException;
        string getHeaterState(long id) throws IllegalArgumentException;
    };
};

#endif
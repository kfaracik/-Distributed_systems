#ifndef FRIDGE_ICE
#define FRIDGE_ICE

module FridgeDemo {

    exception NoInput {};
    exception EmptySequenceError {};
    exception IllegalArgumentException {};

    struct Temperature {
        long id;
        int temp;
    };

    interface FridgeIce {
        void setFridgeTemp(Temperature data) throws IllegalArgumentException;
        void setFreezerTemp(Temperature data) throws IllegalArgumentException;
        float getFridgeFullness(long id) throws IllegalArgumentException;
    };
};

#endif
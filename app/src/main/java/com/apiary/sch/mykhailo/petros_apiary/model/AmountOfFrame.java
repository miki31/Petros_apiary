package com.apiary.sch.mykhailo.petros_apiary.model;

import java.security.KeyStore;

/**
 * Created by User on 17.08.2018.
 */

public enum AmountOfFrame {

    EIGHT(8),
    TEN(10),
    TWELVE(12);

    private int mAmountOfFrame;

    AmountOfFrame(int amountOfFrame){
        mAmountOfFrame = amountOfFrame;
    }

    public int getAmountOfFrame() {
        return mAmountOfFrame;
    }
}

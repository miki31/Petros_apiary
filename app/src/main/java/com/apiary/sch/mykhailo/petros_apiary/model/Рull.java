package com.apiary.sch.mykhailo.petros_apiary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 17.08.2018.
 */

// корпус. мітить в собі певну кількість рамок, але визначену максимальну конструкцією вулика
public class Рull {

    private String mName;
    private List<Frame> mFrames;
    private final AmountOfFrame mMaxAmountOfFrame;


    public Рull(String name, AmountOfFrame amountOfFrame) {
        mMaxAmountOfFrame = amountOfFrame;
        mFrames = new ArrayList<Frame>();
        mName = name;
    }

    public boolean addFrame(Frame frame){
        if (mFrames.size() == mMaxAmountOfFrame.getAmountOfFrame()){
            return false;
        }

        if (frame == null){
            return false;
        }

        mFrames.add(frame);
        return true;
    }

    public String getName() {
        return mName;
    }


}

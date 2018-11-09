package com.apiary.sch.mykhailo.petros_apiary.model.part_hives;

import java.util.ArrayList;
import java.util.List;

import com.apiary.sch.mykhailo.petros_apiary.model.Рull;

/**
 * Created by ServerUser on 17.08.2018.
 */

/*
    тіло вуликаю
    містить в собі кілька корпусів
        напевно стан і наявність конструкції має бути

 */

public class Body {

    private List<Рull> mPulls;

    public Body() {
        mPulls = new ArrayList<Рull>();
    }
}

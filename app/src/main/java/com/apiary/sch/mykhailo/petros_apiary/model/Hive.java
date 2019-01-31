package com.apiary.sch.mykhailo.petros_apiary.model;

import com.apiary.sch.mykhailo.petros_apiary.model.part_hives.Body;
import com.apiary.sch.mykhailo.petros_apiary.model.part_hives.Bottom;
import com.apiary.sch.mykhailo.petros_apiary.model.part_hives.Roof;
import com.apiary.sch.mykhailo.petros_apiary.model.part_hives.UnderRoof;

/**
 * Created by ServerUser on 17.08.2018.
 */

/* вулик.
всі вулики багатокорпусні.вулик може містити кілька корпусів і задавати йому кількість рамок
складається з:
    даху
    піддашника
    тіла
        кілька корпусів
    дна
 */
public class Hive {

    private String mName;

    private Roof mRoof;// дах
    private UnderRoof mUnderRoof;// піддашник
    private Body mBody;// тіло що містить кілька корпусів
    private Bottom mBottom;// дно вулика
    private final AmountOfFrame mMaxAmountOfFrame;


    public Hive(AmountOfFrame maxAmountOfFrame) {
        mMaxAmountOfFrame = maxAmountOfFrame;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}

package com.apiary.sch.mykhailo.petros_apiary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 17.08.2018.
 */
/*
    Бджолине підприємство - на графіку МОЇ ПАСІКИ
    містить в собі кілька пасік
 */
public class BeeCompany {

    private long mIdCompany;
    private long mIdDirector;   // ID директора з таблиці директорів.
    private String mNameCompany;
    private String mRegion;
    private List<Apiary> mApiaries;

    public BeeCompany() {
        mApiaries = new ArrayList<>();
    }

    private BeeCompany(BeeCompany company){
        mIdCompany = company.mIdCompany;
        mIdDirector = company.mIdDirector;
        mNameCompany = company.mNameCompany;
        mRegion = company.mRegion;
        mApiaries = new ArrayList<Apiary>();
    }

    public long getIdCompany() {
        return mIdCompany;
    }

    public void setIdCompany(long idCompany) {
        mIdCompany = idCompany;
    }

    public String getNameCompany() {
        return mNameCompany;
    }

    public void setNameCompany(String nameCompany) {
        mNameCompany = nameCompany;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public List<Apiary> getApiaries() {
        return mApiaries;
    }

    public void setApiaries(List<Apiary> apiaries) {
        mApiaries = apiaries;
    }

    public long getIdDirector() {
        return mIdDirector;
    }

    public void setIdDirector(long idDirector) {
        mIdDirector = idDirector;
    }

    public BeeCompany copy(){
        BeeCompany beeCompany = new BeeCompany(this);
        return beeCompany;
    }
}

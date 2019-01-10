package com.apiary.sch.mykhailo.petros_apiary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ServerUser on 17.08.2018.
 */
/*
    ПАСІКА - складається з кількох точків
 */
public class Apiary {

    private long mIdApiary;
    private long mIdDirector;
    private long mIdCompany;
    private String mNameApiary;
    private String mRegion;
    private String mNearestSettlement;  // найближче поселення (населений пункт)
    //     latitude and longitude -- широта і довгота з карти гугл
    private double mLatitude; // широта
    private double mLongitude; // довгота

    private List<Point> mPoints;  // Point -- точки

    public Apiary() {
        mPoints = new ArrayList<Point>();
    }

    private Apiary(Apiary apiary){
        mIdApiary = apiary.mIdApiary;
        mIdDirector = apiary.mIdDirector;
        mIdCompany = apiary.mIdCompany;
        mNameApiary = apiary.mNameApiary;
        mRegion = apiary.mRegion;
        mNearestSettlement = apiary.mNearestSettlement;
        mLatitude = apiary.mLatitude;
        mLongitude = apiary.mLongitude;
        mPoints = new ArrayList<Point>();
    }

    public long getIdApiary() {
        return mIdApiary;
    }

    public void setIdApiary(long idApiary) {
        mIdApiary = idApiary;
    }

    public long getIdDirector() {
        return mIdDirector;
    }

    public void setIdDirector(long idDirector) {
        mIdDirector = idDirector;
    }

    public long getIdCompany() {
        return mIdCompany;
    }

    public void setIdCompany(long idCompany) {
        mIdCompany = idCompany;
    }

    public String getNameApiary() {
        return mNameApiary;
    }

    public void setNameApiary(String nameApiary) {
        mNameApiary = nameApiary;
    }

    public String getRegion() {
        return mRegion;
    }

    public void setRegion(String region) {
        mRegion = region;
    }

    public List<Point> getPoints() {
        return mPoints;
    }

    public void setPoints(List<Point> points) {
        mPoints = points;
    }

    public String getNearestSettlement() {
        return mNearestSettlement;
    }

    public void setNearestSettlement(String nearestSettlement) {
        mNearestSettlement = nearestSettlement;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public Apiary copy(){
        Apiary apiary = new Apiary(this);
        return apiary;
    }
}

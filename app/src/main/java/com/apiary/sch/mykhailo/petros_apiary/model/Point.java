package com.apiary.sch.mykhailo.petros_apiary.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ServerUser on 17.08.2018.
 */
/*
    точок - напевно потрібно змінити назву класу!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    містить в собі кілька вуликів
 */
public class Point {

    private long mIdPoint;
    private long mIdApiary;
    private long mIdDirector;
    private String mName;
    private String mPosition;


    private List<Hive> mHives;

    public Point() {
        mHives = new ArrayList<Hive>();
    }

    private Point(Point point){
        mIdPoint = point.mIdPoint;
        mIdApiary = point.mIdApiary;
        mIdDirector = point.mIdDirector;
        mName = point.mName;
        mPosition = point.mPosition;

        mHives = new ArrayList<Hive>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public long getIdPoint() {
        return mIdPoint;
    }

    public void setIdPoint(long idPoint) {
        mIdPoint = idPoint;
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

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    public List<Hive> getHives() {
        return mHives;
    }

    public void setHives(List<Hive> hives) {
        mHives = hives;
    }

    public Point copy(){
        Point point = new Point(this);
        return point;
    }
}

package com.apiary.sch.mykhailo.petros_apiary;

/**
 * Created by User on 31.08.2018.
 */

public class TestRemind {
    private String mTitle;
    private String mId;
    private String mDate;


    @Override
    public String toString() {
        return mTitle;
    }


    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }
}

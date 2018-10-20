package com.apiary.sch.mykhailo.petros_apiary.model.persone;

import java.util.Calendar;

/**
 * Created by User on 04.09.2018.
 */
/*
    ПРАЦІВНИК - розширення персони.
    Працівник має обмежений функціонал.
    Через його ID доступ до БД
 */
public class Worker extends Person {
    private Calendar mAcceptedAtWork;
    private Calendar mDismissedFromWork;
    private int mIdWorker;
    private Calendar mDateOfBirth;
    private int mRating;

    public Worker() {
        super();
    }

    private Worker(Calendar acceptedAtWork,
                  Calendar dismissedFromWork,
                  int idWorker,
                  Calendar dateOfBirth,
                  int rating) {
        mAcceptedAtWork = acceptedAtWork;
        mDismissedFromWork = dismissedFromWork;
        mIdWorker = idWorker;
        mDateOfBirth = dateOfBirth;
        mRating = rating;
    }

    private Worker(Worker worker) {
        super((Person) worker);
        mAcceptedAtWork = worker.getAcceptedAtWork();
        mDismissedFromWork = worker.getDismissedFromWork();
        mIdWorker = worker.getIdWorker();
        mDateOfBirth = worker.getDateOfBirth();
        mRating = worker.getRating();
    }

    public Calendar getDateOfBirth() {
        return mDateOfBirth;
    }

    public void setDateOfBirth(Calendar dateOfBirth) {
        mDateOfBirth = dateOfBirth;
    }

    public int getRating() {
        return mRating;
    }

    public void setRating(int rating) {
        mRating = rating;
    }

    public Calendar getDismissedFromWork() {
        return mDismissedFromWork;
    }

    public void setDismissedFromWork(Calendar dismissedFromWork) {
        mDismissedFromWork = dismissedFromWork;
    }

    public int getIdWorker() {
        return mIdWorker;
    }

    public void setIdWorker(int idWorker) {
        mIdWorker = idWorker;
    }

    public Calendar getAcceptedAtWork() {
        return mAcceptedAtWork;
    }

    public void setAcceptedAtWork(Calendar acceptedAtWork) {
        mAcceptedAtWork = acceptedAtWork;
    }

    @Override
    public Worker copy() {
        Worker newWorker = new Worker(this);

        return newWorker;
    }
}

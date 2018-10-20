package com.apiary.sch.mykhailo.petros_apiary.model.persone;

/**
 * Created by User on 04.09.2018.
 */
/*
    ДИРЕКТОР - розширення персони.
    Директор має розширений функціонал.
    Через його ID доступ до БД
    Доступ керування персоналом працівників, також доступ до функцій працівника
 */
public class Director extends Person {
    private int mNumberOfWorkers;

    public Director() {
    }

    protected Director(Director director) {
        super((Person) director);
        mNumberOfWorkers = director.getNumberOfWorkers();
    }

    public int getNumberOfWorkers() {
        return mNumberOfWorkers;
    }

    public void setNumberOfWorkers(int numberOfWorkers) {
        mNumberOfWorkers = numberOfWorkers;
    }

    @Override
    public Director copy() {
        Director newDirector = (Director) super.copy();
//        Director director = (Director) person;

        newDirector.setNumberOfWorkers(this.getNumberOfWorkers());

        return newDirector;
    }
}

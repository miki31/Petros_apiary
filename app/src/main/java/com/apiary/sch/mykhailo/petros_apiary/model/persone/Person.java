package com.apiary.sch.mykhailo.petros_apiary.model.persone;

/**
 * Created by User on 04.09.2018.
 */
/*
    ПЕРСОНА - основні дані про особу
 */
public class Person {
    private String mName;
    private String mSurname;
    private String mEmail;
    private String mPass;
    private int mIdDirector;
    private String mPhone;

    public Person() {
    }

    private Person(String name,
                  String surname,
                  String email,
                  String pass,
                  int idDirector,
                  String phone) {
        mName = name;
        mSurname = surname;
        mEmail = email;
        mPass = pass;
        mIdDirector = idDirector;
        mPhone = phone;
    }

    protected Person(Person person) {
        mName = person.getName();
        mSurname = person.getSurname();
        mEmail = person.getEmail();
        mPass = person.getPass();
        mIdDirector = person.getIdDirector();
        mPhone = person.getPhone();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSurname() {
        return mSurname;
    }

    public void setSurname(String surname) {
        mSurname = surname;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPass() {
        return mPass;
    }

    public void setPass(String pass) {
        mPass = pass;
    }

    public int getIdDirector() {
        return mIdDirector;
    }

    public void setIdDirector(int idDirector) {
        mIdDirector = idDirector;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public Person copy(){
        Person newPerson = new Person();

        newPerson.setName(this.getName());
        newPerson.setSurname(this.getSurname());
        newPerson.setEmail(this.getEmail());
        newPerson.setPass(this.getPass());
        newPerson.setIdDirector(this.getIdDirector());
        newPerson.setPhone(this.getPhone());

        return newPerson;
    }
}

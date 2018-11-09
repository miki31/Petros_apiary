package com.apiary.sch.mykhailo.petros_apiary.servAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

/**
 * Created by ServerUser on 02.11.2018.
 */

public class ServerUser {
    /*
    для працівників і директорів я пропоную використовувати константи
    Якщо є інші варіанти то кажи
         8765 4321 | 8765 4321 | 8765 4321 | 8765 4321
         0000 0000   0000 0000   0000 0000   0000 0001 = 1; => Директор
         0000 0000   0000 0000   0000 0000   0000 0010 = 3; => Працівник
     */
    public static final int DIRECTOR = 0x00000001;
    public static final int WORKER = 0x00000003;

    @SerializedName("login")
    @Expose
    private String mLogin;

    @SerializedName("pass")
    @Expose
    private String mPass;

    // Person - загальна інфа
    private int mId;
    private String mName;
    private String mSurname;
    private String mEmail;
//    private String mPass;
    private String mPhone;
    private int mPosition; // => посада (директор, працівник)

    // Worker - інфа про працівника
    private Calendar mAcceptedAtWork;
    private Calendar mDismissedFromWork;
    private int mIdDirector; // якщо директор то або 0 або mIdDirector=mId
    private Calendar mDateOfBirth;
    private int mRating;

    // Director - інфа про директора
    private int mNumberOfWorkers; // скільки працівників в компанії (можливо і не треба)

}

package com.apiary.sch.mykhailo.petros_apiary.local_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by User on 03.09.2018.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATA_BASE_NAME = ApiarisDatabaseSchema.NAME_DATA_BASE;

    private Context mContext;

    public DBHelper(Context context) {
        super(context, DATA_BASE_NAME, null, VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "
                + ApiarisDatabaseSchema.SavedUser.NAME_TABLE_SAVED_USER + "( "
                + ApiarisDatabaseSchema.SavedUser.Cols._ID + " integer primary key autoincrement, "
                + ApiarisDatabaseSchema.SavedUser.Cols.DIRECTOR_ID + " integer not null, "
                + ApiarisDatabaseSchema.SavedUser.Cols.WORKER_ID + " integer not null)"
        );

        db.execSQL("create table "
                + ApiarisDatabaseSchema.TableOfDirectors.NAME_TABLE_OF_DIRECTORS + "( "
                + ApiarisDatabaseSchema.TableOfDirectors.Cols._ID + " integer primary key autoincrement, "
                + ApiarisDatabaseSchema.TableOfDirectors.Cols.NAME + " text not null, "
                + ApiarisDatabaseSchema.TableOfDirectors.Cols.SURNAME + " text not null, "
                + ApiarisDatabaseSchema.TableOfDirectors.Cols.PHONE + " text not null unique, "
                + ApiarisDatabaseSchema.TableOfDirectors.Cols.EMAIL + " text not null unique, "
                + ApiarisDatabaseSchema.TableOfDirectors.Cols.PASS + " text not null, "
                + ApiarisDatabaseSchema.TableOfDirectors.Cols.NUMBER_OF_WORKERS + " integer not null)"
        );

        db.execSQL("create table "
                + ApiarisDatabaseSchema.TableOfWorkers.NAME_TABLE_OF_WORKERS + "( "
                + ApiarisDatabaseSchema.TableOfWorkers.Cols._ID + " integer primary key autoincrement, "
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.DIRECTOR_ID + " integer not null, "
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.NAME + " text not null, "
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.SURNAME + " text not null, "
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.PHONE + " text not null unique, "
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.EMAIL + " text not null unique, "
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.PASS + " text not null, "
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.DATE_OF_BIRTH + " text not null, "
                // час в мілісекундах від 1970 року
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.ACCEPTED_AT_WORK + " text not null, "
                // час в мілісекундах від 1970 року
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.DISMISSED_FROM_WORK + " text, "
                + ApiarisDatabaseSchema.TableOfWorkers.Cols.RATING + " integer not null)"
        );

        db.execSQL("create table "
                + ApiarisDatabaseSchema.TableCompanies.NAME_TABLE_COMPANIES + "( "
                + ApiarisDatabaseSchema.TableCompanies.Cols._ID + " integer primary key autoincrement, "
                + ApiarisDatabaseSchema.TableCompanies.Cols.DIRECTOR_ID + " integer not null, "
                + ApiarisDatabaseSchema.TableCompanies.Cols.NAME_COMPANY + " text not null, "
                + ApiarisDatabaseSchema.TableCompanies.Cols.REGION + " text not null)"
        );

        db.execSQL("create table "
                + ApiarisDatabaseSchema.TableApiaries.NAME_TABLE_APIARIES + "( "
                + ApiarisDatabaseSchema.TableApiaries.Cols._ID + " integer primary key autoincrement, "
                + ApiarisDatabaseSchema.TableApiaries.Cols.DIRECTOR_ID + " integer not null, "
                + ApiarisDatabaseSchema.TableApiaries.Cols.COMPANY_ID + " integer not null, "
                + ApiarisDatabaseSchema.TableApiaries.Cols.NAME_APIARY + " text not null, "
                + ApiarisDatabaseSchema.TableApiaries.Cols.REGION + " text not null, "
                + ApiarisDatabaseSchema.TableApiaries.Cols.NEAREST_SETTLEMENT + " text not null, "
                + ApiarisDatabaseSchema.TableApiaries.Cols.LATITUDE + " real, "
                + ApiarisDatabaseSchema.TableApiaries.Cols.LONGITUDE + " real)"
        );

        createFirstDataUsers(db);
        createDataInSavedUser(db);
        createFirstDataCompanies(db);
        createFirstDataApiaries(db);
    }

    private void createDataInSavedUser(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

//        values.put(ApiarisDatabaseSchema.SavedUser.Cols.DIRECTOR_ID, User.NOT_DIRECTOR);
//        values.put(ApiarisDatabaseSchema.SavedUser.Cols.WORKER_ID, User.NOT_WORKER);

        values.put(ApiarisDatabaseSchema.SavedUser.Cols.DIRECTOR_ID, 1);
        values.put(ApiarisDatabaseSchema.SavedUser.Cols.WORKER_ID, 1);

        db.insert(ApiarisDatabaseSchema.SavedUser.NAME_TABLE_SAVED_USER,
                null, values);
    }

    private void createFirstDataUsers(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableOfDirectors.Cols.NAME, "Misha");
        values.put(ApiarisDatabaseSchema.TableOfDirectors.Cols.SURNAME, "Schepanskiy");
        values.put(ApiarisDatabaseSchema.TableOfDirectors.Cols.PHONE, "+380966506160");
        values.put(ApiarisDatabaseSchema.TableOfDirectors.Cols.EMAIL, "mykhailo.sch@gmail.com");
        values.put(ApiarisDatabaseSchema.TableOfDirectors.Cols.PASS, "miki31");
        values.put(ApiarisDatabaseSchema.TableOfDirectors.Cols.NUMBER_OF_WORKERS, 1);

        db.insert(ApiarisDatabaseSchema.TableOfDirectors.NAME_TABLE_OF_DIRECTORS,
                null, values);

        values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.DIRECTOR_ID, 1);
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.NAME, "Masha");
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.SURNAME, "Shchepanska");
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.PHONE, "+380966516161");
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.EMAIL, "MashkaPS@gmail.com");
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.PASS, "masha93");

        Calendar dateOfBirth = new GregorianCalendar(1993, 7, 3);
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.DATE_OF_BIRTH,
                String.valueOf(dateOfBirth.getTimeInMillis()));

        // час в мілісекундах від 1970 року
        Calendar acceptedAtWork = new GregorianCalendar(2016, 6, 9);
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.ACCEPTED_AT_WORK,
                String.valueOf(acceptedAtWork.getTimeInMillis()));
//        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.DISMISSED_FROM_WORK,
//                String.valueOf(acceptedAtWork.getTimeInMillis()));
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.DISMISSED_FROM_WORK, (String) null);

        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.RATING, 100);

        db.insert(ApiarisDatabaseSchema.TableOfWorkers.NAME_TABLE_OF_WORKERS,
                null, values);
    }

    private void createFirstDataCompanies(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableCompanies.Cols.DIRECTOR_ID, 1);
        values.put(ApiarisDatabaseSchema.TableCompanies.Cols.NAME_COMPANY, "First CompanyAccess");
        values.put(ApiarisDatabaseSchema.TableCompanies.Cols.REGION, "Івано-Франківськ");

        db.insert(ApiarisDatabaseSchema.TableCompanies.NAME_TABLE_COMPANIES,
                null, values);

        values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableCompanies.Cols.DIRECTOR_ID, 1);
        values.put(ApiarisDatabaseSchema.TableCompanies.Cols.NAME_COMPANY, "Second CompanyAccess");
        values.put(ApiarisDatabaseSchema.TableCompanies.Cols.REGION, "Львів");

        db.insert(ApiarisDatabaseSchema.TableCompanies.NAME_TABLE_COMPANIES,
                null, values);
    }

    private void createFirstDataApiaries(SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.DIRECTOR_ID, 1);
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.COMPANY_ID, 1);
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NAME_APIARY, "Пасіка___1");
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.REGION, "Надвірна");
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NEAREST_SETTLEMENT, "Верхній Майдан_1");

        db.insert(ApiarisDatabaseSchema.TableApiaries.NAME_TABLE_APIARIES,
                null, values);

        values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.DIRECTOR_ID, 1);
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.COMPANY_ID, 1);
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NAME_APIARY, "Пасіка___2");
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.REGION, "Надвірна");
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NEAREST_SETTLEMENT, "Верхній Майдан_2");

        db.insert(ApiarisDatabaseSchema.TableApiaries.NAME_TABLE_APIARIES,
                null, values);

        values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.DIRECTOR_ID, 1);
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.COMPANY_ID, 2);
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NAME_APIARY, "Пасіка___3");
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.REGION, "Коломия");
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NEAREST_SETTLEMENT, "1.Невідоме село гуцулів))");

        db.insert(ApiarisDatabaseSchema.TableApiaries.NAME_TABLE_APIARIES,
                null, values);

        values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.DIRECTOR_ID, 1);
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.COMPANY_ID, 2);
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NAME_APIARY, "Пасіка___4");
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.REGION, "Коломия");
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NEAREST_SETTLEMENT, "2.Невідоме село гуцулів))");

        db.insert(ApiarisDatabaseSchema.TableApiaries.NAME_TABLE_APIARIES,
                null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

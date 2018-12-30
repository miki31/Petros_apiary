package com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apiary.sch.mykhailo.petros_apiary.local_database.ApiariesCursorWrapper;
import com.apiary.sch.mykhailo.petros_apiary.local_database.ApiarisDatabaseSchema;
import com.apiary.sch.mykhailo.petros_apiary.local_database.DBHelper;
import com.apiary.sch.mykhailo.petros_apiary.model.Apiary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 18.10.2018.
 */

public class ApiaryAccess {
    private static ApiaryAccess sApiaryAccess;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private ApiaryAccess(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new DBHelper(mContext).getWritableDatabase();
    }

    public static ApiaryAccess get(Context context){
        if (sApiaryAccess == null){
            sApiaryAccess = new ApiaryAccess(context);
        }
        return sApiaryAccess;
    }

    public List<Apiary> getApiariesByCompanyId(long id){
        List<Apiary> apiaries = new ArrayList<>();

        String nameParameter = ApiarisDatabaseSchema.TableApiaries.Cols.COMPANY_ID;
        String parameter = String.valueOf(id);

        ApiariesCursorWrapper cursor = queryApiary(
                nameParameter + " = ?", new String[]{parameter}
        );

        try {
            if (cursor.getCount() != 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    apiaries.add(cursor.getApiary());
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }

        return apiaries;
    }

    public void addApiary(Apiary apiary){
        ContentValues values = getContentValues(apiary);
        mDatabase.insert(
                ApiarisDatabaseSchema.TableApiaries.NAME_TABLE_APIARIES,
                null, values);
    }

    private ApiariesCursorWrapper queryApiary(
            String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                ApiarisDatabaseSchema.TableApiaries.NAME_TABLE_APIARIES,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ApiariesCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Apiary apiary){
        ContentValues values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.COMPANY_ID,
                apiary.getIdCompany());
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.DIRECTOR_ID,
                apiary.getIdDirector());
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NAME_APIARY,
                apiary.getNameApiary());
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.REGION,
                apiary.getRegion());
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.NEAREST_SETTLEMENT,
                apiary.getNearestSettlement());
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.LATITUDE,
                apiary.getLatitude());
        values.put(ApiarisDatabaseSchema.TableApiaries.Cols.LONGITUDE,
                apiary.getLongitude());

        return values;
    }
}

package com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apiary.sch.mykhailo.petros_apiary.local_database.ApiariesCursorWrapper;
import com.apiary.sch.mykhailo.petros_apiary.local_database.ApiarisDatabaseSchema;
import com.apiary.sch.mykhailo.petros_apiary.local_database.DBHelper;
import com.apiary.sch.mykhailo.petros_apiary.model.BeeCompany;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 16.10.2018.
 */

public class CompanyAccess {
    private static CompanyAccess sCompanyAccess;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private CompanyAccess(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DBHelper(mContext).getWritableDatabase();
    }

    public static CompanyAccess get(Context context) {
        if (sCompanyAccess == null) {
            sCompanyAccess = new CompanyAccess(context);
        }
        return sCompanyAccess;
    }

    public List<BeeCompany> getCompaniesByDirectorID(long id) {
        List<BeeCompany> beeCompanies = new ArrayList<>();

        String nameParameter = ApiarisDatabaseSchema.TableCompanies.Cols.DIRECTOR_ID;
        String parameter = String.valueOf(id);

        ApiariesCursorWrapper cursor = queryCompany(
                nameParameter + " = ?", new String[]{parameter}
        );

        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    beeCompanies.add(cursor.getCompany());
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }

        return beeCompanies;
    }

    public long addCompany(BeeCompany company) {
        ContentValues values = getContentValues(company);
        return mDatabase.insert(
                ApiarisDatabaseSchema.TableCompanies.NAME_TABLE_COMPANIES,
                null, values);
    }

    public void updateCompany(BeeCompany company) {
        ContentValues values = getContentValues(company);
        mDatabase.update(ApiarisDatabaseSchema.TableCompanies.NAME_TABLE_COMPANIES,
                values,
                ApiarisDatabaseSchema.TableCompanies.Cols._ID + " = ?",
                new String[]{String.valueOf(company.getIdCompany())}
        );
    }

    private ApiariesCursorWrapper queryCompany(
            String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ApiarisDatabaseSchema.TableCompanies.NAME_TABLE_COMPANIES,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ApiariesCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(BeeCompany company) {
        ContentValues values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableCompanies.Cols.DIRECTOR_ID,
                company.getIdDirector());
        values.put(ApiarisDatabaseSchema.TableCompanies.Cols.NAME_COMPANY,
                company.getNameCompany());
        values.put(ApiarisDatabaseSchema.TableCompanies.Cols.REGION,
                company.getRegion());

        return values;
    }
}

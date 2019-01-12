package com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.apiary.sch.mykhailo.petros_apiary.local_database.ApiariesCursorWrapper;
import com.apiary.sch.mykhailo.petros_apiary.local_database.ApiarisDatabaseSchema;
import com.apiary.sch.mykhailo.petros_apiary.local_database.DBHelper;
import com.apiary.sch.mykhailo.petros_apiary.model.Apiary;
import com.apiary.sch.mykhailo.petros_apiary.model.Point;

import java.util.ArrayList;
import java.util.List;

public class PointAccess {
    private static PointAccess sPointAccess;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private PointAccess(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DBHelper(mContext).getWritableDatabase();
    }

    public static PointAccess get(Context context) {
        if (sPointAccess == null) {
            sPointAccess = new PointAccess(context);
        }
        return sPointAccess;
    }

    public List<Point> getPointsByApiaryId(long id) {
        List<Point> points = new ArrayList<>();

        String nameParameters = ApiarisDatabaseSchema.TablePoints.Cols.APIARY_ID;
        String parameter = String.valueOf(id);

        ApiariesCursorWrapper cursor = queryPoint(
                nameParameters + " = ?", new String[]{parameter});

        try {
            if (cursor.getCount() != 0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    points.add(cursor.getPoint());
                    cursor.moveToNext();
                }
            }
        } finally {
            cursor.close();
        }

        return points;
    }

    public long addPoint(Point point){
        ContentValues values = getContentValues(point);
        return mDatabase.insert(
                ApiarisDatabaseSchema.TablePoints.NAME_TABLE_POINTS,
                null, values);
    }

    public void updatePoint(Point point){
        ContentValues values = getContentValues(point);
        mDatabase.update(
                ApiarisDatabaseSchema.TablePoints.NAME_TABLE_POINTS,
                values,
                ApiarisDatabaseSchema.TablePoints.Cols._ID + " = ?",
                new String[]{String.valueOf(point.getIdPoint())}
        );
    }

    private ApiariesCursorWrapper queryPoint(
            String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ApiarisDatabaseSchema.TablePoints.NAME_TABLE_POINTS,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ApiariesCursorWrapper(cursor);
    }

    private ContentValues getContentValues(Point point) {
        ContentValues values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TablePoints.Cols.DIRECTOR_ID,
                point.getIdDirector());
        values.put(ApiarisDatabaseSchema.TablePoints.Cols.APIARY_ID,
                point.getIdApiary());
        values.put(ApiarisDatabaseSchema.TablePoints.Cols.NAME_POINT,
                point.getName());
        values.put(ApiarisDatabaseSchema.TablePoints.Cols.POSITION_POINT,
                point.getPosition());

        return values;
    }
}

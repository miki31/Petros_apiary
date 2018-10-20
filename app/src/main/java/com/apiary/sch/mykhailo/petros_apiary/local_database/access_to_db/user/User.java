package com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.apiary.sch.mykhailo.petros_apiary.local_database.ApiariesCursorWrapper;
import com.apiary.sch.mykhailo.petros_apiary.local_database.ApiarisDatabaseSchema;
import com.apiary.sch.mykhailo.petros_apiary.local_database.DBHelper;
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Director;
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Person;
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by User on 18.09.2018.
 */

public class User {
    // константи для Map
    public static final String ID_DIRECTOR = "id_director";
    public static final String ID_WORKER = "id_worker";

    // константи що вказуються в таблиці SavedUser якщо користувач не зберігається
    public static final long NOT_DIRECTOR = -1;
    public static final long NOT_WORKER = -1;


    private static User sUser;
    private Person mPersonUser;

    private Context mContext;

    private SQLiteDatabase mDatabase;

    private User(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new DBHelper(mContext).getWritableDatabase();
        mPersonUser = getPersonFromDB();
    }

    public static User get(Context context) {
        if (sUser == null) {
            sUser = new User(context);
        }
        return sUser;
    }

    public boolean isDirector(){
        if (mPersonUser instanceof Director)
            return true;

        return false;
    }

    public Person getPersonUser() {
        return mPersonUser;
    }

    // при використанні цього методу користуватись явним приведенням типів
    // до Director або до Worker
    private Person getPersonFromDB() {
        Map<String, Long> map = new HashMap<>();

        ApiariesCursorWrapper cursor = queryPerson();

        try {
            cursor.moveToFirst();
            map = cursor.getPerson();
            if (cursor.moveToNext()) {
                throw new Exception("Table data in more than one stored user");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        /*
        в першій колонці зберігається компанія (тобто директор)
        в другій колонці зберігається працівник
        якщо працівник NOT_WORKER то користувачем є директор
         */
        long companyId = map.get(ID_DIRECTOR);
        long workerId = map.get(ID_WORKER);

        if (companyId == NOT_DIRECTOR) {
            return null;
        } else if (workerId == NOT_WORKER) {
            // пошук між директорами
            String nameParameter = ApiarisDatabaseSchema.TableOfDirectors.Cols._ID;
            String parameter = String.valueOf(companyId);

            return (Person) getPersonsFromDirectorsByParameter(nameParameter, parameter).get(0);
        }

        String nameParameter = ApiarisDatabaseSchema.TableOfWorkers.Cols._ID;
        String parameter = String.valueOf(workerId);

        return (Person) getPersonsFromWorkersByParameter(nameParameter, parameter).get(0);
    }

    public Person getPersonByEmail(String email) {
        String parameterName = ApiarisDatabaseSchema.TableOfDirectors.Cols.EMAIL;
        List persons = getPersonsFromDirectorsByParameter(parameterName, email);

        if (persons.size() >= 1){
            return (Person) persons.get(0);
        }

        parameterName = ApiarisDatabaseSchema.TableOfWorkers.Cols.EMAIL;
        persons = getPersonsFromWorkersByParameter(parameterName, email);
        if (persons.size() >= 1){
            return (Person) persons.get(0);
        }
        return null;
    }

    public Person getPersonByPhone(String phone){
        String parameterName = ApiarisDatabaseSchema.TableOfDirectors.Cols.PHONE;
        List persons = getPersonsFromDirectorsByParameter(parameterName, phone);

        if (persons.size() >= 1){
            return (Person) persons.get(0);
        }

        parameterName = ApiarisDatabaseSchema.TableOfWorkers.Cols.PHONE;
        persons = getPersonsFromWorkersByParameter(parameterName, phone);
        if (persons.size() >= 1){
            return (Person) persons.get(0);
        }
        return null;
    }

    private List<Worker> getPersonsFromWorkersByParameter(String nameParameter, String parameter){
        List<Worker> people = new ArrayList<>();

        // пошук між працівниками
        ApiariesCursorWrapper cursor = queryWorker(
                nameParameter + " = ?", new String[]{parameter});

        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    people.add(cursor.getWorker());
                    cursor.moveToNext();
                }
            }
        } finally { cursor.close(); }

        return people;
    }

    private List<Director> getPersonsFromDirectorsByParameter(String nameParameter, String parameter){
        List<Director> people = new ArrayList<>();

        // пошук між директорами
        ApiariesCursorWrapper cursor = queryDirector(
                nameParameter + " = ?", new String[]{parameter});

        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    people.add(cursor.getDirector());
                    cursor.moveToNext();
                }
            }
        } finally { cursor.close(); }

        return people;
    }

    public List<Worker> getWorkersByDirectorID(long id) {
        String nameParameter = ApiarisDatabaseSchema.TableOfWorkers.Cols.DIRECTOR_ID;
        String parameter = String.valueOf(id);

        return getPersonsFromWorkersByParameter(nameParameter, parameter);
    }

    public void addWorker(Worker worker) {
        ContentValues values = getContentValues(worker);
        mDatabase.insert(ApiarisDatabaseSchema.TableOfWorkers.NAME_TABLE_OF_WORKERS,
                null, values);
    }

    public void updateWorker(Worker worker) {
        /*
        приклад з "злочинів"

        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeTable.NAME, values,
                CrimeTable.Cols.UUID + " = ?",
                new String[]{uuidString});
         */
        ContentValues values = getContentValues(worker);
        mDatabase.update(ApiarisDatabaseSchema.TableOfWorkers.NAME_TABLE_OF_WORKERS,
                values,
                ApiarisDatabaseSchema.TableOfWorkers.Cols._ID + " = ?",
                new String[]{String.valueOf(worker.getIdWorker())});
//        mDatabase.insert(ApiarisDatabaseSchema.TableOfWorkers.NAME_TABLE_OF_WORKERS,
//                null, values);

    }


    public void savePerson(Person personUser, boolean saveForNextLogin) {
        mPersonUser = personUser;

        long idWorker = NOT_WORKER;
        long idDirector = NOT_DIRECTOR;

        if (saveForNextLogin) {
            if (mPersonUser instanceof Director) {
                idWorker = NOT_WORKER;
                idDirector = ((Director) mPersonUser).getIdDirector();
            } else if (mPersonUser instanceof Worker) {
                idWorker = ((Worker) mPersonUser).getIdWorker();
                idDirector = ((Worker) mPersonUser).getIdDirector();
            }
        }

        ContentValues values = new ContentValues();
        values.put(ApiarisDatabaseSchema.SavedUser.Cols.WORKER_ID, idWorker);
        values.put(ApiarisDatabaseSchema.SavedUser.Cols.DIRECTOR_ID, idDirector);

        mDatabase.update(
                ApiarisDatabaseSchema.SavedUser.NAME_TABLE_SAVED_USER,
                values,
                ApiarisDatabaseSchema.SavedUser.Cols._ID + " = ?",
                new String[]{String.valueOf(ApiarisDatabaseSchema.SavedUser.ID_ROW)}
        );
    }

    @NonNull
    private ApiariesCursorWrapper queryPerson() {
        Cursor cursor = mDatabase.query(
                ApiarisDatabaseSchema.SavedUser.NAME_TABLE_SAVED_USER,
                null, // columns - з null вибирнаються всі стовпці
                null,
                null,
                null,
                null,
                null
        );

        return new ApiariesCursorWrapper(cursor);
    }

    @NonNull
    private ApiariesCursorWrapper queryDirector(
            String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ApiarisDatabaseSchema.TableOfDirectors.NAME_TABLE_OF_DIRECTORS,
                null, // columns - з null вибирнаються всі стовпці
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ApiariesCursorWrapper(cursor);
    }

    @NonNull
    private ApiariesCursorWrapper queryWorker(
            String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                ApiarisDatabaseSchema.TableOfWorkers.NAME_TABLE_OF_WORKERS,
                null, // columns - з null вибирнаються всі стовпці
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new ApiariesCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(Worker worker) {
        ContentValues values = new ContentValues();

        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.DIRECTOR_ID, worker.getIdDirector());
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.NAME, worker.getName());
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.SURNAME, worker.getSurname());
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.PHONE, worker.getPhone());
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.EMAIL, worker.getEmail());
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.PASS, worker.getPass());
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.DATE_OF_BIRTH,
                "" + worker.getDateOfBirth().getTimeInMillis());
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.ACCEPTED_AT_WORK,
                "" + worker.getAcceptedAtWork().getTimeInMillis());
        if (worker.getDismissedFromWork() == null){
            values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.DISMISSED_FROM_WORK,
                    (String) null);
        } else {
            values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.DISMISSED_FROM_WORK,
                    "" + worker.getDismissedFromWork().getTimeInMillis());
        }
        values.put(ApiarisDatabaseSchema.TableOfWorkers.Cols.RATING, worker.getRating());

        return values;
    }
}

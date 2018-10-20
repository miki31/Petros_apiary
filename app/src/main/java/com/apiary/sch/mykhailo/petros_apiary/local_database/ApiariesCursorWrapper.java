package com.apiary.sch.mykhailo.petros_apiary.local_database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.apiary.sch.mykhailo.petros_apiary.model.Apiary;
import com.apiary.sch.mykhailo.petros_apiary.model.BeeCompany;
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Director;
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Worker;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by User on 04.09.2018.
 */

// цей клас призначений не для того
// переробити

public class ApiariesCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public ApiariesCursorWrapper(Cursor cursor) {
        super(cursor);
    }


    // при використанні цього методу користуватись явним приведенням типів
    // до Director або до Worker
    public Map<String, Long> getPerson() {
        long directorId =
                getLong(getColumnIndex(ApiarisDatabaseSchema.SavedUser.Cols.DIRECTOR_ID));
        long workerId =
                getLong(getColumnIndex(ApiarisDatabaseSchema.SavedUser.Cols.WORKER_ID));

        Map<String, Long> map = new HashMap<>();
        map.put(User.ID_DIRECTOR, directorId);
        map.put(User.ID_WORKER, workerId);

        return map;
    }

    // отримання працівника з таблиці працівників
    public Worker getWorker() {
        Worker worker = new Worker();

        int workerId = getInt(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols._ID));
        String name = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.NAME));
        String surname = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.SURNAME));
        String email = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.EMAIL));
        String pass = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.PASS));
        int companyId = getInt(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.DIRECTOR_ID));
        String phone = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.PHONE));
        int rating = getInt(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.RATING));

        Date acceptedAtWork = new Date(Long.decode(getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.ACCEPTED_AT_WORK))));

        String d = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.DISMISSED_FROM_WORK));
        Date dismissedFromWork;
        if (d != null) {
            dismissedFromWork = new Date(Long.decode(d));
        } else {
            dismissedFromWork = null;
        }

        Date dateOfBirth = new Date(Long.decode(getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfWorkers.Cols.DATE_OF_BIRTH))));

        Calendar calendar = new GregorianCalendar();

        worker.setName(name);
        worker.setSurname(surname);
        worker.setEmail(email);
        worker.setPass(pass);
        worker.setIdDirector(companyId);
        worker.setPhone(phone);

        calendar.setTime(dateOfBirth);
        worker.setDateOfBirth(calendar);

        calendar = new GregorianCalendar();
        calendar.setTime(acceptedAtWork);
        worker.setAcceptedAtWork(calendar);

        if (dismissedFromWork == null){
            worker.setDismissedFromWork(null);
        } else {
            calendar = new GregorianCalendar();
            calendar.setTime(dismissedFromWork);
            worker.setDismissedFromWork(calendar);
        }

        worker.setIdWorker(workerId);
        worker.setRating(rating);

        return worker;
    }

    // отримання директора з таблиці директорів
    public Director getDirector() {

        Director director = new Director();

        String name = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfDirectors.Cols.NAME));
        String surname = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfDirectors.Cols.SURNAME));
        String email = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfDirectors.Cols.EMAIL));
        String pass = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfDirectors.Cols.PASS));
        int idDirector = getInt(getColumnIndex(
                ApiarisDatabaseSchema.TableOfDirectors.Cols._ID));
        String phone = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableOfDirectors.Cols.PHONE));

        int numberOfWorkers = getInt(getColumnIndex(
                ApiarisDatabaseSchema.TableOfDirectors.Cols.NUMBER_OF_WORKERS));

        director.setName(name);
        director.setSurname(surname);
        director.setEmail(email);
        director.setPass(pass);
        director.setIdDirector(idDirector);
        director.setPhone(phone);
        director.setNumberOfWorkers(numberOfWorkers);

        return director;
    }

    public BeeCompany getCompany(){
        BeeCompany beeCompany = new BeeCompany();

        String nameCompany = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableCompanies.Cols.NAME_COMPANY));
        long idCompany = getLong(getColumnIndex(
                ApiarisDatabaseSchema.TableCompanies.Cols._ID));
        long idDirector = getLong(getColumnIndex(
                ApiarisDatabaseSchema.TableCompanies.Cols.DIRECTOR_ID));
        String region = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableCompanies.Cols.REGION));

        beeCompany.setNameCompany(nameCompany);
        beeCompany.setIdCompany(idCompany);
        beeCompany.setIdDirector(idDirector);
        beeCompany.setRegion(region);

        return beeCompany;
    }

    public Apiary getApiary(){
        Apiary apiary = new Apiary();

        long idApiary = getLong(getColumnIndex(
                ApiarisDatabaseSchema.TableApiaries.Cols._ID));
        long idDirector = getLong(getColumnIndex(
                ApiarisDatabaseSchema.TableApiaries.Cols.DIRECTOR_ID));
        long idCompany = getLong(getColumnIndex(
                ApiarisDatabaseSchema.TableApiaries.Cols.COMPANY_ID));
        String name = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableApiaries.Cols.NAME_APIARY));
        String region = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableApiaries.Cols.REGION));
        String nearestSettlement = getString(getColumnIndex(
                ApiarisDatabaseSchema.TableApiaries.Cols.NEAREST_SETTLEMENT));
        Double latitude = getDouble(getColumnIndex(
                ApiarisDatabaseSchema.TableApiaries.Cols.LATITUDE));
        Double longitude = getDouble(getColumnIndex(
                ApiarisDatabaseSchema.TableApiaries.Cols.LONGITUDE));

        apiary.setIdApiary(idApiary);
        apiary.setIdDirector(idDirector);
        apiary.setIdCompany(idCompany);
        apiary.setNameApiary(name);
        apiary.setRegion(region);
        apiary.setNearestSettlement(nearestSettlement);
        if (latitude!=null)
            apiary.setLatitude(latitude.doubleValue());
        if (longitude!=null)
            apiary.setLongitude(longitude.doubleValue());

        return apiary;
    }
}
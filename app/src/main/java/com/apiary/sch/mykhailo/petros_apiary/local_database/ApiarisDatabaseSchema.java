package com.apiary.sch.mykhailo.petros_apiary.local_database;

/**
 * Created by User on 03.09.2018.
 */

// клас з константами для доступу до БД
public class ApiarisDatabaseSchema {
    public static final String NAME_DATA_BASE = "apiary_business.db";

    public static final class SavedUser {
        public static final String NAME_TABLE_SAVED_USER = "saved_user";

        public static final int ID_ROW = 1;

        public static final class Cols {
            public static final String _ID = "_id";
            public static final String DIRECTOR_ID = "director_id";
            public static final String WORKER_ID = "worker_id";
        }
    }

    public static final class TableOfDirectors {
        public static final String NAME_TABLE_OF_DIRECTORS = "directors";

        public static final class Cols {
            public static final String _ID = "_id_director";
            public static final String NAME = "name";
            public static final String SURNAME = "surname";
            public static final String PHONE = "phone_number";
            public static final String EMAIL = "email";
            public static final String PASS = "pass";
            public static final String NUMBER_OF_WORKERS = "how_many_workers";
        }
    }

    public static final class TableOfWorkers {
        public static final String NAME_TABLE_OF_WORKERS = "workers";

        public static final class Cols {
            public static final String _ID = "_id_worker";
            public static final String DIRECTOR_ID = "director_id";
            public static final String NAME = "name";
            public static final String SURNAME = "surname";
            public static final String PHONE = "phone_number";
            public static final String EMAIL = "email";
            public static final String PASS = "pass";
            public static final String DATE_OF_BIRTH = "date_of_birth";
            public static final String ACCEPTED_AT_WORK = "accepted_at_work";
            public static final String DISMISSED_FROM_WORK = "dismissed_from_work";
            public static final String RATING = "rating";
        }
    }

    public static final class TableCompanies {
        public static final String NAME_TABLE_COMPANIES = "bee_company";

        public static final class Cols {
            public static final String _ID = "_id_company";
            public static final String DIRECTOR_ID = "director_id";
            public static final String NAME_COMPANY = "name_company";
            public static final String REGION = "region";
        }
    }

    public static final class TableApiaries {
        public static final String NAME_TABLE_APIARIES = "apiaries";

        public static final class Cols {
            public static final String _ID = "_id_apiary";
            public static final String DIRECTOR_ID = "director_id";
            public static final String COMPANY_ID = "company_id";
            public static final String NAME_APIARY = "name_apiary";
            public static final String REGION = "region";
            public static final String NEAREST_SETTLEMENT = "nearest_settlement";// найбл.нас.пуннкт
            public static final String LATITUDE = "latitude";  // широта
            public static final String LONGITUDE = "longitude";  // довгота
        }
    }
}

package com.apiary.sch.mykhailo.petros_apiary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.apiary.sch.mykhailo.petros_apiary.model.persone.Person;
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Worker;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 *
 * Екран входу, який пропонує вхід через електронну пошту / пароль.
 */
public class ApiaryLoginActivity
        extends AppCompatActivity
        implements LoaderCallbacks<Cursor> {
    private static final String TAG = "LOG___LoginActivity";

    /**
     * Id to identity READ_CONTACTS permission request.
     *
     * Ідентифікатор READ_CONTACTS для запиту дозволу.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * Манекенний магазин аутентифікації, що містить відомі імена користувачів та паролі.
     *
     * TODO: remove after connecting to a real authentication system.
     * TODO: видалити після підключення до реальної системи автентифікації.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello",
            "bar@example.com:world",


            "test@t.com:testtt"
    };

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     *
     * Слідкуйте за запитом на вхід, щоб ми могли скасувати його за запитом.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private CheckBox mBoxRemember;
    private View mProgressView;
    private View mLoginFormView;


    private User mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_apiary);
        // Set up the login form.
            // Налаштуйте форму для входу.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        mEmailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // для відміни фону
                mEmailView.setBackground(null);
                mPasswordView.setBackground(null);
                mBoxRemember.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        populateAutoComplete(); // перевірка можливості автозаповнення з адресної книги користувача

        mPasswordView = (EditText) findViewById(R.id.password);
            // призначення прослуховувача дій вводу
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                        // EditorInfo.IME_ACTION_DONE --
                        // (на віртуальній клавіатурі стрілочка вправо або sing in)
                        // EditorInfo.IME_NULL -- кнопка enter
                    attemptLogin(); // спроба авторизації
                    return true;
                }
                return false;
            }
        });

        mPasswordView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // для відміни фону
                mEmailView.setBackground(null);
                mPasswordView.setBackground(null);
                mBoxRemember.setChecked(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBoxRemember = findViewById(R.id.ch_box_remember_me);

        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(); // спроба авторизації
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        savedUser();

        Log.d(TAG, "створено активність реєстрації");

        // для тестування додавання кнопки що вставить лог і пас працівника чи директора
        quickRegistration();

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!


        // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
//        mEmailSignInButton.callOnClick();// емуляція натискання кнопки для пришвидшення тестування !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // метод з кнопками для спрощення тестування
    private void quickRegistration(){
        Button btn_enter_as_director = findViewById(R.id.btn_enter_as_director);
        Button btn_enter_as_worker = findViewById(R.id.btn_enter_as_worker);

        btn_enter_as_director.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // отримання з БД збереженого користувача
                mUser = User.get(getApplicationContext());

                Person person = mUser.getPersonByEmail("mykhailo.sch@gmail.com");

                mEmailView.setText(person.getEmail());
                mPasswordView.setText(person.getPass());

                mEmailView.setBackground(null);
                mPasswordView.setBackground(null);
                mBoxRemember.setChecked(true);
            }
        });

        btn_enter_as_worker.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // отримання з БД збереженого користувача
                mUser = User.get(getApplicationContext());

                Person person = mUser.getPersonByEmail("MashkaPS@gmail.com");

                mEmailView.setText(person.getEmail());
                mPasswordView.setText(person.getPass());

                mEmailView.setBackground(null);
                mPasswordView.setBackground(null);
                mBoxRemember.setChecked(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ЗНИЩЕНО!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! активність реєстрації");
    }


    /*
    якщо користувач попереднім входом зберіг свої дані

    тут можна реалізувати зміну кольору заповнених форм
     */
    private void savedUser(){

        // отримання з БД збереженого користувача
        mUser = User.get(getApplicationContext());
        if (mUser.getPersonUser() == null){
            return;
        }

        mEmailView.setText(mUser.getPersonUser().getEmail());
        mPasswordView.setText(mUser.getPersonUser().getPass());

        mEmailView.setBackgroundResource(R.drawable.login_remember_user);
        mPasswordView.setBackgroundResource(R.drawable.login_remember_user);

        mBoxRemember.setChecked(true);

//        // для відміни фону
//        mPasswordView.setBackground(null);





//        mEmailView.setText("test@t.com");
//        mPasswordView.setText("testtt");
//
//        mEmailView.setBackgroundColor(Color.YELLOW);
//
//        mBoxRemember.setChecked(true);
    }

    // заповнити автоматичне завершення
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        // Поверніться LoaderManager для цієї операції, створіть, якщо потрібно.
        getLoaderManager().initLoader(0, null, this);
    }

    // може замовити контакти
    private boolean mayRequestContacts() {
        // якщо версія системи менша за версію  API 23
            // M is for Marshmallow! ---> API level 23
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        // перевірка особливого дозволу (чи надано дозвіл користувачем для додатка)
        // в файлі маніфесту
        // <uses-permission android:name="android.permission.READ_CONTACTS" />
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }

        // якщо нема дозволу на отримання контактів то запитуємо дозвіл в користувача
        // https://startandroid.ru/ru/blog/508-android-permissions.html -- розяснення
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView,
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS},
                                    REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     *
     * Здійснення зворотного дзвінка, отриманого після завершення запиту на доступ.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete(); // перевірка можливості автозаповнення з адресної книги користувача
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     *
     * Спроби ввійти або зареєструвати обліковий запис,
     * зазначений за формою входу. Якщо виникають помилки форми
     * (невірне електронне повідомлення, відсутні поля тощо),
     * виникають помилки та не відбувається фактична спроба входу.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        // Скидання помилок.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        // Зберігати значення під час спроби входу.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        // Перевірте правильну адресу електронної пошти.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
                                // Це поле є обов'язковим
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
                                // Ця електронна адреса недійсна
            focusView = mEmailView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        // Перевірте правильний пароль, якщо користувач ввів його.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            // занадто короткий пароль
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            // занадто короткий пароль
            focusView = mPasswordView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
                // Там була помилка; не намагайтеся увійти в систему
                // і зосередити поле першої форми з помилкою.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
                // Покажіть спінер прогресу (спін очікування) та запустіть фонове завдання,
                // щоб виконати спробу входу в систему.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    // чи логін (пошта) дійсний
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        //TODO: замінити це своєю власною логікою

        // true якщо містить   @
        return email.contains("@");
    }

    // Чи пароль дійсний
    // можливо мається на увазі умова допустимої мінімальної складності пароля
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        //TODO: замінити це своїм власним логіком

        // true якщо довжина більше 4 знаків
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     *
     * Показує користувальницький інтерфейс і приховує форму для входу.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
            // У Honeycomb MR2 ми маємо API ViewPropertyAnimator,
            // що дозволяє дуже прості анімації. Якщо доступно,
            // використовуйте ці інтерфейси API, щоб зменшити швидкість переміщення.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources()
                    .getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate()
                    .setDuration(shortAnimTime)
                    .alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
                // API ViewPropertyAnimator недоступні,
                // тому просто показуйте та приховуйте відповідні
                // компоненти інтерфейсу користувача.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    // напевне для отримання доступу до даних на пристрої
    // а саме до контактів
    // onCreateLoader - створення нового загрузчика
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
//                Отримати рядки даних для контакту "профілю" користувача пристрою.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY),
                ProfileQuery.PROJECTION,

                // Select only email addresses.
                // Виберіть лише адреси електронної пошти.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                // Показати первинні адреси електронної пошти. Зауважте,
                // що не буде основної адреси електронної пошти, якщо користувач не вказав її.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    // onLoadFinished - викликається коли раніше створений загрузчик закінчив роботу
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    // onLoaderReset - викликається при припиненні роботи загрузчика
    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        // Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        // Створення адаптера, щоб повідомити AutoCompleteTextView,
        // що відображатись у його випадаючому списку.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(ApiaryLoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line,
                        emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate the user.
     *
     * Представляє асинхронне завдання входу / реєстрації,
     * яке використовується для автентифікації користувача.
     * http://developer.alexanderklimov.ru/android/theory/asynctask.php --> опис класу
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private boolean dismissed = false;

        private Person mPerson = null;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            // TODO: спробу автентифікації проти служби мережі.



            if (isOnline()){
                // true - є підключення
                // TODO: тут запит в нет з перевіркою існування логіна і пароля
                // TODO: Цей пункт реалізовувати коли буде робоча ф-я (API) сервера
                Log.d(TAG, "зєднання з мережею встановлено");
            } else {
                // false - нема підключення
                // TODO: тут запит до локальної БД і пошук користувача в ній

                Log.d(TAG, "зєднання з мережею ВІДСУТНЄ");

                mPerson = User.get(getApplicationContext()).getPersonByEmail(mEmail);

                if (mPerson != null) {
                    Log.d(TAG, "знайдено користувача : " + mPerson.getName());
                }
            }



            try {
                // Simulate network access.
                // Симуляція роботи мережі
                Thread.sleep(500);// 2000 --> щоб пришвидшити тестування зменшив !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: цей цикл видалити після реалізації всіх запитів з перевіркою
//            for (String credential : DUMMY_CREDENTIALS) {
//                String[] pieces = credential.split(":");
//                if (pieces[0].equals(mEmail)) {
//                    // Account exists, return true if the password matches.
//                    // Обліковий запис існує, поверніться істинно, якщо пароль відповідає.
//                    return pieces[1].equals(mPassword);
//                }
//            }

            // TODO: register the new account here.
            // TODO: зареєструйте новий обліковий запис тут. (обдумати доцільність реєстрації)

            if (mPerson != null && mPassword.equals(mPerson.getPass())) {
                // перевірка чи не звільнений працівник
                if (mPerson instanceof Worker){
                    Worker worker = (Worker) mPerson;
                    Calendar dateDismissed = worker.getDismissedFromWork();
                    if (dateDismissed != null){
                        Calendar nowDate = new GregorianCalendar();
                        long dTime = nowDate.getTimeInMillis() - dateDismissed.getTimeInMillis();
                        if (dTime > 0){
                            dismissed = true;
                            return false;
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        }

        // метод для перевірки доступу до інтернету
            /*
            Некоторые особенности
                1) Если смартфон подключен к Wi-Fi, то метод вернет true.
                  Даже если интернет не оплачен или из роутера выдернут шнур,
                  то метод все равно вернет true.
                2) Если смартфон подключен к мобильной сети, но интернет не оплачен,
                  то метод вернет true.
             */
        public boolean isOnline() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                // TODO: тут реалізовуэться запуск наступної активності
                /*
                TODO: оскільки з БД ортимано підтвердження дійсності існування користувача
                TODO: Створення або зміна обєкта User
                TODO: Запис користувача в локальну БД, якщо його нема
                 */
//                User.get(getApplicationContext()).setPersonUser(mPerson);
                User.get(getApplicationContext()).savePerson(mPerson, mBoxRemember.isChecked());

                Log.d(TAG, " нашого користувача буде збережено --> " + mBoxRemember.isChecked());

                createNewActivity();
                finish();
            } else {
                if (dismissed){
                    mPasswordView.setError(getString(R.string.error_worker_dismissed));
                } else {
                    mPasswordView.setError(getString(R.string.error_incorrect_password));
                }
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        private void createNewActivity(){
            Intent intent = new Intent(getApplication(), ApiaryMainActivity.class);
            startActivity(intent);
        }
    }
}


package com.apiary.sch.mykhailo.petros_apiary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.apiary.sch.mykhailo.petros_apiary.fragments.fr_company.CompaniesListFragment;
import com.apiary.sch.mykhailo.petros_apiary.fragments.fr_worker.WorkersListFragment;
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Director;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;

import java.util.ArrayList;
import java.util.List;

public class ApiaryMainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentManager.OnBackStackChangedListener{

//    private TextView mTest;
    // перевірити чи це потрібно
    //////////////////////////////////////////////////////////////////
    private WorkersListFragment workersListFragment;
    public List<Fragment> mFragmentList;
    ////////////////////////////////////////////////////////////////////

    private ImageView mImgVPhotoUser;
    private TextView tvNameSurnameUser;
    private TextView tvEmailUser;

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_apiary);

        mFragmentList = new ArrayList<>();

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        // ActionBarDrawerToggle - кнопка що змінює своє зображення та функціонал
        // тут --> "Ліве меню" : "Назад"
        mToggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();
        mToggle.setDrawerSlideAnimationEnabled(true);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        // якщо користувачем є Директор то дати доступ до розширених функцій
        // "@menu/activity_main_drawer" --> файл меню в якому всі функції
        if (User.get(getApplicationContext()).getPersonUser() instanceof Director){
            navigationView.getMenu().getItem(0).setVisible(true);
        } else {
            navigationView.getMenu().getItem(0).setVisible(false);
        }


        navigationView.setNavigationItemSelectedListener(this);

        // відобразити імя email користувача якщо знайдено якийсь обєкт в голові меню
        if (navigationView.getHeaderCount() > 0) {
            View viewHeader = navigationView.getHeaderView(0);

            mImgVPhotoUser = viewHeader.findViewById(R.id.imgv_photo_user);
            tvNameSurnameUser = viewHeader.findViewById(R.id.tv_name_surname_user);
            tvEmailUser = viewHeader.findViewById(R.id.tv_email_user);

            // TODO: завантажити фото користувача
            String nameSurname = "" + User.get(getApplicationContext()).getPersonUser().getName() +
                    " " + User.get(getApplicationContext()).getPersonUser().getSurname();
            tvNameSurnameUser.setText(nameSurname);
            tvEmailUser.setText(User.get(getApplicationContext()).getPersonUser().getEmail());
        }

//        new ReminderItemTask().execute();

//        mTest = findViewById(R.id.test);

        String text = "";

//        for (TestRemind remind :
//                mItems) {
//            text += "id = " + remind.getId();
//            text += " , date = " + remind.getDate();
//            text += " , title = " + remind.getTitle();
//            text += "\n";
//        }

//        mTest.setText(text);
//        mTest.setText("bla-bla-bla " + ServerUser.get(getApplicationContext()).getPersonUser().getName() +
//                "\n" + User.get(getApplicationContext()).getPersonUser().getClass().getSimpleName()
//        + "\n" + User.get(getApplicationContext()).getPersonUser().getClass().getName());
//        workersListFragment = WorkersListFragment.newInstance();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // меню на барі справа
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//          імітація натискання кнопки "назад"
//        onBackPressed();
//        onBackPressed();
//        onBackPressed();
//        onBackPressed();
//        onBackPressed();
//        onBackPressed();



        // очищення стеку фрагментів
//        if (mRemoveCallbackListener != null){
////            mRemoveCallbackListener.
//            mRemoveCallbackListener.removeFragment();
//            mRemoveCallbackListener = null;
//        }

//        mFragmentList = getSupportFragmentManager().getFragments();
//
//        if (mFragmentList.size() > 0){
//             FragmentTransaction trans = getSupportFragmentManager()
//                        .beginTransaction();
//            for (Fragment f :
//                    mFragmentList) {
//                trans.remove(f)
//                        .commit();
//            }
//            mFragmentList.clear();
//        }

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // метод видаляє фрагмент зі стеку
//        getSupportFragmentManager().popBackStack();
//          те саме але повертає true якщо щось виконано, false - якщо нічого не видалено
//        getSupportFragmentManager().popBackStackImmediate();

        FragmentManager fragmentManager = getSupportFragmentManager();

        // слухач для відображення і реакції кнопки "назад" в верхньому лівому кутку
        fragmentManager.addOnBackStackChangedListener(this);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // видаляє все фрагменти в стеку
        boolean a = fragmentManager.popBackStackImmediate();
        while (a){
            a = fragmentManager.popBackStackImmediate();
        }

        if (id == R.id.nav_workers) {
//            mWorkersListFragment.
            // якщо зробити параметром класу то не працюватиме коректно
            // відображення даних в списку
            // workersListFragment = WorkersListFragment.newInstance();
            // знайшов ф-ю для переходу на фрагмент назад лнопкою "назад"
            // тому вернув в глобальну змінну
            WorkersListFragment workersListFragment = WorkersListFragment.newInstance();
//            mRemoveCallbackListener = workersListFragment;

//            mFragmentList.add(fff);

//            fragmentTransaction.replace(R.id.fragment_container, workersListFragment);
            fragmentTransaction.replace(R.id.fragment_container, workersListFragment);
        } else if (id == R.id.nav_company){
            CompaniesListFragment companiesListFragment = CompaniesListFragment.newInstance();
            fragmentTransaction.replace(R.id.fragment_container, companiesListFragment);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        // додавання фрагмента до стеку зворотніх викликів
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackStackChanged() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // show back button
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);//show hamburger
            mToggle.syncState();
            mToggle.setDrawerSlideAnimationEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mDrawer.openDrawer(GravityCompat.START);
                }
            });
        }
    }

    // метод для закриття клавіатури
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

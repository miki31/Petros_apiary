package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_worker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apiary.sch.mykhailo.petros_apiary.ApiaryMainActivity;
import com.apiary.sch.mykhailo.petros_apiary.R;
import com.apiary.sch.mykhailo.petros_apiary.fragments.DatePickerFragment;
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Person;
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Worker;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ServerUser on 28.09.2018.
 */

public class WorkerDetailFragment extends Fragment {

    public static final String ARG_WORKER_EMAIL = "arg_worker_email";
    public static final String ARG_IS_CREATE_NEW = "is_create_new";
    public static final String ARG_IS_EDIT_DATA_WORKER = "edit_data_worker";
    public static final String ARG_IS_DISMISSED_WORKER = "Dismissed_worker";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE_OF_BIRTH = 0;
    private static final int REQUEST_DATE_ACCEPTED_AT_WORK = 1;
    private static final int REQUEST_DATE_DISMISSED_FROM_WORK = 2;

    private boolean isCreateNew;
    private boolean isEditDataWorker;
    private boolean isDismissedFromWork;

    private Worker mWorker;

    private Button btnSaveWorker;
    private LinearLayout llInfoID;
    private TextView tvIdWorker;
    private TextView tvIdCompany;
    private EditText etNameWorker;
    private EditText etSurnameWorker;
    private EditText etRatingWorker;
    private EditText etEmailWorker;
    private EditText etPasswordWorker;
    private EditText etPhoneWorker;
    private TextView tvDateOfBirth;
    private TextView tvDateAcceptedAtWork;
    private TextView tvDateDismissedFromWork;
    private ImageButton ibtnDateOfBirthWorker;
    private ImageButton ibtnDateAcceptedAtWork;
    private ImageButton ibtnDateDismissedFromWork;

    private Date mDateOfBirth;
    private Date mDateAccepted;
    private Date mDateDismissed;

    /**
     * обовязковий інтерфейс для активності хоста
     */
    public interface Callbacks {
        // для того щоб поміняти дані в списку динамічно
        // (для двохфрагментного вікна)
    }

    public WorkerDetailFragment() {
    }

    public static WorkerDetailFragment newInstance() {
        WorkerDetailFragment fragment = new WorkerDetailFragment();
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            String email = args.getString(ARG_WORKER_EMAIL, null);
            isCreateNew = args.getBoolean(ARG_IS_CREATE_NEW, false);
            isEditDataWorker = args.getBoolean(ARG_IS_EDIT_DATA_WORKER, false);
            isDismissedFromWork = args.getBoolean(ARG_IS_DISMISSED_WORKER, false);
            if (email != null) {
                mWorker = (Worker) User.get(getActivity()).getPersonByEmail(email);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_worker,
                container, false);

        // TODO: заповнити форму
        //find view elements
        btnSaveWorker = view.findViewById(R.id.btn_save_worker);
        llInfoID = view.findViewById(R.id.linear_layout_info_id);
        tvIdWorker = view.findViewById(R.id.tv_id_worker);
        tvIdCompany = view.findViewById(R.id.tv_id_company);
        etNameWorker = view.findViewById(R.id.et_name_worker);
        etSurnameWorker = view.findViewById(R.id.et_surname_worker);
        etRatingWorker = view.findViewById(R.id.et_rating_worker);
        etEmailWorker = view.findViewById(R.id.et_email_worker);
        etPasswordWorker = view.findViewById(R.id.et_password_worker);
        etPhoneWorker = view.findViewById(R.id.et_phone_worker);
        tvDateOfBirth = view.findViewById(R.id.tv_date_of_birth_worker);
        tvDateAcceptedAtWork = view.findViewById(R.id.tv_accepted_at_work);
        tvDateDismissedFromWork = view.findViewById(R.id.tv_dismissed_from_work);
        ibtnDateOfBirthWorker = view.findViewById(R.id.ibtn_date_of_birth_worker);
        ibtnDateAcceptedAtWork = view.findViewById(R.id.ibtn_date_accepted_at_work);
        ibtnDateDismissedFromWork = view.findViewById(R.id.ibtn_date_dismissed_from_work);


        // listeners for buttons
        btnSaveWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiaryMainActivity.hideKeyboard(getActivity());
                if (isCreateNew) {
                    if (saveWorker()) {
                        removeThisFragment();
                    }
                } else if (isEditDataWorker) {
//              TODO: зберегти зміни
                    if (saveWorker()) {
                        isEditDataWorker = false;
                        updateForm();
                    }
                } else if (isDismissedFromWork) {
                    if (saveWorker()) {
                        isDismissedFromWork = false;
                        updateForm();
                    }
                } else {
                    fillOutTheForm();
                }

            }
        });

        ibtnDateOfBirthWorker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiaryMainActivity.hideKeyboard(getActivity());
                FragmentManager manager = getFragmentManager();
                Date date = mDateOfBirth == null ? new Date() : mDateOfBirth;
                DatePickerFragment dialog = DatePickerFragment.newInstance(date,
                        getResources()
                                .getString(
                                        R.string.details_worker_dialog_title_birth_day));
                dialog.setTargetFragment(WorkerDetailFragment.this,
                        REQUEST_DATE_OF_BIRTH);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        ibtnDateAcceptedAtWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiaryMainActivity.hideKeyboard(getActivity());
                FragmentManager manager = getFragmentManager();
                Date date = mDateAccepted == null ? new Date() : mDateAccepted;
                DatePickerFragment dialog =
                        DatePickerFragment.newInstance(date,
                                getResources().getString(
                                        R.string.details_worker_dialog_title_accepted));
                dialog.setTargetFragment(WorkerDetailFragment.this,
                        REQUEST_DATE_ACCEPTED_AT_WORK);
                dialog.show(manager, DIALOG_DATE);
            }
        });
        ibtnDateDismissedFromWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiaryMainActivity.hideKeyboard(getActivity());
                FragmentManager manager = getFragmentManager();
                Date date = mDateDismissed == null ? new Date() : mDateDismissed;
                DatePickerFragment dialog =
                        DatePickerFragment.newInstance(date,
                                getResources().getString(
                                        R.string.details_worker_dialog_title_dismissed));
                dialog.setTargetFragment(WorkerDetailFragment.this,
                        REQUEST_DATE_DISMISSED_FROM_WORK);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        // заповнення форми даними про працівника
        // чи відкрита форма для створення нового працівника

        updateForm();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ApiaryMainActivity.hideKeyboard(getActivity());
    }

    private void updateForm() {
        if (isCreateNew) {
            setHasOptionsMenu(false);
            setChangeOptionsForCreateNew();
        } else if (isEditDataWorker) {
            setHasOptionsMenu(false);
            setChangeOptionsForEdit();
        } else if (isDismissedFromWork) {
            setHasOptionsMenu(false);
            setChangeOptionsForDismissed();
        } else {
            if (mWorker.getDismissedFromWork() == null) {setHasOptionsMenu(true);}
            fillOutTheForm();
        }
    }

    private void setChangeOptionsForCreateNew() {
        llInfoID.setVisibility(View.GONE);
        ibtnDateOfBirthWorker.setVisibility(View.VISIBLE);
        ibtnDateAcceptedAtWork.setVisibility(View.VISIBLE);
//        Ця ф-я доступна при видаленні (звільненні) працівника
//        ibtnDateDismissedFromWork.setVisibility(View.VISIBLE);
        btnSaveWorker.setText(R.string.details_worker_btn_save_worker);
        btnSaveWorker.setVisibility(View.VISIBLE);

    }

    private void setChangeOptionsForEdit() {
        // дозвіл зміни полів
        etNameWorker.setEnabled(true);
        etSurnameWorker.setEnabled(true);
        etRatingWorker.setEnabled(true);
        etEmailWorker.setEnabled(false);
        etPasswordWorker.setEnabled(false);
        etPhoneWorker.setEnabled(true);

        llInfoID.setVisibility(View.GONE);
        ibtnDateOfBirthWorker.setVisibility(View.VISIBLE);

        btnSaveWorker.setText(R.string.details_worker_btn_save_worker_changes);
        btnSaveWorker.setVisibility(View.VISIBLE);

        mDateOfBirth = new Date();
        mDateOfBirth.setTime(mWorker.getDateOfBirth().getTimeInMillis());
        tvDateOfBirth.setText(dateToString(mDateOfBirth));

        mDateAccepted = new Date();
        mDateAccepted.setTime(mWorker.getAcceptedAtWork().getTimeInMillis());
        tvDateAcceptedAtWork.setText(dateToString(mDateAccepted));
    }

    @SuppressLint("StringFormatInvalid")
    private void setChangeOptionsForDismissed() {
        btnSaveWorker.setText(R.string.details_worker_btn_save_worker_dismissed);
        btnSaveWorker.setVisibility(View.VISIBLE);
        ibtnDateDismissedFromWork.setVisibility(View.VISIBLE);
        llInfoID.setVisibility(View.GONE);

        mDateOfBirth = new Date();
        mDateOfBirth.setTime(mWorker.getDateOfBirth().getTimeInMillis());
        tvDateOfBirth.setText(dateToString(mDateOfBirth));

        mDateAccepted = new Date();
        mDateAccepted.setTime(mWorker.getAcceptedAtWork().getTimeInMillis());
        tvDateAcceptedAtWork.setText(dateToString(mDateAccepted));

        mDateDismissed = new Date();
        tvDateDismissedFromWork.setText(
                getString(R.string.details_worker_dismissed_today, dateToString(mDateDismissed)));
    }

    private void fillOutTheForm() {
        btnSaveWorker.setVisibility(View.GONE);
        ibtnDateOfBirthWorker.setVisibility(View.GONE);
        ibtnDateDismissedFromWork.setVisibility(View.GONE);

        // присвоєння значень в відповідні поля форми
        tvIdWorker.setText(String.valueOf(mWorker.getIdWorker()));
        tvIdCompany.setText(String.valueOf(mWorker.getIdDirector()));
        etNameWorker.setText(mWorker.getName());
        etSurnameWorker.setText(mWorker.getSurname());
        etRatingWorker.setText(String.valueOf(mWorker.getRating()));
        etEmailWorker.setText(mWorker.getEmail());
        etPasswordWorker.setText(mWorker.getPass());
        etPhoneWorker.setText(String.valueOf(mWorker.getPhone()));
        tvDateOfBirth.setText(dateToString(mWorker.getDateOfBirth().getTime()));
        tvDateAcceptedAtWork.setText(dateToString(mWorker.getAcceptedAtWork().getTime()));
        if (mWorker.getDismissedFromWork() != null) {
            tvDateDismissedFromWork.setText(dateToString(mWorker.getDismissedFromWork().getTime()));
        } else {
            tvDateDismissedFromWork.setText(R.string.details_worker_no_dismissed_today);
        }

        // заборона зміни полів
        etNameWorker.setEnabled(false);
        etSurnameWorker.setEnabled(false);
        etRatingWorker.setEnabled(false);
        etEmailWorker.setEnabled(false);
        etPasswordWorker.setEnabled(false);
        etPhoneWorker.setEnabled(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_details_worker_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ApiaryMainActivity.hideKeyboard(getActivity());

        switch (item.getItemId()) {
            case R.id.edit_data_worker:
                isEditDataWorker = true;
                updateForm();
                return true;

            case R.id.dismiss_data_worker:
                isDismissedFromWork = true;
                updateForm();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE_OF_BIRTH) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDateOfBirth = date;
            tvDateOfBirth.setText(dateToString(date));
        }

        if (requestCode == REQUEST_DATE_ACCEPTED_AT_WORK) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDateAccepted = date;
            tvDateAcceptedAtWork.setText(dateToString(date));
        }

        if (requestCode == REQUEST_DATE_DISMISSED_FROM_WORK) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mDateDismissed = date;
            tvDateDismissedFromWork.setText(dateToString(date));
        }
    }

    private String dateToString(Date date) {
        String dateFormat = "dd MMMM yyyyр.";
        String dateString = DateFormat.format(
                dateFormat,
                date
        ).toString();
        return dateString;
    }

    private void removeThisFragment() {
        // але кнопка "назад" повертає на це закрите вікно
//        getActivity().getSupportFragmentManager().beginTransaction().removeThisFragment(thisFragment).commit();
        getActivity().getSupportFragmentManager().popBackStack();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
    // збереження працівника (змін чи звільнення) та перевірка введених даних
    private boolean saveWorker() {
        Worker worker = new Worker();
        if (isEditDataWorker || isDismissedFromWork) {
            worker = mWorker.copy();
            if (isDismissedFromWork) {

//          TODO: запит на перевірку дати звільнення працівника
//   це перемістити в if isCorrectDateOfBirth(null)
                if (isCorrectDateDismissedFromWork(mDateDismissed, mDateAccepted)) {
                    Calendar dismissedFromWork = new GregorianCalendar();
                    dismissedFromWork.setTime(mDateDismissed);
                    worker.setDismissedFromWork(dismissedFromWork);


//                    Calendar acceptedAtWork = new GregorianCalendar();
//                    acceptedAtWork.setTime(mDateAccepted);
//                    worker.setAcceptedAtWork(acceptedAtWork);
                } else return false;

                User.get(getActivity()).updateWorker(worker);
                mWorker = worker;
                return true;
            }
        }

        if (isCorrectName(etNameWorker.getText().toString())) {
            worker.setName(etNameWorker.getText().toString());
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_short_name
            ), Toast.LENGTH_LONG).show();
            return false;
        }

        if (isCorrectName(etSurnameWorker.getText().toString())) {
            worker.setSurname(etSurnameWorker.getText().toString());
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_short_surname
            ), Toast.LENGTH_LONG).show();
            return false;
        }

//      TODO: чи вводити рейтинг працівника і яка перевірка рейтингу??????????????????????
        if (isCorrectRating(etRatingWorker.getText().toString())) {
            worker.setRating(new Integer(etRatingWorker.getText().toString()));
        } else return false;

        if (isCorrectPhone(etPhoneWorker.getText().toString())) {
            String phone = etPhoneWorker.getText().toString();
            if (phone.length() == 10){
                phone = "+38" + phone;
            }
            worker.setPhone(phone);
        } else return false;

//          TODO: запит на перевірку дня народження
        if (isCorrectDateOfBirth(mDateOfBirth)) {
            Calendar dateOfBirth = new GregorianCalendar();
            dateOfBirth.setTime(mDateOfBirth);
            worker.setDateOfBirth(dateOfBirth);
        } else return false;


        if (isCreateNew) {
            worker.setIdDirector(User.get(getActivity()).getPersonUser().getIdDirector());

            if (isCorrectEmail(etEmailWorker.getText().toString())) {
                worker.setEmail(etEmailWorker.getText().toString());
            } else return false;

            if (isCorrectPassword(etPasswordWorker.getText().toString())) {
                worker.setPass(etPasswordWorker.getText().toString());
            } else return false;

//          TODO: запит на перевірку дати прийняття працівника
            if (isCorrectDateAcceptedAtWork(mDateAccepted, mDateOfBirth)) {
                Calendar acceptedAtWork = new GregorianCalendar();
                acceptedAtWork.setTime(mDateAccepted);
                worker.setAcceptedAtWork(acceptedAtWork);
            } else if (mDateAccepted == null) {
                Calendar acceptedAtWork = new GregorianCalendar();
                worker.setAcceptedAtWork(acceptedAtWork);
            }
        }

        worker.setDismissedFromWork(null);

        if (isEditDataWorker) {
            User.get(getActivity()).updateWorker(worker);
            mWorker = worker;
        } else if (isCreateNew) {
            long id = User.get(getActivity()).addWorker(worker);
            if (id == -1){
                return false;
            }
        }

        return true;
    }

    private boolean isCorrectName(String name) {
        if (name.length() > 2)
            return true;
        else return false;
    }

    private boolean isCorrectPhone(String phone) {
        // при редагуванні даних чи було змінено номер
        if (mWorker != null && phone.equals(mWorker.getPhone())) {
            return true;
        }
        boolean isCorrect = false;


        Pattern p = Pattern.compile("^(0\\d{9})$");      // вираз
        Matcher m = p.matcher(phone);       // джерело
        if (m.find()) {
            isCorrect = true;
        }

        p = Pattern.compile("^(\\+380\\d{9})$");      // вираз
        m = p.matcher(phone);       // джерело
        if (m.find()) {
            isCorrect = true;
        }

        if (!isCorrect) {
            Toast.makeText(getContext(),
                    getResources().getString(R.string.details_worker_error_message_wrong_phone),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (phone.length() == 10){
            phone = "+38" + phone;
        }

        Person person = User.get(getContext()).getPersonByPhone(phone);
        if (person != null) {
            String mess = getResources().getString(
                    R.string.details_worker_error_message_double_phone);
            mess = String.format(mess,
                    new String(person.getName()).toUpperCase(),
                    new String(person.getSurname()).toUpperCase());

            Toast.makeText(getContext(), mess, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean isCorrectEmail(String email) {
        if (!email.contains("@")) {
            Toast.makeText(getContext(),
                    getResources().getString(R.string.details_worker_error_message_wrong_email),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        Person person = User.get(getContext()).getPersonByEmail(email);
        if (person != null) {
            String mess = getResources().getString(
                    R.string.details_worker_error_message_double_email);
            mess = String.format(mess,
                    new String(person.getName()).toUpperCase(),
                    new String(person.getSurname()).toUpperCase());

            Toast.makeText(getContext(), mess, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean isCorrectPassword(String pass) {
        if (pass.length() > 4) {
            return true;
        } else {
            Toast.makeText(getContext(),
                    getResources().getString(R.string.details_worker_error_message_short_pass),
                    Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean isCorrectDateOfBirth(Date date) {
        if (date == null) {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_worker_not_date_birth),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        Calendar now = new GregorianCalendar();
        Calendar birthDay = new GregorianCalendar();
        birthDay.setTime(date);
        long nowMSec = now.getTimeInMillis();
        long birthDayMSec = birthDay.getTimeInMillis();
        long diff = nowMSec - birthDayMSec;
        long diffDays = diff / (24 * 60 * 60 * 1000); // Days
        if (diffDays < 0) {
            // цей працівник ще не народився)))
            Toast.makeText(getContext(),
                    getResources().getString(R.string.details_worker_error_message_worker_not_born),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (diffDays / 365 < 12) {
            Toast.makeText(getContext(),
                    getResources().getString(R.string.details_worker_error_message_worker_young),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean isCorrectDateAcceptedAtWork(Date accepted, Date dateOfBirth) {
        if (accepted == null) {
            return false;
        }
        Calendar calAccepted = new GregorianCalendar();
        Calendar birthDay = new GregorianCalendar();
        birthDay.setTime(dateOfBirth);
        calAccepted.setTime(accepted);
        long calAcceptedMSec = calAccepted.getTimeInMillis();
        long birthDayMSec = birthDay.getTimeInMillis();
        long diff = calAcceptedMSec - birthDayMSec;
        long diffDays = diff / (24 * 60 * 60 * 1000); // Days
        if (diffDays < 0) {
            // цей працівник ще не народився)))
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_worker_not_born),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        if (diffDays / 365 < 12) {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_worker_young),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean isCorrectDateDismissedFromWork(Date dismissed, Date accepted) {
        if (dismissed == null) {
            return false;
        }

        Calendar calDismissed = new GregorianCalendar();
        Calendar calAccepted = new GregorianCalendar();
        calDismissed.setTime(dismissed);
        calAccepted.setTime(accepted);
        long calDismissedMSec = calDismissed.getTimeInMillis();
        long acceptedMSec = calAccepted.getTimeInMillis();
        long diff = calDismissedMSec - acceptedMSec;
        long diffDays = diff / (24 * 60 * 60 * 1000); // Days
        if (diffDays < 0) {
            // цей працівник ще не народився)))
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_worker_not_work),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        Calendar nowDate = new GregorianCalendar();
        long nowDateMSec = nowDate.getTimeInMillis();
        diff = nowDateMSec - calDismissedMSec;
        diffDays = diff / (24 * 60 * 60 * 1000); // Days
        if (diffDays > 14) {
            // цей працівник ще не народився)))
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_dismissal_more_14_days),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (diffDays < -7) {
            // цей працівник ще не народився)))
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_dismissal_back_date) ,
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean isCorrectRating(String n) {
        if (n.length()==0){
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_not_put_rating),
                    Toast.LENGTH_LONG).show();
            return false;
        }
        Integer rating = new Integer(n);
        if (rating < 0) {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_rating_negative),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        if (rating > 1000) {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_worker_error_message_rating_too_big),
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////


}

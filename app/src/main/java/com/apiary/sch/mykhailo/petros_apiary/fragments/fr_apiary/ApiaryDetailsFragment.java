package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_apiary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apiary.sch.mykhailo.petros_apiary.ApiaryMainActivity;
import com.apiary.sch.mykhailo.petros_apiary.R;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.ApiaryAccess;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;
import com.apiary.sch.mykhailo.petros_apiary.model.Apiary;
import com.apiary.sch.mykhailo.petros_apiary.model.BeeCompany;

/**
 * Created by ServerUser on 19.10.2018.
 */

// test git

public class ApiaryDetailsFragment extends Fragment {

    public static final String ARG_IS_CREATE_NEW_APIARY = "is_create_new_apiary";
    public static final String ARG_IS_EDIT_DATA_APIARY = "edit_data_apiary";

    private BeeCompany mBeeCompany;

    private boolean mIsCreateNewApiary;
    private boolean mIsEditDataApiary;

    private Apiary mApiary;

    private Button mBtnSaveApiary;
    //    @BindView
    private EditText mEtNameApiary;
    private EditText mEtRegionApiary;
    private EditText mEtNearestSettlementApiary;
    private EditText mEtLatitudeApiary;
    private EditText mEtLongitudeApiary;


    public ApiaryDetailsFragment() {
    }

    public static ApiaryDetailsFragment newInstance(BeeCompany company, Apiary apiary) {
        ApiaryDetailsFragment fragment = new ApiaryDetailsFragment();
        fragment.mBeeCompany = company;
        fragment.mApiary = apiary;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            mIsCreateNewApiary = args.getBoolean(
                    ARG_IS_CREATE_NEW_APIARY, false);
            mIsEditDataApiary = args.getBoolean(
                    ARG_IS_EDIT_DATA_APIARY, false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_apiary,
                container, false);

        //find vew elements
        mBtnSaveApiary = view.findViewById(R.id.btn_save_apiary);
        mEtNameApiary = view.findViewById(R.id.et_name_apiary);
        mEtRegionApiary = view.findViewById(R.id.et_region_apiary);
        mEtNearestSettlementApiary = view.findViewById(R.id.et_nearest_settlement_apiary);
        mEtLatitudeApiary = view.findViewById(R.id.et_latitude_apiary);
        mEtLongitudeApiary = view.findViewById(R.id.et_longitude_apiary);

        mBtnSaveApiary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiaryMainActivity.hideKeyboard(getActivity());
                if (saveApiary()){
                    removeThisFragment();
                }
            }
        });

        updateForm();
        return view;
    }

    private void updateForm() {
        if (mIsCreateNewApiary) {
            setChangeOptionsForCreateNew();
        }

        if (mIsEditDataApiary){
            setChangeOptionsForEdit();
        }
    }

    // форма для редагування існуючого
    private void setChangeOptionsForEdit() {
        mBtnSaveApiary.setText(R.string.details_apiary_btn_save_changes_apiary);
        mBtnSaveApiary.setVisibility(View.VISIBLE);

        // TODO: зміна параметрів
        mEtNameApiary.setText(mApiary.getNameApiary());
        mEtRegionApiary.setText(mApiary.getRegion());
        mEtNearestSettlementApiary.setText(mApiary.getNearestSettlement());
        mEtLatitudeApiary.setText(""+mApiary.getLatitude());
        mEtLongitudeApiary.setText(""+mApiary.getLongitude());
    }

    // форма для створення нового обєкта
    private void setChangeOptionsForCreateNew() {
        mBtnSaveApiary.setText(R.string.details_apiary_btn_save_apiary);
        mBtnSaveApiary.setVisibility(View.VISIBLE);
    }

    // метод для повернення на рівень назад та азкриття теперішнього вікна
    private void removeThisFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private boolean saveApiary(){
        Apiary apiary = new Apiary();

        if (mIsEditDataApiary){
            apiary = mApiary.copy();
            // TODO: зміна даних в пасіці
        }

        if (isCorrectName(mEtNameApiary.getText().toString())){
            apiary.setNameApiary(mEtNameApiary.getText().toString());
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_apiary_error_message_short_name
            ), Toast.LENGTH_LONG).show();
            return false;
        }

        if (isCorrectName(mEtRegionApiary.getText().toString())){
            apiary.setRegion(mEtRegionApiary.getText().toString());
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_apiary_error_message_short_name
            ), Toast.LENGTH_LONG).show();
            return false;
        }

        if (isCorrectName(mEtNearestSettlementApiary.getText().toString())){
            apiary.setNearestSettlement(mEtNearestSettlementApiary.getText().toString());
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_apiary_error_message_short_name
            ), Toast.LENGTH_LONG).show();
            return false;
        }

        double coordinate;
        String sCoordinate = mEtLatitudeApiary.getText().toString();
        if (sCoordinate.length() == 0){
            coordinate = 0;
        } else {
           coordinate = new Double(sCoordinate);
        }
        if (isCorrectCoordinats(coordinate)){
            // TODO: правильне ввердення координат

            apiary.setLatitude(coordinate);
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_apiary_error_message_coordint_error
            ), Toast.LENGTH_LONG).show();
            return false;
        }


        sCoordinate = mEtLongitudeApiary.getText().toString();
        if (sCoordinate.length() == 0){
            coordinate = 0;
        } else {
            coordinate = new Double(sCoordinate);
        }
        if (isCorrectCoordinats(coordinate)){
            // TODO: правильне ввердення координат
            apiary.setLongitude(coordinate);
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_apiary_error_message_coordint_error
            ), Toast.LENGTH_LONG).show();
            return false;
        }

        if (mIsCreateNewApiary){
            apiary.setIdCompany(mBeeCompany.getIdCompany());
            apiary.setIdDirector(mBeeCompany.getIdDirector());
            long id = ApiaryAccess.get(getActivity()).addApiary(apiary);
            if (id == -1){
                return false;
            }
        }

        if (mIsEditDataApiary){
            ApiaryAccess.get(getActivity()).updateApiary(apiary);
        }

        return true;
    }

    private boolean isCorrectName(String name){
        if (name.length() > 2)
            return true;
        else return false;
    }

    private boolean isCorrectCoordinats(Double coordinate){
        // TODO: перевірка правильності координат
        return true;
    }
}

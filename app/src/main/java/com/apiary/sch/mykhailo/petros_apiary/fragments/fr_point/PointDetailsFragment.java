package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_point;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apiary.sch.mykhailo.petros_apiary.ApiaryMainActivity;
import com.apiary.sch.mykhailo.petros_apiary.R;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.PointAccess;
import com.apiary.sch.mykhailo.petros_apiary.model.Apiary;
import com.apiary.sch.mykhailo.petros_apiary.model.BeeCompany;
import com.apiary.sch.mykhailo.petros_apiary.model.Point;

public class PointDetailsFragment extends Fragment {

    public static final String ARG_IS_CREATE_NEW_APIARY = "is_create_new_apiary";
    public static final String ARG_IS_EDIT_DATA_APIARY = "edit_data_apiary";

    private boolean mIsCreateNewPoint;
    private boolean mIsEditDataPoint;

    private Point mPoint;
    private Apiary mApiary;

    private Button mBtnSavePoint;
    private EditText mEtNamePoint;
    private EditText mEtPositionPoint;

    public PointDetailsFragment() {
    }

    public static PointDetailsFragment newInstance(Point point, Apiary apiary) {
        PointDetailsFragment fragment = new PointDetailsFragment();
        fragment.mPoint = point;
        fragment.mApiary = apiary;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null) {
            mIsCreateNewPoint = args.getBoolean(
                    ARG_IS_CREATE_NEW_APIARY, false);
            mIsEditDataPoint = args.getBoolean(
                    ARG_IS_EDIT_DATA_APIARY, false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_point,
                container, false);

        //find view elements
        mBtnSavePoint = view.findViewById(R.id.btn_save_point);
        mEtNamePoint = view.findViewById(R.id.et_name_point);
        mEtPositionPoint = view.findViewById(R.id.et_position_point);

        mBtnSavePoint.setOnClickListener(v -> {
            ApiaryMainActivity.hideKeyboard(getActivity());
            if (saveApiary()) {
                removeThisFragment();
                // TODO: збереження даних
            }
        });

        updateForm();
        return view;
    }

    private void updateForm() {
        if (mIsCreateNewPoint) {
            setChangeOptionsForCreateNew();
        }

        if (mIsEditDataPoint) {
            setChangeOptionsForEdit();
        }
    }

    // форма для створення нового обєкта
    private void setChangeOptionsForCreateNew() {
        mBtnSavePoint.setText(R.string.details_point_btn_save_point);
        mBtnSavePoint.setVisibility(View.VISIBLE);
    }

    // форма для редагування існуючого
    private void setChangeOptionsForEdit() {
        mBtnSavePoint.setText(R.string.details_point_btn_save_changes_point);
        mBtnSavePoint.setVisibility(View.VISIBLE);

        // TODO: зміна параметрів
        mEtNamePoint.setText(mPoint.getName());
        mEtPositionPoint.setText(mPoint.getPosition());
    }

    // метод для повернення на рівень назад та азкриття теперішнього вікна
    private void removeThisFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private boolean saveApiary() {
        Point point = new Point();

        if (mIsEditDataPoint) {
            point = mPoint.copy();
            //TODO: зміна даниз в точку
        }

        if (isCorrectName(mEtNamePoint.getText().toString())) {
            point.setName(mEtNamePoint.getText().toString());
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_apiary_error_message_short_name
            ), Toast.LENGTH_LONG).show();
            return false;
        }

        if (isCorrectName(mEtPositionPoint.getText().toString())) {
            point.setPosition(mEtPositionPoint.getText().toString());
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_apiary_error_message_short_name
            ), Toast.LENGTH_LONG).show();
            return false;
        }

        if (mIsCreateNewPoint){
            point.setIdDirector(mApiary.getIdDirector());
            point.setIdApiary(mApiary.getIdApiary());
            long id = PointAccess.get(getActivity()).addPoint(point);
            if (id == -1){
                return false;
            }
        }

        if (mIsEditDataPoint){
            PointAccess.get(getActivity()).updatePoint(point);
        }

        return true;
    }

    private boolean isCorrectName(String name) {
        if (name.length() > 2)
            return true;
        else return false;
    }
}

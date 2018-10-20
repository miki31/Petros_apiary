package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_apiary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.apiary.sch.mykhailo.petros_apiary.R;
import com.apiary.sch.mykhailo.petros_apiary.model.Apiary;

/**
 * Created by User on 19.10.2018.
 */

public class ApiaryDetailsFragment extends Fragment{

    public static final String ARG_IS_CREATE_NEW_APIARY = "is_create_new_apiary";
    public static final String ARG_IS_EDIT_DATA_APIARY = "edit_data_apiary";

    private boolean mIsCreateNewApiary;
    private boolean mIsEditDataApiary;

    private Apiary mApiary;

    private Button mBtnSaveApiary;
    private TextView mEtNameCompany;
    private TextView mEtRegionCompany;


    public ApiaryDetailsFragment() {}

    public static ApiaryDetailsFragment newInstance(){
        return new ApiaryDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null){
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



        return super.onCreateView(inflater, container, savedInstanceState);
    }
}

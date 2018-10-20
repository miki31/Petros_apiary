package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_company;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.apiary.sch.mykhailo.petros_apiary.ApiaryMainActivity;
import com.apiary.sch.mykhailo.petros_apiary.R;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.CompanyAccess;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;
import com.apiary.sch.mykhailo.petros_apiary.model.BeeCompany;

/**
 * Created by User on 17.10.2018.
 */

public class CompanyDetailsFragment extends Fragment{

    public static final String ARG_IS_CREATE_NEW_COMPANY = "is_create_new_company";
    public static final String ARG_IS_EDIT_DATA_COMPANY = "edit_data_company";
    public static final String ARG_BEE_COMPANY = "data_company";

    private boolean mIsCreateNewCompany;
    private boolean mIsEditDataCompany;

    private BeeCompany mBeeCompany;

    private Button mBtnSaveCompany;
    private TextView mEtNameCompany;
    private TextView mEtRegionCompany;

    public CompanyDetailsFragment() {
    }

    @SuppressLint("ValidFragment")
    public CompanyDetailsFragment(BeeCompany beeCompany) {
        mBeeCompany = beeCompany;
    }

    public static CompanyDetailsFragment newInstance(BeeCompany beeCompany){
        return new CompanyDetailsFragment(beeCompany);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if (args != null){
            mIsCreateNewCompany = args.getBoolean(
                    ARG_IS_CREATE_NEW_COMPANY, false);
            mIsEditDataCompany = args.getBoolean(
                    ARG_IS_EDIT_DATA_COMPANY, false);
//            mBeeCompany = (BeeCompany) args.getSerializable(ARG_BEE_COMPANY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_company,
                container, false);

        //find view elements
        mBtnSaveCompany = view.findViewById(R.id.btn_save_company);
        mEtNameCompany = view.findViewById(R.id.et_name_company);
        mEtRegionCompany = view.findViewById(R.id.et_region_company);

        mBtnSaveCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiaryMainActivity.hideKeyboard(getActivity());
                if (saveCompany()){
                    removeThisFragment();
                }

            }
        });

        updateForm();
        return view;
    }

    private void updateForm(){
        if (mIsCreateNewCompany){
            setChangeOptionsForCreateNew();
        }

        if (mIsEditDataCompany){
            setChangeOptionsForEdit();
        }
    }

    private void setChangeOptionsForEdit() {
        mBtnSaveCompany.setText(R.string.details_company_btn_save_changes_company);
        mBtnSaveCompany.setVisibility(View.VISIBLE);

        mEtNameCompany.setText(mBeeCompany.getNameCompany());
        mEtRegionCompany.setText(mBeeCompany.getRegion());
    }

    private void setChangeOptionsForCreateNew() {
        mBtnSaveCompany.setText(R.string.details_company_btn_save_company);
        mBtnSaveCompany.setVisibility(View.VISIBLE);
    }

    private void removeThisFragment() {
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private boolean saveCompany(){
        BeeCompany company = new BeeCompany();

        if (mIsEditDataCompany){
            company = mBeeCompany.copy();
            // TODO: зміна даних в компанії
        }

        if (isCorrectName(mEtNameCompany.getText().toString())){
            company.setNameCompany(mEtNameCompany.getText().toString());
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_company_error_message_short_name
            ), Toast.LENGTH_LONG).show();
            return false;
        }

        if (isCorrectName(mEtRegionCompany.getText().toString())){
            company.setRegion(mEtRegionCompany.getText().toString());
        } else {
            Toast.makeText(getContext(), getResources().getString(
                    R.string.details_company_error_message_short_name
            ), Toast.LENGTH_LONG).show();
            return false;
        }

        if (mIsCreateNewCompany){
            company.setIdDirector(User.get(getActivity()).getPersonUser().getIdDirector());
            CompanyAccess.get(getActivity()).addCompany(company);
        }

        if (mIsEditDataCompany){
            CompanyAccess.get(getActivity()).updateCompany(company);
        }

        return true;
    }

    private boolean isCorrectName(String name){
        if (name.length() > 2)
            return true;
        else return false;
    }
}

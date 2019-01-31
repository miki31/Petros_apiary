package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_hive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apiary.sch.mykhailo.petros_apiary.R;
import com.apiary.sch.mykhailo.petros_apiary.model.Hive;

public class HiveElementFragment extends Fragment {
    private Hive mHive;
    TextView mTextView;

    public static HiveElementFragment newInstance(Hive hive) {
        HiveElementFragment fragment = new HiveElementFragment();
        fragment.mHive = hive;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_hive,container,false);

        mTextView = view.findViewById(R.id.tv_element_hive);
        mTextView.setText(mHive.getName());

        return view;
    }
}

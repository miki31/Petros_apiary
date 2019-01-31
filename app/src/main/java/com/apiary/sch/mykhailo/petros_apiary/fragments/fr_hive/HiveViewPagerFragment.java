package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_hive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apiary.sch.mykhailo.petros_apiary.R;
import com.apiary.sch.mykhailo.petros_apiary.model.AmountOfFrame;
import com.apiary.sch.mykhailo.petros_apiary.model.Apiary;
import com.apiary.sch.mykhailo.petros_apiary.model.Hive;

import java.util.ArrayList;
import java.util.List;

public class HiveViewPagerFragment extends Fragment {
    // про фрагмент в фрагменті
    // http://qaru.site/questions/24847/fragment-inside-fragment

    private Apiary mApiary;

    private ViewPager mHivesViewPager;
    private List<Hive> mHiveList;
    private int mCountFragments;

    public static HiveViewPagerFragment newInstance(Apiary apiary){
        HiveViewPagerFragment fragment = new HiveViewPagerFragment();
        fragment.mApiary = apiary;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: визначити функціонал директора і працівника
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fargment_viewpager_list,container, false
        );

        mHivesViewPager = view.findViewById(R.id.list_view_pager);

        // в випадку коли передаватимуться дані через бандл ТУТ зчитати їх

        updateUI();

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        HiveFragmentStatePagerAdapter adapter = new HiveFragmentStatePagerAdapter(fragmentManager);
        for (int i = 0; i < mHiveList.size(); i++) {
            Fragment f = HiveElementFragment.newInstance(mHiveList.get(i));
            adapter.addFragment(f);
        }

        mHivesViewPager.setAdapter(adapter);

        return view;
    }

    private void updateUI(){
        mHiveList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Hive hive = new Hive(AmountOfFrame.EIGHT);
            hive.setName("Hive № " + i);
            mHiveList.add(hive);
        }
        mCountFragments = mHiveList.size();

    }

    private class HiveFragmentStatePagerAdapter
            extends FragmentStatePagerAdapter {

        List<Fragment> mFragmentList;

        public HiveFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
            if (mFragmentList == null){
                mFragmentList = new ArrayList<>();
            }
        }

        @Override
        public Fragment getItem(int position) {
            //???
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment){
            mFragmentList.add(fragment);
        }
    }
}

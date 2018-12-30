package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_apiary;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apiary.sch.mykhailo.petros_apiary.R;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.ApiaryAccess;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;
import com.apiary.sch.mykhailo.petros_apiary.model.Apiary;
import com.apiary.sch.mykhailo.petros_apiary.model.BeeCompany;

import java.util.List;

/**
 * Created by ServerUser on 18.10.2018.
 */

public class ApiaryListFragment extends Fragment {

    private BeeCompany mBeeCompany;

    private RecyclerView mApiariesRecyclerView;
    private ApiaryAdapter mAdapter;


    public static ApiaryListFragment newInstance(BeeCompany company) {
        ApiaryListFragment fragment = new ApiaryListFragment();
        fragment.mBeeCompany = company;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (User.get(getContext()).isDirector()) {
            // функціонал для директора
            setHasOptionsMenu(true);   // Додавання меню в toolbar
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_list, container, false);

        mApiariesRecyclerView = view.findViewById(R.id.list_recycler_view);
        mApiariesRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity()));

        // в випадку коли передаватимуться дані через бандл ТУТ зчитати їх

        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_apiaries_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_apiary:
                FragmentManager fragmentManager =
                        getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();

                ApiaryDetailsFragment detailsFragment =
                        ApiaryDetailsFragment.newInstance(mBeeCompany);

                Bundle args = new Bundle();
                args.putBoolean(ApiaryDetailsFragment.ARG_IS_CREATE_NEW_APIARY, true);
                detailsFragment.setArguments(args);

                fragmentTransaction.replace(R.id.fragment_container, detailsFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        User user = User.get(getActivity());
        long idDirector = user.getPersonUser().getIdDirector();
        long idCompany = mBeeCompany.getIdCompany();
        ApiaryAccess apiaryAccess = ApiaryAccess.get(getActivity());
        List<Apiary> apiaries = apiaryAccess
                .getApiariesByCompanyId(idCompany);

        if (mAdapter == null) {
            mAdapter = new ApiaryAdapter(apiaries);
            mApiariesRecyclerView.setAdapter(mAdapter);
        } else {
            // щоб працювало забрав змінну фрагмента в класі ApiaryMainActivity
//            // наступний рядок нібито тут не повинен бути
//            // знайти в чому помилка але з ним ніби працює
            // без цього рядка при поверненні від створення нового елемента
            // не відображається список
            mApiariesRecyclerView.setAdapter(mAdapter);
            mAdapter.setApiaries(apiaries);
            mAdapter.notifyDataSetChanged(); // оновити список
        }
    }


    private class ApiaryHolder extends RecyclerView.ViewHolder {

        public static final int NOT_BACKGROUND_COLOR = -1;
        private Apiary mApiary;

        private TextView mApiaryNameTV;
        private TextView mApiaryRegionTV;
        private TextView mApiaryNearestSettlementTV;
        private int mResourceBackgroundColor;

        public ApiaryHolder(View itemView) {
            super(itemView);
        }

        public ApiaryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_apiary,
                    parent, false));

//            для натискання на пункт
//            itemView.setOnClickListener(this);

            mApiaryNameTV = (TextView) itemView.findViewById(R.id.apiary_name);
            mApiaryRegionTV = (TextView) itemView.findViewById(R.id.apiary_region);
            mApiaryNearestSettlementTV =
                    (TextView) itemView.findViewById(R.id.apiary_nearest_settlement);
        }

        @SuppressLint("ResourceType")
        public void bind(Apiary apiary, int backgroundColor) {
            mApiary = apiary;
            mApiaryNameTV.setText(mApiary.getNameApiary());
            mApiaryRegionTV.setText(mApiary.getRegion());
            mApiaryNearestSettlementTV.setText(mApiary.getNearestSettlement());


            if (backgroundColor == NOT_BACKGROUND_COLOR) {
                return;
            }

            ColorDrawable colorDrawable = new ColorDrawable();
            switch (backgroundColor) {
                case 0:
                    mResourceBackgroundColor = getResources().getInteger(R.color.colorCompanyList1);
                    break;
                case 1:
                    mResourceBackgroundColor = getResources().getInteger(R.color.colorCompanyList2);
                    break;
                case 2:
                    mResourceBackgroundColor = getResources().getInteger(R.color.colorCompanyList3);
                    break;
            }

            colorDrawable.setColor(mResourceBackgroundColor);
            int color = colorDrawable.getColor();
            itemView.setBackgroundColor(color);
        }
    }


    private class ApiaryAdapter extends RecyclerView.Adapter<ApiaryHolder> {

        private List<Apiary> mApiaries;

        public ApiaryAdapter(List<Apiary> apiaries) {
            mApiaries = apiaries;
        }

        @NonNull
        @Override
        public ApiaryHolder onCreateViewHolder(
                @NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new ApiaryHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(
                @NonNull ApiaryHolder holder, int position) {
            Apiary apiary = mApiaries.get(position);
            holder.bind(apiary, position % 3);
        }

        @Override
        public int getItemCount() {
            return mApiaries.size();
        }

        public void setApiaries(List<Apiary> apiaries) {
            mApiaries = apiaries;
        }
    }


//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
}

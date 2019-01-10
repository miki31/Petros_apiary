package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_company;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.apiary.sch.mykhailo.petros_apiary.R;
import com.apiary.sch.mykhailo.petros_apiary.fragments.fr_apiary.ApiaryListFragment;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.CompanyAccess;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;
import com.apiary.sch.mykhailo.petros_apiary.model.BeeCompany;

import java.util.List;

/**
 * Created by ServerUser on 11.10.2018.
 */

public class CompaniesListFragment extends Fragment {

    private RecyclerView mCompaniesRecyclerView;
    private BeeCompanyAdapter mAdapter;

    public static CompaniesListFragment newInstance() {
        CompaniesListFragment fragment = new CompaniesListFragment();

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

        mCompaniesRecyclerView = view.findViewById(R.id.list_recycler_view);
        mCompaniesRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity()));

        // в випадку коли передаватимуться дані через бандл ТУТ зчитати їх

        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_companies_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_company:
                FragmentManager fragmentManager =
                        getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();

                CompanyDetailsFragment detailsFragment =
                        CompanyDetailsFragment.newInstance(new BeeCompany());

                Bundle args = new Bundle();
                args.putBoolean(CompanyDetailsFragment.ARG_IS_CREATE_NEW_COMPANY, true);
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
        CompanyAccess companyAccess = CompanyAccess.get(getActivity());
        List<BeeCompany> beeCompanies = companyAccess
                .getCompaniesByDirectorID(idDirector);

        if (mAdapter == null) {
            mAdapter = new BeeCompanyAdapter(beeCompanies);
            mCompaniesRecyclerView.setAdapter(mAdapter);
        } else {
            // щоб працювало забрав змінну фрагмента в класі ApiaryMainActivity
//            // наступний рядок нібито тут не повинен бути
//            // знайти в чому помилка але з ним ніби працює
            // без цього рядка при поверненні від створення нового елемента
            // не відображається список
            mCompaniesRecyclerView.setAdapter(mAdapter);
            mAdapter.setBeeCompanies(beeCompanies);
            mAdapter.notifyDataSetChanged(); // оновити список
        }
    }


    private class BeeCompanyHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public static final int NOT_BACKGROUND_COLOR = -1;
        private BeeCompany mBeeCompany;

        private TextView mCompanyNameTV;
        private TextView mCompanyRegionTV;
        private ImageButton mImgBtnMenuFromItem;
        private int mResourceBackgroundColor;

        public BeeCompanyHolder(View itemView) {
            super(itemView);
        }

        public BeeCompanyHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_company,
                    parent, false));

//            для натискання на пункт
            itemView.setOnClickListener(this);

            mCompanyNameTV = (TextView) itemView.findViewById(R.id.company_name);
            mCompanyRegionTV = (TextView) itemView.findViewById(R.id.company_region);
            if (User.get(getActivity()).isDirector()) {
                mImgBtnMenuFromItem = (ImageButton)
                        itemView.findViewById(R.id.im_btn_company_menu_from_item);
                mImgBtnMenuFromItem.setVisibility(View.VISIBLE);
                mImgBtnMenuFromItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showPopupMenu(v);
                    }
                });
            }
        }

        private void showPopupMenu(View v) {
            PopupMenu menu = new PopupMenu(getContext(), v);
            menu.inflate(R.menu.popup_menu_from_company);

            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menuPopCompanyEdit:
                            Toast.makeText(getContext(), "Змінити",
                                    Toast.LENGTH_LONG).show();

                            FragmentManager fragmentManager =
                                    getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction =
                                    fragmentManager.beginTransaction();

                            CompanyDetailsFragment detailsFragment =
                                    CompanyDetailsFragment.newInstance(mBeeCompany);

                            Bundle args = new Bundle();
                            args.putBoolean(CompanyDetailsFragment.ARG_IS_EDIT_DATA_COMPANY, true);
                            detailsFragment.setArguments(args);

                            fragmentTransaction.replace(R.id.fragment_container, detailsFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();

                            return true;

                        case R.id.menuPopCompanyDelete:
                            Toast.makeText(getContext(), "видалити.\n" +
                                            "Треба створити перевірку запитання чи дійсно видалити",
                                    Toast.LENGTH_LONG).show();
                            return true;

                        default:
                            return false;
                    }
                }
            });

            menu.show();
        }

        @SuppressLint("ResourceType")
        public void bind(BeeCompany beeCompany, int backgroundColor) {
            mBeeCompany = beeCompany;
            mCompanyNameTV.setText(mBeeCompany.getNameCompany());
            mCompanyRegionTV.setText(mBeeCompany.getRegion());

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

        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            ApiaryListFragment apiaryFragment = ApiaryListFragment.newInstance(mBeeCompany);

            // TODO: при потребі тут створити і передати фрагменту бандл

            fragmentTransaction.replace(R.id.fragment_container, apiaryFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    private class BeeCompanyAdapter extends RecyclerView.Adapter<BeeCompanyHolder> {

        private List<BeeCompany> mBeeCompanies;

        public BeeCompanyAdapter(List<BeeCompany> beeCompanies) {
            mBeeCompanies = beeCompanies;
        }

        @NonNull
        @Override
        public BeeCompanyHolder onCreateViewHolder(
                @NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new BeeCompanyHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(
                @NonNull BeeCompanyHolder holder, int position) {
            BeeCompany beeCompany = mBeeCompanies.get(position);
            holder.bind(beeCompany, position % 3);
        }

        @Override
        public int getItemCount() {
            return mBeeCompanies.size();
        }

        public void setBeeCompanies(List<BeeCompany> beeCompanies) {
            mBeeCompanies = beeCompanies;
        }
    }

}

package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_point;

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
import com.apiary.sch.mykhailo.petros_apiary.fragments.fr_hive.HiveViewPagerFragment;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.PointAccess;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;
import com.apiary.sch.mykhailo.petros_apiary.model.Apiary;
import com.apiary.sch.mykhailo.petros_apiary.model.Point;

import java.util.List;

public class PointListFragment extends Fragment {

    private Apiary mApiary;

    private RecyclerView mPointsRecyclerView;
    private PointAdapter mAdapter;

    public static PointListFragment newInstance(Apiary apiary) {
        PointListFragment fragment = new PointListFragment();
        fragment.mApiary = apiary;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: додати функціонал для працівника
        if (User.get(getContext()).isDirector()) {
            // функціонал для директора
            setHasOptionsMenu(true);    // Додавання меню в toolbar
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list,
                container,
                false);

        mPointsRecyclerView = view.findViewById(R.id.list_recycler_view);
        mPointsRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity()));

        // в випадку коли передаватимуться дані через бандл ТУТ зчитати їх

        updateUI();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_points_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_point:
                // перехід на фрагмент створення точка
                FragmentManager fragmentManager =
                        getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction =
                        fragmentManager.beginTransaction();

                PointDetailsFragment detailsFragment =
                        PointDetailsFragment.newInstance(null, mApiary);

                Bundle args = new Bundle();
                args.putBoolean(PointDetailsFragment.ARG_IS_CREATE_NEW_POINT, true);
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
//        User user = User.get(getActivity());
        long idApiary = mApiary.getIdApiary();

        PointAccess pointAccess = PointAccess.get(getActivity());
        List<Point> points = pointAccess.getPointsByApiaryId(idApiary);

        if (mAdapter == null) {
            mAdapter = new PointAdapter(points);
            mPointsRecyclerView.setAdapter(mAdapter);
        } else {
            // щоб працювало забрав змінну фрагмента в класі ApiaryMainActivity
//            // наступний рядок нібито тут не повинен бути
//            // знайти в чому помилка але з ним ніби працює
            // без цього рядка при поверненні від створення нового елемента
            // не відображається список
            mPointsRecyclerView.setAdapter(mAdapter);
            mAdapter.setPoints(points);
            mAdapter.notifyDataSetChanged(); // оновити список
        }
    }


    private class PointHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public static final int NOT_BACKGROUND_COLOR = -1;
        private Point mPoint;

        private TextView mPointNameTV;
        private TextView mPointPositionTV;
        private ImageButton mImgBtnMenuFromItem;
        private int mResourceBackgroundColor;

        public PointHolder(View itemView) {
            super(itemView);
        }

        public PointHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_point,
                    parent, false));

//            для натискання на пункт списку
            itemView.setOnClickListener(this);

            mPointNameTV = (TextView) itemView.findViewById(R.id.point_name);
            mPointPositionTV = (TextView) itemView.findViewById(R.id.point_position);

            if (User.get(getActivity()).isDirector()) {
                mImgBtnMenuFromItem = (ImageButton)
                        itemView.findViewById(R.id.im_btn_point_menu_from_item);
                mImgBtnMenuFromItem.setVisibility(View.VISIBLE);
                mImgBtnMenuFromItem.setOnClickListener((v) -> {
                    showPopupMenu(v);
                });
            }
        }

        private void showPopupMenu(View v) {
            PopupMenu menu = new PopupMenu(getContext(), v);
            menu.inflate(R.menu.popup_menu_from_point);

            menu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menuPopPointEdit:
                        // зміна даних в точку
                        Toast.makeText(getContext(), "Змінити дані точка",
                                Toast.LENGTH_LONG).show();

                        FragmentManager fragmentManager =
                                getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction =
                                fragmentManager.beginTransaction();

                        PointDetailsFragment detailsFragment =
                                PointDetailsFragment.newInstance(mPoint, mApiary);

                        Bundle args = new Bundle();
                        args.putBoolean(PointDetailsFragment.ARG_IS_EDIT_DATA_POINT, true);
                        detailsFragment.setArguments(args);

                        fragmentTransaction.replace(R.id.fragment_container, detailsFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                        return true;
                    case R.id.menuPopPointDelete:
                        //TODO: видалення точка

                        Toast.makeText(getContext(), "видалити точок.\n" +
                                        "Треба створити перевірку запитання чи дійсно видалити",
                                Toast.LENGTH_LONG).show();

                        return true;
                    default:
                        return false;
                }
            });

            menu.show();
        }

        @SuppressLint("ResourceType")
        public void bind(Point point, int backgroundColor) {
            mPoint = point;
            mPointNameTV.setText(mPoint.getName());
            mPointPositionTV.setText(mPoint.getPosition());

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
        public void onClick(View view) {
            // TODO: перехід до вибору нищого рівня по ієрархії моделі даних
            // перехід до вуликів
            // TODO: TEST

            Toast.makeText(getContext(),"asdfghj", Toast.LENGTH_LONG).show();

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            HiveViewPagerFragment hiveViewPagerFragment =
                    HiveViewPagerFragment.newInstance(mApiary);

            //  при потребі тут створити і передати фрагменту бандл

            fragmentTransaction.replace(R.id.fragment_container, hiveViewPagerFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();


            // TODO: при потребі тут створити і передати фрагменту бандл

        }
    }


    private class PointAdapter
            extends RecyclerView.Adapter<PointHolder> {

        private List<Point> mPoints;

        public PointAdapter(List<Point> points) {
            mPoints = points;
        }

        @NonNull
        @Override
        public PointHolder onCreateViewHolder(
                @NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new PointHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(
                @NonNull PointHolder holder, int position) {
            Point point = mPoints.get(position);
            holder.bind(point, position % 3);
        }

        @Override
        public int getItemCount() {
            return mPoints.size();
        }

        public void setPoints(List<Point> points) {
            mPoints = points;
        }
    }


}

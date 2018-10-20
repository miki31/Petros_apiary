package com.apiary.sch.mykhailo.petros_apiary.fragments.fr_worker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.apiary.sch.mykhailo.petros_apiary.model.persone.Worker;
import com.apiary.sch.mykhailo.petros_apiary.local_database.access_to_db.user.User;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link WorkersListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link WorkersListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkersListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView mWorkersRecyclerView;
    private WorkersAdapter mAdapter;

    private WorkerDetailFragment mWorkerDetailFragment;

    public WorkersListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkersListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkersListFragment newInstance(String param1, String param2) {
        WorkersListFragment fragment = new WorkersListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static WorkersListFragment newInstance() {
        WorkersListFragment fragment = new WorkersListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // додавання меню
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.fragment_list, container, false);
        mWorkersRecyclerView = view.findViewById(R.id.list_recycler_view);
        mWorkersRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity()));

        // в випадку коли передаватимуться дані через бандл ТУТ зчитати їх

        updateUI();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // перевірка реалізації деяких інтерфейсів в класі який викликав фрагмент.
        // нам не потрібна
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_worker_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_worker_persone:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                WorkerDetailFragment detailFragment =
                        WorkerDetailFragment.newInstance();
                mWorkerDetailFragment = detailFragment;

                Bundle args = new Bundle();
                args.putSerializable(WorkerDetailFragment.ARG_IS_CREATE_NEW, true);
                detailFragment.setArguments(args);

                fragmentTransaction.replace(R.id.fragment_container, detailFragment);
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
        List<Worker> workers = user.getWorkersByDirectorID(idDirector);

        if (mAdapter == null) {
            mAdapter = new WorkersAdapter(workers);
            mWorkersRecyclerView.setAdapter(mAdapter);
        } else {
            // щоб працювало забрав змінну фрагмента в класі ApiaryMainActivity
//            // наступний рядок нібито тут не повинен бути
//            // знайти в чому помилка але з ним ніби працює
            mWorkersRecyclerView.setAdapter(mAdapter);

            mAdapter.setWorkers(workers);
            mAdapter.notifyDataSetChanged(); // оновити список
        }

        // TODO: дивитись в "злочини". Можна добавити в ActionBar кількість робітників
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     * Цей інтерфейс повинен бути реалізований заходами, що містять це
     *       * фрагмент, який дозволяє повідомляти взаємодію в цьому фрагменті
     *       * до активності та потенційно інших фрагментів, що містяться в цьому
     *       * активність
     *       * <p>
     *       * Перегляньте уроки Android Training <a href =
     *       * "http://developer.android.com/training/basics/fragments/communicating.html"
     *       *> Зв'язок з іншими фрагментами </a> для отримання додаткової інформації.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private class WorkersHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Worker mWorker;

        private TextView mWorkerNameTV;
        private TextView mWorkerSurnameTV;
        private TextView mWorkerEmailTV;

//        private LinearLayout mLinearLayoutItemWorker;


        public WorkersHolder(View itemView) {
            super(itemView);
        }

        public WorkersHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_worker,
                    parent, false));

            itemView.setOnClickListener(this);

            mWorkerNameTV = (TextView) itemView.findViewById(R.id.company_name);
            mWorkerSurnameTV = (TextView) itemView.findViewById(R.id.company_region);
            mWorkerEmailTV = (TextView) itemView.findViewById(R.id.worker_email);
//            mLinearLayoutItemWorker = (LinearLayout) itemView
//                    .findViewById(R.id.linearLayoutItemWorker);
        }

        @SuppressLint("ResourceType")
        public void bind(Worker worker) {
            mWorker = worker;
            mWorkerNameTV.setText(mWorker.getName());
            mWorkerSurnameTV.setText(mWorker.getSurname());
            mWorkerEmailTV.setText(mWorker.getEmail());
            if (mWorker.getDismissedFromWork() == null){
                itemView.setBackgroundColor(
                        getResources().getInteger(R.color.colorWorkerIsNowWorking));
            } else {
                itemView.setBackgroundColor(
                        getResources().getInteger(R.color.colorWorkerDoesNotWork));
            }
        }

        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            WorkerDetailFragment detailFragment = WorkerDetailFragment.newInstance();
            mWorkerDetailFragment = detailFragment;

            Bundle args = new Bundle();
            args.putSerializable(WorkerDetailFragment.ARG_WORKER_EMAIL, mWorker.getEmail());
            detailFragment.setArguments(args);

            fragmentTransaction.replace(R.id.fragment_container, detailFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }


    private class WorkersAdapter extends RecyclerView.Adapter<WorkersHolder> {

        private List<Worker> mWorkers;

        public WorkersAdapter(List<Worker> workers) {
            mWorkers = workers;
        }

        @NonNull
        @Override
        public WorkersHolder onCreateViewHolder(
                @NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());

            return new WorkersHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull WorkersHolder holder, int position) {
            Worker worker = mWorkers.get(position);
            holder.bind(worker);
        }

        @Override
        public int getItemCount() {
            return mWorkers.size();
        }

        public void setWorkers(List<Worker> workers) {
            mWorkers = workers;
        }
    }
}

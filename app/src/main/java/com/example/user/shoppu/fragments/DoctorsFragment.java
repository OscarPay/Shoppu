package com.example.user.shoppu.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.shoppu.DrawerActivity;
import com.example.user.shoppu.R;
import com.example.user.shoppu.Utils.Utils;
import com.example.user.shoppu.adapter.DoctorAdapter;
import com.example.user.shoppu.models.Doctor;
import com.example.user.shoppu.models.Patient;
import com.example.user.shoppu.remote.DoctorAPI;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DoctorsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class DoctorsFragment extends Fragment {

    public static int PAGE_SIZE = 10;

    private static final String TAG = DoctorsFragment.class.getSimpleName();
    private OnFragmentInteractionListener mListener;
    private Patient currentUser;
    public List<Doctor> doctors;
    private Activity activity;
    private DoctorAdapter doctorAdapter;
    private ProgressDialog progressDialog;
    private String token;
    private LinearLayoutManager mLayoutManager;
    private boolean mIsLastPage = false;
    private boolean mIsLoading = false;
    private int mCurrentPage = 1;
    private String mCurrentQuery = "";
    private String mFindBy;
    public DrawerActivity drawerActivity = null;
    public Toolbar toolbar = null;

    @Bind(R.id.recycler_view_doctors)
    public RecyclerView recyclerViewDoctores;

    public SearchView searchView;

    public DoctorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFindBy = getArguments().getString(getString(R.string.find_by_key), "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        drawerActivity = (DrawerActivity) getActivity();

        setToolbar(view);

        getUserData();

        getListDoctors();

        doctorAdapter = new DoctorAdapter(activity, doctors);
        recyclerViewDoctores.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerViewDoctores.setLayoutManager(mLayoutManager);
        recyclerViewDoctores.setAdapter(doctorAdapter);

        // Pagination
        recyclerViewDoctores.setOnScrollListener(mRecyclerViewOnScrollListener);

        return view;
    }

    private void getUserData() {
        String jsonUser = getArguments().getString(getString(R.string.user_key), "");
        currentUser = Utils.toUserAtributtes(jsonUser);
        activity = this.getActivity();
        token = getString(R.string.token) + currentUser.getUserAttributes().getToken();
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_doctors);
        drawerActivity.setSupportActionBar(toolbar);

        switch (mFindBy){
            case "Nombre":
                drawerActivity.getSupportActionBar().setTitle(getString(R.string.by_name));
                break;
            case "Especialidad":
                drawerActivity.getSupportActionBar().setTitle(getString(R.string.by_specialty));
                break;
            case "Hospital":
                drawerActivity.getSupportActionBar().setTitle(getString(R.string.by_hospital));
                break;
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                drawerActivity, drawerActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerActivity.drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.doctors, menu);
        MenuItem searchItem = menu.findItem(R.id.mSearch);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint("Buscar...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mCurrentQuery = query;
                mIsLastPage = false;
                mCurrentPage = 1;
                showLoadingDialog();
                fetchDoctors(mCallbackFirsPage);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getListDoctors() {
        showLoadingDialog();
        fetchDoctors(mCallbackFirsPage);
    }

    private void showLoadingDialog() {
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        mIsLoading = true;
    }

    public void fetchDoctors(Callback<List<Doctor>> callback){
        switch (mFindBy){

            case "Nombre":
                DoctorAPI.Factory.getInstance().getDoctors(token, mCurrentQuery, null, null, String.valueOf(mCurrentPage))
                        .enqueue(callback);
                break;
            case "Especialidad":
                DoctorAPI.Factory.getInstance().getDoctors(token, null, mCurrentQuery, null, String.valueOf(mCurrentPage))
                        .enqueue(callback);
                break;
            case "Hospital":
                DoctorAPI.Factory.getInstance().getDoctors(token, null, null, mCurrentQuery, String.valueOf(mCurrentPage))
                        .enqueue(callback);
                break;
        }
    }

    public Callback<List<Doctor>> mCallbackFirsPage = new Callback<List<Doctor>>() {
        @Override
        public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
            int code = response.code();
            mIsLoading = false;

            switch (code){
                case 200:
                    Log.d(TAG, String.valueOf(code));
                    doctors = response.body();
                    doctorAdapter.swap(doctors);

                    break;
                default:
                    Log.e(TAG, String.valueOf(code));
                    try {
                        Snackbar.make(getActivity().getCurrentFocus(), "Error del Servidor" + response.errorBody().string(), Snackbar.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
            }

            progressDialog.dismiss();
        }

        @Override
        public void onFailure(Call<List<Doctor>> call, Throwable t) {
            progressDialog.dismiss();
            Snackbar.make(getActivity().getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    };

    public Callback<List<Doctor>> mCallbackNextPage = new Callback<List<Doctor>>() {
        @Override
        public void onResponse(Call<List<Doctor>> call, Response<List<Doctor>> response) {
            int code = response.code();
            mIsLoading = false;

            switch (code){
                case 200:
                    Log.d(TAG, String.valueOf(code));
                    doctors = response.body();
                    if (!doctors.isEmpty()){
                        doctorAdapter.add(doctors);
                    }else{
                        mIsLastPage = true;
                        Snackbar.make(getActivity().getCurrentFocus(), "No hay más doctores", Snackbar.LENGTH_SHORT).show();
                    }

                    break;
                default:
                    Log.e(TAG, String.valueOf(code));
                    try {
                        Snackbar.make(getActivity().getCurrentFocus(), "Error del Servidor" + response.errorBody().string(), Snackbar.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
            }

            progressDialog.dismiss();
        }

        @Override
        public void onFailure(Call<List<Doctor>> call, Throwable t) {
            progressDialog.dismiss();
            Snackbar.make(getActivity().getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    };

    private RecyclerView.OnScrollListener mRecyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();


            if (!mIsLoading && !mIsLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= PAGE_SIZE) {
                    mCurrentPage++;
                    loadMoreItems();
                }
            }


        }
    };

    public void loadMoreItems() {
        showLoadingDialog();
        fetchDoctors(mCallbackNextPage);
    }




    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

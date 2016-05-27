package com.example.user.shoppu.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.shoppu.DrawerActivity;
import com.example.user.shoppu.R;
import com.example.user.shoppu.Utils.Utils;
import com.example.user.shoppu.adapter.PurchasesAdapter;
import com.example.user.shoppu.models.Purchase;
import com.example.user.shoppu.models.User;
import com.example.user.shoppu.remote.PurchaseAPI;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseHistoryFragment extends Fragment {
    private static final String TAG = PurchaseHistoryFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private User currentUser;
    private List<Purchase> purchases;
    private Activity activity;
    private PurchasesAdapter purchasesAdapter;
    private ProgressDialog progressDialog;
    private String token;
    private LinearLayoutManager mLayoutManager;
    public DrawerActivity drawerActivity = null;
    public Toolbar toolbar = null;
    private boolean mIsLoading = false;

    @Bind(R.id.recycler_view_doctors)
    public RecyclerView recyclerViewDoctores;

    public PurchaseHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_history, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        drawerActivity = (DrawerActivity) getActivity();

        setToolbar(view);

        getUserData();

        getListPurchasedObjects();

        purchasesAdapter = new PurchasesAdapter(activity, purchases);
        recyclerViewDoctores.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerViewDoctores.setLayoutManager(mLayoutManager);
        recyclerViewDoctores.setAdapter(purchasesAdapter);

        return view;
    }

    private void getUserData() {
        String jsonUser = getArguments().getString(getString(R.string.user_key), "");
        currentUser = Utils.toUserAtributtes(jsonUser);
        activity = this.getActivity();
        token = getString(R.string.token) + currentUser.getUserAttributes().getToken();
    }

    private void getListPurchasedObjects(){
        showLoadingDialog();
        fetchPurchases(mCallbackPurchase);
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_doctors);
        drawerActivity.setSupportActionBar(toolbar);
        drawerActivity.getSupportActionBar().setTitle(getString(R.string.purchases));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                drawerActivity, drawerActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerActivity.drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void showLoadingDialog() {
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        mIsLoading = true;
    }

    private void fetchPurchases(Callback<List<Purchase>> callback){
        PurchaseAPI.Factory.getInstance().getPurchase(currentUser.getUserAttributes().getToken(), currentUser.getId());
    }

    public Callback<List<Purchase>> mCallbackPurchase = new Callback<List<Purchase>>() {
        @Override
        public void onResponse(Call<List<Purchase>> call, Response<List<Purchase>> response) {
            int code = response.code();
            mIsLoading = false;

            switch (code){
                case 200:
                    Log.d(TAG, String.valueOf(code));
                    purchases = response.body();
                    //doctorAdapter.swap(doctors);

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
        public void onFailure(Call<List<Purchase>> call, Throwable t) {
            progressDialog.dismiss();
            Snackbar.make(getActivity().getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    };

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

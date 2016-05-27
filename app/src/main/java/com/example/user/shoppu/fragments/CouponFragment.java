package com.example.user.shoppu.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.shoppu.DB.CouponDataSource;
import com.example.user.shoppu.DrawerActivity;
import com.example.user.shoppu.R;
import com.example.user.shoppu.Utils.Utils;
import com.example.user.shoppu.adapter.CouponAdapter;
import com.example.user.shoppu.models.Coupon;
import com.example.user.shoppu.models.UserAttributes;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CouponFragment extends Fragment {
    public static final String TAG = CouponFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private ProgressDialog progressDialog;
    private Activity activity;
    private LinearLayoutManager mLayoutManager;
    private UserAttributes currentUser;
    public DrawerActivity drawerActivity = null;
    public Toolbar toolbar = null;
    private boolean mIsLoading = false;
    private List<Coupon> coupons;
    private CouponAdapter couponAdapter;
    private CouponDataSource data;

    @Bind(R.id.recycler_view_coupons)
    public RecyclerView recyclerViewDoctores;

    public CouponFragment() {
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
        View view = inflater.inflate(R.layout.fragment_coupon, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);
        drawerActivity = (DrawerActivity) getActivity();
        setToolbar(view);
        getUserData();

        data = new CouponDataSource(getActivity().getApplicationContext());
        data.open();
        this.coupons = data.getAllCoupons();
        data.close();

        couponAdapter = new CouponAdapter(activity, coupons);
        recyclerViewDoctores.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerViewDoctores.setLayoutManager(mLayoutManager);
        recyclerViewDoctores.setAdapter(couponAdapter);

        return view;
    }

    private void getUserData() {
        String jsonUser = getArguments().getString(getString(R.string.user_key), "");
        currentUser = Utils.toUserAtributtes(jsonUser);
        activity = this.getActivity();
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_default);
        drawerActivity.setSupportActionBar(toolbar);
        drawerActivity.getSupportActionBar().setTitle(getString(R.string.products));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                drawerActivity, drawerActivity.drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerActivity.drawer.setDrawerListener(toggle);
        toggle.syncState();
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

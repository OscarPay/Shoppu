package com.example.user.shoppu.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.shoppu.DrawerActivity;
import com.example.user.shoppu.R;
import com.example.user.shoppu.Utils.Utils;
import com.example.user.shoppu.models.UserAttributes;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ProfileFragment extends Fragment {

    @Bind(R.id.textView_phone)
    public TextView textView_phone;

    public DrawerActivity drawerActivity = null;
    public Toolbar toolbar = null;
    private OnFragmentInteractionListener mListener;
    private UserAttributes currentUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bar_toolbar_profile, container, false);
        ButterKnife.bind(this, view);

        drawerActivity = (DrawerActivity) getActivity();

        setToolbar(view);

        String jsonUser = getArguments().getString(getString(R.string.user_key), "");
        currentUser = Utils.toUserAtributtes(jsonUser);


        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapse_toolbar);
        collapsingToolbarLayout.setTitle(currentUser.getFullName());

        setProfileData();

        // Inflate the layout for this fragment
        return view;
    }

    private void setProfileData() {
        textView_phone.setText(currentUser.getMobile());
    }

    private void setToolbar(View view) {
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        drawerActivity.setSupportActionBar(toolbar);
        //drawerActivity.getSupportActionBar().setTitle(getString(R.string.profile));

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

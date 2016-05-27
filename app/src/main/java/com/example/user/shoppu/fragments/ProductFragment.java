package com.example.user.shoppu.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.user.shoppu.DrawerActivity;
import com.example.user.shoppu.R;
import com.example.user.shoppu.Utils.Utils;
import com.example.user.shoppu.adapter.ProductAdapter;
import com.example.user.shoppu.models.Product;
import com.example.user.shoppu.models.UserAttributes;
import com.example.user.shoppu.remote.ProductAPI;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment {
    private static final String TAG = ProductFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private UserAttributes currentUser;
    private List<Product> products;
    private Activity activity;
    private ProductAdapter productAdapter;
    private ProgressDialog progressDialog;
    private LinearLayoutManager mLayoutManager;
    public DrawerActivity drawerActivity = null;
    public Toolbar toolbar = null;
    private boolean mIsLoading = false;

    @Bind(R.id.recycler_view_products)
    public RecyclerView recyclerViewDoctores;
    @Bind(R.id.btn_purchase)
    public Button purchaseBtn;

    public ProductFragment() {
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
        View view =  inflater.inflate(R.layout.fragment_product, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        drawerActivity = (DrawerActivity) getActivity();

        setToolbar(view);
        getUserData();
        getListProducts();

        productAdapter = new ProductAdapter(activity, products);
        recyclerViewDoctores.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerViewDoctores.setLayoutManager(mLayoutManager);
        recyclerViewDoctores.setAdapter(productAdapter);

        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Product> products = productAdapter.getSelectedItems();
                //Se realiza la compra
                Utils.saveListToPurchase(products);
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.content_drawer, new BuyerFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void getListProducts(){
        showLoadingDialog();
        fetchProducts(mCallbackProducts);
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

    private void showLoadingDialog() {
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        mIsLoading = true;
    }

    private void fetchProducts(Callback<List<Product>> callback){
        ProductAPI.Factory.getInstance().getProducts(currentUser.getToken()).enqueue(callback);
    }

    public Callback<List<Product>> mCallbackProducts = new Callback<List<Product>>() {
        @Override
        public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
            int code = response.code();
            mIsLoading = false;

            switch (code){
                case 200:
                    Log.d(TAG, String.valueOf(code));
                    products = response.body();
                    productAdapter.swap(products);

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
        public void onFailure(Call<List<Product>> call, Throwable t) {
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

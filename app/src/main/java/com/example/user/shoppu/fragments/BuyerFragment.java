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

import com.example.user.shoppu.DB.CouponDataSource;
import com.example.user.shoppu.DB.InvoiceDataSource;
import com.example.user.shoppu.DrawerActivity;
import com.example.user.shoppu.R;
import com.example.user.shoppu.Utils.Utils;
import com.example.user.shoppu.adapter.BuyerAdapter;
import com.example.user.shoppu.models.ConceptsAttribute;
import com.example.user.shoppu.models.Product;
import com.example.user.shoppu.models.Purchase;
import com.example.user.shoppu.models.Transaction;
import com.example.user.shoppu.models.UserAttributes;
import com.example.user.shoppu.remote.InvoiceAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuyerFragment extends Fragment {
    public static final String TAG = BuyerFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private ProgressDialog progressDialog;
    private Activity activity;
    private LinearLayoutManager mLayoutManager;
    private UserAttributes currentUser;
    public DrawerActivity drawerActivity = null;
    public Toolbar toolbar = null;
    private boolean mIsLoading = false;
    private List<Product> products;
    private BuyerAdapter buyerAdapter;

    @Bind(R.id.recycler_view_buying)
    public RecyclerView recyclerViewDoctores;
    @Bind(R.id.btn_confirm)
    public Button purchaseBtn;


    private CouponDataSource couponDataSource;
    private InvoiceDataSource invoiceDataSource;
    public BuyerFragment() {
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
        View view = inflater.inflate(R.layout.fragment_buyer, container, false);
        ButterKnife.bind(this, view);

        setHasOptionsMenu(true);

        this.couponDataSource = new CouponDataSource(getActivity().getApplicationContext());
        this.invoiceDataSource = new InvoiceDataSource(getActivity().getApplicationContext());

        drawerActivity = (DrawerActivity) getActivity();
        setToolbar(view);
        getUserData();
        //Se pone la lista de objetos a comprar
        this.products =  Utils.getProductsToBuy();

        buyerAdapter = new BuyerAdapter(activity, products);
        recyclerViewDoctores.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(activity);
        recyclerViewDoctores.setLayoutManager(mLayoutManager);
        recyclerViewDoctores.setAdapter(buyerAdapter);

        purchaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingDialog();

                List<Product> products = buyerAdapter.getProducts();
                List<ConceptsAttribute> conceptsAttributes = new ArrayList<ConceptsAttribute>();
                int total = 0;
                for(Product p: products){
                    ConceptsAttribute toBuy = new ConceptsAttribute();
                    toBuy.setProductId(p.getId());
                    toBuy.setQuantity(Double.toString(p.getQuantity()));
                    conceptsAttributes.add(toBuy);
                    total += p.getQuantity()* Double.parseDouble(p.getPrice());
                }

                Transaction transaction = new Transaction();
                transaction.setConceptsAttributes(conceptsAttributes);
                transaction.setUserId(Integer.toString((currentUser.getId())));
                transaction.setTotal(Integer.toString(total));
                transaction.setDate("27/05/2016");

                InvoiceAPI.Factory.getInstance().buyProducts(currentUser.getToken(), transaction).enqueue(mCallbackProducts);

            }
        });

        return view;
    }

    private void showLoadingDialog() {
        progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(getString(R.string.loading));
        progressDialog.show();
        mIsLoading = true;
    }

    public Callback<Purchase> mCallbackProducts = new Callback<Purchase>() {
        @Override
        public void onResponse(Call<Purchase> call, Response<Purchase> response) {
            int code = response.code();
            mIsLoading = false;

            switch (code){
                case 201:
                    Log.d(TAG, String.valueOf(code));
                    Purchase purchase = response.body();
                    invoiceDataSource.open();
                    invoiceDataSource.insertInvoice(purchase.getInvoice());
                    invoiceDataSource.close();

                    couponDataSource.open();
                    couponDataSource.insertCoupon(purchase.getCoupon());
                    couponDataSource.close();

                    Bundle bundle = new Bundle();
                    bundle.putString(getString(R.string.user_key), new Gson().toJson(currentUser));
                    PurchaseHistoryFragment fragment = new PurchaseHistoryFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.content_drawer, fragment);
                    fragmentTransaction.commit();

                    break;
                default:
                    //Log.e(TAG, String.valueOf(code));
                    try {
                        Snackbar.make(getActivity().getCurrentFocus(), "Error del Servidor" + response.errorBody().string(), Snackbar.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage());
                    }
            }

            progressDialog.dismiss();
        }

        @Override
        public void onFailure(Call<Purchase> call, Throwable t) {
            progressDialog.dismiss();
            Snackbar.make(getActivity().getCurrentFocus(), t.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    };

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

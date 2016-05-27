package com.example.user.shoppu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.shoppu.R;
import com.example.user.shoppu.models.Purchase;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kev' on 26/05/2016.
 */
public class PurchasesAdapter extends RecyclerView.Adapter<PurchasesAdapter.ViewHolder>  {
    private final Context context;
    private List<Purchase> purchaseList;

    public PurchasesAdapter(Context context, List<Purchase> purchaseList) {
        this.context = context;
        if (purchaseList == null){
            purchaseList = new ArrayList<Purchase>();
        }
        this.purchaseList = purchaseList;
    }

    public void swap(List<Purchase> purchaseList){
        this.purchaseList.clear();
        this.purchaseList.addAll(purchaseList);
        notifyDataSetChanged();
    }

    public void add(List<Purchase> purchaseList){
        this.purchaseList.addAll(purchaseList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_purchase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Purchase currentPurchase = purchaseList.get(position);
        holder.txt_purchase.setText(currentPurchase.getInvoice().getTotal());
    }

    @Override
    public int getItemCount() {
        return purchaseList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.container)
        public LinearLayout rl_container;

        @Bind(R.id.purchase_product)
        public TextView txt_purchase;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

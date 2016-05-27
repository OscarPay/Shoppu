package com.example.user.shoppu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.shoppu.R;
import com.example.user.shoppu.models.Coupon;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kev' on 27/05/2016.
 */
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private final Context context;
    private List<Coupon> productList;

    public CouponAdapter(Context context, List<Coupon> productList) {
        this.context = context;
        if (productList == null){
            productList = new ArrayList<Coupon>();
        }
        this.productList = productList;
    }

    public void swap(List<Coupon> productList){
        this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    public void add(List<Coupon> productList){
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coupon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Coupon currentCoupon = productList.get(position);

        holder.txt_name.setText(currentCoupon.getDiscount());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public List<Coupon> getProducts(){
        return productList;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.container)
        LinearLayout layout;
        @Bind(R.id.discount_quantity)
        public TextView txt_name;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

package com.example.user.shoppu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.user.shoppu.R;
import com.example.user.shoppu.models.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kev' on 27/05/2016.
 */
public class BuyerAdapter extends RecyclerView.Adapter<BuyerAdapter.ViewHolder>{
    private final Context context;
    private List<Product> productList;

    public BuyerAdapter(Context context, List<Product> productList) {
        this.context = context;
        if (productList == null){
            productList = new ArrayList<Product>();
        }
        this.productList = productList;
    }

    public void swap(List<Product> productList){
        this.productList.clear();
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    public void add(List<Product> productList){
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_buy, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product currentProduct = productList.get(position);
        holder.txt_name.setText(currentProduct.getName());
        holder.txt_quantity.setText(Integer.toString(currentProduct.getQuantity()));

        holder.minus.setTag(currentProduct);
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                Product product = (Product) button.getTag();

                product.reduceQuantity();
                Log.d("TEST", product.getName() + product.getQuantity());
                notifyDataSetChanged();
            }
        });

        holder.plus.setTag(currentProduct);
        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                Product product = (Product) button.getTag();

                product.increaseQuantity();
                Log.d("TEST", product.getName() + product.getQuantity());
                notifyDataSetChanged();
            }
        });
    }

    public List<Product> getProducts(){
        return productList;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.textView_product_name)
        public TextView txt_name;
        @Bind(R.id.textView_quantity)
        public TextView txt_quantity;
        @Bind(R.id.button_minus)
        public Button minus;
        @Bind(R.id.button_plus)
        public Button plus;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

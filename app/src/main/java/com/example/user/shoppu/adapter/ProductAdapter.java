package com.example.user.shoppu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.shoppu.R;
import com.example.user.shoppu.models.Product;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kev' on 26/05/2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{
    private final Context context;
    private List<Product> productList;

    public ProductAdapter(Context context, List<Product> productList) {
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

    public List<Product> getSelectedItems(){
        List<Product> selectedProducts = new ArrayList<>();
        for(Product p: productList){
            if(p.isSelected()){
                selectedProducts.add(p);
            }
        }
        return selectedProducts;
    }

    public void add(List<Product> productList){
        this.productList.addAll(productList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product currentProduct = productList.get(position);
        holder.txt_name.setText(currentProduct.getName());
        holder.txt_price.setText(currentProduct.getPrice());
        holder.txt_brand.setText(currentProduct.getBrand());
        holder.checkBox.setChecked(currentProduct.isSelected());
        holder.checkBox.setTag(currentProduct);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                Product product = (Product) cb.getTag();

                product.setSelected(cb.isChecked());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.container)
        public LinearLayout rl_container;

        @Bind(R.id.product_name)
        public TextView txt_name;
        @Bind(R.id.product_price)
        public TextView txt_price;
        @Bind(R.id.product_brand)
        public TextView txt_brand;
        @Bind(R.id.checkbox_product)
        public CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

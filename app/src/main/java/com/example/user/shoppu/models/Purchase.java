
package com.example.user.shoppu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;
@Generated("org.jsonschema2pojo")
public class Purchase {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("products")
    @Expose
    private List<Product> products = new ArrayList<Product>();

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     *     The products
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     *
     * @param products
     *     The products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }


}

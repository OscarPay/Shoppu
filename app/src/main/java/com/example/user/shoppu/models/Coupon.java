
package com.example.user.shoppu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Coupon {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("discount")
    @Expose
    private String discount;

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
     *     The discount
     */
    public String getDiscount() {
        return discount;
    }

    /**
     * 
     * @param discount
     *     The discount
     */
    public void setDiscount(String discount) {
        this.discount = discount;
    }


}

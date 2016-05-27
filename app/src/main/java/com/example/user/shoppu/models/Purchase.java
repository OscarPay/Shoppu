
package com.example.user.shoppu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Purchase {

    @SerializedName("invoice")
    @Expose
    private Invoice invoice;
    @SerializedName("coupon")
    @Expose
    private Coupon coupon;

    /**
     * 
     * @return
     *     The invoice
     */
    public Invoice getInvoice() {
        return invoice;
    }

    /**
     * 
     * @param invoice
     *     The invoice
     */
    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    /**
     * 
     * @return
     *     The coupon
     */
    public Coupon getCoupon() {
        return coupon;
    }

    /**
     * 
     * @param coupon
     *     The coupon
     */
    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

}

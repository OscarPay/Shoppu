
package com.example.user.shoppu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Transaction {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("concepts_attributes")
    @Expose
    private List<ConceptsAttribute> conceptsAttributes = new ArrayList<ConceptsAttribute>();

    /**
     * 
     * @return
     *     The userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The total
     */
    public String getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(String total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The conceptsAttributes
     */
    public List<ConceptsAttribute> getConceptsAttributes() {
        return conceptsAttributes;
    }

    /**
     * 
     * @param conceptsAttributes
     *     The concepts_attributes
     */
    public void setConceptsAttributes(List<ConceptsAttribute> conceptsAttributes) {
        this.conceptsAttributes = conceptsAttributes;
    }

}

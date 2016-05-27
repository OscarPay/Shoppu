
package com.example.user.shoppu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class User {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_attributes")
    @Expose
    private UserAttributes userAttributes;

    public User(String name, String lastname, String email, String password, String passwordConfirm, String number){
        this.userAttributes = new UserAttributes(name,lastname,email,password,passwordConfirm,number);
    }

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The user
     */
    public UserAttributes getUserAttributes() {
        return userAttributes;
    }

    /**
     * 
     * @param userAttributes
     *     The user
     */
    public void setUserAttributes(UserAttributes userAttributes) {
        this.userAttributes = userAttributes;
    }

}

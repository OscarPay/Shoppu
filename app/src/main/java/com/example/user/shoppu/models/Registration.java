package com.example.user.shoppu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Kev' on 16/05/2016.
 */
@Generated("org.jsonschema2pojo")
public class Registration {
    @SerializedName("user")
    @Expose
    private User user;

    public Registration(String name, String lastname, String email, String password, String passwordConfirm, String number){
        this.user = new User(name,lastname,password,passwordConfirm,email,number);
    }

    /**
     *
     * @return
     *     The User
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     *     The User
     */
    public void setBloodType(User user) {
        this.user = user;
    }
}

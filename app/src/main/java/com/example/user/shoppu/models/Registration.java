package com.example.user.shoppu.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

/**
 * Created by Kev' on 16/05/2016.
 */
@Generated("org.jsonschema2pojo")
public class Registration {
    @SerializedName("patient")
    @Expose
    private Patient patient;

    public Registration(String name, String lastname, String email, String password, String passwordConfirm, String number){
        this.patient = new Patient(name,lastname,password,passwordConfirm,email,number);
    }

    /**
     *
     * @return
     *     The Patient
     */
    public Patient getPatient() {
        return patient;
    }

    /**
     *
     * @param patient
     *     The Patient
     */
    public void setBloodType(Patient patient) {
        this.patient = patient;
    }
}

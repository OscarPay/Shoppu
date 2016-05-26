package com.example.user.shoppu.Utils;

import com.example.user.shoppu.models.Patient;
import com.google.gson.Gson;

/**
 * Created by USER on 10/05/2016.
 */
public class Utils {

    public static Patient toUserAtributtes(String json){
        Gson gson = new Gson();
        Patient patient = gson.fromJson(json, Patient.class);
        return patient;
    }
}

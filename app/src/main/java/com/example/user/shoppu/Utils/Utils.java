package com.example.user.shoppu.Utils;

import com.example.user.shoppu.models.UserAttributes;
import com.google.gson.Gson;

/**
 * Created by USER on 10/05/2016.
 */
public class Utils {

    public static UserAttributes toUserAtributtes(String json){
        Gson gson = new Gson();
        UserAttributes user = gson.fromJson(json, UserAttributes.class);
        return user;
    }
}

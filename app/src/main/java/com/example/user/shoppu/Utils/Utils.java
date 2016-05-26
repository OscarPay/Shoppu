package com.example.user.shoppu.Utils;

import com.example.user.shoppu.models.User;
import com.google.gson.Gson;

/**
 * Created by USER on 10/05/2016.
 */
public class Utils {

    public static User toUserAtributtes(String json){
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        return user;
    }
}

package com.example.user.shoppu.Utils;

import com.example.user.shoppu.models.Product;
import com.example.user.shoppu.models.UserAttributes;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by USER on 10/05/2016.
 */
public class Utils {
    private static List<Product> productsToBuy = new ArrayList<>();

    public static UserAttributes toUserAtributtes(String json){
        Gson gson = new Gson();
        UserAttributes user = gson.fromJson(json, UserAttributes.class);
        return user;
    }

    public static void saveListToPurchase(List<Product> productList){
        productsToBuy = productList;
    }

    public static List<Product> getProductsToBuy(){
        return productsToBuy;
    }

}

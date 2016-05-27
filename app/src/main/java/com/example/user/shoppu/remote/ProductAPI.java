package com.example.user.shoppu.remote;

import com.example.user.shoppu.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

/**
 * Created by Kev' on 26/05/2016.
 */
public interface ProductAPI {
    String BASE_URL = "http://shoppu.herokuapp.com";

    @Headers("Content-Type: application/json")
    @GET("/api/products/")
    Call<List<Product>> getProducts(@Header("Token") String token);

    class Factory {
        public static ProductAPI service;

        public static ProductAPI getInstance(){
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(ProductAPI.class);
            }

            return service;
        }
    }
}

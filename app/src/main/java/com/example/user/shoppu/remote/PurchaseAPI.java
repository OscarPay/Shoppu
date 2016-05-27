package com.example.user.shoppu.remote;

import com.example.user.shoppu.models.Purchase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Kev' on 26/05/2016.
 */
public interface PurchaseAPI {
    String BASE_URL = "http://shoppu.herokuapp.com";

    @Headers("Content-Type: application/json")
    @GET("/api/user/{id}/purchase")
    Call<List<Purchase>> getPurchase(@Header("Token") String token, @Path("id") int patientID);

    class Factory {
        public static PurchaseAPI service;

        public static PurchaseAPI getInstance(){
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(PurchaseAPI.class);
            }

            return service;
        }
    }
}

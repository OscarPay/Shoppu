package com.example.user.shoppu.remote;

import com.example.user.shoppu.models.Purchase;
import com.example.user.shoppu.models.Transaction;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Kev' on 26/05/2016.
 */
public interface InvoiceAPI {
    String BASE_URL = "http://shoppu-server.herokuapp.com/";

    @Headers("Content-Type: application/json")
    @POST("/api/invoices")
    Call<Purchase> buyProducts(@Header("Authorization") String token,@Body Transaction transaction);

    class Factory {
        public static InvoiceAPI service;

        public static InvoiceAPI getInstance(){
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(InvoiceAPI.class);
            }

            return service;
        }
    }
}

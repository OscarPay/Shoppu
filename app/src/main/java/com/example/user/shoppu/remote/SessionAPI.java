package com.example.user.shoppu.remote;

import com.example.user.shoppu.models.Session;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by USER on 01/05/2016.
 */
public interface SessionAPI {

    String BASE_URL = "http://medicalia.herokuapp.com";

    @Headers("Content-Type: application/json")
    @POST("/api/sessions")
    Call<ResponseBody> login(@Body Session session);

    class Factory {
        public static SessionAPI service;

        public static SessionAPI getInstance(){
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(SessionAPI.class);
            }

            return service;
        }
    }

}

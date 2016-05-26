package com.example.user.shoppu.remote;

import com.example.user.shoppu.models.Doctor;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by USER on 15/05/2016.
 */
public interface DoctorAPI {
    String BASE_URL = "http://medicalia.herokuapp.com/";

    @Headers("Content-Type: application/json")
    @GET("api/doctors")
    Call<List<Doctor>> getDoctors(@Header("Authorization") String token,
                                  @Query("name") String name,
                                  @Query("specialty") String specialty,
                                  @Query("hospital") String hospital,
                                  @Query("page") String page);

    class Factory {
        public static DoctorAPI service;

        public static DoctorAPI getInstance(){
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(DoctorAPI.class);
            }

            return service;
        }
    }

}

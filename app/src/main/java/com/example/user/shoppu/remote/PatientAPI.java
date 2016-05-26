package com.example.user.shoppu.remote;

import com.example.user.shoppu.models.Patient;
import com.example.user.shoppu.models.Registration;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Kev' on 02/05/2016.
 */
public interface PatientAPI {
    String BASE_URL = "http://medicalia.herokuapp.com";

    @Headers("Content-Type: application/json")
    @POST("/api/patients")
    Call<ResponseBody> register(@Body Registration registration);
    @Headers("Content-Type: application/json")
    @PATCH("/api/patients/{id}")
    Call<ResponseBody> update(@Header("Token") String token, @Path("id") int patientID, @Body Patient patient);

    class Factory {
        public static PatientAPI service;

        public static PatientAPI getInstance(){
            if (service == null) {

                Retrofit retrofit = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(BASE_URL)
                        .build();

                service = retrofit.create(PatientAPI.class);
            }

            return service;
        }
    }
}

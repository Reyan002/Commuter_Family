package com.example.commuterfamily.Interfaces;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface FirebaseAPI {

    @POST("/fcm/send")
    Call<Messege> sendMessage(@Body Messege message);

}
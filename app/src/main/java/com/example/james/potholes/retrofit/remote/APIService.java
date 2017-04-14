package com.example.james.potholes.retrofit.remote;

import com.example.james.potholes.retrofit.model.pothole.Pothole;
import com.example.james.potholes.retrofit.model.user.Authenticate;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by James on 4/13/2017.
 */

public interface APIService {

    @POST("/user/authenticate")
    @FormUrlEncoded
    Call<Authenticate> authenticate(@Field("displayName") String displayName,
                                @Field("password") String password);

    @GET
    Call<Pothole> potholeByID(@Url String url);


}
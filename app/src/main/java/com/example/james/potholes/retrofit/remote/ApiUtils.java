package com.example.james.potholes.retrofit.remote;

/**
 * Created by James on 4/13/2017.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://138.68.13.148:3000/";

    public static APIService getAPIService(String accessToken) {

        return RetrofitClient.getClient(BASE_URL, accessToken).create(APIService.class);
    }
}


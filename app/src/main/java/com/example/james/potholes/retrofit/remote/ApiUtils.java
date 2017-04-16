package com.example.james.potholes.retrofit.remote;

import com.example.james.potholes.models.AuthModel;

/**
 * Created by James on 4/13/2017.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://138.68.13.148:3000/";

    public static APIService getAPIService(AuthModel authModel) {

        return RetrofitClient.getClient(BASE_URL, authModel).create(APIService.class);
    }

    public static APIService getNoAuthAPIService() {

        return RetrofitClient.getNoAuthClient(BASE_URL).create(APIService.class);
    }
}


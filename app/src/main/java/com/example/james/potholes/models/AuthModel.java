package com.example.james.potholes.models;

import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.example.james.potholes.retrofit.model.user.Authenticate;

/**
 * Created by James on 4/15/2017.
 */

public class AuthModel {

    private String accessToken;
    private long expireUnixTime;
    private int userid;

    public AuthModel(Authenticate auth)
    {
        accessToken = auth.getToken();
        //JWT jwt = new JWT(auth.getToken());

        //expireUnixTime = jwt.getExpiresAt().getTime();

        //Log.d("expire",expireUnixTime+"");
    }

    public String getAccessToken() {
        return accessToken;
    }
}

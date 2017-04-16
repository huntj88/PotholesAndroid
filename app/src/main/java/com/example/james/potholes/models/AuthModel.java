package com.example.james.potholes.models;

import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.example.james.potholes.retrofit.model.user.Authenticate;

/**
 * Created by James on 4/15/2017.
 */

public class AuthModel {

    //this class is set up to be immutable.
    // currently only using accessToken field, but will need the other stuff later.

    final private String accessToken;
    final private long expireUnixTime = 0;
    final private int userid = 0;

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

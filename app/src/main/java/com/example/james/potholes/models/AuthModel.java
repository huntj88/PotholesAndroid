package com.example.james.potholes.models;

import com.auth0.android.jwt.JWT;

/**
 * Created by James on 4/13/2017.
 */

public class AuthModel {
    private final String accessToken;
    private final String displayName;
    private final String password;

    public AuthModel(String accessToken)
    {
        this.displayName = "guest";
        this.password = "guest";
        this.accessToken = accessToken;
    }

    public AuthModel(String displayName, String password)
    {
        this.displayName = displayName;
        this.password = password;
        this.accessToken = "";
    }

    public AuthModel(String displayName, String password, String accessToken)
    {
        this.displayName = displayName;
        this.password = password;
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLoggedIn()
    {
        JWT jwt = new JWT(getAccessToken());
        boolean isExpired = jwt.isExpired(10);
        return getAccessToken().length()!=0 && !isExpired;
    }
}

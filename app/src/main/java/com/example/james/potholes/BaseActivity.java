package com.example.james.potholes;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.example.james.potholes.retrofit.model.pothole.Pothole;
import com.example.james.potholes.retrofit.model.user.Authenticate;
import com.example.james.potholes.retrofit.remote.APIService;
import com.example.james.potholes.retrofit.remote.ApiUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 4/13/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected APIService mAPIService;
    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIService = ApiUtils.getAPIService(getLocalAuthToken());
        TAG = getClass().getSimpleName();
    }

    protected void getPotholeByID(int potholeID)
    {
        mAPIService.potholeByID("pothole/"+potholeID).enqueue(new Callback<Pothole>() {
            @Override
            public void onResponse(Call<Pothole> call, Response<Pothole> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG,response.body().getLocation().getX()+"");
                }
            }

            @Override
            public void onFailure(Call<Pothole> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    protected void setNewAuthToken(String displayName, String password)
    {
        mAPIService.authenticate(displayName, password).enqueue(new Callback<Authenticate>() {
            @Override
            public void onResponse(Call<Authenticate> call, Response<Authenticate> response) {
                if(response.isSuccessful()) {
                    setAuthToken(response.body().getToken());
                }
            }

            @Override
            public void onFailure(Call<Authenticate> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    protected String getLocalAuthToken() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.access_token), "");

        if(isLoggedIn(token))
            return token;
        else
            return "";

    }

    protected void setAuthToken(String token)
    {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.access_token), token);
        editor.commit();
    }

    protected boolean isLoggedIn(String token)
    {
        JWT jwt = new JWT(token);
        boolean isExpired = jwt.isExpired(10);
        return token.length()!=0 && !isExpired;
    }
}

package com.example.james.potholes.util;

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

    protected String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TAG = getClass().getSimpleName();
    }

}

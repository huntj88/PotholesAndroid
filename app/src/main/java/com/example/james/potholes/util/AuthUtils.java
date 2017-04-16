package com.example.james.potholes.util;

import android.util.Log;

import com.example.james.potholes.models.AuthModel;
import com.example.james.potholes.retrofit.model.user.Authenticate;
import com.example.james.potholes.retrofit.remote.APIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 4/15/2017.
 */

public class AuthUtils {

    private static String TAG = "AuthUtils";

    public static void getToken(APIService apiService, final AuthListener authListener)
    {
        apiService.authenticate("guest", "guest").enqueue(new Callback<Authenticate>() {
            @Override
            public void onResponse(Call<Authenticate> call, Response<Authenticate> response) {
                if(response.isSuccessful()) {
                    authListener.gotAuthModel(new AuthModel(response.body()));
                }
            }

            @Override
            public void onFailure(Call<Authenticate> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public interface AuthListener
    {
        void gotAuthModel(AuthModel authModel);
    }
}

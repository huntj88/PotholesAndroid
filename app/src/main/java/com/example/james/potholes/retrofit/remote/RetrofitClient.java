package com.example.james.potholes.retrofit.remote;

import android.util.Log;

import com.example.james.potholes.models.AuthModel;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by James on 4/13/2017.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static String TAG = "RetrofitClient";

    public static Retrofit getClient(String baseUrl, final AuthModel authModel) {
        if (retrofit==null) {

            Interceptor interceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    final Request request = chain.request().newBuilder()
                            .addHeader("access-token", authModel.getAccessToken())
                            .build();

                    Log.d(TAG,authModel.getAccessToken()+"");
                    return chain.proceed(request);
                }
            };
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(interceptor);
            OkHttpClient client = httpClient.build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


    public static Retrofit getNoAuthClient(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}

package com.example.james.potholes;

import android.content.Context;
import android.util.Log;

import com.example.james.potholes.models.AuthModel;
import com.example.james.potholes.models.PotholeModel;
import com.example.james.potholes.retrofit.model.pothole.Pothole;
import com.example.james.potholes.retrofit.model.user.Authenticate;
import com.example.james.potholes.retrofit.remote.APIService;
import com.example.james.potholes.retrofit.remote.ApiUtils;
import com.example.james.potholes.util.SavedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 4/13/2017.
 */

public class PotholePresenter {

    private PotholeView view;
    private Context context;
    private PotholeModel potholeModel;
    private AuthModel authModel;
    private APIService apiService;
    private String TAG = "pothole presentor";

    public PotholePresenter(PotholeView view, Context context, String accessToken)
    {
        this.view = view;
        this.context = context;
        potholeModel = new PotholeModel(true,null,null);
        authModel = new AuthModel(accessToken);
        apiService = ApiUtils.getAPIService(authModel.getAccessToken());
    }

    public void tokenRefreshed()
    {
        apiService = ApiUtils.getAPIService(authModel.getAccessToken());
    }

    public void getNewAccessToken()
    {
        apiService.authenticate(authModel.getDisplayName(), authModel.getPassword()).enqueue(new Callback<Authenticate>() {
            @Override
            public void onResponse(Call<Authenticate> call, Response<Authenticate> response) {
                if(response.isSuccessful()) {
                    authModel = new AuthModel(authModel.getDisplayName(),authModel.getPassword(),response.body().getToken());
                    tokenRefreshed();
                    SavedPrefsUtils.setLocalAccessToken(context,authModel.getAccessToken());
                }
            }

            @Override
            public void onFailure(Call<Authenticate> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void getAllPotholes()
    {

        potholeModel = new PotholeModel(true,potholeModel.getPotholes(),null);
        view.render(potholeModel);

        apiService.getAllPotholes().enqueue(new Callback<List<Pothole>>() {
            @Override
            public void onResponse(Call<List<Pothole>> call, Response<List<Pothole>> response) {
                if(response.isSuccessful()) {
                    potholeModel = new PotholeModel(false,response.body(),null);
                    view.render(potholeModel);
                }
                else
                {
                    view.render(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Pothole>> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                potholeModel = new PotholeModel(false,potholeModel.getPotholes(),t);
                view.render(potholeModel);
            }
        });
    }

    public void getPotholeByID(int potholeID)
    {
        potholeModel = new PotholeModel(true,potholeModel.getPotholes(),null);
        view.render(potholeModel);

        apiService.potholeByID("pothole/"+potholeID).enqueue(new Callback<Pothole>() {
            @Override
            public void onResponse(Call<Pothole> call, Response<Pothole> response) {
                if(response.isSuccessful()) {
                    Log.d(TAG,response.body().getLocation().getX()+"");
                    List<Pothole> potholes = new ArrayList<>();
                    potholes.add(response.body());
                    potholeModel = new PotholeModel(false,potholes,null);
                    view.render(potholeModel);
                }
                else
                {
                    view.render(response.code());
                }
            }

            @Override
            public void onFailure(Call<Pothole> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                potholeModel = new PotholeModel(false,potholeModel.getPotholes(),t);
                view.render(potholeModel);
            }
        });
    }

    public interface PotholeView{
        void render(PotholeModel potholeModel);
        void render(int code);
    }
}

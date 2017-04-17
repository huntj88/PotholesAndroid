package com.example.james.potholes;

import android.util.Log;

import com.example.james.potholes.models.AuthModel;
import com.example.james.potholes.models.PotholeModel;
import com.example.james.potholes.retrofit.model.pothole.Pothole;
import com.example.james.potholes.retrofit.remote.APIService;
import com.example.james.potholes.retrofit.remote.ApiUtils;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by James on 4/13/2017.
 */

public class PotholePresenter {

    private PotholeView view;
    private PotholeModel potholeModel;
    private APIService apiService;
    private String TAG = "pothole presentor";
    private Realm realm;

    public PotholePresenter(PotholeView view, AuthModel authModel)
    {
        this.view = view;
        potholeModel = new PotholeModel(true,null,null);
        apiService = ApiUtils.getAPIService(authModel);
        realm = Realm.getDefaultInstance();
    }

    public void closeRealm()
    {
        realm.close();
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
                    realm.beginTransaction();

                    for(Pothole p: response.body())
                    {
                        realm.copyToRealmOrUpdate(p);
                    }
                    realm.commitTransaction();

                    /*RealmResults<Pothole> getAllPotholes = realm.where(Pothole.class).findAll();
                    Log.d(TAG,getAllPotholes.size()+"");*/

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

package com.example.james.potholes;

import android.util.Log;

import com.example.james.potholes.models.AuthModel;
import com.example.james.potholes.models.PotholeModel;
import com.example.james.potholes.retrofit.model.pothole.Pothole;
import com.example.james.potholes.retrofit.remote.APIService;
import com.example.james.potholes.retrofit.remote.ApiUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;

import io.realm.Realm;
import io.realm.RealmList;
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
    //private Realm realm;

    public PotholePresenter(PotholeView view, AuthModel authModel)
    {
        this.view = view;
        potholeModel = new PotholeModel(true,null,null);
        apiService = ApiUtils.getAPIService(authModel);
        //realm = Realm.getDefaultInstance();
    }


    /*public void combineTest()
    {

        Observable<RealmResults<Pothole>> realmObservable = Observable.create(new ObservableOnSubscribe<RealmResults<Pothole>>() {
            @Override
            public void subscribe(ObservableEmitter<RealmResults<Pothole>> observableEmitter) throws Exception {

                Realm realm = null;
                try { // I could use try-with-resources here
                    realm = Realm.getDefaultInstance();
                    RealmResults<Pothole> results = realm.where(Pothole.class).findAll();
                    observableEmitter.onNext(results);
                    observableEmitter.onComplete();

                } finally {
                    if(realm != null) {
                        realm.close();
                    }
                }
            }
        });

        Observable<List<Pothole>> apiObservable = apiService.getAllPotholes();


        Observable<List<Pothole>> combined = Observable.merge(realmObservable,apiObservable);


        combined.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.computation())
                .subscribeWith(new DisposableObserver<List<Pothole>>() {
                    @Override
                    public void onNext(List<Pothole> potholes) {

                        //Log.d(TAG,potholes.size()+"");
                        Log.d(TAG,Thread.currentThread().getName()+" thread");


                        if(potholes instanceof RealmList) {
                            Log.d(TAG, "realm list");
                            potholeModel = new PotholeModel(false,potholes,null);
                            view.render(potholeModel);

                        }
                        else
                        {
                            Log.d(TAG,potholes.getClass().getSimpleName());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"complete");
                    }
                });






    }*/

    public void getAllPotholes()
    {

        potholeModel = new PotholeModel(true,potholeModel.getPotholes(),null);
        view.render(potholeModel);

        apiService.getAllPotholes().enqueue(new Callback<List<Pothole>>() {
            @Override
            public void onResponse(Call<List<Pothole>> call, final Response<List<Pothole>> response) {
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

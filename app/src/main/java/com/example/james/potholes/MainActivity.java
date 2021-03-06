package com.example.james.potholes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.james.potholes.adapters.PotholeAdapter;
import com.example.james.potholes.models.AuthModel;
import com.example.james.potholes.models.PotholeModel;
import com.example.james.potholes.retrofit.model.pothole.Pothole;
import com.example.james.potholes.retrofit.remote.ApiUtils;
import com.example.james.potholes.util.AuthUtils;
import com.example.james.potholes.util.BaseActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements
        PotholePresenter.PotholeView,
        AuthUtils.AuthListener,
        OnMapReadyCallback {

    private PotholePresenter presenter;
    private GoogleMap mMap;
    @BindView(R.id.pothole_recycler)
    RecyclerView potholeRecyclerView;
    private PotholeAdapter potholeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_constraint);
        ButterKnife.bind(this);

        //potholeRecylerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        potholeRecyclerView.setLayoutManager(llm);


        setUpMapIfNeeded();

        AuthUtils.getToken(ApiUtils.getNoAuthAPIService(), this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setUpMapIfNeeded() {
        if (mMap == null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public void render(PotholeModel potholeModel) {
        Log.d(TAG, potholeModel.isLoading() + " " + potholeModel.getPotholes() + " ");

        if (potholeModel.getPotholes() != null) {
            for (Pothole p : potholeModel.getPotholes()) {
                LatLng potholeLocation = new LatLng(p.getLocation().getY(), p.getLocation().getX());
                mMap.addMarker(new MarkerOptions().position(potholeLocation)
                        .title(p.getPotholeID() + ""));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
            LatLng potholeLocation = new LatLng(potholeModel.getPotholes().get(0).getLocation().getY(), potholeModel.getPotholes().get(0).getLocation().getX());
            mMap.moveCamera(CameraUpdateFactory.newLatLng(potholeLocation));
            if (potholeAdapter == null) {
                potholeAdapter = new PotholeAdapter(potholeModel);
                potholeRecyclerView.setAdapter(new PotholeAdapter(potholeModel));
            } else {
                potholeAdapter.setPotholeModel(potholeModel);
                potholeAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void render(int code) {
        Log.d(TAG, code + "");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setMinZoomPreference(12.5f);
    }

    @Override
    public void gotAuthModel(AuthModel authModel) {
        Log.d(TAG,authModel.getAccessToken());
        presenter = new PotholePresenter(this, authModel);
        presenter.getAllPotholes();
    }
}

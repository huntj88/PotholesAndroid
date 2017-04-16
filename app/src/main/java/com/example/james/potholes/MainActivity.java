package com.example.james.potholes;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.james.potholes.models.PotholeModel;
import com.example.james.potholes.retrofit.model.pothole.Pothole;
import com.example.james.potholes.util.BaseActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements PotholePresenter.PotholeView, OnMapReadyCallback{

    private PotholePresenter presenter;
    private GoogleMap mMap;
    @BindView(R.id.pothole_recycler) RecyclerView potholeRecylerView;
    private PotholeAdapter potholeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        potholeRecylerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        potholeRecylerView.setLayoutManager(llm);

        setUpMapIfNeeded();
        presenter = new PotholePresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        if(mMap==null) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
    }


    @Override
    public void render(PotholeModel potholeModel) {
        Log.d(TAG,potholeModel.isLoading()+" "+potholeModel.getPotholes()+" ");

        if(potholeModel.getPotholes()!=null) {
            for (Pothole p : potholeModel.getPotholes()) {
                LatLng potholeLocation = new LatLng(p.getLocation().getY(), p.getLocation().getX());
                mMap.addMarker(new MarkerOptions().position(potholeLocation)
                        .title(p.getPotholeID() + ""));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            }
            if(potholeAdapter == null) {
                potholeAdapter = new PotholeAdapter(potholeModel);
                potholeRecylerView.setAdapter(new PotholeAdapter(potholeModel));
            } else {
                potholeAdapter.setPotholeModel(potholeModel);
                potholeAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void render(int code)
    {
        Log.d(TAG,code+"");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        presenter.getAllPotholes();
    }
}

package com.example.james.potholes;

import android.os.Bundle;
import android.util.Log;

import com.example.james.potholes.models.PotholeModel;
import com.example.james.potholes.util.BaseActivity;

public class MainActivity extends BaseActivity implements PotholePresenter.PotholeView{

    PotholePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new PotholePresenter(this);
        presenter.getAllPotholes();
    }

    @Override
    public void render(PotholeModel potholeModel) {
        Log.d(TAG,potholeModel.isLoading()+" "+potholeModel.getPotholes()+" ");
    }

    @Override
    public void render(int code)
    {
        Log.d(TAG,code+"");
    }
}

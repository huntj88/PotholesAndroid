package com.example.james.potholes;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setNewAuthToken("james","googleman2200");
        Log.d(TAG,getLocalAuthToken());

        getPotholeByID(2);
    }
}

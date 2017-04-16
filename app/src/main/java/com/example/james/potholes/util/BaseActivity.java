package com.example.james.potholes.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

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

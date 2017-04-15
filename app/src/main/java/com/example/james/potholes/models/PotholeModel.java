package com.example.james.potholes.models;

import com.example.james.potholes.retrofit.model.pothole.Pothole;

import java.util.List;

/**
 * Created by James on 4/13/2017.
 */

public class PotholeModel {

    private final boolean loading;
    private final List<Pothole> potholes;
    private final Throwable error;

    public PotholeModel(boolean loading, List<Pothole> potholes, Throwable error)
    {
        this.loading = loading;
        this.potholes = potholes;
        this.error = error;
    }

    public boolean isLoading() {
        return loading;
    }

    public List<Pothole> getPotholes() {
        return potholes;
    }

    public Throwable getError() {
        return error;
    }
}

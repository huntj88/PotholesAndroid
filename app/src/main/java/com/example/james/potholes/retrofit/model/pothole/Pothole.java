package com.example.james.potholes.retrofit.model.pothole;

/**
 * Created by James on 4/13/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pothole {

    @SerializedName("potholeID")
    @Expose
    private Integer potholeID;
    @SerializedName("userID")
    @Expose
    private Integer userID;
    @SerializedName("location")
    @Expose
    private Location location;

    public Integer getPotholeID() {
        return potholeID;
    }

    public void setPotholeID(Integer potholeID) {
        this.potholeID = potholeID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}

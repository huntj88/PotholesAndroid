package com.example.james.potholes.retrofit.model.pothole;

/**
 * Created by James on 4/13/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Pothole extends RealmObject{

    @PrimaryKey
    @SerializedName("potholeID")
    @Expose
    private Integer potholeID;

    @SerializedName("userID")
    @Expose
    private Integer userID;

    @SerializedName("location")
    @Expose
    private Location location;

    @SerializedName("fixed")
    @Expose
    private Integer fixed;

    @SerializedName("modified")
    @Expose
    private Integer modified;



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

    public Integer getFixed() {
        return fixed;
    }

    public void setFixed(Integer fixed) {
        this.fixed = fixed;
    }

    public Integer getModified() {
        return modified;
    }

    public void setModified(Integer modified) {
        this.modified = modified;
    }
}

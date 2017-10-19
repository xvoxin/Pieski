package com.example.xvoxin.pieski.Models;

import java.io.Serializable;

/**
 * Created by xvoxin on 15.10.2017.
 */
public class Markers implements Serializable{

    private String latitude;
    private String longitude;
    private String city;
    private String time;
    private int userId;

    public Markers(String latitude, String longitude, String city, String time, int userId){
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.time = time;
        this.userId = userId;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getCity() {
        return city;
    }

    public String getTime() {
        return time;
    }

    public int getUserId() {
        return userId;
    }
}

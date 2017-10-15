package com.example.xvoxin.pieski.Models;

/**
 * Created by xvoxin on 15.10.2017.
 */
public class Markers {

    private String latitude;
    private String longitude;
    private String city;
    private String time;

    public Markers(String latitude, String longitude, String city, String time){
        this.latitude = latitude;
        this.longitude = longitude;
        this.city = city;
        this.time = time;
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
}

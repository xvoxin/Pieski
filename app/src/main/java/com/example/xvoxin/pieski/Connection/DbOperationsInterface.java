package com.example.xvoxin.pieski.Connection;

import com.example.xvoxin.pieski.Models.Markers;

import java.util.ArrayList;

/**
 * Created by xvoxin on 19.10.2017.
 */
public interface DbOperationsInterface {

    void login(int id);

    void register(int id);

    void getMarkers(ArrayList<Markers> marks);

}

package com.example.xvoxin.pieski.Connection;

import com.example.xvoxin.pieski.Models.Markers;
import com.example.xvoxin.pieski.Models.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by xvoxin on 15.10.2017.
 */
public class DbOperations {
    private ConnectionClass connectionClass;

    public ArrayList<Markers> getMarkers() {
        ArrayList<Markers> markers = new ArrayList<Markers>();

        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                System.out.println("wrong");
            } else {

                String query = "select * from pieski;";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    String latitude = rs.getString("latitude");
                    String longitude = rs.getString("longitude");
                    String city = rs.getString("city");
                    String time = rs.getString("time");

                    markers.add(new Markers(latitude, longitude, city, time));
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return markers;
    }

    public int login(String login, String password){

        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                System.out.println("wrong");
            } else {

                String query = "select id, login, password from users where login = '" + login + "' and password = '" + password + "';";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    int id = rs.getInt("id");
                    return id;
                }
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 0;
        }

        return 0;
    }
}

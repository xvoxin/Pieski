package com.example.xvoxin.pieski;

import android.os.AsyncTask;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xvoxin on 07.10.2017.
 */
public class AddLocationToDb extends AsyncTask<String, String, String>{

    private String message = "";

    ConnectionClass connectionClass;

    public String getMessage(){
        return message;
    }

    @Override
    protected void onPostExecute(String result){
        message = result;
    }

    @Override
    protected String doInBackground(String... param){
        String latitude = param[0];
        String longitude = param[1];
        String city = param[2];
        String res = "";

        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                res = "Error in connection with SQL server";
            } else {
                Date dNow = new Date( );
                SimpleDateFormat ft = new SimpleDateFormat (" yyyy.MM.dd HH:mm:ss");

                System.out.println("Current Date: " + ft.format(dNow));
                String query = "insert into pieski(latitude, longitude, city, czas) values('"+ latitude + "', '" + longitude +"', '"+ city +"', '"+ String.valueOf(ft.format(dNow)) +"'); ";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                res = "OK";
            }
        }
        catch (Exception ex)
        {
            res = "Exceptions";
        }

        return res;
    }
}

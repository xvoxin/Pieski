package com.example.xvoxin.pieski.Connection;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by xvoxin on 07.10.2017.
 */
public class AddLocationToDb extends AsyncTask<String, String, String>{

    private Activity activity;
    private ConnectionClass connectionClass;
    private Toast alert;

    public AddLocationToDb(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPostExecute(String result){
        alert = Toast.makeText(activity.getApplicationContext(), result, Toast.LENGTH_SHORT);
        alert.show();
    }

    @Override
    protected String doInBackground(String... param){
        String latitude = param[0];
        String longitude = param[1];
        String city = param[2];
        String time = param[3];
        int userId = Integer.parseInt(param[4]);
        String res = "";

        connectionClass = new ConnectionClass();

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                res = "Error in connection with SQL server";
            } else {
                String query = "insert into pieski(latitude, longitude, city, time, userId) values('"+ latitude + "', '" + longitude +"', '"+ city +"', '"+ time +"', '" + userId +"'); ";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
                res = "I SAW HIM!";
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            res = "Exceptions";
        }

        return res;
    }
}

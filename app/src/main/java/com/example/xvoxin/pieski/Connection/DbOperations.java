package com.example.xvoxin.pieski.Connection;

import android.os.AsyncTask;

import com.example.xvoxin.pieski.Models.Markers;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by xvoxin on 15.10.2017.
 */
public class DbOperations extends AsyncTask<String, String, String[]>{

    private ConnectionClass connectionClass = new ConnectionClass();
    public DbOperationsInterface dbo;

    public DbOperations(DbOperationsInterface dbo){
        this.dbo = dbo;
    }

    private ArrayList<Markers> markers;

    @Override
    protected void onPostExecute(String... param){
        if(param[0].equals("login")){
            dbo.login(Integer.parseInt(param[1]));
        }
        else if(param[0].equals("register")) {
            dbo.register(Integer.parseInt(param[1]));
        }
        else if(param[0].equals("markers")){
            dbo.getMarkers(markers);
        }
    }

    @Override
    protected String[] doInBackground(String... param) {
        String[] ret = new String[2];

        if(param[0].equals("login")){
            ret[0] = "login";
            ret[1] = String.valueOf(login(param[1], param[2]));
        }
        else if(param[0].equals("register")){
            ret[0] = "register";
            ret[1] = String.valueOf(register(param[1], param[2]));
        }
        else if(param[0].equals("markers")){
            ret[0] = "markers";
            getMarkers();
        }
        return ret;
    }

    public void getMarkers() {

        markers = new ArrayList<Markers>();

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
                    int userId = rs.getInt("userId");

                    markers.add(new Markers(latitude, longitude, city, time, userId));
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public int login(String login, String password){

        password = protectedPassword(password);

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
        catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }

        return 0;
    }

    public int register(String login, String password){

        password = protectedPassword(password);

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                System.out.println("wrong");
            } else {

                String query = "insert into users (login, password) values ('" + login +"', '"+ password +"')";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(query);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return 0;
        }

        return 0;
    }

    public String protectedPassword(String passwordToHash){

        String generatedPassword = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length; i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return generatedPassword;
    }
}

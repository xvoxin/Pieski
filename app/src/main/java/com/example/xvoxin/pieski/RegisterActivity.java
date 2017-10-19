package com.example.xvoxin.pieski;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.xvoxin.pieski.Connection.DbOperations;
import com.example.xvoxin.pieski.Connection.DbOperationsInterface;
import com.example.xvoxin.pieski.Models.Markers;

import java.util.ArrayList;

/**
 * Created by xvoxin on 18.10.2017.
 */
public class RegisterActivity extends Activity implements DbOperationsInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void register(int id) {
        continueRegister(id);
    }

    public void doRegister(View v){
        DbOperations db = new DbOperations(this);
        String login = ((EditText) findViewById(R.id.loginText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText1)).getText().toString();;

        if(((EditText) findViewById(R.id.passwordText1)).getText().toString().equals(((EditText) findViewById(R.id.passwordText2)).getText().toString())){

            db.execute("register", login, password);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Passwords didn't match")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void continueRegister(int id){
        if(id != 0){
            onBackPressed();
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("User with this login are already exists")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @Override
    public void getMarkers(ArrayList<Markers> marks) {

    }
    @Override
    public void login(int id) {

    }
}

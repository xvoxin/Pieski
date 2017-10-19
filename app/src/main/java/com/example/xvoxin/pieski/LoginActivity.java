package com.example.xvoxin.pieski;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.example.xvoxin.pieski.Connection.DbOperations;
import com.example.xvoxin.pieski.Connection.DbOperationsInterface;
import com.example.xvoxin.pieski.Models.Markers;

import java.util.ArrayList;


/**
 * Created by xvoxin on 15.10.2017.
 */
public class LoginActivity extends Activity implements DbOperationsInterface{

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPref = getSharedPreferences("IDvalue", 0);
        editor = sharedPref.edit();

        if(sharedPref.getString("login", "") != null && !sharedPref.getString("login", "").isEmpty()) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
    }

    @Override
    public void login(int id) {
        continueLogin(id);
    }

    public void doLogin(View v) {

        DbOperations db = new DbOperations(this);

        EditText login = (EditText) findViewById(R.id.loginText);
        EditText password = (EditText) findViewById(R.id.passwordText);

        String loginStr = login.getText().toString();
        String passwordStr = password.getText().toString();

        editor.putString("login", loginStr);
        editor.commit();

        db.execute("login", loginStr, passwordStr);
    }

    public void continueLogin(int id){
        if (id > 0) {
            editor.putInt("id", id);
            editor.commit();
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        } else {
            editor.putInt("id", 0);
            editor.putString("login", "");
            editor.putString("password", "");
            editor.commit();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Wrong login or password")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void doRegister(View v){
        Intent register = new Intent(this, RegisterActivity.class);
        startActivity(register);
        finish();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(sharedPref.getString("login", "") != null && !sharedPref.getString("login", "").isEmpty()) {
            finish();
        }
    }

    @Override
    public void getMarkers(ArrayList<Markers> marks) {

    }
    @Override
    public void register(int id) {

    }
}

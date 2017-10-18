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


/**
 * Created by xvoxin on 15.10.2017.
 */
public class LoginActivity extends Activity {

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

    public void doLogin(View v){

        DbOperations db = new DbOperations();

        EditText login = (EditText) findViewById(R.id.loginText);
        EditText password = (EditText) findViewById(R.id.passwordText);

        String loginStr = login.getText().toString();
        String passwordStr = password.getText().toString();

        int id = db.login(loginStr, db.protectedPassword(passwordStr));

        if (id != 0) {
            editor.putInt("id", id);
            editor.putString("login", loginStr);
            editor.commit();
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Zły login lub hasło")
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

}

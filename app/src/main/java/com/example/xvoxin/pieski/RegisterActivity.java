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

/**
 * Created by xvoxin on 18.10.2017.
 */
public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void doRegister(View v){
        DbOperations db = new DbOperations();
        String login = ((EditText) findViewById(R.id.loginText)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordText1)).getText().toString();;

        if(((EditText) findViewById(R.id.passwordText1)).getText().toString().equals(((EditText) findViewById(R.id.passwordText2)).getText().toString())){
            db.register(login, db.protectedPassword(password));
            onBackPressed();

        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Uzytkownik o podanej nazwie już istnieje lub hasła nie pasują do siebie.")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }
}

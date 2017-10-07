package com.example.xvoxin.pieski;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements View.OnClickListener {


    private Button btnGetLocation;
    private TextView textView;
    private ProgressBar pb;
    private Toast alert;

    String[] location = new String[3];

    private static final String TAG = "CHECKING WHATS GOING ON";
    private boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText("No, gdzie jesteś cwaniaczku?");

        btnGetLocation = (Button) findViewById(R.id.button);
        btnGetLocation.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        String statusDB;
        flag = displayGpsStatus();
        if (flag == true) {
            Log.v(TAG, "onClick");

            runOnUiThread(new Runnable() {
                public void run() {
                    location = pullLocation();
                    textView.setText("You are here!\n"+location[0]+", "+location[1]+"\nIn city - "+ location[2]);
                }
            });

            AddLocationToDb addLocationToDb = new AddLocationToDb();
            addLocationToDb.execute(location);
            statusDB = addLocationToDb.getMessage();
            alert = Toast.makeText(getApplicationContext(), statusDB, Toast.LENGTH_SHORT);
            alert.show();

        } else {
            alert = Toast.makeText(getApplicationContext(), "Gps is off", Toast.LENGTH_SHORT);
            alert.show();
        }

    }

    private boolean displayGpsStatus() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean gpsStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.GPS_PROVIDER);
        if (gpsStatus) {
            return true;

        } else {
            return false;
        }
    }
    private String[] pullLocation() {

        String[] locationArray = new String[3];
        double latitude = 0;
        double longitude = 0;
        String city = "";

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {

            locationManager.requestLocationUpdates(provider, 1000, 10, new MyLocationListener());

            Location myLocation = locationManager.getLastKnownLocation(provider);

            if (myLocation != null) {
                latitude = myLocation.getLatitude();
                longitude = myLocation.getLongitude();
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = gcd.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0)
                System.out.println(addresses.get(0).getLocality());
            city=addresses.get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        locationArray[0] = String.valueOf(latitude);
        locationArray[1] = String.valueOf(longitude);
        locationArray[2] = city;


//        return "You are here!"+latitude+", "+longitude+"\nIn city - "+ city;
        return  locationArray;
    }
}


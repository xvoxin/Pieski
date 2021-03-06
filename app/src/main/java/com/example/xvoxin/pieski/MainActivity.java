package com.example.xvoxin.pieski;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.xvoxin.pieski.Connection.AddLocationToDb;
import com.example.xvoxin.pieski.Connection.DbOperations;
import com.example.xvoxin.pieski.Connection.DbOperationsInterface;
import com.example.xvoxin.pieski.Models.Markers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity implements View.OnClickListener, DbOperationsInterface{

    private Button btnGetLocation;
    private TextView textView;
    private Toast alert;
    private ArrayList<Markers> markers;
    private boolean canMaps = false;

    String[] location = new String[5];

    SharedPreferences sharedPref;

    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sharedPref = getSharedPreferences("IDvalue", 0);

        textView = (TextView) findViewById(R.id.textView);
        String setText = "Hej " + sharedPref.getString("login", "") + "!";
        textView.setText(setText);

        btnGetLocation = (Button) findViewById(R.id.button);
        btnGetLocation.setOnClickListener(this);

        DbOperations db = new DbOperations(this);
        db.execute("markers");

    }

    @Override
    public void onClick(View v) {

        flag = displayNetworkStatus();
        if (flag == true) {
            location = pullLocation();

            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat (" yyyy.MM.dd HH:mm:ss");
            location[3] = String.valueOf(ft.format(dNow));
            location[4] = String.valueOf(sharedPref.getInt("id", 0));

            markers.add(new Markers(location[0], location[1], location[2], location[3], sharedPref.getInt("id", 0)));

            AddLocationToDb addLocationToDb = new AddLocationToDb(this);
            addLocationToDb.execute(location);

        } else {
            alert = Toast.makeText(getApplicationContext(), "Network is off", Toast.LENGTH_SHORT);
            alert.show();
        }

    }

    @Override
    public void getMarkers(ArrayList<Markers> marks) {
        markers = (ArrayList<Markers>) marks.clone();
        canMaps = true;
    }

    public void goToMaps(View v){
        if(canMaps) {
            Intent maps = new Intent(this, MapsActivity.class);
            maps.putExtra("markers", markers);
            startActivity(maps);
        }
        else {
            alert = Toast.makeText(getApplicationContext(), "Maps are not ready ", Toast.LENGTH_SHORT);
            alert.show();
        }
    }

    private boolean displayNetworkStatus() {
        ContentResolver contentResolver = getBaseContext().getContentResolver();
        boolean networkStatus = Settings.Secure.isLocationProviderEnabled(contentResolver, LocationManager.NETWORK_PROVIDER);
        if (networkStatus) {
            return true;

        } else {
            return false;
        }
    }

    private String[] pullLocation() {

        String[] locationArray = new String[5];
        double latitude = 0;
        double longitude = 0;
        String city = "";

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, new MyLocationListener());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }

        Location myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (myLocation != null) {
            latitude = myLocation.getLatitude();
            longitude = myLocation.getLongitude();
        }

        Geocoder gcd = new Geocoder(this, Locale.getDefault());
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

        return  locationArray;
    }

    public void doLogout(View view) {
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("id", 0);
        editor.putString("login", "");
        editor.putString("password", "");
        editor.commit();

        onBackPressed();
    }

    @Override
    public void login(int id) {

    }

    @Override
    public void register(int id) {

    }
}


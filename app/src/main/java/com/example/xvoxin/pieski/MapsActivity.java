package com.example.xvoxin.pieski;

import android.*;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.example.xvoxin.pieski.Connection.DbOperations;
import com.example.xvoxin.pieski.Models.Markers;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private ArrayList<Markers> markers;
    private DbOperations readMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        markers = new ArrayList<Markers>();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        markers = new ArrayList<Markers>();
        readMarks = new DbOperations();
        markers = (ArrayList<Markers>)readMarks.getMarkers().clone();


        LatLng pszczolki = new LatLng(54.175057, 18.702465);
        mMap.addMarker(new MarkerOptions().position(pszczolki).title("Tutaj na pewno był goldenek!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pszczolki));

        for(int i = 0; i < markers.size(); i++){
            System.out.println(markers.get(i).getCity() + " - " + markers.get(i).getTime());

            LatLng marker = new LatLng(Double.parseDouble(markers.get(i).getLatitude()), Double.parseDouble(markers.get(i).getLongitude()));
            mMap.addMarker(new MarkerOptions().position(marker).title(markers.get(i).getCity() + " - " + markers.get(i).getTime()));
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LatLng coordinate = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 15);
        mMap.animateCamera(yourLocation);
    }
}

package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.commuterfamily.Adapters.CustomInfoWindowAdapter;
import com.example.commuterfamily.DirectionHelpers.FetchURL;
import com.example.commuterfamily.DirectionHelpers.TaskLoadedCallback;
import com.example.commuterfamily.R;
import com.firebase.geofire.util.Constants;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class RiderRouteMapActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {
    private GoogleMap mMap;
    private MarkerOptions place1, place2;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    public final static double AVERAGE_RADIUS_OF_EARTH = 6371;

    Button getDirection;
    private Polyline currentPolyline;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_route_map);
 //        place1 = new MarkerOptions().position(new LatLng(Double.valueOf(getIntent().getStringExtra("latFrom")), Double.valueOf(getIntent().getStringExtra("longFrom")))).title("Location 1");
//        place2 = new MarkerOptions().position(new LatLng(Double.valueOf(getIntent().getStringExtra("latTo")), Double.valueOf(getIntent().getStringExtra("longTo")))).title("Location 2");
        place1 = new MarkerOptions().position( new LatLng( Double.parseDouble(getIntent().getStringExtra("latFrom")),Double.parseDouble(getIntent().getStringExtra("longFrom")))).title("From");
        place2 = new MarkerOptions().position(new LatLng( Double.parseDouble(getIntent().getStringExtra("latTo")),Double.parseDouble(getIntent().getStringExtra("longTo")))).title("To");
        new FetchURL(RiderRouteMapActivity.this).execute(getUrl(place1.getPosition(), place2.getPosition(), "driving"), "driving");


        //27.658143,85.3199503
        //27.667491,85.3208583
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapNearBy);
        mapFragment.getMapAsync(this);

        double a=calculateDistance(Double.parseDouble(getIntent().getStringExtra("latFrom")),
                Double.parseDouble(getIntent().getStringExtra("longFrom")),
                Double.parseDouble(getIntent().getStringExtra("latTo")),
                Double.parseDouble(getIntent().getStringExtra("longTo"))
                );
        Toast.makeText(this, String.valueOf(a), Toast.LENGTH_SHORT).show();

    }


    public double calculateDistance(double userLat, double userLng, double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = (Math.sin(latDistance / 2) * Math.sin(latDistance / 2)) +
                (Math.cos(Math.toRadians(userLat))) *
                        (Math.cos(Math.toRadians(venueLat))) *
                        (Math.sin(lngDistance / 2)) *
                        (Math.sin(lngDistance / 2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return   AVERAGE_RADIUS_OF_EARTH * c  ;

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.d("mylog", "Added Markers");
        mMap.addMarker(place1);
        mMap.addMarker(place2);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(  new LatLng( Double.parseDouble(getIntent().getStringExtra("latFrom")),Double.parseDouble(getIntent().getStringExtra("longFrom"))) , 12));

    }


    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
    }






    }

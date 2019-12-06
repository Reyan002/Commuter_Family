package com.example.commuterfamily.Activities;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.commuterfamily.Adapters.PlaceAutoSuggestAdapter;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.LatLongClass;
import com.example.commuterfamily.R;
import com.example.commuterfamily.TutorialFragment.Ride;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String apiKey="AIzaSyCrr57KiCAmX_PJK57nCryJZ9wwuYNoFok";
    private LatLongClass latLongFrom,latLongTo;
    private MapView mMapView;
    private    LatLng sydney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mMapView = findViewById(R.id.mapView);

        if(!Places.isInitialized()){
            Places.initialize(this,apiKey);
        }


        latLongFrom=new LatLongClass();
        latLongTo=new LatLongClass();
        findViewById(R.id.goBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( !DemoClass.latLongTo.equals(null)&&! DemoClass.latLongFrom.equals(null)){
                    startActivity(new Intent(MapsActivity.this, RideActivity.class));
                }
                else
                    Toast.makeText(MapsActivity.this, "Please Give Complte Details", Toast.LENGTH_SHORT).show();
            }
        });
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(MapsActivity.this, "Place: " + place.getLatLng().latitude, Toast.LENGTH_SHORT).show();
                DemoClass.AdressFrom=place.getName();
                DemoClass.latLongFrom.setLat(place.getLatLng().latitude) ;
                DemoClass.latLongFrom.setLong(place.getLatLng().longitude); ;
                //Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),8.0f));

            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(MapsActivity.this, "An error occurred: " + status, Toast.LENGTH_SHORT).show();
//                Log.i(TAG, "An error occurred: " + status);
            }
        });
        AutocompleteSupportFragment autocompleteFragmentTO = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_to);

// Specify the types of place data to return
        autocompleteFragmentTO.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

// Set up a PlaceSelectionListener to handle the response
        autocompleteFragmentTO.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(MapsActivity.this, "Place: " + place.getLatLng().latitude , Toast.LENGTH_SHORT).show();
                //Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                DemoClass.AdressTo=place.getName();

                DemoClass.latLongTo.setLat(place.getLatLng().latitude) ;
               DemoClass.latLongTo.setLong(place.getLatLng().longitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),8.0f));
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(MapsActivity.this, "An error occurred: " + status, Toast.LENGTH_SHORT).show();
//                Log.i(TAG, "An error occurred: " + status);
            }
        });
//        AutoCompleteTextView autoCompleteTextView=findViewById(R.id.input_search);
//        autoCompleteTextView.setAdapter(new PlaceAutoSuggestAdapter( this,android.R.layout.simple_list_item_1));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);

        initGoogleMap(savedInstanceState);

    }

    private void initGoogleMap(Bundle savedInstanceState){
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(apiKey);
        }

        mMapView.onCreate(mapViewBundle);
        mMapView.getMapAsync(this);
    }

//    private void initUserListRecyclerView() {
//        mUserRecyclerAdapter = new UserRecyclerAdapter(mUserList);
//        mUserListRecyclerView.setAdapter(mUserRecyclerAdapter);
//        mUserListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(apiKey);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(apiKey, mapViewBundle);
        }

        mMapView.onSaveInstanceState(mapViewBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;

        // Add a marker in Sydney and  move the camera
        LatLng sydney = new LatLng(24.8607, 67.0011);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }

    @Override
    public void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mMapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//    }

}
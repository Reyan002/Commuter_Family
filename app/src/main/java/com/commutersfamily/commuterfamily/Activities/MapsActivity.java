package com.commutersfamily.commuterfamily.Activities;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.Classes.LatLongClass;
import com.commutersfamily.commuterfamily.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String apiKey="AIzaSyCrr57KiCAmX_PJK57nCryJZ9wwuYNoFok";
    private LatLongClass latLongFrom , latLongTo;
    private MapView mMapView;
    private double bb,lb,tb,rb;

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

                if(DemoClass.distance=="distance")
                {

                    Intent intent = new Intent(MapsActivity.this,DistanceActivity.class);
                     intent.putExtra("fromMap",DemoClass.AdressFrom);
                    intent.putExtra("toMap",DemoClass.AdressTo);
                    startActivity(intent);
                    DemoClass.distance="";
                }
                else if( !DemoClass.latLongTo.equals(null)&&! DemoClass.latLongFrom.equals(null)){

                    startActivity(new Intent(MapsActivity.this,RideActivity.class));
                    Intent intent = new Intent(MapsActivity.this,RideActivity.class);
                    String from = DemoClass.AdressFrom, to = DemoClass.AdressTo;
                    intent.putExtra("fromMap",DemoClass.AdressFrom);
                    intent.putExtra("toMap",DemoClass.AdressTo);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(MapsActivity.this, "Please Give Complete Details", Toast.LENGTH_SHORT).show();
                }
            }

        });

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setCountry("PAK");

// Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(MapsActivity.this, "Place: " + place.getLatLng().latitude, Toast.LENGTH_SHORT).show();
                DemoClass.AdressFrom=place.getName();
                DemoClass.latLongFrom.setLat(place.getLatLng().latitude);
                DemoClass.latLongFrom.setLong(place.getLatLng().longitude);

                //Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                mMap.clear();

                mMap.addMarker(new MarkerOptions().position(place.getLatLng()).title(place.getName()));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(place.getLatLng()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),15.0f));

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
        autocompleteFragmentTO.setCountry("PAK");
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
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(),15.0f));
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

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapView);
//        mapFragment.getMapAsync(this);
//
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this).addConnectionCallbacks(this)
//                .addApi(LocationServices.API).build();
//
//        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//        checkLocation();

    }

//    private boolean checkLocation() {
//        if (!isLocationEnabled()){
//            showAlert();
//        }
//        return isLocationEnabled();
//    }
//
//    private void showAlert() {
//
//        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//        dialog.setTitle("Enable Location")
//        .setMessage("Your Device Location is Off.\n Please Enable to location to use this app")
//        .setPositiveButton("Location Setting", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Intent myIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
//                startActivity(myIntent);
//            }
//        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//            }
//        });
//        dialog.show();
//    }

//    private boolean isLocationEnabled() {
//
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
//                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//    }

    private void initGoogleMap(Bundle savedInstanceState){

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

        LatLng sydney = new LatLng(24.8607, 67.0011);

//        bb = 24.926294 - 0.1 ;
//        lb = 67.022095 - 0.1 ;
//        tb = 24.926294 + 0.1 ;
//        rb = 67.022095 + 0.1 ;
//
//        LatLngBounds latLngBounds = new LatLngBounds(new LatLng(bb,lb),new LatLng(tb,rb));
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
//        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds,1));


        // Add a marker in Sydney and  move the camera
//        LatLng sydney = new LatLng(24.8607, 67.0011);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Karachi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,10.0f));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            return;
        }

//        mMap.setMyLocationEnabled(true);
//        mMap.getUiSettings().setMyLocationButtonEnabled(true);
//        mMap.getUiSettings().setZoomControlsEnabled(true);
//        mMap.getUiSettings().setMapToolbarEnabled(true);
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
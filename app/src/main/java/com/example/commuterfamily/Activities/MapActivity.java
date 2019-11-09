package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.commuterfamily.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class MapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        String key="AIzaSyBrm3VpQ0S7i5A824GquqrLhPzKv2NHOng";
        if(!Places.isInitialized()){
            Places.initialize(this,key);
        }
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragmentFrom = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_from);

// Specify the types of place data to return.
        autocompleteFragmentFrom
                .setPlaceFields(Arrays.asList(Place.Field.ID,
                        Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragmentFrom.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                 Toast.makeText(MapActivity.this, "Place: " + place.getName() +  ", " + place.getLatLng() , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(MapActivity.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragmentTo = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_to);

// Specify the types of place data to return.
        autocompleteFragmentTo
                .setPlaceFields(Arrays.asList(Place.Field.ID,
                        Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));
// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragmentTo.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(MapActivity.this, "Place: " + place.getName() +  ", " + place.getLatLng(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Toast.makeText(MapActivity.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}

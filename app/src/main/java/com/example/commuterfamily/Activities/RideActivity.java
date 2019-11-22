package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.LatLongClass;
import com.example.commuterfamily.MapsCredentials.GPSTracker;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.example.commuterfamily.TutorialFragment.Ride;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class RideActivity extends AppCompatActivity  {

    private TextView locationFrom;
    private String txtDay,txtShift,txtMTimeFrom,txtMTimeTo,txtETimeFrom,txtEtimeTo ;
     private Spinner spinner,spinnerTimeMornigFrom,spinnerTimeMornigTo,spinnerTimeEveFrom,spinnerEveMornigTo ;
    private GPSTracker gps;
    private LinearLayout morning,evening;
    private   RadioGroup radioGroup;
    private FloatingActionButton next;
    private LatLongClass latLongFrom,latLongTo;
    String randomKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride);
         Initialize();
        spinnerDay();
        setSpinnerTimeMornigFrom();
        setSpinnerTimeEveFrom();
        setSpinnerTimeMornigTo();
        setSpinnerTimeEveTo();
        latLongFrom=new LatLongClass();
        latLongTo=new LatLongClass();
         spinnerTimeMornigFrom.setEnabled(false);
                spinnerTimeMornigTo.setEnabled(false);
        // Spinner click listener

next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if(!txtDay.equals("")&&!txtShift.equals("")&&
                ((!txtMTimeFrom.equals("")&&!txtMTimeTo.equals(""))||
                         (!txtETimeFrom.equals("")&&!txtEtimeTo.equals("") ) )
        &&!DemoClass.latLongFrom.equals("")&&!DemoClass.latLongTo.equals("") )  {
            addRiderIntoDataBase();
        }
      else{
            Toast.makeText(RideActivity.this, "Please Fill All the Require Fields to Add the Route", Toast.LENGTH_SHORT).show();
        }

    }
});
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.morning:

                        RadioButton radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
                        txtShift=radioButton.getText().toString();
                        spinnerTimeMornigFrom.setEnabled(true);
                        spinnerTimeMornigTo.setEnabled(true);
                        evening.setVisibility(View.GONE);
                        morning.setVisibility(View.VISIBLE);
                         break;
                    case R.id.evening:
                        RadioButton radioButton1=findViewById(radioGroup.getCheckedRadioButtonId());
                        txtShift=radioButton1.getText().toString();
                        morning.setVisibility(View.GONE);
                        evening.setVisibility(View.VISIBLE);
                          break;
                }
            }
        });
        locationFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=");
//                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                        mapIntent.setPackage("com.google.android.apps.maps");
//                        startActivity(mapIntent);
//                    }
//                }, 1000);
                startActivity(new Intent( RideActivity.this, MapsActivity.class));
            }
        });

//        locationFrom.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // Create class object
//                gps = new GPSTracker(RideActivity.this);
//
//                // Check if GPS enabled
//                if(gps.canGetLocation()) {
//
//                    double latitude = gps.getLatitude();
//                    double longitude = gps.getLongitude();
//
//                    // \n is for new line
//                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//
//                    locationFrom.setText("Your Location is - \nLat: " + latitude + "\nLong: " + longitude);
//                } else {
//                    // Can't get location.
//                    // GPS or network is not enabled.
//                    // Ask user to enable GPS/network in settings.
//                    gps.showSettingsAlert();
//                }
//            }
//        });


    }

    public void spinnerDay(){
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtDay = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("");
        categories.add("Monday");
        categories.add("Tuesday");
        categories.add("Wednesday");
        categories.add("Thursday");
        categories.add("Friday");
        categories.add("Saturday");
        categories.add("Sunday");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    public void setSpinnerTimeMornigFrom(){
        spinnerTimeMornigFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtMTimeFrom = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("");
        categories.add("7:30 am");
        categories.add("7:45 am");
        categories.add("8:00 am");
        categories.add("8:15 am");
        categories.add("8:30 am");
        categories.add("8:45 am");
        categories.add("9:00 am");
        categories.add("9:15 am");
        categories.add("9:30 am");
        categories.add("9:45 am");
        categories.add("10:00 am");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerTimeMornigFrom.setAdapter(dataAdapter);
    }
    public void setSpinnerTimeEveFrom(){
        spinnerTimeEveFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 txtETimeFrom = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("");
        categories.add("4:30 pm");
        categories.add("4:45 pm");
        categories.add("5:00 pm");
        categories.add("5:15 pm");
        categories.add("5:30 pm");
        categories.add("5:45 pm");
        categories.add("6:00 pm");
        categories.add("6:15 pm");
        categories.add("6:30 pm");
        categories.add("6:45 pm");
        categories.add("7:00 pm");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerTimeEveFrom.setAdapter(dataAdapter);
    }
    public void setSpinnerTimeMornigTo(){
        spinnerTimeMornigTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtMTimeTo = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("");
        categories.add("7:45 am");
        categories.add("8:00 am");
        categories.add("8:15 am");
        categories.add("8:30 am");
        categories.add("8:45 am");
        categories.add("9:00 am");
        categories.add("9:15 am");
        categories.add("9:30 am");
        categories.add("9:45 am");
        categories.add("10:00 am");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerTimeMornigTo.setAdapter(dataAdapter);
    }
    public void setSpinnerTimeEveTo(){
        spinnerEveMornigTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                txtEtimeTo = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("");
        categories.add("4:45 pm");
        categories.add("5:00 pm");
        categories.add("5:15 pm");
        categories.add("5:30 pm");
        categories.add("5:45 pm");
        categories.add("6:00 pm");
        categories.add("6:15 pm");
        categories.add("6:30 pm");
        categories.add("6:45 pm");
        categories.add("7:00 pm");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerEveMornigTo.setAdapter(dataAdapter);
    }
public void Initialize(){
    next=findViewById(R.id.nextToMap);
        morning=findViewById(R.id.morning_times);
    spinner =  findViewById(R.id.ride_days);
 evening=findViewById(R.id.evening_times);
    locationFrom=findViewById(R.id.locFrom);

    spinnerTimeMornigFrom=findViewById(R.id.morning_timeFrom);
    spinnerTimeMornigTo=findViewById(R.id.morning_timeTO);
    spinnerTimeEveFrom=findViewById(R.id.timeFrom);
    spinnerEveMornigTo=findViewById(R.id.TimeTo);

    radioGroup = (RadioGroup) findViewById(R.id.shift);

}

    public void addRiderIntoDataBase(){

        final String saveCurrentDate,saveCurrentime;

        Calendar calForDate= Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,YYY");
        saveCurrentDate=currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentime=currentTime.format(calForDate.getTime());



        if(DemoClass.RouteFor=="Rider") {
            final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Commuters");

            final HashMap<String, Object> cartMap = new HashMap<>();
//        cartMap.put("cid","");
            randomKey = saveCurrentDate + saveCurrentime;
            cartMap.put("RouteID", randomKey);
            cartMap.put("Day", txtDay);
            cartMap.put("Shift", txtShift);
            cartMap.put("Date", saveCurrentDate);
            cartMap.put("Time", saveCurrentime);
            cartMap.put("MTimeFrom", txtMTimeFrom);
            cartMap.put("MTimeTo", txtMTimeTo);
            cartMap.put("ETimeFrom", txtETimeFrom);
            cartMap.put("ETimeTo", txtEtimeTo);
            cartMap.put("AdressFrom", DemoClass.AdressFrom);
            cartMap.put("AdressTo", DemoClass.AdressTo);
            cartMap.put("LocFrom", DemoClass.latLongFrom);
            cartMap.put("LocTo", DemoClass.latLongTo);


            cartListRef.child(DemoClass.RouteFor).child(Prevalent.currentOnlineUser.getPhone())
                    .child("Ride").child(saveCurrentDate + saveCurrentime).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {

                        // Access a Cloud Firestore instance from your Activity


                        DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Riders");
                        reference.child(saveCurrentDate + saveCurrentime).updateChildren(cartMap);
                        Toast.makeText(RideActivity.this, "Wellcome Commuter", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RideActivity.this,RiderRouteActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();

                    }
                }
            });

        }
        if(DemoClass.RouteFor=="Driver"){
            if(DemoClass.CarKey==""){
                Toast.makeText(RideActivity.this, "Add Car firstly", Toast.LENGTH_LONG).show();
                startActivity(new Intent(RideActivity.this,DriveActivity.class));
                finish();

        }
        else{
                final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Commuters");

                final HashMap<String,Object> cartMap =new HashMap<>();
//        cartMap.put("cid","");
                randomKey=saveCurrentDate+saveCurrentime;
                cartMap.put("CarKey",DemoClass.CarKey);
                cartMap.put("RouteID",randomKey) ;
                cartMap.put("Day",txtDay) ;
                cartMap.put("Shift",txtShift);
                cartMap.put("Date",saveCurrentDate);

                cartMap.put("Time",saveCurrentime);
                cartMap.put("MTimeFrom",txtMTimeFrom);
                cartMap.put("MTimeTo",txtMTimeTo);
                cartMap.put("ETimeFrom",txtETimeFrom);
                cartMap.put("ETimeTo",txtEtimeTo);
                cartMap.put("AdressFrom",DemoClass.AdressFrom);
                cartMap.put("AdressTo",DemoClass.AdressTo);
                cartMap.put("LocFrom",  DemoClass.latLongFrom );
                cartMap.put("LocTo", DemoClass.latLongTo );


                cartListRef.child(DemoClass.RouteFor).child(Prevalent.currentOnlineUser.getPhone())
                        .child("Ride").child(saveCurrentDate+saveCurrentime).updateChildren(cartMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Drivers");
                            reference.child(saveCurrentDate+saveCurrentime).updateChildren(cartMap);
                            Toast.makeText(RideActivity.this,"Wellcome Commuter",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(RideActivity.this,DriveActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                    }
                });


            }
        }

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RideActivity.this,MainActivity.class));
    }
}

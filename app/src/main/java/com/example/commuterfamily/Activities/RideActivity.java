package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.LatLongClass;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.DashBoardDrawerActivity.DashboardDrawerActivity;
import com.example.commuterfamily.MapsCredentials.GPSTracker;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class RideActivity extends AppCompatActivity  {

    private TextView locationFrom;
    private String txtDay,txtShift,txtMTimeFrom,txtMTimeTo,txtETimeFrom,txtEtimeTo ;
    private Spinner spinner,spinnerTimeMornigFrom,spinnerTimeMornigTo,spinnerTimeEveFrom,spinnerEveMornigTo ;
    private GPSTracker gps;
    private LinearLayout morning,evening;
    private RadioGroup radioGroup;
    private FloatingActionButton next;
    private LatLongClass latLongFrom,latLongTo;
    private String randomKey;
    private EditText pickup;

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
        pickup=findViewById(R.id.pickPoint);
        // Spinner click listener

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtDay.equals("")&&!txtShift.equals("")&&
                ((!txtMTimeFrom.equals("")&&!txtMTimeTo.equals(""))||
                         (!txtETimeFrom.equals("")&&!txtEtimeTo.equals("")||!pickup.getText().equals("") ) )
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

        String from = getIntent().getStringExtra("fromMap");
        String to = getIntent().getStringExtra("toMap");
        Toast.makeText(RideActivity.this, "From"+from+"\nTo"+to, Toast.LENGTH_SHORT).show();

        if (locationFrom.getText().equals(null)){
            locationFrom.setHint("Route");
        }
        else{
            locationFrom.setText(""+from+"\nto\n"+""+to);
        }

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
                if (position==0){
//                    Toast.makeText(RideActivity.this, "Select any Day", Toast.LENGTH_SHORT).show();
                    return;
                }
                txtDay = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Day");
        categories.add("Monday");
        categories.add("Tuesday");
        categories.add("Wednesday");
        categories.add("Thursday");
        categories.add("Friday");
        categories.add("Saturday");
        categories.add("Sunday");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item, categories) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position==0) {
                    // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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
        radioGroup = findViewById(R.id.shift);
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
            cartMap.put("Number",Prevalent.currentOnlineUser.getPhone());
            cartMap.put("RouteID", randomKey);
            cartMap.put("Day", txtDay);
            cartMap.put("PickUp",pickup.getText().toString());
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


                            final DatabaseReference match = FirebaseDatabase.getInstance().getReference().child("Drivers");
                            match.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                                    List<Routes> users = new ArrayList<>();
                                    while (dataSnapshots.hasNext()) {
                                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
                                        Routes user = dataSnapshotChild.getValue(Routes.class);
                                        users.add(user);
                                    }
                                    String userids = "";
                                    List<Routes> temp = new ArrayList();
                                    try {
                                        for (int i = 0; i < users.size(); i++) {
                                            if (users.get(i).getAdressFrom().equalsIgnoreCase(DemoClass.AdressFrom)) {
                                                temp.add(users.get(i));
//                                                //Here you can find your searchable user
//                                                Log.e("temp", "+" + temp.get(i).getFirebaseId());
//                                                email = temp.get(i).getEmailId();
                                                Toast.makeText(RideActivity.this, "Your Match Has Found", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        Toast.makeText(RideActivity.this, String.valueOf(temp.size()), Toast.LENGTH_SHORT).show();
                                        if(temp.isEmpty()){
                                            Toast.makeText(RideActivity.this, "Please Wait We are Comming soon with your matched route", Toast.LENGTH_SHORT).show();
                                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Riders");
                                            reference.child(saveCurrentDate + saveCurrentime).updateChildren(cartMap);
                                            Toast.makeText(RideActivity.this, "Wellcome Commuter", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(RideActivity.this,RiderRouteActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(RideActivity.this, "We hav Your Match ", Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(RideActivity.this, temp.get(0).getNumber(), Toast.LENGTH_SHORT).show();

                                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Riders");
                                            reference.child(saveCurrentDate + saveCurrentime).updateChildren(cartMap);
                                            Toast.makeText(RideActivity.this, "Wellcome Commuter", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(RideActivity.this,RiderRouteActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
//                                        Log.e("Logs", e.toString());
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }

            });

        }
        if(DemoClass.RouteFor=="Driver") {
                final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Commuters");

                final HashMap<String,Object> cartMap =new HashMap<>();
//        cartMap.put("cid","");
                randomKey=saveCurrentDate+saveCurrentime;
                cartMap.put("CarKey",DemoClass.CarKey);
                cartMap.put("RouteID",randomKey) ;
                cartMap.put("Day",txtDay) ;
                cartMap.put("Shift",txtShift);
                cartMap.put("Date",saveCurrentDate);
                cartMap.put("Number",Prevalent.currentOnlineUser.getPhone());
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

                            final DatabaseReference match = FirebaseDatabase.getInstance().getReference().child("Riders");
                            match.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Iterator<DataSnapshot> dataSnapshots = dataSnapshot.getChildren().iterator();
                                    List<Routes> users = new ArrayList<>();
                                    while (dataSnapshots.hasNext()) {
                                        DataSnapshot dataSnapshotChild = dataSnapshots.next();
                                        Routes user = dataSnapshotChild.getValue(Routes.class);
                                        users.add(user);
                                    }
                                    String userids = "";
                                    List<Routes> temp = new ArrayList();
                                    try {
                                        for (int i = 0; i < users.size(); i++) {
                                            if (users.get(i).getAdressFrom().equalsIgnoreCase(DemoClass.AdressFrom)) {
                                                temp.add(users.get(i));
//                                                //Here you can find your searchable user
//                                                Log.e("temp", "+" + temp.get(i).getFirebaseId());
//                                                email = temp.get(i).getEmailId();
                                                Toast.makeText(RideActivity.this, "Your Match Has Found", Toast.LENGTH_SHORT).show();

                                            }
                                        }

                                        Toast.makeText(RideActivity.this, String.valueOf(temp.size()), Toast.LENGTH_SHORT).show();
                                        if(temp.isEmpty()){
                                            Toast.makeText(RideActivity.this, "Please Wait We are Comming soon with your matched route", Toast.LENGTH_SHORT).show();
                                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Drivers");
                                            reference.child(saveCurrentDate + saveCurrentime).updateChildren(cartMap);
                                            Toast.makeText(RideActivity.this, "Wellcome Commuter", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(RideActivity.this,RiderRouteActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(RideActivity.this, "We hav Your Match ", Toast.LENGTH_SHORT).show();
//                                            Toast.makeText(RideActivity.this, temp.get(0).getNumber(), Toast.LENGTH_SHORT).show();

                                            DatabaseReference reference=FirebaseDatabase.getInstance().getReference().child("Drivers");
                                            reference.child(saveCurrentDate + saveCurrentime).updateChildren(cartMap);
                                            Toast.makeText(RideActivity.this, "Wellcome Commuter", Toast.LENGTH_SHORT).show();
                                            Intent intent=new Intent(RideActivity.this,RiderRouteActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
//                                        Log.e("Logs", e.toString());
                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });

                        }
                    }
                });


            }
        }



    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(RideActivity.this, DashboardDrawerActivity.class));
    }
}

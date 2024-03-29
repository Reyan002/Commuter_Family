package com.commutersfamily.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.commutersfamily.commuterfamily.Adapters.MatchAdapter;
import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.Classes.Routes;
import com.commutersfamily.commuterfamily.Prevalent.Prevalent;
import com.commutersfamily.commuterfamily.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.commutersfamily.commuterfamily.Activities.RiderRouteMapActivity.AVERAGE_RADIUS_OF_EARTH;

public class MatchActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MatchAdapter matchAdapter;
    private double locLatFrom,locLongFrom,locLatTo,locLngTo;
    private ProgressDialog loadingBar;

    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        recyclerView= findViewById(R.id.allRoutes);
        locLatFrom= Double.parseDouble(getIntent().getStringExtra("locLatFrom"));
        locLongFrom= Double.parseDouble(getIntent().getStringExtra("locLongFrom"));
        locLatTo= Double.parseDouble(getIntent().getStringExtra("locLatTo"));
        locLngTo= Double.parseDouble(getIntent().getStringExtra("locLongTo"));
//        Toast.makeText(MatchActivity.this, DemoClass.commuterMatch, Toast.LENGTH_SHORT).show();

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
     }
    @Override
    protected void onStart() {
        super.onStart();

        loadingBar=new ProgressDialog(this);
        loadingBar.setTitle("Loading Routes");
        loadingBar.setMessage("Please wait ...");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();


        databaseReference = FirebaseDatabase.getInstance().getReference().child(DemoClass.commuterMatch);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  ArrayList<Routes> routes=new ArrayList<>();
                 for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(
                              !dataSnapshot1.child("Number").getValue().toString().equalsIgnoreCase(Prevalent.currentOnlineUser.getPhone())&&
//                            dataSnapshot1.child("AdressFrom").getValue().toString().equalsIgnoreCase(getIntent().getStringExtra("adressFrom"))
//                            && dataSnapshot1.child("AdressTo").getValue().toString().equalsIgnoreCase(getIntent().getStringExtra("adressTo"))
//                            && dataSnapshot1.child("Date").getValue().toString().equals(getIntent().getStringExtra(""))
                            dataSnapshot1.child("Day").getValue().toString().equalsIgnoreCase(getIntent().getStringExtra("day"))
//                            && ! dataSnapshot1.child("Number").equals(Prevalent.currentOnlineUser.getPhone())
//                            && dataSnapshot1.child("ETimeFrom").getValue().toString().equals(getIntent().getStringExtra("eveningTimeFrom"))
//                            && dataSnapshot1.child("ETimeTo").getValue().toString().equals(getIntent().getStringExtra("eveningTimeTo"))
//                            && dataSnapshot1.child("MTimeTo").getValue().toString().equals(getIntent().getStringExtra("morningTimeTo"))
//                            && dataSnapshot1.child("MTimeFrom").getValue().toString().equals(getIntent().getStringExtra("morningTimeFrom"))
                            && dataSnapshot1.child("Shift").getValue().toString().equals(getIntent().getStringExtra("shift"))

                    )
                    {
//                        Toast.makeText(MatchActivity.this, dataSnapshot1.child("Number").getValue().toString(), Toast.LENGTH_SHORT).show();
                        if((calculateDistance(locLatFrom,locLongFrom,Double.valueOf(dataSnapshot1.child("LocFrom").child("lat").getValue().toString()),
                                Double.valueOf(dataSnapshot1.child("LocFrom").child("long").getValue().toString())
                               )<1.0f)&&(calculateDistance(locLatTo,locLngTo,Double.valueOf(dataSnapshot1.child("LocTo").child("lat").getValue().toString()),
                                Double.valueOf(dataSnapshot1.child("LocTo").child("long").getValue().toString())
                        )<1.0f)){
//                      if      Toast.makeText(MatchActivity.this, DemoClass.commuterMatch, Toast.LENGTH_SHORT).show();

                            routes.add(dataSnapshot1.getValue(Routes.class));
                        }



                    }


                }
                 if(!routes.isEmpty()){
                     loadingBar.dismiss();
                     matchAdapter = new MatchAdapter(routes, MatchActivity.this);
                     recyclerView.setAdapter(matchAdapter);

                 }
                 else{
                     loadingBar.dismiss();
                     Toast.makeText(MatchActivity.this, "No Match Found", Toast.LENGTH_SHORT).show();
                 }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//
//
//                .orderByChild("AdressFrom").equalTo(getIntent().getStringExtra("adressTo"))
//                .orderByChild("EtimeFrom").equalTo(getIntent().getStringExtra("eveningTimeFrom"))
//                .orderByChild("ETimeTo").equalTo(getIntent().getStringExtra("eveningTimeTo"))
//                .orderByChild("MTimeFrom").equalTo(getIntent().getStringExtra("morningTimeFrom"))
//                .orderByChild("MTimeTo").equalTo(getIntent().getStringExtra("morningTimeTo"))
//                .orderByChild("Day").equalTo(getIntent().getStringExtra("day"))
//                 .orderByChild("lat").equalTo(getIntent().getStringExtra("locLatFrom"))
//                .orderByChild("long").equalTo(getIntent().getStringExtra("locLongFrom") )
//                .orderByChild("lat").equalTo(getIntent().getStringExtra("locLatTo"))
//                .orderByChild("long").equalTo(getIntent().getStringExtra("locLongTo"))


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

}
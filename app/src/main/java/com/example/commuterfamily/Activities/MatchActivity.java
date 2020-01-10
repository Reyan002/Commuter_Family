package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.commuterfamily.Adapters.CartViewHolder;
import com.example.commuterfamily.Adapters.MatchAdapter;
import com.example.commuterfamily.Adapters.MatchViewHolder;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.squareup.okhttp.Route;

import java.util.ArrayList;

import static com.example.commuterfamily.Activities.RiderRouteMapActivity.AVERAGE_RADIUS_OF_EARTH;

public class MatchActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private MatchAdapter matchAdapter;
    private double locLatFrom,locLongFrom,locLatTo,locLngTo;


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

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
     }
    @Override
    protected void onStart() {
        super.onStart();

//        loadingBar=new ProgressDialog(this);
//        loadingBar.setTitle("Loading Routes");
//        loadingBar.setMessage("Please wait ...");
//        loadingBar.setCanceledOnTouchOutside(false);
//        loadingBar.show();


        databaseReference = FirebaseDatabase.getInstance().getReference().child(DemoClass.commuterMatch);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                  ArrayList<Routes> routes=new ArrayList<>();
                 for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    if(
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
                        if(calculateDistance(locLatFrom,locLongFrom,Double.valueOf(dataSnapshot1.child("LocFrom").child("lat").getValue().toString()),
                                Double.valueOf(dataSnapshot1.child("LocFrom").child("long").getValue().toString())
                               )<1.0f){
                            Toast.makeText(MatchActivity.this, DemoClass.commuterMatch, Toast.LENGTH_SHORT).show();

                            routes.add(dataSnapshot1.getValue(Routes.class));
                        }
                        else{
                            Toast.makeText(MatchActivity.this, "Phat  gya", Toast.LENGTH_SHORT).show();
                        }


                    }


                }
                matchAdapter = new MatchAdapter(routes, MatchActivity.this);
                recyclerView.setAdapter(matchAdapter);

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
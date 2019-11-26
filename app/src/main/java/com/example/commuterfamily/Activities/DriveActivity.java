package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.commuterfamily.Adapters.CartViewHolder;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.example.commuterfamily.SessionManager.SessionManager;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriveActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ProgressDialog loadingBar;
    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive);
        recyclerView=(RecyclerView)findViewById(R.id.vehicles_driver);

        sessionManager=new SessionManager(this);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        findViewById(R.id.car).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DriveActivity.this,AddCarActivity.class));
            }
        });
        findViewById(R.id.route).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!sessionManager.isKey())
                {
                    Toast.makeText(DriveActivity.this, "Add Car First", Toast.LENGTH_SHORT).show();
                }
                else{startActivity(new Intent(DriveActivity.this,RideActivity.class));

                    DemoClass.RouteFor="Driver";}

            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

//        loadingBar=new ProgressDialog(this);
//        loadingBar.setTitle("Loading Routes");
//        loadingBar.setMessage("Please wait ...");
//        loadingBar.setCanceledOnTouchOutside(false);
//        loadingBar.show();
        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Commuters");
        FirebaseRecyclerOptions<Routes> options=
                new FirebaseRecyclerOptions.Builder<Routes>()
                        .setQuery(cartListRef.child("Driver")
                                .child(Prevalent.currentOnlineUser.getPhone()).child("Ride"),Routes.class)
                        .build();


        FirebaseRecyclerAdapter<Routes, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Routes, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder , int position , @NonNull final Routes model) {

//                loadingBar.dismiss();
                holder.txtProductPrice.setText("Trip Day: "+model.getDay());
                holder.txtProductnName.setText("Trip Shift: " +model.getShift());
                holder.txtProductQuantity.setText("Trip Starts From: "+ model.getAdressFrom());
                holder.toLocation.setText( "Trip Ends On: "+model.getAdressTo());
                holder.fromTIme.setText( "Trip Time Range: "+model.getMTimeTo()+model.getETimeFrom() +" - "+model.getMTimeTo()+model.getETimeTo());
                //                int oneTypeProductTotalPric=((Integer.valueOf(model.getProductPrice())))*((Integer.valueOf(model.getQuantity())));
//                overAlltotalPrice = overAlltotalPrice + oneTypeProductTotalPric;



                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        CharSequence options[]=new CharSequence[]{
                                "See Your Commute","View Route","Edit","Remove"
                        };
                        AlertDialog.Builder builder=new AlertDialog.Builder(DriveActivity.this);
                        builder.setTitle("Route Option");
                        builder.setItems(options , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog , int which) {
                                if(which==0) {
                                    DemoClass.commuterMatch = "Riders";
                                    Intent intent = new Intent(DriveActivity.this, MatchActivity.class);
                                    intent.putExtra("morningTimeFrom", model.getMTimeFrom());
                                    intent.putExtra("morningTimeTo", model.getMTimeTo());
                                    intent.putExtra("eveningTimeFrom", model.getETimeFrom());
                                    intent.putExtra("eveningTimeTo", model.getETimeTo());
                                    intent.putExtra("shift", model.getShift());
                                    intent.putExtra("day", model.getDay());
                                    intent.putExtra("adressTo", model.getAdressTo());
                                    intent.putExtra("adressFrom", model.getAdressFrom());
                                    intent.putExtra("locLongFrom", String.valueOf(model.getLocFrom().getLong()));
                                    intent.putExtra("locLatTo", String.valueOf(model.getLocTo().getLat()));
                                    intent.putExtra("locLongTo", String.valueOf(model.getLocTo().getLong()));
                                    startActivity(intent);
                                }
                                    if(which==1){
                                    Intent intent=new Intent(DriveActivity.this,RiderRouteMapActivity.class);
                                    intent.putExtra("pid",model.getRouteID());
                                    intent.putExtra("latFrom",String.valueOf( model.getLocFrom().getLat()));
                                    intent.putExtra("longFrom",String.valueOf( model.getLocFrom().getLong()));
                                    intent.putExtra("latTo",String.valueOf( model.getLocTo().getLat()));
                                    intent.putExtra("longTo",String.valueOf( model.getLocTo().getLong()));
                                    startActivity(intent);
                                }
                                if(which==2){
                                    Intent intent=new Intent(DriveActivity.this,RideActivity.class);
                                    intent.putExtra("pid",model.getRouteID());
                                    startActivity(intent);
                                }
                                if(which==3){
                                    cartListRef.child("Driver")
                                            .child(Prevalent.currentOnlineUser.getPhone())
                                            .child("Ride")
                                            .child(model.getRouteID())
                                            .removeValue()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(DriveActivity.this,"Route removed Succesfully.",Toast.LENGTH_SHORT).show();
                                                        // startActivity(new Intent(RiderRouteActivity.this,HomeActivity.class));
                                                    }
                                                }
                                            });
                                    DatabaseReference newRef=FirebaseDatabase.getInstance().getReference();
                                    newRef.child("Drivers").child(model.getRouteID()).removeValue();

                                }
                            }
                        });
                        builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_layout,parent,false);
                CartViewHolder holder=new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }


}

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
import com.example.commuterfamily.Adapters.MatchViewHolder;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MatchActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        recyclerView=(RecyclerView)findViewById(R.id.allRoutes);

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

        final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child(DemoClass.commuterMatch)  ;
        FirebaseRecyclerOptions<Routes> options=
                new FirebaseRecyclerOptions.Builder<Routes>()
                        .setQuery(cartListRef ,Routes.class)
                        .build();



        FirebaseRecyclerAdapter<Routes, MatchViewHolder> adapter=new FirebaseRecyclerAdapter<Routes, MatchViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MatchViewHolder holder, int i, @NonNull final Routes model ) {
//                if (model.getMTimeFrom().equals(getIntent().getStringExtra("morningTimeFrom"))
//                        && model.getMTimeTo().equals(getIntent().getStringExtra("morningTimeTo"))
//                        && model.getETimeFrom().equals(getIntent().getStringExtra("eveningTimeFrom"))
//                        && model.getETimeTo().equals(getIntent().getStringExtra("eveningTimeTo"))
//                        && model.getShift().equals(getIntent().getStringExtra("shift"))
//                        && model.getDay().equals(getIntent().getStringExtra("day"))
//                        && model.getLocFrom().getLat() == Double.valueOf(getIntent().getStringExtra("locLatFrom"))
//                        && model.getLocFrom().getLong() == Double.valueOf(getIntent().getStringExtra("locLongFrom"))
//                        && model.getLocTo().getLat() == Double.valueOf(getIntent().getStringExtra("locLatTo"))
//                        && model.getLocTo().getLong() == Double.valueOf(getIntent().getStringExtra("locLongTo"))
//
//                )
//                {
//                loadingBar.dismiss();
                    holder.txtProductPrice.setText("Trip Day: " + model.getDay());
                    holder.txtProductnName.setText("Trip Shift: " + model.getShift());
                    holder.txtProductQuantity.setText("Trip Starts From: " + model.getAdressFrom());
                    holder.toLocation.setText("Trip Ends On: " + model.getAdressTo());
                    holder.fromTIme.setText("Trip Time Range: " + model.getMTimeTo() + model.getETimeFrom() + " - " + model.getMTimeTo() + model.getETimeTo());
                    //                int oneTypeProductTotalPric=((Integer.valueOf(model.getProductPrice())))*((Integer.valueOf(model.getQuantity())));
//                overAlltotalPrice = overAlltotalPrice + oneTypeProductTotalPric;


                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CharSequence options[] = new CharSequence[]{
                                    "View Route"
                            };
                            AlertDialog.Builder builder = new AlertDialog.Builder(MatchActivity.this);
                            builder.setTitle("Route Option");
                            builder.setItems(options, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (which == 0) {
                                        Intent intent = new Intent(MatchActivity.this, RiderRouteMapActivity.class);
                                        intent.putExtra("pid", model.getRouteID());
                                        intent.putExtra("latFrom", String.valueOf(model.getLocFrom().getLat()));
                                        intent.putExtra("longFrom", String.valueOf(model.getLocFrom().getLong()));
                                        intent.putExtra("latTo", String.valueOf(model.getLocTo().getLat()));
                                        intent.putExtra("longTo", String.valueOf(model.getLocTo().getLong()));
                                        startActivity(intent);
                                    }
//                                    if (which == 1) {
//                                        Intent intent = new Intent(MatchActivity.this, RideActivity.class);
//                                        intent.putExtra("pid", model.getRouteID());
//                                        startActivity(intent);
//                                    }
//                                if (which == 2) {
//                                    cartListRef.child("Driver")
//
//                                            .child(model.getRouteID())
//                                            .removeValue()
//                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//                                                    if (task.isSuccessful()) {
//                                                        Toast.makeText(MatchActivity.this, "Route removed Succesfully.", Toast.LENGTH_SHORT).show();
//                                                        // startActivity(new Intent(RiderRouteActivity.this,HomeActivity.class));
//                                                    }
//                                                }
//                                            });
//                                }
                                }
                            });
                            builder.show();
                        }
                    });

//                }else{
//                    Toast.makeText(MatchActivity.this, "You Have No any Match", Toast.LENGTH_SHORT).show();
//                }

            }



            @NonNull
            @Override
            public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.match_items,parent,false);
                MatchViewHolder holder=new MatchViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

}

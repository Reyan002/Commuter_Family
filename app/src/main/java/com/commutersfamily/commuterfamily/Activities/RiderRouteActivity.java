
package com.commutersfamily.commuterfamily.Activities;

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
import android.widget.Button;
import android.widget.Toast;

import com.commutersfamily.commuterfamily.Adapters.CartViewHolder;
import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.Classes.Routes;
import com.commutersfamily.commuterfamily.DashBoardDrawerActivity.DashboardDrawerActivity;
import com.commutersfamily.commuterfamily.Prevalent.Prevalent;
import com.commutersfamily.commuterfamily.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
 import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RiderRouteActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager layoutManager;
  private Button addRoute;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_rider_route);
    addRoute=findViewById(R.id.addRoute);

    recyclerView= findViewById(R.id.cart_list);
    recyclerView.setHasFixedSize(true);
    layoutManager=new LinearLayoutManager(this);
    recyclerView.setLayoutManager(layoutManager);
    addRoute.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
//                startActivity(new Intent(RiderRouteActivity.this, RideActivity.class));
        startActivity(new Intent(RiderRouteActivity.this, MapsActivity.class));

        DemoClass.RouteFor="Rider";
      }
    });
  }

  @Override
  protected void onStart() {
    super.onStart();

//        final ProgressDialog loadingBar=new ProgressDialog(this);
//        loadingBar.setTitle("Loading Routes");
//        loadingBar.setMessage("Please wait ...");
//        loadingBar.setCanceledOnTouchOutside(false);
//        loadingBar.show();

    final DatabaseReference cartListRef= FirebaseDatabase.getInstance().getReference().child("Commuters");
    FirebaseRecyclerOptions<Routes> options=
            new FirebaseRecyclerOptions.Builder<Routes>()
                    .setQuery(cartListRef.child("Rider")
                            .child(Prevalent.currentOnlineUser.getPhone()).child("Ride"),Routes.class)
                    .build();


    FirebaseRecyclerAdapter<Routes, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Routes, CartViewHolder>(options) {
      @Override
      protected void onBindViewHolder(@NonNull CartViewHolder holder , int position , @NonNull final Routes model) {

//                loadingBar.dismiss();
        holder.txtProductPrice.setText("Trip Day: "+model.getDay());
//                holder.txtProductnName.setText("Trip Shift: " +model.getShift());
        holder.txtProductQuantity.setText("Trip Starts From: "+ model.getAdressFrom());
        holder.toLocation.setText( "Trip Ends On: "+model.getAdressTo());
        holder.fromTIme.setText( "Trip Time Range: "+model.getMTimeTo()+model.getETimeFrom()
                +" - "+model.getMTimeTo()+model.getETimeTo());
        //                int oneTypeProductTotalPric=((Integer.valueOf(model.getProductPrice())))*((Integer.valueOf(model.getQuantity())));
//                overAlltotalPrice = overAlltotalPrice + oneTypeProductTotalPric;



        holder.kebab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              CharSequence[] options = new CharSequence[]{
                      "See your Commute", "View Route", "Edit", "Remove"
              };
            AlertDialog.Builder builder=new AlertDialog.Builder(RiderRouteActivity.this);
            builder.setTitle("Route Option");
            builder.setItems(options , new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog , int which) {
                if(which==0){
                  DemoClass.commuterMatch= "Drivers";
                  Intent intent=new Intent(RiderRouteActivity.this,MatchActivity.class);
                  intent.putExtra("morningTimeFrom",model.getMTimeFrom());
                  intent.putExtra("morningTimeTo",model.getMTimeTo());
                  intent.putExtra("eveningTimeFrom",model.getETimeFrom());
                  intent.putExtra("eveningTimeTo",model.getETimeTo());
                  intent.putExtra("shift",model.getShift());
                  intent.putExtra("day",model.getDay());
                  intent.putExtra( "adressTo",model.getAdressTo());
                  intent.putExtra( "adressFrom",model.getAdressFrom());
                  intent.putExtra("locLongFrom",String.valueOf(model.getLocFrom().getLong()));
                  intent.putExtra("locLatFrom",String.valueOf(model.getLocFrom().getLat()));
                  intent.putExtra("locLatTo",String.valueOf(model.getLocTo().getLat()));
                  intent.putExtra("locLongTo",String.valueOf(model.getLocTo().getLong()));

                  startActivity(intent);
                }
                if(which==1){
                  Intent intent=new Intent(RiderRouteActivity.this,RiderRouteMapActivity.class);
                  intent.putExtra("pid",model.getRouteID());
                  intent.putExtra("latFrom",String.valueOf( model.getLocFrom().getLat()));
                  intent.putExtra("longFrom",String.valueOf( model.getLocFrom().getLong()));
                  intent.putExtra("latTo",String.valueOf( model.getLocTo().getLat()));
                  intent.putExtra("longTo",String.valueOf( model.getLocTo().getLong()));
                  startActivity(intent);
                }
                if(which==2){
                  Intent intent=new Intent(RiderRouteActivity.this,RideActivity.class);
                  intent.putExtra("pid",model.getRouteID());
                  startActivity(intent);
                }
                if(which==3){
                  cartListRef.child("Rider")
                          .child(Prevalent.currentOnlineUser.getPhone())
                          .child("Ride")
                          .child(model.getRouteID())
                          .removeValue()
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                              if(task.isSuccessful()){
                                Toast.makeText(RiderRouteActivity.this,"Route removed Succesfully.",Toast.LENGTH_SHORT).show();
                                // startActivity(new Intent(RiderRouteActivity.this,HomeActivity.class));
                              }
                            }
                          });
                  DatabaseReference newRef=FirebaseDatabase.getInstance().getReference();
                  newRef.child("Riders").child(model.getRouteID()).removeValue();
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

  @Override
  public void onBackPressed() {
    startActivity(new Intent(this, DashboardDrawerActivity.class));
    finish();
  }
}

package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.Classes.User;
import com.example.commuterfamily.Classes.Vehicle;
import com.example.commuterfamily.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MatchRouteDetailActivity extends AppCompatActivity {

private String  ProductId;
private TextView shift,day,time,start,end;
private TextView type,number;
private TextView name,view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_route_detail);

        ProductId=getIntent().getStringExtra("rid");

    }

   public void Initilize(){
        shift=findViewById(R.id.shift);
        day=findViewById(R.id.shift);
        time=findViewById(R.id.shift);
        start=findViewById(R.id.shift);
        end=findViewById(R.id.shift);
        type=findViewById(R.id.shift);
        number=findViewById(R.id.shift);
        name=findViewById(R.id.shift);
        view=findViewById(R.id.shift);

   }
    private void getRouteDetails(String productId) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(DemoClass.RouteFor);
        reference.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Routes products=dataSnapshot.getValue(Routes.class);

//                    PDname.setText(products.getName());
//                    PDprice.setText(products.getPrice());
//                    PDdescription.setText(products.getDescription());
//                    PDdescription.setText(products.getDescription());
//                    PDdescription.setText(products.getDescription());
                 }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getVehiclesDetail(String productId) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Commuters").child("Driver")  ;
        reference.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                   Vehicle v=dataSnapshot.getValue(Vehicle.class);

//                    PDname.setText(products.getName());
//                    PDprice.setText(products.getPrice());
//                    PDdescription.setText(products.getDescription());
//                    Picasso.get().load(products.getImage()).into(productDetailsImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void getUserDetails(String productId) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference() .child("Users") ;
        reference.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    User v=dataSnapshot.getValue(User.class);

//                    PDname.setText(products.getName());
//                    PDprice.setText(products.getPrice());
//                    PDdescription.setText(products.getDescription());
//                    Picasso.get().load(products.getImage()).into(productDetailsImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

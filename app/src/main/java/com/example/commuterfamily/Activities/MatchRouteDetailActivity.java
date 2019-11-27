package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.Classes.User;
import com.example.commuterfamily.Classes.Vehicle;
import com.example.commuterfamily.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MatchRouteDetailActivity extends AppCompatActivity {

private String  ProductId,Pnumber;
private TextView shift,day,time,start,end;
private TextView type,number;
private TextView name,view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_route_detail);
        Initilize();
        ProductId=getIntent().getStringExtra("rid");
        Pnumber=getIntent().getStringExtra("number");
       getVehiclesDetail(Pnumber);
        getUserDetails(Pnumber);

    }

   public void Initilize(){
        shift=findViewById(R.id.textViewMR_SD1);
        day=findViewById(R.id.textViewMR_SD2);
        time=findViewById(R.id.textViewMR_SD3);
        start=findViewById(R.id.textViewMR_SD4);
        end=findViewById(R.id.textViewMR_SD5);

        type=findViewById(R.id.textViewMR_VD1);
        number=findViewById(R.id.textViewMR_VD2);

        name=findViewById(R.id.textViewMR_CD1);
        view=findViewById(R.id.textViewMR_CD2);

   }

//            @Override


    private void getVehiclesDetail(String productId) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Commuters").child("Driver")  ;
        reference.child(productId).child("Car").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                   Vehicle v=dataSnapshot.getValue(Vehicle.class);

                   type.setText("Type: "+v.getVehicleType());
                   number.setText("Number: "+v.getVehicleNumber());
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

                   name.setText(v.getName());
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

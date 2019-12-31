package com.example.commuterfamily.Activities;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.Classes.User;
import com.example.commuterfamily.Classes.Vehicle;
 import com.example.commuterfamily.Interfaces.FirebaseAPI;
import com.example.commuterfamily.Interfaces.Messege;
import com.example.commuterfamily.Interfaces.NotifyData;
import com.example.commuterfamily.Prevalent.Prevalent;

import com.example.commuterfamily.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.type.Color;

import java.util.HashMap;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;


public class MatchRouteDetailActivity extends AppCompatActivity implements OnMapReadyCallback {

private String  ProductId,Pnumber;
private TextView shift,day,time,start,end;
private Button request,cancle;
private TextView type,number;
private DatabaseReference request_ref,connect_ref,notify_ref;
private FirebaseAuth mAuth;
private String sender;
private String current_request ;
private String firebaseInstanceId;
private TextView name,view;
//private MapView mapView;
private GoogleMap gMap;
private MapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_route_detail);

        firebaseInstanceId=FirebaseInstanceId.getInstance().getToken();
        current_request="new";
        Initilize();


        shift.setText(getIntent().getStringExtra("shift"));
        day.setText(getIntent().getStringExtra("day"));
        time.setText(getIntent().getStringExtra("time_m_f")+getIntent().getStringExtra("time_e_f")+"-"+getIntent().getStringExtra("time_m_t")+getIntent().getStringExtra("time_e_t"));
        start.setText(getIntent().getStringExtra("from"));
        end.setText(getIntent().getStringExtra("to"));
        sender = Prevalent.currentOnlineUser.getPhone();
        request_ref=FirebaseDatabase.getInstance().getReference().child("Request");
        connect_ref=FirebaseDatabase.getInstance().getReference().child("PeopleConnected");
        notify_ref=FirebaseDatabase.getInstance().getReference().child("Notification");
        ProductId=getIntent().getStringExtra("rid");
        Pnumber=getIntent().getStringExtra("number");
        retriev();




    }
    public void retriev(){
        getVehiclesDetail( );

        Initilize();
        ProductId=getIntent().getStringExtra("rid");
        Pnumber=getIntent().getStringExtra("number");
        getRouteDetails(ProductId);
          getUserDetails(Pnumber);
        manageDetails();
        manageRequestInfo();
    }
    public void manageDetails(){
        if(!sender.equals(Pnumber)){
            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(current_request.equals ("new")){
                        sendRequestOfRide();
                    }
                    if(current_request.equals ("request_sent")){
                        cancle.setVisibility(View.GONE);
                        cancle.setEnabled(false);
                        cancleRequest();
                    }
                    if(current_request.equals("request_recieved")){

                        acceptRequest();
                    }if(current_request.equals("commute")){

                        removeRequest();
                    }
//                    if(current_request.equals("cancle_request")){
//
//                    }
//                    if(current_request.equals("acceept_request")){
//
//                    }

                }
            });
        }else
        {
            request.setEnabled(false);
            request.setVisibility(View.GONE);
        }
    }
    private void acceptRequest() {
        connect_ref.child(sender).child(Pnumber).child("Contact").setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    connect_ref.child(Pnumber).child(sender).child("Contact").setValue("Saved").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){

                                request_ref.child(sender).child(Pnumber).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            request_ref.child(Pnumber).child(sender).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){

                                                        current_request="commute";
                                                        request.setText("Remove");
                                                         cancle.setVisibility(View.GONE);
                                                         cancle.setEnabled(false);
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    private void cancleRequest() {
        request_ref.child(sender).child(Pnumber).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    request_ref.child(Pnumber).child(sender).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                request.setEnabled(true);
                                request.setText("Send Request");
                                current_request="new";

                            }
                        }
                    });
                }
            }
        });
    }
    private void getVehiclesDetail() {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Commuters").child("Driver")  ;
        reference.child(Pnumber).child("Car").addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(MatchRouteDetailActivity.this, "NOT EXIST", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
     public void manageRequestInfo(){
        request_ref.child(sender).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(Pnumber))
                {
                    String request_type=dataSnapshot.child(Pnumber).child("request_type").getValue() .toString();

                    if(request_type.equals("sent")){
                        current_request="request_sent";
                        request.setText("Cancle Request");
                        cancle.setVisibility(View.GONE);
                        Toast.makeText(MatchRouteDetailActivity.this, request_type, Toast.LENGTH_SHORT).show();
                    }
                    else if(request_type.equals("recieved")){
                        current_request="request_recieved";
                        request.setText("Accept Request");
                        cancle.setVisibility(View.VISIBLE);
                        cancle.setText("Decline Request");
                        Toast.makeText(MatchRouteDetailActivity.this, request_type, Toast.LENGTH_SHORT).show();

                        cancle.setEnabled(true);
                        cancle.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                cancleRequest();
                                cancle.setVisibility(View.GONE);
                                cancle.setEnabled(false);
                            }
                        });
                    }

                }
                else{

                        connect_ref.child(sender).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(Pnumber)) {
                                    current_request="new";
                                    request.setText("Remove");
                                }



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                   

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void sendRequestOfRide(){
        request_ref.child(sender).child(Pnumber).child("request_type").setValue("sent").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    request_ref.child(Pnumber).child(sender).child("request_type").setValue("recieved").addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){

                                HashMap<String ,String > chatNotifi=new HashMap<>();
                                chatNotifi.put("from",sender);
                                chatNotifi.put("type","request");

                                notify_ref.child(Pnumber).push().setValue(chatNotifi).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            request.setEnabled(false);
                                            request.setText("Cancle Request");
                                            current_request="request_sent";
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });
    }
    public void Initilize(){
        cancle=findViewById(R.id.cancle_request);
        request=findViewById(R.id.button_request);
        shift=findViewById(R.id.textViewMR_SD1);
        day=findViewById(R.id.textViewMR_SD2);
        time=findViewById(R.id.textViewMR_SD3);
        start=findViewById(R.id.textViewMR_SD4);
        end=findViewById(R.id.textViewMR_SD5);
        type=findViewById(R.id.textViewMR_VD1);
        number=findViewById(R.id.textViewMR_VD2);
        name=findViewById(R.id.textViewMR_CD1);
        view=findViewById(R.id.textViewMR_CD2);
//        mapView = findViewById(R.id.mapViewMatchRoute);
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapViewMatchRoute);
        mapFragment.getMapAsync(this);

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
    private void removeRequest() {

        connect_ref.child(sender).child(Pnumber).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    connect_ref.child(Pnumber).child(sender).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                request.setText("Send Request");
                                current_request="new";
                            }
                        }
                    });
                }
            }
        });

    }


    private void getRouteDetails(String productId) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(DemoClass.RouteFor);
        reference.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Routes routes=dataSnapshot.getValue(Routes.class);
                    shift.setText("Shift: "+routes.getShift());
                    day.setText("Day: "+routes.getDay());
                    time.setText("Time: "+routes.getETimeFrom()+"-"+routes.getETimeTo()+routes.getMTimeFrom()+"-"+routes.getMTimeTo());
                    start.setText("Start From: "+routes.getAdressFrom());
                    end.setText("End On: "+routes.getAdressTo());


//            @Override
 //                    PDname.setText(products.getName());
//                    PDprice.setText(products.getPrice());
//                    PDdescription.setText(products.getDescription());
//                    PDdescription.setText(products.getDescription());
//                    PDdescription.setText(products.getDescription());
                 }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }}  );

        }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double lat = Double.parseDouble(getIntent().getStringExtra("latFrom"));
        double lng = Double.parseDouble(getIntent().getStringExtra("longFrom"));

        LatLng latlng = new LatLng(lat, lng);



        gMap = googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {

        }

        gMap.setTrafficEnabled(true);
        gMap.setIndoorEnabled(true);
        gMap.setBuildingsEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);

//        Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation)
//                .title(***NAME OF PLACE HERE***));
        gMap.addCircle(new CircleOptions().center(latlng).radius(100));
        gMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14.0f));


    }
}








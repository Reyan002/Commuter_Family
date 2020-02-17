package com.commutersfamily.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.Classes.Routes;
import com.commutersfamily.commuterfamily.Classes.User;
import com.commutersfamily.commuterfamily.Classes.Vehicle;
import com.commutersfamily.commuterfamily.Prevalent.Prevalent;

import com.commutersfamily.commuterfamily.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
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
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


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
private TextView pickUp;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS =0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_route_detail);

        findViewById(R.id.message).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:"+Pnumber));
               startActivity(sendIntent);
            }
        });
        findViewById(R.id.whatsapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contact = "+92"+Pnumber; // use country code with your phone number
                String url = "https://api.whatsapp.com/send?phone=" + contact;
                try {
                    PackageManager pm = MatchRouteDetailActivity.this.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(MatchRouteDetailActivity.this, "Whatsapp app not installed in your phone", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
        findViewById(R.id.call).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                callPhoneNumber();
            }
        });

        CardView cardView=findViewById(R.id.cardViewMd2);

        if(DemoClass.commuterMatch=="Drivers"){
            cardView.setVisibility(View.VISIBLE);
        }
                firebaseInstanceId=FirebaseInstanceId.getInstance().getToken();
        Initilize();


        current_request="new";

//        pickUp.setText(getIntent().getStringExtra("pick"));
//
//        shift.setText(getIntent().getStringExtra("shift"));
//        day.setText(getIntent().getStringExtra("day"));
//        time.setText(getIntent().getStringExtra("time_m_f")+getIntent().getStringExtra("time_e_f")+"-"+getIntent().getStringExtra("time_m_t")+getIntent().getStringExtra("time_e_t"));
//        start.setText(getIntent().getStringExtra("from"));
//        end.setText(getIntent().getStringExtra("to"));
        sender = Prevalent.currentOnlineUser.getPhone();
        request_ref=FirebaseDatabase.getInstance().getReference().child("Request");
        connect_ref=FirebaseDatabase.getInstance().getReference().child("PeopleConnected");
        notify_ref=FirebaseDatabase.getInstance().getReference().child("Notification");

 //        Pnumber=getIntent().getStringExtra("number");
        retriev();


//        DatabaseReference details=FirebaseDatabase.getInstance().getReference().child("Riders");
//        details.child(ProductId).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.exists()){
//                shift.setText(dataSnapshot.getValue(Routes.class).getShift());
//                day.setText(dataSnapshot.getValue(Routes.class).getDay());
//                time.setText(dataSnapshot.getValue(Routes.class).getETimeFrom()+dataSnapshot.getValue(Routes.class).getETimeTo()+dataSnapshot.getValue(Routes.class).getMTimeFrom()+dataSnapshot.getValue(Routes.class).getMTimeTo());
//                start.setText(dataSnapshot.getValue(Routes.class).getAdressFrom());
//                    Toast.makeText(MatchRouteDetailActivity.this, shift.getText().toString(), Toast.LENGTH_SHORT).show();
//
//                    end.setText(dataSnapshot.getValue(Routes.class).getAdressTo());}
//                else{
//                    Toast.makeText(MatchRouteDetailActivity.this, "Error", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

//        Toast.makeText(this, ProductId, Toast.LENGTH_SHORT).show();


    }

    public void retriev(){
        ProductId=getIntent().getStringExtra("rid");
        Pnumber=getIntent().getStringExtra("number");
        getVehiclesDetail( );

        Initilize();

        getRouteDetails(ProductId);
          getUserDetails(Pnumber);
        manageDetails();
        manageRequestInfo();
    }
    public void manageDetails(){
        if(!sender.equals(Pnumber) ){
            request.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(current_request.equals ("new")){

//                        AlertDialog.Builder builder=new AlertDialog.Builder(MatchRouteDetailActivity.this);
//                        builder.setTitle("Alert!");
//                        builder.setTitle("This Will Charge 10 pkr From your Account Balance, Are You Agree ?");
//                        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
                                sendRequestOfRide();
//                            }
//                        });
//                        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                               dialog.dismiss();
//                            }
//                        });
//
//                        builder.show();

                    }
                    if(current_request.equals ("request_sent")){
                        cancle.setVisibility(View.GONE);
                        cancle.setEnabled(false);
                        cancleRequest();
                    }
                    if(current_request.equals("request_recieved")){

                        acceptRequest();
                    }if(current_request.equals("commute")){
//                        Toast.makeText(MatchRouteDetailActivity.this, current_request, Toast.LENGTH_SHORT).show();

                        removeRequest();
                    }
//                    if(current_request.equals("cancle_request")){
//
//                    }
//                    if(current_request.equals("acceept_request")){
//
//                    }

//                    Toast.makeText(MatchRouteDetailActivity.this, current_request, Toast.LENGTH_SHORT).show();
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
//                Toast.makeText(MatchRouteDetailActivity.this, "NOT EXIST", Toast.LENGTH_SHORT).show();
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
//                        Toast.makeText(MatchRouteDetailActivity.this, request_type, Toast.LENGTH_SHORT).show();
                    }
                    else if(request_type.equals("recieved")){
                        current_request="request_recieved";
                        request.setText("Accept Request");
                        cancle.setVisibility(View.VISIBLE);
                        cancle.setText("Decline Request");
//                        Toast.makeText(MatchRouteDetailActivity.this, request_type, Toast.LENGTH_SHORT).show();

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
                                    current_request="commute";
                                    request.setText("Remove");
                                    request.setVisibility(View.GONE);
                                    LinearLayout linearLayout=findViewById(R.id.ll);
                                    linearLayout.setVisibility(View.VISIBLE);
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
    public void showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
// Add the buttons
         builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                sendSMSMessage();
             }
        });
        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
finish();            }
        });
        builder.show();
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

                                final String saveCurrentDate,saveCurrentime;

                                Calendar calForDate= Calendar.getInstance();
                                SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,YYY");
                                saveCurrentDate=currentDate.format(calForDate.getTime());

                                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
                                saveCurrentime=currentTime.format(calForDate.getTime());
                                HashMap<String ,String > chatNotifi=new HashMap<>();
                                chatNotifi.put("from",sender);
                                chatNotifi.put("type","request");
                                chatNotifi.put("Date", saveCurrentDate);
                                chatNotifi.put("Time",saveCurrentime);
                                chatNotifi.put("NotiId",getIntent().getStringExtra("rid"));
                                chatNotifi.put("WantTo",DemoClass.commuterMatch);


                                notify_ref.child(Pnumber).child(getIntent().getStringExtra("rid")).setValue(chatNotifi).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            request.setEnabled(false);
                                            FirebaseMessaging.getInstance().subscribeToTopic("sendNotification");
                                            request.setText("Cancle Request");
                                            current_request="request_sent";




//
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
        pickUp=findViewById(R.id.pick_text);
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
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child(DemoClass.commuterMatch);
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
                    pickUp.setText("Pick up Point: "+routes.getPickUp());


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

        double lat = 15.3276;//Double.parseDouble(getIntent().getStringExtra("latFrom"));
        double lng = 119.978;//Double.parseDouble(getIntent().getStringExtra("longFrom"));

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

        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(new LatLng(lat,lng));
        circleOptions.radius(300);
        circleOptions.strokeColor(Color.parseColor("#000000"));
        circleOptions.fillColor(Color.parseColor("#E4B0E7FF"));
        circleOptions.strokeWidth(3);

        gMap.addCircle(circleOptions);
        gMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng,14.0f));


    }

    protected void sendSMSMessage() {


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage("+923123850471", null, "Hello G", null, null);
                    sendRequestOfRide();
//                    Toast.makeText(getApplicationContext(), "SMS sent.",
//                            Toast.LENGTH_LONG).show();
                } else {
//                    Toast.makeText(getApplicationContext(),
//                            "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

    }





    public void callPhoneNumber()
    {
        try
        {
            if(Build.VERSION.SDK_INT > 22)
            {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(MatchRouteDetailActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 101);

                    return;
                }

                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" +Pnumber));
                startActivity(callIntent);

            }
            else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "03408377547"));
                startActivity(callIntent);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



}








package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;


public class MatchRouteDetailActivity extends AppCompatActivity  {

private String  ProductId,Pnumber;
private TextView shift,day,time,start,end;
private Button request,cancle;
private TextView type,number;
private DatabaseReference request_ref,connect_ref;
private FirebaseAuth mAuth;
private String sender;
private String current_request ;


private TextView name,view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_route_detail);

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
        ProductId=getIntent().getStringExtra("rid");
        Pnumber=getIntent().getStringExtra("number");
        retriev();




    }
    public void retriev(){
        getVehiclesDetail( );
        getUserDetails(Pnumber);
        manageDetails();
        manageRequestInfo();
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
                                request.setText("Send Request");
                                current_request="new";

                            }
                        }
                    });
                }
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
                                request.setText("Cancle Request");
                                current_request="request_sent";
                            }
                        }
                    });
                }
            }
        });
  }
//            @Override


    private void getVehiclesDetail( ) {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("Commuters")  ;
        reference.child(DemoClass.commuterMatch).child(Pnumber).child("Car").addValueEventListener(new ValueEventListener() {
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

}

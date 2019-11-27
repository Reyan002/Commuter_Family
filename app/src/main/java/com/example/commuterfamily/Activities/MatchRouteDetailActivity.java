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
import com.example.commuterfamily.R;
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
private Button request;
private TextView type,number;
    OkHttpClient mClient = new OkHttpClient();

    String refreshedToken = "AAAAA29OTRk:APA91bFIQpeFy1-dbkHXop66shdEEcEzQX3bBfN-LOnjUClucGUiuqxo-YhUBhuhGyFVBFChzjaStyuRBQRH4wNQjyVK5LkKxMz6oWmus2WexYuydk-rAvsrJiqeqVNK1wgjGqEXyNy2";//add your user refresh tokens who are logged in with firebase.

    JSONArray jsonArray = new JSONArray();
private TextView name,view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_route_detail);
        jsonArray.put(refreshedToken);

        Initilize();
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage(jsonArray,"Hello","How r u","Http:\\google.com","My Name is Reyan and i am from CFamily ");

            }
        });
        ProductId=getIntent().getStringExtra("rid");
        Pnumber=getIntent().getStringExtra("number");
       getVehiclesDetail(Pnumber);
        getUserDetails(Pnumber);

    }

   public void Initilize(){
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

    public void sendMessage(final JSONArray recipients, final String title, final String body, final String icon, final String message) {

        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... params) {
                try {
                    JSONObject root = new JSONObject();
                    JSONObject notification = new JSONObject();
                    notification.put("body", body);
                    notification.put("title", title);
                    notification.put("icon", icon);

                    JSONObject data = new JSONObject();
                    data.put("message", message);
                    root.put("notification", notification);
                    root.put("data", data);
                    root.put("registration_ids", recipients);

                    String result = postToFCM(root.toString());
                    Log.d("Main Activity", "Result: " + result);
                    return result;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String result) {
                try {
                    JSONObject resultJson = new JSONObject(result);
                    int success, failure;
                    success = resultJson.getInt("success");
                    failure = resultJson.getInt("failure");
                    Toast.makeText(MatchRouteDetailActivity.this, "Message Success: " + success + "Message Failed: " + failure, Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MatchRouteDetailActivity.this, "Message Failed, Unknown error occurred.", Toast.LENGTH_LONG).show();
                }
            }
        }.execute();
    }

    String postToFCM(String bodyString) throws IOException {


   String FCM_MESSAGE_URL = "https://fcm.googleapis.com/fcm/send";
        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, bodyString);
        Request request = new Request.Builder()
                .url(FCM_MESSAGE_URL)
                .post(body)
                .addHeader("Authorization", "key=" + "your server key")
                .build();
        Response response = mClient.newCall(request).execute();
        return response.body().string();
    }

}

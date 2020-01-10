package com.example.commuterfamily.Classes;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


import com.example.commuterfamily.Activities.MainActivity;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


            String type=remoteMessage.getData().get("type") ;
            Toast.makeText(this, type, Toast.LENGTH_SHORT).show();
            switch (type){
                 
            }

            NotificationCompat.Builder notifyBuilder=new NotificationCompat.Builder(this);



            NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0,notifyBuilder.build()) ;




        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        tokenG(token);
    }


    public void tokenG(String token){
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        if(Prevalent.currentOnlineUser  !=null && Prevalent.currentOnlineUser.getPhone()!=null) {
            RootRef.child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("DeviceToken").setValue(token).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(MyFirebaseMessagingService.this, "Sent", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(MyFirebaseMessagingService.this, "SomeThing is missing", Toast.LENGTH_SHORT).show();
                }
            });
        }


    }
}

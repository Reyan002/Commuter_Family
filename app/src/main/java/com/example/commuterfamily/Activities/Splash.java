
package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.DashBoardDrawerActivity.DashboardDrawerActivity;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.example.commuterfamily.SessionManager.SessionManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Splash extends AppCompatActivity {

    private ImageView imageView;

    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        if(!isNetworkAvailable()){
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing the App")
                    .setMessage("No Internet Connection,check your settings")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .show();
        }else {
            sessionManager = new SessionManager(this);
            imageView = findViewById(R.id.whitelogo);
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim);
            imageView.startAnimation(animation);


            if (!sessionManager.isLoggedIn()) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Splash.this, SplashScreenActivity.class));


                    }
                }, 2000);
            } else {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Prevalent.currentOnlineUser.setPhone(sessionManager.getUserDetails());

                        startActivity(new Intent(Splash.this, DashboardDrawerActivity.class));

                    }
                }, 2000);


            }


        }

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}

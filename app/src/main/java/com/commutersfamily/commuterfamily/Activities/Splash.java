package com.commutersfamily.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.commutersfamily.commuterfamily.DashBoardDrawerActivity.DashboardDrawerActivity;
import com.commutersfamily.commuterfamily.Prevalent.Prevalent;
import com.commutersfamily.commuterfamily.R;
import com.commutersfamily.commuterfamily.SessionManager.SessionManager;

public class Splash extends AppCompatActivity {

    private ImageView imageView;

    private SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);



        sessionManager=new SessionManager(this);
        imageView=findViewById(R.id.whitelogo);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim);
        imageView.startAnimation(animation);


        if(!sessionManager.isLoggedIn()){
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(Splash.this, IntroActivity.class));


                }
            },2000);
        }
        else{
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Prevalent.currentOnlineUser.setPhone(sessionManager.getUserDetails()) ;

                    startActivity(new Intent(Splash.this, DashboardDrawerActivity.class));

                }
            },2000);


        }

//        sessionManager.imagesList("https://firebasestorage.googleapis.com/v0/b/commuter-family.appspot.com/o/Home%2Fcover1.jpg?alt=media&token=a5e77cd6-fcf7-4ab3-a76a-4fa51af90eae",
//        "https://firebasestorage.googleapis.com/v0/b/commuter-family.appspot.com/o/Home%2Fcover2.jpg?alt=media&token=103a996b-ed93-429d-8339-d0b5918ec3f8",
//                "https://firebasestorage.googleapis.com/v0/b/commuter-family.appspot.com/o/Home%2Fcover3.jpg?alt=media&token=33f115d2-9895-461c-8e4f-8dc4f8766092");


    }
}

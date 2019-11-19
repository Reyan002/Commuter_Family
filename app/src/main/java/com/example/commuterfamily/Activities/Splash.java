package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.example.commuterfamily.SessionManager.SessionManager;

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
                    startActivity(new Intent(Splash.this, SplashScreenActivity.class));


                }
            },2000);
        }
        else{
            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Prevalent.currentOnlineUser.setPhone(sessionManager.getUserDetails()) ;
                    if(sessionManager.isKey()){ DemoClass.CarKey=sessionManager.getIsKey();}

                    startActivity(new Intent(Splash.this,MainActivity.class));


                }
            },2000);


          }


    }
}

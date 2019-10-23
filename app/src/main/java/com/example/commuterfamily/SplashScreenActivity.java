package com.example.commuterfamily;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.commuterfamily.Activities.CFLoginActivity;
import com.example.commuterfamily.Activities.CFSignUp;

public class SplashScreenActivity extends AppCompatActivity {

    private Button signup,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        signup=findViewById(R.id.btn_signup);
        login=findViewById(R.id.btn_login);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreenActivity.this, CFLoginActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SplashScreenActivity.this, CFSignUp.class));

            }
        });
    }
}

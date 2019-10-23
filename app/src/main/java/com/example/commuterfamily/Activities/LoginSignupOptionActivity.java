package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.commuterfamily.R;

public class LoginSignupOptionActivity extends AppCompatActivity {

    private Button login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup_option);
        login=findViewById(R.id.loginCF);
        signup=findViewById(R.id.signuppCF);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignupOptionActivity.this,CFLoginActivity.class));
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginSignupOptionActivity.this,CFSignUp.class));
            }
        });
    }
}

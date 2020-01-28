
package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.commuterfamily.R;

import java.util.Objects;

public class SplashScreenActivity extends AppCompatActivity {

  Button btn_signup, btn_login;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash_screen);

    getSupportActionBar().hide();

    btn_signup = findViewById(R.id.btn_signup);
    btn_login = findViewById(R.id.btn_login);

    btn_signup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(SplashScreenActivity.this, SignUpActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
      }
    });

    btn_login.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
      }
    });

//    findViewById(R.id.textForgPass).setOnClickListener(new View.OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Toast.makeText(SplashScreenActivity.this, "Forgot Password", Toast.LENGTH_SHORT).show();
//      }
//    });

  }

  @Override
  public void onBackPressed() {
    Intent a = new Intent(Intent.ACTION_MAIN);
    a.addCategory(Intent.CATEGORY_HOME);
    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(a);}

}

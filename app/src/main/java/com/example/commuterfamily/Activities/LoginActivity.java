package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.commuterfamily.Fragments.SignUp.PhoneNumber;
import com.example.commuterfamily.R;

import static com.example.commuterfamily.Activities.SignUpActivity.fragmentManager;

public class LoginActivity extends AppCompatActivity {


    public static FragmentManager fragmentManagerLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        fragmentManagerLogin = getSupportFragmentManager();

        if(findViewById(R.id.fragment_container_login) != null) {
            if(savedInstanceState != null) {
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManagerLogin.beginTransaction();
            PhoneNumber numberFragment = new PhoneNumber();
            fragmentTransaction.add(R.id.fragment_container_login, numberFragment, null);
            fragmentTransaction.commit();
        }
       }
}
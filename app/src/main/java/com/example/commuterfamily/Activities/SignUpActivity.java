package com.example.commuterfamily.Activities;



import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.commuterfamily.Fragments.SignUp.NumberFragment;
import com.example.commuterfamily.R;

public class SignUpActivity extends AppCompatActivity {

    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }


            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            NumberFragment numberFragment = new NumberFragment();
            fragmentTransaction.add(R.id.fragment_container, numberFragment, null);
            fragmentTransaction.commit();

        }
    }

}

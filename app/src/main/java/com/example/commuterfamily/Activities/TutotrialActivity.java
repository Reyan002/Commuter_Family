package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.commuterfamily.Fragments.SignUp.NumberFragment;
import com.example.commuterfamily.R;

import static com.example.commuterfamily.Activities.SignUpActivity.fragmentManager;

public class TutotrialActivity extends AppCompatActivity {
    public static FragmentManager fragmentManagerTutorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutotrial);
        getSupportActionBar().hide();

        fragmentManagerTutorial = getSupportFragmentManager();

        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManagerTutorial.beginTransaction();
            NumberFragment numberFragment = new NumberFragment();
            fragmentTransaction.add(R.id.fragment_container, numberFragment, null);
            fragmentTransaction.commit();
        }
    }
}

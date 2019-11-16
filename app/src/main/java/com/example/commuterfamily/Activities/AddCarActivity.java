package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.commuterfamily.AddCar.VehicleType;
import com.example.commuterfamily.R;

public class AddCarActivity extends AppCompatActivity {
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        fragmentManager = getSupportFragmentManager();

        if(findViewById(R.id.fragment_container_add_car) != null) {
            if(savedInstanceState != null) {
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            VehicleType vehivalType = new VehicleType();
            fragmentTransaction.add(R.id.fragment_container_add_car, vehivalType, null);
            fragmentTransaction.commit();
        }
    }
    }

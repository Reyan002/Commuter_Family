package com.commutersfamily.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.commutersfamily.commuterfamily.AddCar.VehicleType;
import com.commutersfamily.commuterfamily.R;

public class AddCarActivity extends AppCompatActivity {
    public static FragmentManager fragmentManagerAddCar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        fragmentManagerAddCar = getSupportFragmentManager();

        if(findViewById(R.id.fragment_container_add_car) != null) {
            if(savedInstanceState != null) {
                return;
            }/**/
            FragmentTransaction fragmentTransaction = fragmentManagerAddCar.beginTransaction();
            VehicleType vehicleType = new VehicleType();
            fragmentTransaction.add(R.id.fragment_container_add_car, vehicleType, null).addToBackStack(null).commit();
        }
    }
    }
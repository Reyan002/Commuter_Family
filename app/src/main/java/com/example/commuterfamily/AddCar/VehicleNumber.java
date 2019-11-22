package com.example.commuterfamily.AddCar;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.commuterfamily.Activities.AddCarActivity;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VehicleNumber extends Fragment {
    private EditText number;
    private FloatingActionButton next;
    public VehicleNumber() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.car_number,container,false);
        number=view.findViewById(R.id.v_number);
        next=view.findViewById(R.id.nextCarNumber);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(number.getText().toString())) {
                    DemoClass.vnum=number.getText().toString();
                    AddCarActivity.fragmentManagerAddCar.beginTransaction()
                            .replace(R.id.fragment_container_add_car, new DriverLicense()
                                    , null)
                            .commit();

                }

                else{
                    Toast.makeText(getContext(), "Please Provide Vehicle Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}

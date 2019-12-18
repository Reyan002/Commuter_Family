package com.example.commuterfamily.AddCar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.commuterfamily.Activities.AddCarActivity;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VehicleType extends Fragment {
    private FloatingActionButton next;
    private String Vtype;
    private Spinner spinner;
    public VehicleType() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.car_type,container,false);
        spinner=view.findViewById(R.id.v_type);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vtype = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Vehicle");
        categories.add("Car");
        categories.add("Bike");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        next=view.findViewById(R.id.nextCarType);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Vtype=="Select Vehicle"){
                    Toast.makeText(getContext(), "Please Select Vehicle type", Toast.LENGTH_SHORT).show();
                }
                else{
                    DemoClass.vtype=Vtype;
                    AddCarActivity.fragmentManagerAddCar.beginTransaction()
                            .replace(R.id.fragment_container_add_car, new VehicleNumber(), null)
                            .addToBackStack(null).commit();
                }

            }
        });


        return view;
    }
}

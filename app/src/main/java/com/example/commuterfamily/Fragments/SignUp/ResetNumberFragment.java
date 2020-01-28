package com.example.commuterfamily.Fragments.SignUp;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.commuterfamily.Activities.LoginActivity;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.google.android.gms.plus.PlusOneButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ResetNumberFragment extends Fragment {
   private EditText number;
   private Button next;

    private String mParam1;
    private String mParam2;

    public ResetNumberFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reset_number, container, false);

        number=view.findViewById(R.id.numberRes);
        next=view.findViewById(R.id.next);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoClass.P_number=number.getText().toString();

                nextToChange(number.getText().toString());
            }
        });



        return view;
    }

    private void nextToChange(String number) {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users").child(number);
        ref.child("Phone").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    LoginActivity.fragmentManagerLogin.beginTransaction()
                            .replace(R.id.fragment_container_login, new ResetPassword(), null)
                            .commit();



                }
                else{
                    Toast.makeText(getContext(), "Type Correct Number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}

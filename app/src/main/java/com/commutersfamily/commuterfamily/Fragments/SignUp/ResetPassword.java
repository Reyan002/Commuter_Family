package com.commutersfamily.commuterfamily.Fragments.SignUp;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.commutersfamily.commuterfamily.Activities.LoginActivity;
import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class ResetPassword extends Fragment {
 private EditText pass,cpass;
 private Button next;
    public ResetPassword() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
     View view =inflater.inflate(R.layout.fragment_reset_password, container, false);

     pass=view.findViewById(R.id.pass);
     cpass=view.findViewById(R.id.cpass);
     next=view.findViewById(R.id.res);

     next.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(pass.getText().toString().equals(cpass.getText().toString())){
                 nextToReset(DemoClass.P_number);
             }
            else{
                 Toast.makeText(getContext(), "Eror", Toast.LENGTH_SHORT).show();
             }
         }
     });


     return view;
    }

    private void nextToReset(String Number) {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Users");
        HashMap<String, Object> userMap = new HashMap<>();
        userMap. put("Password", cpass.getText().toString());

        ref.child(Number).updateChildren(userMap);
        LoginActivity.fragmentManagerLogin.beginTransaction()
                .replace(R.id.fragment_container_login, new Paswoord(), null)
                .commit();
    }


}

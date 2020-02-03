package com.example.commuterfamily.DashBoardDrawerActivity.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.commuterfamily.Activities.MainActivity;
import com.example.commuterfamily.R;
import com.example.commuterfamily.SessionManager.SessionManager;


public class Setting extends Fragment {
    private Button logout;
    private SessionManager sessionManager;

   public Setting() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View vieew=inflater.inflate(R.layout.fragment_setting, container, false);

              sessionManager=new SessionManager(getContext());

      logout=vieew.findViewById(R.id.logout);
      logout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {

              showPopup();
          }
      });


      return vieew;
    }


    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setMessage("Are you sure?")
                .setPositiveButton("Logout", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        logout();

                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    private void logout() {

        sessionManager.logoutUser();
        Toast.makeText(getContext(), "Logged Out Succesfully", Toast.LENGTH_SHORT).show();
    }


}

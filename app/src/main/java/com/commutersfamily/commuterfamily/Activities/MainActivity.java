package com.commutersfamily.commuterfamily.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.DashBoardDrawerActivity.DashboardDrawerActivity;
import com.commutersfamily.commuterfamily.R;
import com.commutersfamily.commuterfamily.SessionManager.SessionManager;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

     private Boolean check=false;
     SessionManager sessionManager;
     ImageButton logOutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(MainActivity.this);
        logOutBtn = findViewById(R.id.logoutBtn);

        findViewById(R.id.ride).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, DashboardDrawerActivity.class));
                 overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                 DemoClass.RouteFor = "passenger";
             }
         });

        findViewById(R.id.drive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DashboardDrawerActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

                DemoClass.RouteFor = "driver";

            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 showPopup();
                startActivity(new Intent(MainActivity.this,Notification.class));
            }
        });



    }
    @Override
    public void onBackPressed() {
        if(!check){
            Snackbar.make( findViewById(R.id.content_main_activit),"Press one more time to exit",Snackbar.LENGTH_SHORT).show();
           // Toast.makeText(this,"Press one more time to exit",Toast.LENGTH_SHORT).show();
            check=true;
        }
        else {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);}

    }

    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
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
        Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
    }


}
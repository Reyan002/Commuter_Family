package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.commuterfamily.R;
import com.example.commuterfamily.SessionManager.SessionManager;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

     private Boolean check=false;
     SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(MainActivity.this);

        findViewById(R.id.Ride).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this,RiderRouteActivity.class));
                 overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

             }
         });
        findViewById(R.id.Drive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DriveActivity.class));
                overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.kebab_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.logout_text:
//                Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();
                showPopup();
                return true;
        }

        return super.onOptionsItemSelected(item);
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
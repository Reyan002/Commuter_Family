package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.commuterfamily.R;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

     private Boolean check=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         findViewById(R.id.Ride).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this,RideActivity.class));
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
}

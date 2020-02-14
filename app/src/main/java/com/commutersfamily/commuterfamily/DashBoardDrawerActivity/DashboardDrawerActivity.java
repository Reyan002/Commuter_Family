package com.commutersfamily.commuterfamily.DashBoardDrawerActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.commutersfamily.commuterfamily.Activities.Notification;
import com.commutersfamily.commuterfamily.Activities.UpdateProfile;
import com.commutersfamily.commuterfamily.Classes.User;
import com.commutersfamily.commuterfamily.Prevalent.Prevalent;
import com.commutersfamily.commuterfamily.R;

import android.os.Handler;
import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.MenuItemCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.commutersfamily.commuterfamily.SessionManager.SessionManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardDrawerActivity extends AppCompatActivity {

    long count;
    private TextView userName,email;
    private ImageView profilePicture;
    private boolean check=false;
    private AppBarConfiguration mAppBarConfiguration;
    TextView textCartItemCount;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sessionManager = new SessionManager(DashboardDrawerActivity.this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_passenger,
                R.id.nav_wallet, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.bringToFront();
         
       NavGraph navGraph = navController.getGraph();
        navGraph.setStartDestination(R.id.nav_passenger);
        navController.setGraph(navGraph);

        View header = navigationView.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardDrawerActivity.this, UpdateProfile.class));            }
        });

        userName=header.findViewById(R.id.nameU);
        email=header.findViewById(R.id.emailU);
        profilePicture=header.findViewById(R.id.imageView);

        fetch();

    }


    public void fetch(){
        DatabaseReference get=FirebaseDatabase.getInstance().getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        get.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName.setText(dataSnapshot.getValue(User.class).getName());
                email.setText(dataSnapshot.getValue(User.class).getEmail());
                Picasso.get().load(dataSnapshot.getValue(User.class).getImage()).placeholder(R.drawable.ic_person_black_24dp).into(profilePicture);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showPopup() {
        AlertDialog.Builder alert = new AlertDialog.Builder(DashboardDrawerActivity.this);
        alert.setMessage("Are you sure?")
                .setPositiveButton("SignOut", new DialogInterface.OnClickListener()                 {

                    public void onClick(DialogInterface dialog, int which) {

                        sessionManager.logoutUser();

                    }
                }).setNegativeButton("Cancel", null);

        AlertDialog alert1 = alert.create();
        alert1.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_drawer,menu);
        final MenuItem menuItem=menu.findItem(R.id.action_settings);




        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = actionView.findViewById(R.id.cart_badge);


        showNumber();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardDrawerActivity.this, "Hello Notification", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardDrawerActivity.this, Notification.class));
                textCartItemCount.setText("");

            }
        });

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private long showNumber(){


        long ncount=0;

        DatabaseReference notificatioRef;
        notificatioRef = FirebaseDatabase.getInstance().getReference().child("Notification").child(Prevalent.currentOnlineUser.getPhone());

        notificatioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    count = dataSnapshot.getChildrenCount();

                    setupBadge( count);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        ncount=count;
        return ncount;

    }

    private void setupBadge(long count) {

        if (textCartItemCount != null) {
            if ( count == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(count, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
//        if(!check){
//            Snackbar.make( findViewById(R.id.nav_host_fragment),"Press one more time to exit",Snackbar.LENGTH_SHORT).show();
//            // Toast.makeText(this,"Press one more time to exit",Toast.LENGTH_SHORT).show();
//            check=true;
//        }
//        else {
//            Intent a = new Intent(Intent.ACTION_MAIN);
//            a.addCategory(Intent.CATEGORY_HOME);
//            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(a);
        if (!check) {
            Toast.makeText(this, "Please click Back again to exit", Toast.LENGTH_SHORT).show();
            check = true;
        }else{

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent a = new Intent(Intent.ACTION_MAIN);
                    a.addCategory(Intent.CATEGORY_HOME);
                    a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(a);
                    finish();
                }
            }, 400);
            check = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                finish();
            }
        }, 400);
        check = false;
        }
    }
}
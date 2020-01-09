package com.example.commuterfamily.DashBoardDrawerActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.commuterfamily.Activities.DriveActivity;
import com.example.commuterfamily.Activities.Notification;
import com.example.commuterfamily.Activities.RiderRouteActivity;
import com.example.commuterfamily.DashBoardDrawerActivity.ui.HomeFragment;
import com.example.commuterfamily.R;

import android.view.MenuInflater;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Toast;

public class DashboardDrawerActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_drive, R.id.nav_passenger,
                R.id.nav_wallet, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.nav_home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.nav_drive:
                        startActivity(new Intent(DashboardDrawerActivity.this, DriveActivity.class));
                        Toast.makeText(DashboardDrawerActivity.this, "Driver Activity", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_passenger:
                        startActivity(new Intent(DashboardDrawerActivity.this, RiderRouteActivity.class));
                        Toast.makeText(DashboardDrawerActivity.this, "Passenger Activity", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_wallet:
                        Toast.makeText(DashboardDrawerActivity.this, "Wallet Activity work in progress...!!!", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_about:
                        Toast.makeText(DashboardDrawerActivity.this, "About Us Activity work in progress...!!!", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dashboard_drawer,menu);
        MenuItem menuItem=menu.findItem(R.id.action_settings);
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Toast.makeText(DashboardDrawerActivity.this, "Hello Notification", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(DashboardDrawerActivity.this, Notification.class));
                return false;
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
//    public void displaySelectedScreen(int itemId){
//
//        Fragment fragment = null;
//        switch (itemId){
//            case R.id.nav_home:
//                fragment = new HomeFragment();
//                break;
//            case R.id.nav_drive:
//                startActivity(new Intent(DashboardDrawerActivity.this, DriveActivity.class));
//                Toast.makeText(this, "Driver Activity", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.nav_passenger:
//                startActivity(new Intent(DashboardDrawerActivity.this, RiderRouteActivity.class));
//                Toast.makeText(this, "Passenger Activity", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.nav_wallet:
//                Toast.makeText(this, "Wallet Activity work in progress...!!!", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.nav_about:
//                Toast.makeText(this, "About Us Activity work in progress...!!!", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//    }
}
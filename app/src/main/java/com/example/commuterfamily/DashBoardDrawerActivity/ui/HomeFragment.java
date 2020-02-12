package com.example.commuterfamily.DashBoardDrawerActivity.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.commuterfamily.Activities.AddCarActivity;
import com.example.commuterfamily.Activities.DriveActivity;
import com.example.commuterfamily.Activities.IntroActivity;
import com.example.commuterfamily.Activities.MapsActivity;
import com.example.commuterfamily.Activities.RideActivity;
import com.example.commuterfamily.Activities.RiderRouteActivity;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.DashBoardDrawerActivity.DashboardDrawerActivity;
import com.example.commuterfamily.R;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

public class HomeFragment extends Fragment {

    private View myView;
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    static FragmentManager fragmentManager;
    private Handler handler = new Handler();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home_fragment, container, false);

        viewPager = myView.findViewById(R.id.pager);
        sliderDotspanel = myView.findViewById(R.id.layout_dots);

        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());

        viewPager.setAdapter(viewPagerAdapter);

        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {

            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.not_active_dots));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                    (LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.not_active_dots));

                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dots));
//                autoSlider(viewPager);
//                final Handler handler = new Handler();
//
//                Runnable rr = new Runnable() {
//                    public void run() {
//                        int pos = viewPager.getCurrentItem();
//                        if(pos > dotscount && pos != dots.length - 1){
//                            dotscount = pos;
//                            dotscount++;
//                        }
//                        else if(pos < (dotscount-1)){
//                            dotscount = pos;
//                            dotscount++;
//                        }
//                        viewPager.setCurrentItem(dotscount, true);
//                        dotscount++;
//                        if (dotscount >= dots.length)
//                            dotscount = 0;
//                    }};
//                handler.postDelayed(rr, 3000);
//
////                final Runnable Update = new Runnable() {
////                    public void run() {
////                        if (viewPager.getCurrentItem() == dotscount-1) {
////                            dotscount = 0;
////                        }
////                        viewPager.setCurrentItem(dotscount++, true);
//                        dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dots));
////                    }
//                };
//
//                Timer timer = new Timer(); // This will create a new Thread
//                timer.schedule(new TimerTask() { // task to be scheduled
//                    @Override
//                    public void run() {
//                        handler.post(Update);
//                    }
//                }, 300, 5000);
            }
//                final Handler handler = new Handler();
//                final Runnable update = new Runnable() {
//                    public void run() {
//                        if (viewPager.getCurrentItem() == dots.length-1) {
//                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
//                        }
//                        viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
//                        dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.active_dots));
//                    }
//                };
//
//                new Timer().schedule(new TimerTask() {
//                    @Override
//                    public void run() {
//                        handler.post(update);
//                    }
//                }, 3000, 4000);
//
//            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTaskTimer(), 3000, 4000);


        myView.findViewById(R.id.card1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddCarActivity.class));
            }
        });

        myView.findViewById(R.id.card2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoClass.RouteFor="Driver";
                startActivity(new Intent(getContext(), MapsActivity.class));

            }
        });

        myView.findViewById(R.id.card3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Distance Calculator under construction...:p", Toast.LENGTH_SHORT).show();
            }
        });

        myView.findViewById(R.id.card4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DriveActivity.class));
//                Fragment fragment = new AboutUs();
//                    fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new AboutUs()).commit();
//                Fragment fragment;
//                fragment = new AboutUs();
////                if (fragment != null) {
//                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                    ft.replace(R.id.nav_host_fragment, fragment);
//                    ft.commit();
//                }
            }
        });
        return myView;
    }

    public class MyTaskTimer extends TimerTask {
        @Override
        public void run() {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0){
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1){
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

}

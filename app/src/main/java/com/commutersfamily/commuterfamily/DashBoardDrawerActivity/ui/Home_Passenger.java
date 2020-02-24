package com.commutersfamily.commuterfamily.DashBoardDrawerActivity.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.commutersfamily.commuterfamily.Activities.HistoryActivity;
import com.commutersfamily.commuterfamily.Activities.MapsActivity;
import com.commutersfamily.commuterfamily.Activities.RiderRouteActivity;
import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.R;

import java.util.Timer;
import java.util.TimerTask;

public class Home_Passenger extends Fragment {

    View myView;
    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home_passenger, container, false);

        viewPager = myView.findViewById(R.id.pagerP);
        sliderDotspanel = myView.findViewById(R.id.layout_dotsP);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getContext());

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
            public void onPageSelected(int position) {

                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.not_active_dots));
                }


                dots[position].setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.active_dots));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTaskTimer(), 2000, 4000);


        myView.findViewById(R.id.card1p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoClass.RouteFor = "Rider";
                startActivity(new Intent(getContext(), MapsActivity.class));
            }
        });

        myView.findViewById(R.id.card2p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), RiderRouteActivity.class));
            }
        });

        myView.findViewById(R.id.card3p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoClass.distance="distance";
                startActivity(new Intent(getContext(), MapsActivity.class));
            }
        });

        myView.findViewById(R.id.card4p).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HistoryActivity.class));
            }
        });

        return myView;
    }

    public class MyTaskTimer extends TimerTask {
        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (viewPager.getCurrentItem() == 0) {
                            viewPager.setCurrentItem(1);
                        } else if (viewPager.getCurrentItem() == 1) {
                            viewPager.setCurrentItem(2);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        }
    }
}
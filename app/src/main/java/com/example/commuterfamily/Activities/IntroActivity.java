
package com.example.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.commuterfamily.Classes.IntroClasses.IntroViewPagerAdapter;
import com.example.commuterfamily.Classes.IntroClasses.ScreenItem;
import com.example.commuterfamily.DashBoardDrawerActivity.DashboardDrawerActivity;
import com.example.commuterfamily.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

  private ViewPager viewPager;
  private IntroViewPagerAdapter adapter;
  TabLayout tabIndicator;
  int pos = 0;
  Button getStart;
  TextView btnnext;
  Animation btnAnim;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intro);

    btnnext = findViewById(R.id.button_NxtInt);
    getStart = findViewById(R.id.getStartBtn);
    btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_anim);
    viewPager = findViewById(R.id.viewPagerIntro);
    tabIndicator = findViewById(R.id.tab_indicator);
    tabIndicator.setSelectedTabIndicator(null);
    final List<ScreenItem> mlist = new ArrayList<>();
    adapter = new IntroViewPagerAdapter(this, mlist);

    mlist.add(new ScreenItem("Find a Perfect Match","@string/scr2",R.drawable.intro_match));
    mlist.add(new ScreenItem("Cheaper & Affordable","@string/scr4",R.drawable.intro_payment));
    mlist.add(new ScreenItem("Reliable and Safe","@string/scr5",R.drawable.intro_safe));
    mlist.add(new ScreenItem("Empower Women","string/scr6",R.drawable.intro_female_only));
    mlist.add(new ScreenItem("Set Schedule","string/scr6",R.drawable.intro_set_schedule));

    viewPager.setAdapter(adapter);
    tabIndicator.setupWithViewPager(viewPager);

    findViewById(R.id.textSkip).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(IntroActivity.this, DashboardDrawerActivity.class));
      }
    });

    btnnext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        pos = viewPager.getCurrentItem();
        if (pos <= mlist.size()){
          pos++;
          viewPager.setCurrentItem(pos);
        }
        if (pos == mlist.size()-1){
          loadLastScreen();
        }
      }
    });

    tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == mlist.size()-1){
          loadLastScreen();
        }
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });

    getStart.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(IntroActivity.this, SplashScreenActivity.class));
      }
    });

  }

  private void loadLastScreen() {

    btnnext.setVisibility(View.INVISIBLE);
    getStart.setVisibility(View.VISIBLE);
    tabIndicator.setVisibility(View.INVISIBLE);

    getStart.setAnimation(btnAnim);

  }
}
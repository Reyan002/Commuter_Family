package com.commutersfamily.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.commutersfamily.commuterfamily.Classes.IntroClasses.IntroViewPagerAdapter;
import com.commutersfamily.commuterfamily.Classes.IntroClasses.ScreenItem;
import com.commutersfamily.commuterfamily.R;
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

    if (restorePrefData()){
        Intent intent = new Intent(IntroActivity.this,SplashScreenActivity.class);
        startActivity(intent);
        finish();
    }

    setContentView(R.layout.activity_intro);

    btnnext = findViewById(R.id.button_NxtInt);
    getStart = findViewById(R.id.getStartBtn);
    btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_anim);
    viewPager = findViewById(R.id.viewPagerIntro);
    tabIndicator = findViewById(R.id.tab_indicator);
    tabIndicator.setSelectedTabIndicator(null);
    final List<ScreenItem> mlist = new ArrayList<>();
    adapter = new IntroViewPagerAdapter(this, mlist);

    mlist.add(new ScreenItem("Find a Perfect Match",getString(R.string.scr2),R.drawable.intro_match));
    mlist.add(new ScreenItem("Cheaper & Affordable",getString(R.string.scr4),R.drawable.intro_payment));
    mlist.add(new ScreenItem("Reliable and Safe",getString(R.string.scr5),R.drawable.intro_safe));
    mlist.add(new ScreenItem("Empower Women",getString(R.string.scr6),R.drawable.intro_female_only));
    mlist.add(new ScreenItem("Set Schedule",getString(R.string.scr7),R.drawable.intro_set_schedule));

    viewPager.setAdapter(adapter);
    tabIndicator.setupWithViewPager(viewPager);

    findViewById(R.id.textSkip).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(IntroActivity.this, SplashScreenActivity.class));
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

        savePrefsData();
        finish();
      }
    });

  }

    private void loadLastScreen() {

    btnnext.setVisibility(View.INVISIBLE);
    getStart.setVisibility(View.VISIBLE);
    tabIndicator.setVisibility(View.INVISIBLE);

    getStart.setAnimation(btnAnim);

  }

  private void savePrefsData(){
      SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
      SharedPreferences.Editor editor = pref.edit();
      editor.putBoolean("isIntrupted",true);
      editor.commit();
  }

  private boolean restorePrefData() {

    SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
    Boolean isIntroActOpenBefore = pref.getBoolean("isIntrupted",false);
    return isIntroActOpenBefore;
  }
}
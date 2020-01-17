package com.example.commuterfamily.DashBoardDrawerActivity.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.commuterfamily.R;
import com.squareup.picasso.Picasso;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private String [] images = {"https://firebasestorage.googleapis.com/v0/b/commuter-family.appspot.com/o/Home%2Fcover1.jpg?alt=media&token=a5e77cd6-fcf7-4ab3-a76a-4fa51af90eae",
            "https://firebasestorage.googleapis.com/v0/b/commuter-family.appspot.com/o/Home%2Fcover2.jpg?alt=media&token=103a996b-ed93-429d-8339-d0b5918ec3f8",
            "https://firebasestorage.googleapis.com/v0/b/commuter-family.appspot.com/o/Home%2Fcover3.jpg?alt=media&token=33f115d2-9895-461c-8e4f-8dc4f8766092"};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.images_view, null);
        ImageView imageView = view.findViewById(R.id.imagesView);
        Picasso.get().load(images[position]).into(imageView);


        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}
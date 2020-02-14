package com.commutersfamily.commuterfamily.Classes.IntroClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.commutersfamily.commuterfamily.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class IntroViewPagerAdapter extends PagerAdapter {

    Context context;
    List<ScreenItem> dataList;

    public IntroViewPagerAdapter(Context context, List<ScreenItem> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_intro1,null);

        ImageView imageView = view.findViewById(R.id.imageIntro);
        TextView titleText = view.findViewById(R.id.textViewIntroTitle);
        TextView desText = view.findViewById(R.id.textViewIntroDes);

        titleText.setText(dataList.get(position).getTitle());
        desText.setText(dataList.get(position).getDes());
        imageView.setImageResource(dataList.get(position).getImageInt());

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

}

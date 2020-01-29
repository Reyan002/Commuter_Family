package com.example.commuterfamily.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commuterfamily.Activities.Notification;
import com.example.commuterfamily.Classes.Nification;
import com.example.commuterfamily.Classes.NotificationDisplay;
import com.example.commuterfamily.Classes.User;
import com.example.commuterfamily.Interfaces.itemClickListener;
import com.example.commuterfamily.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView product_name,product_desc,product_price;
    public itemClickListener listener;

    public NotificationAdapter(View itemView) {
        super(itemView);

        product_name=(TextView)itemView.findViewById(R.id.title_noti);
        product_desc=(TextView)itemView.findViewById(R.id.starting);
        product_price=(TextView)itemView.findViewById(R.id.date);

    }

    public void setItemClickListener(itemClickListener listener){
        this.listener=listener;
    }
    @Override
    public void onClick(View v) {
        listener.OnClick(v,getAdapterPosition(),false);

    }
}
package com.commutersfamily.commuterfamily.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.commutersfamily.commuterfamily.Interfaces.itemClickListener;
import com.commutersfamily.commuterfamily.R;

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
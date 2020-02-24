package com.commutersfamily.commuterfamily.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.commutersfamily.commuterfamily.Classes.Nification;
import com.commutersfamily.commuterfamily.Interfaces.itemClickListener;
import com.commutersfamily.commuterfamily.R;

public class HistoryAdapter extends  RecyclerView.ViewHolder{

    public TextView product_name,product_desc,product_price;



    public HistoryAdapter(@NonNull View itemView) {
        super(itemView);
        product_name= itemView.findViewById(R.id.personName_history);
        product_desc= itemView.findViewById(R.id.history_date);
        product_price= itemView.findViewById(R.id.history_time);
    }
}

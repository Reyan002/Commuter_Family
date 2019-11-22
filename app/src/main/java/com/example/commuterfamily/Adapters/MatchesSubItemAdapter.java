package com.example.commuterfamily.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MatchesSubItemAdapter extends RecyclerView.Adapter<MatchesSubItemAdapter.MyViewHolder> {

    ArrayList<Routes> arrayList;
    Context context;

    public MatchesSubItemAdapter(ArrayList<Routes> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_item_adapter, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MatchesSubItemAdapter.MyViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView1,textView2,textView3,textView4, textView5;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView1 = itemView.findViewById(R.id.textview1_adapterMI);
            textView2 = itemView.findViewById(R.id.textview2_adapterMI);
            textView3 = itemView.findViewById(R.id.textview3_adapterMI);
            textView4 = itemView.findViewById(R.id.textview4_adapterMI);
            textView5 = itemView.findViewById(R.id.textview5_adapterMI);
        }
    }
}

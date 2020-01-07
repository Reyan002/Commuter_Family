package com.example.commuterfamily.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commuterfamily.Activities.Notification;
import com.example.commuterfamily.Classes.Nification;
import com.example.commuterfamily.Classes.NotificationDisplay;
import com.example.commuterfamily.R;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    private ArrayList<NotificationDisplay> list;
    private Context context;

    public NotificationAdapter(ArrayList<NotificationDisplay> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notify_item,
                parent, false);
        return new NotificationViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {

        final NotificationDisplay notify= list.get(position);
        holder.Title.setText(notify.getName());
        holder.Pick.setText(notify.getPhone());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
     private View view;
    //        public TextView txtProductPrice;
    public TextView Title;
    public TextView Pick;
    public TextView DT;


    public NotificationViewHolder(View itemView) {
        super(itemView);
        this.view=itemView;

        Title= itemView.findViewById(R.id.title_noti);
        Pick= itemView.findViewById(R.id.starting);
        DT= itemView.findViewById(R.id.date);
    }

}

//

}
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
import com.example.commuterfamily.Classes.User;
import com.example.commuterfamily.R;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {


    private ArrayList<User> list;
    private Context context;
    private    ArrayList<String> date ;
    private    ArrayList<String> time ;

    public NotificationAdapter(ArrayList<User> list, Context context, ArrayList<String> date, ArrayList<String> time) {
        this.list = list;
        this.context = context;
        this.date = date;
        this.time = time;
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

        String Date=date.get(position);
        String Time=time.get(position);
      User notify= list.get(position);
//        holder.Title.setText(notify.getName());
        holder.Pick.setText(notify.getName()+" Phone : "+notify.getPhone());
        holder.DT.setText(Date+"  "+Time);


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
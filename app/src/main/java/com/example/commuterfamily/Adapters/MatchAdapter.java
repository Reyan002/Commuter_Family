package com.example.commuterfamily.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.R;

import java.util.ArrayList;
import java.util.List;

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.ViewHolder> {
    ArrayList<Routes> myCarList;
    Context context;

    public MatchAdapter(ArrayList<Routes> myCarList, Context context) {
        this.myCarList = myCarList;
        this.context = context;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.match_items,
                parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Routes cars= myCarList.get(position);
        holder.txtProductnName.setText(cars.getDay());
        holder.txtProductPrice.setText( cars.getShift());
        holder.txtProductQuantity.setText(cars.getAdressFrom());
        holder.toLocation.setText(cars.getAdressTo());
        holder.fromTIme.setText(cars.getMTimeFrom()+"  "+cars.getMTimeTo()+cars.getETimeFrom()+"  "+cars.getETimeTo() );

    }

    @Override
    public int getItemCount() {
        return myCarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtProductnName;
        private View view;
        public TextView txtProductPrice;
         public TextView txtProductQuantity;
        public TextView toLocation;
        public TextView fromTIme;


        public ViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            txtProductnName=(TextView)itemView.findViewById(R.id.cart_product_name_m);
            txtProductPrice=(TextView)itemView.findViewById(R.id.cart_product_price_m);
            txtProductQuantity=(TextView)itemView.findViewById(R.id.cart_product_quantity_m);
            toLocation=(TextView)itemView.findViewById(R.id.location_to_m);
            fromTIme=(TextView)itemView.findViewById(R.id.from_time_m);
        }

    }

    //

}

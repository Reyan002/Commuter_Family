package com.example.commuterfamily.Adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.commuterfamily.Interfaces.itemClickListener;
import com.example.commuterfamily.R;

public class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtProductnName,txtProductPrice,txtProductQuantity,toLocation,fromTIme,ToTime;
    private itemClickListener itemclicklistener;



    public MatchViewHolder(@NonNull View itemView) {
        super(itemView);
        txtProductnName=(TextView)itemView.findViewById(R.id.cart_product_name_m);
        txtProductPrice=(TextView)itemView.findViewById(R.id.cart_product_price_m);
        txtProductQuantity=(TextView)itemView.findViewById(R.id.cart_product_quantity_m);
        toLocation=(TextView)itemView.findViewById(R.id.location_to_m);
        fromTIme=(TextView)itemView.findViewById(R.id.from_time_m);
    }


    @Override
    public void onClick(View v) {
        itemclicklistener.OnClick(v,getAdapterPosition(),false);
    }

    public void setItemclicklistener(itemClickListener itemclicklistener) {
        this.itemclicklistener = itemclicklistener;
    }
}

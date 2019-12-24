package com.example.commuterfamily.Adapters;


import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.commuterfamily.Interfaces.itemClickListener;
import com.example.commuterfamily.R;


public class CartViewHolder extends RecyclerView.ViewHolder{
   public TextView
//           txtProductnName,
           txtProductPrice,txtProductQuantity,toLocation,fromTIme,ToTime;
    public ImageButton kebab;

    public CartViewHolder(View itemView) {
        super(itemView);

//        txtProductnName=(TextView)itemView.findViewById(R.id.cart_product_name);
        txtProductPrice=(TextView)itemView.findViewById(R.id.cart_product_price);
        txtProductQuantity=(TextView)itemView.findViewById(R.id.cart_product_quantity);
        toLocation=(TextView)itemView.findViewById(R.id.location_to);
        fromTIme=(TextView)itemView.findViewById(R.id.from_time);
        kebab =(ImageButton) itemView.findViewById(R.id.kebabCard);
     }
}

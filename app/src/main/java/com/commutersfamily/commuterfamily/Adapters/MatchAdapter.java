package com.commutersfamily.commuterfamily.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.commutersfamily.commuterfamily.Activities.MatchRouteDetailActivity;
import com.commutersfamily.commuterfamily.Activities.RiderRouteMapActivity;
import com.commutersfamily.commuterfamily.Classes.Routes;
import com.commutersfamily.commuterfamily.R;

import java.util.ArrayList;

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

        final Routes cars= myCarList.get(position);
        holder.txtProductnName.setText(cars.getDay());
//        holder.txtProductPrice.setText( cars.getShift());
        holder.txtProductQuantity.setText(cars.getAdressFrom());
        holder.toLocation.setText(cars.getAdressTo());
        holder.fromTIme.setText(cars.getMTimeFrom()+"  "+cars.getMTimeTo()+cars.getETimeFrom()+"  "+cars.getETimeTo() );
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence options[]=new CharSequence[]{
                         "View Details of your Commute","View Route"
                };
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Route Option");
                builder.setItems(options , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog , int which) {

                        if(which==0){

                           Intent intent=new Intent(context, MatchRouteDetailActivity.class);
                           intent.putExtra("number",cars.getNumber());
                           intent.putExtra("shift",cars.getShift());
                           intent.putExtra("day",cars.getDay());
                           intent.putExtra("time_m_f",cars.getMTimeFrom());
                           intent.putExtra("time_m_t",cars.getMTimeTo());
                           intent.putExtra("time_e_f",cars.getETimeFrom());
                           intent.putExtra("time_e_t",cars.getETimeTo());
                           intent.putExtra("from",cars.getAdressFrom());
                           intent.putExtra("to",cars.getAdressTo());
                           intent.putExtra("pick",cars.getPickUp());
//                           Toast.makeText(context, cars.getNumber(), Toast.LENGTH_SHORT).show();
                           intent.putExtra("rid",cars.getRouteID());
                           intent.putExtra("latFrom",String.valueOf( cars.getLocFrom().getLat()));
                           intent.putExtra("longFrom",String.valueOf( cars.getLocFrom().getLong()));
//                           Toast.makeText(context, cars.getRouteID(), Toast.LENGTH_SHORT).show();
                           context.startActivity(intent);
                        }
                        if(which==1){
                            Intent intent=new Intent(context, RiderRouteMapActivity.class);
                            intent.putExtra("pid",cars.getRouteID());
                            intent.putExtra("latFrom",String.valueOf( cars.getLocFrom().getLat()));
                            intent.putExtra("longFrom",String.valueOf( cars.getLocFrom().getLong()));
                            intent.putExtra("latTo",String.valueOf( cars.getLocTo().getLat()));
                            intent.putExtra("longTo",String.valueOf( cars.getLocTo().getLong()));

                            context.startActivity(intent);
                        }


                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myCarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtProductnName;
        private View view;
//        public TextView txtProductPrice;
         public TextView txtProductQuantity;
        public TextView toLocation;
        public TextView fromTIme;
        private CardView layout;


        public ViewHolder(View itemView) {
            super(itemView);
            this.view=itemView;
            txtProductnName=(TextView)itemView.findViewById(R.id.cart_product_name_m);
            layout= itemView.findViewById(R.id.caardviewmatch);
//            txtProductPrice=(TextView)itemView.findViewById(R.id.cart_product_price_m);
            txtProductQuantity=(TextView)itemView.findViewById(R.id.cart_product_quantity_m);
            toLocation=(TextView)itemView.findViewById(R.id.location_to_m);
            fromTIme=(TextView)itemView.findViewById(R.id.from_time_m);
        }

    }

    //

}

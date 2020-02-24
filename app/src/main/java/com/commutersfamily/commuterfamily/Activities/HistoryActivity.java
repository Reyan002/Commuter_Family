package com.commutersfamily.commuterfamily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commutersfamily.commuterfamily.Adapters.HistoryAdapter;
import com.commutersfamily.commuterfamily.Adapters.NotificationAdapter;
import com.commutersfamily.commuterfamily.Classes.DemoClass;
import com.commutersfamily.commuterfamily.Classes.Nification;
import com.commutersfamily.commuterfamily.Classes.User;
import com.commutersfamily.commuterfamily.Prevalent.Prevalent;
import com.commutersfamily.commuterfamily.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private DatabaseReference getNotificatioRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recyclerHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getNotificatioRef=FirebaseDatabase.getInstance().getReference().child("Connected Commuter");
        recyclerView.setHasFixedSize(true);


        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("History");

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Nification> options=
                new FirebaseRecyclerOptions.Builder<Nification>()
                        .setQuery(getNotificatioRef.child(Prevalent.currentOnlineUser.getPhone()),Nification.class)
                        .build();


        FirebaseRecyclerAdapter<Nification, HistoryAdapter> adapter=
                new FirebaseRecyclerAdapter<Nification, HistoryAdapter>(options) {


                    @Override
                    protected void onBindViewHolder(@NonNull final HistoryAdapter notificationAdapter, int i, @NonNull final Nification nification) {

                        notificationAdapter.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent=new Intent(HistoryActivity.this,MatchRouteDetailActivity.class);
                                intent.putExtra("rid",nification.getNotiId());
                                intent.putExtra("number",nification.getFrom());
                                DemoClass.commuterMatch =nification.getWantTo();
                                startActivity(intent);
                            }
                        });
                        notificationAdapter.product_desc.setText(nification.getDate() );
                        notificationAdapter.product_price.setText(nification.getTime() );
                        DatabaseReference noi=getRef(i).child("from").getRef();
                        noi.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                if(dataSnapshot.exists()){

                                    DatabaseReference reference;
                                    reference= FirebaseDatabase.getInstance().getReference().child("Users");
                                    reference.child(dataSnapshot.getValue().toString()).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            notificationAdapter.product_name.setText(dataSnapshot.getValue(User.class).getName()  );
                                            //notificationAdapter.product_name.setText(dataSnapshot.getValue(User.class).getPhone());
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });
                                }
                                else{
                                    Toast.makeText(HistoryActivity.this, "No any Notification", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }



                    @Override
                    public HistoryAdapter onCreateViewHolder(ViewGroup parent , int viewType) {
                        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item,parent,false);
                        HistoryAdapter holder=new HistoryAdapter(view);
                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}

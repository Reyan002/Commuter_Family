package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.commuterfamily.Adapters.NotificationAdapter;
import com.example.commuterfamily.Classes.Nification;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Notification extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  LinearLayoutManager layoutManager;
    private DatabaseReference notificatioRef;
    private NotificationAdapter notificationAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recyclerView=(RecyclerView)findViewById(R.id.notifiList);

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        notificatioRef = FirebaseDatabase.getInstance().getReference().child("Notification").child("03408377547");

        notificatioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(Notification.this, String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                ArrayList<Nification> noti=new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Nification order = snapshot.getValue(Nification.class);
                    noti.add(order);
                }

                notificationAdapter = new NotificationAdapter(noti, Notification.this);
                recyclerView.setAdapter(notificationAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

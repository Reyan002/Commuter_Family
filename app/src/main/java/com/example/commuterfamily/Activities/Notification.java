package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.commuterfamily.Adapters.NotificationAdapter;
import com.example.commuterfamily.Classes.Nification;
import com.example.commuterfamily.Classes.NotificationDisplay;
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
    private DatabaseReference notificatioRef,refDisplay;
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recyclerView= findViewById(R.id.notifiList);

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Notification");

        }
        notificatioRef = FirebaseDatabase.getInstance().getReference().child("Notification").child("03408377547");

        notificatioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Toast.makeText(Notification.this, String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                final ArrayList<NotificationDisplay> noti=new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
 //                    Toast.makeText(Notification.this, snapshot.getValue(Nification.class).toString() , Toast.LENGTH_SHORT).show();
                    final Nification order = snapshot.getValue(Nification.class);
                    refDisplay = FirebaseDatabase.getInstance().getReference().child("Users").child(order.getFrom());


                    refDisplay.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            if(!dataSnapshot.exists()){
                                Toast.makeText(Notification.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(Notification.this, dataSnapshot.getValue(NotificationDisplay.class).getPhone() , Toast.LENGTH_SHORT).show();

                dataSnapshot.getValue(NotificationDisplay.class);

                            }



                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });






                }
//                Toast.makeText(Notification.this, noti.size(), Toast.LENGTH_SHORT).show();
                notificationAdapter = new NotificationAdapter(noti, Notification.this);

                recyclerView.setAdapter(notificationAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

package com.example.commuterfamily.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.commuterfamily.Adapters.NotificationAdapter;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.Nification;
import com.example.commuterfamily.Classes.Noti;
import com.example.commuterfamily.Classes.NotificationDisplay;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.Classes.User;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.commuterfamily.Classes.DemoClass.user;

public class Notification extends AppCompatActivity {

    private RecyclerView recyclerView;
    private  LinearLayoutManager layoutManager;
    private DatabaseReference notificatioRef,refDisplay;
    private NotificationAdapter notificationAdapter;
     private String date,time;
     private Noti notif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        notif=new Noti();
         recyclerView= findViewById(R.id.notifiList);

        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);

        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Notification");

        }
        notificatioRef = FirebaseDatabase.getInstance().getReference().child("Notification").child(Prevalent.currentOnlineUser.getPhone());

        notificatioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Toast.makeText(Notification.this, String.valueOf(dataSnapshot.getChildrenCount()), Toast.LENGTH_SHORT).show();
                final ArrayList<User> noti=new ArrayList<>();
                final ArrayList<String> date=new ArrayList<>();
                final ArrayList<String> time=new ArrayList<>();




                for ( DataSnapshot snapshot : dataSnapshot.getChildren()) {
 //                    Toast.makeText(Notification.this, snapshot.getValue(Nification.class).toString() , Toast.LENGTH_SHORT).show();
                    final Nification order = snapshot.getValue(Nification.class);
                     date.add(order.getDate());
                   time.add(order.getTime());
                    refDisplay = FirebaseDatabase.getInstance().getReference().child("Users").child(order.getFrom()) ;
                    refDisplay.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                           user= dataSnapshot.getValue(User.class) ;



                            Toast.makeText(Notification.this, user.getName(), Toast.LENGTH_SHORT).show();
//

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(Notification.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    });



                    noti.add(user);

   }
//             Toast.makeText(Notification.this, noti.size(), Toast.LENGTH_SHORT).show();
                notificationAdapter = new NotificationAdapter( noti, Notification.this, date, time);

                recyclerView.setAdapter(notificationAdapter);
                notificationAdapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

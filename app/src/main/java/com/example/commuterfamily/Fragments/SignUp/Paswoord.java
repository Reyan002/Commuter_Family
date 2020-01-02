package com.example.commuterfamily.Fragments.SignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.commuterfamily.Activities.LoginActivity;
import com.example.commuterfamily.Activities.MainActivity;
import com.example.commuterfamily.Activities.SplashScreenActivity;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.User;
import com.example.commuterfamily.Prevalent.Prevalent;
import com.example.commuterfamily.R;
import com.example.commuterfamily.SessionManager.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class Paswoord extends Fragment {

    private FloatingActionButton btn_next;
    private EditText numberr;
    private ImageView btn_back;
    String parentDB="Users";
    private ProgressDialog loadingBar;
    private SessionManager sessionManager;
    public Paswoord() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.password_fragment, container, false);
        btn_next = view.findViewById(R.id.next_to_login);
        btn_back = view.findViewById(R.id.btn_backLoginPass_2);
        loadingBar=new ProgressDialog(getContext());
        numberr=view.findViewById(R.id.et_password_login);
        sessionManager=new SessionManager(getContext());
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = numberr.getText().toString().trim();


                if(pass.isEmpty() ){
                    numberr.setError("Enter Password");
                    numberr.requestFocus();
                    return;
                }
                else{
                    loadingBar.setTitle("Login Account");
                    loadingBar.setMessage("Please wait ...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();

                    AllowAccessToAccount(DemoClass.number,pass);
                }
//                startActivity(new Intent(getContext(), MainActivity.class));
//






            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SplashScreenActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_out, R.anim.slide_in);
            }
        });
        return view;
    }

    private void AllowAccessToAccount(final String number, final String pass) {

        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(parentDB).child(number).exists()){

                    final User userData=dataSnapshot.child(parentDB).child(number).getValue(User.class);

                    if(userData.getPhone() .equals(number)){
                        if(userData.getPassword().equals(pass)){

                            String DeviceToken= FirebaseInstanceId.getInstance().getToken();
                            RootRef.child(parentDB).child(number).child("DeviceToken").setValue(DeviceToken).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    Toast.makeText(getContext(),"Logged In Successfully....",Toast.LENGTH_SHORT).show();
                                    Prevalent.currentOnlineUser=userData;
                                    Prevalent.UserPhoneKey=userData.getPhone();
                                    sessionManager.createLoginSession(number,pass);
                                    loadingBar.dismiss();
                                    startActivity(new Intent(getContext(),MainActivity.class));
                                }
                            });


                        }
                        else{
                            loadingBar.dismiss();
                            Toast.makeText(getContext(),"Incorrect Password",Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                else{
                    Toast.makeText(getContext(),"Account with "+number+" Number not exists",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(),databaseError.getCode(),Toast.LENGTH_SHORT).show();

            }
        });

    }

}

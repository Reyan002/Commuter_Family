package com.example.commuterfamily.Fragments.SignUp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commuterfamily.Activities.MainActivity;
import com.example.commuterfamily.Activities.SignUpActivity;
import com.example.commuterfamily.Activities.SplashScreenActivity;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.Classes.User;
import com.example.commuterfamily.R;
import com.example.commuterfamily.SessionManager.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoFragment extends Fragment {



    private EditText name,email,password,conform_password,cnic ;
    private TextView gender,gender_text;
    private ProgressDialog loadingBar;
    private FloatingActionButton btn_next;
    private ImageView btn_back;
    private Button verify;
    private SessionManager sessionManager;
    private FirebaseAuth firebaseAuth;

    public InfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        loadingBar=new ProgressDialog(getContext());

         verify=view.findViewById(R.id.verifyemail);


         firebaseAuth=FirebaseAuth.getInstance();
        sessionManager=new SessionManager(getContext());

        name=view.findViewById(R.id.et_username);
        email=view.findViewById(R.id.et_email);
        password=view.findViewById(R.id.et_pass);
        conform_password=view.findViewById(R.id.et_confirm_pass);
        cnic=view.findViewById(R.id.et_cnic);

        gender=view.findViewById(R.id.tv_gender_dettail);
         gender_text=view.findViewById(R.id.tv_gender);

         btn_next = view.findViewById(R.id.btn_next_2);
        btn_next.setEnabled(false);
        btn_back = view.findViewById(R.id.btn_back_2);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(task.isSuccessful()){

                                    firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getContext(), "Please go to the gmail account to verify email", Toast.LENGTH_SHORT).show();

                                                btn_next.setEnabled(true);
                                            }
                                            else{
                                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }
                                else{
                                    Toast.makeText(getContext(), task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Userdetails();
             }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new NumberFragment(), null)
                        .commit();
            }
        });
        cnic.addTextChangedListener( new TextWatcher() {
            boolean isEdiging;
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }


            @Override
            public void afterTextChanged(Editable s) {
                if(isEdiging) return;
                isEdiging = true;
                // removing old dashes
                StringBuilder sb = new StringBuilder();
                sb.append(s.toString().replace("-", ""));

                if (sb.length()> 5)
                    sb.insert(5, "-");
                if (sb.length()> 13)
                    sb.insert(13, "-");
                if(sb.length()> 15)
                    sb.delete(15, sb.length());

                s.replace(0, s.length(), sb.toString());
                isEdiging = false;
            }
        });
        return view;
    }
    private void ValidatePhoneNumber(final String name , final String phone ,final String email , final String pass, final String gender, final String cnic) {
        final DatabaseReference RootRef;
        RootRef= FirebaseDatabase.getInstance().getReference();
        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!(dataSnapshot.child("Users").child(phone).exists())){
                    HashMap<String,Object> userDataMap =new HashMap<>();
                    userDataMap.put("Name",name);
                    userDataMap.put("Phone",phone);
                    userDataMap.put("Email",email);
                    userDataMap.put("Password",pass);
                    userDataMap.put("CNIC",cnic);
                    userDataMap.put("Gender",gender);

                    RootRef.child("Users").child(phone).updateChildren(userDataMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){

                                        Toast.makeText(getContext(),"Congratulations! your Account has Created succesfully",Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                        sessionManager.createLoginSession(phone,pass);
                                        startActivity(new Intent(getContext(), MainActivity.class));
                                    }
                                    else {
                                        loadingBar.dismiss();
                                        Toast.makeText(getContext(),"Network Error: Please try again after some time...",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(getContext(),"Already exist.... ",Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Toast.makeText(getContext(),"Try again with another Phone Number.... ",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), SplashScreenActivity.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Userdetails(){
        String userName=name.getText().toString();
        String userEmail=email.getText().toString();
        String userPass=password.getText().toString();
        String userCPass=conform_password.getText().toString();
        String usergender=gender.getText().toString();
        String userCnic=cnic.getText().toString();

        if(userCnic.length()<15){
            Toast.makeText(getContext(), "Please Fill Complete CNIC", Toast.LENGTH_SHORT).show();
        }

        else if(TextUtils.isEmpty(userName)||TextUtils.isEmpty(userEmail)||TextUtils.isEmpty(userPass)||TextUtils.isEmpty(userCPass)||TextUtils.isEmpty(userCnic) ){
            Toast.makeText(getContext(), "Pleasse provide All the credendials", Toast.LENGTH_SHORT).show();

        }
        else if(!firebaseAuth.getCurrentUser().isEmailVerified()){
            Toast.makeText(getContext(), "Kindly click to the verify email and go to the GMail to verify your email ", Toast.LENGTH_LONG).show();

        }
         else if (!userPass.equals(userCPass)){
            Toast.makeText(getContext(), "Password Not Match ", Toast.LENGTH_SHORT).show();
        }
        else if(userCnic.charAt(14)=='1'|| userCnic.charAt(14)=='3'||userCnic.charAt(14)=='5'||userCnic.charAt(14)=='7'||userCnic.charAt(14)=='9' )
         {
             gender_text.setVisibility(View.VISIBLE);
             gender.setText("Male");
             gender.setVisibility(View.VISIBLE);
             loadingBar.setTitle("Create Account");
             loadingBar.setMessage("Please wait while we are checking Credentials");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();
             ValidatePhoneNumber(userName, DemoClass.number,userEmail,userPass,gender.getText().toString(),userCnic);
         }
         else  if(userCnic.charAt(14)=='0'|| userCnic.charAt(14)=='2'||userCnic.charAt(14)=='4'||userCnic.charAt(14)=='6'||userCnic.charAt(14)=='8' )
         {
             gender_text.setVisibility(View.VISIBLE);

             gender.setText("Female");
             gender.setVisibility(View.VISIBLE);
             loadingBar.setTitle("Create Account");
             loadingBar.setMessage("Please wait while we are checking Credentials");
             loadingBar.setCanceledOnTouchOutside(false);
             loadingBar.show();
             ValidatePhoneNumber(userName, DemoClass.number,userEmail,userPass,gender.getText().toString(),userCnic);
         }
        else{
            Toast.makeText(getContext(), "you are doing something wrong", Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void onResume() {
        super.onResume();

        firebaseAuth.getInstance().getCurrentUser().reload();
    }
    @Override
    public void onStart() {
        super.onStart();

        firebaseAuth.getInstance().getCurrentUser().reload();
    }
     
}

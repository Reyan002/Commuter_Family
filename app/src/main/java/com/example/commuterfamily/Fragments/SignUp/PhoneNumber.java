package com.example.commuterfamily.Fragments.SignUp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.commuterfamily.Activities.LoginActivity;
import com.example.commuterfamily.Activities.SplashScreenActivity;
import com.example.commuterfamily.Classes.DemoClass;
import com.example.commuterfamily.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PhoneNumber extends Fragment {

    private FloatingActionButton btn_next;
    private EditText numberr;
    private ImageView btn_back;

    public PhoneNumber() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.phone_number, container, false);
        btn_next = view.findViewById(R.id.btn_nextLogin_2);
        btn_back = view.findViewById(R.id.btn_backLogin_2);


        numberr=view.findViewById(R.id.et_number_login);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mobile = numberr.getText().toString().trim();


                if(mobile.isEmpty() || mobile.length() < 10){
                    numberr.setError("Enter a valid mobile");
                    numberr.requestFocus();
                    return;
                }
                LoginActivity.fragmentManagerLogin.beginTransaction()
                        .replace(R.id.fragment_container_login, new Code_login(), null)
                        .commit();


                DemoClass.number=mobile;



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
}

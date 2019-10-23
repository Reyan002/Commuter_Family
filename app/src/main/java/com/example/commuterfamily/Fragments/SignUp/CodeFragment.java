package com.example.commuterfamily.Fragments.SignUp;


import android.icu.text.IDNA;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.commuterfamily.Activities.SignUpActivity;
import com.example.commuterfamily.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class CodeFragment extends Fragment {

    private FloatingActionButton btn_next;
    private ImageView btn_back;

    public CodeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_code, container, false);

        btn_next = view.findViewById(R.id.btn_next_3);
        btn_back = view.findViewById(R.id.btn_back_3);

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpActivity.fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new InfoFragment(), null)
                        .commit();
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

        return view;
    }

}

package com.commutersfamily.commuterfamily.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.commutersfamily.commuterfamily.Adapters.MatchesItemAdapter;
import com.commutersfamily.commuterfamily.R;

public class MatchesActivity extends AppCompatActivity {

    RelativeLayout relativeLayoutP;
    RecyclerView recyclerView = new RecyclerView(this);
    MatchesItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        relativeLayoutP = findViewById(R.id.relativeLayout);
        adapter = new MatchesItemAdapter();

        RecyclerView.LayoutParams params;
        params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        recyclerView.setLayoutParams(params);
        recyclerView.setPadding(10,10,10,10);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);




    }
}

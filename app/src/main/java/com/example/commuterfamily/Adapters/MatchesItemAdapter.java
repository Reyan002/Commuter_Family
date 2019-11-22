package com.example.commuterfamily.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commuterfamily.Classes.RecyclerBean;
import com.example.commuterfamily.Classes.Routes;
import com.example.commuterfamily.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MatchesItemAdapter extends RecyclerView.Adapter<MatchesItemAdapter.MyViewHolder> {

    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    ArrayList<RecyclerBean> arrayList;
    Context context;

    public MatchesItemAdapter(ArrayList<RecyclerBean> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public MatchesItemAdapter() {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_item_adapter,parent);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RecyclerBean bean = arrayList.get(position);
        LinearLayoutManager manager = new LinearLayoutManager(
                holder.recyclerView.getContext(),
                LinearLayoutManager.VERTICAL, false);
        manager.setInitialPrefetchItemCount(bean.getArrayList().size());

        MatchesSubItemAdapter adapter = new MatchesSubItemAdapter(bean.getArrayList(),context);

        holder.recyclerView.setLayoutManager(manager);
        holder.recyclerView.setAdapter(adapter);
        holder.recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.recyclerSubItems);
        }
    }
}

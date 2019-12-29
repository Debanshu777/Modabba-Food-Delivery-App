package com.example.modabba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ActiveSubcriptionAdapter extends RecyclerView.Adapter<ActiveSubcriptionAdapter.RecycleViewHolder>{

    Context context;
    List<ActiveSubcription> subcriptionList;

    public ActiveSubcriptionAdapter(Context context, List<ActiveSubcription> subcriptionList) {
        this.context = context;
        this.subcriptionList = subcriptionList;
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(context).inflate(R.layout.subscription_single_item,parent,false);
        RecycleViewHolder recycleViewHolder=new RecycleViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder holder, int position) {
        ActiveSubcription activeSubcription=subcriptionList.get(position);
        holder.subid.setText(activeSubcription.getSubcriptionid());
        holder.plan_name.setText(activeSubcription.getPlan_name());
        holder.start_date.setText(activeSubcription.getStart_date());
        holder.end_date.setText(activeSubcription.getEnd_date());


    }

    @Override
    public int getItemCount() {
        return subcriptionList.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder
    {
        TextView subid,plan_name,start_date,end_date;
        Button pause_btn,skip_btn;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            subid=itemView.findViewById(R.id.subid);
            plan_name=itemView.findViewById(R.id.plan_name);
            start_date=itemView.findViewById(R.id.start);
            end_date=itemView.findViewById(R.id.end);
            pause_btn=itemView.findViewById(R.id.pause);
            skip_btn=itemView.findViewById(R.id.skip);
        }
    }
}

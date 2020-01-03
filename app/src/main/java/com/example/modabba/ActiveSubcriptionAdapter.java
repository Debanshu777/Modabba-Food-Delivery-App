package com.example.modabba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

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
    public void onBindViewHolder(@NonNull final RecycleViewHolder holder, int position) {
        ActiveSubcription activeSubcription=subcriptionList.get(position);
        holder.subid.setText(activeSubcription.getSubcriptionid());
        holder.plan_name.setText(activeSubcription.getPlan_name());
        holder.start_date.setText(activeSubcription.getStart_date());
        holder.end_date.setText(activeSubcription.getEnd_date());
        //counter for skip meal
        holder.skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btn1.setVisibility(View.VISIBLE);
                holder.skip_btn.setVisibility(View.GONE);
                holder.btn1.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String skipnum = holder.btn1.getNumber();
                    }
                });
            }
        });
        //counter for pause meal
        holder.pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btn2.setVisibility(View.VISIBLE);
                holder.pause_btn.setVisibility(View.GONE);
                holder.btn2.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String pausenum = holder.btn2.getNumber();
                    }
                });
            }
        });




    }

    @Override
    public int getItemCount() {
        return subcriptionList.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder
    {
        TextView subid,plan_name,start_date,end_date;
        Button pause_btn,skip_btn;
        ElegantNumberButton btn1,btn2;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            subid=itemView.findViewById(R.id.subid);
            plan_name=itemView.findViewById(R.id.plan_name);
            start_date=itemView.findViewById(R.id.start);
            end_date=itemView.findViewById(R.id.end);
            pause_btn=itemView.findViewById(R.id.pause);
            skip_btn=itemView.findViewById(R.id.skip);
            btn1=itemView.findViewById(R.id.number_btn1);
            btn2=itemView.findViewById(R.id.number_btn2);
        }
    }
}

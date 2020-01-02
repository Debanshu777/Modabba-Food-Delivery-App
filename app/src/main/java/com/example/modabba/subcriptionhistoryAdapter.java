package com.example.modabba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class subcriptionhistoryAdapter extends RecyclerView.Adapter<subcriptionhistoryAdapter.SubcriptionHistoryViewHolder> {

    Context context;
    List<subcriptionhistory> subcriptionhistories;

    public subcriptionhistoryAdapter(Context context, List<subcriptionhistory> subcriptionhistories) {
        this.context = context;
        this.subcriptionhistories = subcriptionhistories;
    }

    @NonNull
    @Override
    public SubcriptionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subcriptionhistory_single_item,parent,false);
        SubcriptionHistoryViewHolder subcriptionHistoryViewHolder=new SubcriptionHistoryViewHolder(view);
        return  subcriptionHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubcriptionHistoryViewHolder holder, int position) {

        subcriptionhistory subcriptionhistorydata=subcriptionhistories.get(position);
        holder.subcriptionid.setText(subcriptionhistorydata.getSubcriptionid());
        holder.plan.setText(subcriptionhistorydata.getPlan());
        holder.time.setText(subcriptionhistorydata.getTime());
        holder.addorminus.setText(subcriptionhistorydata.getAddorMinus());
        holder.amount.setText(subcriptionhistorydata.getAmount());

    }

    @Override
    public int getItemCount() {
        return subcriptionhistories.size();
    }

    public class SubcriptionHistoryViewHolder extends RecyclerView.ViewHolder
    {
        TextView subcriptionid,plan,time,addorminus,amount;

        public SubcriptionHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            subcriptionid=itemView.findViewById(R.id.subid);
            plan=itemView.findViewById(R.id.plan_name);
            time=itemView.findViewById(R.id.start);
            addorminus=itemView.findViewById(R.id.op);
            amount=itemView.findViewById(R.id.amt);
        }
    }
}

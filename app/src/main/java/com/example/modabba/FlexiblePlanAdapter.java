package com.example.modabba;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlexiblePlanAdapter extends RecyclerView.Adapter<FlexiblePlanAdapter.MyViewHolder> {
    List<FexiblePlanData> flexibleDataList;
    public FlexiblePlanAdapter(List<FexiblePlanData> flexibleDataList) {
        this.flexibleDataList = flexibleDataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_flexibleplan,parent,false);
        MyViewHolder recycleViewHolder=new MyViewHolder(view);
        return recycleViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FexiblePlanData data=flexibleDataList.get(position);
        holder.name.setText(data.getName());
        holder.descp.setText(data.getDescp());
        holder.image.setImageResource(data.getImgid());
    }

    @Override
    public int getItemCount() {
        return flexibleDataList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,descp;
        ImageView image;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            descp=itemView.findViewById(R.id.descp);
            image=itemView.findViewById(R.id.image);
        }
    }


}

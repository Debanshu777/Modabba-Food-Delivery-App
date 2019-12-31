package com.example.modabba;

import android.content.Context;
import android.graphics.drawable.VectorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modabba.Fragments.DashboardFragment;
import com.github.vipulasri.timelineview.TimelineView;

import java.util.List;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.TimeLineViewHolder> {

    List<OrderSatusModel> orderSatusModelList;
    Context context;

    public OrderStatusAdapter(List<OrderSatusModel> orderSatusModelList, Context context) {
        this.orderSatusModelList = orderSatusModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_timeline,parent,false);

        //View view = View.inflate(parent.getContext(), R.layout.item_timeline, null);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {

        OrderSatusModel model=orderSatusModelList.get(position);
        holder.date.setText(model.getDate());
        holder.msg.setText(model.getDescription());


    }


    @Override
    public int getItemCount() {
        return orderSatusModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position, getItemCount());
    }

    public static class TimeLineViewHolder extends RecyclerView.ViewHolder
    {
        TextView date;
        TextView msg;
        public TimelineView mTimelineView;
        public TimeLineViewHolder(@NonNull View itemView,int viewType) {
            super(itemView);
            mTimelineView = itemView.findViewById(R.id.timeline);
            date=itemView.findViewById(R.id.text_timeline_date);
            msg=itemView.findViewById(R.id.text_timeline_title);
            mTimelineView.initLine(viewType);
        }
    }
}

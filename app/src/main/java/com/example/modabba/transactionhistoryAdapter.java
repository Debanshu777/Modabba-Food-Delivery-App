package com.example.modabba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class transactionhistoryAdapter extends RecyclerView.Adapter<transactionhistoryAdapter.TransactionHistoryViewHolder> {

    Context context;
    List<transactionhistory> transactionhistoryList;

    public transactionhistoryAdapter(Context context, List<transactionhistory> transactionhistoryList) {
        this.context = context;
        this.transactionhistoryList = transactionhistoryList;
    }

    @NonNull
    @Override
    public TransactionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transactionhistory_single_item,parent,false);
        TransactionHistoryViewHolder transactionHistoryViewHolder=new TransactionHistoryViewHolder(view);
        return  transactionHistoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHistoryViewHolder holder, int position) {

        transactionhistory transactionhistorydata=transactionhistoryList.get(position);
        holder.transactionid.setText(transactionhistorydata.getTransactionid());
        holder.name.setText(transactionhistorydata.getName());
        holder.paymentmethod.setText(transactionhistorydata.getPaymentmethod());
        holder.addorminus.setText(transactionhistorydata.getAddorMinus());
        holder.amount.setText(transactionhistorydata.getAmount());
        holder.time.setText(transactionhistorydata.getTime());
    }

    @Override
    public int getItemCount() {
        return transactionhistoryList.size();
    }

    public class TransactionHistoryViewHolder extends RecyclerView.ViewHolder{

        TextView transactionid,name,time,addorminus,amount,paymentmethod;
        public TransactionHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionid=itemView.findViewById(R.id.subid);
            name=itemView.findViewById(R.id.name);
            time=itemView.findViewById(R.id.time);
            addorminus=itemView.findViewById(R.id.op);
            amount=itemView.findViewById(R.id.amt);
            paymentmethod=itemView.findViewById(R.id.paymentm);
        }
    }
}

package com.example.modabba;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modabba.SessionManagement.SessionManagement;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ChoosePlanAdapter extends RecyclerView.Adapter<ChoosePlanAdapter.PlanViewHolder>  {

    List<ChoosePlan> choosePlanList;
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private SessionManagement sessionManagement;
    boolean accessable;
    public ChoosePlanAdapter(List<ChoosePlan> choosePlanList) {
        this.choosePlanList = choosePlanList;
    }

    @NonNull
    @Override
    public PlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_plan_single,parent,false);
        sessionManagement= new SessionManagement(parent.getContext());
        PlanViewHolder planViewHolder=new PlanViewHolder(view);

        return planViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PlanViewHolder holder, int position) {
        ChoosePlan data=choosePlanList.get(position);
        holder.plan_name.setText(data.getPlan_name());
        holder.plan_price.setText(data.getPlan_price());
        db.collection("users").document(sessionManagement.getUserDocumentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        accessable=(boolean)documentSnapshot.get("serviceable");
                    }
                });

        holder.chhosebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(accessable)
                {
                    Intent intent = new Intent(v.getContext(),CheckoutActivity.class);
                    v.getContext().startActivity(intent);
                }
                else
                {
                    Toast.makeText(v.getContext(), "Service Not Available", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    @Override
    public int getItemCount() {
        return choosePlanList.size();
    }

    public static class PlanViewHolder extends RecyclerView.ViewHolder{

        TextView plan_name,plan_price;
        Button chhosebtn;

        public PlanViewHolder(@NonNull View itemView) {
            super(itemView);
            plan_name=itemView.findViewById(R.id.plan_name);
            plan_price=itemView.findViewById(R.id.plan_price);
            chhosebtn=itemView.findViewById(R.id.choosebtn);

        }
    }
}

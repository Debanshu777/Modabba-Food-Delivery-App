package com.example.modabba;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.modabba.SessionManagement.SessionManagement;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActiveSubcriptionAdapter extends RecyclerView.Adapter<ActiveSubcriptionAdapter.RecycleViewHolder>{

    Context context;
    FirebaseFirestore db;
    SessionManagement sessionManagement;
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
        final ActiveSubcription activeSubcription=subcriptionList.get(position);
        init();
        holder.subid.setText(activeSubcription.getSubcriptionid());
        holder.plan_name.setText(activeSubcription.getPlan_name());
        holder.start_date.setText(activeSubcription.getStart_date());
        holder.end_date.setText(activeSubcription.getEnd_date());
        holder.noofdabba.setText(activeSubcription.getDabba());
        final String s=activeSubcription.getDabba();
        final int[] skipnum = new int[1];
        //counter for skip meal
        holder.skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.btn1.setVisibility(View.VISIBLE);


                holder.btn1.setRange(1,Integer.parseInt(s));
                holder.pause_btn.setVisibility(View.GONE);
                holder.btn1.setOnClickListener(new ElegantNumberButton.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        skipnum[0] = Integer.parseInt(holder.btn1.getNumber());
                        if(skipnum[0] <=Integer.parseInt(s)){
                            holder.done1.setVisibility(View.VISIBLE);
                            holder.skip_btn.setVisibility(View.INVISIBLE);
                        }
                        else{
                            Toast.makeText(context, "Please Check!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        holder.done1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DocumentReference documentReference=db.collection("users").document(sessionManagement.getUserDocumentId()).collection("Subscription")
                        .document(activeSubcription.getSubcriptionid());
                documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        //int skip = (int) documentSnapshot.get("skip");
                        //int ext=(int)documentSnapshot.get("extented");
                        if((int) documentSnapshot.get("skip")==0 ) {//extention conditon has to be added
                            Map<String, Object> subcription = new HashMap<>();
                            subcription.put("skip", 1);
                            //subcription.put("extented", ext + 1);
                            documentReference.update(subcription);
                        }
                    }
                });
            }
        });





    }
    private void init() {
        db = FirebaseFirestore.getInstance();
        sessionManagement = new SessionManagement(context);
    }
    @Override
    public int getItemCount() {
        return subcriptionList.size();
    }

    public static class RecycleViewHolder extends RecyclerView.ViewHolder
    {
        TextView subid,plan_name,start_date,end_date,noofdabba;
        Button pause_btn,skip_btn,done1;
        ElegantNumberButton btn1,btn2;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            subid=itemView.findViewById(R.id.subid);
            plan_name=itemView.findViewById(R.id.plan_name);
            start_date=itemView.findViewById(R.id.start);
            end_date=itemView.findViewById(R.id.end);
            pause_btn=itemView.findViewById(R.id.pause);
            skip_btn=itemView.findViewById(R.id.skip);
            done1=itemView.findViewById(R.id.done1);
            noofdabba=itemView.findViewById(R.id.noofdabba);
            btn1=itemView.findViewById(R.id.number_btn2);
            btn2=itemView.findViewById(R.id.number_btn1);
        }
    }
}

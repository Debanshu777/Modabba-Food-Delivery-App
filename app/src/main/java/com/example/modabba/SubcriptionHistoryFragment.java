package com.example.modabba;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modabba.SessionManagement.SessionManagement;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SubcriptionHistoryFragment extends Fragment {

    private Context context;
    RecyclerView recyclerView;
    List<subcriptionhistory> list;
    private FirebaseFirestore db;
    private SessionManagement sessionManagement;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmentsubcriptionhistory,container,false);
        recyclerView=view.findViewById(R.id.subcription_list);
        init();
        final CollectionReference ref = db.collection("users").document(sessionManagement.getUserDocumentId()).collection("Wallet");
        list=new ArrayList<>();
        final String[] amt = {""};
        final String[] id = {""};
        final String[] time = {""};
        ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                    Map<String,Object> sublist=documentSnapshot.getData();
                    id[0]= String.valueOf(sublist.get("subscription id"));
                    if(id[0]!="") {
                        amt[0]=String.valueOf(sublist.get("amount_deducted"));
                        time[0]=String.valueOf(sublist.get("date_Of_transaction"))+" "+String.valueOf(sublist.get("time_Of_transaction"));
                        list.add(new subcriptionhistory(id[0], "p1", time[0], "-", amt[0]));
                        subcriptionhistoryAdapter subcriptionhistoryAdapter = new subcriptionhistoryAdapter(getContext(), list);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(subcriptionhistoryAdapter);
                    }
                }
            }
        });

        return (view);


    }
    private void init() {
        db = FirebaseFirestore.getInstance();
        sessionManagement = new SessionManagement(getContext());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}

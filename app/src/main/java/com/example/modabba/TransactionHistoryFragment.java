package com.example.modabba;

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

public class TransactionHistoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<transactionhistory>  list;
    private FirebaseFirestore db;
    private SessionManagement sessionManagement;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmenttransactionhistory,container,false);
        recyclerView=view.findViewById(R.id.transaction_list);
        init();
        list=new ArrayList<>();
        final CollectionReference ref = db.collection("users").document(sessionManagement.getUserDocumentId()).collection("Wallet");
        list=new ArrayList<>();
        final String[] amt = {""};
        final String[] id = {""};
        final String[] time = {""};
        ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots) {
                    Map<String, Object> sublist = documentSnapshot.getData();
                    id[0]= String.valueOf(sublist.get("wal_transaction_razor"));
                    if(id[0]!="") {
                        amt[0]=String.valueOf(sublist.get("amount_added"));
                        time[0]=String.valueOf(sublist.get("date_Of_transaction"))+" "+String.valueOf(sublist.get("time_Of_transaction"));
                        list.add(new transactionhistory(id[0], "+", "doyel saha",time[0],"card", amt[0]));
                        transactionhistoryAdapter transactionhistoryAdapter = new transactionhistoryAdapter(getActivity(), list);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        recyclerView.setAdapter(transactionhistoryAdapter);
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

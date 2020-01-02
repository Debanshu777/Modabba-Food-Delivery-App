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

import java.util.ArrayList;
import java.util.List;

public class TransactionHistoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<transactionhistory> list;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmenttransactionhistory,container,false);
        recyclerView=view.findViewById(R.id.transaction_list);
        transactionhistoryAdapter transactionhistoryAdapter=new transactionhistoryAdapter(getActivity(),list);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(transactionhistoryAdapter);



        return (view);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list=new ArrayList<>();
        list.add(new transactionhistory("#1234","+","doyel saha","02-01-2020","card","1300"));
    }
}

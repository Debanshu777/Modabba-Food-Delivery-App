package com.example.modabba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SubcriptionHistoryFragment extends Fragment {

    RecyclerView recyclerView;
    List<subcriptionhistory> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragmentsubcriptionhistory,container,false);
        recyclerView=view.findViewById(R.id.subscribtion_list);
        return view;


    }
}

package com.example.modabba.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modabba.ActiveSubcription;
import com.example.modabba.ActiveSubcriptionAdapter;
import com.example.modabba.CheckoutActivity;
import com.example.modabba.R;
import com.example.modabba.SessionManagement.SessionManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class SubscriptionFragment extends Fragment {

    private Context context;
    private FirebaseFirestore db;
    private SessionManagement sessionManagement;
    private static final String TAG = "SUBSCRIPTION";
    private RecyclerView recyclerView;
    List<ActiveSubcription> subcriptionList;

    public SubscriptionFragment() {
    }

    public SubscriptionFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        View view = inflater.inflate(R.layout.fragment_subscription, container, false);
        recyclerView = view.findViewById(R.id.subscribtion_list);
        final CollectionReference ref = db.collection("users").document(sessionManagement.getUserDocumentId()).collection("Subscriptions");
        subcriptionList = new ArrayList<>();
        final String[] days = {""};
        final String[] date_Of_activation={""};
        final String[] no_of_dabba={""};
        ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot documentSnapshot:queryDocumentSnapshots)
                {
                    Map<String,Object>sublist=documentSnapshot.getData();
                    days[0] = String.valueOf(sublist.get("days"));
                    date_Of_activation[0]= String.valueOf(sublist.get("date_Of_activation"));
                    no_of_dabba[0]=String.valueOf(sublist.get("no_of_dabba"));
                    subcriptionList.add(new ActiveSubcription(documentSnapshot.getId(), days[0] + "Day",date_Of_activation[0], "27:01:2020",no_of_dabba[0]));
                    ActiveSubcriptionAdapter subcriptionAdapter = new ActiveSubcriptionAdapter(getContext(), subcriptionList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(subcriptionAdapter);
                }
            }
        });

        return view;

    }

    private void init() {
        db = FirebaseFirestore.getInstance();
        sessionManagement = new SessionManagement(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}

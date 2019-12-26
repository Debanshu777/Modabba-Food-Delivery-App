package com.example.modabba.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionFragment extends Fragment {

    private Context context;
    private FirebaseFirestore db;
    private SessionManagement sessionManagement;
    private TextView subscriptionCredit;
    private int selectedPlan;
    private int _foodCategory = 0; // Veg/NonVeg
    private int _mealCategory = 0; // Lunch/Dinner
    private ChipGroup group;
    private RadioGroup categoryGroup,mealCategory;
    private Button subscribe;

    private RecyclerView recyclerView;
    List<ActiveSubcription> subcriptionList;

    public SubscriptionFragment(){}
    public SubscriptionFragment(Context context){
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    //    init();
        View view = inflater.inflate(R.layout.fragment_subscription,container,false);
        recyclerView=view.findViewById(R.id.subscribtion_list);
        ActiveSubcriptionAdapter subcriptionAdapter=new ActiveSubcriptionAdapter(getContext(),subcriptionList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(subcriptionAdapter);
        return view;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subcriptionList=new ArrayList<>();
        subcriptionList.add(new ActiveSubcription("#223434","7 Day Plan","11:12:2019","18:12:2019"));
        subcriptionList.add(new ActiveSubcription("#222367","14 Day Plan","11:12:2019","25:12:2019"));
        subcriptionList.add(new ActiveSubcription("#224289","30 Day Plan","11:12:2019","11:01:2020"));
        subcriptionList.add(new ActiveSubcription("#223434","7 Day Plan","11:12:2019","18:12:2019"));
        subcriptionList.add(new ActiveSubcription("#223434","7 Day Plan","11:12:2019","18:12:2019"));

    }

}

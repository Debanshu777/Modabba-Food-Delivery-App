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
   // public SubscriptionFragment(Context context){
    //    this.context = context;
  //  }
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

/*    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        subcriptionList=new ArrayList<>();
        subcriptionList.add(new ActiveSubcription("#223434","7 Day Plan","11:12:2019","18:12:2019"));
        subcriptionList.add(new ActiveSubcription("#222367","14 Day Plan","11:12:2019","25:12:2019"));
        subcriptionList.add(new ActiveSubcription("#224289","30 Day Plan","11:12:2019","11:01:2020"));
        subcriptionList.add(new ActiveSubcription("#223434","7 Day Plan","11:12:2019","18:12:2019"));

        //      initView(view);
        //setCredits();
    }
*/
    /*private void setCredits() {
        db.collection("user").document(sessionManagement.getUserDocumentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        long credit  = (long)documentSnapshot.get("wallet");

                        subscriptionCredit.setText(String.valueOf(credit));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void init() {
        db = FirebaseFirestore.getInstance();
        sessionManagement = new SessionManagement(context);
    }
    private void initView(final View view) {

        subscriptionCredit = view.findViewById(R.id.subscription_credit);
        subscribe  = view.findViewById(R.id.subscribe_button);

        mealCategory = view.findViewById(R.id.mealGroup);
        mealCategory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.lunch_radioButton : _mealCategory = 0;
                    break;
                    case R.id.dinner_radioButton : _mealCategory = 1;
                    break;
                    case R.id.both_radioButton : _mealCategory = 2;
                    break;
                }
            }
        });

        categoryGroup = view.findViewById(R.id.food_group);
        categoryGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.veg : _foodCategory = 0;
                    break;
                    case R.id.non_veg :
                        _foodCategory = 1;
                    break;
                }
            }
        });

        group = view.findViewById(R.id.plan_group);
        group.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup chipGroup, int i) {
                switch (i) {
                    case R.id.p1:
                        selectedPlan = 7;
                        break;
                    case R.id.p2:
                        selectedPlan = 14;
                        break;
                    case R.id.p3:
                        selectedPlan = 30;
                        break;
                }
            }
        });

        subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //TODO:Map the data to Firebase
                Intent intent = new Intent(context,CheckoutActivity.class);
                intent.putExtra("mealPlan",_mealCategory);
                intent.putExtra("foodPlan",_foodCategory);
                intent.putExtra("plan",selectedPlan);
                startActivity(intent);
            }
        });


    }
*/
}

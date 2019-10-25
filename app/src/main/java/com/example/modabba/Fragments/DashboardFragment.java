package com.example.modabba.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.modabba.R;
import com.example.modabba.SessionManagement.SessionManagement;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Iterator;
import java.util.Map;

public class DashboardFragment extends Fragment {

    private Context context;
    private FirebaseFirestore db;
    private SessionManagement sessionManagement;
    private TextView dashBoardCredit;
    private String[] descriptionData = {"Preparing", "On Way", "Delivered"};
    private StateProgressBar stateProgressBar;
    private TextView dashboardUsername,dashboardLunch,dashBoardDinner;

    public DashboardFragment(){}
    public DashboardFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            init();

            View view = inflater.inflate(R.layout.fragment_dashboard    ,container,false);
            return view;
    }

    private void init() {
        db = FirebaseFirestore.getInstance();
        sessionManagement = new SessionManagement(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            initView(view);

            setCredits();

        setTodaysMenu();


    }

    private void setCredits() {
        db.collection("user").document(sessionManagement.getUserDocumentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        long credit  = (long)documentSnapshot.get("wallet");

                        dashBoardCredit.setText(String.valueOf(credit));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initView(View view) {

        dashBoardCredit = view.findViewById(R.id.dashboard_credits);
        stateProgressBar  = view.findViewById(R.id.progress_bar);
        stateProgressBar.setStateDescriptionData(descriptionData);
        dashboardUsername = view.findViewById(R.id.dashboard_username);

        dashboardUsername.setText(sessionManagement.getUserName());

        dashboardLunch = view.findViewById(R.id.dashboard_lunch);
        dashBoardDinner = view.findViewById(R.id.dashboard_dinner);

    }

    private void setTodaysMenu(){

        DocumentReference lunchRef = db.collection("menu").document("lunch");
        DocumentReference dinnerRef = db.collection("menu").document("dinner");

        lunchRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        StringBuilder builder = new StringBuilder();

                        Map<String,String> data = (Map<String, String>) documentSnapshot.get("lunch");

                        Iterator<String> itr  = data.keySet().iterator();

                        while (itr.hasNext()){

                            String key = itr.next();
                            String value = data.get(key);

                            String cap  = key.substring(0, 1).toUpperCase() + key.substring(1);

                            builder.append(" "+value);
                            builder.append(cap+" ");

                            if((itr.hasNext()))
                                builder.append("+");

                        }
                        dashboardLunch.setText(builder);
                        System.out.println(builder);
                    }
                });

        dinnerRef.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        StringBuilder builder = new StringBuilder();

                        Map<String,String> data = (Map<String, String>) documentSnapshot.get("dinner");

                        Iterator<String> itr  = data.keySet().iterator();

                        while (itr.hasNext()){

                            String key = itr.next();
                            String value = data.get(key);

                            String cap  = key.substring(0, 1).toUpperCase() + key.substring(1);

                            builder.append(" "+value);
                            builder.append(cap+" ");

                            if((itr.hasNext()))
                                builder.append("+");

                        }
                        dashBoardDinner.setText(builder);
                        System.out.println(builder);
                    }
                });
    }
}

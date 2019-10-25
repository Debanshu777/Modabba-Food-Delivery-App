package com.example.modabba.ActiveSubActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.modabba.R;
import com.example.modabba.SessionManagement.SessionManagement;
import com.example.modabba.Utils.SubscriptionModal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ActiveSubscriptionActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    private SessionManagement sessionManagement;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private List<SubscriptionModal> list;
    private Toolbar toolbar;

    @Override
    protected void onResume() {
        super.onResume();

        getUserSubscription();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_subscription);

        toolbar = findViewById(R.id.active_subscription_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        db = FirebaseFirestore.getInstance();
        sessionManagement = new SessionManagement(this);
        progressBar = findViewById(R.id.active_subscription_progressBar);

    }

    private void initRecycler() {

        progressBar.setVisibility(View.GONE);

        recyclerView = findViewById(R.id.active_subscription__recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        RecyclerAdapter recyclerAdapter = new RecyclerAdapter(this,list);
        recyclerView.setAdapter(recyclerAdapter);

    }

    private void getUserSubscription() {

        list = new ArrayList<>();

        CollectionReference ref = db.collection("user").document(sessionManagement.getUserDocumentId())
                .collection("subscription");
        ref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                if(queryDocumentSnapshots.isEmpty()){
                    Toast.makeText(getApplicationContext(),"Empty",Toast.LENGTH_SHORT).show();
                }
                else{

                    List<DocumentSnapshot> documents  = queryDocumentSnapshots.getDocuments();

                    for(DocumentSnapshot documentSnapshot : documents){

                        list.add(new SubscriptionModal(
                                (String)documentSnapshot.get("activationDate"),
                                (String) documentSnapshot.get("expiryDate"),
                                (String)documentSnapshot.get("purchaseDate"),
                                (Long) documentSnapshot.get("foodCategory"),
                                (Long)documentSnapshot.get("planDays"),
                                (Long)documentSnapshot.get("mealCategory"),
                                (Long)documentSnapshot.get("status")
                        ));
                    }
                }
                initRecycler();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();

            }
        });
    }
}

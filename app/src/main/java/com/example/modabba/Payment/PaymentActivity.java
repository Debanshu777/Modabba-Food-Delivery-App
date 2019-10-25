package com.example.modabba.Payment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modabba.R;
import com.example.modabba.SessionManagement.SessionManagement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DecimalFormat;

public class PaymentActivity extends AppCompatActivity {

    private TextView number,balance;
    private SessionManagement sessionManagement;
    private FirebaseFirestore db;
    private FloatingActionButton add_money;
    private DecimalFormat decimalFormat;
    private String TAG = PaymentActivity.class.getName();
    private ImageView backArrow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        init();


        listenToDocument();


        add_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(PaymentActivity.this,AddMoney.class);
                startActivity(i);
            }
        });
    }
    private void listenToDocument() {

        String userDocId = sessionManagement.getUserDocumentId();

        DocumentReference docRef = db.collection("user").document(userDocId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.i(TAG,"Listen Failed");
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    if(Long.parseLong(snapshot.get("wallet").toString())>0)
                    balance.setText("₹ " +String.valueOf(decimalFormat.format(Double.parseDouble(snapshot.get("wallet").toString())*100/100.0)));
                    else
                        balance.setText("₹ 0");
                } else {
                   Log.i(TAG,"Field Does not exists");
                }
            }
        });
    }
    private void init(){

        db = FirebaseFirestore.getInstance();
        backArrow = findViewById(R.id.b2);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        decimalFormat = new DecimalFormat("#.00");

        number  = findViewById(R.id.payment_number);
        balance = findViewById(R.id.credit);

        add_money=findViewById(R.id.add_money);

        sessionManagement  = new SessionManagement(this);

        number.setText(sessionManagement.getUserNumber());
    }
}

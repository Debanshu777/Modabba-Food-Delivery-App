package com.example.modabba;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.modabba.SessionManagement.SessionManagement;
import com.example.modabba.Utils.SubscriptionModal;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Transaction;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import io.opencensus.common.ToLongFunction;

public class CheckoutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView selectedDate,deliveryAddress,walletBalance,totalPrice,planPrice,planDays;
    private MaterialButton selectDate,updateAddress,pay;
    private int mealCategory = 0;
    private int foodCategory = 0;
    private int plan ;
    private TextView category_veg,category_nonveg;
    private ImageView checkoutImage;
    private FirebaseFirestore db;
    private SessionManagement sessionManagement;
    private long credits = 0;
    private ProgressDialog progressDialog;

    Calendar calendar = Calendar.getInstance();

    int year = calendar.get(Calendar.YEAR);
    int month  = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        sessionManagement = new SessionManagement(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Doing something");
        progressDialog.setCancelable(false);
        toolbar = findViewById(R.id.checkout_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        initViews();

        mealCategory = getIntent().getIntExtra("mealPlan",0); //Lunch = 0 Dinner = 1, Both = 2
        foodCategory = getIntent().getIntExtra("foodPlan",0); // Veg/NonVeg
        plan  = getIntent().getIntExtra("plan",7);

        setDeliveryWalletBalance();

        planPrice.setText("₹" + getTotalPrice(mealCategory,plan));

        totalPrice.setText(planPrice.getText());

        planDays.setText(String.valueOf(plan) + "Days");

        switch (mealCategory){

            case 0 : checkoutImage.setBackgroundResource(R.drawable.lunch_time);
            break;
            case 1: checkoutImage.setBackgroundResource(R.drawable.dinner_time);
            break;
            case 2: checkoutImage.setBackgroundResource(R.drawable.dinner_time);
            break;

        }
        switch (foodCategory){
            
            case 0: category_veg.setVisibility(View.VISIBLE);
            category_nonveg.setVisibility(View.GONE);
            break;
            case 1: category_veg.setVisibility(View.GONE);
                category_nonveg.setVisibility(View.VISIBLE);
            break;
        }

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDatePicker();
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPayment();
            }
        });

    }

    private void createPayment() {

        // 1. Check sufficient wallet balance



        if(credits >=Long.parseLong(totalPrice.getText().toString().substring(1))){

            progressDialog.show();

            final SubscriptionModal subscriptionModal = new SubscriptionModal(
                    selectedDate.getText().toString(),
                    null,
                    formateDate(new Date(year-1900,month,day)),
                            foodCategory,
                            plan,
                            mealCategory,
                    0
                    );


            final long remaining_balance = credits - Long.parseLong(totalPrice.getText().toString().substring(1));

            // 1. Update Wallet Credits
            final DocumentReference walletRef = db.collection("user").document(sessionManagement.getUserDocumentId());
           // batch.update(walletRef,"wallet",remaining_balance);

            // 2. Create Subscription
            final CollectionReference subRef = db.collection("user").document(sessionManagement.getUserDocumentId()).collection("subscription");


            // 3. Create Transaction
         //   CollectionReference tranRef = db.collection("user").document(sessionManagement.getUserDocumentId()).collection("transaction");

            db.runTransaction(new Transaction.Function<Void>() {
                @Nullable
                @Override
                public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {

                    transaction.update(walletRef,"wallet",remaining_balance);
                    return null;
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void o) {
                    subRef.add(subscriptionModal)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    subscribeToNotification();
                                }
                            });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getApplicationContext(),"Please try after some time",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(getApplicationContext(),"You don't have enough credit balance",Toast.LENGTH_SHORT).show();
        }

    }
    private void subscribeToNotification(){

        FirebaseMessaging.getInstance().subscribeToTopic("subscription");
    }
    private void setDeliveryWalletBalance() {

        db = FirebaseFirestore.getInstance();
        db.collection("user")
            .document(sessionManagement.getUserDocumentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Map<String, Map<String,Object>> address = (Map<String, Map<String, Object>>) documentSnapshot.get("address");
                        credits  = (long)documentSnapshot.get("wallet");
                        walletBalance.setText(String.valueOf(credits));

                        for(Map.Entry<String,Map<String,Object>> data : address.entrySet()){

                            String address_type = data.getKey(); // address type for each of the address

                            Map<String,Object> address_type_data  = data.getValue();

                            String c = (String) address_type_data.get("completeAddress");

                            deliveryAddress.setText(c);
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),"Please try after some time",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void initViews() {
        selectedDate = findViewById(R.id.selected_Date);
        selectedDate.setText(formateDate(new Date(year-1900,month,day+1)));
        deliveryAddress = findViewById(R.id.checkout_address);
        selectDate = findViewById(R.id.select_Date);
        pay  = findViewById(R.id.checkout_pay);
        walletBalance = findViewById(R.id.checkout_wallet_balance);
        totalPrice = findViewById(R.id.checkout_total_price);
        planPrice = findViewById(R.id.checkout_price);
        planDays = findViewById(R.id.days);
        checkoutImage = findViewById(R.id.checkout_image);
        category_nonveg = findViewById(R.id.checkout_nonveg);
        category_veg = findViewById(R.id.checkout_veg);

    }

    private void openDatePicker(){


        DatePickerDialog datePickerDialog = new DatePickerDialog(CheckoutActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Date date = new Date(year-1900,month,day);
                        selectedDate.setText(formateDate(date));
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private String formateDate(Date date){

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM, yyyy");
        return dateFormat.format(date);
    }
    private String getTotalPrice(int mealCategory,int plan){

        int total = 0;

        switch (mealCategory){
            case 0:
            case 1:
                total = 30*plan;
            break;
            case 2:total = 60*plan;
            break;
        }
        String s = String.valueOf(total);
        return s;
    }
}

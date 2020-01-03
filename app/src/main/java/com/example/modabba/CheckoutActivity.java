package com.example.modabba;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.modabba.SessionManagement.SessionManagement;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class CheckoutActivity extends AppCompatActivity {

    private TextView selectedDate,deliveryAddress,walletBalance,totalPrice,planPrice,planDays;
    private MaterialButton selectDate,pay;
    private TextView category_veg,category_nonveg;
    private ImageView checkoutImage;
    private FirebaseFirestore db;
    private SessionManagement sessionManagement;
    private long credits = 0;
    private ProgressDialog progressDialog;
    Calendar calendar = Calendar.getInstance();
    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
    int year = calendar.get(Calendar.YEAR);
    int month  = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    int no_days,num=1;

    public ElegantNumberButton btn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        sessionManagement = new SessionManagement(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Doing something");
        progressDialog.setCancelable(false);

        initViews();
        no_days=Integer.parseInt(getIntent().getStringExtra("days"));
        final int per_day=Integer.parseInt(getIntent().getStringExtra("prices"));
        final int meal=getIntent().getIntExtra("meal",0);
        planPrice.setText("₹"+Integer.toString(no_days*per_day));
        planDays.setText(no_days+"Days");
        totalPrice.setText(planPrice.getText());
        setDeliveryWalletBalance();
        category_veg=findViewById(R.id.checkout_veg);
        category_nonveg=findViewById(R.id.checkout_nonveg);
        if(meal== 0)
        {
            category_veg.setVisibility(View.INVISIBLE);
            category_nonveg.setVisibility(View.VISIBLE);
        }


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDatePicker();
            }
        });
        //set the maximum range of dabbas
        btn1.setRange(1, 5);
        btn1.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                num = Integer.parseInt(btn1.getNumber());
                if(num==0)
                {
                    planPrice.setText("₹"+Integer.toString(no_days*per_day));
                    totalPrice.setText(planPrice.getText());
                }
                else {
                    planPrice.setText("₹" + Integer.toString(no_days * per_day * num));
                    totalPrice.setText(planPrice.getText());
                }
            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createPayment(meal,num);
            }
        });
        deliveryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CheckoutActivity.this, MapActivity.class).putExtra("callingActivity",002)
                        .putExtra("Sessionid",sessionManagement.getUserDocumentId()));
            }
        });


    }

    private void createPayment(final int meal, final int num) {

        if(credits >=Long.parseLong(totalPrice.getText().toString().substring(1))){

            progressDialog.show();

            final long remaining_balance = credits - Long.parseLong(totalPrice.getText().toString().substring(1));


            final DocumentReference docRef = db.collection("users").document(sessionManagement.getUserDocumentId());
            docRef.update("wallet",remaining_balance).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    addSubcription(meal,Long.parseLong(totalPrice.getText().toString().substring(1)),num);
                   subscribeToNotification();
                   Toast.makeText(CheckoutActivity.this, "Subcription Added!", Toast.LENGTH_SHORT).show();
                   startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
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
    private void walletTransaction(long deducted,String id)
    {
        String currentDate = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(new Date());
        Map<String,Object> wal=new HashMap<>();
        wal.put("date_Of_transaction",currentDate);
        wal.put("wal_transaction_razor","");
        wal.put("subscription id",id);
        wal.put("time_Of_transaction",currentTime);
        wal.put("amount_deducted",deducted);
        wal.put("amount_added",0);
        CollectionReference coref=db.collection("users").document(sessionManagement.getUserDocumentId()).collection("Wallet");
        coref.add(wal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "wallet updated");
            }
        });
    }
    private void addorder(final String id, final int num)
    {
        List<DocumentSnapshot>documentSnapshots;
        CollectionReference ref=db.collection("users").document(sessionManagement.getUserDocumentId()).collection("Subscriptions");
        ref.document().get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> order = new HashMap<>();
                order.put("date", selectedDate.getText());
                order.put("status", "preparing");
                order.put("subcription_id",id);
                order.put("no_of_dabba",num);
                order.put("time_of_arrival", "14:20:30");
                db.collection("users").document(sessionManagement.getUserDocumentId()).collection("MyOrders")
                        .add(order).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "order placed");
                    }
                });
            }
        });






    }
    private void addSubcription(int meal, final long c, final int num)
    {
        String a="Veg";
        if(meal==0)
        {
            a="Non-Veg";
        }

        Map<String,Object> subcription=new HashMap<>();
        subcription.put("date_Of_activation",selectedDate.getText());
        subcription.put("days",no_days);
        subcription.put("payment_id","");
        subcription.put("plan","p1");
        subcription.put("skip",0);
        subcription.put("extented",0);
        subcription.put("status","active");
        subcription.put("type",a);
        subcription.put("no_of_dabba",num);
        db.collection("users").document(sessionManagement.getUserDocumentId()).collection("Subscriptions")
        .add(subcription).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                String b=documentReference.getId();
                addorder(b,num);
                walletTransaction(c,b);
                Toast.makeText(CheckoutActivity.this, "Subcription Added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CheckoutActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void subscribeToNotification(){

        FirebaseMessaging.getInstance().subscribeToTopic("subscription");
    }
    private void setDeliveryWalletBalance() {

        db = FirebaseFirestore.getInstance();
        db.collection("users")
            .document(sessionManagement.getUserDocumentId())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Map<String, Map<String,Object>> address = (Map<String, Map<String, Object>>) documentSnapshot.get("address");
                        credits  = (long)documentSnapshot.get("wallet");
                        walletBalance.setText(String.valueOf(credits));

                        for(Map.Entry<String,Map<String,Object>> data : address.entrySet()){

                            String address_type = data.getKey();

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
        selectedDate.setText(formateDate(new Date(year-1900,month,day)));
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

        btn1 = findViewById(R.id.number_button2);




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
        //Calendar cal = Calendar.getInstance();
        //cal.setTime(date);
        //cal.add(Calendar.DATE, no_days);
        //Date futureDate = cal.getTime();
        //settext the future date

        return dateFormat.format(date);
    }

}

package com.example.modabba.Payment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.modabba.CheckoutActivity;
import com.example.modabba.NotificationService;
import com.example.modabba.R;
import com.example.modabba.SessionManagement.SessionManagement;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultListener;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddMoney extends AppCompatActivity implements PaymentResultWithDataListener {

    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private EditText et_amount;
    private FloatingActionButton floatingActionButton;
    private static final String TAG = AddMoney.class.getSimpleName();
    private ImageView backArrow;
    private TextView min;
    int amount;
    private RequestQueue requestQueue;
    private  String url1 = "http://192.168.43.21:5000/order";
    private  String url2 = "http://192.168.43.21:5000/verifysign";

    private SessionManagement sessionManagement;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);

        initViews();
        requestQueue = Volley.newRequestQueue(this);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = Integer.parseInt(et_amount.getText().toString().trim());
                if(amount<50){
                    min.setVisibility(View.VISIBLE);
                }else {

                    //amount = amount * 100;
                    startPayment(amount);

                }
            }
        });


    }

    private void startPayment(int amt) {

        Checkout checkout=new Checkout();
        checkout.setImage(R.drawable.app_logo);
        final Activity activity=this;
        try {
            JSONObject option=new JSONObject();
            option.put("description","Add To Modabba Cash");
            option.put("currency","INR");
            option.put("payment_amt",amt);
            checkout.open(activity,option);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initViews() {

        sessionManagement = new SessionManagement(this);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.done);
        backArrow = findViewById(R.id.b1);
        min = findViewById(R.id.minimum_indicator);

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        et_amount = findViewById(R.id.amount);
        et_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @SuppressLint("RestrictedApi")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length()>0){
                    floatingActionButton.setVisibility(View.VISIBLE);
                }
                else{
                    floatingActionButton.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void walletTransaction(long added,String id)
    {
        String currentDate = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Map<String,Object> wal=new HashMap<>();
        wal.put("date_Of_transaction",currentDate);
        wal.put("wal_transaction_razor",id);
        wal.put("subscription id","");
        wal.put("time_Of_transaction",currentTime);
        wal.put("amount_deducted",0);
        wal.put("amount_added",added);
        CollectionReference coref=db.collection("users").document(sessionManagement.getUserDocumentId()).collection("Wallet");
        coref.add(wal).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Log.d(TAG, "wallet updated");
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        String currentDate = new SimpleDateFormat("dd MMM, yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Map<String,Object> transaction=new HashMap<>();
        transaction.put("date_Of_transaction",currentDate);
        transaction.put("time_Of_transaction",currentTime);
        transaction.put("razor_payment_id",paymentData.getPaymentId());
        transaction.put("amount",100);//transaction.put("amount",paymentData.getData().getInt("payment_amt"));
        walletTransaction(100,paymentData.getPaymentId());
        final DocumentReference docref=db.collection("users").document(sessionManagement.getUserDocumentId());
        docref.collection("Transaction").add(transaction).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference){
               docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                   @Override
                   public void onSuccess(DocumentSnapshot documentSnapshot) {
                       long credits  = (long)documentSnapshot.get("wallet");
                       docref.update("wallet",credits+amount).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void aVoid) {
                               NotificationService notificationService = new NotificationService();
                               notificationService.showNotification(getApplicationContext(),"Wallet","Money Has Been Added");
                               Toast.makeText(AddMoney.this, "Added to Modabba Cash", Toast.LENGTH_SHORT).show();
                           }
                       }).addOnFailureListener(new OnFailureListener() {
                           @Override
                           public void onFailure(@NonNull Exception e) {
                               Toast.makeText(AddMoney.this, "something came upg ", Toast.LENGTH_SHORT).show();
                           }
                       });

                   }
               });
               // Toast.makeText(AddMoney.this, "", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddMoney.this, "Try Again", Toast.LENGTH_SHORT).show();
            }
        });
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Toast.makeText(this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
    }
}

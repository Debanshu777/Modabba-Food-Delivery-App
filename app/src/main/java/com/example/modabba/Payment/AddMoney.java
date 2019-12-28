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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.modabba.R;
import com.example.modabba.SessionManagement.SessionManagement;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddMoney extends AppCompatActivity implements PaymentResultWithDataListener {

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

                    amount = amount * 100;
                    String amt = String.valueOf(amount);
                    sendCreateOrderData(amt);

                }
            }
        });


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
    private void send(String amt)
    {
        String userDocId = sessionManagement.getUserDocumentId();

        Map<String, String> params = new HashMap();
        params.put("amount", amt);
        params.put("currency", "INR");
        params.put("receipt", Calendar.getInstance().getTime().toString());
        params.put("payment_capture", "amt");
        params.put("userDocId",userDocId);
    }
    private void sendCreateOrderData(String amt)
    {
        String userDocId = sessionManagement.getUserDocumentId();

        Map<String, String> params = new HashMap();
        params.put("amount", amt);
        params.put("currency", "INR");
        params.put("receipt", Calendar.getInstance().getTime().toString());
        params.put("payment_capture", "amt");
        params.put("userDocId",userDocId);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url1, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                startPayment(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddMoney.this, "Check your internet connection !!    ", Toast.LENGTH_LONG).show();
            }
        });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                0,
                0));

        requestQueue.add(jsonRequest);
    }
    private void startPayment(JSONObject jsonResponse) {

        Log.i(TAG,"at start payment");

        String razorpayOrdeId = null;

        try{
            razorpayOrdeId = jsonResponse.getString("id");
        }
        catch(JSONException e){
            Log.i(TAG,"Exception Caught " + e.getMessage());
        }

        Checkout checkout = new Checkout();
        Activity activity = this;

        try {

            //customer details

            JSONObject options = new JSONObject();
            options.put("name", "Modabba");
            options.put("description", "Add Modabba Cash");
            options.put("order_id", razorpayOrdeId);
            options.put("currency", "INR");

            JSONObject preFill = new JSONObject();
            preFill.put("email", sessionManagement.getUserEmail());
            preFill.put("contact", sessionManagement.getUserNumber().substring(3));

            options.put("prefill", preFill);

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e("Error", e.toString());
        }
    }
    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

        String paymentId = paymentData.getPaymentId();
        String signature = paymentData.getSignature();
        String orderId = paymentData.getOrderId();
        sendDataForSignatureVerification(s,paymentId,signature,orderId,this);
    }

    private void sendDataForSignatureVerification(String razorpayPaymentID, String paymentId, String signature, String orderId, final AddMoney addMoney)
    {
        String userDocId = sessionManagement.getUserDocumentId();


        Map<String, String> params = new HashMap();
        params.put("razorpayPaymentID", razorpayPaymentID);
        params.put("paymentId", paymentId);
        params.put("signature", signature);
        params.put("orderId", orderId);
        params.put("userDocId",userDocId);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url2, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.i(TAG,"ONJSON Response");
                addMoney.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG,"ONJSON Error Response");
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                0,
                0));
        requestQueue.add(jsonRequest);
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

        Log.i(TAG,"ON Payment Error Response");

        Toast.makeText(AddMoney.this, String.valueOf(i)+": "+s, Toast.LENGTH_LONG).show();

    }
}

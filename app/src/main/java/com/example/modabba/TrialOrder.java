package com.example.modabba;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TrialOrder extends AppCompatActivity {

    Button veg_subcribe,nonveg_subcribe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial_order);


        veg_subcribe=findViewById(R.id.veg);
        nonveg_subcribe=findViewById(R.id.nonveg);
        veg_subcribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrialOrder.this,CheckoutActivity.class));
            }
        });

        nonveg_subcribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrialOrder.this,CheckoutActivity.class));
            }
        });
    }
}

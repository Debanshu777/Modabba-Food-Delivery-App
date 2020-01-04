package com.example.modabba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modabba.ActiveSubActivity.ActiveSubscriptionActivity;
import com.example.modabba.Fragments.DashboardFragment;

public class OrderSuccess extends AppCompatActivity{

    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orer_success_layout);
        NotificationService notificationService = new NotificationService();
        notificationService.showNotification(getApplicationContext(),"Subscription","New Subscription has been added");

        text = findViewById(R.id.my_orders);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderSuccess.this, DashboardFragment.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

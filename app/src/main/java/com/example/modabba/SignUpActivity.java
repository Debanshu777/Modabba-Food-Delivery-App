package com.example.modabba;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modabba.Utils.PassingData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText username,useremail,userpass,userconfirm,useraltphone;
    private MaterialButton next;
    private PassingData data;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initView();

        data = getIntent().getParcelableExtra("object");

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().isEmpty()){
                    username.setError("Username cannot be empty");
                }
                else if(userpass.getText().toString().isEmpty() || !(userpass.getText().toString().equals(userconfirm.getText().toString()))){
                    userpass.setError("Password does not match");
                    userconfirm.setError("Password does not match");
                }
                else
                {

                    data.setName(username.getText().toString());
                    data.setEmail(useremail.getText().toString());
                    data.setAltphone(useraltphone.getText().toString());
                    data.setPassword(userconfirm.getText().toString());


                    startActivity(new Intent(SignUpActivity.this, MapActivity.class).putExtra("object",data)
                            .putExtra("callingActivity",ActivityConstants.SignUpActivity));

                }
            }
        });
    }

    private void initView() {
        username  = findViewById(R.id.user_name);
        useremail  = findViewById(R.id.user_email);
        userpass = findViewById(R.id.user_password);
        userconfirm  = findViewById(R.id.user_confirm_password);
        useraltphone=findViewById(R.id.altphone);
        next = findViewById(R.id.next);
    }
}

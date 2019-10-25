package com.example.modabba;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.appcompat.app.AppCompatActivity;

import com.example.modabba.Utils.PassingData;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText textInputEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditText  = findViewById(R.id.login_number);

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(charSequence.length()==10){

                    String phone_number = "+91" + charSequence.toString();

                    PassingData model = new PassingData(LoginActivity.this);
                    model.setPhone(phone_number);

                    startActivity(new Intent(LoginActivity.this, VerificationActivity.class).putExtra("object",model)
                            .putExtra("callingActivity",ActivityConstants.LoginActivity));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}

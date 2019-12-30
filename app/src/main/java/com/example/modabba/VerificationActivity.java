package com.example.modabba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modabba.SessionManagement.SessionManagement;
import com.example.modabba.Utils.PassingData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OtpTextView;

public class VerificationActivity extends AppCompatActivity {
    private String verificationId;
    private FirebaseAuth mAuth;
    private static final String TAG = VerificationActivity.class.getSimpleName();
    private ProgressDialog progressDialog;
    private FirebaseFirestore db;
    private OtpTextView otpTextView;
    private TextView resendCode;
    private SessionManagement sessionManagement ;
    private String number;
    private int callingActivity ;
    private PassingData userDetails;
    private Button submit;
    private ImageView iv_lines;
    private KProgressHUD progressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        otpTextView = findViewById(R.id.otp_view);
        resendCode = findViewById(R.id.resend_code);
        submit  =findViewById(R.id.submit);

        progressHUD = new KProgressHUD(this);
        sessionManagement = new SessionManagement(this);

        iv_lines=findViewById(R.id.iv_line);

        final AnimatedVectorDrawable avd= (AnimatedVectorDrawable) iv_lines.getBackground();
        avd.registerAnimationCallback(new Animatable2.AnimationCallback() {
            @Override
            public void onAnimationEnd(Drawable drawable) {
                iv_lines.post(new Runnable() {
                    @Override
                    public void run() {
                        avd.start();
                    }
                });
            }
        });
        avd.start();



        callingActivity = getIntent().getIntExtra("callingActivity",0000);

        switch (callingActivity){

            case ActivityConstants.LoginActivity:

                userDetails = (PassingData) getIntent().getParcelableExtra("object");
                number = userDetails.getPhone();

                break;
            case ActivityConstants.MainActivity:

                number = getIntent().getStringExtra("newnumber");
                number = "+91" + number;

                break;

        }
        TextView n = findViewById(R.id.verification_number);
        n.setText(number);
        sendVerificationCode(number);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = otpTextView.getOTP();

                if (code.isEmpty() || code.length() < 6) {

                    otpTextView.showError();
                    return;
                }
                verifyCode(code);
            }
        });



    }
    private void verifyCode(String code) {
        Log.i(TAG,"In verify Code");
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }
    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //verification code matched
                            showProgressDialog("Please Wait","Checking User");
                            checkIfUserExist(callingActivity);


                        } else {
                            //entered code is invalid
                            otpTextView.showError();
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
    private void checkIfUserExist(int callingActivity){

        switch (callingActivity) {

            case ActivityConstants.LoginActivity:

                isUserNumberIsPrimaryNumber();

                break;
            case ActivityConstants.MainActivity:

                // 1. Check if the user exist with the new number or not
                //      1.1  YES, then return ERROR
                //      1.2  NO, Create New User with Document ID = new_number, Document Data = old_number data

                db.collection("users")
                        .whereEqualTo("primaryNumber",number)
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                //if user does not exist
                                if(queryDocumentSnapshots.isEmpty()){

                                    Map<String, Object> secondaryNumber = new HashMap<>();
                                    secondaryNumber.put("alternateNumber",number);

                                    db.collection("users")
                                            .document(getIntent().getStringExtra("documentId"))
                                            .set(secondaryNumber, SetOptions.merge());

                                    sessionManagement.changeNumber(number);

                                    finish();
                                }
                                else{

                                    // if user exist with the new number
                                    Toast.makeText(getApplicationContext(),"User Already with the number", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                break;
        }
    }
    public void isUserNumberIsPrimaryNumber(){

        Log.i(TAG,"checkUserPrimaryNumber "+ number);
        db.collection("users")
                .whereEqualTo("primaryNumber",number)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {


                        // If user exists
                        if(queryDocumentSnapshots.size() == 1){

                            hideProgressDialog();

                            List<DocumentSnapshot> documents  = queryDocumentSnapshots.getDocuments();
                            String username = (String)documents.get(0).get("username");
                            String email = (String)documents.get(0).get("email");
                            String number = (String)documents.get(0).get("primaryNumber");
                            String documentId = (String) documents.get(0).getId();
                            String serviceable = (String) documents.get(0).get("serviceable");

                            Log.i(TAG,"Serviceable Location");
                            //create login session for the user
                            sessionManagement.createLoginSession(number,email,username,documentId);
                            startActivity(new Intent(VerificationActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                        //If user does not exists
                        else {

                            //check in alternate number field
                            isUserNumberIsAlternateNumber();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(),"Please try after some time", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void sendVerificationCode(String number) {

        Log.i(TAG,"Verification Code send to "+number);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                20,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            //  super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otpTextView.setOTP(code);
                verifyCode(code);
            }
        }
        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(String s) {
            super.onCodeAutoRetrievalTimeOut(s);
            Log.i(TAG,"Timed Out");
            resendCode.setVisibility(View.VISIBLE);
            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendVerificationCode(number);
                    Toast.makeText(getApplicationContext(),"Code Resent",Toast.LENGTH_SHORT).show();
                }
            });

        }
    };
    public void isUserNumberIsAlternateNumber(){

        Log.i(TAG,"checkUserAlternateNumber" + number);
        db.collection("users")
                .whereEqualTo("alternateNumber",number)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        hideProgressDialog();

                        // If user exists
                        if(queryDocumentSnapshots.size() == 1){

                            List<DocumentSnapshot> documents  = queryDocumentSnapshots.getDocuments();
                            String username = (String)documents.get(0).get("username");
                            String email = (String)documents.get(0).get("email");
                            String number = (String)documents.get(0).get("alternateNumber");
                            String documentId = (String) documents.get(0).getId();
                            //create login session for the user
                            sessionManagement.createLoginSession(number,email,username,documentId);
                            startActivity(new Intent(VerificationActivity.this, MainActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        }
                        //If user does not exists
                        else {

                            Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                            intent.putExtra("object", userDetails);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                        Toast.makeText(getApplicationContext(),"Please try after some time", Toast.LENGTH_SHORT).show();

                    }
                });
    }
    private void showProgressDialog(String title,String Message){

        progressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please Wait")
                .setCancellable(false)
                .setAnimationSpeed(1)
                .setDimAmount(0.5f)
                .show();
    }
    private void hideProgressDialog(){

        progressHUD.dismiss();
    }
}

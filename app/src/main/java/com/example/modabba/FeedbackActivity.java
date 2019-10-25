package com.example.modabba;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.modabba.SessionManagement.SessionManagement;
import com.example.modabba.Utils.Feedback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    private Button button;
    private EditText feedbackEdt;
    private FirebaseFirestore db;
    private SessionManagement sessionManagement;
    private  ImageView emoji1,emoji2,emoji3,emoji4,emoji5;
    private TextView sad,ok,good,satisfied,best;
    private long rating = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_feedback);


        button = findViewById(R.id.btn);

        feedbackEdt = findViewById(R.id.feedback);

        emoji1=findViewById(R.id.emoji1);
        emoji2=findViewById(R.id.emoji2);
        emoji3=findViewById(R.id.emoji3);
        emoji4=findViewById(R.id.emoji4);
        emoji5=findViewById(R.id.emoji5);

        sad=findViewById(R.id.sad);
        ok=findViewById(R.id.ok);
        good=findViewById(R.id.good);
        satisfied=findViewById(R.id.satisfied);
        best=findViewById(R.id.best);

        emoji1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sad.setVisibility(View.VISIBLE);
                ok.setVisibility(View.INVISIBLE);
                good.setVisibility(View.INVISIBLE);
                satisfied.setVisibility(View.INVISIBLE);
                best.setVisibility(View.INVISIBLE);
                emoji5.setImageResource(R.drawable.happy1);
                emoji4.setImageResource(R.drawable.happy11);
                emoji3.setImageResource(R.drawable.smiling1);
                emoji2.setImageResource(R.drawable.confused1);
                emoji1.setImageResource(R.drawable.sad2);

                rating = 1;
            }
        });



        emoji2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sad.setVisibility(View.INVISIBLE);
                ok.setVisibility(View.VISIBLE);
                good.setVisibility(View.INVISIBLE);
                satisfied.setVisibility(View.INVISIBLE);
                best.setVisibility(View.INVISIBLE);
                emoji5.setImageResource(R.drawable.happy1);
                emoji4.setImageResource(R.drawable.happy11);
                emoji3.setImageResource(R.drawable.smiling1);
                emoji1.setImageResource(R.drawable.sad1);
                emoji2.setImageResource(R.drawable.confused2);

                rating = 2;
            }
        });

        emoji3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sad.setVisibility(View.INVISIBLE);
                ok.setVisibility(View.INVISIBLE);
                good.setVisibility(View.VISIBLE);
                satisfied.setVisibility(View.INVISIBLE);
                best.setVisibility(View.INVISIBLE);
                emoji5.setImageResource(R.drawable.happy1);
                emoji4.setImageResource(R.drawable.happy11);
                emoji3.setImageResource(R.drawable.smiling2);
                emoji1.setImageResource(R.drawable.sad1);
                emoji2.setImageResource(R.drawable.confused1);

                rating = 3;

            }
        });


        emoji4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sad.setVisibility(View.INVISIBLE);
                ok.setVisibility(View.INVISIBLE);
                good.setVisibility(View.INVISIBLE);
                satisfied.setVisibility(View.VISIBLE);
                best.setVisibility(View.INVISIBLE);
                emoji5.setImageResource(R.drawable.happy1);
                emoji4.setImageResource(R.drawable.happy12);
                emoji3.setImageResource(R.drawable.smiling1);
                emoji1.setImageResource(R.drawable.sad1);
                emoji2.setImageResource(R.drawable.confused1);

                rating = 4;

            }
        });
        emoji5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sad.setVisibility(View.INVISIBLE);
                ok.setVisibility(View.INVISIBLE);
                good.setVisibility(View.INVISIBLE);
                satisfied.setVisibility(View.INVISIBLE);
                best.setVisibility(View.VISIBLE);
                emoji5.setImageResource(R.drawable.happy2);
                emoji4.setImageResource(R.drawable.happy11);
                emoji3.setImageResource(R.drawable.smiling1);
                emoji1.setImageResource(R.drawable.sad1);
                emoji2.setImageResource(R.drawable.confused1);

                rating = 5;

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(rating>=1){

                    submitFeedback();
                }
                else{

                    Toast.makeText(getApplicationContext(),"Please select ratings",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void submitFeedback() {

        db = FirebaseFirestore.getInstance();
        sessionManagement = new SessionManagement(getApplicationContext());

        db.collection("user").document(sessionManagement.getUserDocumentId())
                .collection("feedback")
                .add(new Feedback(rating,feedbackEdt.getText().toString()))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(getApplicationContext(),"Feedback Submitted",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Try after sometime",Toast.LENGTH_SHORT).show();
            }
        });

    }
}

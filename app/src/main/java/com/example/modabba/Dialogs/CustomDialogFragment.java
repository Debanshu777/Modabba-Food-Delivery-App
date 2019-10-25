package com.example.modabba.Dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.modabba.ActivityConstants;
import com.example.modabba.LoginActivity;
import com.example.modabba.R;
import com.example.modabba.SessionManagement.SessionManagement;
import com.google.firebase.auth.FirebaseAuth;

public class CustomDialogFragment extends DialogFragment {

    private Context context;
    private SessionManagement sessionManagement;
    private FirebaseAuth firebaseAuth;

    private TextView title, positiveButton, negativeButton;
    private String positiveText,negativeText,t;
    private int fc;

    public CustomDialogFragment(Context context, String t, String positiveText, String negativeText, int fragment_constant) {
        this.context = context;
        this.positiveText = positiveText;
        this.negativeText = negativeText;
        this.t = t;
        this.fc = fragment_constant;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_dialog_layout,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        title  = view.findViewById(R.id.dialog_message);
        positiveButton = view.findViewById(R.id.dialog_positive_text);
        negativeButton = view.findViewById(R.id.dialog_negative_text);

        firebaseAuth  = FirebaseAuth.getInstance();
        sessionManagement = new SessionManagement(context);

        title.setText(t);
        positiveButton.setText(positiveText);
        negativeButton.setText(negativeText);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (fc){

                    case ActivityConstants.ProfileFragment:
                        logoutUser();
                        break;
                }
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }
    private void logoutUser() {

        if(firebaseAuth.getCurrentUser()!=null)
             firebaseAuth.signOut();

        if(sessionManagement.isLoggedIn())
            sessionManagement.logoutUser();

        context.startActivity(new Intent(context, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }
}

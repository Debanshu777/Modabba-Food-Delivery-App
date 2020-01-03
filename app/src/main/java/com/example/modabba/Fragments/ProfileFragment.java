package com.example.modabba.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.modabba.ActiveSubActivity.ActiveSubscriptionActivity;
import com.example.modabba.ActivityConstants;
import com.example.modabba.Dialogs.CustomDialogFragment;
import com.example.modabba.Dialogs.EditProfileBottomSheet;
import com.example.modabba.FeedbackActivity;
import com.example.modabba.MapActivity;
import com.example.modabba.Payment.PaymentActivity;
import com.example.modabba.R;
import com.example.modabba.SessionManagement.SessionManagement;
import com.example.modabba.TrialOrder;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class ProfileFragment extends Fragment {

    private Context context;
    private SessionManagement sessionManagement;
    private FirebaseAuth firebaseAuth;
    private Button logout;
    private TextView currentUser, currentEmail, phone, edit;

    private LinearLayout manage_address, payment, order_trial, shareapp, leavefeedback,contactus;

    public ProfileFragment(Context context){
        this.context = context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            init();
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        return view;
    }

    private void init() {

        firebaseAuth = FirebaseAuth.getInstance();
        sessionManagement = new SessionManagement(context);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        setCurrentUserDetails();
        manage_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, MapActivity.class).putExtra("callingActivity",002));
            }
        });

        order_trial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, TrialOrder.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = "Are you sure want to logout";
                String p = "Logout";
                String n = "Cancel";
                CustomDialogFragment dialog  = new CustomDialogFragment(context,title,p,n, ActivityConstants.ProfileFragment);
                dialog.show(getFragmentManager(),"dialog");


            }
        });
        leavefeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, FeedbackActivity.class));
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PaymentActivity.class));
            }
        });
        shareapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add a share link
                Intent i=new Intent(android.content.Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,"Share");
                i.putExtra(android.content.Intent.EXTRA_TEXT, "Share link");
                startActivity(Intent.createChooser(i,"Share via"));
            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:Add Restaurant Phone number
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0123456789"));
                startActivity(intent);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditProfileBottomSheet editProfileBottomSheet = new EditProfileBottomSheet(context,phone.getText().toString(),currentEmail.getText().toString(),currentUser.getText().toString(),sessionManagement.getUserDocumentId());
                editProfileBottomSheet.show(getChildFragmentManager(),"bottomSheet");
            }
        });

    }

    private void setCurrentUserDetails() {

        HashMap<String,String> map= sessionManagement.getUserDetails();

        currentUser.setText(map.get("NAME"));
        currentEmail.setText(map.get("EMAIL"));
        phone.setText(map.get("NUMBER"));

    }

    private void initView(View view) {

        logout = view.findViewById(R.id.logout);

        currentUser = view.findViewById(R.id.name);
        currentEmail = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.number);
        edit = view.findViewById(R.id.editProfile);
        order_trial=view.findViewById(R.id.order_trial);
        manage_address = view.findViewById(R.id.manage_address);
        contactus=view.findViewById(R.id.contact_us);
        payment = view.findViewById(R.id.payment);
        shareapp = view.findViewById(R.id.share_app);
        leavefeedback = view.findViewById(R.id.leave_feedback);
    }


}

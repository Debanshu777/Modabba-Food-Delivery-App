package com.example.modabba.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.modabba.R;

public class LunchDashboard extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.lunchdashboard,container,false);
//        TextView dashboard_lunch=rootView.findViewById(R.id.dashboard_lunch);
//        String value=getArguments().getString("key");
//        dashboard_lunch.setText(value);
        return rootView;
    }
}

package com.example.modabba;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.modabba.Fragments.DashboardFragment;
import com.example.modabba.Fragments.ProfileFragment;
import com.example.modabba.Fragments.SubscriptionFragment;
import com.example.modabba.RemoteConfig.UpdateHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements UpdateHelper.onUpdateCheckListener {


    private BottomNavigationView bottomNavigationView;
    private Fragment fragment;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notif1", "Notifications",NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("menu")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "successful";
                        if (!task.isSuccessful()) {
                            msg = "failed";
                        }
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });*/
        init();
        loadFragment(new SubscriptionFragment(getApplicationContext()));
        setDefaultFragment();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                switch (menuItem.getItemId()){

                    case R.id.menu:

                        fragment = new DashboardFragment(getApplicationContext());
                        loadFragment(fragment);
                        return true;

                    case R.id.subscribe:

                        fragment = new SubscriptionFragment(getApplicationContext());
                        loadFragment(fragment);
                        return true;


                    case R.id.profile:

                        fragment = new ProfileFragment(getApplicationContext());
                        loadFragment(fragment);
                        return true;


                }
                return true;
            }
        });
    }
    private void init() {

        bottomNavigationView = findViewById(R.id.bottom_nav);


    }
    private void loadFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.screens,fragment,TAG).commit();

    }
    private void setDefaultFragment() {
        fragment = new DashboardFragment(getApplicationContext());
        loadFragment(fragment);

    }

    @Override
    public void onUpdateListener(final String urlApp) {

        //TODO: Update Play Store Url
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("New Version Available")
                .setMessage("Please update your app to continue")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        redirectStore(urlApp);
                    }
                }).setNegativeButton("No,Thanks", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create();

        alertDialog.setCancelable(false);
        alertDialog.show();

    }
    private void checkUpdated() {

        Log.i(MainActivity.class.getSimpleName(),"In Check Updated");
        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check();
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkUpdated();
    }
    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
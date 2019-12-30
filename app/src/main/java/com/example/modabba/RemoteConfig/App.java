package com.example.modabba.RemoteConfig;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.modabba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class App extends Application {

    private static final String TAG = App.class.getSimpleName();
    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate() {
        super.onCreate();
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        Map<String, Object> defaultValue  = new HashMap<>();
        defaultValue.put(UpdateHelper.KEY_VERSION,"1.0.0");
        defaultValue.put(UpdateHelper.KEY_UPDATE_ENABLE,false);
        defaultValue.put(UpdateHelper.KEY_UPDATE_URL,"App Store Url");

        remoteConfig.setDefaults(defaultValue);
        remoteConfig.fetch(5)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Log.d(TAG, "remote config is fetched.");

                            remoteConfig.activateFetched();
                        }
                    }
                });
        
    }
}

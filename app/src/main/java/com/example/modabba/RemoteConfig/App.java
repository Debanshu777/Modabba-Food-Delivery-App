package com.example.modabba.RemoteConfig;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

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

    @Override
    public void onCreate() {
        super.onCreate();
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseFirestore db=FirebaseFirestore.getInstance();

        db.collection("menu").document("lunch")
                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                StringBuilder builder = new StringBuilder();

                Map<String,String> data = (Map<String, String>) documentSnapshot.get("lunch");

                Iterator<String> itr  = data.keySet().iterator();

                while (itr.hasNext()){

                    String key = itr.next();
                    String value = data.get(key);

                    String cap  = key.substring(0, 1).toUpperCase() + key.substring(1);

                    builder.append(" "+value);
                    builder.append(" "+cap+" ");

                    if((itr.hasNext()))
                        builder.append("/");

                }
                //jaha_tujhe_set_arna_ho_mc.setText(builder+" ");
                System.out.println(builder);
            }
        });
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

package com.example.modabba.RemoteConfig;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

public class UpdateHelper {

    public static String KEY_UPDATE_ENABLE = "isUpdated";
    public static String KEY_VERSION = "version";
    public static String KEY_UPDATE_URL = "update_url";
    private static final String TAG = UpdateHelper.class.getSimpleName();


    public interface onUpdateCheckListener{
        void onUpdateListener(String urlApp);
    }

    public static Builder with(Context context){
        return new Builder(context);
    }

    private onUpdateCheckListener onUpdateCheckListener;
    private Context context;

    public UpdateHelper(Context context, UpdateHelper.onUpdateCheckListener onUpdateCheckListener) {
        this.onUpdateCheckListener = onUpdateCheckListener;
        this.context = context;
    }
    public void check(){

        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if(remoteConfig.getBoolean(KEY_UPDATE_ENABLE)){

            String currentVersion = remoteConfig.getString(KEY_VERSION);
            String appVersion  = getAppVersion(context);
            String update_url = remoteConfig.getString(KEY_UPDATE_URL);

            if(!TextUtils.equals(currentVersion,appVersion) && onUpdateCheckListener!=null){
                onUpdateCheckListener.onUpdateListener(update_url);
            }
        }
    }
    private String getAppVersion(Context context){
        String result = "";

        try {
            result = context.getPackageManager().getPackageInfo(context.getPackageName(),0).versionName;
            result = result.replaceAll("[a-zA-Z]|-","");

            Log.d(TAG,result);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static class Builder{

        private Context context;
        private onUpdateCheckListener onUpdateCheckListener;

        public Builder(Context context){
            this.context = context;
        }

        public Builder onUpdateCheck(onUpdateCheckListener onUpdateCheckListener){
            this.onUpdateCheckListener = onUpdateCheckListener;
            return this;
        }

        public UpdateHelper build(){
            return new UpdateHelper(context,onUpdateCheckListener);
        }
        public UpdateHelper check(){
            UpdateHelper updateHelper = build();
            updateHelper.check();

            return updateHelper;
        }
    }

}


package com.example.modabba.SessionManagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.modabba.LoginActivity;
import com.example.modabba.MainActivity;

import java.util.HashMap;

public class SessionManagement {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;


    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "session_pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String KEY_NUMBER = "NUMBER";
    private static final String KEY_EMAIL  = "EMAIL";
    private static final String KEY_USERNAME = "NAME";
    private static final String KEY_DOCUMENTID = "DOCUMENTID";

    // Constructor
    public SessionManagement(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    //Create Login Sessions
    public void createLoginSession(String number, String email, String name, String id){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);


        // commit changes
        // Storing number in pref
        editor.putString(KEY_NUMBER, number);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_USERNAME, name);
        editor.putString(KEY_DOCUMENTID,id);
        editor.commit();
    }

    public void changeUserName(String username){

        editor.putString(KEY_USERNAME,username);
        editor.commit();
    }
    public String getUserDocumentId(){

        return pref.getString(KEY_DOCUMENTID,null);
    }

    public void changeNumber(String number){

        editor.putString(KEY_NUMBER,number);
        editor.commit();
    }
    //Get Stored Session
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NUMBER, pref.getString(KEY_NUMBER, null));
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
        user.put(KEY_DOCUMENTID,pref.getString(KEY_DOCUMENTID,null));
        // return user
        return user;
    }

    //check the login status of the user
    public void checkLogin() {
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
        else {

            Intent i = new Intent(_context, MainActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }
    }

    public String getUserNumber(){

        return pref.getString(KEY_NUMBER,null);

    }
    public String getUserName(){

        return pref.getString(KEY_USERNAME,null);

    }
    public String getUserEmail(){

        return pref.getString(KEY_EMAIL,null);

    }
    //clear login sessions
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Logging Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}

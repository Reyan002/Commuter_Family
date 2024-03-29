package com.commutersfamily.commuterfamily.SessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.commutersfamily.commuterfamily.Activities.SplashScreenActivity;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "CFamily";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String IS_KEY = "IsKey";

     // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";
//    public static final String image1 = "name1";
//    public static final String image2 = "name2";
//    public static final String image3 = "name3";

    // Email address (make variable public to access from outside)
    public static final String KEY_PHONE = "phone";
    public static final String KEY_CAR = "key";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

//    public void imagesList(String image1,String image2, String image3){
//
//        editor.putString(this.image1,image1);
//        editor.putString(this.image2,image2);
//        editor.putString(this.image3,image3);
//        editor.commit();
//    }


    /**
     * Create login session
     * */
    public void createLoginSession(String name, String phone){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_PHONE, phone);

        // commit changes
        editor.commit();
    }
    public void createSessionOfKey(String key){
        editor.putBoolean(IS_KEY, true);
        editor.putString(KEY_CAR,key);
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
//    public void checkLogin(){
//        // Check login status
//        if(!this.isLoggedIn()){
//            // user is not logged in redirect him to Login Activity
//            Intent i = new Intent(_context, LoginActivity.class);
//            // Closing all the Activities
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//
//            // Add new Flag to start new Activity
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            // Staring Login Activity
//            _context.startActivity(i);
//        }
//
//    }


//    public String getImage1(){
//        String imag1 = pref.getString(image1,null);
//        return imag1;
//    }
//    public String getImage2(){
//        String imag1 = pref.getString(image2,null);
//        return imag1;
//    }
//    public String getImage3(){
//        String imag1 = pref.getString(image3,null);
//        return imag1;
//    }

    /**
     * Get stored session data
     * */
    public String getUserDetails(){
//        HashMap<String, String> user = new HashMap<String, String>();
        // user name
//        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        String user=pref.getString(KEY_NAME, null) ;;
        // user email id


        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, SplashScreenActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);

    }
    public String getIsKey(){
//        HashMap<String, String> user = new HashMap<String, String>();
        // user name
//        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        String user=pref.getString(KEY_CAR, null) ;;
        // user email id


        // return user
        return user;
    }
    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public boolean isKey(){
        return pref.getBoolean(IS_KEY, false);
    }

}
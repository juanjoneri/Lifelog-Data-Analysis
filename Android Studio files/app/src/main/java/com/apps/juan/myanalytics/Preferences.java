package com.apps.juan.myanalytics;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    public Preferences(Activity activity){

        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

    }

    public void putInt(String key, int data){

        editor.putInt(key, data);
        editor.commit();

    }
    public void putString(String key, String data){

        editor.putString(key, data);
        editor.commit();

    }
    public int getInt(String key, int defaultValue){

        return sharedPref.getInt(key, defaultValue);
    }
    public String getString(String key, String defaultValue){

        return sharedPref.getString(key, defaultValue);
    }
}

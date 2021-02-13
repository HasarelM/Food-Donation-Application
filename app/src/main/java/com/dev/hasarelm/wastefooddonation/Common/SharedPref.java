package com.dev.hasarelm.wastefooddonation.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SharedPref {

    public static final String FILE_KEY = "";
    public static final String VERIFIED_LOGGED = "";

    public static final String USERNAME_KEY = "";
    public static final String PASSWORD_KEY = "";
    public static final String USER_VIEW_NAME_KEY = "";
    public static final String USER_VIEW_EMPLOYEE_ROLE_KEY = "";
    public static final String USER_VIEW_EMPLOYEE_ID_KEY = "";

    public static final String USER_ROLE_KEY = "";
    public static final String ACCOUNT_NUMBER_KEY = "";
    public static final String LIFE_CYCLE_LOCK_KEY = "";

    public static final String USER_TASK_LIST_START_DATE = "";
    public static final String USER_TASK_LIST_END_DATE = "";
    public static String SETTINGS = "SETTINGS";
    public static final String DEFAULT_VALUE_FOR_STRING = "";

    public static SharedPreferences getPref(Context ctx) {
        return ctx.getSharedPreferences(SharedPref.FILE_KEY, Context.MODE_PRIVATE);
    }

    public static void setLocalSharedPreference(final Context con, String localSPKey, String localSPValue) {
        SharedPreferences localSP = con.getSharedPreferences(SETTINGS, Context.MODE_PRIVATE+ Context.MODE_PRIVATE);
        SharedPreferences.Editor localBackupEditor = localSP.edit();
        localBackupEditor.putString(localSPKey, localSPValue);
        localBackupEditor.commit();
    }

    public static void saveString(Context act, String key, String value) {
        SharedPreferences.Editor editor = act.getSharedPreferences(
                SharedPref.FILE_KEY, Context.MODE_PRIVATE).edit();
//        editor.putString(Encryption.getInstance().encrypt(key), Encryption.getInstance().encrypt(value));
        editor.putString(key, value);
        editor.commit();
    }

    public static void saveBoolean(Context act, String key, boolean value) {
        SharedPreferences.Editor editor = act.getSharedPreferences(
                SharedPref.FILE_KEY, Context.MODE_PRIVATE).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static String getString(Context act, String key, String defaultval) {
        SharedPreferences prefs = act.getSharedPreferences(SharedPref.FILE_KEY,
                Context.MODE_PRIVATE);
//        return Encryption.getInstance().decrypt(prefs.getString(Encryption.getInstance().encrypt(key), defaultval));
        return prefs.getString(key, defaultval);
    }

    public static boolean getBoolean(Context act, String key,
                                     boolean defaultval) {
        SharedPreferences prefs = act.getSharedPreferences(SharedPref.FILE_KEY,
                Context.MODE_PRIVATE);
        return prefs.getBoolean(key, defaultval);
    }

    public static void saveInteger(Activity act, String key, Integer value) {
        SharedPreferences.Editor editor = act.getSharedPreferences(
                SharedPref.FILE_KEY, Context.MODE_PRIVATE).edit();
        editor.putInt(key, value);
//        editor.putString(Encryption.getInstance().encrypt(key), Encryption.getInstance().encrypt(String.valueOf(value)));
        editor.commit();
    }

    public static int getInteger(Activity act, String key,
                                 int defaultval) {
        SharedPreferences prefs = act.getSharedPreferences(SharedPref.FILE_KEY,
                Context.MODE_PRIVATE);
        return prefs.getInt(key, defaultval);
//        return Integer.parseInt(Encryption.getInstance().decrypt(prefs.getString(Encryption.getInstance().encrypt(key), String.valueOf(defaultval))));
    }

    public static void saveLong(Activity act, String key, long value) {
        SharedPreferences.Editor editor = act.getSharedPreferences(
                SharedPref.FILE_KEY, Context.MODE_PRIVATE).edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public static long getLong(Activity act, String key,
                               long defaultval) {
        SharedPreferences prefs = act.getSharedPreferences(SharedPref.FILE_KEY,
                Context.MODE_PRIVATE);
        return prefs.getLong(key, defaultval);
    }

    public static void saveFloat(Activity act, String key, float value) {
        SharedPreferences.Editor editor = act.getSharedPreferences(
                SharedPref.FILE_KEY, Context.MODE_PRIVATE).edit();
        editor.putFloat(key, value);
        editor.commit();
    }


    public static float getFloat(Activity act, String key,
                                 float defaultval) {
        SharedPreferences prefs = act.getSharedPreferences(SharedPref.FILE_KEY,
                Context.MODE_PRIVATE);
        return prefs.getFloat(key, defaultval);
    }


    public static void deleteString(Activity act, String key) {
        SharedPreferences.Editor editor = act.getSharedPreferences(
                SharedPref.FILE_KEY, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.commit();
    }

    public static void deleteInt(Activity act, String key) {
        SharedPreferences.Editor editor = act.getSharedPreferences(
                SharedPref.FILE_KEY, Context.MODE_PRIVATE).edit();
        editor.remove(key);
        editor.commit();
    }

    public static void clearSharedPref(Activity act) {
        SharedPreferences.Editor editor = act.getSharedPreferences(
                SharedPref.FILE_KEY, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }
}

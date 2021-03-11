package com.dev.hasarelm.wastefooddonation.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

public class SharedPref extends Activity{

    public static final String FILE_KEY = "";
    public static final String VERIFIED_LOGGED = "";
    public static final String USERNAME_KEY = "";
    public static final String USER_VIEW_NAME_KEY = "";
    public static final String ACCOUNT_NUMBER_KEY = "";
    public static String SETTINGS = "SETTINGS";
    public static final String DEFAULT_VALUE_FOR_STRING = "";

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
}

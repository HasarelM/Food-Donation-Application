package com.dev.hasarelm.wastefooddonation.Common;

import android.app.Activity;

public class UserInfoManager {

    public static void logout(Activity act) {
        try {
            String existingAccountNumber = SharedPref.getString(act, SharedPref.ACCOUNT_NUMBER_KEY, SharedPref.DEFAULT_VALUE_FOR_STRING);
            SharedPref.saveString(act, SharedPref.ACCOUNT_NUMBER_KEY, existingAccountNumber);
        } catch (Exception e) {
        }
    }

    public static boolean isSignedIn(Activity act) {
        return SharedPref.getBoolean(act,
                SharedPref.VERIFIED_LOGGED, false);

    }
}

package com.dev.hasarelm.wastefooddonation.Common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterHomeActivity;
import com.dev.hasarelm.wastefooddonation.R;

public class CommonFunction {

    // Navigation Drawer menu items
    public static final String NAV_ITEM_dashboard = "Home";
    public static final String NAV_ITEM_Donation = "Donation History";
    public static final String NAV_ITEM_Rate = "Your Rate";
    public static final String NAV_ITEM_notification = "Notification";
    public static final String NAV_ITEM_about_us = "About us";
    public static final String NAV_ITEM_Logout = "Logout";
    public static final int BACK_PRESSED_DELAY = 1000; // onBackPressed()-> delay

    public static void CustomTost(Context context, String message) {
        try {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            View view = toast.getView();
            view.getBackground().setColorFilter(ContextCompat.getColor(context, R.color.col_gray), PorterDuff.Mode.SRC_IN);
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(ContextCompat.getColor(context, R.color.clr_white_900));

            toast.show();
        } catch (NullPointerException ee) {

        }

    }

    // Check network connection availability
    public static boolean checkNetworkConnection(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo activeNetworks = connectivityManager.getActiveNetworkInfo();
        if (activeNetworks != null && activeNetworks.isConnected()) {
            return activeNetworks.isConnectedOrConnecting();
        }
        return false;
    }


    // start an activity using drawer
    public static void startActivityFromDrawer(Activity activity, Class<? extends Activity> aClass) {
        if (!activity.getClass().getSimpleName().equals(DonaterHomeActivity.class.getSimpleName()))
            activity.finish();
        Intent intent = new Intent(activity, aClass);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
    }
}

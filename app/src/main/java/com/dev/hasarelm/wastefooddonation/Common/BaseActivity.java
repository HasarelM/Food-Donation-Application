package com.dev.hasarelm.wastefooddonation.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.hasarelm.wastefooddonation.Activity.Donater.AboutUsActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterHomeActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterNotificationActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterRateActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonationHistoryActivity;
import com.dev.hasarelm.wastefooddonation.Activity.LoginActivity;
import com.dev.hasarelm.wastefooddonation.Fragment.DrawerFragment;
import com.dev.hasarelm.wastefooddonation.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BaseActivity extends AppCompatActivity implements DrawerFragment.FragmentDrawerListener {

    // constants
    private static final String TAG = BaseActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private DrawerFragment mDrawerFragment;
    private DrawerLayout mDrawerLayout;

    public BaseActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void setToolbar(String toolbarName, Activity activity) {
        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        TextView tvTitle = findViewById(R.id.tv_toolbar_title);
        tvTitle.setText(toolbarName);
        if (toolbarName.isEmpty()) {
            // mToolbar.setBackgroundColor(Color.TRANSPARENT);
        }
        setSupportActionBar(mToolbar);
        if (!activity.getClass().getSimpleName().equals(DonaterHomeActivity.class.getSimpleName())) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //set custom navigation icon
        /*getSupportActionBar().setHomeAsUpIndicator(AppUtil.getDrawable(Iconify.IconValue.arrow_left,
                ContextCompat.getColor(this, R.color.color_toolbar_icon_white), this, 34));*/
    }

    public void setDrawer() {

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mDrawerFragment = (DrawerFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        mDrawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        mDrawerFragment.setDrawerListener(this);
    }

    @Override
    public void onDrawerItemSelected(int position) {
        displayView(this, position);
    }

    private void displayView(final Activity activity, int position) {
        switch (position) {
            case 0: // Dashboard (MainActivity)
                if (!activity.getClass().getSimpleName().equals(DonaterHomeActivity.class.getSimpleName())) {
                    activity.finish();
                    activity.overridePendingTransition(R.anim.pull_in_right, R.anim.push_out_left);
                }
                break;
            case 1: // Sync (Sync Activity)
                if (!activity.getClass().getSimpleName().equals(DonationHistoryActivity.class.getSimpleName())) {
                    CommonFunction.startActivityFromDrawer(activity, DonationHistoryActivity.class);
                }
                break;
            case 2: // About (About Activity)
                if (!activity.getClass().getSimpleName().equals(DonaterRateActivity.class.getSimpleName())) {
                    CommonFunction.startActivityFromDrawer(activity, DonaterRateActivity.class);
                }
                break;
            case 3:
                if (!activity.getClass().getSimpleName().equals(DonaterNotificationActivity.class.getSimpleName())) {
                    CommonFunction.startActivityFromDrawer(activity, DonaterNotificationActivity.class);
                }
                break;
            case 4:
                if (!activity.getClass().getSimpleName().equals(AboutUsActivity.class.getSimpleName())) {
                    CommonFunction.startActivityFromDrawer(activity, AboutUsActivity.class);
                }
                break;
            case 5:// Logout
                final Activity innerActivity = activity;
                new SweetAlertDialog(BaseActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Do you want logout!")
                        .setConfirmText("Ok")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                SharedPreferencesClass.ClearSharedPreference(BaseActivity.this,"deoneter_user_name");
                                SharedPreferencesClass.ClearSharedPreference(BaseActivity.this,"USER_ID");
                                UserInfoManager.logout(innerActivity);
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            }
                        })
                        .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            super.onBackPressed();
            overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
        }
    }
}
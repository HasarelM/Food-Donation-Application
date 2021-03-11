package com.dev.hasarelm.wastefooddonation.Activity.Rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import com.dev.hasarelm.wastefooddonation.Activity.Donater.AboutUsActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterRegisterActivity;
import com.dev.hasarelm.wastefooddonation.Activity.LoginActivity;
import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.Common.CommonFunction;
import com.dev.hasarelm.wastefooddonation.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.dev.hasarelm.wastefooddonation.Common.CommonFunction.CustomTost;

public class RiderHomeActivity extends BaseActivity {

    //Ui components
    private ImageButton mImgLogOut;
    private BottomNavigationView mBtnBottomNavigation;
    private boolean mBackPressedToExitOnce = false;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_home);
        initView();
        mBtnBottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Fragment fragmeffnt = new RiderHomeFragment();
        FragmentManager fragmentManahhger = getSupportFragmentManager();
        FragmentTransaction fragmentTrkkansaction = fragmentManahhger.beginTransaction();
        fragmentTrkkansaction.replace(R.id.container, fragmeffnt);
        fragmentTrkkansaction.commit();


    }


    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.navigation_home:
                            Fragment fragmeffnt = new RiderHomeFragment();
                            FragmentManager fragmentManahhger = getSupportFragmentManager();
                            FragmentTransaction fragmentTrkkansaction = fragmentManahhger.beginTransaction();
                            fragmentTrkkansaction.replace(R.id.container, fragmeffnt);
                            fragmentTrkkansaction.commit();
                            return true;

                        case R.id.navigation_sms:
                            Fragment frament = new RiderNotificationFragment();
                            FragmentManager fragmentManageer = getSupportFragmentManager();
                            FragmentTransaction fragmentTrsaction = fragmentManageer.beginTransaction();
                            fragmentTrsaction.replace(R.id.container, frament);
                            fragmentTrsaction.commit();
                            return true;
                        case R.id.navigation_notifications:

                            return true;
                    }
                    return false;
                }
            };

    private void initView() {
        mBtnBottomNavigation = findViewById(R.id.bottom_navigation);
    }

    @Override
    public void onBackPressed() {
       if (mBackPressedToExitOnce) {
            super.onBackPressed();
        } else {
            this.mBackPressedToExitOnce = true;
            CustomTost(RiderHomeActivity.this, "Press again to exit");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBackPressedToExitOnce = false;
                }
            }, CommonFunction.BACK_PRESSED_DELAY);
        }
    }
}
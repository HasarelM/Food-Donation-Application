package com.dev.hasarelm.wastefooddonation.Activity.Donater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.R;

public class DonaterNotificationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donater_notification);

        setToolbar(getResources().getString(R.string.notification_activity_title), DonaterNotificationActivity.this);
        setDrawer();
    }
}
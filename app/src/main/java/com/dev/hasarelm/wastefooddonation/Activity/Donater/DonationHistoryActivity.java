package com.dev.hasarelm.wastefooddonation.Activity.Donater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.R;

public class DonationHistoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donation_history);

        setToolbar(getResources().getString(R.string.Donation_activity_title), DonationHistoryActivity.this);
        setDrawer();
    }
}
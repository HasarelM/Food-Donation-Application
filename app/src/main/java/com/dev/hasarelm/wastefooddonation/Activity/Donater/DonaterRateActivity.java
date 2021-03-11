package com.dev.hasarelm.wastefooddonation.Activity.Donater;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Common.SharedPreferencesClass;
import com.dev.hasarelm.wastefooddonation.Model.UserDetails;
import com.dev.hasarelm.wastefooddonation.Model.profile;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class DonaterRateActivity extends BaseActivity {

    //Ui components
    private TextView mTvUserName,mTvDonateLevel,mTvDescription;
    private RatingBar mRatingBar;

    private UserDetails mUserDetails;
    private ArrayList<profile>mProfile;

    public static SharedPreferences localSP;
    private String ID = "";
    private int userID=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donater_rate);

        setToolbar(getResources().getString(R.string.rate_activity_title), DonaterRateActivity.this);
        setDrawer();
        initView();


        localSP = this.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
        ID = localSP.getString("ID","");
        userID = Integer.parseInt(ID);

        getDonaterLevel();
    }

    private void getDonaterLevel() {

        final ProgressDialog myPd_ring = ProgressDialog.show(this, "Please wait", "", true);
        EndPoints endPoints = RetrofitClient.getLoginClient().create(EndPoints.class);
        Call<UserDetails> call = endPoints.getUserDetails(VLF_BASE_URL+"profile?",userID);
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {

                try {

                    if (response.code()==200){

                        mUserDetails = response.body();
                        mProfile = mUserDetails.getProfile();

                        for (profile pf : mProfile){

                            mTvUserName.setText("Mr."+pf.getName());

                            if (pf.getRate()==1){

                                mTvDonateLevel.setText("Level 3 Donater");
                                mRatingBar.setRating(Float.parseFloat("3.0"));
                                mTvDescription.setText("In this tutorial, we shows you two basic examples to send SMS message. ... 1, You will use Android Studio IDE to create an Android application and name it as");
                                myPd_ring.dismiss();
                            }
                        }
                    }

                }catch (Exception f){}
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {

                myPd_ring.dismiss();
            }
        });
    }

    private void initView() {

        mTvUserName = findViewById(R.id.activity_donater_rate_tv_name);
        mTvDonateLevel = findViewById(R.id.activity_donater_tv_level);
        mTvDescription = findViewById(R.id.activity_donater_tv_description);
        mRatingBar = findViewById(R.id.ratingBar);
    }
}
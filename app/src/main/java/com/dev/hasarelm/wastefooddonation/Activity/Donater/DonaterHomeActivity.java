package com.dev.hasarelm.wastefooddonation.Activity.Donater;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Adapter.DonaterRequestAdapter;
import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Common.SharedPreferencesClass;
import com.dev.hasarelm.wastefooddonation.Interface.OnDonationItemClickListner;
import com.dev.hasarelm.wastefooddonation.Model.DonationRequestListModel;
import com.dev.hasarelm.wastefooddonation.Model.donations;
import com.dev.hasarelm.wastefooddonation.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Activity.LoginActivity.loginID;
import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class DonaterHomeActivity extends BaseActivity implements View.OnClickListener, OnDonationItemClickListner<donations> {

    //Ui Components
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRvAllDonationList;
    private LinearLayout mLvPending, mLvApprove, mLvDelivery;

    SharedPreferences localSP;
    private DonationRequestListModel mDonationTypeModel;
    private ArrayList<donations> mDonationTypes;
    private DonaterRequestAdapter mDonaterRequestAdapter;
    private String ID = "";
    private int userID = 0;
    private int state=1;
    private String rider_user_name;
    String category, weight, address ,street, city, monile, date,vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donater_home);

        setToolbar(getResources().getString(R.string.home_activity_title), DonaterHomeActivity.this);
        setDrawer();
        initView();

        try {

            loginID = userID;

            localSP = this.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
            ID = localSP.getString("USER_ID", "");
            userID = Integer.parseInt(ID);

        } catch (Exception f) {
        }


        getAllDonations(0,userID);
    }

    private void setupRecyclerView(ArrayList<donations> donationsArrayList) {
        mDonaterRequestAdapter = new DonaterRequestAdapter(DonaterHomeActivity.this, donationsArrayList, this);
        mRvAllDonationList.setHasFixedSize(true);
        mRvAllDonationList.setLayoutManager(new LinearLayoutManager(DonaterHomeActivity.this));
        mRvAllDonationList.setAdapter(mDonaterRequestAdapter);
    }

    private void getAllDonations(int state, int userID) {
        final ProgressDialog myPd_ring = ProgressDialog.show(this, "Please wait", "", true);
        try {
            EndPoints apiService = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<DonationRequestListModel> call_customer = apiService.getAllDonationList(VLF_BASE_URL + "donations?donater_id=", userID, state);
            call_customer.enqueue(new Callback<DonationRequestListModel>() {
                @Override
                public void onResponse(Call<DonationRequestListModel> call, Response<DonationRequestListModel> response) {

                    if (response.code() == 200) {

                        mDonationTypeModel = response.body();
                        mDonationTypes = mDonationTypeModel.getDonations();
                        myPd_ring.dismiss();
                        if (mDonationTypes.size() > 0) {

                            setupRecyclerView(mDonationTypes);
                            myPd_ring.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<DonationRequestListModel> call, Throwable t) {
                    myPd_ring.dismiss();
                }
            });

        } catch (Exception hh) {
        }
    }

    //Toolbar back button pressed
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void initView() {
        mRvAllDonationList = findViewById(R.id.activity_donater_home_rv_donation_list);
        mFloatingActionButton = findViewById(R.id.activity_donater_home_request_button);
        mLvPending = findViewById(R.id.activity_donater_home_lv_pending);
        mLvApprove = findViewById(R.id.activity_donater_home_lv_approve);
        mLvDelivery = findViewById(R.id.activity_donater_home_lv_delivery);
        mLvPending.setOnClickListener(this);
        mLvApprove.setOnClickListener(this);
        mLvDelivery.setOnClickListener(this);
        mFloatingActionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.activity_donater_home_request_button:
                Intent intent = new Intent(DonaterHomeActivity.this, DonaterRequestActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_donater_home_lv_pending:
                getAllDonations(1,userID);
                break;
            case R.id.activity_donater_home_lv_approve:
                getAllDonations(2,userID);
                break;
            case R.id.activity_donater_home_lv_delivery:
                getAllDonations(3,userID);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDonationClick(int position, donations data) {

        try {

            category = data.getDonation_type().toString().trim();
            address = data.getAdd_line_1().toString().trim();
            street = data.getAdd_line_2().toString().trim();
            city = data.getAdd_line_3().toString().trim();
            monile = data.getPhone().toString().trim();
            date = data.getCreated_at().toString().trim();
            weight = data.getWeight().toString().trim();
            vehicle = data.getVehicle_type().toString().trim();

            viewUploadDonation(category,address,street,city,monile,date,weight,vehicle);

        }catch (Exception g){}

    }

    private void viewUploadDonation(String category, String address, String street, String city, String monile, String date, String weight,String vehicle) {

        try {

            Dialog dialog = new Dialog(DonaterHomeActivity.this);
            dialog.setContentView(R.layout.expand_donation_layout);
            dialog.setTitle("Title");

            TextView cat = dialog.findViewById(R.id.expand_donation_layout_category);
            TextView add = dialog.findViewById(R.id.expand_donation_layout_address);
            TextView str = dialog.findViewById(R.id.expand_donation_layout_street);
            TextView cty = dialog.findViewById(R.id.expand_donation_layout_city);
            TextView dt = dialog.findViewById(R.id.expand_donation_layout_date);
            TextView wgh = dialog.findViewById(R.id.expand_donation_layout_weight);
            TextView mob = dialog.findViewById(R.id.expand_donation_layout_mobile);
            Button btn = dialog.findViewById(R.id.expand_donation_layout_btn_close);
            TextView vty = dialog.findViewById(R.id.expand_donation_layout_vehicle_type);

            StringTokenizer tokens = new StringTokenizer(date, "T");
            String first = tokens.nextToken();
            String second = tokens.nextToken();
            StringTokenizer token = new StringTokenizer(second, "T");
            String third = token.nextToken();
            StringTokenizer toke = new StringTokenizer(third, ".");
            String val = toke.nextToken();

            cat.setText(category);
            add.setText(address);
            str.setText(street);
            cty.setText(city);
            mob.setText(monile);
            dt.setText(first);
            wgh.setText(weight);
            vty.setText(vehicle);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            dialog.show();

        }catch (Exception f){}

    }

}
package com.dev.hasarelm.wastefooddonation.Activity.Donater;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.hasarelm.wastefooddonation.Activity.LoginActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Rider.RiderRegisterActivity;
import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Common.SharedPreferencesClass;
import com.dev.hasarelm.wastefooddonation.Model.DistrictsModel;
import com.dev.hasarelm.wastefooddonation.Model.DonationRequestModel;
import com.dev.hasarelm.wastefooddonation.Model.DonationTypeModel;
import com.dev.hasarelm.wastefooddonation.Model.RiderRegisterModel;
import com.dev.hasarelm.wastefooddonation.Model.VehicleTypeModel;
import com.dev.hasarelm.wastefooddonation.Model.WeightRangeModel;
import com.dev.hasarelm.wastefooddonation.Model.donationCreate;
import com.dev.hasarelm.wastefooddonation.Model.donationTypes;
import com.dev.hasarelm.wastefooddonation.Model.vehicleTypes;
import com.dev.hasarelm.wastefooddonation.Model.weights;
import com.dev.hasarelm.wastefooddonation.R;
import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Activity.LoginActivity.GPS_Latitude;
import static com.dev.hasarelm.wastefooddonation.Activity.LoginActivity.GPS_Longitude;
import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class DonaterRequestActivity extends BaseActivity implements View.OnClickListener {

    //Ui components
    private SearchableSpinner mSpCategoryType, mSpWeight, mSpSuggestVehicle;
    private EditText mEtAddress, mEtStreet, mEtCity, mEtMobileNo;
    private Button mBtnDonated;


    private String mAddress, mStreet, mCity, mMobile;
    public static SharedPreferences localSP;
    private DonationTypeModel mDonationTypeModel;
    private ArrayList<donationTypes> mDonationTypes;
    private ArrayList<String> mTypes = new ArrayList<>();
    private WeightRangeModel mWeightRangeModel;
    private ArrayList<weights> mWeights;
    private ArrayList<String> weightList = new ArrayList<>();
    private List<String> mSelectDistrict = new ArrayList<String>();
    private List<String> mSelectVehicleType = new ArrayList<String>();
    private RiderRegisterModel mRegisterModel;
    private  String message;
    private DonationRequestModel mDonationRequestModel;
    private ArrayList<donationCreate>mDonationCreates;
    private String ID = "";
    private String address,street,city,mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donater_request);

        setToolbar(getResources().getString(R.string.donater_request_activity_title), DonaterRequestActivity.this);

        final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myPd_ring.dismiss();
                // show popup
            }
        }, 3000);

        initView();
        getDonationTypes();
        getWeightRange();
        getVehicleAllTypes();

        localSP = this.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
        ID = localSP.getString("ID","");
        address = localSP.getString("address","");
        street = localSP.getString("street","");
        city = localSP.getString("city","");
        mobile = localSP.getString("mobile","");

        mEtMobileNo.setText(mobile);
        mEtStreet.setText(street);
        mEtCity.setText(city);
        mEtAddress.setText(address);
    }

    private void getVehicleAllTypes() {

        try {
            EndPoints apiService = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<VehicleTypeModel> call_customer = apiService.getVehicleTypes(VLF_BASE_URL + "vehicle-types");
            call_customer.enqueue(new Callback<VehicleTypeModel>() {
                @Override
                public void onResponse(Call<VehicleTypeModel> call, Response<VehicleTypeModel> response) {

                    if (response.code() == 200) {

                        VehicleTypeModel vehicleTypeModel = response.body();
                        ArrayList<vehicleTypes> vehicleTypesArrayList = vehicleTypeModel.getVehicleTypes();

                        for (vehicleTypes Ds : vehicleTypesArrayList) {

                            String description = Ds.getName();
                            mSelectVehicleType.add(description);
                        }

                        mSelectVehicleType.add(0,"Select Vehicle type");
                        ArrayAdapter<String> dataAdapter_type =
                                new ArrayAdapter<String>(DonaterRequestActivity.this
                                        , android.R.layout.simple_spinner_item, mSelectVehicleType);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpSuggestVehicle.setAdapter(dataAdapter_type);
                    }
                }

                @Override
                public void onFailure(Call<VehicleTypeModel> call, Throwable t) {

                    Toast.makeText(DonaterRequestActivity.this, "get districts", Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception hh) {
        }
    }

    private void getWeightRange() {
        try {
            EndPoints endPoints = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<WeightRangeModel> call = endPoints.getWeightsList(VLF_BASE_URL + "weights");
            call.enqueue(new Callback<WeightRangeModel>() {
                @Override
                public void onResponse(Call<WeightRangeModel> call, Response<WeightRangeModel> response) {

                    if (response.code()==200){

                        mWeightRangeModel = response.body();
                        mWeights = mWeightRangeModel.getWeights();
                        String weight ="";
                        for (weights wl : mWeights){

                            weight = wl.getRange().toString().trim();
                            weightList.add(weight);
                        }

                        weightList.add(0,"Select Weight Range");
                        ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(DonaterRequestActivity.this, android.R.layout.simple_spinner_item, weightList);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpWeight.setAdapter(dataAdapter_type);
                    }
                }

                @Override
                public void onFailure(Call<WeightRangeModel> call, Throwable t) {

                }
            });

        }catch (Exception hh){}
    }

    private void getDonationTypes() {
        try {
            EndPoints endPoints = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<DonationTypeModel> call = endPoints.getTypes(VLF_BASE_URL + "donation-types");
            call.enqueue(new Callback<DonationTypeModel>() {
                @Override
                public void onResponse(Call<DonationTypeModel> call, Response<DonationTypeModel> response) {

                    if (response.code()==200){

                        mDonationTypeModel = response.body();
                        mDonationTypes = mDonationTypeModel.getDonationTypes();
                        String name="";
                        for (donationTypes dt : mDonationTypes){

                            name = dt.getName().toString().trim();
                            mTypes.add(name);
                        }
                        mTypes.add(0,"Select Category");
                        ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(DonaterRequestActivity.this, android.R.layout.simple_spinner_item, mTypes);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpCategoryType.setAdapter(dataAdapter_type);
                    }
                }

                @Override
                public void onFailure(Call<DonationTypeModel> call, Throwable t) {
                }
            });
        }catch (Exception h){}
    }

    private void initView() {
        mSpCategoryType = findViewById(R.id.activity_donater_request_sp_category);
        mSpWeight = findViewById(R.id.activity_donater_request_sp_weight);
        mSpSuggestVehicle = findViewById(R.id.activity_donater_request_sp_suggest_vehicle);
        mEtAddress = findViewById(R.id.activity_donater_request_et_address);
        mEtStreet = findViewById(R.id.activity_donater_request_et_street);
        mEtCity = findViewById(R.id.activity_donater_request_et_city);
        mEtMobileNo = findViewById(R.id.activity_donation_request_et_mobile_no);
        mBtnDonated = findViewById(R.id.activity_donation_request_btn_donated);
        mBtnDonated.setOnClickListener(this);
    }

    private ArrayList<donationCreate> getDonaterRequest() {

        donationCreate dc = new donationCreate();
        ArrayList<donationCreate> donationCreateArrayList = new ArrayList<>();

        if (validation()) {

            int type = mSpCategoryType.getSelectedItemPosition();
            int weight = mSpWeight.getSelectedItemPosition();
            int suggest_vehicle = mSpSuggestVehicle.getSelectedItemPosition();
            String address = mEtAddress.getText().toString().trim();
            String street = mEtStreet.getText().toString().trim();
            String city = mEtCity.getText().toString().trim();
            String mobile = mEtMobileNo.getText().toString().trim();
            double lng = GPS_Longitude;
            double lat = GPS_Latitude;

            dc.setDonation_type_id(type);
            dc.setWeight_id(weight);
            dc.setVehicle_type_id(suggest_vehicle);
            dc.setAdd_line_1(address);
            dc.setAdd_line_2(street);
            dc.setAdd_line_3(city);
            dc.setPhone(mobile);
            dc.setLatitude(lat);
            dc.setLongitude(lng);
            dc.setDonater_id(Integer.parseInt(ID));
            donationCreateArrayList.add(dc);

        }

        return donationCreateArrayList;
    }

    private void uploadRequest() {

        try {
            String Json_Body = new Gson().toJson(getDonaterRequest());
            JSONArray jsonArray = new JSONArray(Json_Body);
            JSONObject jsonObject = new JSONObject();
            if (jsonArray.length() > 0) {
                jsonObject = jsonArray.getJSONObject(0);
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
            EndPoints apiService = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<DonationRequestModel> call_customer = apiService.sendDonation(VLF_BASE_URL + "donation", body);
            call_customer.enqueue(new Callback<DonationRequestModel>() {
                @Override
                public void onResponse(Call<DonationRequestModel> call, Response<DonationRequestModel> response) {

                    if (response.code() == 200) {

                        mDonationRequestModel = response.body();
                        mDonationCreates = mDonationRequestModel.getDonationCreate();
                        message = mDonationRequestModel.getMessage();
                        Toast.makeText(DonaterRequestActivity.this, ""+message, Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(DonaterRequestActivity.this,DonaterHomeActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<DonationRequestModel> call, Throwable t) {

                }
            });

        } catch (Exception hh) {
        }
    }

    private boolean validation() {

        final String Address = mEtAddress.getText().toString();
        final String Street = mEtStreet.getText().toString();
        final String City = mEtCity.getText().toString();
        final String Mobile_No = mEtMobileNo.getText().toString();

        if (Address.length() == 0) {
            mEtAddress.requestFocus();
            mEtAddress.setError("Address cannot be blank");
            return false;
        } else if (Street.length() == 0) {
            mEtStreet.requestFocus();
            mEtStreet.setError("Street cannot be blank");
            return false;
        } else if (City.length() == 0) {
            mEtCity.requestFocus();
            mEtCity.setError("City cannot be blank");
            return false;
        } else if (Mobile_No.length() < 10) {
            mEtMobileNo.requestFocus();
            mEtMobileNo.setError("invalid mobile number");
            return false;
        } else {

        }

        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_donation_request_btn_donated:
                final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        myPd_ring.dismiss();
                        uploadRequest();
                        // show popup
                    }
                }, 3000);

                break;
            default:
                break;
        }
    }
}
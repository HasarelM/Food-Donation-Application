package com.dev.hasarelm.wastefooddonation.Activity.Rider;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterRegisterActivity;
import com.dev.hasarelm.wastefooddonation.Activity.LoginActivity;
import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.Common.CommonFunction;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Common.SharedPref;
import com.dev.hasarelm.wastefooddonation.Model.DistrictsModel;
import com.dev.hasarelm.wastefooddonation.Model.RiderRegisterModel;
import com.dev.hasarelm.wastefooddonation.Model.VehicleTypeModel;
import com.dev.hasarelm.wastefooddonation.Model.districts;
import com.dev.hasarelm.wastefooddonation.Model.register;
import com.dev.hasarelm.wastefooddonation.Model.vehicleTypes;
import com.dev.hasarelm.wastefooddonation.R;
import com.google.gson.Gson;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class RiderRegisterActivity extends BaseActivity implements View.OnClickListener {

    //Ui components
    private EditText mEtFname, mEtLname, mEtVehicleNo, mEtMobileNo, mEtPassword, mEtCpassword,mEtEmail;
    private SearchableSpinner mSpVehicleType, mSpDistrict;
    private Button mBtnRegister;
    private TextView mTvBackArrow;

    private ArrayList<register> mRegister;
    private List<String> mSelectDistrict = new ArrayList<String>();
    private List<String> mSelectVehicleType = new ArrayList<String>();
    private RiderRegisterModel mRegisterModel;
    private  String message;
    String defaultTextForSpinner = "Your deafult text here";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_register);

        setToolbar(getResources().getString(R.string.rider_register_activity_title), RiderRegisterActivity.this);

        initView();

        final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                myPd_ring.dismiss();
                // show popup
            }
        }, 3000);
        getVehicleTypes();
        getDistricts();
    }

    private void getDistricts() {

        try {

            EndPoints endPoints = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<DistrictsModel> call = endPoints.getDistrict(VLF_BASE_URL + "districts");
            call.enqueue(new Callback<DistrictsModel>() {
                @Override
                public void onResponse(Call<DistrictsModel> call, Response<DistrictsModel> response) {
                    if (response.code() == 200) {

                        DistrictsModel districtsModel = response.body();
                        ArrayList<districts> districtsArrayList = districtsModel.getDistricts();

                        for (districts DS : districtsArrayList)//loda all district
                        {
                            String Description = DS.getName();
                            mSelectDistrict.add(Description);
                        }

                        mSelectDistrict.add(0,"Select District");
                        ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(RiderRegisterActivity.this, android.R.layout.simple_spinner_item, mSelectDistrict);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpDistrict.setAdapter(dataAdapter_type);
                    }
                }

                @Override
                public void onFailure(Call<DistrictsModel> call, Throwable t) {

                }
            });

        } catch (Exception ff) {
        }

    }

    private void getVehicleTypes() {

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
                                new ArrayAdapter<String>(RiderRegisterActivity.this
                                        , android.R.layout.simple_spinner_item, mSelectVehicleType);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpVehicleType.setAdapter(dataAdapter_type);
                    }
                }

                @Override
                public void onFailure(Call<VehicleTypeModel> call, Throwable t) {

                    Toast.makeText(RiderRegisterActivity.this, "get districts", Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception hh) {
        }

    }

    private void initView() {
        mEtFname = findViewById(R.id.activity_rider_register_et_fName);
        mEtLname = findViewById(R.id.activity_rider_register_et_lName);
        mEtVehicleNo = findViewById(R.id.activity_rider_register_et_vehicle_no);
        mEtMobileNo = findViewById(R.id.activity_rider_register_et_mobile_no);
        mEtPassword = findViewById(R.id.activity_rider_register_et_password);
        mEtCpassword = findViewById(R.id.activity_rider_register_et_confirm_password);
        mBtnRegister = findViewById(R.id.activity_rider_register_btn_register);
        mSpVehicleType = findViewById(R.id.activity_rider_register_sp_vehicle_type);
        mEtEmail = findViewById(R.id.activity_rider_register_et_email);
        mSpDistrict = findViewById(R.id.activity_rider_register_sp_district);
        mTvBackArrow = findViewById(R.id.txt_back_arrow);
        mTvBackArrow.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_rider_register_btn_register:
                uploadRiderRegisterDetails();
                break;
            case R.id.txt_back_arrow:
                Intent intent = new Intent(RiderRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            default:
                break;
        }
    }

    private ArrayList<register> userRegister() {

        ArrayList<register> riderRegisterModels = new ArrayList<>();
        register obj = new register();
        ArrayList<register> objLst = new ArrayList<>();

        if (validation()) {

            String email = mEtEmail.getText().toString();
            String name = mEtFname.getText().toString();
            String lName = mEtLname.getText().toString();
            String phone = mEtMobileNo.getText().toString();
            String vehicle_no = mEtVehicleNo.getText().toString();
            String c_password = mEtCpassword.getText().toString();
            String password = mEtCpassword.getText().toString();
            String vehicle_type = mSpVehicleType.getSelectedItemPosition()+ "";
            String district_type = mSpDistrict.getSelectedItemPosition()+ "";

            obj.setEmail(email+"");
            obj.setName(name+"");
            obj.setLast_name(lName+"");
            obj.setPhone(phone+"");
            obj.setVehicle_no(vehicle_no+"");
            obj.setPassword(password+"");
            obj.setVehicleType(vehicle_type+"");
            obj.setDistrict_id(district_type+"");
            obj.setPassword_confirmation(c_password+"");
            obj.setType("1");
            objLst.add(obj);
        }
        return objLst;
    }

    private void uploadRiderRegisterDetails() {

        final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);
        try {

            String Json_Body = new Gson().toJson(userRegister());
            JSONArray jsonArray = new JSONArray(Json_Body);
            JSONObject jsonObject = new JSONObject();
            if (jsonArray.length()>0){
               jsonObject = jsonArray.getJSONObject(0);
            }
            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
            EndPoints apiService = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<RiderRegisterModel> call_customer = apiService.riderRegister(VLF_BASE_URL + "register", body);
            call_customer.enqueue(new Callback<RiderRegisterModel>() {
                @Override
                public void onResponse(Call<RiderRegisterModel> call, Response<RiderRegisterModel> response) {

                    String userName = "";
                    String ID = "";

                    if (response.code() == 200) {
                        myPd_ring.dismiss();
                        Toast.makeText(RiderRegisterActivity.this, "Rider "+message,Toast.LENGTH_LONG).show();
                        message = response.body().getMessage();
                        mRegisterModel = response.body();
                        mRegister = response.body().getRegister();

                        for (register rs : mRegister){

                            userName = rs.getEmail().toString().trim();
                            ID = rs.getId().toString().trim();
                        }
                        SharedPref.setLocalSharedPreference(RiderRegisterActivity.this,"R_LOGIN_USER_NAME",userName);
                        SharedPref.setLocalSharedPreference(RiderRegisterActivity.this,"R_LOGIN_USER_ID",ID);

                        Intent intent = new Intent(RiderRegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(Call<RiderRegisterModel> call, Throwable t) {
                    myPd_ring.dismiss();
                    Toast.makeText(RiderRegisterActivity.this, ""+message,Toast.LENGTH_LONG).show();
                }
            });

        } catch (Exception gg) {
        }
    }

    private boolean validation() {
        final String NameF = mEtFname.getText().toString();
        final String NameL = mEtLname.getText().toString();
        final String contact = mEtMobileNo.getText().toString();
        final String vehicle_no = mEtVehicleNo.getText().toString();
        String password = mEtPassword.getText().toString();
        String c_password = mEtCpassword.getText().toString();

        if (NameF.length() == 0) {
            mEtFname.requestFocus();
            mEtFname.setError("First name cannot be blank");
            return false;
        } else if (!NameF.matches("[a-zA-Z ]+")) {
            mEtFname.requestFocus();
            mEtFname.setError("enter only alphabetical character");
            return false;
        } else if (NameL.length() == 0) {
            mEtLname.requestFocus();
            mEtLname.setError("Last name cannot be blank");
            return false;
        } else if (!NameL.matches("[a-zA-Z ]+")) {
            mEtLname.requestFocus();
            mEtLname.setError("enter only alphabetical character");
            return false;
        } else if (contact.length() < 10) {
            mEtMobileNo.requestFocus();
            mEtMobileNo.setError("invalid mobile number");
            return false;
        } else if (vehicle_no.length() == 0) {
            mEtVehicleNo.requestFocus();
            mEtVehicleNo.setError("Vehicle no cannot be blank");
            return false;
        } else if (password.length() < 7) {
            mEtPassword.requestFocus();
            mEtPassword.setError("Password should be more than 7");
            return false;
        } else if (c_password.length() <7) {
            mEtCpassword.requestFocus();
            mEtCpassword.setError("C_Password should be more than 7");
            return false;
        } else if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(c_password)) {
            if (password.equals(c_password)) {

            } else {
                mEtCpassword.requestFocus();
                mEtCpassword.setError("Passwords do not match");
                return false;
            }
        } else {

        }

        return true;
    }
}
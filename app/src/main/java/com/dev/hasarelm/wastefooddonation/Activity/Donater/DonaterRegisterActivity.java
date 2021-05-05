package com.dev.hasarelm.wastefooddonation.Activity.Donater;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.hasarelm.wastefooddonation.Activity.LoginActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Rider.RiderRegisterActivity;
import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Common.SharedPref;
import com.dev.hasarelm.wastefooddonation.Model.DistrictsModel;
import com.dev.hasarelm.wastefooddonation.Model.DonaterRegisterModel;
import com.dev.hasarelm.wastefooddonation.Model.RiderRegisterModel;
import com.dev.hasarelm.wastefooddonation.Model.districts;
import com.dev.hasarelm.wastefooddonation.Model.register;
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

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class DonaterRegisterActivity extends BaseActivity implements View.OnClickListener {

    //Ui components
    private EditText mEtFname,mEtLname,mEtAddress,mEtStreet,mEtCity,mEtEmail,mEtMobileNo,mEtPassword,mEtCpassword;
    private Button mBtnRegister;
    private TextView mTvBackArrow;
    private SearchableSpinner mSpDistrict;
    private TextView tv;

    private String message;
    private ArrayList<register> mRegister;
    private DonaterRegisterModel mDonaterRegisterModel;
    private List<String> mSelectDistrict = new ArrayList<String>();
    private List<String> mSelectVehicleType = new ArrayList<String>();
    private RiderRegisterModel mRegisterModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donater_register);

        setToolbar(getResources().getString(R.string.register_activity_title), DonaterRegisterActivity.this);

        initView();
        getDistricts();
    }

    private void initView() {
        mEtFname = findViewById(R.id.activity_register_et_fName);
        mEtLname = findViewById(R.id.activity_register_et_lName);
        mEtAddress = findViewById(R.id.activity_register_et_address);
        mEtCity = findViewById(R.id.activity_register_et_city);
        mEtStreet = findViewById(R.id.activity_register_et_street);
        mEtEmail = findViewById(R.id.activity_register_et_email);
        mEtMobileNo = findViewById(R.id.activity_register_et_mobile_no);
        mEtPassword = findViewById(R.id.activity_register_et_password);
        mEtCpassword = findViewById(R.id.activity_register_et_confirm_password);
        mBtnRegister = findViewById(R.id.activity_register_btn_register);
        mTvBackArrow = findViewById(R.id.txt_back_arrow_dont);
        mSpDistrict = findViewById(R.id.activity_donater_register_sp_district);
        mTvBackArrow.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
    }

    private void getDistricts() {

        final ProgressDialog myPd_ring = ProgressDialog.show(this, "Please wait", "", true);
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
                        ArrayAdapter<String> dataAdapter_type = new ArrayAdapter<String>(DonaterRegisterActivity.this, android.R.layout.simple_spinner_item, mSelectDistrict);
                        dataAdapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        mSpDistrict.setAdapter(dataAdapter_type);

                        myPd_ring.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<DistrictsModel> call, Throwable t) {

                    myPd_ring.dismiss();
                }
            });

        } catch (Exception ff) {
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.activity_register_btn_register:
                donaterUserRegister();
                break;
            case R.id.txt_back_arrow_dont:
                Intent intent = new Intent(DonaterRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
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
            String address = mEtAddress.getText().toString();
            String street = mEtStreet.getText().toString();
            String city = mEtCity.getText().toString();
            String phone = mEtMobileNo.getText().toString();
            String c_password = mEtCpassword.getText().toString();
            String password = mEtCpassword.getText().toString();
            String district_type = mSpDistrict.getSelectedItemPosition()+ "";

            obj.setName(name+"");
            obj.setLast_name(lName+"");
            obj.setAdd_line_1(address+"");
            obj.setAdd_line_2(street+"");
            obj.setAdd_line_3(city+"");
            obj.setPhone(phone+"");
            obj.setEmail(email+"");
            obj.setDistrict_id(district_type+"");
            obj.setPassword(password+"");
            obj.setPassword_confirmation(c_password+"");
            obj.setType("2");
            objLst.add(obj);
        }
        return objLst;
    }

    private void donaterUserRegister() {

        if (validation()){

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
                Call<DonaterRegisterModel> call_customer = apiService.donetorRegister(VLF_BASE_URL + "register", body);
                call_customer.enqueue(new Callback<DonaterRegisterModel>() {
                    @Override
                    public void onResponse(Call<DonaterRegisterModel> call, Response<DonaterRegisterModel> response) {

                        String userName = "";
                        String ID = "";
                        if (response.code() == 200) {
                            myPd_ring.dismiss();
                            Toast.makeText(DonaterRegisterActivity.this, "Doneter Registered ",Toast.LENGTH_LONG).show();
                            message = response.body().getMessage();
                            mDonaterRegisterModel = response.body();
                            mRegister = mDonaterRegisterModel.getRegister();
                            mRegister = response.body().getRegister();

                            for (register rs : mRegister){

                                userName = rs.getEmail().toString().trim();
                                ID = rs.getId().toString().trim();
                            }

                            SharedPref.setLocalSharedPreference(DonaterRegisterActivity.this,"D_LOGIN_USER_NAME",userName);
                            SharedPref.setLocalSharedPreference(DonaterRegisterActivity.this,"D_LOGIN_USER_ID",ID);
                            Intent intent = new Intent(DonaterRegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<DonaterRegisterModel> call, Throwable t) {
                        myPd_ring.dismiss();
                        Toast.makeText(DonaterRegisterActivity.this, "Doneter Registered",Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception gg) {
            }
        }

    }

    private boolean validation() {
        final String NameF = mEtFname.getText().toString();
        final String NameL = mEtLname.getText().toString();
        final String contact = mEtMobileNo.getText().toString();
        final String address = mEtAddress.getText().toString();
        final String street = mEtStreet.getText().toString();
        final String city = mEtCity.getText().toString();
        final String email = mEtEmail.getText().toString();
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
        }else if (NameL.length() == 0) {
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
        } else if (address.length() == 0) {
            mEtAddress.requestFocus();
            mEtAddress.setError("Address cannot be blank");
            return false;
        }else if (street.length() == 0) {
            mEtStreet.requestFocus();
            mEtStreet.setError("Street cannot be blank");
            return false;
        }else if (city.length() == 0) {
            mEtCity.requestFocus();
            mEtCity.setError("City cannot be blank");
            return false;
        } else if (email.length() == 0) {
            mEtEmail.requestFocus();
            mEtEmail.setError("Email cannot be blank");
            return false;
        } else if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")) {
            mEtEmail.requestFocus();
            mEtEmail.setError("invalid email address");
            return false;
        } else if (password.length() <7) {
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
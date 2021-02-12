package com.dev.hasarelm.wastefooddonation.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterHomeActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterRegisterActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Rider.RiderHomeActivity;
import com.dev.hasarelm.wastefooddonation.Activity.Rider.RiderRegisterActivity;
import com.dev.hasarelm.wastefooddonation.Common.CommonFunction;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Model.LoginUserModel;
import com.dev.hasarelm.wastefooddonation.Model.RiderRegisterModel;
import com.dev.hasarelm.wastefooddonation.Model.login;
import com.dev.hasarelm.wastefooddonation.Model.register;
import com.dev.hasarelm.wastefooddonation.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;
import static com.dev.hasarelm.wastefooddonation.Common.CommonFunction.CustomTost;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    // constants
    private static final String TAG = LoginActivity.class.getSimpleName();

    //Ui Components
    private EditText mEtUsername, mEtPassword;
    private Button mBtnLogin, mBtnRegister;
    private TextView mTvForgetPassword;
    private RadioButton mRbnDonator, mRbnDelivery;

    private ArrayList<login> mLogin;
    private LoginUserModel mLoginUserModel;
    private String message;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    public static final int PERMISSIONS_MULTIPLE_REQUEST = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mRbnDonator.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked == true) {
                mRbnDelivery.setChecked(false);
            }
        });
        mRbnDelivery.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked == true) {
                mRbnDonator.setChecked(false);
            }
        });
    }

    private void initView() {

        mEtUsername = findViewById(R.id.activity_login_et_username);
        mEtPassword = findViewById(R.id.activity_login_et_password);
        mBtnLogin = findViewById(R.id.activity_login_btn_login);
        mTvForgetPassword = findViewById(R.id.activity_login_tv_forgetPassword);
        mBtnRegister = findViewById(R.id.activity_login_btn_register);
        mRbnDelivery = findViewById(R.id.activity_login_rbn_delivery);
        mRbnDonator = findViewById(R.id.activity_login_rbn_donater);
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mTvForgetPassword.setOnClickListener(this);

        checkPermission();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.activity_login_btn_login:
                /*  Intent intent = new Intent(LoginActivity.this, DonaterHomeActivity.class);
        startActivity(intent);*/
                userLogin();
                break;
            case R.id.activity_login_tv_forgetPassword:
                forgetPassword();
                break;
            case R.id.activity_login_btn_register:
                userRegister();
                break;
            default:
                break;
        }
    }

    private void userRegister() {

        if (CommonFunction.checkNetworkConnection(LoginActivity.this)) {
            if (mRbnDelivery.isChecked() == true || mRbnDonator.isChecked() == true) {
                int FLG = 0;
                if (mRbnDonator.isChecked() == true) {
                    FLG = 1;//buyer
                    Intent intent = new Intent(LoginActivity.this, DonaterRegisterActivity.class);
                    intent.putExtra("NEW_USER_TYPE", FLG);
                    startActivity(intent);
                    finish();
                } else if (mRbnDelivery.isChecked() == true) {
                    FLG = 2;//Delivery_person
                    Intent intent = new Intent(LoginActivity.this, RiderRegisterActivity.class);
                    intent.putExtra("NEW_USER_TYPE", FLG);
                    startActivity(intent);
                    finish();
                }

            } else {
                CustomTost(LoginActivity.this, "Please select Register type");
            }
        } else {

            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet Connection")
                    .setContentText("Please turn mobile data on or connect to wifi to proceed!")
                    .show();

        }


       /* Intent intent = new Intent(LoginActivity.this, DonaterRegisterActivity.class);
        startActivity(intent);*/
    }

    private void forgetPassword() {

        Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
        startActivity(intent);
    }


    private ArrayList<register> userLoginToApp() {

        ArrayList<register> riderRegisterModels = new ArrayList<>();
        register obj = new register();
        ArrayList<register> objLst = new ArrayList<>();

        String uName = mEtUsername.getText().toString();
        String password = mEtPassword.getText().toString();
        obj.setEmail(uName + "");
        obj.setPassword(password + "");
        objLst.add(obj);

        return objLst;
    }

    private void userLogin() {

        if (CommonFunction.checkNetworkConnection(LoginActivity.this)) {

            if (mEtUsername.getText().toString().length() > 0) {

                if (mEtPassword.getText().toString().length()>0){

                    if (emailVerification(mEtUsername.getText().toString()) == 1) {

                        final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);

                        try {
                            String Json_Body = new Gson().toJson(userLoginToApp());
                            JSONArray jsonArray = new JSONArray(Json_Body);
                            JSONObject jsonObject = new JSONObject();
                            if (jsonArray.length() > 0) {
                                jsonObject = jsonArray.getJSONObject(0);
                            }
                            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());
                            EndPoints apiService = RetrofitClient.getLoginClient().create(EndPoints.class);
                            Call<LoginUserModel> call_customer = apiService.loginUser(VLF_BASE_URL + "login", body);
                            call_customer.enqueue(new Callback<LoginUserModel>() {
                                @Override
                                public void onResponse(Call<LoginUserModel> call, Response<LoginUserModel> response) {

                                    if (response.code() == 200) {

                                        myPd_ring.dismiss();
                                        int type = 0;
                                        message = response.body().getMessage();
                                        mLoginUserModel = response.body();
                                        mLogin = response.body().getLogin();

                                        for (login ls : mLogin) {

                                            type = Integer.parseInt(ls.getType());

                                            if (type == 1) {

                                                CustomTost(LoginActivity.this, "Successfully");
                                                Intent intent = new Intent(LoginActivity.this, RiderHomeActivity.class);
                                                startActivity(intent);
                                            } else if (type == 2) {

                                                CustomTost(LoginActivity.this, "Successfully");
                                                Intent intent = new Intent(LoginActivity.this, DonaterHomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginUserModel> call, Throwable t) {

                                    myPd_ring.dismiss();
                                    CustomTost(LoginActivity.this, "" + message);
                                }
                            });

                        } catch (Exception g) {
                        }

                    /*if (mRbnDonator.isChecked()) {
                        //login api call here
                        //type 1 = donater
                        try {
                            String Json_Body = new Gson().toJson(userLoginToApp());
                            JSONArray jsonArray = new JSONArray(Json_Body);
                            JSONObject jsonObject = new JSONObject();
                            if (jsonArray.length()>0){
                                jsonObject = jsonArray.getJSONObject(0);
                            }
                            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
                            EndPoints apiService = RetrofitClient.getLoginClient().create(EndPoints.class);
                            Call<LoginUserModel> call_customer = apiService.loginUser(VLF_BASE_URL + "login", body);
                            call_customer.enqueue(new Callback<LoginUserModel>() {
                                @Override
                                public void onResponse(Call<LoginUserModel> call, Response<LoginUserModel> response) {

                                    if (response.code()==200){

                                        int type =0;
                                        message = response.body().getMessage();
                                        mLoginUserModel = response.body();
                                        mLogin = response.body().getLogin();

                                        for (login ls : mLogin){

                                            type = Integer.parseInt(ls.getType());

                                            if (type==1){

                                                CustomTost(LoginActivity.this, ""+message);
                                                Intent intent = new Intent(LoginActivity.this, DonaterHomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginUserModel> call, Throwable t) {

                                    CustomTost(LoginActivity.this, ""+message);
                                }
                            });

                        }catch (Exception g){}

                    } else if (mRbnDelivery.isChecked()) {
                        //login api call here
                        //type 2 for rider
                        try {
                            String Json_Body = new Gson().toJson(userLoginToApp());
                            JSONArray jsonArray = new JSONArray(Json_Body);
                            JSONObject jsonObject = new JSONObject();
                            if (jsonArray.length()>0){
                                jsonObject = jsonArray.getJSONObject(0);
                            }
                            RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),jsonObject.toString());
                            EndPoints apiService = RetrofitClient.getLoginClient().create(EndPoints.class);
                            Call<LoginUserModel> call_customer = apiService.loginUser(VLF_BASE_URL + "login", body);
                            call_customer.enqueue(new Callback<LoginUserModel>() {
                                @Override
                                public void onResponse(Call<LoginUserModel> call, Response<LoginUserModel> response) {

                                    if (response.code()==200){

                                        int type =0;
                                        message = response.body().getMessage();
                                        mLoginUserModel = response.body();
                                        mLogin = response.body().getLogin();

                                        for (login ls : mLogin){

                                            type = Integer.parseInt(ls.getType());

                                            if (type==2){

                                                CustomTost(LoginActivity.this, ""+message);
                                                Intent intent = new Intent(LoginActivity.this, RiderHomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginUserModel> call, Throwable t) {

                                    CustomTost(LoginActivity.this, ""+message);
                                }
                            });

                        }catch (Exception g){}*/


                    } else {
                        CustomTost(LoginActivity.this, "Please select login type");
                    }

                }else {

                    Toast.makeText(this,"User name cannot be blank",Toast.LENGTH_LONG).show();
                }

            }else {

                Toast.makeText(this,"Password cannot be blank",Toast.LENGTH_LONG).show();
            }
        }else{

            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("No Internet Connection")
                    .setContentText("Please turn mobile data on or connect to wifi to proceed!")
                    .show();

        }
    }

    public int emailVerification(String email) {

        int result = 0;
        if (email.length() > 0) {
            if (email.matches(emailPattern)) {
                result = 1;
            }
        } else if (email.length() == 0) {
            result = 0;
        }
        return result;

    }

    private void checkPermission() {
        try {
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    + ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    + ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    + ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE)
                    + ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                    + ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
                    + ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CALL_PHONE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_PHONE_STATE) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_SMS)) {

                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(
                                new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                        android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        android.Manifest.permission.CALL_PHONE,
                                        android.Manifest.permission.CAMERA,
                                        android.Manifest.permission.READ_PHONE_STATE,
                                        android.Manifest.permission.READ_SMS
                                },
                                PERMISSIONS_MULTIPLE_REQUEST);
                    }

                }
            } else {
                // write your logic code if permission already granted
            }
        }catch (IllegalArgumentException ee)
        {
            CustomTost(LoginActivity.this,ee.toString());
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_MULTIPLE_REQUEST:
                if (grantResults.length > 0) {

                    boolean readExternalFile = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean coarselocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean finelocationPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalPermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean Call_phone = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraPermission = grantResults[5] == PackageManager.PERMISSION_GRANTED;
                    boolean number = grantResults[6] == PackageManager.PERMISSION_GRANTED;
                    boolean sms = grantResults[7] == PackageManager.PERMISSION_GRANTED;



                    if (cameraPermission && readExternalFile
                            && coarselocationPermission
                            && finelocationPermission
                            && writeExternalPermission
                            && Call_phone && sms & number) {
                        // write your logic here

                    } else {
                        //CustomTost(LoginActivity.this,"Please grant all permission");
                        //checkPermission();

                    }

                }
                break;
            case 100:
                break;
        }
    }
}
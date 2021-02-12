package com.dev.hasarelm.wastefooddonation.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterRegisterActivity;
import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Model.DonaterRegisterModel;
import com.dev.hasarelm.wastefooddonation.Model.ForgetPasswordModel;
import com.dev.hasarelm.wastefooddonation.Model.passwordResetEmail;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    //Ui components
    private EditText mEtEmail;
    private Button mBtnReset;

    private String message;
    private ArrayList<ForgetPasswordModel> mForgetPasswordModels;
    private ArrayList<passwordResetEmail> mPasswordResetEmails;

    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        setToolbar(getResources().getString(R.string.forget_password_activity_title), ForgetPasswordActivity.this);
        initView();
    }

    private void initView() {

        mEtEmail = findViewById(R.id.activity_forget_password_et_change_password);
        mBtnReset = findViewById(R.id.activity_forget_password_btn_reset_password);
        mBtnReset.setOnClickListener(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.activity_forget_password_btn_reset_password:
                if (emailVerification(mEtEmail.getText().toString())==1){

                    final ProgressDialog myPd_ring=ProgressDialog.show(this, "Please wait", "", true);
                    String email = mEtEmail.getText().toString();

                    EndPoints apiService = RetrofitClient.getLoginClient().create(EndPoints.class);
                    Call<ForgetPasswordModel> call_customer = apiService.forgetPassword(VLF_BASE_URL + "password/email?", email);
                    call_customer.enqueue(new Callback<ForgetPasswordModel>() {
                        @Override
                        public void onResponse(Call<ForgetPasswordModel> call, Response<ForgetPasswordModel> response) {

                            if (response.code()==200){
                                myPd_ring.dismiss();
                                message = response.body().getMessage();

                                if (message.equals("A password reset email will be sent to you in a moment.")){

                                    new SweetAlertDialog(ForgetPasswordActivity.this, SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("Password Rest!")
                                            .setContentText("A password reset email will be sent to you in a moment.")
                                            .setConfirmText("Ok!")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.dismiss();
                                                }
                                            })
                                            .show();
                                   /* Toast.makeText(ForgetPasswordActivity.this, ""+message,Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                    startActivity(intent);*/

                                }else {

                                    //dialog box popup
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ForgetPasswordModel> call, Throwable t) {

                        }
                    });

                }
                break;
            default:
                break;
        }
    }
}
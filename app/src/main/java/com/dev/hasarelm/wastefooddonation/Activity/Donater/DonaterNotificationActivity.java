package com.dev.hasarelm.wastefooddonation.Activity.Donater;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.dev.hasarelm.wastefooddonation.Adapter.DonaterNotificationAdapter;
import com.dev.hasarelm.wastefooddonation.Adapter.DonaterRequestAdapter;
import com.dev.hasarelm.wastefooddonation.Common.BaseActivity;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Common.SharedPreferencesClass;
import com.dev.hasarelm.wastefooddonation.Interface.OnItemClickListener;
import com.dev.hasarelm.wastefooddonation.Model.NotificationModel;
import com.dev.hasarelm.wastefooddonation.Model.notifications;
import com.dev.hasarelm.wastefooddonation.R;

import java.security.PrivateKey;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class DonaterNotificationActivity extends BaseActivity implements OnItemClickListener<notifications> {

    //Ui components
    private RecyclerView mNotificationRecyclerView;
    private NotificationModel mNotificationModel;
    private ArrayList<notifications> mNotificationsArrayList;
    private DonaterNotificationAdapter mDonaterNotificationAdapter;


    public static SharedPreferences localSP;
    private String ID = "";
    private int userID=0;
    private int mClickID;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donater_notification);

        setToolbar(getResources().getString(R.string.notification_activity_title), DonaterNotificationActivity.this);
        setDrawer();
        initView();

        localSP = this.getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
        ID = localSP.getString("USER_ID","");
        userID = Integer.parseInt(ID);

        getAllNotifications(userID);

    }

    private void updateViewNotification(int mClickID) {

        try {
            EndPoints endPoints = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<NotificationModel> call = endPoints.updateClickNotification(VLF_BASE_URL+"notification?",mClickID);
            call.enqueue(new Callback<NotificationModel>() {
                @Override
                public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {

                    if (response.code()==200){

                        mNotificationModel = response.body();
                        message = mNotificationModel.getMessage();
                    }
                }

                @Override
                public void onFailure(Call<NotificationModel> call, Throwable t) {

                }
            });

        }catch (Exception h){}

    }

    private void getAllNotifications(int userID) {

        final ProgressDialog myPd_ring = ProgressDialog.show(this, "Please wait", "", true);
        try {
            EndPoints endPoints = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<NotificationModel> call = endPoints.getAllNotifications(VLF_BASE_URL+"notifications?",userID);
            call.enqueue(new Callback<NotificationModel>() {
                @Override
                public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {

                    if (response.code()==200){

                        myPd_ring.dismiss();

                        mNotificationModel = response.body();
                        mNotificationsArrayList = mNotificationModel.getNotifications();

                        if (mNotificationsArrayList.size()>0){

                           setupRecyclerView(mNotificationsArrayList);
                          //  myPd_ring.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<NotificationModel> call, Throwable t) {

                }
            });

        }catch (Exception h){}
    }

    private void setupRecyclerView(ArrayList<notifications> phoneCallsList) {
        mDonaterNotificationAdapter = new DonaterNotificationAdapter(DonaterNotificationActivity.this, phoneCallsList, this);
        mNotificationRecyclerView.setHasFixedSize(true);
        mNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(DonaterNotificationActivity.this));
        mNotificationRecyclerView.setAdapter(mDonaterNotificationAdapter);
    }

    private void initView() {

        mNotificationRecyclerView = findViewById(R.id.activity_donater_notification_rv_list);
    }

    @Override
    public void onItemClick(int position, notifications data) {

        mClickID = data.getId();

        updateViewNotification(mClickID);
    }
}
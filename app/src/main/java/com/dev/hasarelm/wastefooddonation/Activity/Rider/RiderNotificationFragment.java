package com.dev.hasarelm.wastefooddonation.Activity.Rider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Activity.Donater.DonaterNotificationActivity;
import com.dev.hasarelm.wastefooddonation.Adapter.DonaterNotificationAdapter;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Common.SharedPreferencesClass;
import com.dev.hasarelm.wastefooddonation.Interface.OnItemClickListener;
import com.dev.hasarelm.wastefooddonation.Model.NotificationModel;
import com.dev.hasarelm.wastefooddonation.Model.notifications;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class RiderNotificationFragment extends Fragment implements OnItemClickListener<notifications> {

    private RecyclerView mNotificationRecyclerView;
    private NotificationModel mNotificationModel;
    private ArrayList<notifications> mNotificationsArrayList;
    private DonaterNotificationAdapter mDonaterNotificationAdapter;

    public static SharedPreferences localSP;
    private String ID = "";
    private int userID=0;
    private int mClickID;
    private String message;
    private Activity activity;
    View rootView;

    public RiderNotificationFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.rider_notification_fragment, container, false);
        mNotificationRecyclerView = rootView.findViewById(R.id.activity_donater_notificationssss_rv_list);

        try {
            localSP = getContext().getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE+Context.MODE_PRIVATE);
            ID = localSP.getString("USER_ID","");
            userID = Integer.parseInt(ID);
        }catch (Exception f){}

        getAllNotifications();

        return rootView;
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

    private void getAllNotifications() {

        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);
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
        mDonaterNotificationAdapter = new DonaterNotificationAdapter(activity, phoneCallsList, this);
        mNotificationRecyclerView.setHasFixedSize(true);
        mNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mNotificationRecyclerView.setAdapter(mDonaterNotificationAdapter);
    }

    @Override
    public void onItemClick(int position, notifications data) {

        mClickID = data.getId();

        updateViewNotification(mClickID);
    }
}

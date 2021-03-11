package com.dev.hasarelm.wastefooddonation.Activity.Rider;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Activity.LoginActivity;
import com.dev.hasarelm.wastefooddonation.Adapter.RiderRequestAdapter;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.NotificationReceiver;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Common.SharedPreferencesClass;
import com.dev.hasarelm.wastefooddonation.Interface.OnDonationItemClickListner;
import com.dev.hasarelm.wastefooddonation.Interface.OnMoreInfoClickListner;
import com.dev.hasarelm.wastefooddonation.Interface.OnRiderCallClickLisner;
import com.dev.hasarelm.wastefooddonation.Model.DonationRequestListModel;
import com.dev.hasarelm.wastefooddonation.Model.donations;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.dev.hasarelm.wastefooddonation.Activity.LoginActivity.GPS_Latitude;
import static com.dev.hasarelm.wastefooddonation.Activity.LoginActivity.GPS_Longitude;
import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class RiderHomeFragment extends Fragment implements View.OnClickListener, OnDonationItemClickListner<donations>, OnMoreInfoClickListner<donations>
        , OnRiderCallClickLisner<donations> {

    private LinearLayout mTvBike, mTvCar, mTvVan, mTvTruck, mTvThreeWeel;
    private RecyclerView mRvDonationList;
    private ImageButton mImgLogOut;
    View rootView;
    private Activity activity;

    private int ID = 0;
    private DonationRequestListModel mDonationRequestListModel;
    private ArrayList<donations> mDonationsArrayList;
    private RiderRequestAdapter mRiderRequestAdapter;

    private String longitude;
    private String latitude;
    private String fName, lName, address, city, street, weight, creation_date, donation_type, phoneCall;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;

    public RiderHomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.rider_home_fragment, container, false);
        mTvBike = rootView.findViewById(R.id.rider_home_fragment_bike);
        mTvCar = rootView.findViewById(R.id.rider_home_fragment_car);
        mTvVan = rootView.findViewById(R.id.rider_home_fragment_van);
        mTvTruck = rootView.findViewById(R.id.rider_home_fragment_truck);
        mTvThreeWeel = rootView.findViewById(R.id.rider_home_fragment_threewell);
        mRvDonationList = rootView.findViewById(R.id.rider_home_fragment_rv_all_list);
        mTvThreeWeel.setOnClickListener(this);
        mTvBike.setOnClickListener(this);
        mTvCar.setOnClickListener(this);
        mTvVan.setOnClickListener(this);
        mTvTruck.setOnClickListener(this);


        mImgLogOut = rootView.findViewById(R.id.activity_rider_home_btn_logout);
        mImgLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Do you want to logout this application!")
                        .setConfirmText("Yes!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();

                                Intent intent = new Intent(getContext(), LoginActivity.class);
                                startActivity(intent);
                                SharedPreferencesClass.ClearSharedPreference(getContext(), "rider_user_name");

                            }
                        })
                        .setCancelButton("No", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        });

        addNotification();
        getDonationRequestList(ID);

        return rootView;
    }


    private void addNotification() {
        Intent snoozeIntent = new Intent(getContext(), RiderNotificationFragment. class ) ;
        PendingIntent snoozePendingIntent = PendingIntent. getBroadcast (getContext(), 0 , snoozeIntent , 0 ) ;
        NotificationManager mNotificationManager = ( NotificationManager ) getActivity().getSystemService( getActivity().NOTIFICATION_SERVICE );
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getContext(), default_notification_channel_id ) ;
        mBuilder.setContentTitle( "My Notification" ) ;
        mBuilder.setContentText( "Notification Listener Service Example" ) ;
        mBuilder.setTicker( "Notification Listener Service Example" ) ;
        mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        mBuilder.addAction(R.drawable. ic_launcher_foreground , "Snooze" , snoozePendingIntent) ;
        mBuilder.setAutoCancel( true ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
    }

    private void setupRecyclerView(ArrayList<donations> phoneCallsList) {
        mRiderRequestAdapter = new RiderRequestAdapter(activity, phoneCallsList, this, this, this);
        mRvDonationList.setHasFixedSize(true);
        mRvDonationList.setLayoutManager(new LinearLayoutManager(activity));
        mRvDonationList.setAdapter(mRiderRequestAdapter);
    }

    private void getDonationRequestList(int id) {

        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);
        try {

            EndPoints endPoints = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<DonationRequestListModel> call = endPoints.getAllDonationRequest(VLF_BASE_URL + "donations?", id);
            call.enqueue(new Callback<DonationRequestListModel>() {
                @Override
                public void onResponse(Call<DonationRequestListModel> call, Response<DonationRequestListModel> response) {

                    if (response.code() == 200) {

                        mDonationRequestListModel = response.body();
                        mDonationsArrayList = mDonationRequestListModel.getDonations();

                        setupRecyclerView(mDonationsArrayList);

                        myPd_ring.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<DonationRequestListModel> call, Throwable t) {

                    myPd_ring.dismiss();
                }
            });

        } catch (Exception f) {
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rider_home_fragment_bike:

                getDonationRequestList(1);
                break;
            case R.id.rider_home_fragment_car:
                getDonationRequestList(2);
                break;
            case R.id.rider_home_fragment_threewell:
                getDonationRequestList(3);
                break;
            case R.id.rider_home_fragment_truck:
                getDonationRequestList(5);
                break;
            case R.id.rider_home_fragment_van:
                getDonationRequestList(4);
                break;
            default:
                break;
        }
    }

    @Override
    public void onDonationClick(int position, donations data) {

        double lng = GPS_Longitude;
        double lat = GPS_Latitude;

        try {

            longitude = data.getLongitude() + "";
            latitude = data.getLatitude() + "";

            String uri = String.format("http://maps.google.com/maps?daddr=" + latitude + "," + longitude);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);

        } catch (Exception h) {
        }

    }

    @Override
    public void onCallClick(int position, donations data) {

        try {

            phoneCall = data.getPhone().toString().trim();
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneCall));
            startActivity(callIntent);

        } catch (Exception g) {
        }

    }

    @Override
    public void onMoreInfoClick(int position, donations data) {

        try {

            fName = data.getDonator_first_name().toString().trim();
            lName = data.getDonator_last_name().toString().trim();
            address = data.getAdd_line_1().toString().trim();
            city = data.getAdd_line_3().toString().trim();
            street = data.getAdd_line_2().toString().trim();
            weight = data.getWeight().toString().trim();
            creation_date = data.getCreated_at().toString().trim();
            donation_type = data.getDonation_type().toString().trim();

            viewFullRequestDetails(fName, lName, address, city, street, weight, creation_date, donation_type);

        } catch (Exception h) {

        }

    }

    private void viewFullRequestDetails(String fName, String lName, String address, String city, String street, String weight, String creation_date, String donation_type) {
        try {

            Dialog dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.rider_more_info_layout);
            dialog.setTitle("Title");

            TextView fname = dialog.findViewById(R.id.rider_info_more_fname);
            TextView lname = dialog.findViewById(R.id.rider_info_more_lname);
            TextView add = dialog.findViewById(R.id.rider_info_more_address);
            TextView cty = dialog.findViewById(R.id.rider_info_more_city);
            TextView srt = dialog.findViewById(R.id.rider_info_more_street);
            TextView wig = dialog.findViewById(R.id.rider_info_more_weight);
            TextView cd = dialog.findViewById(R.id.rider_info_more_date);
            TextView type = dialog.findViewById(R.id.rider_info_more_category);
            Button mBtnClose = dialog.findViewById(R.id.rider_info_more_btn_close);


            mBtnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });

            StringTokenizer tokens = new StringTokenizer(creation_date, "T");
            String first = tokens.nextToken();
            String second = tokens.nextToken();
            StringTokenizer token = new StringTokenizer(second, "T");
            String third = token.nextToken();
            StringTokenizer toke = new StringTokenizer(third, ".");
            String val = toke.nextToken();

            fname.setText(fName);
            lname.setText(lName);
            add.setText(address);
            cty.setText(city);
            srt.setText(street);
            wig.setText(weight);
            cd.setText(first);
            type.setText(donation_type);

            dialog.show();

        } catch (Exception f) {

        }

    }
}

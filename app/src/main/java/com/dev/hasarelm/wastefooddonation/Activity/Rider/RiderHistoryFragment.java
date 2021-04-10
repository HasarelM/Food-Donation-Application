package com.dev.hasarelm.wastefooddonation.Activity.Rider;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Adapter.RiderCompletedAdapter;
import com.dev.hasarelm.wastefooddonation.Adapter.RiderRequestAdapter;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Interface.OnCompleteOrderClickListener;
import com.dev.hasarelm.wastefooddonation.Model.DonationRequestListModel;
import com.dev.hasarelm.wastefooddonation.Model.donations;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class RiderHistoryFragment extends Fragment implements OnCompleteOrderClickListener<donations> {

    private RecyclerView mRvDonationCompleteList;
    View rootView;
    private Activity activity;

    private int ID = 0;
    private DonationRequestListModel mDonationRequestListModel;
    private ArrayList<donations> mDonationsArrayList;
    private RiderCompletedAdapter mRiderCompletedAdapter;

    public RiderHistoryFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.rider_history_fragment, container, false);
        mRvDonationCompleteList = rootView.findViewById(R.id.rider_home_fragment_rv_all_complete_list);

        getCompleteOrderList(2);
        return rootView;
    }

    private void getCompleteOrderList(int status) {
        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);
        try {
            EndPoints endPoints = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<DonationRequestListModel> call = endPoints.getCompleteOrders(VLF_BASE_URL+"donations?state=",status);
            call.enqueue(new Callback<DonationRequestListModel>() {
                @Override
                public void onResponse(Call<DonationRequestListModel> call, Response<DonationRequestListModel> response) {

                    if (response.code()==200){

                        mDonationRequestListModel = response.body();
                        mDonationsArrayList = mDonationRequestListModel.getDonations();

                        setupRecyclerView(mDonationsArrayList);

                        myPd_ring.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<DonationRequestListModel> call, Throwable t) {

                }
            });

        }catch (Exception g){}
    }

    private void setupRecyclerView(ArrayList<donations> mDonationsArrayList) {

        mRiderCompletedAdapter = new RiderCompletedAdapter(mDonationsArrayList, activity, this);
        mRvDonationCompleteList.setHasFixedSize(true);
        mRvDonationCompleteList.setLayoutManager(new LinearLayoutManager(activity));
        mRvDonationCompleteList.setAdapter(mRiderCompletedAdapter);
    }

    @Override
    public void onCompleteOrder(int position, donations data) {

        int id = data.getId();
        int driver_id = data.getDriver_id();
        String type = "drop";

        Intent intent = new Intent(getActivity(), RiderCompleteOrderActivity.class);
        String name = "Transporter";
        intent.putExtra("id", id);
        intent.putExtra("driver_id", driver_id);
        intent.putExtra("type", type);
        startActivity(intent);

    }
}

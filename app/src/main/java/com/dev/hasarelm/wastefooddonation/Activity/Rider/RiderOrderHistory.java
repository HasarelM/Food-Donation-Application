package com.dev.hasarelm.wastefooddonation.Activity.Rider;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Adapter.DonationHistoryAdapter;
import com.dev.hasarelm.wastefooddonation.Common.EndPoints;
import com.dev.hasarelm.wastefooddonation.Common.RetrofitClient;
import com.dev.hasarelm.wastefooddonation.Common.SharedPreferencesClass;
import com.dev.hasarelm.wastefooddonation.Interface.OnHistoryClickListner;
import com.dev.hasarelm.wastefooddonation.Model.DonationRequestListModel;
import com.dev.hasarelm.wastefooddonation.Model.donations;
import com.dev.hasarelm.wastefooddonation.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dev.hasarelm.wastefooddonation.Common.BaseURL.VLF_BASE_URL;

public class RiderOrderHistory extends Fragment implements OnHistoryClickListner<donations> {

    private RecyclerView mRecyclerView;
    private String ID = "";
    private int userID = 0;
    private int state;
    SharedPreferences localSP;
    private DonationHistoryAdapter mDonationHistoryAdapter;
    private DonationRequestListModel mDonationTypeModel;
    private ArrayList<donations> mDonationTypes;
    private Dialog add_dialog;

    View rootView;

    public RiderOrderHistory() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.rider_order_history, container, false);
        mRecyclerView = rootView.findViewById(R.id.activity_donater_home_rv_donation_list_history);

        try {
            localSP = getContext().getSharedPreferences(SharedPreferencesClass.SETTINGS, Context.MODE_PRIVATE + Context.MODE_PRIVATE);
            ID = localSP.getString("USER_ID", "");
            userID = Integer.parseInt(ID);

        } catch (Exception f) {
        }

        getHistoryList(userID, 3);

        return rootView;
    }

    private void getHistoryList(int userID, int state) {
        final ProgressDialog myPd_ring = ProgressDialog.show(getContext(), "Please wait", "", true);

        try {
            EndPoints apiService = RetrofitClient.getLoginClient().create(EndPoints.class);
            Call<DonationRequestListModel> call_customer = apiService.getAllCompleteOrderList(VLF_BASE_URL + "donations?driver_id=", userID, state);
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

        } catch (Exception f) {
        }
    }

    private void setupRecyclerView(ArrayList<donations> donationsArrayList) {
        mDonationHistoryAdapter = new DonationHistoryAdapter(donationsArrayList, getActivity(), this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mDonationHistoryAdapter);
    }

    @Override
    public void onHistory(int position, donations data) {

        int Id = data.getId();

        add_dialog = new Dialog(getContext());
        add_dialog.setContentView(R.layout.view_history);
        add_dialog.setTitle("Title");

        try {
            ImageView imageView = add_dialog.findViewById(R.id.image_add_view);
            TextView donate_name = add_dialog.findViewById(R.id.add_sekller_view_name_donate);
            TextView donate_mobile = add_dialog.findViewById(R.id.add_seller_view_mobile_donate);
            TextView category = add_dialog.findViewById(R.id.add_seller_view_category_donation_type_donate);
            TextView weight = add_dialog.findViewById(R.id.add_seller_view_title_weight_donate);
            TextView create_date = add_dialog.findViewById(R.id.add_seller_view_description_create_date_donate);
            Button close = add_dialog.findViewById(R.id.add_view_seller_btn_close_close);

            String imagename = data.getImages().get(0).getImage_url_2();

            try {
                Picasso.get().load(imagename.toString().trim())
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView);

            } catch (Exception ww) {
            }

            donate_name.setText(data.getDonator_first_name() + "");
            donate_mobile.setText(data.getPhone() + "");
            category.setText(data.getDonation_type() + "");
            weight.setText(data.getWeight() + "");
            create_date.setText(data.getCreated_at() + "");

            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add_dialog.dismiss();
                }
            });

            add_dialog.show();

        } catch (Exception h) {
        }

    }


}

package com.dev.hasarelm.wastefooddonation.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Interface.OnDonationItemClickListner;
import com.dev.hasarelm.wastefooddonation.Model.donations;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class DonaterRequestAdapter extends RecyclerView.Adapter<DonaterRequestAdapter.DonationMyViewHolder> {

    private ArrayList<donations> mDonationsArrayList;
    private Activity activity;
    private OnDonationItemClickListner<donations> donationsOnDonationItemClickListner;

    public DonaterRequestAdapter(Activity activity, ArrayList<donations> mDonationsArrayList, OnDonationItemClickListner<donations> listners) {
        this.activity = activity;
        this.mDonationsArrayList = mDonationsArrayList;
        this.donationsOnDonationItemClickListner = listners;
    }

    @NonNull
    @Override
    public DonationMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donater_request_adapter_layout, parent, false);

        return new DonationMyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull DonationMyViewHolder holder, int position) {

        donations donation = mDonationsArrayList.get(position);
        holder.mTvVehicle.setText(donation.getVehicle_type());
        holder.mTvCategory.setText(donation.getDonation_type());

        StringTokenizer tokens = new StringTokenizer(donation.getCreated_at(), "T");
        String first = tokens.nextToken();
        String second = tokens.nextToken();
        StringTokenizer token = new StringTokenizer(second, "T");
        String third = token.nextToken();
        StringTokenizer toke = new StringTokenizer(third, ".");
        String val = toke.nextToken();

        holder.mTvDate.setText(first + "     Time  : " + val);

        if (donation.getState() == 1) {

            holder.mTvStatus.setText("Pending");
            holder.mLvColourBar.setBackgroundColor(activity.getResources().getColor(R.color.col_red));

        } else if (donation.getState() == 2) {

            holder.mTvStatus.setText("Approve");
            holder.mLvColourBar.setBackgroundColor(activity.getResources().getColor(R.color.col_yellow));

        } else if (donation.getState() == 3) {

            holder.mTvStatus.setText("Delivery");
            holder.mLvColourBar.setBackgroundColor(activity.getResources().getColor(R.color.colorTopOval));
        }

    }

    @Override
    public int getItemCount() {
        return mDonationsArrayList.size();
    }

    public class DonationMyViewHolder extends RecyclerView.ViewHolder {

        TextView mTvDate, mTvCategory, mTvVehicle, mTvStatus;
        LinearLayout mLvColourBar;

        public DonationMyViewHolder(View view) {
            super(view);

            mLvColourBar = view.findViewById(R.id.donater_request_adapter_colour_bar);
            mTvDate = view.findViewById(R.id.donater_request_adapter_date);
            mTvCategory = view.findViewById(R.id.donater_request_adapter_category);
            mTvVehicle = view.findViewById(R.id.donater_request_adapter_description);
            mTvStatus = view.findViewById(R.id.donater_request_adapter_status);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = DonationMyViewHolder.this.getAdapterPosition();

                    donationsOnDonationItemClickListner.onDonationClick(position, mDonationsArrayList.get(position));
                }
            });

        }
    }
}

package com.dev.hasarelm.wastefooddonation.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Interface.OnDonationItemClickListner;
import com.dev.hasarelm.wastefooddonation.Interface.OnMoreInfoClickListner;
import com.dev.hasarelm.wastefooddonation.Interface.OnRiderCallClickLisner;
import com.dev.hasarelm.wastefooddonation.Model.donations;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.ArrayList;

public class RiderRequestAdapter extends RecyclerView.Adapter<RiderRequestAdapter.RiderMyViewHolder> {

    private ArrayList<donations> mDonationsArrayList;
    private Activity activity;
    private OnDonationItemClickListner<donations> mItemClickListner;
    private OnMoreInfoClickListner<donations> mMoreInfoClickListner;
    private OnRiderCallClickLisner<donations> mRiderCallClickLisner;

    public RiderRequestAdapter(Activity activity, ArrayList<donations> donations,OnDonationItemClickListner<donations> donationItemClickListner,OnMoreInfoClickListner<donations> donationsOnMoreInfoClickListner
            ,OnRiderCallClickLisner<donations> riderCallClickLisner){

        this.mDonationsArrayList = donations;
        this.activity = activity;
        this.mItemClickListner = donationItemClickListner;
        this.mMoreInfoClickListner = donationsOnMoreInfoClickListner;
        this.mRiderCallClickLisner = riderCallClickLisner;
    }

    @NonNull
    @Override
    public RiderMyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rider_request_adapter_layout, parent, false);

        return new RiderMyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RiderMyViewHolder holder, int position) {

        donations dt = mDonationsArrayList.get(position);
        holder.mTvRefNo.setText("RF/CFE/00000"+dt.getId()+"");
        holder.mTvCategory.setText(dt.getDonation_type());
        holder.mTvVehicleType.setText(dt.getVehicle_type());
        holder.mTvContact.setText(dt.getPhone());

        if (dt.getDonation_type_id()==1){

            holder.mTvType.setText("Only Animal");

        }else if (dt.getDonation_type_id()==2){
            holder.mTvType.setText("Only Peoples");

        }else if (dt.getDonation_type_id()==3){
            holder.mTvType.setText("Both");
        }
    }

    @Override
    public int getItemCount() {
        return mDonationsArrayList.size();
    }

    public class RiderMyViewHolder extends RecyclerView.ViewHolder {

        TextView mTvRefNo,mTvCategory,mTvVehicleType,mTvContact,mTvType,mTvText;
        LinearLayout mLvMoreInfo,mLvLocation;

        public RiderMyViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvCategory = itemView.findViewById(R.id.rider_request_adapter_category);
            mTvContact = itemView.findViewById(R.id.rider_request_adapter_contact);
            mTvRefNo = itemView.findViewById(R.id.rider_request_adapter_refNo);
            mTvVehicleType = itemView.findViewById(R.id.rider_request_adapter_vehicle_type);
            mLvLocation = itemView.findViewById(R.id.rider_request_adapter_location);
            mLvMoreInfo = itemView.findViewById(R.id.rider_request_adapter_more_info);
            mTvType = itemView.findViewById(R.id.donater_adapter_type_category);
           // mTvText = itemView.findViewById(R.id.donater_adapter_type_category);

            mLvLocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = RiderMyViewHolder.this.getAdapterPosition();

                    mItemClickListner.onDonationClick(position,mDonationsArrayList.get(position));
                }
            });

            mLvMoreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final  int position = RiderMyViewHolder.this.getAdapterPosition();
                    mMoreInfoClickListner.onMoreInfoClick(position,mDonationsArrayList.get(position));
                }
            });

            mTvContact.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final  int position = RiderMyViewHolder.this.getAdapterPosition();
                    mRiderCallClickLisner.onCallClick(position,mDonationsArrayList.get(position));
                }
            });

        }
    }
}

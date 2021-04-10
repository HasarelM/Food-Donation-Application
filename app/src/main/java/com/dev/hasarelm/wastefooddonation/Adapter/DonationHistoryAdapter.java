package com.dev.hasarelm.wastefooddonation.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Interface.OnDonationItemClickListner;
import com.dev.hasarelm.wastefooddonation.Interface.OnHistoryClickListner;
import com.dev.hasarelm.wastefooddonation.Model.donations;
import com.dev.hasarelm.wastefooddonation.Model.images;
import com.dev.hasarelm.wastefooddonation.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DonationHistoryAdapter extends RecyclerView.Adapter<DonationHistoryAdapter.DonationHistoryViewHolder> {

    private ArrayList<donations> mDonationsArrayList;
    private ArrayList<images> imagesArrayList;
    private Activity activity;
    private OnHistoryClickListner<donations> onHistoryClickListner;

    public DonationHistoryAdapter(ArrayList<donations> donations,Activity activity,OnHistoryClickListner<donations> donation){
        this.mDonationsArrayList = donations;
        this.activity = activity;
        this.onHistoryClickListner = donation;
    }

    @NonNull
    @Override
    public DonationHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donation_history_layout, parent, false);

        return new DonationHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationHistoryViewHolder holder, int position) {

        try {

            donations donation = mDonationsArrayList.get(position);

            holder.mTvCusName.setText(donation.getDonator_first_name());
            holder.mTvCusMobile.setText(donation.getPhone());
            holder.mTvRefID.setText("RF/CFE/00000"+donation.getId());

            if (donation.getState()==3){
                holder.mTvStatus.setText("Complete");
            }

            try {
                Picasso.get().load(donation.getImages().get(0).getImage_url_2().toString().trim())
                        .error(R.drawable.ic_launcher_background)
                        .into(holder.mImgImage);

            }catch (Exception ww)
            {
            }

        }catch (Exception g){}
    }

    @Override
    public int getItemCount() {
        return mDonationsArrayList.size();
    }

    public class DonationHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView  mTvCusName, mTvCusMobile, mTvStatus,mTvRefID;
        ImageView mImgImage;

        public DonationHistoryViewHolder(@NonNull View view) {
            super(view);

            mImgImage = view.findViewById(R.id.donater_iv_imageview);
            mTvCusName = view.findViewById(R.id.seller_add_category_donate);
            mTvCusMobile = view.findViewById(R.id.seller_add_title_no);
            mTvStatus = view.findViewById(R.id.seller_add_to_state);
            mTvRefID = view.findViewById(R.id.seller_add_category_donate_id);


            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = DonationHistoryViewHolder.this.getAdapterPosition();

                    onHistoryClickListner.onHistory(position, mDonationsArrayList.get(position));
                }
            });
        }
    }
}

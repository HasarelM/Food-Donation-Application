package com.dev.hasarelm.wastefooddonation.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Interface.OnCompleteOrderClickListener;
import com.dev.hasarelm.wastefooddonation.Model.donations;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class RiderCompletedAdapter extends RecyclerView.Adapter<RiderCompletedAdapter.RiderCompletedViewHolder>{

    private ArrayList<donations> mDonationsArrayList;
    private Activity activity;
    private OnCompleteOrderClickListener orderClickListener;

    public RiderCompletedAdapter(ArrayList<donations> donations, Activity activity, OnCompleteOrderClickListener<donations> orderClickListener){
        this.mDonationsArrayList = donations;
        this.activity = activity;
        this.orderClickListener = orderClickListener;
    }

    @NonNull
    @Override
    public RiderCompletedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rider_order_complete_adapter, parent, false);
        return new RiderCompletedViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RiderCompletedViewHolder holder, int position) {

        try {
            donations dt = mDonationsArrayList.get(position);
            holder.mTvRefID.setText("RF/CFE/00000"+dt.getId()+"");
            StringTokenizer tokens = new StringTokenizer(dt.getCreated_at(), "T");
            String first = tokens.nextToken();
            String second = tokens.nextToken();
            StringTokenizer token = new StringTokenizer(second, "T");
            String third = token.nextToken();
            StringTokenizer toke = new StringTokenizer(third, ".");
            String val = toke.nextToken();

            holder.mTvDate.setText(""+first);
            holder.mTvTime.setText(""+val);
            holder.mTvStatus.setText("To be Complete");

            if (dt.getState()==3){


            }
        }catch (Exception h){}


    }

    @Override
    public int getItemCount() {
        return mDonationsArrayList.size();
    }

    public class RiderCompletedViewHolder extends RecyclerView.ViewHolder {

        TextView mTvRefID,mTvDate,mTvTime,mTvStatus;

        public RiderCompletedViewHolder(@NonNull View itemView) {
            super(itemView);

            mTvRefID = itemView.findViewById(R.id.donater_request_adapter_refNo);
            mTvDate = itemView.findViewById(R.id.donater_request_adapter_complete_date);
            mTvTime = itemView.findViewById(R.id.donater_request_adapter_time);
            mTvStatus = itemView.findViewById(R.id.donater_request_adapter_complete_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final int position = RiderCompletedViewHolder.this.getAdapterPosition();

                    orderClickListener.onCompleteOrder(position,mDonationsArrayList.get(position));
                }
            });

        }
    }
}

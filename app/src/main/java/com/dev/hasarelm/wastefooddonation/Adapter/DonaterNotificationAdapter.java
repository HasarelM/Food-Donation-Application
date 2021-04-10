package com.dev.hasarelm.wastefooddonation.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Interface.OnItemClickListener;
import com.dev.hasarelm.wastefooddonation.Model.notifications;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.ArrayList;

public class DonaterNotificationAdapter extends RecyclerView.Adapter<DonaterNotificationAdapter.MyViewHolder> {

    private ArrayList<notifications> mNotificationsArrayList;
    private Activity activity;
    private OnItemClickListener<notifications> mListener;

    public DonaterNotificationAdapter(Activity activity, ArrayList<notifications> mNotificationsArrayList, OnItemClickListener<notifications> listener) {
        this.activity = activity;
        this.mNotificationsArrayList = mNotificationsArrayList;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.donater_notification_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            notifications nfy = mNotificationsArrayList.get(position);
            holder.mTvNotificationHeader.setText(nfy.getTitle());
            holder.mTvNotificationBody.setText(nfy.getDescription());

            if (nfy.getIs_read()==1){

            }else {

                holder.mTvNotificationIcon.setBackground(activity.getResources().getDrawable(R.drawable.ic_baseline_notifications_active_24));
            }
        }catch (Exception d){}

    }

    @Override
    public int getItemCount() {
        return mNotificationsArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTvNotificationHeader, mTvNotificationBody,mTvNotificationIcon;
        CardView mCardView;

        public MyViewHolder(View view) {
            super(view);

            mTvNotificationHeader = view.findViewById(R.id.notification_layout_header);
            mTvNotificationBody = view.findViewById(R.id.notification_layout_body);
            mTvNotificationIcon = view.findViewById(R.id.donater_notification_icon_tv);
            mCardView = view.findViewById(R.id.donater_notification_cart_view_icon);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = MyViewHolder.this.getAdapterPosition();

                        mListener.onItemClick(position, mNotificationsArrayList.get(position));
                }
            });
        }
    }
}

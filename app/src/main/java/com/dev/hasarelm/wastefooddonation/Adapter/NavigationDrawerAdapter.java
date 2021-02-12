package com.dev.hasarelm.wastefooddonation.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dev.hasarelm.wastefooddonation.Common.CommonFunction;
import com.dev.hasarelm.wastefooddonation.Interface.OnItemClickListener;
import com.dev.hasarelm.wastefooddonation.Model.NavDrawerItem;
import com.dev.hasarelm.wastefooddonation.R;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder>{

    private Context mContext;
    private List<NavDrawerItem> mData = Collections.emptyList();
    private OnItemClickListener<NavDrawerItem> mListener;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data, @NonNull OnItemClickListener<NavDrawerItem> listener) {
        this.mContext = context;
        this.mData = data;
        this.mListener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_navigation_drawer, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem navigationItem = mData.get(position);
        holder.mTvDrawerItemTitle.setText(navigationItem.getTitle());
        if (navigationItem.getTitle().equals(CommonFunction.NAV_ITEM_dashboard)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_action_home));
        } else if (navigationItem.getTitle().equals(CommonFunction.NAV_ITEM_Donation)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_action_history));
        } else if (navigationItem.getTitle().equals(CommonFunction.NAV_ITEM_Rate)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_action_rating));
        }else if (navigationItem.getTitle().equals(CommonFunction.NAV_ITEM_notification)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_action_notification));
        } else if (navigationItem.getTitle().equals(CommonFunction.NAV_ITEM_about_us)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_action_about));
        }else if (navigationItem.getTitle().equals(CommonFunction.NAV_ITEM_Logout)) {
            holder.mIvItemIcon.setBackground(mContext.getResources().getDrawable(R.drawable.ic_action_logout));
        }

        if (navigationItem.isSelectedItem()) {
            holder.mRlBase.setBackground(mContext.getResources().getDrawable(R.drawable.item_selected_background));
            holder.mTvDrawerItemTitle.setTextColor(mContext.getResources().getColor(R.color.colorSelectedMenuItemText));
        } else {
            holder.mRlBase.setBackground(mContext.getResources().getDrawable(R.drawable.item_click_background));
            holder.mTvDrawerItemTitle.setTextColor(mContext.getResources().getColor(R.color.colorNotSelectedMenuItemText));
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout mRlBase;
        ImageView mIvItemIcon;
        TextView mTvDrawerItemTitle;

        public MyViewHolder(View view) {
            super(view);

            mRlBase = view.findViewById(R.id.row_navigation_drawer_rl_base);
            mIvItemIcon = view.findViewById(R.id.row_navigation_drawer_iv_item_icon);
            mTvDrawerItemTitle = view.findViewById(R.id.row_navigation_drawer_tv_item_title);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = MyViewHolder.this.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(position, mData.get(position));
                    }
                }
            });
        }
    }
}

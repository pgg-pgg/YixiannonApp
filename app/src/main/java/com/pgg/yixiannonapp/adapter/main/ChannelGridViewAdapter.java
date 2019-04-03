package com.pgg.yixiannonapp.adapter.main;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.global.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<MainEntity.ChannelEntity> channel_info;

    public ChannelGridViewAdapter(Context mContext, List<MainEntity.ChannelEntity> channel_info) {
        this.mContext = mContext;
        this.channel_info = channel_info;
    }


    @Override
    public int getCount() {
        return channel_info == null ? 0 : channel_info.size();
    }

    @Override
    public Object getItem(int position) {
        return channel_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_channel_layout, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MainEntity.ChannelEntity channelInfoBean = channel_info.get(position);
        holder.tvChannel.setText(channelInfoBean.getChannelName());
        Glide.with(mContext)
                .load(Constant.BASE_URL+channelInfoBean.getChannelUrl())
                .into(holder.ivChannel);
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_channel)
        ImageView ivChannel;
        @BindView(R.id.tv_channel)
        TextView tvChannel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

package com.pgg.yixiannonapp.adapter.main;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.MainEntity;
import com.pgg.yixiannonapp.widget.GridViewChannelView;

import java.util.ArrayList;
import java.util.List;

public class ChannelViewPagerAdapter extends PagerAdapter {

    private Context context;
    private List<List<MainEntity.ChannelEntity>> mChannelEntities;
    private int count;
    private List<View> views = new ArrayList<>();
    public ChannelViewPagerAdapter(Context context, List<MainEntity.ChannelEntity> channelEntities) {
        this.context = context;
        int size = channelEntities.size();
        count = size%10>0?size/10+1:size/10;
        mChannelEntities = groupListByQuantity(channelEntities,10);

        for (int i=0;i<count;i++){
            View view = View.inflate(context, R.layout.layout_channel_grid_view,null);
            views.add(view);
        }
    }

    private List groupListByQuantity(List list, int quantity) {
        if (list == null || list.size() == 0) {
            return list;
        }

        if (quantity <= 0) {
            new IllegalArgumentException("Wrong quantity.");
        }

        List wrapList = new ArrayList();
        int count = 0;
        while (count < list.size()) {
            wrapList.add(list.subList(count, (count + quantity) > list.size() ? list.size() : count + quantity));
            count += quantity;
        }

        return wrapList;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public View instantiateItem(ViewGroup container, final int position) {
        View view = views.get(position);
        GridViewChannelView gv_channel = view.findViewById(R.id.gv_channel);
        gv_channel.setAdapter(new ChannelGridViewAdapter(context,mChannelEntities.get(position)));
        gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Todo:频道的点击事件
                Toast.makeText(context,mChannelEntities.get(position).get(i).getChannelName(),Toast.LENGTH_SHORT).show();
            }
        });
        container.addView(view);
        return view;
    }



}

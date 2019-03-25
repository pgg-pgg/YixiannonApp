package com.pgg.yixiannonapp.module.goods_detail.fragment;

import android.content.Context;
import android.widget.ListView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.goods_detail.GoodsConfigAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.GoodsConfigBean;
import com.pgg.yixiannonapp.module.goods_detail.GoodsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class GoodsConfigFragment extends BaseFragment {

    public GoodsDetailActivity activity;
    @BindView(R.id.lv_config)
    public ListView lv_config;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (GoodsDetailActivity) context;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_item_config;
    }

    @Override
    public void initView() {
        lv_config.setFocusable(false);

        List<GoodsConfigBean> data = new ArrayList<>();
        data.add(new GoodsConfigBean("品牌", "Letv/乐视"));
        data.add(new GoodsConfigBean("型号", "LETV体感-超级枪王"));
        lv_config.setAdapter(new GoodsConfigAdapter(activity, data));
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}

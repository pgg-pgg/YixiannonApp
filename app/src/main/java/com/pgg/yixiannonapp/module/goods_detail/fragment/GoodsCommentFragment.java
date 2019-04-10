package com.pgg.yixiannonapp.module.goods_detail.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.goods_detail.ItemCommentsAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.Comments;
import com.pgg.yixiannonapp.module.goods_detail.GoodsDetailActivity;

import java.util.List;

import butterknife.BindView;

public class GoodsCommentFragment extends BaseFragment {
    @BindView(R.id.tv_none_comments)
    TextView tv_none_comments;
    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;
    @BindView(R.id.tv_good_comment)
    TextView tv_good_comment;
    @BindView(R.id.lv_comments)
    ListView lv_comments;
    public GoodsDetailActivity activity;
    private List<Comments> comments;

    public void setComments(List<Comments> comments){
        this.comments = comments;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (GoodsDetailActivity) context;
    }
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_goods_comment;
    }

    @Override
    public void initView() {
        int startNums = 0;
        for (int i = 0; i < comments.size(); i++) {
            startNums += comments.get(i).getRatingStar();
        }
        float percent = (float) startNums / (5 * comments.size());
        tv_comment_count.setText("(" + comments.size() + ")");
        tv_good_comment.setText((percent * 100) + "%");
        if (comments.size()>0){
            tv_none_comments.setVisibility(View.GONE);
            lv_comments.setVisibility(View.VISIBLE);
            lv_comments.setAdapter(new ItemCommentsAdapter(getContext(), comments));
        }
    }

    @Override
    protected void managerArguments() {

    }

    @Override
    public String getUmengFragmentName() {
        return null;
    }
}

package com.pgg.yixiannonapp.adapter.goods_detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.idlestar.ratingstar.RatingStarView;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.Comments;

import java.util.List;

public class ItemCommentsAdapter extends BaseAdapter {

    private List<Comments> comments;
    private LayoutInflater inflater;
    private Context context;
    public ItemCommentsAdapter(Context context,List<Comments> comments) {
        this.context = context;
        this.comments = comments;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Comments getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return comments.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Comments comments = this.comments.get(position);
        ViewHolder holder =null;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.item_list_comment, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.rsv_starNum.setRating(comments.getRatingStar());
        holder.rsv_starNum.setStarNum(5);
        holder.tv_comment_content.setText(comments.getCommentContent());
        holder.tv_comment_name.setText(comments.getCommentsName());
        holder.tv_posts_number.setText(comments.getRelayNum()+"");
        holder.tv_shenhe_cai_number.setText(comments.getTreadNum()+"");
        holder.tv_shenhe_ding_number.setText(comments.getPraiseNum()+"");
        return convertView;
    }

    class ViewHolder{
        private TextView tv_comment_name;
        private TextView tv_comment_content;
        private TextView tv_shenhe_ding_number;
        private TextView tv_shenhe_cai_number;
        private TextView tv_posts_number;
        private RatingStarView rsv_starNum;

        public ViewHolder(View convertView) {
            tv_comment_name = convertView.findViewById(R.id.tv_comment_name);
            tv_comment_content = convertView.findViewById(R.id.tv_comment_content);
            tv_shenhe_ding_number = convertView.findViewById(R.id.tv_shenhe_ding_number);
            tv_shenhe_cai_number = convertView.findViewById(R.id.tv_shenhe_cai_number);
            tv_posts_number = convertView.findViewById(R.id.tv_posts_number);
            rsv_starNum = convertView.findViewById(R.id.rsv_starNum);
        }
    }
}

package com.pgg.yixiannonapp.adapter.address;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.domain.order.ShipAddress;

import java.util.List;

public class ShipAddressAdapter extends BaseQuickAdapter<ShipAddress,BaseViewHolder> {

    public ShipAddressAdapter(int layoutResId, List<ShipAddress> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final ShipAddress item) {
        TextView mShipNameTv = helper.convertView.findViewById(R.id.mShipNameTv);
        TextView mShipAddressTv = helper.convertView.findViewById(R.id.mShipAddressTv);
        final TextView mSetDefaultTv = helper.convertView.findViewById(R.id.mSetDefaultTv);
        final TextView mEditTv = helper.convertView.findViewById(R.id.mEditTv);
        final TextView mDeleteTv = helper.convertView.findViewById(R.id.mDeleteTv);
        mSetDefaultTv.setSelected(item.getShipIsDefault()==0);
        mShipNameTv.setText(item.getShipUserName()+"     "+item.getShipUserMobile());
        mShipAddressTv.setText(item.getShipAddress());
        mSetDefaultTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optClickListener!=null){
                    if (!mSetDefaultTv.isSelected()){
                        item.setShipIsDefault(0);
                        optClickListener.onSetDefault(item);
                    }
                }
            }
        });
        mEditTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optClickListener!=null){
                    optClickListener.onEdit(item);
                }
            }
        });
        mDeleteTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (optClickListener!=null){
                    optClickListener.onDelete(item);
                }
            }
        });

        helper.convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener!=null){
                    itemClickListener.OnItemClick(v,item);
                }
            }
        });
    }

    private OnOptClickListener optClickListener;

    public void setOnOptClickListener(OnOptClickListener listener){
        this.optClickListener = listener;
    }

    public interface OnOptClickListener{
        void onSetDefault(ShipAddress address);
        void onEdit(ShipAddress address);
        void onDelete(ShipAddress address);
    }

    private OnItemClickListener itemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }
    public interface OnItemClickListener{
        void OnItemClick(View view,ShipAddress address);
    }
}

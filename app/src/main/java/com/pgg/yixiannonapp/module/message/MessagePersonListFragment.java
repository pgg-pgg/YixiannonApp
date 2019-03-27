package com.pgg.yixiannonapp.module.message;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pgg.yixiannonapp.R;
import com.pgg.yixiannonapp.adapter.message.MessageRecyclerAdapter;
import com.pgg.yixiannonapp.base.BaseFragment;
import com.pgg.yixiannonapp.domain.MessageBean;
import com.pgg.yixiannonapp.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;

public class MessagePersonListFragment extends BaseFragment {

    @BindView(R.id.fm_contact_rv)
    RecyclerView mFmContactRv;
    @BindView(R.id.fm_contact_no)
    TextView mFmContactNo;
    @BindView(R.id.fm_contact_msg)
    RelativeLayout mFmContactMsg;
    private List<MessageBean> data = new ArrayList<>();
    private MessageRecyclerAdapter adapter;
    private UserInfo info;
    private String[] listUserName = new String[]{"1000", "1006"};

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_message_person_list;
    }

    @Override
    public void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mFmContactRv.setLayoutManager(layoutManager);
        adapter = new MessageRecyclerAdapter(data, getActivity());
        //分割线
        mFmContactRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mFmContactRv.setAdapter(adapter);
//        initGetList();
        initItemOnClick();
    }

    /*监听item*/
    private void initItemOnClick() {
        adapter.setOnItemClickListener(new MessageRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Log.e("initItem", data.get(position).getType() + "");
                if (data.get(position).type == 3) {
//                    Intent intent = new Intent(getActivity(), UserInfoActivity.class);
//                    intent.putExtra("USERNAME", data.get(position).getUserName());
//                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, final int position) {

            }
        });
    }

    @Override
    public void onResume() {
        adapter.clear();
        initGetList();
//        isFriendStateList(listUserName);
        super.onResume();
    }

    /*获取好友列表*/
    MessageBean bean;

    private void initGetList() {
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void gotResult(final int i, String s, List<UserInfo> list) {

                if (i == 0) {
                    Log.e("Log:好友数", "" + list
                            .size());

                    info = list.get(i);
                    mFmContactNo.setVisibility(View.GONE);
                    mFmContactRv.setVisibility(View.VISIBLE);
                    for (int j = 0; j < list.size(); j++) {
                        bean = new MessageBean();
                        bean.setTitle(list.get(j).getNickname());
                        bean.setContent(list.get(j).getSignature());
                        bean.setTime(TimeUtils.ms2date("MM-dd HH:mm", list.get(j).getmTime()));
                        bean.setUserName(list.get(j).getUserName());
                        bean.setImg(list.get(j).getAvatarFile().toURI().toString());
                        bean.setType(3);
                        data.add(bean);
                    }
                    Collections.reverse(list);
                    Collections.reverse(data);
                    adapter.notifyDataSetChanged();

                    if (list.size()<=0){
                        mFmContactRv.setVisibility(View.GONE);
                        mFmContactNo.setVisibility(View.VISIBLE);
                    }
                }else {
                    mFmContactRv.setVisibility(View.GONE);
                    mFmContactNo.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    @OnClick({R.id.fm_contact_no, R.id.fm_contact_msg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fm_contact_no:
//                Intent intent = new Intent(getActivity(), AddFriendsActivity.class);
//                startActivity(intent);
                Toast.makeText(getContext(),"sss",Toast.LENGTH_SHORT).show();
                break;
            case R.id.fm_contact_msg:
//                startActivity(new Intent(getActivity(), PullMsgListActivity.class));
                Toast.makeText(getContext(),"www",Toast.LENGTH_SHORT).show();
                break;
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

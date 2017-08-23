package com.xiaocool.sugarangel.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.acyivity.GroupChatActivity;
import com.xiaocool.sugarangel.acyivity.NearlyActivity;
import com.xiaocool.sugarangel.acyivity.PhoneFriendActivity;
import com.xiaocool.sugarangel.acyivity.PhoneFriendTestActivity;
import com.xiaocool.sugarangel.adapter.ContactRecyclerAdapter;
import com.xiaocool.sugarangel.adapter.OnRecyclerViewListener;
import com.xiaocool.sugarangel.bean.FriendListBean;
import com.xiaocool.sugarangel.bean.InfoBean;
import com.xiaocool.sugarangel.bean.UserInfo;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.net.ResponseHelper;
import com.xiaocool.sugarangel.utils.Cn2Spell;
import com.xiaocool.sugarangel.utils.GlideUtils;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.xiaocool.sugarangel.view.ClearEditText;
import com.xiaocool.sugarangel.view.SideBar;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;


public class HealthCircleFragment extends BaseFragment {

    @BindView(R.id.filter_edit)
    ClearEditText filterEdit;
    @BindView(R.id.phone_friend_iv)
    ImageView phoneFriendIv;
    @BindView(R.id.circle_phone_friend)
    LinearLayout circlePhoneFriend;
    @BindView(R.id.group_chat_iv)
    ImageView groupChatIv;
    @BindView(R.id.circle_group_chat)
    LinearLayout circleGroupChat;
    @BindView(R.id.circle_nearby_iv)
    ImageView circleNearbyIv;
    @BindView(R.id.circle_nearby)
    LinearLayout circleNearby;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.noResult)
    TextView noResult;
    @BindView(R.id.anno)
    TextView anno;
    @BindView(R.id.sideBar)
    SideBar sideBar;

    Unbinder unbinder;

    //数据源
    private List<FriendListBean> exampleList;
    private List<FriendListBean> tempExampleList;//实际操作数据

    //列表

    private ContactRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    UserInfo userInfo;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_blank;
    }

    @Override
    public void initView() {
        setTitleBar("健康圈");
        exampleList = new ArrayList<>();
        tempExampleList = new ArrayList<>();
        mContext = getActivity();
        userInfo = new UserInfo(mContext);
        GlideUtils.loadRectangImageView(mContext, "http://imgsrc.baidu.com/imgad/pic/item/caef76094b36acaf0accebde76d98d1001e99ce7.jpg", phoneFriendIv);
        GlideUtils.loadRectangImageView(mContext, "http://imgsrc.baidu.com/imgad/pic/item/caef76094b36acaf0accebde76d98d1001e99ce7.jpg", groupChatIv);
        GlideUtils.loadRectangImageView(mContext, "http://imgsrc.baidu.com/imgad/pic/item/caef76094b36acaf0accebde76d98d1001e99ce7.jpg", circleNearbyIv);
        getData();
        initRecyclerView();
        initSideBar();
        initSearchEdtit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 搜索栏
     */
    private void initSearchEdtit() {


        // 根据输入框输入值的改变来过滤搜索
        filterEdit.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表

                filterData(s.toString());


            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /**
     * 获取每个字符或者直接用 toCharArray 方法
     * 通过检索输入的字符，是不是顺序匹配pinyin项实现筛选
     *
     * @param s 搜索条件
     */
    private void filterData(String s) {
        tempExampleList.clear();
        if (TextUtils.isEmpty(s)) {
//            tempExampleList.addAll(sourceDateList);
            tempExampleList.addAll(exampleList);
        } else {
            FriendListBean tempInfoBean;
            int listSize = exampleList.size();
            for (int i = 0; i < listSize; i++) {
                tempInfoBean = exampleList.get(i);
                String[] pinyinstr = tempInfoBean.getPinyin().split(" ");
                String[] namestr = tempInfoBean.getName().split(" ");
                int strLength = pinyinstr.length;
                for (int j = 0; j < strLength; j++) {
                    if (pinyinstr[j].startsWith(s)||namestr[j].startsWith(s)) {
                        tempExampleList.add(tempInfoBean);
                    }
                }
            }
        }

        //放置没有搜索结果的图片
        if (tempExampleList.size() == 0) {
            noResult.setVisibility(View.VISIBLE);
        } else {
            noResult.setVisibility(View.GONE);
        }

        mAdapter.notifyDataSetChanged();
    }

    /**
     * 侧边音序索引
     */
    private void initSideBar() {

        anno.setVisibility(View.GONE);
        sideBar.setTextView(anno);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    ((LinearLayoutManager) mLayoutManager).scrollToPositionWithOffset(position, 0);
                }
            }
        });
    }

    /**
     * 列表
     */
    private void initRecyclerView() {


// use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerview.setHasFixedSize(true);

        mAdapter = new ContactRecyclerAdapter(tempExampleList, mContext);
        mAdapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showShort(mContext, tempExampleList.get(position).getName());

            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        recyclerview.setLayoutManager(mLayoutManager);
        recyclerview.addItemDecoration(new StickyRecyclerHeadersDecoration(mAdapter));
        // specify an adapter (see also next example)

        recyclerview.setAdapter(mAdapter);
    }

    /**
     * 直接使用出现过问题的两个例子
     * 如有需要，可以去掉相应的拼音内容，以查看是否出现问题
     * 注：加空格是因为SQLite的order by 在没加空格时会出现非预期排序
     */
//    private void initData() {
//        exampleList = new ArrayList<>();
//        tempExampleList = new ArrayList<>();
//        List<String> strings = new ArrayList<>();
//        strings.add("我");
//        strings.add("你");
//        strings.add("可");
//        strings.add("快");
//        strings.add("拉");
//        strings.add("倒");
//        strings.add("吧");
//        strings.add("吧");
//
//        InfoBean info;
//        for (int i = 0; i < strings.size(); i++) {
//            info = new InfoBean(strings.get(i), Cn2Spell.getPinYin(strings.get(i)));
//            exampleList.add(info);
////            info = new InfoBean("鲍" + i, "BAO 鲍 " + i);
////            exampleList.add(info);
//        }
////        for (int i = 0; i < 5; i++) {
////            info = new InfoBean("万国" + i, "WAN 万 GUO 国 " + i);
////            exampleList.add(info);
////            info = new InfoBean("王" + i, "WANG 王 " + i);
////            exampleList.add(info);
////        }
//        sortList();
//
//        tempExampleList.addAll(exampleList);
//    }

    /**
     *
     */
    private void sortList() {
        Collections.sort(exampleList);
    }


    @OnClick({R.id.circle_phone_friend, R.id.circle_group_chat, R.id.circle_nearby})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle_phone_friend:
                startActivity(new Intent(mContext, PhoneFriendActivity.class));
                break;
            case R.id.circle_group_chat:
                startActivity(new Intent(mContext, GroupChatActivity.class));
                break;
            case R.id.circle_nearby:
                startActivity(new Intent(mContext, NearlyActivity.class));
                break;
        }
    }
    //获取好友列表并排序
    private void getData() {
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.GetFriendList)
                .addParams("userid", userInfo.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        closeProgressDialog();
                        try {
                            ResponseHelper rh = new ResponseHelper(response);
                            if (rh.isSuccess()) {
                                String data = rh.getData();
                                List<FriendListBean> list = new Gson().fromJson(data,
                                        new TypeToken<List<FriendListBean>>() {
                                        }.getType());
                                exampleList.clear();
                                tempExampleList.clear();
                                for (int i = 0; i < list.size(); i++) {
                                    FriendListBean info = list.get(i);
                                    info = new FriendListBean(info.getId(), info.getName(), info.getPhoto(), info.getBirthday(), info.getTime(), info.getMorning_glucose(), Cn2Spell.getPinYin(info.getName()));
                                    exampleList.add(info);
                                }
                                sortList();

                                tempExampleList.addAll(exampleList);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.showShort(mContext, rh.getData());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}

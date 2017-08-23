package com.xiaocool.sugarangel.acyivity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.adapter.ContactRecyclerAdapter;
import com.xiaocool.sugarangel.adapter.OnRecyclerViewListener;
import com.xiaocool.sugarangel.adapter.PhoneFriendAdapter;
import com.xiaocool.sugarangel.bean.InfoBean;
import com.xiaocool.sugarangel.bean.PhoneFriendInfo;
import com.xiaocool.sugarangel.utils.Cn2Spell;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.xiaocool.sugarangel.view.ClearEditText;
import com.xiaocool.sugarangel.view.SideBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.Manifest.permission.READ_CONTACTS;

@RuntimePermissions()
public class PhoneFriendActivity extends BaseActivity {

    List<PhoneFriendInfo> phoneFriendList;
    List<PhoneFriendInfo> tempphoneFriendList;
    @BindView(R.id.resolver_list)
    RecyclerView resolverList;
    @BindView(R.id.filter_edit)
    ClearEditText filterEdit;
    @BindView(R.id.noResult)
    TextView noResult;
    @BindView(R.id.anno)
    TextView anno;
    @BindView(R.id.sideBar)
    SideBar sideBar;
    private PhoneFriendAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Context mContext;
    Cursor c;
    @Override
    public int getContentViewId() {
        return R.layout.activity_phone_friend;
    }

    @Override
    public void initView() {
        mContext = this;

        initSearchEdtit();
        initSideBar();
        initRecyclerView();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
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
        tempphoneFriendList.clear();
        if (TextUtils.isEmpty(s)) {
//            tempExampleList.addAll(sourceDateList);
            tempphoneFriendList.addAll(phoneFriendList);
        } else {
            PhoneFriendInfo tempInfoBean;
            int listSize = phoneFriendList.size();
            for (int i = 0; i < listSize; i++) {
                tempInfoBean = phoneFriendList.get(i);
                String[] str = tempInfoBean.getPinyin().split(" ");
                int strLength = str.length;
                for (int j = 0; j < strLength; j++) {
                    if (str[j].startsWith(s)) {
                        tempphoneFriendList.add(tempInfoBean);
                    }
                }
            }
        }

        //放置没有搜索结果的图片
        if (tempphoneFriendList.size() == 0) {
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
//                int position = adapter.getPosition(s);
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

        phoneFriendList = new ArrayList<>();
        tempphoneFriendList = new ArrayList<>();
// use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        resolverList.setHasFixedSize(true);

        mAdapter = new PhoneFriendAdapter(tempphoneFriendList, mContext);
        mAdapter.setOnRecyclerViewListener(new OnRecyclerViewListener() {
            @Override
            public void onItemClick(int position) {
                ToastUtils.showShort(mContext, tempphoneFriendList.get(position).getName());
            }

            @Override
            public boolean onItemLongClick(int position) {
                return false;
            }
        });

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(mContext);
        resolverList.setLayoutManager(mLayoutManager);
        resolverList.addItemDecoration(new StickyRecyclerHeadersDecoration(mAdapter));
        // specify an adapter (see also next example)

        resolverList.setAdapter(mAdapter);
        PhoneFriendActivityPermissionsDispatcher.getPhoneContactsWithCheck(this);
    }


    private static String getSortKey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        }else {
            return "#";
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhoneFriendActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
    //获取系统联系人，获取1000个联系人0.2秒，最快速
    @NeedsPermission(READ_CONTACTS)
    public void getPhoneContacts() {
        //联系人集合
        List<ContactsContract.Contacts> data = new ArrayList<>();
        ContentResolver resolver = mContext.getContentResolver();
        //搜索字段
        String[] projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY};
        // 获取手机联系人
        Cursor contactsCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                projection, null, null, null);
        if (contactsCursor != null) {
            //key: contactId,value: 该contactId在联系人集合data的index
            Map<Integer, Integer> contactIdMap = new HashMap<>();
            while (contactsCursor.moveToNext()) {
                //获取联系人的ID
                int contactId = contactsCursor.getInt(0);
                //获取联系人的姓名
                String name = contactsCursor.getString(2);
                //获取联系人的号码
                String phoneNumber = contactsCursor.getString(1);
                String sortKey =getSortKey(contactsCursor.getString(3)) ;

                phoneFriendList.add(new PhoneFriendInfo(name, phoneNumber, sortKey));
//               Collections.sort(phoneFriendList);
            }
            contactsCursor.close();
            tempphoneFriendList.addAll(phoneFriendList);
            Collections.sort(tempphoneFriendList);
            mAdapter.notifyDataSetChanged();
        }
    }
}

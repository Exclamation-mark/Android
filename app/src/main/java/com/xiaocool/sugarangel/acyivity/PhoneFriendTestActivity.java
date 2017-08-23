package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.adapter.OnRecyclerViewListener;
import com.xiaocool.sugarangel.adapter.PhoneFriendAdapter;
import com.xiaocool.sugarangel.bean.PhoneFriendInfo;
import com.xiaocool.sugarangel.utils.Cn2Spell;
import com.xiaocool.sugarangel.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static android.Manifest.permission.READ_CONTACTS;

@RuntimePermissions
public class PhoneFriendTestActivity extends BaseActivity {


    @BindView(R.id.resolver_list)
    RecyclerView resolverList;
    Context mContext;
    List<PhoneFriendInfo> phoneFriendList;
    List<PhoneFriendInfo> tempphoneFriendList;
    private PhoneFriendAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public int getContentViewId() {
        return R.layout.activity_phone_friend_test;
    }

    @Override
    public void initView() {
        mContext = this;
        phoneFriendList = new ArrayList<>();
        tempphoneFriendList = new ArrayList<>();
        initRecyclerView();

    }


    @NeedsPermission(READ_CONTACTS)
    public void readContacts() {
        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    //获取联系人姓名
                    String displayName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    //获取联系人手机号
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String sortKey = getSortKey(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.SORT_KEY_PRIMARY)));
                    phoneFriendList.add(new PhoneFriendInfo(displayName, number, sortKey));
                    Collections.sort(phoneFriendList);

                }

                tempphoneFriendList.addAll(phoneFriendList);
                mAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    //        / * 获取sort key的首个字符，如果是英文字母就直接返回，否则返回#。
//            *
//            * @param sortKeyString
//     * 数据库中读取出的sort key
//     * @return 英文字母或者#
//            */
    private static String getSortKey(String sortKeyString) {
        String key = sortKeyString.substring(0, 1).toUpperCase();
        if (key.matches("[A-Z]")) {
            return key;
        }
        return "#";
    }

    /**
     * 列表
     */
    private void initRecyclerView() {


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
        PhoneFriendTestActivityPermissionsDispatcher.readContactsWithCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PhoneFriendTestActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}

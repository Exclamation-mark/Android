package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.FriendListBean;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.net.ResponseHelper;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.xiaocool.sugarangel.view.ClearEditText;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class SearchFriendActivity extends BaseActivity {
    @BindView(R.id.filter_edit)
    ClearEditText filterEdit;
    @BindView(R.id.search_friend_xrv)
    XRecyclerView searchFriendXrv;
    private Context context;
    FriendListBean friendListBean;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search_friend;
    }


    @Override
    public void initView() {
        context = this;
        setTitleBar("添加病友", true);
        initSearch();

    }

    private void initSearch() {
        filterEdit.setOnKeyListener(new View.OnKeyListener() {

            @Override

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchFriendActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    //进行搜索操作的方法，在该方法中可以加入mEditSearchUser的非空判断
                    if (filterEdit.getText().toString().equals("")) {
                        ToastUtils.showShort(context, "请输入控糖id");
                    } else {
                        search(filterEdit.getText().toString());
                    }
                }
                return false;
            }
        });
    }

    private void search(String id) {
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.GetUserInfo)
                .addParams("userid", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort(context, "搜索失败，请检查您的网络");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        closeProgressDialog();
                        try {
                            ResponseHelper rh = new ResponseHelper(response);
                            if (rh.isSuccess()) {
                                Gson gson = new Gson();
                                friendListBean = gson.fromJson(rh.getData(), FriendListBean.class);
                                Intent intent = new Intent(context, CircleFriendDetialActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("friend", friendListBean);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

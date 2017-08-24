package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.adapter.DataAdapter;
import com.xiaocool.sugarangel.bean.HistoryDataBean;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.net.ResponseHelper;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class FriendNoteActivity extends BaseActivity {
    @BindView(R.id.data_Xlist)
    XRecyclerView dataXlist;
    private Context context;
    private ArrayList<HistoryDataBean> historyLists;
    DataAdapter dataAdapter;
    String id;

    @Override
    public int getContentViewId() {
        return R.layout.activity_friend_note;
    }

    @Override
    public void initView() {
        context = this;
        setTitleBar("好友康复笔记", true);
        historyLists = new ArrayList<>();
        id = getIntent().getStringExtra("id");
        initRecycler();
        readHistoryData();
    }

    /*初始化recyclerview*/
    private void initRecycler() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataXlist.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        dataXlist.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        dataXlist.setLayoutManager(linearLayoutManager);
        dataXlist.setHasFixedSize(true);
        dataAdapter = new DataAdapter(historyLists, context);
        dataXlist.setAdapter(dataAdapter);
        dataXlist.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                readHistoryData();
//                getOrder();
            }

            @Override
            public void onLoadMore() {
//                getMoreDatas(keyWord);
                dataXlist.loadMoreComplete();
            }
        });
    }

    /*
    * 获取好友康复笔记
    * */
    private void readHistoryData() {

        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.GetDeviceDataListByUserid)
                .addParams("userid", id)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        dataXlist.refreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dataXlist.refreshComplete();
                        try {
                            ResponseHelper rh = new ResponseHelper(response);
                            if (rh.isSuccess()) {
                                String data = rh.getData();
                                List<HistoryDataBean> s = new Gson().fromJson(data,
                                        new TypeToken<List<HistoryDataBean>>() {
                                        }.getType());
                                historyLists.clear();
                                historyLists.addAll(s);

                                dataAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.showShort(context, rh.getData());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();
                        dataXlist.refreshComplete();
                    }
                });

    }
}

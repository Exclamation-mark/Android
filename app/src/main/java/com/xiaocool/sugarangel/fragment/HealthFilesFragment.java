package com.xiaocool.sugarangel.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.acyivity.DataActivity;
import com.xiaocool.sugarangel.adapter.DataAdapter;
import com.xiaocool.sugarangel.adapter.HealthFilesAdapter;
import com.xiaocool.sugarangel.bean.FriendListBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.健康档案
 */
public class HealthFilesFragment extends BaseFragment {


    @BindView(R.id.health_files_list)
    XRecyclerView healthFilesList;
    Unbinder unbinder;
    Context mContext;
    HealthFilesAdapter healthFilesAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_health_files;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        setTitleBar("健康档案");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        healthFilesList.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        healthFilesList.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        healthFilesList.setLayoutManager(linearLayoutManager);
        healthFilesList.setHasFixedSize(true);
        healthFilesAdapter = new HealthFilesAdapter( mContext);
        healthFilesList.setAdapter(healthFilesAdapter);
        healthFilesList.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                healthFilesList.refreshComplete();
            }

            @Override
            public void onLoadMore() {
//                getMoreDatas(keyWord);
                healthFilesList.loadMoreComplete();
            }
        });
        healthFilesAdapter.setOnItemClickLitener(new HealthFilesAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(mContext, DataActivity.class));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
}

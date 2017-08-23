package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.view.MyNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelpActivity extends BaseActivity {


    @BindView(R.id.self_help)
    MyNavigationView selfHelp;
    @BindView(R.id.contact_customer_service)
    MyNavigationView contactCustomerService;
    Context mContext;

    @Override
    public int getContentViewId() {
        return R.layout.activity_help;
    }

    @Override
    public void initView() {
        mContext = this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.self_help, R.id.contact_customer_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.self_help:
                startActivity(new Intent(mContext, SelfhelpActivity.class));
                break;
            case R.id.contact_customer_service:
                break;
        }
    }
}

package com.xiaocool.sugarangel.acyivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.xiaocool.sugarangel.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GenderSelectActivity extends BaseActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.setting_gender_man)
    LinearLayout settingGenderMan;
    @BindView(R.id.setting_gender_woman)
    LinearLayout settingGenderWoman;
    Intent intent = new Intent();
    @Override
    public int getContentViewId() {
        return R.layout.activity_gender_select;
    }

    @Override
    public void initView() {
        setTitleBar("性别", true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.setting_gender_man, R.id.setting_gender_woman})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_gender_man:

                intent.putExtra("sex", "1");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.setting_gender_woman:
                intent.putExtra("sex", "0");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

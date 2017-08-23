package com.xiaocool.sugarangel.acyivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiaocool.sugarangel.R;

public class HealthFilesDetialsActivity extends BaseActivity {


    @Override
    public int getContentViewId() {
        return R.layout.activity_health_files_detials;
    }

    @Override
    public void initView() {
        setTitleBar("详细信息", true);
    }
}

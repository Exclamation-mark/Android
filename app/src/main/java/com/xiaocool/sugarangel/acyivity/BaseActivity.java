package com.xiaocool.sugarangel.acyivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.helper.ActivityCollector;


import butterknife.ButterKnife;


/**
 * Created by hzh on 16/11/22.
 * 此层封装头布局
 */

public abstract class BaseActivity extends AppCompatActivity {
    private final String TAG = "BaseActivity";
    private TextView titleTv, customTxtBtn;
    private View appBar;
    private ImageView backBtn, customBtn;
    private KProgressHUD hud;//Loading加载框

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);//子类使用ButterKnife时不用在调用此方法
        ActivityCollector.getInstance().addActivity(this);
        Log.d(TAG, getClass().getSimpleName());
        initView();
    }

    public abstract int getContentViewId();

    public abstract void initView();

    /**
     * 通用titlebar 使用时在子类调用此方法，布局文件中include toolbar
     *
     * @param title
     * @param showBackBtn 是否显示返回按钮
     */
    public void setTitleBar(String title, boolean showBackBtn) {
        appBar = findViewById(R.id.common_app_bar);
        titleTv = (TextView) appBar.findViewById(R.id.title);
        backBtn = (ImageView) appBar.findViewById(R.id.back);
        titleTv.setText(title);
        if (showBackBtn) {
            backBtn.setVisibility(View.VISIBLE);
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        } else {
            backBtn.setVisibility(View.GONE);
        }

    }


    /**
     * 右侧带有按钮的titlebar
     *
     * @param title
     * @param showBackBtn
     * @param imageId         右侧按钮的图片资源
     * @param onClickListener
     */
    public void setTitleBar(String title, boolean showBackBtn, int imageId,
                            View.OnClickListener onClickListener) {
        this.setTitleBar(title, showBackBtn);
        customBtn = (ImageView) appBar.findViewById(R.id.custom_btn);
        customBtn.setImageResource(imageId);
        customBtn.setOnClickListener(onClickListener);
    }

    /**
     * 右侧带文字的titlebar
     *
     * @param title
     * @param showBackBtn
     * @param customTxt
     * @param onClickListener
     */
    public void setTitleBar(String title, boolean showBackBtn, String customTxt,
                            View.OnClickListener onClickListener) {
        this.setTitleBar(title, showBackBtn);
        customTxtBtn = (TextView) appBar.findViewById(R.id.custom_text_btn);
        customTxtBtn.setText(customTxt);
        customTxtBtn.setOnClickListener(onClickListener);
    }

    /**
     * @param cancellable 返回键是否可取消
     *                    Loading加载框
     */
    public void showProgressDialog(boolean cancellable) {
        if (hud == null) {
            hud = KProgressHUD.create(this).setCancellable(cancellable).show();
        }
    }

    /**
     * @param cancellable
     * @param label       //提示信息
     */
    public void showProgressDialog(boolean cancellable, String label) {
        hud = KProgressHUD.create(this).setCancellable(cancellable).setLabel(label).show();
    }

    public void closeProgressDialog() {
        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeProgressDialog();
        ActivityCollector.getInstance().removeActivity(this);

//        RefWatcher refWatcher = HappyNineApplication.getRefWatcher(this);
//        refWatcher.watch(this);
    }

}

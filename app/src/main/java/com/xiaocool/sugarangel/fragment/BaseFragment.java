package com.xiaocool.sugarangel.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.xiaocool.sugarangel.R;


import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hzh on 16/11/24.
 * 此层封装头布局
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder unbinder;
    private TextView titleTv,custom_text_btn;
    private View appBar;
    private ImageView customBtn, backBtn;
    private View view;
    private KProgressHUD hud;//Loading加载框


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    public abstract int getContentViewId();

    public abstract void initView();

    /**
     * 通用titlebar 使用时在子类调用此方法，布局文件中include toolbar
     *
     * @param title
     */
    public void setTitleBar(String title) {
        appBar = view.findViewById(R.id.common_app_bar);
        titleTv = (TextView) appBar.findViewById(R.id.title);
        titleTv.setText(title);
        backBtn = (ImageView) appBar.findViewById(R.id.back);
        backBtn.setVisibility(View.GONE);
    }
    public void setTitleBar(String title,String customTitle,View.OnClickListener onClickListener) {
        appBar = view.findViewById(R.id.common_app_bar);
        titleTv = (TextView) appBar.findViewById(R.id.title);
        titleTv.setText(title);
        backBtn = (ImageView) appBar.findViewById(R.id.back);
        backBtn.setVisibility(View.GONE);
        custom_text_btn = (TextView)appBar.findViewById(R.id.custom_text_btn);
        custom_text_btn.setText(customTitle);
        custom_text_btn.setOnClickListener(onClickListener);
    }


    /**
     * 右侧带有按钮的titlebar
     *
     * @param title
     * @param imageId         右侧按钮的图片资源
     * @param onClickListener
     */
    public void setTitleBar(String title, int imageId,
                            View.OnClickListener onClickListener) {
        this.setTitleBar(title);
        customBtn = (ImageView) appBar.findViewById(R.id.custom_btn);
        customBtn.setImageResource(imageId);
        customBtn.setOnClickListener(onClickListener);
    }

    /**
     * @param cancellable 返回键是否可取消
     *                    Loading加载框
     */
    public void showProgressDialog(boolean cancellable) {
        if (hud == null) {
            hud = KProgressHUD.create(getContext()).setCancellable(cancellable).show();
        }
    }

    public void showProgressDialog(boolean cancellable, String label) {
        hud = KProgressHUD.create(getContext()).setCancellable(cancellable).setLabel(label).show();
    }

    public void closeProgressDialog() {
        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        closeProgressDialog();
        unbinder.unbind();//fragment中ButterKnife需要解绑
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = HappyNineApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }
}

package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.Tab;
import com.xiaocool.sugarangel.fragment.HealthCircleFragment;
import com.xiaocool.sugarangel.fragment.ChatFragment;
import com.xiaocool.sugarangel.fragment.HealthFilesFragment;
import com.xiaocool.sugarangel.fragment.MineFragment;
import com.xiaocool.sugarangel.utils.FragmentSwitcher;
import com.xiaocool.sugarangel.view.FragmentTabHost;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
/*
* 主界面转到homeActivity
* */
public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {
    @BindView(R.id.layFrame)
    FrameLayout layFrame;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private ArrayList<Fragment> fragments;

    FragmentSwitcher fragmentSwitcher;
    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC
                );
        BadgeItem numberBadgeItem = new BadgeItem()
                .setBorderWidth(4)
                .setBackgroundColor(Color.RED)
                .setText("5")
                .setHideOnSelect(true);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_selecter_home, "消息").setActiveColorResource(R.color.bottom_text_blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_selecter_ring, "健康圈").setActiveColorResource(R.color.bottom_text_blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_selecter_file, "健康档案").setActiveColorResource(R.color.bottom_text_blue))
                .addItem(new BottomNavigationItem(R.drawable.ic_selecter_mine, "我的").setActiveColorResource(R.color.bottom_text_blue))

                .setFirstSelectedPosition(0)

                .initialise();

        fragmentSwitcher = new FragmentSwitcher(getSupportFragmentManager(),R.id.layFrame);
        fragmentSwitcher.addFragment("f1",new ChatFragment());
        fragmentSwitcher.addFragment("f2",new HealthCircleFragment());
        fragmentSwitcher.addFragment("f3",new HealthFilesFragment());
        fragmentSwitcher.addFragment("f4",new MineFragment());
        fragmentSwitcher.showFragment("f1");
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int i) {
        switch (i){
            case 0:
                fragmentSwitcher.showFragment("f1");
                break;
            case 1:
                fragmentSwitcher.showFragment("f2");
                break;
            case 2:
                fragmentSwitcher.showFragment("f3");
                break;
            case 3:
                fragmentSwitcher.showFragment("f4");
                break;
        }

    }

    @Override
    public void onTabUnselected(int i) {
//        if (fragments != null) {
//            if (i < fragments.size()) {
//                FragmentManager fm = getSupportFragmentManager();
//                FragmentTransaction ft = fm.beginTransaction();
//                Fragment fragment = fragments.get(i);
//                ft.remove(fragment);
//                ft.commitAllowingStateLoss();
//            }
//        }

    }

    @Override
    public void onTabReselected(int i) {

    }
}

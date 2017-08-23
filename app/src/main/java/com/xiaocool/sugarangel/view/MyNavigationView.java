package com.xiaocool.sugarangel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xiaocool.sugarangel.R;


/**
 * Created by hzh on 16/12/14.
 * 组合布局 拥有一个icon 一个title 一个hidetext 一个右侧箭头
 * 适用于我的页面
 */

public class MyNavigationView extends RelativeLayout {
    private TextView titltTv, hideTv;
    private String title, hide;
    private int resource,arrow;
    private int titleColor;
    private ImageView iconIv, arrowImg;
    private boolean showArrow;//是否显示箭头,默认显示

    public MyNavigationView(Context context) {
        super(context);
        initView(context);
    }

    public MyNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.navigation_combination);
        title = a.getString(R.styleable.navigation_combination_title_text);
        hide = a.getString(R.styleable.navigation_combination_hide_text);
        resource = a.getResourceId(R.styleable.navigation_combination_icon_src, 0);
        arrow =  a.getResourceId(R.styleable.navigation_combination_arrow_src, R.drawable.wodejiantou);
        showArrow = a.getBoolean(R.styleable.navigation_combination_show_arrow, true);
        titleColor = a.getColor(R.styleable.navigation_combination_title_color,getResources().getColor(R.color.text_black));
        initView(context);
        a.recycle();
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.navigation_combinate_view, this, true);
        titltTv = (TextView) view.findViewById(R.id.title);
        hideTv = (TextView) view.findViewById(R.id.hide_text);
        iconIv = (ImageView) view.findViewById(R.id.icon);
        arrowImg = (ImageView) view.findViewById(R.id.arrow);
        titltTv.setText(title);
        titltTv.setTextColor(titleColor);
        hideTv.setText(hide);
        if (resource != 0) {
            iconIv.setImageResource(resource);
            iconIv.setVisibility(View.VISIBLE);
        } else {
            iconIv.setVisibility(GONE);
        }
        if (showArrow) {
            arrowImg.setImageResource(arrow);
            arrowImg.setVisibility(View.VISIBLE);
        } else {
            arrowImg.setVisibility(View.GONE);
        }
    }

    public void setHideStr(String text) {
        hideTv.setText(text);
    }

    public void setTitleStr(String text) {
        titltTv.setText(text);
    }

    public String getTitleStr(){
        return titltTv.getText().toString();
    }

    public String getHideStr() {
        return hideTv.getText().toString();
    }

    public void hideArrow() {
        arrowImg.setVisibility(View.GONE);
    }
}

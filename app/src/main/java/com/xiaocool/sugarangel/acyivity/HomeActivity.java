//package com.xiaocool.sugarangel.acyivity;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentTabHost;
//import android.support.v7.app.AppCompatActivity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.TabHost;
//import android.widget.TextView;
//
//import com.xiaocool.sugarangel.R;
//import com.xiaocool.sugarangel.fragment.BaseFragment;
//import com.xiaocool.sugarangel.fragment.ChatFragment;
//import com.xiaocool.sugarangel.fragment.HealthCircleFragment;
//import com.xiaocool.sugarangel.fragment.HealthFilesFragment;
//import com.xiaocool.sugarangel.fragment.MineFragment;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class HomeActivity extends BaseActivity {
//
//    @BindView(R.id.realtabcontent)
//    FrameLayout realtabcontent;
//    @BindView(android.R.id.tabcontent)
//    FrameLayout tabcontent;
//    @BindView(android.R.id.tabhost)
//    FragmentTabHost tabhost;
//    private LayoutInflater layoutInflater;
//    private Class fragmentArray[] = {ChatFragment.class, HealthCircleFragment.class, HealthFilesFragment.class, MineFragment.class};
//    private int mImageViewArray[] = {R.drawable.ic_selecter_home, R.drawable.ic_selecter_ring, R.drawable.ic_selecter_file, R.drawable.ic_selecter_mine};
//    private String mTextviewArray[] = {"消息", "健康圈", "健康档案", "我的"};
//
//    @Override
//    public int getContentViewId() {
//        return R.layout.activity_home;
//    }
//
//    @Override
//    public void initView() {
//        layoutInflater = LayoutInflater.from(this);
//        tabhost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
//        int count = fragmentArray.length;
//        for (int i = 0; i < count; i++) {
//
//            TabHost.TabSpec tabSpec = tabhost.newTabSpec(mTextviewArray[i]).setIndicator(getTabItemView(i));
//
//            tabhost.addTab(tabSpec, fragmentArray[i], null);
//
////            tabhost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.selector_tab_background);
//        }
//    }
//    private View getTabItemView(int index) {
//        View view = layoutInflater.inflate(R.layout.tab_indicator, null);
//
//        ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);
//        imageView.setImageResource(mImageViewArray[index]);
//
//        TextView textView = (TextView) view.findViewById(R.id.txt_indicator);
//        textView.setText(mTextviewArray[index]);
//
//        return view;
//    }
//}

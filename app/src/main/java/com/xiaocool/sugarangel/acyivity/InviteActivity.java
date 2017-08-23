package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InviteActivity extends BaseActivity {

    @BindView(R.id.gview)
    GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = {R.drawable.wx, R.drawable.wx,
            R.drawable.wx, R.drawable.wx, R.drawable.wx,
            R.drawable.wx};
    private String[] iconName = {"微信", "朋友圈", "微博", "QQ", "QQ空间", "短信"};
    Context mContext;


    @Override
    public int getContentViewId() {
        return R.layout.activity_invite;
    }

    @Override
    public void initView() {
        mContext = this;
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from = {"image", "text"};
        int[] to = {R.id.image, R.id.text};
        sim_adapter = new SimpleAdapter(mContext, data_list, R.layout.gview_item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (data_list.get(i).get("text").toString()) {
                    case "QQ":
                        ToastUtils.showShort(mContext, "111");
                        break;
                    default:
                        ToastUtils.showShort(mContext, "222");
                        break;
                }
            }
        });
    }

    public List<Map<String, Object>> getData() {
        //cion和iconName的长度是相同的，这里任选其一都可以
        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }
}

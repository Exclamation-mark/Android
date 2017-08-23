package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.RelativesBean;
import com.xiaocool.sugarangel.view.CircleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DrugManagementActivity extends BaseActivity {


    @BindView(R.id.drug_manage_list)
    ListView drugManageList;
    DrugManageAdapter drugManageAdapter;
    Context mContext;
    @Override
    public int getContentViewId() {
        return R.layout.activity_drug_management;
    }

    @Override
    public void initView() {
        mContext = this;
        drugManageAdapter = new DrugManageAdapter(mContext);
        drugManageList.setAdapter(drugManageAdapter);
    }
    static class DrugManageAdapter extends BaseAdapter {

        private Context context;


        public DrugManageAdapter(Context context) {

            this.context = context;

        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_drugmanagement, parent, false);
            ViewHolder holder;
            if (convertView == null) {
                convertView = view;
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            return convertView;
        }

        static class ViewHolder {


            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }

}

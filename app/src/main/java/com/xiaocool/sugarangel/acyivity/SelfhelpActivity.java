package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.RelativesBean;
import com.xiaocool.sugarangel.view.CircleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelfhelpActivity extends BaseActivity {


    @BindView(R.id.self_help_list)
    ListView selfHelpList;
    SelfHelpAdapter selfHelpAdapter;
    Context mContext;

    @Override
    public int getContentViewId() {
        return R.layout.activity_selfhelp;
    }

    @Override
    public void initView() {
        mContext = this;
        selfHelpAdapter = new SelfHelpAdapter(mContext);
        selfHelpList.setAdapter(selfHelpAdapter);
    }

    static class SelfHelpAdapter extends BaseAdapter {

        private Context context;


        public SelfHelpAdapter(Context context) {

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
            View view = LayoutInflater.from(context).inflate(R.layout.item_self_help, parent, false);
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

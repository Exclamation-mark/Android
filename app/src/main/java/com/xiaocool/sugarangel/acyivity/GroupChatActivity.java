package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xiaocool.sugarangel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupChatActivity extends BaseActivity {

    @BindView(R.id.group_chat_list)
    ListView groupChatList;
    GroupChatAdapter groupChatAdapter;
    Context mContext;

    @Override
    public int getContentViewId() {
        return R.layout.activity_group_chat;
    }

    @Override
    public void initView() {
        mContext = this;
        groupChatAdapter = new GroupChatAdapter(mContext);
        groupChatList.setAdapter(groupChatAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    static class GroupChatAdapter extends BaseAdapter {

        private Context context;


        public GroupChatAdapter(Context context) {

            this.context = context;

        }

        @Override
        public int getCount() {
            return 4;
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_group_chat, parent, false);
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

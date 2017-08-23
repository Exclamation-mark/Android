package com.xiaocool.sugarangel.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.acyivity.ChatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.消息
 */
public class ChatFragment extends BaseFragment {


    @BindView(R.id.drug_list)
    ListView drugList;
    Unbinder unbinder;
    Context mContext;
    ChatAdapter chatAdapter;
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_drug_administration;
    }

    @Override
    public void initView() {
        mContext = getActivity();
        chatAdapter = new ChatAdapter(mContext);
        drugList.setAdapter(chatAdapter);
        drugList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(mContext, ChatActivity.class));
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    static class ChatAdapter extends BaseAdapter {

        private Context context;


        public ChatAdapter(Context context) {

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
            View view = LayoutInflater.from(context).inflate(R.layout.item_chat, parent, false);
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

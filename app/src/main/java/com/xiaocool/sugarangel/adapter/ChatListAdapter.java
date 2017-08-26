package com.xiaocool.sugarangel.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mylibrary.Chat.ChatItemView;
import com.example.mylibrary.Chat.OnChatViewClickListener;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.ChatDataItemBean;

import java.util.List;

import com.example.mylibrary.Chat.ChatType.*;
import com.example.mylibrary.Chat.photoType;

/**
 * Created by 赵自强 on 2017/8/25 20:26.
 * 这个类的用处
 */
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.viewHolder> {
    private List<ChatDataItemBean> list;
    private Context context;
    private int LayoutId;

    public ChatListAdapter(List<ChatDataItemBean> list, Context context, int layoutId) {
        this.list = list;
        this.context = context;
        LayoutId = layoutId;
    }

    public void setList(List<ChatDataItemBean> list) {
        this.list = list;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(LayoutInflater.from(context).inflate(LayoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, final int position) {
        ChatDataItemBean chatItemView = list.get(position);
        switch (chatItemView.getChatType()){
            case textLeft:
                Log.i("看左边文字",""+chatItemView.getText());
                holder.chatItemView.setLeftTextType(chatItemView.getHisname(),chatItemView.getText(),chatItemView.getHisPhoto());
                break;
            case textRight:
                Log.i("看右边文字",""+chatItemView.getText());
                holder.chatItemView.setRightTextType("我",chatItemView.getText(),chatItemView.getMyPhoto());
                break;
            case pictureLeft:
                holder.chatItemView.setLeftPictureType(chatItemView.getHisname(),chatItemView.getPicturePath(),chatItemView.getHisPhoto());
                break;
            case pictureRight:
                holder.chatItemView.setRightPictureType("我",chatItemView.getPicturePath(),chatItemView.getMyPhoto());
                break;
            case voiceLeft:
                holder.chatItemView.setLeftVoiceType(chatItemView.getHisname(),chatItemView.getDurtation(),chatItemView.getHisPhoto());
                break;
            case voiceRight:
                holder.chatItemView.setRightVoiceType("我",chatItemView.getDurtation(),chatItemView.getMyPhoto());
                break;
        }
        holder.chatItemView.setOnChatViewClickListener(new OnChatViewClickListener() {
            /*头像的点事件监听*/
            @Override
            public void OnPhotoClicked(View view, photoType photoType) {
                switch ( photoType){
                    case left:
                        Log.i("看","看" + "    "+ position+"条数据：" +"左头像被点击");
                        break;
                    case right:
                        Log.i("看","看" + "    "+ position+"条数据：" +"右头像被点击");
                        break;
                }
            }

            @Override
            public void OnTextViewLongClicked(View view) {
                Log.i("看","看" + "    "+ position+"条数据：" +"文本被长按");
            }

            @Override
            public void OnPlayButtonClickedListener(View view) {
                Log.i("看","看" + "    "+ position+"条数据：" +"播放按钮被点击");
            }

            @Override
            public void OnPictureClickedListener(View view) {
                Log.i("看","看" + "    "+ position+"条数据：" +"照片被点击");
            }

            @Override
            public void OnPictureLongClickedListener(View view) {
                Log.i("看","看" + "    "+ position+"条数据：" +"照片被长按");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    final class viewHolder extends RecyclerView.ViewHolder {
        private ChatItemView chatItemView;
        public viewHolder(View itemView) {
            super(itemView);
            chatItemView = (ChatItemView) itemView.findViewById(R.id.c);
        }
    }
}

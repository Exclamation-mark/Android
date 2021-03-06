package com.xiaocool.sugarangel.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.acyivity.CircleFriendDetialActivity;
import com.xiaocool.sugarangel.bean.FriendListBean;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.utils.DateUtils;
import com.xiaocool.sugarangel.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by TpOut on 2016/7/22.
 * Email: 416756910@qq.com
 */
public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ViewHolder>
        implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private List<FriendListBean> mDataList;
    private OnRecyclerViewListener onRecyclerViewListener;
    Context context;

    public void setOnRecyclerViewListener(OnRecyclerViewListener onRecyclerViewListener) {
        this.onRecyclerViewListener = onRecyclerViewListener;
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        // each data item is just a string in this case

        public int vhPosition;
        @BindView(R.id.circle_av)
        ImageView circleAv;
        @BindView(R.id.circle_name)
        TextView circleName;
        @BindView(R.id.circle_age)
        TextView circleAge;
        @BindView(R.id.circle_day)
        TextView circleDay;
        @BindView(R.id.circle_sugar)
        TextView circleSugar;
        @BindView(R.id.circle_diseases)
        TextView circleDiseases;
        public ViewHolder(View v) {
            super(v);

            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onRecyclerViewListener != null) {
                onRecyclerViewListener.onItemClick(vhPosition);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if (onRecyclerViewListener != null) {
                onRecyclerViewListener.onItemLongClick(vhPosition);
            }
            return false;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactRecyclerAdapter(List<FriendListBean> mDataList, Context context) {
        this.mDataList = mDataList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_health_circle, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final FriendListBean friendListBean =mDataList.get(position);
        holder.vhPosition = holder.getAdapterPosition();
        holder.circleName.setText(friendListBean.getName());
//        holder.circleAge.setText(friendListBean.g);
        GlideUtils.loadRectangImageView(context, NetConstant.SHOW_IMAGE+friendListBean.getPhoto(),holder.circleAv);
        String time = ((DateUtils.dateToUnixTimestamp()/1000-Integer.parseInt(friendListBean.getTime())) )/86400+"";
        int age = (Integer.parseInt(DateUtils.getNowTime("yyyyMMdd"))-Integer.parseInt(DateUtils.getStrTime(friendListBean.getBirthday(),"yyyyMMdd")));
        holder.circleDay.setText(time+"天");
        holder.circleAge.setText(age/10000+"岁");
        holder.circleSugar.setText(friendListBean.getMorning_glucose()+"mmol/L");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CircleFriendDetialActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("friend",friendListBean);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public long getHeaderId(int position) {
        return getItemSortLetter(position).charAt(0);
    }

    public String getItemSortLetter(int position) {
        return mDataList.get(position).getPinyin().substring(0, 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = (TextView) holder.itemView;
        String showValue = getItemSortLetter(position);

        textView.setText(showValue);
    }

    public int getPositionForSection(char section) {
        for (int i = 0; i < getItemCount(); i++) {
            char firstChar = mDataList.get(i).getPinyin().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;

    }

}

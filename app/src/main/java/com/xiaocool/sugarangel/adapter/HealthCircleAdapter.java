package com.xiaocool.sugarangel.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.view.MyNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class HealthCircleAdapter extends RecyclerView.Adapter<HealthCircleAdapter.Holder> {
    private Context context;
    private OnItemClickLitener listener;


    public HealthCircleAdapter(Context context) {
        this.context = context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(LayoutInflater.from(context).inflate(R.layout.item_health_circle, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
    }

        @Override
        public int getItemCount () {
            return 10;
        }

        class Holder extends RecyclerView.ViewHolder {


            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }

    private long getRadomnumber(int max, int min) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 自定义RecyclerView的点击事件
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
        //void onItemLongClick(View view , int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.listener = mOnItemClickLitener;
    }


}


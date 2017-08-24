package com.xiaocool.sugarangel.adapter;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.HistoryDataBean;
import com.xiaocool.sugarangel.utils.DateUtils;

import java.util.List;
import java.util.Map;
import java.util.Random;
import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/5/24 0024.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {
    private List<HistoryDataBean> list;
    private Context context;
    private OnItemClickLitener listener;


    public DataAdapter(List<HistoryDataBean> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(LayoutInflater.from(context).inflate(R.layout.data_list_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        HistoryDataBean item = list.get(position);
        long date = Long.parseLong(item.getDatatime());
        holder.dataTime.setText(DateUtils.getStrTime(date+"","yyyy-MM-dd HH:mm:ss"));
        holder.bloodSugar.setText(item.getBloodsugar() + "mmol/L");

        if (item.getSampletype().equals(" 0")) {
            holder.sampleType.setText("血液");
        } else if (item.getSampletype().equals("1")) {
            holder.sampleType.setText("质控液");
        }
        holder.temperature.setText(item.getTemperature() + "℃");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.data_time)
        TextView dataTime;
        @BindView(R.id.blood_sugar)
        TextView bloodSugar;
        @BindView(R.id.sample_type)
        TextView sampleType;
        @BindView(R.id.temperature)
        TextView temperature;
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


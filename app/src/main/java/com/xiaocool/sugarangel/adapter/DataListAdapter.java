package com.xiaocool.sugarangel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sinocare.domain.BloodSugarData;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.HistoryDataBean;
import com.xiaocool.sugarangel.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class DataListAdapter extends BaseAdapter {
    private ArrayList<HistoryDataBean> list;

    private Context context;

    public DataListAdapter(Context context, ArrayList<HistoryDataBean> list2) {
        list = list2;
        this.context = context;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getItemViewType(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.data_list_item, parent, false);
        final ViewHolder holder;
        HistoryDataBean item = list.get(position);
        if (convertView == null) {
            convertView = view;
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        long date = Long.parseLong(item.getDatatime())*1000;
        holder.dataTime.setText(DateUtils.getStrTime(date+"","yyyy-MM-dd HH:mm:ss"));
        holder.bloodSugar.setText(item.getBloodsugar() + "mmol/L");

        if (item.getSampletype().equals(" 0")) {
            holder.sampleType.setText("血液");
        } else if (item.getSampletype().equals("1")) {
            holder.sampleType.setText("质控液");
        }
        holder.temperature.setText(item.getTemperature() + "℃");
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.data_time)
        TextView dataTime;
        @BindView(R.id.blood_sugar)
        TextView bloodSugar;
        @BindView(R.id.sample_type)
        TextView sampleType;
        @BindView(R.id.temperature)
        TextView temperature;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}

package com.xiaocool.sugarangel.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.acyivity.DataActivity;
import com.xiaocool.sugarangel.acyivity.HealthFilesDetialsActivity;
import com.xiaocool.sugarangel.bean.HistoryDataBean;
import com.xiaocool.sugarangel.utils.ChartUtils;
import com.xiaocool.sugarangel.utils.DateUtils;
import com.xiaocool.sugarangel.view.MyNavigationView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/6/12 0012.
 */

public class HealthFilesAdapter extends RecyclerView.Adapter<HealthFilesAdapter.Holder> {
    private Context context;
    private OnItemClickLitener listener;

    public HealthFilesAdapter(Context context) {
        this.context = context;

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        Holder holder = new Holder(LayoutInflater.from(context).inflate(R.layout.item_health_files, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {
        ChartUtils.initChart(holder.chart);
        ChartUtils.notifyDataSetChanged(holder.chart);
        holder.detailsNv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context,HealthFilesDetialsActivity.class));
            }
        });
        holder.chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, DataActivity.class));
            }
        });
        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<Entry> values2 = new ArrayList<>();
        ArrayList<Entry> values3 = new ArrayList<>();

        values1.add(new Entry(0, 7));
        values1.add(new Entry(1, 9));
        values1.add(new Entry(2, 5));
        values1.add(new Entry(3, 8));
        values1.add(new Entry(4, 3));
        values1.add(new Entry(5, 7));
        values1.add(new Entry(6, 8));

        values2.add(new Entry(0, 7));
        values2.add(new Entry(1, 8));
        values2.add(new Entry(2, 6));
        values2.add(new Entry(3, 2));
        values2.add(new Entry(4, 4));
        values2.add(new Entry(5, 2));
        values2.add(new Entry(6, 9));

        values3.add(new Entry(0, 5));
        values3.add(new Entry(1, 8));
        values3.add(new Entry(2, 6));
        values3.add(new Entry(3, 7));
        values3.add(new Entry(4, 5));
        values3.add(new Entry(5, 2));
        values3.add(new Entry(6, 10));
        //LineDataSet每一个对象就是一条连接线
        LineDataSet set1;
        LineDataSet set2;
        LineDataSet set3;

        //判断图表中原来是否有数据
        if (holder.chart.getData() != null &&
                holder.chart.getData().getDataSetCount() > 0) {
            //获取数据1
            set1 = (LineDataSet) holder.chart.getData().getDataSetByIndex(0);
            set1.setValues(values1);
            set2 = (LineDataSet) holder.chart.getData().getDataSetByIndex(1);
            set2.setValues(values2);
            //刷新数据
            holder.chart.getData().notifyDataChanged();
            holder.chart.notifyDataSetChanged();
        } else {
            //设置数据1  参数1：数据源 参数2：图例名称
            set1 = new LineDataSet(values1, "测试数据1");
            set1.setColor(Color.parseColor("#558ef7"));
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);//设置线宽
            set1.setCircleRadius(3f);//设置焦点圆心的大小
            set1.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
            set1.setHighlightLineWidth(2f);//设置点击交点后显示高亮线宽
            set1.setHighlightEnabled(false);//是否禁用点击高亮线
            set1.setHighLightColor(Color.RED);//设置点击交点后显示交高亮线的颜色
            set1.setValueTextSize(0f);//设置显示值的文字大小
            set1.setDrawFilled(false);//设置禁用范围背景填充

            //格式化显示数据
            final DecimalFormat mFormat = new DecimalFormat("###,###,##0");
            set1.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return mFormat.format(value);
                }
            });
//            if (Utils.getSDKInt() >= 18) {
//                // fill drawable only supported on api level 18 and above
//                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.denglu);
//                set1.setFillDrawable(drawable);//设置范围背景填充
//            } else {
//                set1.setFillColor(Color.BLACK);
//            }

            //设置数据2
            set2 = new LineDataSet(values2, "测试数据2");
            set2.setColor(Color.parseColor("#f39800"));
            set2.setCircleColor(Color.GRAY);
            set2.setLineWidth(1f);
            set2.setCircleRadius(3f);
            set2.setValueTextSize(0f);
            set2.setHighlightEnabled(false);//是否禁用点击高亮线
            //设置数据3
            set3 = new LineDataSet(values3, "测试数据3");
            set3.setColor(Color.parseColor("#7de78f"));
            set3.setCircleColor(Color.GRAY);
            set3.setLineWidth(1f);
            set3.setCircleRadius(3f);
            set3.setValueTextSize(0f);
            set3.setHighlightEnabled(false);//是否禁用点击高亮线
            //保存LineDataSet集合
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1); // add the datasets
            dataSets.add(set2);
            dataSets.add(set3);
            //创建LineData对象 属于LineChart折线图的数据集合
            LineData data = new LineData(dataSets);
            // 添加到图表中
            holder.chart.setData(data);
            //绘制图表
            holder.chart.invalidate();


        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.chart)
        LineChart chart;
        @BindView(R.id.details_nv)
        MyNavigationView detailsNv;
        @BindView(R.id.bind_device_nv)
        MyNavigationView bindDeviceNv;


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

    private List<Entry> getData() {

        List<List<Entry>> value = new ArrayList<>();
        List<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 0));
        values.add(new Entry(1, 2));
        values.add(new Entry(2, 3));
        values.add(new Entry(3, 4));
        values.add(new Entry(4, 5));
        values.add(new Entry(5, 6));
        values.add(new Entry(6, 10));

        return values;
    }
}

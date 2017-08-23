package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.RelativesBean;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.net.ResponseHelper;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.xiaocool.sugarangel.view.CircleImageView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class PatientManagementActivity extends BaseActivity {


    @BindView(R.id.patient_management_list)
    ListView patientManagementList;
    Context mContext;
    private PatientAdapter patientAdapter;
    private ArrayList<RelativesBean> relativesList;

    @Override
    public int getContentViewId() {
        return R.layout.activity_patient_management;
    }

    @Override
    public void initView() {
        mContext = this;

        relativesList = new ArrayList<>();
        patientAdapter = new PatientAdapter(mContext, relativesList);
        patientManagementList.setAdapter(patientAdapter);
        getRelatives();
    }

    //获取亲属列表
    private void getRelatives() {
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.GET_USER_RELATION_SHIP)
                .addParams("userid", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showShort(mContext, "网络连接失败，请检查您的网络");
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            ResponseHelper rh = new ResponseHelper(response);
                            if (rh.isSuccess()) {
                                String data = rh.getData();
                                List<RelativesBean> list = new Gson().fromJson(data,
                                        new TypeToken<List<RelativesBean>>() {
                                        }.getType());
                                relativesList.clear();
                                relativesList.addAll(list);
                                patientAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();
                    }

                });
    }

    static class PatientAdapter extends BaseAdapter {
        ArrayList<RelativesBean> list;
        private Context context;


        public PatientAdapter(Context context, ArrayList<RelativesBean> list) {
            this.list = list;
            this.context = context;

        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_patient_manage, parent, false);
            ViewHolder holder;
            if (convertView == null) {
                convertView = view;
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.patientName.setText(list.get(position).getName());
            holder.patientRelationship.setText(list.get(position).getType());
            return convertView;
        }

        static class ViewHolder {
            @BindView(R.id.patient_ava)
            CircleImageView patientAva;
            @BindView(R.id.patient_name)
            TextView patientName;
            @BindView(R.id.patient_relationship)
            TextView patientRelationship;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }

    }
}

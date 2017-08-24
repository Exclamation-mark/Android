package com.xiaocool.sugarangel.acyivity;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.sinocare.Impl.SC_CmdCallBack;
import com.sinocare.Impl.SC_CurrentDataCallBack;
import com.sinocare.Impl.SC_DataCallBack;
import com.sinocare.domain.BloodSugarData;
import com.sinocare.handler.SN_MainHandler;
import com.sinocare.protocols.ProtocolVersion;
import com.sinocare.status.SC_DataStatusUpdate;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.adapter.DataAdapter;
import com.xiaocool.sugarangel.adapter.DataListAdapter;
import com.xiaocool.sugarangel.bean.HistoryDataBean;
import com.xiaocool.sugarangel.bean.UserInfo;
import com.xiaocool.sugarangel.helper.ActivityCollector;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.net.ResponseHelper;
import com.xiaocool.sugarangel.utils.DateUtils;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class DataActivity extends BaseActivity {
    private final int BIND_DEVICE = 0011;
    private final int LINK_DEVICE = 0012;
    private final int MC_SHUTDOWN = 0013;
    private final int NO_HISTORY = 0014;
    public static final int REFRESH = 1001;
    @BindView(R.id.iv_data_binddevice)
    ImageView ivDataBinddevice;
    @BindView(R.id.tv_bind_device)
    TextView tvBindDevice;
    @BindView(R.id.data_Xlist)
    XRecyclerView dataXlist;
    private SN_MainHandler Sn_MainHandler = null;
    private ArrayList<HistoryDataBean> historyLists;
    private ArrayList<BloodSugarData> uploadList;
    DataAdapter dataAdapter;
    private long exitTime = 0;
    Context mContext;
    UserInfo userInfo;
    String sn;

    @Override
    public int getContentViewId() {
        return R.layout.activity_data;
    }

    @Override
    public void initView() {
        Sn_MainHandler = SN_MainHandler.getBlueToothInstance();
        Sn_MainHandler.initSDK(this, ProtocolVersion.WL_1);//双模 手机支持BLE 使用BLE方式
        mContext = this;
        historyLists = new ArrayList<>();
        uploadList = new ArrayList<>();
//        dataListAdapter = new DataListAdapter(mContext, historyLists);
//        dataList.setAdapter(dataListAdapter);
        userInfo = new UserInfo(mContext);
        registerReceiver(mBtReceiver, makeIntentFilter());
        setTitleBar("我的血糖值", true);
        if (userInfo.getDeviceBinded().equals("1")) {
            if (Sn_MainHandler.isConnected()) {
                tvBindDevice.setText("添加血糖记录");
                ivDataBinddevice.setImageResource(R.drawable.tongbushujv);
            } else {
                tvBindDevice.setText("设备未连接");
            }
        } else {
            tvBindDevice.setText("绑定设备");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dataXlist.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        dataXlist.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        dataXlist.setLayoutManager(linearLayoutManager);
        dataXlist.setHasFixedSize(true);
        dataAdapter = new DataAdapter(historyLists, mContext);
        dataXlist.setAdapter(dataAdapter);
        dataXlist.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                readHistoryData();
            }

            @Override
            public void onLoadMore() {
//                getMoreDatas(keyWord);
                dataXlist.loadMoreComplete();
            }
        });
        Sn_MainHandler.registerReceiveBloodSugarData(new SC_CurrentDataCallBack<BloodSugarData>() {

            @Override
            public void onStatusChange(int status) {
                // TODO Auto-generated method stub


                if (status == SC_DataStatusUpdate.SC_MC_SHUTTINGDOWN) {
                    ToastUtils.showShort(mContext, "设备正在关机");
                    Sn_MainHandler.disconnectDevice();
                } else if (status == SC_DataStatusUpdate.SC_MC_SHUTDOWN) {
//                    ToastUtils.showShort(mContext, "设备已关机");
                    loadHandler.sendEmptyMessage(MC_SHUTDOWN);

                }

            }

            @Override
            public void onReceiveSyncData(BloodSugarData datas) {

            }

            @Override
            public void onReceiveSucess(BloodSugarData datas) {
                // TODO Auto-generated method stub
//                float v = datas.getBloodSugarValue();
//                Date date = datas.getCreatTime();
//                float t = datas.getTemperature();
//                list.add(new deviceListItem("测试结果：" + v + "mmol/l," + "时间："
//                        + date.toLocaleString() + "当前温度：" + t + "°", false));
//                loadHandler.sendEmptyMessage(REFRESH);
            }
        });
    }

    /*
    * 绑定设备
    * */
    private void bindDevice(final String sn) {
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.BIND_DEVICE)
                .addParams("sn", sn)
                .addParams("phone", userInfo.getPhone())
                .addParams("userid", userInfo.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            ResponseHelper rh = new ResponseHelper(response);
                            if (rh.isSuccess()) {
                                ToastUtils.showShort(mContext, "设备绑定成功");
                                userInfo.setSn(sn);
                                userInfo.writeData(mContext);
                                tvBindDevice.setText("添加血糖记录");
                                ivDataBinddevice.setImageResource(R.drawable.tongbushujv);
                            } else {
                                ToastUtils.showShort(mContext, rh.getData());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();
                    }
                });

    }

    /*
       * 检测当前设备与绑定设备是否一致
       * */
    private void checkBindDevice() {
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.CHECK_BIND_DEVICE)
                .addParams("sn", sn)
                .addParams("userid", userInfo.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            ResponseHelper rh = new ResponseHelper(response);
                            if (rh.isSuccess()) {
                                for (int i = 0; i < uploadList.size(); i++) {
                                    uploadData(uploadList.get(i), i, uploadList.size());
                                }
                            } else {
                                ToastUtils.showShort(mContext, rh.getData());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();
                    }
                });

    }

    /*
     * 读取服务器历史数据
     * */
    private void readHistoryData() {

        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.GET_DEVICE_DATA)
                .addParams("userid", userInfo.getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                        dataXlist.refreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            ResponseHelper rh = new ResponseHelper(response);
                            if (rh.isSuccess()) {
                                String data = rh.getData();
                                List<HistoryDataBean> s = new Gson().fromJson(data,
                                        new TypeToken<List<HistoryDataBean>>() {
                                        }.getType());
                                Log.d("sss",s.get(0).getDatatime());
                                historyLists.clear();
                                historyLists.addAll(s);

                                dataAdapter.notifyDataSetChanged();
                            } else {
                                ToastUtils.showShort(mContext, rh.getData());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();
                        dataXlist.refreshComplete();
                    }
                });

    }

    /*
     * 上传数据
     * */
    private void uploadData(BloodSugarData sugarData, final int i, final int size) {

        OkHttpUtils.post().url(NetConstant.ADD_DEVICE_DATA_BYUSERIDSN)
                .addParams("sn", sn)
                .addParams("userid", userInfo.getId())
                .addParams("temperature", sugarData.getTemperature() + "")
                .addParams("sampletype", sugarData.getSampleType() + "")
                .addParams("bloodsugar", sugarData.getBloodSugarValue() + "")
                .addParams("datatime",DateUtils.dateToUnixTimestamp(DateUtils.DateToString(sugarData.getCreatTime()) + "") + "")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            ResponseHelper rh = new ResponseHelper(response);
                            if (rh.isSuccess()) {

                                if (i == size - 1) {
                                    ToastUtils.showShort(mContext, "数据上传成功");
                                    //清除历史数据
                                    Sn_MainHandler.clearHistory(new SC_CmdCallBack() {

                                        @Override
                                        public void onCmdFeedback(int result) {
                                            // TODO Auto-generated method stub
                                            if (result == 1) {
                                                ToastUtils.showShort(mContext, "历史记录清除成功");
                                            } else {
                                                ToastUtils.showShort(mContext, "历史记录清除失败");
                                            }

                                        }
                                    });
                                }

                            } else {
                                ToastUtils.showShort(mContext, rh.getData());

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();
                    }
                });

    }

    /*
    * 读取血糖仪内数据
    * */
    private void readHistory() {
        Sn_MainHandler.readHistoryDatas(new SC_DataCallBack<ArrayList<BloodSugarData>>() {

            @Override
            public void onStatusChange(int status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onReceiveSucess(ArrayList<BloodSugarData> datas, int currentPackage, int totalPackages) {
                // TODO Auto-generated method stub

                if (datas.size() == 0) {
                    // ToastUtils.showShort(mContext, "无历史数据");
                    loadHandler.sendEmptyMessage(NO_HISTORY);
                } else {
                    for (int i = 0; i < datas.size(); i++) {
                        BloodSugarData data = datas.get(i);
                        HistoryDataBean historyDataBean = new HistoryDataBean();
                        historyDataBean.setDatatime((DateUtils.dateToUnixTimestamp(DateUtils.DateToString(data.getCreatTime()) + "")) + "");
                        historyDataBean.setSampletype(data.getSampleType() + "");
                        historyDataBean.setTemperature(String.valueOf(data.getTemperature()));
                        historyDataBean.setBloodsugar(data.getBloodSugarValue() + "");
                        historyLists.add(historyDataBean);
                        uploadList.add(data);

                    }

                    loadHandler.sendEmptyMessageDelayed(REFRESH, 100);
                }
            }
        });
    }

    //广播监听SDK ACTION
    private final BroadcastReceiver mBtReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (SN_MainHandler.ACTION_SN_CONNECTION_STATE_CHANGED.equals(action)) {
                //D.log("ACTION_HUD_CONNECTION_STATE_CHANGED");
                if (Sn_MainHandler.isConnected()) {
                    tvBindDevice.setText("添加血糖记录");
                    handler.post(runnable);
                } else if (Sn_MainHandler.isIdleState() || Sn_MainHandler.isDisconnecting()) {

                    handler.removeCallbacks(runnable);
                } else if (Sn_MainHandler.isSearching()) {
                }
            } else if (SN_MainHandler.ACTION_SN_ERROR_STATE.equals(action)) {
                Bundle bundle = intent.getExtras();
                int errorStatus = bundle.getInt(SN_MainHandler.EXTRA_ERROR_STATUS);
//                if(errorStatus== SC_ErrorStatus.SC_OVER_RANGED_TEMPERATURE)
//                    list.add(new deviceListItem("错误码：E-2", false));
//                else if(errorStatus==SC_ErrorStatus.SC_AUTH_ERROR)
//                    list.add(new deviceListItem("错误：认证失败！", false));
//                else if(errorStatus==SC_ErrorStatus.SC_ERROR_OPERATE)
//                    list.add(new deviceListItem("错误码：E-3！", false));
//                else if(errorStatus==SC_ErrorStatus.SC_ERROR_FACTORY)
//                    list.add(new deviceListItem("错误码：E-6！", false));
//                else if(errorStatus==SC_ErrorStatus.SC_ABLOVE_MAX_VALUE)
//                    list.add(new deviceListItem("错误码：HI", false));
//                else if(errorStatus==SC_ErrorStatus.SC_BELOW_LEAST_VALUE)
//                    list.add(new deviceListItem("错误码：LO", false));
//                else if(errorStatus==SC_ErrorStatus.SC_LOW_POWER)
//                    list.add(new deviceListItem("错误码：E-1！", false));
//                else if(errorStatus==SC_ErrorStatus.SC_UNDEFINED_ERROR)
//                    list.add(new deviceListItem("未知错误！", false));
//                else if(errorStatus==6)
//                    list.add(new deviceListItem("E-6", false));
//                loadHandler.sendEmptyMessage(REFRESH);
            } else if (SN_MainHandler.ACTION_SN_MC_STATE.equals(action)) {
                Bundle bundle = intent.getExtras();
                int MCStatus = bundle.getInt(SN_MainHandler.EXTRA_MC_STATUS);
            }

        }
    };

    private IntentFilter makeIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SN_MainHandler.ACTION_SN_CONNECTION_STATE_CHANGED);
        intentFilter.addAction(SN_MainHandler.ACTION_SN_ERROR_STATE);
        intentFilter.addAction(SN_MainHandler.ACTION_SN_MC_STATE);
        return intentFilter;
    }

    @OnClick(R.id.iv_data_binddevice)
    public void onViewClicked() {
        switch (tvBindDevice.getText().toString()) {
            case "绑定设备":
                Intent intent = new Intent(mContext, BindDeviceActivity.class);
                startActivityForResult(intent, BIND_DEVICE);
                break;
            case "添加血糖记录":
                if (Sn_MainHandler.isConnected()) {
                    readHistory();
                } else {
                    ToastUtils.showShort(mContext, "请先连接设备");
                }
                break;
            case "设备未连接":
                Intent intents = new Intent(mContext, BindDeviceActivity.class);
                startActivityForResult(intents, LINK_DEVICE);
                break;
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case BIND_DEVICE:
                    if (Sn_MainHandler.isConnected()) {
                        ToastUtils.showShort(mContext, "已连接");
                    } else {
                        ToastUtils.showShort(mContext, "未连接");
                    }
                    AlertDialog.Builder StopDialog = new AlertDialog.Builder(mContext);//定义一个弹出框对象

                    StopDialog.setTitle("绑定设备");//标题
                    StopDialog.setMessage("确定要绑定此设备吗");
                    StopDialog.setPositiveButton("绑定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub
                            sn = data.getStringExtra("uuid");
                            bindDevice(sn);
                        }
                    });
                    StopDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    StopDialog.show();
                    break;
                case LINK_DEVICE:
                    if (Sn_MainHandler.isConnected()) {
                        ToastUtils.showShort(mContext, "已连接");
                        ivDataBinddevice.setImageResource(R.drawable.tongbushujv);
                        sn = data.getStringExtra("uuid");
                    } else {
                        ToastUtils.showShort(mContext, "未连接");
                    }

                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Sn_MainHandler.close();
        unregisterReceiver(mBtReceiver);
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!Sn_MainHandler.isBlueToothEnable()) {
            Intent enableIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, 3);
        }
        readHistoryData();
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        if (Sn_MainHandler.isConnected())
            Sn_MainHandler.disconnectDevice();
    }

    //主线程中的handler
    private Handler loadHandler = new Handler() {
        /**
         * 接受子线程传递的消息机制
         */
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;

            switch (what) {
                case REFRESH: {
                    dataAdapter.notifyDataSetChanged();
//                    dataList.setSelection(historyLists.size());
                    checkBindDevice();
                    Log.d("sn##", sn + "userid" + userInfo.getId());
                    break;
                }
                case MC_SHUTDOWN:
                    ToastUtils.showShort(mContext, "设备已关机");
                    tvBindDevice.setText("设备未连接");
                    break;
                case NO_HISTORY:
                    ToastUtils.showShort(mContext, "无历史记录");
                    break;
            }
        }

    };

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            handler.postDelayed(this, 4000);
            Sn_MainHandler.requestAllRecord();
            //handler.removeCallbacks(runnable);
        }

    };


}

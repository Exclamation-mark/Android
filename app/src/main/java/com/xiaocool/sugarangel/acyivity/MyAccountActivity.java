package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.RelativesBean;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.net.ResponseHelper;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MyAccountActivity extends BaseActivity {


    @BindView(R.id.account_user_score)
    TextView accountUserScore;
    Context mContext;
    @Override
    public int getContentViewId() {
        return R.layout.activity_my_account;
    }

    @Override
    public void initView() {
        mContext = this;
        getUserScore();
    }
    //获取用户积分
    private void getUserScore(){
        showProgressDialog(true);
        OkHttpUtils.post().url(NetConstant.GET_USER_SCORE)
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
                                accountUserScore.setText(data);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();
                    }

                });
    }
}

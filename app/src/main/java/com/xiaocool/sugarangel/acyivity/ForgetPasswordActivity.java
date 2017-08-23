package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaocool.sugarangel.R;
import com.xiaocool.sugarangel.bean.UserInfo;
import com.xiaocool.sugarangel.net.NetConstant;
import com.xiaocool.sugarangel.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ForgetPasswordActivity extends BaseActivity {


    @BindView(R.id.fog_phone_edt)
    EditText fogPhoneEdt;
    @BindView(R.id.change_get_code_btn)
    TextView changeGetCodeBtn;
    @BindView(R.id.fog_code_edt)
    EditText fogCodeEdt;
    @BindView(R.id.fog_pw_edt)
    EditText fogPwEdt;
    @BindView(R.id.fog_pw_nextpw)
    EditText fogPwNextpw;
    @BindView(R.id.fog_pw_btn)
    Button fogPwBtn;
    private TimerTask timerTask;
    private Timer timer;
    private int time;
    private Context mContext;
    private UserInfo userInfo;

    @Override
    public int getContentViewId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void initView() {
        setTitleBar("忘记密码", true);
        mContext = this;
        userInfo = new UserInfo(mContext);
        fogPhoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 11) {
                    changeGetCodeBtn.setEnabled(true);
                } else {
                    changeGetCodeBtn.setEnabled(false);
                }
            }
        });
        timer = new Timer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 获取验证码
     */
    private void getCode() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        time--;
                        if (time > 0) {
                            changeGetCodeBtn.setText("" + time);
                            changeGetCodeBtn.setEnabled(false);
                        } else {
                            changeGetCodeBtn.setText("获取验证码");
                            changeGetCodeBtn.setEnabled(true);
                            timerTask.cancel();
                        }
                    }
                });
            }
        };
        time = 60;
        timer.schedule(timerTask, 0, 1000);

        OkHttpUtils.post().url(NetConstant.GET_MOBILE_CODE)
                .addParams("phone", fogPhoneEdt.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String state = jsonObject.getString("status");
                            if ("success".equals(state)) {
                                ToastUtils.showShort(mContext, "发送成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /*
    *忘记|修改密码
    * */
    private void goChange() {
        OkHttpUtils.post().url(NetConstant.FORGET_PW)
                .addParams("phone", fogPhoneEdt.getText().toString())
                .addParams("code", fogCodeEdt.getText().toString())
                .addParams("password", fogPwNextpw.getText().toString())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String state = jsonObject.getString("status");
                            if ("success".equals(state)) {
                                finish();
                                ToastUtils.showShort(mContext, "密码重置成功");
                            } else {
                                ToastUtils.showShort(mContext, "密码重置失败");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @OnClick({R.id.change_get_code_btn, R.id.fog_pw_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_get_code_btn:
                getCode();
                break;
            case R.id.fog_pw_btn:
                if (fogPhoneEdt.getText().toString().equals("")) {
                    ToastUtils.showShort(mContext, "请输入手机号");
                    return;
                } else if (fogCodeEdt.getText().toString().equals("")) {
                    ToastUtils.showShort(mContext, "请输入验证码");
                    return;
                } else if (fogPwEdt.getText().toString().equals("")) {
                    ToastUtils.showShort(mContext, "请输入密码");
                    return;
                } else if (!(fogPwEdt.getText().toString().equals(fogPwNextpw.getText().toString()))) {
                    ToastUtils.showShort(mContext, "您两次密码输入不一致");
                    return;
                } else {
                    goChange();
                }

                break;
        }
    }
}

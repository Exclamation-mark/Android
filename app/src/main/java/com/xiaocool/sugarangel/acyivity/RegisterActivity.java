package com.xiaocool.sugarangel.acyivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiaocool.sugarangel.R;
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

public class RegisterActivity extends BaseActivity {


    @BindView(R.id.phone_edt)
    EditText phoneEdt;
    @BindView(R.id.get_code_btn)
    TextView getCodeBtn;
    @BindView(R.id.code_edt)
    EditText codeEdt;
    @BindView(R.id.password_edt)
    EditText passwordEdt;
    @BindView(R.id.name_edt)
    EditText usernameEdt;
    @BindView(R.id.show_pass_btn)
    ImageView showPassBtn;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    private TimerTask timerTask;
    private Timer timer;
    private int time;
    private Context mContext;

    @Override
    public int getContentViewId() {
        return R.layout.activity_rejister;
    }

    @Override
    public void initView() {
        setTitleBar("注册", true);
        mContext = this;
        phoneEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 11) {
                    getCodeBtn.setEnabled(true);
                } else {
                    getCodeBtn.setEnabled(false);
                }
            }
        });
        timer = new Timer();
    }

    @OnClick({R.id.get_code_btn, R.id.show_pass_btn, R.id.confirm_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_code_btn:
                getCode();
                break;
            case R.id.show_pass_btn:
                //记住光标开始的位置
                int pos = passwordEdt.getSelectionStart();
                if (passwordEdt.getInputType() != (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {//隐藏密码
                    passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    showPassBtn.setImageResource(R.drawable.ic_close_eyes);
                } else {//显示密码
                    passwordEdt.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    showPassBtn.setImageResource(R.drawable.ic_open_eyes);
                }
                passwordEdt.setSelection(pos);
                break;
            case R.id.confirm_btn:
                goRegister();
                break;
        }
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
                            getCodeBtn.setText("" + time);
                            getCodeBtn.setEnabled(false);
                        } else {
                            getCodeBtn.setText("获取验证码");
                            getCodeBtn.setEnabled(true);
                            timerTask.cancel();
                        }
                    }
                });
            }
        };
        time = 60;
        timer.schedule(timerTask, 0, 1000);

        OkHttpUtils.post().url(NetConstant.GET_MOBILE_CODE)
                .addParams("phone", phoneEdt.getText().toString())
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
                                ToastUtils.showShort(RegisterActivity.this, "发送成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }


    private void goRegister() {
        final String phoneStr = phoneEdt.getText().toString();
        final String passwordStr = passwordEdt.getText().toString();
        final String userNameStr = usernameEdt.getText().toString();
        String code = codeEdt.getText().toString();
        if (userNameStr.length() < 2) {
            ToastUtils.showShort(this, "姓名不能少于两个字");
            return;
        }
        if (phoneStr.length() < 11) {
            ToastUtils.showShort(this, "请输入11位手机号码");
            return;
        }
        if (passwordStr.length() < 6) {
            ToastUtils.showShort(this, "密码必须大于6位");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtils.showShort(this, "请输入验证码");
            return;
        }

        showProgressDialog(true);

        OkHttpUtils.post().url(NetConstant.REGISTER)
                .addParams("phone", phoneStr)
                .addParams("password", passwordStr)
                .addParams("code", code)
                .addParams("usertype", "1")
                .addParams("userName", userNameStr)
                .addParams("devicetype", NetConstant.DEVICE_STATE)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        closeProgressDialog();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equals("success")) {
//                                SPUtils.putWithCommit(mContext, SPHelper.USER_PHONE,phoneStr);
//                                SPUtils.putWithCommit(mContext,SPHelper.PASSWORD,passwordStr);
                                setResult(RESULT_OK);
                                finish();
                                ToastUtils.showShort(RegisterActivity.this, "注册成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        closeProgressDialog();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerTask != null) {
            timerTask.cancel();
        }
        if (timer != null) {
            timer.cancel();
        }
    }
}
